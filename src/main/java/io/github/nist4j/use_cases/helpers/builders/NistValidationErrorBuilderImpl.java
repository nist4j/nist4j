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
package io.github.nist4j.use_cases.helpers.builders;

import static java.lang.String.format;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.entities.validation.NistValidationErrorBuilder;
import io.github.nist4j.entities.validation.impl.NistValidationErrorImpl;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import lombok.Getter;
import lombok.NonNull;

public class NistValidationErrorBuilderImpl implements NistValidationErrorBuilder {

  private static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  @SuppressWarnings("unused")
  private final NistOptions nistOptions;

  @Getter private RecordTypeEnum recordType;
  @Getter private IFieldTypeEnum fieldType;
  @Getter private String subfieldName;
  @Getter private String code;
  @Getter private String message;
  @Getter private Object attemptedFound;

  @SuppressWarnings("unused")
  public NistValidationErrorBuilderImpl(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
  }

  public NistValidationErrorBuilderImpl() {
    this.nistOptions = DEFAULT_OPTIONS_FOR_VALIDATION;
  }

  public static NistValidationErrorBuilderImpl newNistValidationErrorBuilder() {
    return new NistValidationErrorBuilderImpl();
  }

  public static NistValidationErrorBuilderImpl newNistValidationErrorBuilder(
      RecordTypeEnum recordType,
      IFieldTypeEnum fieldType,
      @NonNull INistValidationErrorEnum error,
      Object attemptedFound) {

    return new NistValidationErrorBuilderImpl()
        .withRecordType(recordType)
        .withFieldType(fieldType)
        .withCode(error.getCode())
        .withMessage(ValidationMessage.format(error, recordType, fieldType))
        .withAttemptedFound(attemptedFound);
  }

  public static NistValidationErrorBuilderImpl newNistValidationErrorBuilder(
      RecordTypeEnum recordType,
      IFieldTypeEnum fieldType,
      @NonNull INistValidationErrorEnum error) {

    return new NistValidationErrorBuilderImpl()
        .withRecordType(recordType)
        .withFieldType(fieldType)
        .withCode(error.getCode())
        .withMessage(ValidationMessage.format(error, recordType, fieldType));
  }

  public static NistValidationErrorBuilderImpl newNistValidationErrorBuilder(
      RecordTypeEnum recordType, @NonNull INistValidationErrorEnum error) {

    return new NistValidationErrorBuilderImpl()
        .withRecordType(recordType)
        .withFieldType(null)
        .withCode(error.getCode())
        .withMessage(ValidationMessage.format(error, recordType));
  }

  public static INistValidationErrorEnum newNistValidationError(
      @NonNull INistValidationErrorEnum parentError,
      @NonNull RecordTypeEnum recordType,
      @NonNull IFieldTypeEnum fieldType,
      @NonNull String subfieldName,
      Object... args) {

    final String fieldName = format("%s.%s", fieldType.getId(), subfieldName);
    final Object[] formatArgs = new Object[2 + args.length];
    formatArgs[0] = recordType.getNumber();
    formatArgs[1] = fieldName;
    System.arraycopy(args, 0, formatArgs, 2, args.length);
    final String newMessage = format(parentError.getMessage(), formatArgs);

    return new INistValidationErrorEnum() {
      @Override
      public String getMessage() {
        return newMessage;
      }

      @Override
      public String getCode() {
        return parentError.getCode();
      }
    };
  }

  public NistValidationError build() {
    return new NistValidationErrorImpl(this);
  }

  public NistValidationErrorBuilderImpl withRecordType(RecordTypeEnum recordType) {
    this.recordType = recordType;
    return this;
  }

  public NistValidationErrorBuilderImpl withFieldType(IFieldTypeEnum fieldType) {
    this.fieldType = fieldType;
    return this;
  }

  public NistValidationErrorBuilderImpl withSubfieldName(String subfieldName) {
    this.subfieldName = subfieldName;
    return this;
  }

  public NistValidationErrorBuilderImpl withCode(String code) {
    this.code = code;
    return this;
  }

  public NistValidationErrorBuilderImpl withMessage(String message) {
    this.message = message;
    return this;
  }

  public NistValidationErrorBuilderImpl withAttemptedFound(Object attemptedFound) {
    this.attemptedFound = attemptedFound;
    return this;
  }
}
