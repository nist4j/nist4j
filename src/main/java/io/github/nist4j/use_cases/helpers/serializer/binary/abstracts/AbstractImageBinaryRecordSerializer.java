/*
 * Copyright (C) 2025 Sopra Steria.
 *
 * Licenced under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.nist4j.use_cases.helpers.serializer.binary.abstracts;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.converters.ByteToStringConverter;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.serializer.RecordReader;
import io.github.nist4j.use_cases.helpers.serializer.RecordWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractImageBinaryRecordSerializer<Z extends NistRecordBuilder>
    extends AbstractBinaryRecordSerializer implements RecordReader, RecordWriter {

  private final ByteToStringConverter byteToStringConverter;

  protected AbstractImageBinaryRecordSerializer(NistOptions nistOptions, Z recordBuilder) {
    super(nistOptions, recordBuilder);
    byteToStringConverter = new ByteToStringConverter(nistOptions);
  }

  @Override
  public NistRecord read(NistDecoderHelper.Token token) throws ErrorDecodingNist4jException {
    checkIfPosOutOfIndex(token);

    NistRecordBuilder nistRecordBuilder = getNistRecordBuilder().newBuilder();
    int recordId = nistRecordBuilder.getRecordId();

    // X.001 : LEN
    int length = (int) read4BytesAsInt(token);
    {
      Data dataText =
          new DataTextBuilder().withValue(longToStringConverter.toString(length)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.LEN, dataText);
    }

    if (checkRecordSizeLength(token, 4)) {
      // X.002 : IDC
      int fingerPrintNo = token.buffer[token.pos + 4];
      Data dataText =
          new DataTextBuilder().withValue(longToStringConverter.toString(fingerPrintNo)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.IDC, dataText);
    }

    if (checkRecordSizeLength(token, 5)) {
      // X.003 : IMP
      int imp = token.buffer[token.pos + 5];
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(imp)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.IMP, dataText);
    }

    List<String> fgpArrays = new ArrayList<>();
    if (checkRecordSizeLength(token, 6)) {
      // X.004 : FGP
      fgpArrays.add(byteToStringConverter.toString(token.buffer[token.pos + 6]));
    }

    if (checkRecordSizeLength(token, 7)) {
      fgpArrays.add(byteToStringConverter.toString(token.buffer[token.pos + 7]));
    }

    if (checkRecordSizeLength(token, 8)) {
      fgpArrays.add(byteToStringConverter.toString(token.buffer[token.pos + 8]));
    }

    if (checkRecordSizeLength(token, 9)) {
      fgpArrays.add(byteToStringConverter.toString(token.buffer[token.pos + 9]));
    }

    if (checkRecordSizeLength(token, 10)) {
      fgpArrays.add(byteToStringConverter.toString(token.buffer[token.pos + 10]));
    }

    if (checkRecordSizeLength(token, 11)) {
      fgpArrays.add(byteToStringConverter.toString(token.buffer[token.pos + 11]));
    }
    Data dataTextFGP = new DataTextBuilder().withItems(fgpArrays).build();
    nistRecordBuilder.withField(GenericImageTypeEnum.FGP, dataTextFGP);

    if (checkRecordSizeLength(token, 12)) {
      int isr = token.buffer[token.pos + 12];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(isr)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.ISR, dataText);
    }

    if (checkRecordSizeLength(token, 13)) {
      long hll = read2BytesAsInt(token, 13);
      log.debug("T{} record - parsing du record HLL {}", recordId, hll);
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(hll)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.HLL, dataText);
    }

    if (checkRecordSizeLength(token, 15)) {
      long vll = read2BytesAsInt(token, 15);
      log.debug("T{} record - parsing du record VLL {}", recordId, vll);
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(vll)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.VLL, dataText);
    }

    if (checkRecordSizeLength(token, 17)) {
      int gca = token.buffer[token.pos + 17];
      log.debug("T{} record - parsing du record GCA {}", recordId, gca);
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(gca)).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.GCA, dataText);
    }

    // X.999 : DATA
    int dataSize = length - 18;

    if (token.pos + dataSize + 17 > token.buffer.length) {
      dataSize += token.buffer.length - token.pos - 18;
    }

    if (dataSize > 0) {
      byte[] data = new byte[dataSize];
      System.arraycopy(token.buffer, token.pos + 18, data, 0, data.length + 18 - 18);
      Data dataImage = new DataImageBuilder().withValue(data).build();
      nistRecordBuilder.withField(GenericImageTypeEnum.DATA, dataImage);
    }

    token.pos += length;

    return nistRecordBuilder.build();
  }

  @Override
  public void write(OutputStream outputStream, NistRecord record) {
    try {
      write4IntByteToken(outputStream, record, GenericImageTypeEnum.LEN);

      // int idc = token.buffer[token.pos + 4];
      write1IntByteToken(outputStream, record, GenericImageTypeEnum.IDC);
      // int imp = token.buffer[token.pos + 5];
      write1IntByteToken(outputStream, record, GenericImageTypeEnum.IMP);
      // int fingerPrintNo = token.buffer[token.pos + 6];

      byte[] fgpBytes =
          new byte[] {
            (byte) EMPTY_INT,
            (byte) EMPTY_INT,
            (byte) EMPTY_INT,
            (byte) EMPTY_INT,
            (byte) EMPTY_INT,
            (byte) EMPTY_INT
          };
      Optional<String> fieldFPG = record.getFieldText(GenericImageTypeEnum.FGP);
      if (fieldFPG.isPresent()) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Stream.of(fieldFPG.get())
            .flatMap((ss) -> SubFieldToStringConverter.toItems(ss).stream())
            .forEach((n) -> fgpBytes[atomicInteger.getAndIncrement()] = (byte) Integer.parseInt(n));
      }
      outputStream.write(fgpBytes);

      // int isr = token.buffer[token.pos + 12];
      write1IntByteToken(outputStream, record, GenericImageTypeEnum.ISR);

      // long hll = read2BytesAsInt(token, 13);
      write2IntByteToken(outputStream, record, GenericImageTypeEnum.HLL);

      // long vll = read2BytesAsInt(token, 15);
      write2IntByteToken(outputStream, record, GenericImageTypeEnum.VLL);

      // int gca = token.buffer[token.pos + 17];
      write1IntByteToken(outputStream, record, GenericImageTypeEnum.GCA);

      // Image
      writeDataToken(outputStream, record, GenericImageTypeEnum.DATA);
    } catch (IOException e) {
      log.error("Error writing record", e);
      throw new ErrorEncodingNist4jException(e.getMessage());
    }
  }
}
