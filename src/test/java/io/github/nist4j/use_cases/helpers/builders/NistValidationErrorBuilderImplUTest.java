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
package io.github.nist4j.use_cases.helpers.builders;

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_MANDATORY_NUMERIC_BETWEEN;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import org.junit.jupiter.api.Test;

class NistValidationErrorBuilderImplUTest {
  @Test
  void newNistValidationError_should_create_nistValidationError() {
    // Given
    INistValidationErrorEnum err1 =
        NistValidationErrorBuilderImpl.newNistValidationError(
            STD_ERR_MANDATORY_NUMERIC_BETWEEN,
            RecordTypeEnum.RT1,
            RT1FieldsEnum.LEN,
            "ABC",
            asList(1, 255));

    // When
    // Then
    assertThat(err1.getMessage()).doesNotContainPattern("\\{[^}]*}");
    assertThat(err1.getMessage()).doesNotContain("[Ljava.lang.Object;@");
  }
}
