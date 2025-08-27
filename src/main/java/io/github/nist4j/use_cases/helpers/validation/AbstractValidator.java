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

import io.github.nist4j.use_cases.helpers.validation.builder.RuleBuilderCollection;
import io.github.nist4j.use_cases.helpers.validation.builder.RuleBuilderProperty;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.rule.Rule;
import io.github.nist4j.use_cases.helpers.validation.rule.RuleBuilderCollectionImpl;
import io.github.nist4j.use_cases.helpers.validation.rule.RuleBuilderPropertyImpl;
import io.github.nist4j.use_cases.helpers.validation.rule.RuleProcessorStrategy;
import io.github.nist4j.use_cases.helpers.validation.transform.ValidationResultTransform;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractValidator<T> implements Validator<T> {

  private final List<Rule<T>> rules = new LinkedList<>();

  private final Initializer<T> initialize;

  private String property;

  private RuleProcessorStrategy ruleProcessor = RuleProcessorStrategy.getDefault();

  private static class Initializer<T> {

    private final AtomicReference<Boolean> atomicReference = new AtomicReference<>(Boolean.FALSE);

    private final Validator<T> validator;

    Initializer(final Validator<T> validator) {
      this.validator = validator;
    }

    /**
     * This method cause Race Condition. We are using Compare And Swap (CAS)
     * <p>
     * {@link <a href="https://en.wikipedia.org/wiki/Race_condition">...</a>}
     * {@link <a href="https://en.wikipedia.org/wiki/Compare-and-swap">...</a>}
     */
    public void init() {
      if (isNotInitialized()) {
        synchronized (atomicReference) {
          if (isNotInitialized()) { // double check if was initialized
            validator.rules();
            final Boolean oldValue = atomicReference.get();
            atomicReference.compareAndSet(oldValue, Boolean.TRUE);
          }
        }
      }
    }

    private boolean isNotInitialized() {
      return Boolean.FALSE.equals(atomicReference.get());
    }
  }

  protected AbstractValidator() {
    this.initialize = new Initializer<>(this);
  }

  @Override
  public void failFastRule() {
    this.ruleProcessor = RuleProcessorStrategy.getFailFast();
  }

  @Override
  public void setPropertyOnContext(final String property) {
    this.property = property;
  }

  @Override
  public <P> P getPropertyOnContext(final String property, final Class<P> clazz) {
    return ValidationContext.get().getProperty(property, clazz);
  }

  @Override
  public ValidationResult validate(final T instance) {
    ruleProcessor.process(instance, this);
    return ValidationContext.get().getValidationResult();
  }

  @Override
  public <E> E validate(final T instance, final ValidationResultTransform<E> resultTransform) {
    return resultTransform.transform(validate(instance));
  }

  @Override
  public List<ValidationResult> validate(final Collection<T> instances) {
    return Collections.unmodifiableList(
        instances.stream().map(this::validate).collect(Collectors.toList()));
  }

  @Override
  public <E> List<E> validate(
      final Collection<T> instances, final ValidationResultTransform<E> resultTransform) {
    return Collections.unmodifiableList(
        instances.stream()
            .map(instance -> this.validate(instance, resultTransform))
            .collect(Collectors.toList()));
  }

  @Override
  public boolean apply(final T instance) {
    this.initialize.init();
    ValidationContext.get().setProperty(this.property, instance);
    return ruleProcessor.process(instance, instance, rules);
  }

  @Override
  public <P> RuleBuilderProperty<T, P> ruleFor(final Function<T, P> function) {
    final RuleBuilderPropertyImpl<T, P> rule = new RuleBuilderPropertyImpl<>(function);
    this.rules.add(rule);
    return rule;
  }

  @Override
  public <P> RuleBuilderProperty<T, P> ruleFor(
      final String recordName, final String fieldName, final Function<T, P> function) {
    final RuleBuilderPropertyImpl<T, P> rule =
        new RuleBuilderPropertyImpl<>(recordName, fieldName, function);
    this.rules.add(rule);
    return rule;
  }

  @Override
  public <P> RuleBuilderCollection<T, P> ruleForEach(
      final String recordName, final String fieldName, final Function<T, Collection<P>> function) {
    final RuleBuilderCollectionImpl<T, P> rule =
        new RuleBuilderCollectionImpl<>(recordName, fieldName, function);
    this.rules.add(rule);
    return rule;
  }

  @Override
  public <P> RuleBuilderCollection<T, P> ruleForEach(final Function<T, Collection<P>> function) {
    final RuleBuilderCollectionImpl<T, P> rule = new RuleBuilderCollectionImpl<>(function);
    this.rules.add(rule);
    return rule;
  }
}
