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

import static io.github.nist4j.use_cases.helpers.validation.predicates.LocalDatePredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.is;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Predicate;

public final class LocalDateTimePredicate {

  public static <T extends LocalDateTime> Predicate<T> localDateTimeAfterToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateAfterToday().test(localDateTime.toLocalDate()));
  }

  public static <T> Predicate<T> localDateTimeAfterToday(final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeAfterToday().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeAfterOrEqualToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateAfterOrEqualToday().test(localDateTime.toLocalDate()));
  }

  public static <T> Predicate<T> localDateTimeAfterOrEqualToday(
      final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeAfterOrEqualToday().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBeforeToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateBeforeToday().test(localDateTime.toLocalDate()));
  }

  public static <T> Predicate<T> localDateTimeBeforeToday(final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBeforeToday().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBeforeOrEqualToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateBeforeOrEqualToday().test(localDateTime.toLocalDate()));
  }

  public static <T> Predicate<T> localDateTimeBeforeOrEqualToday(
      final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBeforeOrEqualToday().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeIsToday() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateIsToday().test(localDateTime.toLocalDate()));
  }

  public static <T> Predicate<T> localDateTimeIsToday(final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeIsToday().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeAfterNow() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateTimeAfter(LocalDateTime.now()).test(localDateTime));
  }

  public static <T> Predicate<T> localDateTimeAfterNow(final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeAfterNow().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBeforeNow() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTime -> localDateTimeBefore(LocalDateTime.now()).test(localDateTime));
  }

  public static <T> Predicate<T> localDateTimeBeforeNow(final Function<T, LocalDateTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBeforeNow().test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeEqualTo(
      final LocalDateTime localDateTime) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(localDateTime))
        .and(obj -> localDateTime.isEqual(obj));
  }

  public static <T> Predicate<T> localDateTimeEqualTo(
      final Function<T, LocalDateTime> source, final LocalDateTime localDateTime) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeEqualTo(localDateTime).test(source.apply(obj)));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeAfter(
      final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(obj -> obj.isAfter(target));
  }

  public static <T> Predicate<T> localDateTimeAfter(
      final Function<T, LocalDateTime> source, final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeAfter(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateTimeAfter(
      final Function<T, LocalDateTime> source, final Function<T, LocalDateTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateTimeAfter(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeAfterOrEqual(
      final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(is(localDateTimeAfter(target)).or(localDateTimeEqualTo(target)));
  }

  public static <T> Predicate<T> localDateTimeAfterOrEqual(
      final Function<T, LocalDateTime> source, final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeAfterOrEqual(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateTimeAfterOrEqual(
      final Function<T, LocalDateTime> source, final Function<T, LocalDateTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateTimeAfterOrEqual(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBefore(
      final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(obj -> obj.isBefore(target));
  }

  public static <T> Predicate<T> localDateTimeBefore(
      final Function<T, LocalDateTime> source, final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBefore(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateTimeBefore(
      final Function<T, LocalDateTime> source, final Function<T, LocalDateTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateTimeBefore(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBeforeOrEqual(
      final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(is(localDateTimeBefore(target)).or(localDateTimeEqualTo(target)));
  }

  public static <T> Predicate<T> localDateTimeBeforeOrEqual(
      final Function<T, LocalDateTime> source, final LocalDateTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBeforeOrEqual(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateTimeBeforeOrEqual(
      final Function<T, LocalDateTime> source, final Function<T, LocalDateTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localDateTimeBeforeOrEqual(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBetween(
      final LocalDateTime min, final LocalDateTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTimeAfter(min).and(localDateTimeBefore(max)));
  }

  public static <T> Predicate<T> localDateTimeBetween(
      final Function<T, LocalDateTime> source, final LocalDateTime min, final LocalDateTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBetween(min, max).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateTimeBetween(
      final Function<T, LocalDateTime> source,
      final Function<T, LocalDateTime> min,
      final LocalDateTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(obj -> localDateTimeBetween(source, min.apply(obj), max).test(obj));
  }

  public static <T> Predicate<T> localDateTimeBetween(
      final Function<T, LocalDateTime> source,
      final LocalDateTime min,
      final Function<T, LocalDateTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(max))
        .and(obj -> localDateTimeBetween(source, min, max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateTimeBetween(
      final Function<T, LocalDateTime> source,
      final Function<T, LocalDateTime> min,
      final Function<T, LocalDateTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> localDateTimeBetween(source, min.apply(obj), max.apply(obj)).test(obj));
  }

  public static <T extends LocalDateTime> Predicate<T> localDateTimeBetweenOrEqual(
      final LocalDateTime min, final LocalDateTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localDateTimeAfterOrEqual(min).and(localDateTimeBeforeOrEqual(max)));
  }

  public static <T> Predicate<T> localDateTimeBetweenOrEqual(
      final Function<T, LocalDateTime> source, final LocalDateTime min, final LocalDateTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localDateTimeBetweenOrEqual(min, max).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localDateTimeBetweenOrEqual(
      final Function<T, LocalDateTime> source,
      final Function<T, LocalDateTime> min,
      final LocalDateTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(obj -> localDateTimeBetweenOrEqual(source, min.apply(obj), max).test(obj));
  }

  public static <T> Predicate<T> localDateTimeBetweenOrEqual(
      final Function<T, LocalDateTime> source,
      final LocalDateTime min,
      final Function<T, LocalDateTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(max))
        .and(obj -> localDateTimeBetweenOrEqual(source, min, max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localDateTimeBetweenOrEqual(
      final Function<T, LocalDateTime> source,
      final Function<T, LocalDateTime> min,
      final Function<T, LocalDateTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> localDateTimeBetweenOrEqual(source, min.apply(obj), max.apply(obj)).test(obj));
  }

  private LocalDateTimePredicate() {
    super();
  }
}
