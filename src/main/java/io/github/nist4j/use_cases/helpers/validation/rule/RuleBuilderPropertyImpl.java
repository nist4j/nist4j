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

import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.annotation.CleanValidationContextException;
import io.github.nist4j.use_cases.helpers.validation.builder.*;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.exceptions.Nist4jValidationException;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class RuleBuilderPropertyImpl<T, P>
    extends AbstractRuleBuilder<T, P, WhenProperty<T, P>, WheneverProperty<T, P>>
    implements RuleBuilderProperty<T, P>, WhenProperty<T, P>, WheneverProperty<T, P> {

  private final Collection<Rule<P>> rules = new LinkedList<>();

  private final RuleProcessorStrategy ruleProcessor = RuleProcessorStrategy.getFailFast();

  private ValidationRule<P, P> currentValidation;

  public RuleBuilderPropertyImpl(
      final String recordName, final String fieldName, final Function<T, P> function) {
    super(recordName, fieldName, function);
  }

  public RuleBuilderPropertyImpl(final Function<T, P> function) {
    super(function);
  }

  @Override
  public boolean apply(final T instance) {
    final P value = Objects.nonNull(instance) ? function.apply(instance) : null;
    return ruleProcessor.process(instance, value, rules);
  }

  @Override
  public WheneverProperty<T, P> whenever(final Predicate<P> whenever) {
    this.currentValidation = new ValidatorRuleInternal(fieldName, whenever);
    this.rules.add(this.currentValidation);
    return this;
  }

  @Override
  public Must<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> must(final Predicate<P> must) {
    this.currentValidation = new ValidationRuleInternal(fieldName, must);
    this.rules.add(this.currentValidation);
    return this;
  }

  @Override
  public Message<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withMessage(
      final String message) {
    this.currentValidation.withMessage(obj -> message);
    return this;
  }

  @Override
  public Message<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withMessage(
      final Function<T, String> message) {
    this.currentValidation.withMessage(message);
    return this;
  }

  @Override
  public Code<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withCode(final String code) {
    this.currentValidation.withCode(obj -> code);
    return this;
  }

  @Override
  public Code<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withCode(
      final Function<T, String> code) {
    this.currentValidation.withCode(code);
    return this;
  }

  @Override
  public FieldName<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withFieldName(
      final String fieldName) {
    this.currentValidation.withFieldName(obj -> fieldName);
    return this;
  }

  @Override
  public FieldName<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withFieldName(
      final Function<T, String> fieldName) {
    this.currentValidation.withFieldName(fieldName);
    return this;
  }

  @Override
  public RecordName<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withRecordName(
      final String recordName) {
    this.currentValidation.withRecordName(obj -> recordName);
    return this;
  }

  @Override
  public RecordName<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withRecordName(
      final Function<T, String> recordName) {
    this.currentValidation.withRecordName(recordName);
    return this;
  }

  @Override
  public AttemptedValue<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withAttemptedValue(
      final Object attemptedValue) {
    this.currentValidation.withAttemptedValue(obj -> attemptedValue);
    return this;
  }

  @Override
  public AttemptedValue<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withAttemptedValue(
      final Function<T, Object> attemptedValue) {
    this.currentValidation.withAttemptedValue(attemptedValue);
    return this;
  }

  @Override
  public Critical<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> critical() {
    this.currentValidation.critical();
    return this;
  }

  @Override
  public Critical<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> critical(
      final Class<? extends Nist4jValidationException> clazz) {
    this.currentValidation.critical(clazz);
    return this;
  }

  @Override
  public HandleInvalidField<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> handlerInvalidField(
      final HandlerInvalidField<P> handlerInvalidField) {
    this.currentValidation.withHandlerInvalidField(handlerInvalidField);
    return this;
  }

  @Override
  public WithValidator<T, P, WhenProperty<T, P>, WheneverProperty<T, P>> withValidator(
      final Validator<P> validator) {
    this.currentValidation.withValidator(validator);
    return this;
  }

  @Override
  public WhenProperty<T, P> when(final Predicate<P> predicate) {
    this.currentValidation.when(predicate);
    return this;
  }

  class ValidationRuleInternal extends AbstractValidationRule<P, P> {

    ValidationRuleInternal(final Function<T, String> fieldName, final Predicate<P> must) {
      super.must(must);
      super.withFieldName(fieldName);
    }

    @Override
    public boolean support(final P instance) {
      return getWhen().test(instance);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @CleanValidationContextException
    public boolean apply(final Object obj, final P instance) {

      final boolean apply = getMust().test(instance);

      if (!apply) {
        ValidationContext.get().addErrors(getHandlerInvalid().handle(obj, instance));
      }

      if (Objects.nonNull(getCriticalException()) && !apply) {
        throw Nist4jValidationException.create(getCriticalException());
      }

      return !(isCritical() && !apply);
    }
  }

  class ValidatorRuleInternal extends AbstractValidationRule<P, P> {

    ValidatorRuleInternal(final Function<T, String> fieldName, final Predicate<P> whenever) {
      super.whenever(whenever);
      super.withFieldName(fieldName);
    }

    @Override
    public boolean support(final P instance) {
      return getWhenever().test(instance);
    }

    @Override
    @CleanValidationContextException
    public boolean apply(final Object obj, final P instance) {

      final boolean apply = ruleProcessor.process(obj, instance, getValidator());

      if (Objects.nonNull(getCriticalException()) && !apply) {
        throw Nist4jValidationException.create(getCriticalException());
      }

      return !(isCritical() && !apply);
    }
  }
}
