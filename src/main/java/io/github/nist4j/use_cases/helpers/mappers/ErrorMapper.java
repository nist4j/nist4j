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
package io.github.nist4j.use_cases.helpers.mappers;

import br.com.fluentvalidator.context.Error;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMapper {

  public static Error toErrorOnRecord(
      @NonNull INistValidationErrorEnum error, String attemptedValueStr) {
    return Error.create(
        convertToField(error.getFieldTypeEnum(), Optional.empty()),
        error.getMessage(),
        error.getCode(),
        attemptedValueStr);
  }

  @Deprecated
  /* This method does not permit to add the record's number (idc) associated with the error */
  public static Error toErrorOnField(
      @NonNull INistValidationErrorEnum error, String attemptedValueStr) {
    return Error.create(
        error.getFieldName(), error.getMessage(), error.getCode(), attemptedValueStr);
  }

  public static Error toErrorOnField(
      @NonNull INistValidationErrorEnum error,
      String attemptedValueStr,
      Optional<String> idcRecord) {
    return Error.create(
        convertToField(error.getFieldTypeEnum(), idcRecord),
        error.getMessage(),
        error.getCode(),
        attemptedValueStr);
  }

  private static String convertToField(IFieldTypeEnum fieldTypeEnum, Optional<String> idcRecord) {
    return new ErrorCustomField(
            fieldTypeEnum.getCode(),
            String.valueOf(fieldTypeEnum.getId()),
            fieldTypeEnum.getRecordType(),
            idcRecord.orElse(""))
        .toString();
  }

  public static String getNistRecordFromError(Error error) {
    ErrorCustomField errorCustomField = new ErrorCustomField(error.getField());
    String idc =
        errorCustomField.getRecordIdc() != null
            ? String.format(" (IDC:%s)", errorCustomField.getRecordIdc())
            : "";
    return errorCustomField.getRecordType() + idc;
  }

  public static String getNistFieldFromError(Error error) {
    ErrorCustomField errorCustomField = new ErrorCustomField(error.getField());
    return String.format(
        "%s (%s)", errorCustomField.getFieldNumber(), errorCustomField.getFieldCode());
  }

  @Getter
  @AllArgsConstructor
  // "Error" object contains only one attribute "field" as string. But we have multiples values to
  // save : field number, field code, record idc and record type
  public static class ErrorCustomField {
    private static final String DELIMITER = "|";
    private String fieldCode;
    private String fieldNumber;
    private String recordType;
    private String recordIdc;

    @Override
    public String toString() {
      return Stream.of(fieldCode, fieldNumber, recordType, recordIdc)
          .map(str -> str == null ? "" : str)
          .collect(Collectors.joining(DELIMITER));
    }

    public ErrorCustomField(String string) {
      String[] stringList = string.split("\\|");
      this.fieldCode = stringList.length > 0 ? stringList[0] : null;
      this.fieldNumber = stringList.length > 1 ? stringList[1] : null;
      this.recordType = stringList.length > 2 ? stringList[2] : null;
      this.recordIdc = stringList.length > 3 ? stringList[3] : null;
    }
  }
}
