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

import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.is;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.Predicate;

public final class LocalDatePredicate {

  public static <T> Predicate<T> localDateAfter(
      final Function<T, LocalDate> source, final Function<T, LocalDate> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateAfter(source, target.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateAfter(
      final Function<T, LocalDate> source, final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateAfter(target).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateAfter(final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(obj -> obj.isAfter(target));
  }

  public static <T> Predicate<T> localDateAfterOrEqual(
      final Function<T, LocalDate> source, final Function<T, LocalDate> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateAfterOrEqual(source, target.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateAfterOrEqual(
      final Function<T, LocalDate> source, final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateAfterOrEqual(target).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateAfterOrEqual(final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(is(localDateAfter(target)).or(localDateEqualTo(target)));
  }

  public static <T extends LocalDate> Predicate<T> localDateAfterOrEqualToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDate -> localDate.isAfter(LocalDate.now()) || localDate.isEqual(LocalDate.now()));
  }

  public static <T> Predicate<T> localDateAfterOrEqualToday(final Function<T, LocalDate> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> localDateAfterOrEqualToday().test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateAfterToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDate -> localDate.isAfter(LocalDate.now()));
  }

  public static <T> Predicate<T> localDateAfterToday(final Function<T, LocalDate> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> localDateAfterToday().test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateBefore(
      final Function<T, LocalDate> source, final Function<T, LocalDate> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateBefore(source, target.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateBefore(
      final Function<T, LocalDate> source, final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateBefore(target).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateBefore(final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(obj -> obj.isBefore(target));
  }

  public static <T> Predicate<T> localDateBeforeOrEqual(
      final Function<T, LocalDate> source, final Function<T, LocalDate> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateBeforeOrEqual(source, target.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateBeforeOrEqual(
      final Function<T, LocalDate> source, final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateBeforeOrEqual(target).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateBeforeOrEqual(final LocalDate target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(is(localDateBefore(target)).or(localDateEqualTo(target)));
  }

  public static <T extends LocalDate> Predicate<T> localDateBeforeOrEqualToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(
            localDate -> localDate.isBefore(LocalDate.now()) || localDate.isEqual(LocalDate.now()));
  }

  public static <T> Predicate<T> localDateBeforeOrEqualToday(final Function<T, LocalDate> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> localDateBeforeOrEqualToday().test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateBeforeToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDate -> localDate.isBefore(LocalDate.now()));
  }

  public static <T> Predicate<T> localDateBeforeToday(final Function<T, LocalDate> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> localDateBeforeToday().test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateBetween(
      final Function<T, LocalDate> source,
      final Function<T, LocalDate> min,
      final Function<T, LocalDate> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> localDateBetween(source, min.apply(obj), max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateBetween(
      final Function<T, LocalDate> source, final Function<T, LocalDate> min, final LocalDate max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(obj -> localDateBetween(source, min.apply(obj), max).test(obj));
  }

  public static <T> Predicate<T> localDateBetween(
      final Function<T, LocalDate> source, final LocalDate min, final Function<T, LocalDate> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(max))
        .and(obj -> localDateBetween(source, min, max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateBetween(
      final Function<T, LocalDate> source, final LocalDate min, final LocalDate max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateBetween(min, max).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateBetween(
      final LocalDate min, final LocalDate max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateAfter(min).and(localDateBefore(max)));
  }

  public static <T> Predicate<T> localDateBetweenOrEqual(
      final Function<T, LocalDate> source,
      final Function<T, LocalDate> min,
      final Function<T, LocalDate> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> localDateBetweenOrEqual(source, min.apply(obj), max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateBetweenOrEqual(
      final Function<T, LocalDate> source, final Function<T, LocalDate> min, final LocalDate max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(obj -> localDateBetweenOrEqual(source, min.apply(obj), max).test(obj));
  }

  public static <T> Predicate<T> localDateBetweenOrEqual(
      final Function<T, LocalDate> source, final LocalDate min, final Function<T, LocalDate> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(max))
        .and(obj -> localDateBetweenOrEqual(source, min, max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateBetweenOrEqual(
      final Function<T, LocalDate> source, final LocalDate min, final LocalDate max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateBetweenOrEqual(min, max).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateBetweenOrEqual(
      final LocalDate min, final LocalDate max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateAfterOrEqual(min).and(localDateBeforeOrEqual(max)));
  }

  public static <T> Predicate<T> localDateEqualTo(
      final Function<T, LocalDate> source, final LocalDate localDate) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateEqualTo(localDate).test(source.apply(obj)));
  }

  public static <T extends LocalDate> Predicate<T> localDateEqualTo(final LocalDate localDate) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> isNotNull(localDate) && localDate.isEqual(obj));
  }

  public static <T extends LocalDate> Predicate<T> localDateIsToday() {
    return PredicateBuilder.<T>from(notNullValue()).and(obj -> obj.isEqual(LocalDate.now()));
  }

  public static <T> Predicate<T> localDateIsToday(final Function<T, LocalDate> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateIsToday().test(source.apply(obj)));
  }

  private LocalDatePredicate() {
    super();
  }
}
