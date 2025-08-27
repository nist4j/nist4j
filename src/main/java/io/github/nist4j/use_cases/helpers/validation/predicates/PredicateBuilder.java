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

import java.util.function.Predicate;

public final class PredicateBuilder<T> implements Predicate<T> {

  private final Predicate<T> predicate;

  public static <T> Predicate<T> from(final Predicate<T> predicate) {
    return new PredicateBuilder<>(predicate);
  }

  private PredicateBuilder(final Predicate<T> predicate) {
    this.predicate = predicate;
  }

  @Override
  public boolean test(final T value) {
    return predicate.test(value);
  }
}
