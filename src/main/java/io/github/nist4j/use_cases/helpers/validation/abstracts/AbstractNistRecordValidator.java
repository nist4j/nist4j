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
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.StringCondition.EMPTY;
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
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidFieldNistRecord;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidFieldNistRecordWithMessage;
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
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(recordType, field, error));
  }

  protected void checkCustomPredicateOnField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      Predicate<String> predicate,
      String message) {

    ruleFor(r -> r)
        .must(handlePredicateOnField(field, predicate))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, message));
  }

  protected void checkForMandatoryField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, not(stringEmptyOrNull())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForEmptyField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_EMPTY_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is not present
        .must(handlePredicateOnField(field, stringEmptyOrNull()))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryLENField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_LEN;
    String msg = ValidationMessage.format(error, recordType, field);

    checkCustomPredicateOnField(field, error, validateLEN(), msg);
  }

  private static Predicate<String> validateLEN() {
    return mandatory(isCharTypeWithMinMaxLength(N, 1, 9).and(stringNotStartingWith("0")));
  }

  protected void checkForMandatoryAndRegexField(
      @NonNull IFieldTypeEnum field, @NonNull String regex) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_AND_MATCHS_REGEX_FORMAT_PATTERN;
    String msg = ValidationMessage.format(error, recordType, field, regex);

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnField(field, mandatory(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryCharTypeAndMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum characterType, int min, int max) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_CHAR_FORMAT_WITH_MIN_MAX_LENGTH;
    String msg = ValidationMessage.format(error, recordType, field, characterType.name(), min, max);

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnField(
                field, mandatory(isCharTypeWithMinMaxLength(characterType, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryCharTypeAndMinLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum characterType, int min) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_CHAR_FORMAT_WITH_MIN_LENGTH;
    String msg = ValidationMessage.format(error, recordType, field, characterType.name(), min);

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnField(field, mandatory(isCharTypeWithMinLength(characterType, min))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryAndExactStringField(
      @NonNull IFieldTypeEnum field, String expectedValue) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_AND_EXACT_MATCH;

    String msg = ValidationMessage.format(error, recordType, field, expectedValue);

    ruleFor(r -> r)
        // is Mandatory AND must be equal to value
        .must(handlePredicateOnField(field, mandatory(stringEquals(expectedValue))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButRegexField(
      @NonNull IFieldTypeEnum field, @NonNull String regex) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_AND_MATCHS_REGEX_FORMAT_PATTERN;
    String msg = ValidationMessage.format(error, recordType, field, regex);

    ruleFor(r -> r)
        // must follow regex or be empty
        .must(handlePredicateOnField(field, optional(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButCharTypeAndMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum characterType, int min, int max) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_CHAR_FORMAT_WITH_MIN_MAX_LENGTH;
    String msg = ValidationMessage.format(error, recordType, field, characterType.name(), min, max);

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnField(
                field, optional(isCharTypeWithMinMaxLength(characterType, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButCharTypeAndMinLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum characterType, int min) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_CHAR_FORMAT_WITH_MIN_LENGTH;
    String msg = ValidationMessage.format(error, recordType, field, characterType.name(), min);

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnField(field, optional(isCharTypeWithMinLength(characterType, min))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryDateField(@NonNull IFieldTypeEnum field) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATE_FORMAT_YYYYMMDD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory AND must be a Date
        .must(handlePredicateOnField(field, mandatory(isYYYYMMDDDate())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButDateField(@NonNull IFieldTypeEnum field) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATE_FORMAT_YYYYMMDD;
    String msg = ValidationMessage.format(error, recordType, field);

    this.ruleFor(r -> r)
        .must(handlePredicateOnField(field, optional(isYYYYMMDDDate())))
        .handlerInvalidField(
            this.handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryDateTimeField(@NonNull IFieldTypeEnum field) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATETIME_FORMAT_YYYYMMDDHHMMSS;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory AND must be a DateTime
        .must(handlePredicateOnField(field, mandatory(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButDateTimeField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_DATETIME_FORMAT_YYYYMMDDHHMMSS;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is optional OR must be a DateTime
        .must(handlePredicateOnField(field, optional(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryInCollectionField(
      @NonNull IFieldTypeEnum field, @NonNull List<String> allowedValues) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_MATCHS_COLLECTION;
    String params = String.join(",", allowedValues);
    String msg = ValidationMessage.format(error, recordType, field, params);

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, mandatory(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButInCollectionField(
      @NonNull IFieldTypeEnum field, @NonNull List<String> allowedValues) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_MATCHS_COLLECTION;
    String params = String.join(",", allowedValues);
    String msg = ValidationMessage.format(error, recordType, field, params);

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, optional(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryNumericFieldBetween(
      @NonNull IFieldTypeEnum field, int min, int max) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_NUMERIC_BETWEEN_VALUES;
    String msg = ValidationMessage.format(error, recordType, field, min, max);
    ruleFor(r -> r)
        .must(handlePredicateOnField(field, mandatory(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButNumericFieldBetween(
      @NonNull IFieldTypeEnum field, int min, int max) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_NUMERIC_BETWEEN;
    String msg = ValidationMessage.format(error, recordType, field, min, max);

    ruleFor(r -> r)
        .must(handlePredicateOnField(field, optional(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected final void checkForOptionalButUniqueSubfields(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum errorGlobal,
      SubfieldRule... subfieldValidators) {

    ruleFor(r -> r.getFieldText(field).orElse(EMPTY))
        .whenever(s -> isNotEmpty(s))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType, field, errorGlobal, subfieldValidators));
  }

  protected final void checkForOptionalButRepeatedSubfields(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum errorGlobal,
      SubfieldRule... subfieldValidators) {

    ruleFor(r -> r.getFieldText(field).orElse(EMPTY))
        .whenever(s -> isNotEmpty(s))
        .withValidator(
            new NistRepeatedSubfieldsValidator(
                this.nistOptions, this.recordType, field, errorGlobal, subfieldValidators));
  }

  protected void checkForMandatoryDataField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATA_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnDataField(field, not(ObjectPredicate.nullValue())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected HandlerInvalidField<NistRecord> handlerInvalidFieldInRecordWithMsg(
      RecordTypeEnum recordType, IFieldTypeEnum field, INistValidationErrorEnum error, String msg) {
    return new HandlerInvalidFieldNistRecordWithMessage(recordType, field, error, msg);
  }

  protected HandlerInvalidField<NistRecord> handlerInvalidFieldInRecordWithError(
      RecordTypeEnum recordType, IFieldTypeEnum field, INistValidationErrorEnum error) {
    return new HandlerInvalidFieldNistRecord(recordType, field, error);
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
