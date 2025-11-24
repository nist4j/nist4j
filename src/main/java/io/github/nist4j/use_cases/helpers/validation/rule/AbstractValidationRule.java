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

import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationErrorBuilder;
import static java.util.Collections.singletonList;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.exceptions.Nist4jValidationException;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.Getter;

abstract class AbstractValidationRule<T, P>
    implements ValidationRule<T, P>, FieldDescriptor<Object, P> {

  @Getter private Predicate<P> whenever = w -> true;

  @Getter private Predicate<P> when = w -> true;

  @Getter private Predicate<P> must = m -> true;

  private Function<Object, String> message = obj -> null;

  private Function<Object, String> code = obj -> null;

  private Function<Object, IFieldTypeEnum> fieldType = obj -> null;

  private Function<Object, RecordTypeEnum> recordType = obj -> null;

  private Function<Object, String> subfieldName = obj -> null;

  private Function<Object, Object> attemptedValue;

  @Getter private boolean critical;

  @Getter private Class<? extends Nist4jValidationException> criticalException;

  @Getter private Validator<T> validator = new InternalValidator();

  private HandlerInvalidField<P> handlerInvalidField = new InternalHandlerInvalidField(this);

  @Override
  public RecordTypeEnum getRecordType(final Object instance) {
    return this.recordType.apply(instance);
  }

  @Override
  public String getMessage(final Object instance) {
    return this.message.apply(instance);
  }

  @Override
  public String getCode(final Object instance) {
    return this.code.apply(instance);
  }

  @Override
  public IFieldTypeEnum getFieldType(final Object instance) {
    return this.fieldType.apply(instance);
  }

  @Override
  public String getSubfieldName(final Object instance) {
    return this.subfieldName.apply(instance);
  }

  @Override
  public Object getAttemptedValue(final Object instance, final P defaultValue) {
    return Objects.isNull(this.attemptedValue) ? defaultValue : this.attemptedValue.apply(instance);
  }

  public HandlerInvalidField<P> getHandlerInvalid() {
    return handlerInvalidField;
  }

  @Override
  public void when(final Predicate<P> when) {
    this.when = when;
  }

  @Override
  public void must(final Predicate<P> must) {
    this.must = must;
  }

  public void withFieldType(final Function<?, IFieldTypeEnum> fieldType) {
    this.fieldType = (Function<Object, IFieldTypeEnum>) fieldType;
  }

  public void withRecordType(final Function<?, RecordTypeEnum> recordType) {
    this.recordType = (Function<Object, RecordTypeEnum>) recordType;
  }

  public void withSubfieldName(final Function<?, String> subfieldName) {
    this.subfieldName = (Function<Object, String>) subfieldName;
  }

  @Override
  public void withMessage(final Function<?, String> message) {
    this.message = (Function<Object, String>) message;
  }

  @Override
  public void withCode(final Function<?, String> code) {
    this.code = (Function<Object, String>) code;
  }

  @Override
  public void withAttemptedValue(final Function<?, Object> attemptedValue) {
    this.attemptedValue = (Function<Object, Object>) attemptedValue;
  }

  @Override
  public void withHandlerInvalidField(final HandlerInvalidField<P> handlerInvalidField) {
    this.handlerInvalidField = handlerInvalidField;
  }

  @Override
  public void critical() {
    this.critical = true;
  }

  @Override
  public void critical(final Class<? extends Nist4jValidationException> clazz) {
    this.critical = true;
    this.criticalException = clazz;
  }

  @Override
  public void whenever(final Predicate<P> whenever) {
    this.whenever = whenever;
  }

  @Override
  public void withValidator(final Validator<T> validator) {
    this.validator = validator;
  }

  private class InternalValidator extends AbstractValidator<T> {
    @SuppressWarnings("unused")
    @Override
    public void rules() {
      // Do nothing
    }
  }

  private class InternalHandlerInvalidField implements HandlerInvalidField<P> {

    private final FieldDescriptor<Object, P> fieldDescriptor;

    public InternalHandlerInvalidField(final FieldDescriptor<Object, P> fieldDescriptor) {
      this.fieldDescriptor = fieldDescriptor;
    }

    @Override
    public List<NistValidationError> handle(final Object instance, final P attemptedValue) {
      return singletonList(
          newNistValidationErrorBuilder()
              .withRecordType(fieldDescriptor.getRecordType(instance))
              .withFieldType(fieldDescriptor.getFieldType(instance))
              .withCode(fieldDescriptor.getCode(instance))
              .withMessage(fieldDescriptor.getMessage(instance))
              .withAttemptedFound(fieldDescriptor.getAttemptedValue(instance, attemptedValue))
              .build());
    }
  }
}
