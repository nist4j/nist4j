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

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.mandatory;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static java.util.Objects.isNull;

import io.github.nist4j.enums.CharacterTypeEnum;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import lombok.NonNull;

public class NistCharacterPredicate {

  public static Predicate<String> isCharTypeWithMinMaxLength(
      @NonNull CharacterTypeEnum characterType,
      final int expectedMinLength,
      final int expectedMaxLength) {

    if (!isNull(characterType.getAllowedCharacters())) {
      return stringSizeBetween(expectedMinLength, expectedMaxLength)
          .and(areAllowCharacters(characterType.getAllowedCharacters()));
    }
    if (!isNull(characterType.getRegexpValidation())) {
      return stringSizeBetween(expectedMinLength, expectedMaxLength)
          .and(stringMatches(characterType.getRegexpValidation()));
    }
    return stringSizeBetween(expectedMinLength, expectedMaxLength);
  }

  public static Predicate<String> isCharTypeWithMinLength(
      @NonNull CharacterTypeEnum characterType, final int min) {

    if (!isNull(characterType.getAllowedCharacters())) {
      return stringSizeGreaterThanOrEqual(min)
          .and(areAllowCharacters(characterType.getAllowedCharacters()));
    }
    if (!isNull(characterType.getRegexpValidation())) {
      return stringSizeGreaterThanOrEqual(min)
          .and(stringMatches(characterType.getRegexpValidation()));
    }
    return stringSizeGreaterThanOrEqual(min);
  }

  private static Predicate<String> areAllowCharacters(final Set<Character> allowedCharacters) {
    return str -> {
      for (char c : str.toCharArray()) {
        if (!allowedCharacters.contains(c)) return false;
      }
      return true;
    };
  }

  public static Predicate<List<String>> areCharTypeWithMinMaxLength(
      @NonNull CharacterTypeEnum characterType,
      final int expectedMinLength,
      final int expectedMaxLength) {

    return fields -> {
      for (String field : fields) {
        if (!isCharTypeWithMinMaxLength(characterType, expectedMinLength, expectedMaxLength)
            .test(field)) {
          return false;
        }
      }
      return true;
    };
  }

  public static Predicate<List<String>> areCharTypeWithMinLength(
      @NonNull CharacterTypeEnum characterType, final int expectedMinLength) {

    return fields -> {
      for (String field : fields) {
        if (!isCharTypeWithMinLength(characterType, expectedMinLength).test(field)) {
          return false;
        }
      }
      return true;
    };
  }

  public static Predicate<String> isHexaCodeWithLength(int length) {
    return mandatory(stringSize(length)).and(stringMatches("^[0-9A-Fa-f]+$"));
  }
}
