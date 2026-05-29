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
package io.github.nist4j.use_cases.helpers.data_formatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.github.nist4j.exceptions.InvalidDataFormatNist4jException;
import org.junit.jupiter.api.Test;

class TextFormatterUTest {

  @Test
  void leadZeroText_should_lead_with_zeros() {
    // Given
    // When
    // Then
    assertThat(TextFormatter.leadZeroText(1, 1)).isEqualTo("1");
    assertThat(TextFormatter.leadZeroText(1, 3)).isEqualTo("001");
    assertThat(TextFormatter.leadZeroText(123, 2)).isEqualTo("123");
  }

  @Test
  void leadZeroText_with_invalid_data_should_throw_exception() {
    // Given
    // When
    // Then
    Exception exception1 =
        assertThrows(
            InvalidDataFormatNist4jException.class, () -> TextFormatter.leadZeroText(1, -1));
    assertThat(exception1.getMessage()).isEqualTo("Invalid format 'length out of range [0,999]'");
    Exception exception2 =
        assertThrows(
            InvalidDataFormatNist4jException.class, () -> TextFormatter.leadZeroText(1000, -1));
    assertThat(exception2.getMessage()).isEqualTo("Invalid format 'length out of range [0,999]'");
    Exception exception3 =
        assertThrows(
            InvalidDataFormatNist4jException.class, () -> TextFormatter.leadZeroText(-1, 2));
    assertThat(exception3.getMessage()).isEqualTo("Invalid format 'intVal must be positive'");
  }
}
