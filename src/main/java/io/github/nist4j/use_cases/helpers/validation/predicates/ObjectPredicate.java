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

import io.github.nist4j.use_cases.helpers.conditions.ObjectCondition;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ObjectPredicate {

  public static <T> Predicate<T> equalObject(
      final Function<T, Object> source, final Function<T, Object> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(obj -> Objects.equals(source.apply(obj), target.apply(obj)));
  }

  public static <T> Predicate<T> equalObject(
      final Function<T, Object> source, final Object target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(obj -> target))
        .and(obj -> Objects.equals(source.apply(obj), target));
  }

  public static <T> Predicate<T> equalObject(final Object target) {
    return PredicateBuilder.<T>from(notNullValue()).and(obj -> Objects.equals(obj, target));
  }

  @SuppressWarnings("java:S1612")
  public static <T> Predicate<T> instanceOf(final Class<?> clazz) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(fn -> clazz))
        .and(obj -> clazz.isInstance(obj));
  }

  @SuppressWarnings("unused")
  public static <T> Predicate<T> instanceOf(final Function<T, ?> source, final Class<?> clazz) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> instanceOf(clazz).test(source.apply(obj)));
  }

  public static <T> Predicate<T> nullValue() {
    return PredicateBuilder.from(Objects::isNull);
  }

  public static <T> Predicate<T> nullValue(final Function<T, ?> source) {
    return PredicateBuilder.<T>from(nullValue())
        .or(obj -> Objects.isNull(source))
        .or(obj -> Objects.isNull(source.apply(obj)));
  }

  public static <T> Predicate<T> emptyValue() {
    return PredicateBuilder.from(ObjectCondition::isEmpty);
  }

  @SuppressWarnings("unused")
  public static <T> Predicate<T> emptyValue(final Function<T, ?> source) {
    return PredicateBuilder.<T>from(emptyValue())
        .or(obj -> Objects.isNull(source))
        .or(obj -> Objects.isNull(source.apply(obj)));
  }

  public static <T> Predicate<T> notNullValue() {
    return PredicateBuilder.from(not(nullValue()));
  }

  public static <T> Predicate<T> notNullValue(final Function<T, ?> source) {
    return PredicateBuilder.from(not(nullValue(source)));
  }

  private ObjectPredicate() {
    super();
  }
}
