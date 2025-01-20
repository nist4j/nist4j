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
package io.github.nist4j.use_cases.helpers.converters;

import static io.github.nist4j.use_cases.helpers.converters.NumericFieldConverter.tryParseIntOrDefault;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NumericFieldConverterUTest {

  @Test
  void tryParseIntOrDefault_should_return_int_if_value_is_integer() {
    int result = tryParseIntOrDefault("23", 0);
    assertThat(result).isEqualTo(23);
  }

  @Test
  void tryParseIntOrDefault_should_return_Default_if_value_is_not_integer() {
    int result = tryParseIntOrDefault("A23", 0);
    assertThat(result).isZero();
  }
}
