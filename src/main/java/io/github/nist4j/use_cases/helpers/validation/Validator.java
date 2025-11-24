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
package io.github.nist4j.use_cases.helpers.validation;

import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.builder.RuleBuilderCollection;
import io.github.nist4j.use_cases.helpers.validation.builder.RuleBuilderProperty;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.rule.Rule;
import io.github.nist4j.use_cases.helpers.validation.transform.ValidationResultTransform;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface Validator<T> extends Rule<T> {

  void rules();

  void failFastRule();

  void setPropertyOnContext(final String property);

  <P> P getPropertyOnContext(final String property, final Class<P> clazz);

  ValidationResult validate(final T instance);

  <E> E validate(final T instance, final ValidationResultTransform<E> transform);

  List<ValidationResult> validate(final Collection<T> instances);

  <E> List<E> validate(final Collection<T> instances, final ValidationResultTransform<E> transform);

  <P> RuleBuilderProperty<T, P> ruleFor(final Function<T, P> function);

  <P> RuleBuilderProperty<T, P> ruleFor(
      final RecordTypeEnum recordType,
      final IFieldTypeEnum fieldType,
      final String subfieldName,
      final Function<T, P> function);

  <P> RuleBuilderCollection<T, P> ruleForEach(final Function<T, Collection<P>> function);

  <P> RuleBuilderCollection<T, P> ruleForEach(
      final RecordTypeEnum recordType,
      final IFieldTypeEnum fieldType,
      final String subfieldName,
      final Function<T, Collection<P>> function);
}
