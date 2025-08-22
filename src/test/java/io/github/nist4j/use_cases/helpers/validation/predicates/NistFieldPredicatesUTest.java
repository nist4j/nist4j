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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NistFieldPredicatesUTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "50", // not integer
        "1", // inferior_to_min
        "100" // superior_to_max
      })
  void isNumberBetween_should_return_true_when_X(String valueToTest) {
    assertThat(NistFieldPredicates.isNumberBetween(1, 100).test(valueToTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "test", // not integer
        "0", // inferior_to_min
        "101" // superior_to_max
      })
  void isNumberBetween_should_return_false_when_X(String valueToTest) {
    assertThat(NistFieldPredicates.isNumberBetween(1, 100).test(valueToTest)).isFalse();
  }

  @Test
  void isYYYYMMDDDate_should_return_true_when_valid_date() {
    String valueToTest = "20240531";
    assertThat(NistFieldPredicates.isYYYYMMDDDate().test(valueToTest)).isTrue();
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
    assertThat(NistFieldPredicates.isYYYYMMDDDate().test(valueToTest)).isFalse();
  }

  @Test
  void isYYYYMMDDHHMMSSDateTime_should_return_true_when_valid_date() {
    String valueToTest = "20091117124523Z";
    assertThat(NistFieldPredicates.isYYYYMMDDHHMMSSDateTime().test(valueToTest)).isTrue();
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
    assertThat(NistFieldPredicates.isYYYYMMDDHHMMSSDateTime().test(valueToTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "0", "10", "11", "12", "30", "-5", "-10", "5,3,-2",
      })
  void areNumbersBetween_should_return_true(String valueToTest) {
    List<String> values = asList(valueToTest.split(","));
    assertThat(NistFieldPredicates.areNumbersBetween(-10, 30).test(values)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "a", // invalid_characters
        "-11", // valid_number_but_out_of_range
        "31", // valid_number_but_out_of_range
        "5,3,-2,-100", // valid_number_but_out_of_range
      })
  void areNumbersBetween_should_return_false(String valueToTest) {
    List<String> values = asList(valueToTest.split(","));
    assertThat(NistFieldPredicates.areNumbersBetween(-10, 30).test(values)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "A", "B", "C", "B,C",
      })
  void areInCollection_should_return_true(String valueToTest) {
    List<String> values = asList(valueToTest.split(","));
    List<String> collection = asList("A", "B", "C");
    assertThat(NistFieldPredicates.areInCollection(collection).test(values)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "a", // out of collection
        "1", // out of collection
        "A,B,C,D", // valid_number_but_out_of_range
      })
  void areInCollection_should_return_false(String valueToTest) {
    List<String> collection = asList("A", "B", "C");
    List<String> values = asList(valueToTest.split(","));
    assertThat(NistFieldPredicates.areInCollection(collection).test(values)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "STARTWITH_AAZERTYU",
        "STARTWITH_1",
        "STARTWITH_?",
      })
  void stringStartingWith_should_return_true(String valueToTest) {
    assertThat(NistFieldPredicates.stringStartingWith("STARTWITH_").test(valueToTest)).isTrue();
    assertThat(NistFieldPredicates.stringNotStartingWith("STARTWITH_").test(valueToTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "NOT_STARTWITH_A", // not start with
        "1", // not present
        "", // empty
      })
  void stringStartingWith_should_return_false(String valueToTest) {
    assertThat(NistFieldPredicates.stringStartingWith("STARTWITH_").test(valueToTest)).isFalse();
    assertThat(NistFieldPredicates.stringNotStartingWith("STARTWITH_").test(valueToTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "1234", "FFFF", "1FFE",
      })
  void isHexaCodeWithLength_should_return_true(String valueToTest) {
    assertThat(NistFieldPredicates.isHexaCodeWithLength(4).test(valueToTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "123", // too short
        "1", // too long
        "", // empty
        "1F2G", // not hexa
      })
  void isHexaCodeWithLength_should_return_false(String valueToTest) {
    assertThat(NistFieldPredicates.isHexaCodeWithLength(4).test(valueToTest)).isFalse();
  }
}
