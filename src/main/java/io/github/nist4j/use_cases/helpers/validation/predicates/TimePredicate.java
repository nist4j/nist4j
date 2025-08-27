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

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.mandatory;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.function.Function;
import java.util.function.Predicate;

public final class TimePredicate {
  private static final String LOCAL_DATE_FORMAT = "uuuuMMdd";
  private static final String GMT_DATE_FORMAT = "uuuuMMddHHmmssz";

  public static <T> Predicate<T> timeBetween(
      final Function<T, String> source,
      final String timeStringMin,
      final String timeStringMax,
      final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeBetween(timeStringMin, timeStringMax, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> timeBetween(
      final String timeStringMin, final String timeStringMax, final String pattern) {
    return PredicateBuilder.from(
        timeLessThanOrEqual(timeStringMax, pattern)
            .and(timeGreaterThanOrEqual(timeStringMin, pattern)));
  }

  public static <T> Predicate<T> timeEqualTo(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeEqualTo(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> timeEqualTo(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeEqualTo(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> timeEqualTo(final String timeString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(timeEqualTo -> not(stringEmptyOrNull()).test(timeString))
        .and(timeEqualTo -> not(stringEmptyOrNull()).test(pattern))
        .and(
            timeEqualTo -> {
              try {
                final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalTime timeTest = LocalTime.parse(timeEqualTo, timeFormat);
                final LocalTime time = LocalTime.parse(timeString, timeFormat);
                return timeTest.equals(time);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> timeGreaterThan(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeGreaterThan(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> timeGreaterThan(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeGreaterThan(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> timeGreaterThan(final String timeString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(timeGreaterThan -> not(stringEmptyOrNull()).test(timeString))
        .and(timeGreaterThan -> not(stringEmptyOrNull()).test(pattern))
        .and(
            timeGreaterThan -> {
              try {
                final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalTime timeTest = LocalTime.parse(timeGreaterThan, timeFormat);
                final LocalTime time = LocalTime.parse(timeString, timeFormat);
                return timeTest.isAfter(time);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> timeGreaterThanOrEqual(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.from(
        timeGreaterThan(source, target, pattern).or(timeEqualTo(source, target, pattern)));
  }

  public static <T> Predicate<T> timeGreaterThanOrEqual(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.from(
        timeGreaterThan(source, target, pattern).or(timeEqualTo(source, target, pattern)));
  }

  public static Predicate<String> timeGreaterThanOrEqual(
      final String timeString, final String pattern) {
    return PredicateBuilder.from(
        timeGreaterThan(timeString, pattern).or(timeEqualTo(timeString, pattern)));
  }

  public static <T> Predicate<T> timeLessThan(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeLessThan(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> timeLessThan(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> timeLessThan(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> timeLessThan(final String timeString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(timeLessThan -> not(stringEmptyOrNull()).test(timeString))
        .and(timeLessThan -> not(stringEmptyOrNull()).test(pattern))
        .and(
            timeLessThan -> {
              try {
                final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalTime timeTest = LocalTime.parse(timeLessThan, timeFormat);
                final LocalTime time = LocalTime.parse(timeString, timeFormat);
                return timeTest.isBefore(time);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> timeLessThanOrEqual(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.from(
        timeLessThan(source, target, pattern).or(timeEqualTo(source, target, pattern)));
  }

  public static <T> Predicate<T> timeLessThanOrEqual(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.from(
        timeLessThan(source, target, pattern).or(timeEqualTo(source, target, pattern)));
  }

  public static Predicate<String> timeLessThanOrEqual(
      final String timeString, final String pattern) {
    return PredicateBuilder.from(
        timeLessThan(timeString, pattern).or(timeEqualTo(timeString, pattern)));
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

  private TimePredicate() {
    super();
  }
}
