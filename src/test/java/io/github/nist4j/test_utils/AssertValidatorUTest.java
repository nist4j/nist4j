/*
 * Copyright (C) 2026 Sopra Steria.
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
package io.github.nist4j.test_utils;

import static io.github.nist4j.enums.records.RT1FieldsEnum.VER;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import org.junit.jupiter.api.Test;

class AssertValidatorUTest {

  @Test
  void containsValidMsg_should_verify_message_content() {
    // Given
    NistValidationError errOk =
        new NistValidationErrorBuilderImpl()
            .withRecordType(RecordTypeEnum.RT1)
            .withFieldType(VER)
            .withCode(StdNistValidatorErrorEnum.STD_ERR_VER_RT1.name())
            .withMessage(
                ValidationMessage.format(
                    StdNistValidatorErrorEnum.STD_ERR_VER_RT1, RecordTypeEnum.RT1, VER))
            .withAttemptedFound("attemptedValue")
            .build();
    NistValidationError errBad =
        new NistValidationErrorBuilderImpl()
            .withRecordType(RecordTypeEnum.RT1)
            .withFieldType(VER)
            .withCode(StdNistValidatorErrorEnum.STD_ERR_VER_RT1.name())
            .withMessage(StdNistValidatorErrorEnum.STD_ERR_VER_RT1.getMessage())
            .withAttemptedFound("attemptedValue")
            .build();
    NistValidationError errBad2 =
        new NistValidationErrorBuilderImpl()
            .withRecordType(RecordTypeEnum.RT1)
            .withFieldType(VER)
            .withCode(StdNistValidatorErrorEnum.STD_ERR_VER_RT1.name())
            .withMessage("Erreur with {param6}")
            .withAttemptedFound("attemptedValue")
            .build();

    // When
    // Then
    AssertValidator.assertThatErrors(singletonList(errOk)).containsValidMsg(VER);

    assertThatThrownBy(
        () -> AssertValidator.assertThatErrors(singletonList(errBad)).containsValidMsg(VER));
    assertThatThrownBy(
        () -> AssertValidator.assertThatErrors(singletonList(errBad2)).containsValidMsg(VER));
  }
}
