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
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;

import br.com.fluentvalidator.predicate.PredicateBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NistFieldPredicates {

  private static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

  @SuppressWarnings("SpellCheckingInspection")
  private static final String GMT_DATE_FORMAT = "yyyyMMddHHmmssz";

  public static Predicate<String> isNumberBetween(int min, int max) {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(
            str -> {
              try {
                final int integer = Integer.parseInt(str);
                return integer >= min && integer <= max;
              } catch (final IllegalArgumentException ex) {
                return false;
              }
            });
  }

  /**
   * Does not authorize partial dates (with 00 replacing unknown data)
   */
  public static Predicate<String> isYYYYMMDDDate() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(
            isDate -> {
              try {
                final DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)
                        .withResolverStyle(ResolverStyle.SMART);
                LocalDate.parse(isDate, dateFormat);
                return true;
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static Predicate<String> isYYYYMMDDHHMMSSDateTime() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(
            isDateTime -> {
              try {
                final DateTimeFormatter dateTimeFormat =
                    DateTimeFormatter.ofPattern(GMT_DATE_FORMAT)
                        .withResolverStyle(ResolverStyle.SMART);
                LocalDateTime.parse(isDateTime, dateTimeFormat);
                return true;
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }
}
