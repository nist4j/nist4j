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

import static java.util.Optional.ofNullable;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.entities.validation.NistValidationErrorBuilder;
import io.github.nist4j.entities.validation.impl.NistValidationErrorImpl;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
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

  @Getter private String recordName;
  @Getter private String fieldName;
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
      @NonNull INistValidationErrorEnum errorEnum, Object attemptedFound) {

    return newNistValidationErrorBuilder(errorEnum).withAttemptedFound(attemptedFound);
  }

  public static NistValidationErrorBuilderImpl newNistValidationErrorBuilder(
      @NonNull INistValidationErrorEnum errorEnum) {

    String recordName =
        ofNullable(errorEnum.getFieldTypeEnum()).map(IFieldTypeEnum::getRecordType).orElse(null);
    return new NistValidationErrorBuilderImpl()
        .withRecordName(recordName)
        .withFieldName(errorEnum.getFieldName())
        .withCode(errorEnum.getCode())
        .withMessage(errorEnum.getMessage());
  }

  public NistValidationError build() {
    return new NistValidationErrorImpl(this);
  }

  public NistValidationErrorBuilderImpl withRecordName(String recordName) {
    this.recordName = recordName;
    return this;
  }

  public NistValidationErrorBuilderImpl withFieldName(String fieldName) {
    this.fieldName = fieldName;
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
