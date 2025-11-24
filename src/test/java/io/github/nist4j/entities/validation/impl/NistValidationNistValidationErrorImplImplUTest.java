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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationErrorBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import org.junit.jupiter.api.Test;

class NistValidationNistValidationErrorImplImplUTest {
  @Test
  void toString_should_return_correct_message_when_fieldName_is_filled() {
    // Given
    String expectedString =
        "{code='STD_ERR_VER_RT1', recordType='RT1', fieldType='VER', subfieldName='null', message='1.002 VER invalid field \"VER\"', valueFound='FOUND'}";
    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder()
            .withRecordType(RT1)
            .withFieldType(RT1FieldsEnum.VER)
            .withCode(STD_ERR_VER_RT1.getCode())
            .withMessage(ValidationMessage.format(STD_ERR_VER_RT1, RT1, RT1FieldsEnum.VER))
            .withAttemptedFound("FOUND")
            .build();

    // Then
    assertThat(nistValidationError.toString()).contains(expectedString);
  }

  @Test
  void builder_should_create_and_be_accessed_by_getter() {
    // Given
    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder()
            .withRecordType(RT1)
            .withFieldType(RT1FieldsEnum.VER)
            .withCode("CODE")
            .withMessage("MESSAGE")
            .withAttemptedFound("VAL")
            .build();

    // Then
    assertThat(nistValidationError.getRecordType()).isEqualTo(RT1);
    assertThat(nistValidationError.getValueFound()).isEqualTo("VAL");
    assertThat(nistValidationError.getCode()).isEqualTo("CODE");
    assertThat(nistValidationError.getFieldType()).isEqualTo(RT1FieldsEnum.VER);
    assertThat(nistValidationError.getMessage()).isEqualTo("MESSAGE");
  }

  @Test
  void equals_should_be_implemented() {
    // Given
    NistValidationError expectedValue =
        newNistValidationErrorBuilder()
            .withRecordType(RT1)
            .withFieldType(RT1FieldsEnum.VER)
            .withCode("CODE")
            .withMessage("MESSAGE")
            .withAttemptedFound("VAL")
            .build();

    // When
    NistValidationError nistValidationError =
        newNistValidationErrorBuilder()
            .withRecordType(RT1)
            .withFieldType(RT1FieldsEnum.VER)
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
