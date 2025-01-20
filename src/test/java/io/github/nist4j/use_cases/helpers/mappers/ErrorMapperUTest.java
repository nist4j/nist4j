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

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_BPX_RT14;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_VER_RT1;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.fluentvalidator.context.Error;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ErrorMapperUTest {

  @Test
  void toErrorOnRecord_should_map_correctly() {
    // Given
    Error expectedError =
        Error.create(
            "VER|2|RT1|", STD_ERR_VER_RT1.getMessage(), STD_ERR_VER_RT1.getCode(), "value");
    // When
    Error error = ErrorMapper.toErrorOnRecord(STD_ERR_VER_RT1, "value");
    // Then
    assertThat(error.toString()).isEqualTo(expectedError.toString());
  }

  @Test
  void toErrorOnField_should_map_correctly() {
    // Given
    Error expectedError =
        Error.create(
            "BPX|12|RT14|2", STD_ERR_BPX_RT14.getMessage(), STD_ERR_BPX_RT14.getCode(), "value");
    // When
    Error error = ErrorMapper.toErrorOnField(STD_ERR_BPX_RT14, "value", Optional.of("2"));
    // Then
    assertThat(error.toString()).isEqualTo(expectedError.toString());
  }

  @Test
  void toErrorOnField_2_should_map_correctly() {
    // Given
    Error expectedError =
        Error.create("VER", STD_ERR_VER_RT1.getMessage(), STD_ERR_VER_RT1.getCode(), "value");
    // When
    Error error = ErrorMapper.toErrorOnField(STD_ERR_VER_RT1, "value");
    // Then
    assertThat(error.toString()).isEqualTo(expectedError.toString());
  }

  @Test
  void getter_should_map_correctly() {
    // Given
    Error errorOnRecord = ErrorMapper.toErrorOnRecord(STD_ERR_VER_RT1, "value");
    Error errorOnField = ErrorMapper.toErrorOnField(STD_ERR_BPX_RT14, "value", Optional.of("2"));
    // When
    // Then
    assertThat(ErrorMapper.getNistFieldFromError(errorOnRecord)).isEqualTo("2 (VER)");
    assertThat(ErrorMapper.getNistRecordFromError(errorOnRecord)).isEqualTo("RT1");
    assertThat(ErrorMapper.getNistFieldFromError(errorOnField)).isEqualTo("12 (BPX)");
    assertThat(ErrorMapper.getNistRecordFromError(errorOnField)).isEqualTo("RT14 (IDC:2)");
  }
}
