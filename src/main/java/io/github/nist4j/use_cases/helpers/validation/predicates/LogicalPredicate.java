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

import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import java.util.function.Function;
import java.util.function.Predicate;
import lombok.NonNull;

public final class LogicalPredicate {

  static <T> Predicate<T> is(final Predicate<T> predicate) {
    return PredicateBuilder.from(predicate.and(is -> true));
  }

  public static Predicate<Boolean> isFalse() {
    return PredicateBuilder.<Boolean>from(notNullValue()).and(not(isFalse -> isFalse));
  }

  public static <T> Predicate<T> isFalse(final Function<T, Boolean> function) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(function))
        .and(not(function::apply));
  }

  public static Predicate<Boolean> isTrue() {
    return PredicateBuilder.<Boolean>from(notNullValue()).and(is(isTrue -> isTrue));
  }

  public static <T> Predicate<T> isTrue(final Function<T, Boolean> function) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(function))
        .and(function::apply);
  }

  public static <T> Predicate<T> not(final Predicate<T> predicate) {
    return PredicateBuilder.from(predicate.negate());
  }

  public static Predicate<String> optional(@NonNull Predicate<String> validator) {
    return PredicateBuilder.from(stringEmptyOrNull().or(validator));
  }

  public static Predicate<String> mandatory(@NonNull Predicate<String> validator) {
    return PredicateBuilder.from(not(stringEmptyOrNull()).and(validator));
  }

  private LogicalPredicate() {
    super();
  }
}
