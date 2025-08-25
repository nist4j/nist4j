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

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.*;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NistFieldPredicates {

  private static final String LOCAL_DATE_FORMAT = "uuuuMMdd";

  private static final String GMT_DATE_FORMAT = "uuuuMMddHHmmssz";

  public static Predicate<String> isRealNumberBetween(double min, double max) {
    return mandatory(s -> toReal(s).map(val -> val >= min && val <= max).orElse(false));
  }

  private static Optional<Double> toReal(String str) {
    try {
      final Double realVal = Double.parseDouble(str);
      return Optional.of(realVal);
    } catch (final IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public static Predicate<String> isNumberBetween(int min, int max) {
    return mandatory(s -> toInt(s).map(val -> val >= min && val <= max).orElse(false));
  }

  public static Optional<Integer> toInt(String str) {
    try {
      final Integer intVal = Integer.parseInt(str);
      return Optional.of(intVal);
    } catch (final IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public static Predicate<List<String>> areNumbersBetween(int min, int max) {
    return items -> {
      if (isEmpty(items)) {
        return false;
      }
      for (String item : items) {
        boolean isValid = toInt(item).map(intVal -> intVal >= min && intVal <= max).orElse(false);
        if (!isValid) {
          return false;
        }
      }
      return true;
    };
  }

  public static Predicate<List<String>> areInCollection(List<String> allowsValues) {
    return items -> {
      if (isEmpty(items)) {
        return false;
      }
      for (String item : items) {
        boolean isValid = allowsValues.contains(item);
        if (!isValid) {
          return false;
        }
      }
      return true;
    };
  }

  public static Predicate<String> stringStartingWith(String txt) {
    return mandatory(
        str -> {
          try {
            return str.startsWith(txt);
          } catch (final IllegalArgumentException ex) {
            return false;
          }
        });
  }

  public static Predicate<String> stringNotStartingWith(String txt) {
    return optional(
        str -> {
          try {
            return !str.startsWith(txt);
          } catch (final IllegalArgumentException ex) {
            return false;
          }
        });
  }

  /** Does not authorize partial dates (with 00 replacing unknown data) */
  public static Predicate<String> isYYYYMMDDDate() {
    return mandatory(
        date -> {
          try {
            final DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDate.parse(date, dateFormat);
            return true;
          } catch (final IllegalArgumentException | DateTimeParseException ex) {
            return false;
          }
        });
  }

  public static Predicate<String> isYYYYMMDDHHMMSSDateTime() {
    return mandatory(
        dateTime -> {
          try {
            final DateTimeFormatter dateTimeFormat =
                DateTimeFormatter.ofPattern(GMT_DATE_FORMAT)
                    .withResolverStyle(ResolverStyle.STRICT);
            LocalDateTime.parse(dateTime, dateTimeFormat);
            return true;
          } catch (final IllegalArgumentException | DateTimeParseException ex) {
            return false;
          }
        });
  }

  public static Predicate<String> isHexaCodeWithLength(int length) {
    return mandatory(stringSize(length)).and(stringMatches("^[0-9A-Fa-f]+$"));
  }

  public static Predicate<String> optional(@NonNull Predicate<String> validator) {
    return stringEmptyOrNull().or(validator);
  }

  public static Predicate<String> mandatory(@NonNull Predicate<String> validator) {
    return not(stringEmptyOrNull()).and(validator);
  }
}
