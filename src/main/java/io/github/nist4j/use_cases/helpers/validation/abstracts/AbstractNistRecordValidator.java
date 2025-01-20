/*
 * Copyright (C) 2025 Sopra Steria.
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
package io.github.nist4j.use_cases.helpers.validation.abstracts;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.PredicateBuilder.from;
import static br.com.fluentvalidator.predicate.StringPredicate.*;
import static io.github.nist4j.use_cases.helpers.mappers.ErrorMapper.toErrorOnField;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.getFieldImageOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.getFieldStringOrNull;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.handler.HandlerInvalidField;
import br.com.fluentvalidator.predicate.ObjectPredicate;
import br.com.fluentvalidator.predicate.StringPredicate;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.NonNull;

public abstract class AbstractNistRecordValidator extends AbstractValidator<NistRecord> {

  protected static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();
  public static final String EMPTY_VALUE = null;

  protected final NistOptions nistOptions;
  protected final RecordTypeEnum recordType;

  protected AbstractNistRecordValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
    this.nistOptions = nistOptions;
    this.recordType = recordType;
  }

  protected void checkCustomPredicateOnField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      Predicate<String> predicate) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, predicate))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, not(stringEmptyOrNull())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForEmptyField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is not present
        .must(handlePredicateOnField(field, stringEmptyOrNull()))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryAndRegexField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, String regex) {
    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnField(field, from(not(stringEmptyOrNull())).and(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryAndExactStringField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      String expectedValue) {
    ruleFor(r -> r)
        // is Mandatory AND must be equal to value
        .must(
            handlePredicateOnField(
                field, from(not(stringEmptyOrNull())).and(stringEquals(expectedValue))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButRegexField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, String regex) {
    ruleFor(r -> r)
        // must follow regex or be empty
        .must(handlePredicateOnField(field, from(stringEmptyOrNull()).or(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryDateField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is Mandatory AND must be a Date
        .must(handlePredicateOnField(field, from(not(stringEmptyOrNull())).and(isYYYYMMDDDate())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButDateField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    this.ruleFor(r -> r)
        .must(
            handlePredicateOnField(
                field,
                from(StringPredicate.stringEmptyOrNull()).or(NistFieldPredicates.isYYYYMMDDDate())))
        .handlerInvalidField(this.handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryDateTimeField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is Mandatory AND must be a DateTime
        .must(
            handlePredicateOnField(field, not(stringEmptyOrNull()).and(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButDateTimeField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is optional OR must be a DateTime
        .must(handlePredicateOnField(field, stringEmptyOrNull().or(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryInCollectionField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull List<String> allowedValues) {
    ruleFor(r -> r)
        // is Mandatory
        .must(
            handlePredicateOnField(
                field, not(stringEmptyOrNull()).and(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButInCollectionField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull List<String> allowedValues) {
    ruleFor(r -> r)
        // is Mandatory
        .must(
            handlePredicateOnField(
                field, stringEmptyOrNull().or(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryAlphaNumWithMinMaxLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      final int expectedMinLength,
      final int expectedMaxLength) {
    ruleFor(r -> r)
        // is Mandatory and his length must be between
        .must(
            handlePredicateOnField(field, stringSizeBetween(expectedMinLength, expectedMaxLength)))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButAlphaNumWithMinMaxLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      final int expectedMinLength,
      final int expectedMaxLength) {
    ruleFor(r -> r)
        // is Mandatory and his length must be between
        .must(
            handlePredicateOnField(
                field,
                from(stringEmptyOrNull())
                    .or(stringSizeBetween(expectedMinLength, expectedMaxLength))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryAlphaNumFixedLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      final int expectedLength) {
    ruleFor(r -> r)
        // is Mandatory AND length is valid
        .must(
            handlePredicateOnField(
                field, from(not(stringEmptyOrNull())).and(stringSize(expectedLength))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButAlphaNumFixedLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      final int expectedLength) {
    ruleFor(r -> r)
        .must(
            handlePredicateOnField(field, from(stringEmptyOrNull()).or(stringSize(expectedLength))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryNumericField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, StringPredicate.isNumeric()))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryNumericFieldBetween(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, from(isNumeric()).and(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButNumericFieldBetween(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(
            handlePredicateOnField(
                field,
                from(stringEmptyOrNull()).or(from(isNumeric()).and(isNumberBetween(min, max)))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryDataField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnDataField(field, not(ObjectPredicate.nullValue())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected HandlerInvalidField<NistRecord> handlerInvalidFieldInRecordWithError(
      INistValidationErrorEnum error) {
    return new HandlerInvalidField<NistRecord>() {
      @Override
      public Collection<Error> handle(final NistRecord attemptedValue) {
        // Get the value of the field specify in error
        // Or the value is absent
        String attemptedValueStr =
            Optional.of(error.getFieldTypeEnum())
                .flatMap(attemptedValue::getFieldText)
                .orElse(EMPTY_VALUE);
        Optional<String> idcRecord = getIDCField(attemptedValue);
        return Collections.singletonList(toErrorOnField(error, attemptedValueStr, idcRecord));
      }
    };
  }

  protected Predicate<NistRecord> handlePredicateOnField(
      @NonNull IFieldTypeEnum field, Predicate<String> predicate) {
    return r -> predicate.test(getFieldStringOrNull(field, r));
  }

  protected Predicate<NistRecord> handlePredicateOnDataField(
      @NonNull IFieldTypeEnum field, Predicate<Data> predicate) {
    return r -> predicate.test(getFieldImageOrNull(field, r));
  }

  private static Optional<String> getIDCField(NistRecord attemptedValue) {
    if (attemptedValue.getRecordId() != 1) {
      return attemptedValue.getFieldText(2);
    }
    return Optional.empty();
  }
}
