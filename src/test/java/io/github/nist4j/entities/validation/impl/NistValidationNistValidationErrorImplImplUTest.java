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
package io.github.nist4j.entities.validation.impl;

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationErrorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import org.junit.jupiter.api.Test;

class NistValidationNistValidationErrorImplImplUTest {
  @Test
  void toString_should_return_correct_message_when_fieldName_is_filled() {
    // Given
    String expectedString =
        "{code='STD_ERR_VER_RT1', record='RT1', fieldName='VER', message='Invalid field \"VER\"', valueFound='FOUND'}";
    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder()
            .withRecordName("RT1")
            .withFieldName(RT1FieldsEnum.VER.getCode())
            .withCode(STD_ERR_VER_RT1.getCode())
            .withMessage(STD_ERR_VER_RT1.getMessage())
            .withAttemptedFound("FOUND")
            .build();

    // Then
    assertThat(nistValidationError.toString()).contains(expectedString);
  }

  @Test
  void buildFromNistValidationErrorEnum_should_return_correct_message_when_record_is_filled() {
    // Given
    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder(STD_ERR_SRC, "VALUE").build();

    // Then
    assertThat(nistValidationError.getCode()).contains(STD_ERR_SRC.getCode());
    assertThat(nistValidationError.getMessage()).contains(STD_ERR_SRC.getMessage());
    assertThat(nistValidationError.getFieldName()).contains(STD_ERR_SRC.getFieldName());
    assertThat(nistValidationError.getRecordName()).contains("RT14");
  }

  @Test
  void builder_should_create_and_be_accessed_by_getter() {
    // Given
    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder()
            .withRecordName("RT1")
            .withFieldName("FIELD")
            .withCode("CODE")
            .withMessage("MESSAGE")
            .withAttemptedFound("VAL")
            .build();

    // Then
    assertThat(nistValidationError.getRecordName()).isEqualTo("RT1");
    assertThat(nistValidationError.getValueFound()).isEqualTo("VAL");
    assertThat(nistValidationError.getCode()).isEqualTo("CODE");
    assertThat(nistValidationError.getFieldName()).isEqualTo("FIELD");
    assertThat(nistValidationError.getMessage()).isEqualTo("MESSAGE");
  }

  @Test
  void equals_should_be_implemented() {
    // Given
    NistValidationError expectedValue =
        newNistValidationErrorBuilder()
            .withRecordName("RT1")
            .withFieldName("FIELD")
            .withCode("CODE")
            .withMessage("MESSAGE")
            .withAttemptedFound("VAL")
            .build();

    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder()
            .withRecordName("RT1")
            .withFieldName("FIELD")
            .withCode("CODE")
            .withMessage("MESSAGE")
            .withAttemptedFound("VAL")
            .build();

    // Then
    assertThat(nistValidationError).isEqualTo(expectedValue);
    assertThat(nistValidationError.equals(expectedValue)).isTrue();
    assertThat(nistValidationError.hashCode()).isEqualTo(expectedValue.hashCode());
    assertThat(nistValidationError.toString()).isEqualTo(expectedValue.toString());
  }
}
