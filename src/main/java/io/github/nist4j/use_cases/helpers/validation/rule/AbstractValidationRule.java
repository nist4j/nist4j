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
import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.Validator;
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

  private Function<Object, String> fieldName = obj -> null;

  private Function<Object, String> recordName = obj -> null;

  private Function<Object, Object> attemptedValue;

  @Getter private boolean critical;

  @Getter private Class<? extends Nist4jValidationException> criticalException;

  @Getter private Validator<T> validator = new InternalValidator();

  private HandlerInvalidField<P> handlerInvalidField = new InternalHandlerInvalidField(this);

  @Override
  public String getRecordName(final Object instance) {
    return this.recordName.apply(instance);
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
  public String getFieldName(final Object instance) {
    return this.fieldName.apply(instance);
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

  @Override
  public void withFieldName(final Function<?, String> fieldName) {
    this.fieldName = (Function<Object, String>) fieldName;
  }

  @Override
  public void withRecordName(final Function<?, String> recordName) {
    this.recordName = (Function<Object, String>) recordName;
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
              .withRecordName(fieldDescriptor.getRecordName(instance))
              .withFieldName(fieldDescriptor.getFieldName(instance))
              .withCode(fieldDescriptor.getCode(instance))
              .withMessage(fieldDescriptor.getMessage(instance))
              .withAttemptedFound(fieldDescriptor.getAttemptedValue(instance, attemptedValue))
              .build());
    }
  }
}
