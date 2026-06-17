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

import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.annotation.CleanValidationContextException;
import io.github.nist4j.use_cases.helpers.validation.builder.*;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class RuleBuilderCollectionImpl<T, P>
    extends AbstractRuleBuilder<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
    implements RuleBuilderCollection<T, P>, WhenCollection<T, P>, WheneverCollection<T, P> {

  private final Collection<Rule<Collection<P>>> rules = new LinkedList<>();

  private final RuleProcessorStrategy ruleProcessor = RuleProcessorStrategy.getFailFast();

  private ValidationRule<P, Collection<P>> currentValidation;

  public RuleBuilderCollectionImpl(
      final RecordTypeEnum recordType,
      final IFieldTypeEnum fieldType,
      final String subfieldName,
      final Function<T, Collection<P>> function) {
    super(recordType, fieldType, subfieldName, function);
  }

  public RuleBuilderCollectionImpl(final Function<T, Collection<P>> function) {
    super(function);
  }

  @Override
  public boolean apply(final T instance) {
    final Collection<P> value = Objects.nonNull(instance) ? function.apply(instance) : null;
    return ruleProcessor.process(instance, value, rules);
  }

  @Override
  public WheneverCollection<T, P> whenever(final Predicate<Collection<P>> whenever) {
    this.currentValidation = new ValidatorRuleInternal(fieldType, whenever);
    this.rules.add(this.currentValidation);
    return this;
  }

  @Override
  public Must<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> must(
      final Predicate<Collection<P>> must) {
    this.currentValidation = new ValidationRuleInternal(fieldType, must);
    this.rules.add(this.currentValidation);
    return this;
  }

  @Override
  public Message<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> withMessage(
      final String message) {
    this.currentValidation.withMessage(obj -> message);
    return this;
  }

  @Override
  public Message<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> withMessage(
      final Function<T, String> message) {
    this.currentValidation.withMessage(message);
    return this;
  }

  @Override
  public Code<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> withCode(
      final String code) {
    this.currentValidation.withCode(obj -> code);
    return this;
  }

  @Override
  public Code<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> withCode(
      final Function<T, String> code) {
    this.currentValidation.withCode(code);
    return this;
  }

  @Override
  public FieldType<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> withFieldType(
      final IFieldTypeEnum fieldType) {
    this.currentValidation.withFieldType(obj -> fieldType);
    return this;
  }

  public FieldType<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>> withFieldType(
      final Function<T, IFieldTypeEnum> fieldType) {
    this.currentValidation.withFieldType(fieldType);
    return this;
  }

  @Override
  public SubfieldName<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withSubfieldName(final String subfieldName) {
    this.currentValidation.withSubfieldName(obj -> subfieldName);
    return this;
  }

  public SubfieldName<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withSubfieldName(final Function<T, String> subfieldName) {
    this.currentValidation.withSubfieldName(subfieldName);
    return this;
  }

  @Override
  public RecordType<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withRecordType(final RecordTypeEnum recordType) {
    this.currentValidation.withRecordType(obj -> recordType);
    return this;
  }

  @Override
  public RecordType<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withRecordType(final Function<T, RecordTypeEnum> recordType) {
    this.currentValidation.withRecordType(recordType);
    return this;
  }

  @Override
  public AttemptedValue<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withAttemptedValue(final Object attemptedValue) {
    this.currentValidation.withAttemptedValue(obj -> attemptedValue);
    return this;
  }

  @Override
  public AttemptedValue<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withAttemptedValue(final Function<T, Object> attemptedValue) {
    this.currentValidation.withAttemptedValue(attemptedValue);
    return this;
  }

  @Override
  public HandleInvalidField<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      handlerInvalidField(final HandlerInvalidField<Collection<P>> handlerInvalidField) {
    this.currentValidation.withHandlerInvalidField(handlerInvalidField);
    return this;
  }

  @Override
  public WithValidator<T, Collection<P>, WhenCollection<T, P>, WheneverCollection<T, P>>
      withValidator(final Validator<P> validator) {
    this.currentValidation.withValidator(validator);
    return this;
  }

  @Override
  public WhenCollection<T, P> when(final Predicate<Collection<P>> when) {
    this.currentValidation.when(when);
    return this;
  }

  class ValidationRuleInternal extends AbstractValidationRule<P, Collection<P>> {

    ValidationRuleInternal(
        final Function<T, IFieldTypeEnum> fieldType, final Predicate<Collection<P>> must) {
      super.must(must);
      super.withFieldType(fieldType);
    }

    @Override
    public boolean support(final Collection<P> instance) {
      return getWhen().test(instance);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @CleanValidationContextException
    public boolean apply(final Object obj, final Collection<P> instance) {

      final boolean apply = getMust().test(instance);
      if (!apply) {
        ValidationContext.get().addErrors(getHandlerInvalid().handle(obj, instance));
      }
      return apply;
    }
  }

  class ValidatorRuleInternal extends AbstractValidationRule<P, Collection<P>> {

    ValidatorRuleInternal(
        final Function<T, IFieldTypeEnum> fieldType, final Predicate<Collection<P>> whenever) {
      super.whenever(whenever);
      super.withFieldType(fieldType);
    }

    @Override
    public boolean support(final Collection<P> instance) {
      return getWhenever().test(instance);
    }

    @Override
    @CleanValidationContextException
    public boolean apply(final Object obj, final Collection<P> instance) {

      return ruleProcessor.process(obj, instance, getValidator());
    }
  }
}
