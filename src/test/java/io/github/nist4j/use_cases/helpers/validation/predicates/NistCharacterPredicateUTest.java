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

import static io.github.nist4j.enums.CharacterTypeEnum.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NistCharacterPredicateUTest {

  @ParameterizedTest
  @ValueSource(strings = {"A", "a", "azeRTYUIO"})
  void isCharTypeWithMinMaxLength_with_type_A_should_return_true(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(A, 1, 10).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(AN, 1, 10).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(ANS, 1, 10).test(valueTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "_", // bad format
        "1", // bad format
        "Ⴤ", // bad format
        "", // too short
        "1234567890A", // too long
        "With space" // space is a S for all std except for std2011
      })
  void isCharTypeWithMinMaxLength_with_type_A_should_return_false(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(A, 1, 10).test(valueTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(strings = {"=", "{!", "*", "\\", " "})
  void isCharTypeWithMinMaxLength_with_type_S_should_return_true(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(S, 1, 3).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(NS, 1, 3).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(ANS, 1, 3).test(valueTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "a", // bad format
        "A", // bad format
        "123", // bad format
        "Ⴤ", // bad format
        "123456", // too long
        "", // too short
      })
  void isCharTypeWithMinMaxLength_with_type_S_should_return_false(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(S, 1, 5).test(valueTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "9", "12", "999"})
  void isCharTypeWithMinMaxLength_with_type_N_should_return_true(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(N, 1, 3).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(AN, 1, 3).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(NS, 1, 3).test(valueTest)).isTrue();
    assertThat(isCharTypeWithMinMaxLength(ANS, 1, 3).test(valueTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "a", // bad format
        "!", // bad format
        "A", // bad format
        "Ⴤ", // bad format
        "白", // bad format
        "1234", // too long
        "", // too short
      })
  void isCharTypeWithMinMaxLength_with_type_N_should_return_false(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(N, 1, 3).test(valueTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(strings = {"白", "a", "azeRTYUIO", "With space", "234"})
  void isCharTypeWithMinMaxLength_with_type_U_should_return_true(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(U, 1, 10).test(valueTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "", // too short
        "白白白白", // too long
        "\u001E", // exclure cause reserved character
      })
  void isCharTypeWithMinMaxLength_with_type_U_should_return_false(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(U, 1, 3).test(valueTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(strings = {"12", "FE", "AF", "1F3E"})
  void isCharTypeWithMinMaxLength_with_type_H_should_return_true(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(H, 1, 5).test(valueTest)).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "", // too short
        "1231315FF", // too long
        "f", // bad format
        "G", // bad format
        "à", // bad format
        "!", // bad format
        "白", // bad format
        " ", // bad format
      })
  void isCharTypeWithMinMaxLength_with_type_H_should_return_false(String valueTest) {
    assertThat(isCharTypeWithMinMaxLength(H, 1, 5).test(valueTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "1234", "FFFF", "1FFE",
      })
  void isHexaCodeWithLength_should_return_true(String valueToTest) {
    assertThat(NistCharacterPredicate.isHexaCodeWithLength(4).test(valueToTest)).isTrue();
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
    assertThat(NistCharacterPredicate.isHexaCodeWithLength(4).test(valueToTest)).isFalse();
  }
}
