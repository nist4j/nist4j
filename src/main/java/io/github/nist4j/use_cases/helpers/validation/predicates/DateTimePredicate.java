/*
 * Copyright (C) 2019 Sopra Steria.
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

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;
import java.util.function.Predicate;

public final class DateTimePredicate {

  public static <T> Predicate<T> dateTimeBetween(
      final Function<T, String> source,
      final String dateStringMin,
      final String dateStringMax,
      final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeBetween(dateStringMin, dateStringMax, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateTimeBetween(
      final String dateStringMin, final String dateStringMax, final String pattern) {
    return PredicateBuilder.from(
        dateTimeLessThanOrEqual(dateStringMax, pattern)
            .and(dateTimeGreaterThanOrEqual(dateStringMin, pattern)));
  }

  public static <T> Predicate<T> dateTimeEqualTo(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeEqualTo(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> dateTimeEqualTo(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeEqualTo(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateTimeEqualTo(final String dateString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(dateTimeEqualTo -> not(stringEmptyOrNull()).test(dateString))
        .and(dateTimeEqualTo -> not(stringEmptyOrNull()).test(pattern))
        .and(
            dateTimeEqualTo -> {
              try {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalDateTime dateTimeTest = LocalDateTime.parse(dateTimeEqualTo, dateFormat);
                final LocalDateTime dateTime = LocalDateTime.parse(dateString, dateFormat);
                return dateTimeTest.isEqual(dateTime);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> dateTimeGreaterThan(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeGreaterThan(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> dateTimeGreaterThan(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeGreaterThan(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateTimeGreaterThan(
      final String dateString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(dateTimeGreaterThan -> not(stringEmptyOrNull()).test(dateString))
        .and(dateTimeGreaterThan -> not(stringEmptyOrNull()).test(pattern))
        .and(
            dateTimeGreaterThan -> {
              try {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalDateTime dateTimeTest =
                    LocalDateTime.parse(dateTimeGreaterThan, dateFormat);
                final LocalDateTime dateTime = LocalDateTime.parse(dateString, dateFormat);
                return dateTimeTest.isAfter(dateTime);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> dateTimeGreaterThanOrEqual(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.from(
        dateTimeGreaterThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
  }

  public static <T> Predicate<T> dateTimeGreaterThanOrEqual(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.from(
        dateTimeGreaterThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
  }

  public static Predicate<String> dateTimeGreaterThanOrEqual(
      final String dateString, final String pattern) {
    return PredicateBuilder.from(
        dateTimeGreaterThan(dateString, pattern).or(dateTimeEqualTo(dateString, pattern)));
  }

  public static <T> Predicate<T> dateTimeLessThan(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeLessThan(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> dateTimeLessThan(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateTimeLessThan(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateTimeLessThan(final String dateString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(dateTimeLessThan -> not(stringEmptyOrNull()).test(dateString))
        .and(dateTimeLessThan -> not(stringEmptyOrNull()).test(pattern))
        .and(
            dateTimeLessThan -> {
              try {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalDateTime dateTest = LocalDateTime.parse(dateTimeLessThan, dateFormat);
                final LocalDateTime date = LocalDateTime.parse(dateString, dateFormat);
                return dateTest.isBefore(date);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> dateTimeLessThanOrEqual(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.from(
        dateTimeLessThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
  }

  public static <T> Predicate<T> dateTimeLessThanOrEqual(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.from(
        dateTimeLessThan(source, target, pattern).or(dateTimeEqualTo(source, target, pattern)));
  }

  public static Predicate<String> dateTimeLessThanOrEqual(
      final String dateString, final String pattern) {
    return PredicateBuilder.from(
        dateTimeLessThan(dateString, pattern).or(dateTimeEqualTo(dateString, pattern)));
  }

  private DateTimePredicate() {
    super();
  }
}
