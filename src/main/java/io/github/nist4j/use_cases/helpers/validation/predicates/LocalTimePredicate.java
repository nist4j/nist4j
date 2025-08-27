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

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.is;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;

import java.time.LocalTime;
import java.util.function.Function;
import java.util.function.Predicate;

public final class LocalTimePredicate {

  public static <T extends LocalTime> Predicate<T> localTimeAfterNow() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localTime -> localTimeAfter(LocalTime.now()).test(localTime));
  }

  public static <T> Predicate<T> localTimeAfterNow(final Function<T, LocalTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeAfterNow().test(source.apply(obj)));
  }

  public static <T extends LocalTime> Predicate<T> localTimeBeforeNow() {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localTime -> localTimeBefore(LocalTime.now()).test(localTime));
  }

  public static <T> Predicate<T> localTimeBeforeNow(final Function<T, LocalTime> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeBeforeNow().test(source.apply(obj)));
  }

  public static <T extends LocalTime> Predicate<T> localTimeEqualTo(final LocalTime localTime) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(localTime))
        .and(obj -> localTime.compareTo(obj) == 0);
  }

  public static <T> Predicate<T> localTimeEqualTo(
      final Function<T, LocalTime> source, final LocalTime localTime) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeEqualTo(localTime).test(source.apply(obj)));
  }

  public static <T extends LocalTime> Predicate<T> localTimeAfter(final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(obj -> obj.isAfter(target));
  }

  public static <T> Predicate<T> localTimeAfter(
      final Function<T, LocalTime> source, final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeAfter(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localTimeAfter(
      final Function<T, LocalTime> source, final Function<T, LocalTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localTimeAfter(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalTime> Predicate<T> localTimeAfterOrEqual(final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(is(localTimeAfter(target)).or(localTimeEqualTo(target)));
  }

  public static <T> Predicate<T> localTimeAfterOrEqual(
      final Function<T, LocalTime> source, final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeAfterOrEqual(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localTimeAfterOrEqual(
      final Function<T, LocalTime> source, final Function<T, LocalTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localTimeAfterOrEqual(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalTime> Predicate<T> localTimeBefore(final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(obj -> obj.isBefore(target));
  }

  public static <T> Predicate<T> localTimeBefore(
      final Function<T, LocalTime> source, final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeBefore(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localTimeBefore(
      final Function<T, LocalTime> source, final Function<T, LocalTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localTimeBefore(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalTime> Predicate<T> localTimeBeforeOrEqual(final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target))
        .and(is(localTimeBefore(target)).or(localTimeEqualTo(target)));
  }

  public static <T> Predicate<T> localTimeBeforeOrEqual(
      final Function<T, LocalTime> source, final LocalTime target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeBeforeOrEqual(target).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localTimeBeforeOrEqual(
      final Function<T, LocalTime> source, final Function<T, LocalTime> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> localTimeBeforeOrEqual(source, target.apply(obj)).test(obj));
  }

  public static <T extends LocalTime> Predicate<T> localTimeBetween(
      final LocalTime min, final LocalTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localTimeAfter(min).and(localTimeBefore(max)));
  }

  public static <T> Predicate<T> localTimeBetween(
      final Function<T, LocalTime> source, final LocalTime min, final LocalTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeBetween(min, max).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localTimeBetween(
      final Function<T, LocalTime> source, final Function<T, LocalTime> min, final LocalTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(obj -> localTimeBetween(source, min.apply(obj), max).test(obj));
  }

  public static <T> Predicate<T> localTimeBetween(
      final Function<T, LocalTime> source, final LocalTime min, final Function<T, LocalTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(max))
        .and(obj -> localTimeBetween(source, min, max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localTimeBetween(
      final Function<T, LocalTime> source,
      final Function<T, LocalTime> min,
      final Function<T, LocalTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> localTimeBetween(source, min.apply(obj), max.apply(obj)).test(obj));
  }

  public static <T extends LocalTime> Predicate<T> localTimeBetweenOrEqual(
      final LocalTime min, final LocalTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(localTimeAfterOrEqual(min).and(localTimeBeforeOrEqual(max)));
  }

  public static <T> Predicate<T> localTimeBetweenOrEqual(
      final Function<T, LocalTime> source, final LocalTime min, final LocalTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> localTimeBetweenOrEqual(min, max).test(source.apply(obj)));
  }

  public static <T> Predicate<T> localTimeBetweenOrEqual(
      final Function<T, LocalTime> source, final Function<T, LocalTime> min, final LocalTime max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(obj -> localTimeBetweenOrEqual(source, min.apply(obj), max).test(obj));
  }

  public static <T> Predicate<T> localTimeBetweenOrEqual(
      final Function<T, LocalTime> source, final LocalTime min, final Function<T, LocalTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(max))
        .and(obj -> localTimeBetweenOrEqual(source, min, max.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> localTimeBetweenOrEqual(
      final Function<T, LocalTime> source,
      final Function<T, LocalTime> min,
      final Function<T, LocalTime> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> localTimeBetweenOrEqual(source, min.apply(obj), max.apply(obj)).test(obj));
  }

  private LocalTimePredicate() {
    super();
  }
}
