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
package io.github.nist4j.use_cases.helpers.validation.predicates;

import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NistFieldValuePredicatesUTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "50", // not integer
        "1", // inferior_to_min
        "100" // superior_to_max
      })
  void isNumberBetween_should_return_true_when_X(String valueToTest) {
    assertThat(isNumberBetween(1, 100).test(valueToTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "test", // not integer
        "0", // inferior_to_min
        "101" // superior_to_max
      })
  void isNumberBetween_should_return_false_when_X(String valueToTest) {
    assertThat(isNumberBetween(1, 100).test(valueToTest)).isFalse();
  }

  @Test
  void isYYYYMMDDDate_should_return_true_when_valid_date() {
    String valueToTest = "20240531";
    assertThat(isYYYYMMDDDate().test(valueToTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "20240532", // valid_number_but_incorrect_date
        "20240500", // unknown_data
        "test", // invalid_characters
        "empty"
      })
  void isYYYYMMDDDate_should_return_false_when_X(String valueToTest) {
    assertThat(isYYYYMMDDDate().test(valueToTest)).isFalse();
  }

  @Test
  void isYYYYMMDDHHMMSSDateTime_should_return_true_when_valid_date() {
    String valueToTest = "20091117124523Z";
    assertThat(isYYYYMMDDHHMMSSDateTime().test(valueToTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "20091137124523Z", // valid_number_but_incorrect_date
        "20091117124523", // valid_date_and_missingZ
        "test", // invalid_characters
        "empty"
      })
  void isYYYYMMDDHHMMSSDateTime_should_return_false_when_X(String valueToTest) {
    assertThat(isYYYYMMDDHHMMSSDateTime().test(valueToTest)).isFalse();
  }
}
