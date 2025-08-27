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
package io.github.nist4j.use_cases.helpers.validation.rule;

import java.util.Collection;
import java.util.stream.Collectors;

public interface RuleProcessorStrategy {

  default <E> boolean process(final Object obj, final E value, final Rule<E> rule) {
    return !rule.support(value) || rule.apply(obj, value);
  }

  default <E> boolean process(final E value, final Rule<E> rule) {
    return !rule.support(value) || rule.apply(value);
  }

  default <E> boolean process(final Object obj, final Collection<E> values, final Rule<E> rule) {
    return values.stream()
        .map(value -> this.process(obj, value, rule))
        .collect(Collectors.toList())
        .stream()
        .allMatch(result -> result);
  }

  default <E> boolean process(final Collection<E> values, final Rule<E> rule) {
    return values.stream()
        .map(value -> this.process(value, rule))
        .collect(Collectors.toList())
        .stream()
        .allMatch(result -> result);
  }

  default <E> boolean process(final Object obj, final E value, final Collection<Rule<E>> rules) {
    return rules.stream()
        .map(rule -> this.process(obj, value, rule))
        .collect(Collectors.toList())
        .stream()
        .allMatch(result -> result);
  }

  default <E> boolean process(final E value, final Collection<Rule<E>> rules) {
    return rules.stream()
        .map(rule -> this.process(value, rule))
        .collect(Collectors.toList())
        .stream()
        .allMatch(result -> result);
  }

  static RuleProcessorStrategy getFailFast() {
    return new RuleProcessorFailFast();
  }

  static RuleProcessorStrategy getDefault() {
    return new RuleProcessorDefault();
  }
}
