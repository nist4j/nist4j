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

import static io.github.nist4j.enums.CharacterTypeEnum.*;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.TimePredicate.isYYYYMMDDDate;
import static io.github.nist4j.use_cases.helpers.validation.predicates.TimePredicate.isYYYYMMDDHHMMSSDateTime;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidFieldNistRecord;
import io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate;
import java.util.*;
import java.util.function.Predicate;
import lombok.NonNull;

public abstract class AbstractNistRecordValidator extends AbstractValidator<NistRecord> {

  protected static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  protected final NistOptions nistOptions;

  @SuppressWarnings("unused")
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

  protected void checkForMandatoryLENField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {

    checkForMandatoryCharTypeAndLengthField(field, error, N, 1, 999999999);
    checkForMandatoryNumericFieldNotLeadingByZero(field, error);
  }

  protected void checkForMandatoryAndRegexField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, String regex) {
    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnField(field, mandatory(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryCharTypeAndLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull CharacterTypeEnum characterType,
      int min,
      int max) {
    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnField(
                field, mandatory(isCharTypeWithMinMaxLength(characterType, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryCharTypeAndMinLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull CharacterTypeEnum characterType,
      int min) {
    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnField(field, mandatory(isCharTypeWithMinLength(characterType, min))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryAndExactStringField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      String expectedValue) {
    ruleFor(r -> r)
        // is Mandatory AND must be equal to value
        .must(handlePredicateOnField(field, mandatory(stringEquals(expectedValue))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButRegexField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, String regex) {
    ruleFor(r -> r)
        // must follow regex or be empty
        .must(handlePredicateOnField(field, optional(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButCharTypeAndMinMaxLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull CharacterTypeEnum characterType,
      int min,
      int max) {
    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnField(
                field, optional(isCharTypeWithMinMaxLength(characterType, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButCharTypeAndMinLengthField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull CharacterTypeEnum characterType,
      int min) {
    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnField(field, optional(isCharTypeWithMinLength(characterType, min))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryDateField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is Mandatory AND must be a Date
        .must(handlePredicateOnField(field, mandatory(isYYYYMMDDDate())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButDateField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    this.ruleFor(r -> r)
        .must(handlePredicateOnField(field, optional(isYYYYMMDDDate())))
        .handlerInvalidField(this.handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryDateTimeField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is Mandatory AND must be a DateTime
        .must(handlePredicateOnField(field, mandatory(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButDateTimeField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        // is optional OR must be a DateTime
        .must(handlePredicateOnField(field, optional(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryInCollectionField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull List<String> allowedValues) {
    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, mandatory(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButInCollectionField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      @NonNull List<String> allowedValues) {
    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, optional(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButUnicodeFieldWithMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, optional(isCharTypeWithMinMaxLength(U, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryAlphaNumWithMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, isCharTypeWithMinMaxLength(ANS, min, max)))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForOptionalButAlphaNumWithMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, optional(isCharTypeWithMinMaxLength(ANS, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryAlphaNumFixedLengthField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int length) {
    ruleFor(r -> r)
        .must(
            handlePredicateOnField(
                field, mandatory(isCharTypeWithMinMaxLength(ANS, length, length))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButAlphaNumFixedLengthField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int length) {
    ruleFor(r -> r)
        .must(
            handlePredicateOnField(
                field, optional(isCharTypeWithMinMaxLength(ANS, length, length))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryNumericField(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, mandatory(isNumeric())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryNumericFieldBetween(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, mandatory(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  protected void checkForMandatoryNumericFieldNotLeadingByZero(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, mandatory(isNumeric().and(stringNotStartingWith("0")))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButNumericFieldBetween(
      @NonNull IFieldTypeEnum field, @NonNull INistValidationErrorEnum error, int min, int max) {
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, optional(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("unchecked")
  protected void checkForOptionalButUniqueSubfields(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      Predicate<String>... subfieldValidators) {

    ruleFor(r -> r)
        .must(handlePredicateOnField(field, validateStringSubfields(subfieldValidators)))
        .when(isFieldPresent(field))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  @SuppressWarnings("unchecked")
  protected void checkForOptionalButRepeatedSubfields(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      Predicate<String>... subfieldValidators) {

    ruleFor(r -> r)
        .must(handlePredicateOnField(field, validateRepeatedSubfields(subfieldValidators)))
        .when(isFieldPresent(field))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(error));
  }

  private Predicate<String> validateRepeatedSubfields(Predicate<String>[] subfieldValidators) {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems)
          || listOfItems.stream().allMatch(validateSubfields(subfieldValidators));
    };
  }

  private Predicate<List<String>> validateSubfields(Predicate<String>[] subfieldValidators) {
    return items -> {
      if (items.size() > subfieldValidators.length) {
        // missing validators, so this is too many subfields
        return false;
      }
      for (int i = 0; i < subfieldValidators.length; i++) {
        if (!subfieldValidators[i].test(getIndexOrNull(items, i))) {
          return false;
        }
      }
      return true;
    };
  }

  private Predicate<String> validateStringSubfields(Predicate<String>[] subfieldValidators) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return validateSubfields(subfieldValidators).test(items);
    };
  }

  private String getIndexOrNull(List<String> items, int index) {
    if (isEmpty(items) || index >= items.size()) {
      return null;
    } else {
      return items.get(index);
    }
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
    return new HandlerInvalidFieldNistRecord(error);
  }

  protected Predicate<NistRecord> handlePredicateOnField(
      @NonNull IFieldTypeEnum field, Predicate<String> predicate) {
    return r -> predicate.test(getFieldStringOrNull(field, r));
  }

  @SuppressWarnings("SameParameterValue")
  protected Predicate<NistRecord> handlePredicateOnPairOfFields(
      @NonNull IFieldTypeEnum fieldLeft,
      @NonNull IFieldTypeEnum fieldRight,
      Predicate<Pair<String, String>> predicate) {
    return r ->
        predicate.test(
            Pair.of(getFieldStringOrNull(fieldLeft, r), getFieldStringOrNull(fieldRight, r)));
  }

  @SuppressWarnings("SameParameterValue")
  protected Predicate<NistRecord> handlePredicateOnFieldWithImage(
      @NonNull IFieldTypeEnum field, Predicate<Pair<String, DataImage>> predicate) {
    return r ->
        predicate.test(
            Pair.of(
                getFieldStringOrNull(field, r), getFieldImageOrNull(GenericImageTypeEnum.DATA, r)));
  }

  protected Predicate<NistRecord> handlePredicateOnDataField(
      @NonNull IFieldTypeEnum field, Predicate<DataImage> predicate) {
    return r -> predicate.test(getFieldImageOrNull(field, r));
  }
}
