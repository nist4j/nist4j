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
package io.github.nist4j.use_cases.helpers.serializer.binary;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.RT7FieldsEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT7UserDefinedImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.ByteToStringConverter;
import io.github.nist4j.use_cases.helpers.serializer.RecordReader;
import io.github.nist4j.use_cases.helpers.serializer.RecordWriter;
import io.github.nist4j.use_cases.helpers.serializer.binary.abstracts.AbstractImageBinaryRecordSerializer;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RT7UserDefinedImageRecordSerializerImpl
    extends AbstractImageBinaryRecordSerializer<RT7UserDefinedImageNistRecordBuilderImpl>
    implements RecordReader, RecordWriter {

  public static final int FIXED_SIZE_OF_FIELDS = 33;
  private final ByteToStringConverter byteToStringConverter;

  public RT7UserDefinedImageRecordSerializerImpl(NistOptions nistOptions) {
    super(nistOptions, new RT7UserDefinedImageNistRecordBuilderImpl(nistOptions));
    byteToStringConverter = new ByteToStringConverter(nistOptions);
  }

  @Override
  public NistRecord read(NistDecoderHelper.Token token) throws ErrorDecodingNist4jException {
    checkIfPosOutOfIndex(token);

    NistRecordBuilder nistRecordBuilder = getNistRecordBuilder().newBuilder();
    int recordId = nistRecordBuilder.getRecordId();

    // 7.001 : LEN
    int length = (int) read4BytesAsInt(token);
    {
      Data dataText =
          new DataTextBuilder().withValue(longToStringConverter.toString(length)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.LEN, dataText);
    }

    if (checkRecordSizeLength(token, 4)) {
      // 7.002 : IDC
      int idc = token.buffer[token.pos + 4];
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(idc)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IDC, dataText);
    }

    if (checkRecordSizeLength(token, 5)) {
      // 7.003 : IMT
      int imt = token.buffer[token.pos + 5];
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(imt)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMT, dataText);
    }

    if (checkRecordSizeLength(token, 6)) {
      // 7.004 : IMD
      int imd = token.buffer[token.pos + 6];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(imd)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMD, dataText);
    }

    if (checkRecordSizeLength(token, 7)) {
      int pcn = token.buffer[token.pos + 7];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(pcn)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.PCN, dataText);
    }

    if (checkRecordSizeLength(token, 8)) {
      int pcn2 = token.buffer[token.pos + 8];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(pcn2)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.PCN2, dataText);
    }

    if (checkRecordSizeLength(token, 9)) {
      int pcn3 = token.buffer[token.pos + 9];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(pcn3)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.PCN3, dataText);
    }

    if (checkRecordSizeLength(token, 10)) {
      int pcn4 = token.buffer[token.pos + 10];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(pcn4)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.PCN4, dataText);
    }

    if (checkRecordSizeLength(token, 11)) {
      int pcn5 = token.buffer[token.pos + 11];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(pcn5)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.PCN5, dataText);
    }

    if (checkRecordSizeLength(token, 17)) {
      int intVal = token.buffer[token.pos + 17];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR, dataText);
    }

    if (checkRecordSizeLength(token, 18)) {
      int intVal = token.buffer[token.pos + 18];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR2, dataText);
    }

    if (checkRecordSizeLength(token, 19)) {
      int intVal = token.buffer[token.pos + 19];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR3, dataText);
    }

    if (checkRecordSizeLength(token, 20)) {
      int intVal = token.buffer[token.pos + 20];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR4, dataText);
    }

    if (checkRecordSizeLength(token, 21)) {
      int intVal = token.buffer[token.pos + 21];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR5, dataText);
    }

    if (checkRecordSizeLength(token, 22)) {
      int intVal = token.buffer[token.pos + 22];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR6, dataText);
    }

    if (checkRecordSizeLength(token, 23)) {
      int intVal = token.buffer[token.pos + 23];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR7, dataText);
    }

    if (checkRecordSizeLength(token, 24)) {
      int intVal = token.buffer[token.pos + 24];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR8, dataText);
    }

    if (checkRecordSizeLength(token, 25)) {
      int intVal = token.buffer[token.pos + 25];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR9, dataText);
    }

    if (checkRecordSizeLength(token, 26)) {
      int intVal = token.buffer[token.pos + 26];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR10, dataText);
    }

    if (checkRecordSizeLength(token, 27)) {
      int intVal = token.buffer[token.pos + 27];
      Data dataText =
          new DataTextBuilder().withValue(byteToStringConverter.toString(intVal)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.IMR11, dataText);
    }

    if (checkRecordSizeLength(token, 28)) {
      long hll = read2BytesAsInt(token, 28);
      log.debug("T{} recordBuilder - parsing du recordBuilder HLL {}", recordId, hll);
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(hll)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.HLL, dataText);
    }

    if (checkRecordSizeLength(token, 30)) {
      long vll = read2BytesAsInt(token, 30);
      log.debug("T{} recordBuilder - parsing du recordBuilder VLL {}", recordId, vll);
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(vll)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.VLL, dataText);
    }

    if (checkRecordSizeLength(token, 32)) {
      int gca = token.buffer[token.pos + 32];
      log.debug("T{} recordBuilder - parsing du recordBuilder GCA {}", recordId, gca);
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(gca)).build();
      nistRecordBuilder.withField(RT7FieldsEnum.GCA, dataText);
    }

    // 7.999 : DATA
    int dataSize = length - FIXED_SIZE_OF_FIELDS;

    if (token.pos + dataSize + FIXED_SIZE_OF_FIELDS - 1 > token.buffer.length) {
      dataSize += token.buffer.length - token.pos - FIXED_SIZE_OF_FIELDS;
    }

    if (dataSize > 0) {
      byte[] data = new byte[dataSize];
      System.arraycopy(token.buffer, token.pos + FIXED_SIZE_OF_FIELDS, data, 0, dataSize);
      Data dataImage = new DataImageBuilder().withValue(data).build();
      nistRecordBuilder.withField(RT7FieldsEnum.DATA, dataImage);
    }

    token.pos += length;

    return nistRecordBuilder.build();
  }

  @Override
  public void write(OutputStream outputStream, NistRecord record) {
    try {
      write4IntByteToken(outputStream, record, RT7FieldsEnum.LEN);

      // token.buffer[token.pos + 4];
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IDC);
      // token.buffer[token.pos + 5];
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMT);
      // token.buffer[token.pos + 6];
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMD);
      // token.buffer[token.pos + 7];
      write1IntByteToken(outputStream, record, RT7FieldsEnum.PCN);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.PCN2);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.PCN3);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.PCN4);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.PCN5);
      // token.buffer[token.pos + 12];
      outputStream.write(new byte[] {3, 3, 3, 3, 3});
      // token.buffer[token.pos + 17];
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR2);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR3);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR4);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR5);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR6);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR7);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR8);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR9);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR10);
      write1IntByteToken(outputStream, record, RT7FieldsEnum.IMR11);

      // long hll = read2BytesAsInt(token, 28);
      write2IntByteToken(outputStream, record, RT7FieldsEnum.HLL);

      // long vll = read2BytesAsInt(token, 30);
      write2IntByteToken(outputStream, record, RT7FieldsEnum.VLL);

      // int gca = token.buffer[token.pos + 32];
      write1IntByteToken(outputStream, record, RT7FieldsEnum.GCA);

      // token.buffer[token.pos + 33];
      // Image
      writeDataToken(outputStream, record, RT7FieldsEnum.DATA);
    } catch (IOException e) {
      log.error("Error writing record", e);
      throw new ErrorEncodingNist4jException(e.getMessage());
    }
  }
}
