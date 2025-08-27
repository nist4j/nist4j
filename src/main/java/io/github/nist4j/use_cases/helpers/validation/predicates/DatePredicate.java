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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Function;
import java.util.function.Predicate;

public final class DatePredicate {

  public static <T> Predicate<T> dateBetween(
      final Function<T, String> source,
      final String dateStringMin,
      final String dateStringMax,
      final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateBetween(dateStringMin, dateStringMax, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateBetween(
      final String dateStringMin, final String dateStringMax, final String pattern) {
    return PredicateBuilder.from(
        dateLessThanOrEqual(dateStringMax, pattern)
            .and(dateGreaterThanOrEqual(dateStringMin, pattern)));
  }

  public static <T> Predicate<T> dateEqualTo(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateEqualTo(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> dateEqualTo(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateEqualTo(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateEqualTo(final String dateString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(dateEqualTo -> not(stringEmptyOrNull()).test(dateString))
        .and(dateEqualTo -> not(stringEmptyOrNull()).test(pattern))
        .and(
            dateEqualTo -> {
              try {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalDate dateTest = LocalDate.parse(dateEqualTo, dateFormat);
                final LocalDate date = LocalDate.parse(dateString, dateFormat);
                return dateTest.isEqual(date);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> dateGreaterThan(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateGreaterThan(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> dateGreaterThan(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateGreaterThan(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateGreaterThan(final String dateString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(dateGreaterThan -> not(stringEmptyOrNull()).test(dateString))
        .and(dateGreaterThan -> not(stringEmptyOrNull()).test(pattern))
        .and(
            dateGreaterThan -> {
              try {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalDate dateTest = LocalDate.parse(dateGreaterThan, dateFormat);
                final LocalDate date = LocalDate.parse(dateString, dateFormat);
                return dateTest.isAfter(date);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> dateGreaterThanOrEqual(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.from(
        dateGreaterThan(source, target, pattern).or(dateEqualTo(source, target, pattern)));
  }

  public static <T> Predicate<T> dateGreaterThanOrEqual(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.from(
        dateGreaterThan(source, target, pattern).or(dateEqualTo(source, target, pattern)));
  }

  public static Predicate<String> dateGreaterThanOrEqual(
      final String dateString, final String pattern) {
    return PredicateBuilder.from(
        dateGreaterThan(dateString, pattern).or(dateEqualTo(dateString, pattern)));
  }

  public static <T> Predicate<T> dateLessThan(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateLessThan(target.apply(obj), pattern).test(source.apply(obj)));
  }

  public static <T> Predicate<T> dateLessThan(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> dateLessThan(target, pattern).test(source.apply(obj)));
  }

  public static Predicate<String> dateLessThan(final String dateString, final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(dateLessThan -> not(stringEmptyOrNull()).test(dateString))
        .and(dateLessThan -> not(stringEmptyOrNull()).test(pattern))
        .and(
            dateLessThan -> {
              try {
                final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
                final LocalDate dateTest = LocalDate.parse(dateLessThan, dateFormat);
                final LocalDate date = LocalDate.parse(dateString, dateFormat);
                return dateTest.isBefore(date);
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> dateLessThanOrEqual(
      final Function<T, String> source, final Function<T, String> target, final String pattern) {
    return PredicateBuilder.from(
        dateLessThan(source, target, pattern).or(dateEqualTo(source, target, pattern)));
  }

  public static <T> Predicate<T> dateLessThanOrEqual(
      final Function<T, String> source, final String target, final String pattern) {
    return PredicateBuilder.from(
        dateLessThan(source, target, pattern).or(dateEqualTo(source, target, pattern)));
  }

  public static Predicate<String> dateLessThanOrEqual(
      final String dateString, final String pattern) {
    return PredicateBuilder.from(
        dateLessThan(dateString, pattern).or(dateEqualTo(dateString, pattern)));
  }

  private DatePredicate() {
    super();
  }
}
