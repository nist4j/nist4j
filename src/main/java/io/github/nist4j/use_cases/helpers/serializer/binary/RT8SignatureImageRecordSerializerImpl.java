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
import io.github.nist4j.enums.records.RT8FieldsEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT8SignatureImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.ByteToStringConverter;
import io.github.nist4j.use_cases.helpers.serializer.RecordReader;
import io.github.nist4j.use_cases.helpers.serializer.RecordWriter;
import io.github.nist4j.use_cases.helpers.serializer.binary.abstracts.AbstractImageBinaryRecordSerializer;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RT8SignatureImageRecordSerializerImpl
    extends AbstractImageBinaryRecordSerializer<RT8SignatureImageNistRecordBuilderImpl>
    implements RecordReader, RecordWriter {

  public static final int FIXED_SIZE_OF_FIELDS = 12;
  private final ByteToStringConverter byteToStringConverter;

  public RT8SignatureImageRecordSerializerImpl(NistOptions nistOptions) {
    super(nistOptions, new RT8SignatureImageNistRecordBuilderImpl(nistOptions));
    byteToStringConverter = new ByteToStringConverter(nistOptions);
  }

  @Override
  public NistRecord read(NistDecoderHelper.Token token) throws ErrorDecodingNist4jException {
    checkIfPosOutOfIndex(token);

    NistRecordBuilder nistRecordBuilder = getNistRecordBuilder().newBuilder();
    int recordId = nistRecordBuilder.getRecordId();

    // 8.001 : LEN
    int length = (int) read4BytesAsInt(token);
    {
      Data dataText =
          new DataTextBuilder().withValue(longToStringConverter.toString(length)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.LEN, dataText);
    }

    if (checkRecordSizeLength(token, 4)) {
      // 8.002 : IDC
      int idc = token.buffer[token.pos + 4];
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(idc)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.IDC, dataText);
    }

    if (checkRecordSizeLength(token, 5)) {
      int sig = token.buffer[token.pos + 5];
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(sig)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.SIG, dataText);
    }

    if (checkRecordSizeLength(token, 6)) {
      // 8.004 : SRT
      int srt = token.buffer[token.pos + 6];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(srt)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.SRT, dataText);
    }

    if (checkRecordSizeLength(token, 7)) {
      // 8.004 : ISR
      int isr = token.buffer[token.pos + 7];
      Data dataText = new DataTextBuilder().withValue(byteToStringConverter.toString(isr)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.ISR, dataText);
    }

    if (checkRecordSizeLength(token, 8)) {
      long hll = read2BytesAsInt(token, 8);
      log.debug("T{} recordBuilder - parsing du recordBuilder HLL {}", recordId, hll);
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(hll)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.HLL, dataText);
    }

    if (checkRecordSizeLength(token, 10)) {
      long vll = read2BytesAsInt(token, 10);
      log.debug("T{} recordBuilder - parsing du recordBuilder VLL {}", recordId, vll);
      Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(vll)).build();
      nistRecordBuilder.withField(RT8FieldsEnum.VLL, dataText);
    }

    // 8.999 : DATA
    int dataSize = length - FIXED_SIZE_OF_FIELDS;

    if (token.pos + dataSize + FIXED_SIZE_OF_FIELDS - 1 > token.buffer.length) {
      dataSize += token.buffer.length - token.pos - FIXED_SIZE_OF_FIELDS;
    }

    if (dataSize > 0) {
      byte[] data = new byte[dataSize];
      System.arraycopy(token.buffer, token.pos + FIXED_SIZE_OF_FIELDS, data, 0, dataSize);
      Data dataImage = new DataImageBuilder().withValue(data).build();
      nistRecordBuilder.withField(RT8FieldsEnum.DATA, dataImage);
    }

    token.pos += length;

    return nistRecordBuilder.build();
  }

  @Override
  public void write(OutputStream outputStream, NistRecord record) {
    try {
      write4IntByteToken(outputStream, record, RT8FieldsEnum.LEN);

      // token.buffer[token.pos + 4];
      write1IntByteToken(outputStream, record, RT8FieldsEnum.IDC);
      // token.buffer[token.pos + 5];
      write1IntByteToken(outputStream, record, RT8FieldsEnum.SIG);
      // token.buffer[token.pos + 6];
      write1IntByteToken(outputStream, record, RT8FieldsEnum.SRT);
      // token.buffer[token.pos + 7];
      write1IntByteToken(outputStream, record, RT8FieldsEnum.ISR);

      // long hll = read2BytesAsInt(token, 8);
      write2IntByteToken(outputStream, record, RT8FieldsEnum.HLL);

      // long vll = read2BytesAsInt(token, 10);
      write2IntByteToken(outputStream, record, RT8FieldsEnum.VLL);

      // int gca = token.buffer[token.pos + 12];
      // Image
      writeDataToken(outputStream, record, RT8FieldsEnum.DATA);
    } catch (IOException e) {
      log.error("Error writing record", e);
      throw new ErrorEncodingNist4jException(e.getMessage());
    }
  }

  private int calculateLength(NistRecord record) {
    return FIXED_SIZE_OF_FIELDS + record.getFieldLength(RT8FieldsEnum.DATA).orElse(0);
  }
}
