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
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.records.RT1FieldsEnum;
import org.junit.jupiter.api.Test;

class NistValidationErrorImplUTest {
  @Test
  void toString_should_return_correct_message_when_fieldName_is_filled() {
    // Given
    String expectedString =
        "{code='STD_ERR_VER_RT1', record='RT1', fieldName='VER', message='Invalid field \"VER\"', valueFound='FOUND'}";
    // When
    NistValidationErrorImpl nistValidationError =
        NistValidationErrorImpl.builder()
            .message(STD_ERR_VER_RT1.getMessage())
            .code(STD_ERR_VER_RT1.getCode())
            .recordName("RT1")
            .fieldName(RT1FieldsEnum.VER.getCode())
            .valueFound("FOUND")
            .build();

    // Then
    assertThat(nistValidationError.toString()).contains(expectedString);
  }

  @Test
  void buildFromNistValidationErrorEnum_should_return_correct_message_when_record_is_filled() {
    // Given
    // When
    NistValidationErrorImpl nistValidationError = new NistValidationErrorImpl(STD_ERR_SRC);

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
    NistValidationErrorImpl nistValidationError =
        NistValidationErrorImpl.builder()
            .message("MESSAGE")
            .code("CODE")
            .recordName("RT1")
            .fieldName("FIELD")
            .valueFound("VAL")
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
    NistValidationErrorImpl expectedValue =
        NistValidationErrorImpl.builder()
            .message("MESSAGE")
            .code("CODE")
            .recordName("RT1")
            .fieldName("FIELD")
            .valueFound("VAL")
            .build();

    // When
    NistValidationErrorImpl nistValidationError =
        NistValidationErrorImpl.builder()
            .message("MESSAGE")
            .code("CODE")
            .recordName("RT1")
            .fieldName("FIELD")
            .valueFound("VAL")
            .build();

    // Then
    assertThat(nistValidationError).isEqualTo(expectedValue);
    assertThat(nistValidationError.canEqual(expectedValue)).isTrue();
    assertThat(nistValidationError.equals(expectedValue)).isTrue();
    assertThat(nistValidationError.hashCode()).isEqualTo(expectedValue.hashCode());
    assertThat(nistValidationError.toString()).isEqualTo(expectedValue.toString());
  }
}
