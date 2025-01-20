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
package io.github.nist4j.use_cases.helpers.validation;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.validation.RegexpCharacterTypeEnum;
import io.github.nist4j.use_cases.helpers.builders.validation.NistValidationRegexBuilder;
import org.junit.jupiter.api.Test;

class NistValidationRegexUTest {

  @Test
  void generateRegex_with_nb_occurrences_should_generate_regex_for_number() {
    // Given
    // When
    String regexpNum =
        new NistValidationRegexBuilder()
            .min(1)
            .max(2)
            .allowsChar(RegexpCharacterTypeEnum.NUMERIC)
            .build();

    // Then
    assertThat("1".matches(regexpNum)).isTrue();
    assertThat("11".matches(regexpNum)).isTrue();
    assertThat("123".matches(regexpNum)).isFalse(); // too long
    assertThat("".matches(regexpNum)).isFalse(); // too short
    assertThat("A".matches(regexpNum)).isFalse(); // not number
    assertThat("a".matches(regexpNum)).isFalse(); // not number
  }

  @Test
  void generateRegex_with_nb_occurrences_should_generate_regex_for_alpha() {
    // Given
    // When
    String regexpNum =
        new NistValidationRegexBuilder()
            .min(1)
            .max(2)
            .allowsChar(RegexpCharacterTypeEnum.ALPHABETIC)
            .build();

    // Then
    assertThat("A".matches(regexpNum)).isTrue();
    assertThat("a".matches(regexpNum)).isTrue();
    assertThat("AB".matches(regexpNum)).isTrue();
    assertThat("ab".matches(regexpNum)).isTrue();
    assertThat("aB".matches(regexpNum)).isTrue();

    assertThat("ABC".matches(regexpNum)).isFalse(); // too long
    assertThat("".matches(regexpNum)).isFalse(); // too short
    assertThat("1".matches(regexpNum)).isFalse(); // not number
    assertThat("!".matches(regexpNum)).isFalse(); // not special
  }

  @Test
  void generateRegex_with_nb_occurrences_should_generate_regex_for_special() {
    // Given
    // When
    String regexpNum =
        new NistValidationRegexBuilder()
            .min(0)
            .max(2)
            .allowsChar(RegexpCharacterTypeEnum.SPECIAL)
            .build();

    // Then
    assertThat("#".matches(regexpNum)).isTrue();
    assertThat("#!".matches(regexpNum)).isTrue();
    assertThat("###".matches(regexpNum)).isFalse(); // too long
    assertThat("".matches(regexpNum)).isTrue(); // too short

    assertThat("1".matches(regexpNum)).isFalse(); // not number
    assertThat("A".matches(regexpNum)).isFalse(); // not alpha
    assertThat("a".matches(regexpNum)).isFalse(); // not alpha
  }

  @Test
  void generateRegex_with_wildcard_should_generate_regex_for_numeric() {
    // Given
    // When
    String regexpNum =
        new NistValidationRegexBuilder().allowsChar(RegexpCharacterTypeEnum.NUMERIC).build();

    // Then
    assertThat("1".matches(regexpNum)).isTrue();
    assertThat("11".matches(regexpNum)).isTrue();
    assertThat("123456789".matches(regexpNum)).isTrue();

    assertThat("".matches(regexpNum)).isFalse(); // empty
    assertThat("A".matches(regexpNum)).isFalse(); // not number
    assertThat("a".matches(regexpNum)).isFalse(); // not number
    assertThat("#".matches(regexpNum)).isFalse(); // not number
  }

  @Test
  void generateRegex_with_wildcard_should_generate_regex_for_special() {
    // Given
    // When
    String regexpNum =
        new NistValidationRegexBuilder().allowsChar(RegexpCharacterTypeEnum.SPECIAL).build();

    // Then
    assertThat("#".matches(regexpNum)).isTrue();
    assertThat("#!".matches(regexpNum)).isTrue();
    assertThat("#!&%$@_+<=>:;`~{|} '*+-.[]()^".matches(regexpNum)).isTrue();

    assertThat("".matches(regexpNum)).isFalse(); // too short
    assertThat("1".matches(regexpNum)).isFalse(); // not number
    assertThat("A".matches(regexpNum)).isFalse(); // not alpha
  }

  @Test
  void REGEXP_ANS_ANY_LENGTH_should_generate_a_valid_regexp() {
    // Given
    // When
    String regexpNum = NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH;

    // Then
    assertThat("A".matches(regexpNum)).isTrue(); // only alpha
    assertThat("1".matches(regexpNum)).isTrue(); // only number
    assertThat("#".matches(regexpNum)).isTrue(); // only special
    assertThat("A1#".matches(regexpNum)).isTrue(); // all kinds
    assertThat("A1#B2!".matches(regexpNum)).isTrue(); // all kinds several times
    assertThat("Aa1#Bb2!CcDE345&%$@_+<=>:;`~{|} '*+-.\"".matches(regexpNum))
        .isTrue(); // all kinds several times

    assertThat("".matches(regexpNum)).isFalse();
    assertThat("ç".matches(regexpNum)).isFalse();
  }
}
