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

import static io.github.nist4j.use_cases.helpers.validation.function.FunctionBuilder.of;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;

import java.util.function.Function;
import java.util.function.Predicate;

public final class ComparablePredicate {

  private static final Integer ZERO = 0;

  public static <E, T extends Comparable<E>> Predicate<T> between(final E min, final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(lessThan(max).and(greaterThan(min)));
  }

  public static <E, T extends Comparable<E>> Predicate<T> between(
      final E min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue()).and(between(of((T fn) -> min), max));
  }

  public static <E, T extends Comparable<E>> Predicate<T> between(
      final Function<T, E> min, final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(between(min, of((T fn) -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> between(
      final Function<T, E> source, final E min, final E max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> between(min, max).test(source.apply(obj)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> between(
      final Function<T, E> source, final E min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue()).and(between(source, of((T fn) -> min), max));
  }

  public static <E, T extends Comparable<E>> Predicate<T> between(
      final Function<T, E> min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> lessThan(max.apply(obj)).and(greaterThan(min.apply(obj))).test(obj));
  }

  public static <T, E extends Comparable<E>> Predicate<T> between(
      final Function<T, E> source, final Function<T, E> min, final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(between(source, min, of((T fn) -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> between(
      final Function<T, E> source, final Function<T, E> min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(
            obj ->
                lessThan(max.apply(obj)).and(greaterThan(min.apply(obj))).test(source.apply(obj)));
  }

  public static <E, T extends Comparable<E>> Predicate<T> betweenInclusive(
      final E min, final E max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(lessThanOrEqual(max).and(greaterThanOrEqual(min)));
  }

  public static <E, T extends Comparable<E>> Predicate<T> betweenInclusive(
      final E min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue()).and(betweenInclusive(of((T fn) -> min), max));
  }

  public static <E, T extends Comparable<E>> Predicate<T> betweenInclusive(
      final Function<T, E> min, final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(betweenInclusive(min, of((T fn) -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> betweenInclusive(
      final Function<T, E> source, final E min, final E max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(betweenInclusive(source, of((T fn) -> min), of((T fn) -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> betweenInclusive(
      final Function<T, E> source, final E min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(betweenInclusive(source, of((T fn) -> min), max));
  }

  public static <E, T extends Comparable<E>> Predicate<T> betweenInclusive(
      final Function<T, E> min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(
            obj ->
                lessThanOrEqual(max.apply(obj)).and(greaterThanOrEqual(min.apply(obj))).test(obj));
  }

  public static <T, E extends Comparable<E>> Predicate<T> betweenInclusive(
      final Function<T, E> source, final Function<T, E> min, final E max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(betweenInclusive(source, min, of((T fn) -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> betweenInclusive(
      final Function<T, E> source, final Function<T, E> min, final Function<T, E> max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(min))
        .and(notNullValue(max))
        .and(obj -> betweenInclusive(min.apply(obj), max.apply(obj)).test(source.apply(obj)));
  }

  public static <E, T extends Comparable<E>> Predicate<T> equalTo(final E value) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(fn -> value))
        .and(obj -> obj.compareTo(value) == ZERO);
  }

  public static <T, E extends Comparable<E>> Predicate<T> equalTo(
      final Function<T, E> source, final E value) {
    return PredicateBuilder.<T>from(notNullValue()).and(equalTo(source, of(fn -> value)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> equalTo(
      final Function<T, E> source, final Function<T, E> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(obj -> source.apply(obj).compareTo(target.apply(obj)) == ZERO);
  }

  public static <E, T extends Comparable<E>> Predicate<T> greaterThan(final E min) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(fn -> min))
        .and(obj -> obj.compareTo(min) > ZERO);
  }

  public static <T, E extends Comparable<E>> Predicate<T> greaterThan(
      final Function<T, E> source, final E min) {
    return PredicateBuilder.<T>from(notNullValue()).and(greaterThan(source, of(fn -> min)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> greaterThan(
      final Function<T, E> source, final Function<T, E> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(obj -> source.apply(obj).compareTo(target.apply(obj)) > ZERO);
  }

  public static <E, T extends Comparable<E>> Predicate<T> greaterThanOrEqual(final E min) {
    return PredicateBuilder.<T>from(notNullValue()).and(greaterThan(min).or(equalTo(min)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> greaterThanOrEqual(
      final Function<T, E> source, final E min) {
    return PredicateBuilder.<T>from(notNullValue()).and(greaterThanOrEqual(source, of(fn -> min)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> greaterThanOrEqual(
      final Function<T, E> source, final Function<T, E> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(greaterThan(source, target).or(equalTo(source, target)));
  }

  public static <E, T extends Comparable<E>> Predicate<T> lessThan(final E max) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(fn -> max))
        .and(lessThan -> lessThan.compareTo(max) < ZERO);
  }

  public static <T, E extends Comparable<E>> Predicate<T> lessThan(
      final Function<T, E> source, final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(lessThan(source, of(fn -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> lessThan(
      final Function<T, E> source, final Function<T, E> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(obj -> source.apply(obj).compareTo(target.apply(obj)) < ZERO);
  }

  public static <E, T extends Comparable<E>> Predicate<T> lessThanOrEqual(final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(lessThan(max).or(equalTo(max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> lessThanOrEqual(
      final Function<T, E> source, final E max) {
    return PredicateBuilder.<T>from(notNullValue()).and(lessThanOrEqual(source, of(fn -> max)));
  }

  public static <T, E extends Comparable<E>> Predicate<T> lessThanOrEqual(
      final Function<T, E> source, final Function<T, E> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(lessThan(source, target).or(equalTo(source, target)));
  }

  private ComparablePredicate() {
    super();
  }
}
