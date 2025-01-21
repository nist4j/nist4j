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

import static java.lang.String.format;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.use_cases.helpers.converters.LongTo2BytesConverter;
import io.github.nist4j.use_cases.helpers.converters.LongTo4BytesConverter;
import java.io.IOException;
import java.io.OutputStream;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBinaryRecordSerializer extends AbstractRecordSerializer {

  protected AbstractBinaryRecordSerializer(
      NistOptions nistOptions, NistRecordBuilder nistRecordBuilder) {
    super(nistOptions, nistRecordBuilder);
  }

  protected void write1IntByteToken(
      @NonNull OutputStream outputStream,
      @NonNull NistRecord nistRecord,
      @NonNull IFieldTypeEnum field)
      throws IOException {

    int intVal = nistRecord.getFieldText(field).map(Integer::valueOf).orElse(EMPTY_INT);
    log.debug("T{} record - writing 1IntByte field {}", nistRecord.getRecordId(), intVal);
    outputStream.write(intVal);
  }

  protected void write2IntByteToken(
      @NonNull OutputStream outputStream, @NonNull NistRecord record, @NonNull IFieldTypeEnum field)
      throws IOException {

    byte[] buffer =
        record
            .getFieldText(field)
            .map(Integer::valueOf)
            .map(LongTo2BytesConverter::to2Bytes)
            .orElse(EMPTY_BYTES2);
    log.debug("T{} record - writing 2IntByte field {}", record.getRecordId(), field.getCode());
    outputStream.write(buffer);
  }

  protected void write4IntByteToken(
      @NonNull OutputStream outputStream, @NonNull NistRecord record, @NonNull IFieldTypeEnum field)
      throws IOException {

    byte[] buffer =
        record
            .getFieldText(field)
            .map(Integer::valueOf)
            .map(LongTo4BytesConverter::to4Bytes)
            .orElse(EMPTY_BYTES4);

    log.debug("T{} record - writing 4IntByte field {}", record.getRecordId(), field.getCode());
    outputStream.write(buffer);
  }

  protected void writeDataToken(
      @NonNull OutputStream outputStream, @NonNull NistRecord record, @NonNull IFieldTypeEnum field)
      throws IOException {

    if (field.getTypeClass() != DataImageImmutableImpl.class
        && field.getTypeClass() != DataImage.class) {
      throw new InvalidFormatNist4jException(format("Field %s is not type Data", field.getCode()));
    }
    if (!record.getFieldImage(field).isPresent()) {
      throw new InvalidFormatNist4jException(format("Missing %s field", field.getCode()));
    }

    byte[] byteArrays = record.getFieldImage(field).get();
    log.debug("T{} record - writing du field  {}", record.getRecordId(), field.getCode());
    outputStream.write(byteArrays);
  }
}
