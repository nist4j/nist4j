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
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.subject.NistRefSubjectStatusCodeEnum.DECEASED_PERSON;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationError;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.StringCondition.EMPTY;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.TimePredicate.isYYYYMMDDDate;
import static io.github.nist4j.use_cases.helpers.validation.predicates.TimePredicate.isYYYYMMDDHHMMSSDateTime;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.GenericBinaryFieldsEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.ref.NistRefColorSpaceEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgeCaptureTechEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum;
import io.github.nist4j.enums.ref.subject.NistRefSubjectBodyClassCodeEnum;
import io.github.nist4j.enums.ref.subject.NistRefSubjectBodyStatusCodeEnum;
import io.github.nist4j.enums.ref.subject.NistRefSubjectStatusCodeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.options.NistOptionsBuilderImpl;
import io.github.nist4j.use_cases.helpers.checksum.Sha256Checksum;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidFieldNistRecord;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidFieldNistRecordWithMessage;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.NonNull;

public abstract class AbstractNistRecordValidator extends AbstractValidator<NistRecord> {

  protected static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsBuilderImpl.DefaultOpts.TO_VALIDATE.getOptions();

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
        .must(handlePredicateOnTextField(field, predicate))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(recordType, field, error));
  }

  protected void checkCustomPredicateOnField(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum error,
      Predicate<String> predicate,
      String message) {

    ruleFor(r -> r)
        .must(handlePredicateOnTextField(field, predicate))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, message));
  }

  protected void checkForMandatoryField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnTextField(field, not(stringEmptyOrNull())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForEmptyField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_EMPTY_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is not present
        .must(handlePredicateOnTextField(field, stringEmptyOrNull()))
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

  @SuppressWarnings({"unused", "SameParameterValue"})
  protected void checkForMandatoryAndRegexField(
      @NonNull IFieldTypeEnum field, @NonNull String regex) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_AND_MATCHS_REGEX_FORMAT_PATTERN;
    String msg = ValidationMessage.format(error, recordType, field, null, singletonList(regex));

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnTextField(field, mandatory(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryCharTypeAndMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum characterType, int min, int max) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_CHAR_FORMAT_WITH_MIN_MAX_LENGTH;
    String msg =
        ValidationMessage.format(error, recordType, field, asList(characterType.name(), min, max));

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnTextField(
                field, mandatory(isCharTypeWithMinMaxLength(characterType, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForMandatoryCharTypeAndMinLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum charType, int min) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_CHAR_FORMAT_WITH_MIN_LENGTH;
    String msg = ValidationMessage.format(error, recordType, field, asList(charType.name(), min));

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnTextField(field, mandatory(isCharTypeWithMinLength(charType, min))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings({"SameParameterValue", "unused"})
  protected void checkForMandatoryAndExactStringField(
      @NonNull IFieldTypeEnum field, String expectedValue) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_AND_EXACT_MATCH;

    String msg =
        ValidationMessage.format(error, recordType, field, null, singletonList(expectedValue));

    ruleFor(r -> r)
        // is Mandatory AND must be equal to value
        .must(handlePredicateOnTextField(field, mandatory(stringEquals(expectedValue))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("unused")
  protected void checkForOptionalButRegexField(
      @NonNull IFieldTypeEnum field,
      @SuppressWarnings("SameParameterValue") @NonNull String regex) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_AND_MATCHS_REGEX_FORMAT_PATTERN;
    String msg = ValidationMessage.format(error, recordType, field, null, singletonList(regex));

    ruleFor(r -> r)
        // must follow regex or be empty
        .must(handlePredicateOnTextField(field, optional(stringMatches(regex))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButCharTypeAndMinMaxLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum characterType, int min, int max) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_CHAR_FORMAT_WITH_MIN_MAX_LENGTH;
    String msg =
        ValidationMessage.format(error, recordType, field, asList(characterType.name(), min, max));

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(
            handlePredicateOnTextField(
                field, optional(isCharTypeWithMinMaxLength(characterType, min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButCharTypeAndMinLengthField(
      @NonNull IFieldTypeEnum field, @NonNull CharacterTypeEnum charType, int min) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_CHAR_FORMAT_WITH_MIN_LENGTH;
    String msg = ValidationMessage.format(error, recordType, field, asList(charType.name(), min));

    ruleFor(r -> r)
        // is Mandatory AND must follow regex
        .must(handlePredicateOnTextField(field, optional(isCharTypeWithMinLength(charType, min))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryDateField(@NonNull IFieldTypeEnum field) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATE_FORMAT_YYYYMMDD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory AND must be a Date
        .must(handlePredicateOnTextField(field, mandatory(isYYYYMMDDDate())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("unused")
  protected void checkForOptionalButDateField(@NonNull IFieldTypeEnum field) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATE_FORMAT_YYYYMMDD;
    String msg = ValidationMessage.format(error, recordType, field);

    this.ruleFor(r -> r)
        .must(handlePredicateOnTextField(field, optional(isYYYYMMDDDate())))
        .handlerInvalidField(
            this.handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("unused")
  protected void checkForMandatoryDateTimeField(@NonNull IFieldTypeEnum field) {
    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATETIME_FORMAT_YYYYMMDDHHMMSS;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory AND must be a DateTime
        .must(handlePredicateOnTextField(field, mandatory(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButDateTimeField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_DATETIME_FORMAT_YYYYMMDDHHMMSS;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is optional OR must be a DateTime
        .must(handlePredicateOnTextField(field, optional(isYYYYMMDDHHMMSSDateTime())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryInCollectionField(
      @NonNull IFieldTypeEnum field, @NonNull List<String> allowedValues) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_MATCHS_COLLECTION;
    String params = String.join(",", allowedValues);
    String msg = ValidationMessage.format(error, recordType, field, null, singletonList(params));

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnTextField(field, mandatory(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForOptionalButInCollectionField(
      @NonNull IFieldTypeEnum field, @NonNull List<String> allowedValues) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_MATCHS_COLLECTION;
    String params = String.join(",", allowedValues);
    String msg = ValidationMessage.format(error, recordType, field, null, singletonList(params));

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnTextField(field, optional(stringInCollection(allowedValues))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected void checkForMandatoryNumericFieldBetween(
      @NonNull IFieldTypeEnum field, int min, int max) {

    StdNistValidatorErrorEnum error =
        StdNistValidatorErrorEnum.STD_ERR_MANDATORY_NUMERIC_BETWEEN_VALUES;
    String msg = ValidationMessage.format(error, recordType, field, asList(min, max));
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(field, mandatory(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForOptionalButNumericFieldBetween(
      @NonNull IFieldTypeEnum field, int min, int max) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_NUMERIC_BETWEEN;
    String msg = ValidationMessage.format(error, recordType, field, asList(min, max));

    ruleFor(r -> r)
        .must(handlePredicateOnTextField(field, optional(isNumberBetween(min, max))))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  protected final void checkForOptionalButUniqueSubfields(
      @NonNull IFieldTypeEnum field,
      @NonNull INistValidationErrorEnum errorGlobal,
      SubfieldRule... subfieldValidators) {

    // Optional but not empty
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(field, not(stringEmptyOrNull())))
        .when(isFieldPresent(field))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(this.recordType, field, errorGlobal));
    // Optional but contains validators
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

    // Optional but not empty
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(field, not(stringEmptyOrNull())))
        .when(isFieldPresent(field))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(this.recordType, field, errorGlobal));

    // Optional but contains validators
    ruleFor(r -> r.getFieldText(field).orElse(EMPTY))
        .whenever(s -> isNotEmpty(s))
        .withValidator(
            new NistRepeatedSubfieldsValidator(
                this.nistOptions, this.recordType, field, errorGlobal, subfieldValidators));
  }

  @SuppressWarnings("unused")
  @Deprecated
  protected void checkForMandatoryDataField(@NonNull IFieldTypeEnum field) {
    checkForMandatoryImageField(field);
  }

  protected void checkForMandatoryImageField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_MANDATORY_DATA_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Mandatory
        .must(handlePredicateOnField(field, not(emptyOrNull()).and(isFieldImage())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, field, error, msg));
  }

  @SuppressWarnings("unused")
  protected void checkForOptionalButImageField(@NonNull IFieldTypeEnum field) {

    StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_OPTIONAL_BUT_DATA_FIELD;
    String msg = ValidationMessage.format(error, recordType, field);

    ruleFor(r -> r)
        // is Optional
        .must(handlePredicateOnField(field, nullValue().or(isFieldImage().and(not(emptyOrNull())))))
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

  protected Predicate<NistRecord> handlePredicateOnTextField(
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
                getFieldStringOrNull(field, r),
                getFieldImageOrNull(GenericBinaryFieldsEnum.DATA, r)));
  }

  @SuppressWarnings("unused")
  protected Predicate<NistRecord> handlePredicateOnImageField(
      @NonNull IFieldTypeEnum field, Predicate<DataImage> predicate) {
    return r -> predicate.test(getFieldImageOrNull(field, r));
  }

  protected Predicate<NistRecord> handlePredicateOnImageField(
      @SuppressWarnings("SameParameterValue") int fieldId, Predicate<DataImage> predicate) {
    return r -> predicate.test(getFieldImageOrNull(fieldId, r));
  }

  @SuppressWarnings("rawtypes")
  protected Predicate<NistRecord> handlePredicateOnField(
      @NonNull IFieldTypeEnum field, Predicate<Data> predicate) {
    return r -> predicate.test(getFieldOrNull(field, r));
  }

  protected List<String> getAllowedValuesForCGA(
      RecordTypeEnum recordType, NistStandardEnum nistStandard) {
    List<NistRefCompressionAlgorithmEnum> valuesOfCGA =
        Arrays.stream(NistRefCompressionAlgorithmEnum.values())
            .filter(cga -> cga.getAllowedRT().contains(recordType))
            .collect(Collectors.toList());
    return findCodesAllowedByStandard(valuesOfCGA, nistStandard);
  }

  protected void checkForGenericFieldCSP_xxx(
      @NonNull IFieldTypeEnum fieldCSP,
      @NonNull IFieldTypeEnum fieldBPX,
      @NonNull NistStandardEnum nistStandard) {
    // Mandatory when BPX > 8, otherwise Optional
    final List<String> allowedColors =
        findCodesAllowedByStandard(NistRefColorSpaceEnum.values(), nistStandard);
    final String param = String.join(",", allowedColors);
    final String msg =
        ValidationMessage.format(STD_ERR_CSP, recordType, fieldCSP, null, singletonList(param));

    ruleFor(r -> r)
        .must(handlePredicateOnTextField(fieldCSP, stringInCollection(allowedColors)))
        .when(isFieldPresent(fieldBPX).and(not(isFieldNumberBetween(fieldBPX, 0, 8))))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithMsg(recordType, fieldCSP, STD_ERR_CSP, msg));
  }

  protected void checkForGenericFieldFQC_029(
      @NonNull IFieldTypeEnum fqcField,
      List<NistRefFrictionRidgePositionEnum> allowedFGP,
      NistStandardEnum nistStandard) {

    final List<String> allowedValuesForFGP = findCodesAllowedByStandard(allowedFGP, nistStandard);
    checkForOptionalButRepeatedSubfields(
        fqcField,
        StdNistValidatorErrorEnum.STD_ERR_FQC,
        SubfieldRule.of("FRP", stringInCollection(allowedValuesForFGP).and(not(stringEquals("0")))),
        SubfieldRule.of("QNQ", isCharTypeWithMinLength(ANS, 1)),
        SubfieldRule.of("QAV", isCharTypeWithMinMaxLength(H, 4, 4)),
        SubfieldRule.of("QAP", isNumberBetween(1, 65535)),
        SubfieldRule.of("QPV", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("QCM", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("QCK", optional(isCharTypeWithMinMaxLength(H, 64, 64))));
  }

  protected void checkForGenericFieldSUB_046(
      @NonNull IFieldTypeEnum fieldSUB, @NonNull NistStandardEnum nistStandard) {
    // Mandatory when SSC = D, otherwise omitted.
    final List<String> codesAllowedForSSC =
        findCodesAllowedByStandard(NistRefSubjectStatusCodeEnum.values(), nistStandard);
    final List<String> codesAllowedForSBSC =
        findCodesAllowedByStandard(NistRefSubjectBodyStatusCodeEnum.values(), nistStandard);
    final List<String> codesAllowedForSBCC =
        findCodesAllowedByStandard(
            NistRefSubjectBodyClassCodeEnum.listForRT(recordType), nistStandard);
    SubfieldRule[] subfieldValidatorsWhenSSCisDthenOtherFieldsAreMandatory =
        asList(
                SubfieldRule.of("SSC", stringInCollection(codesAllowedForSSC)),
                SubfieldRule.of("SBSC", stringInCollection(codesAllowedForSBSC)),
                SubfieldRule.of("SBCC", stringInCollection(codesAllowedForSBCC)))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(fieldSUB).orElse(EMPTY))
        .whenever(s -> whenSUBisPresentAndEqualsTo(s, DECEASED_PERSON.getCode()))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                fieldSUB,
                STD_ERR_SUB_1,
                subfieldValidatorsWhenSSCisDthenOtherFieldsAreMandatory));

    SubfieldRule[] subfieldValidatorsWhenSSCisNotDthenOtherFieldsAreForbideen =
        asList(
                SubfieldRule.of("SSC", stringInCollection(codesAllowedForSSC)),
                SubfieldRule.of("SBSC", stringEmptyOrNull()),
                SubfieldRule.of("SBCC", stringEmptyOrNull()))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(fieldSUB).orElse(EMPTY))
        .whenever(s -> whenSUBisPresentAndNotEqualsTo(s, DECEASED_PERSON.getCode()))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                fieldSUB,
                STD_ERR_SUB_2,
                subfieldValidatorsWhenSSCisNotDthenOtherFieldsAreForbideen));
  }

  private boolean whenSUBisPresentAndEqualsTo(String fieldSUBValue, String expectedSUBValue) {
    if (isEmpty(fieldSUBValue)) {
      return false;
    }
    return fieldSUBValue.startsWith(expectedSUBValue);
  }

  private boolean whenSUBisPresentAndNotEqualsTo(String fieldSUBValue, String expectedSUBValue) {
    if (isEmpty(fieldSUBValue)) {
      return false;
    }
    return !fieldSUBValue.startsWith(expectedSUBValue);
  }

  protected void checkForGenericFieldBRI_199(@NonNull IFieldTypeEnum fieldBRI) {
    checkForOptionalButCharTypeAndMinLengthField(fieldBRI, U, 1);
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForGenericFieldFCT_901(
      @NonNull IFieldTypeEnum fieldFCT,
      @NonNull IFieldTypeEnum fieldIMP,
      @NonNull NistStandardEnum standard,
      @NonNull Predicate<NistRecord> condition) {

    final StdNistValidatorErrorEnum error = StdNistValidatorErrorEnum.STD_ERR_FCT;
    final String msg =
        ValidationMessage.format(
            error, recordType, fieldFCT, null, singletonList(fieldIMP.getCode()));
    final List<String> refFRCaptTech =
        findCodesAllowedByStandard(NistRefFrictionRidgeCaptureTechEnum.values(), standard);

    ruleFor(r -> r)
        .must(handlePredicateOnTextField(fieldFCT, stringInCollection(refFRCaptTech)))
        .when(condition)
        .handlerInvalidField(handlerInvalidFieldInRecordWithMsg(recordType, fieldFCT, error, msg));
  }

  protected void checkForGenericFieldANN_902(@NonNull IFieldTypeEnum fieldType) {
    checkForOptionalButRepeatedSubfields(
        fieldType,
        StdNistValidatorErrorEnum.STD_ERR_ANN,
        SubfieldRule.of("GMT", isYYYYMMDDHHMMSSDateTime()),
        SubfieldRule.of("NAV", isCharTypeWithMinMaxLength(U, 1, 64)),
        SubfieldRule.of("OWN", isCharTypeWithMinMaxLength(U, 1, 64)),
        SubfieldRule.of("PRO", isCharTypeWithMinMaxLength(U, 1, 255)));
  }

  protected void checkForGenericFieldDUI_903(@NonNull IFieldTypeEnum fieldType) {
    checkCustomPredicateOnField(
        fieldType,
        StdNistValidatorErrorEnum.STD_ERR_DUI,
        stringEmptyOrNull()
            .or(
                isCharTypeWithMinMaxLength(ANS, 13, 16)
                    .and(stringStartingWith("M").or(stringStartingWith("P")))));
  }

  protected void checkForGenericFieldMMS_904(@NonNull IFieldTypeEnum fieldType) {
    checkForOptionalButUniqueSubfields(
        fieldType,
        StdNistValidatorErrorEnum.STD_ERR_MMS,
        SubfieldRule.of("MAK", isCharTypeWithMinMaxLength(U, 1, 50)),
        SubfieldRule.of("MOD", isCharTypeWithMinMaxLength(U, 1, 50)),
        SubfieldRule.of("SER", isCharTypeWithMinMaxLength(U, 1, 50)));
  }

  @SuppressWarnings("SameParameterValue")
  protected void checkForGenericFieldEFR_994(
      @NonNull IFieldTypeEnum fieldEFR, @NonNull NistStandardEnum nistStandard) {
    if (nistStandard.isPriorTo(NistStandardEnum.ANSI_NIST_ITL_2025)) {
      // In 2015 only one subfield
      checkForOptionalButCharTypeAndMinMaxLengthField(fieldEFR, U, 1, 200);
    } else {
      checkForOptionalButUniqueSubfields(
          fieldEFR,
          StdNistValidatorErrorEnum.STD_ERR_EFR,
          SubfieldRule.of("EFL", isCharTypeWithMinMaxLength(U, 1, 50)),
          SubfieldRule.of("EFF", isCharTypeWithMinMaxLength(U, 1, 50)));
    }
    // EFR and DATA must not be used in the same time
    ruleFor(r -> r)
        .must(handlePredicateOnImageField(999, Objects::isNull))
        .when(isFieldPresent(fieldEFR))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, fieldEFR, StdNistValidatorErrorEnum.STD_ERR_EFR_WITH_DATA));
  }

  protected void checkForGenericFieldASC_995(@NonNull IFieldTypeEnum fieldType) {
    checkForOptionalButRepeatedSubfields(
        fieldType,
        StdNistValidatorErrorEnum.STD_ERR_ASC,
        SubfieldRule.of("ACN", isNumberBetween(1, 255)),
        SubfieldRule.of("ASP", optional(isNumberBetween(1, 99))));
  }

  protected void checkForGenericFieldHAS_996(@NonNull IFieldTypeEnum fieldType) {
    checkForOptionalButCharTypeAndMinMaxLengthField(fieldType, H, 64, 64);

    ruleFor(r -> r)
        .must(handlePredicateOnFieldWithImage(fieldType, validateFieldHASequalsToHashOfDATA()))
        .when(isFieldPresent(fieldType))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, fieldType, StdNistValidatorErrorEnum.STD_ERR_HAS));
  }

  protected Predicate<Pair<String, DataImage>> validateFieldHASequalsToHashOfDATA() {
    return pairOfFields -> {
      DataImage dataImage = pairOfFields.getRight();
      if (isEmpty(dataImage) || isEmpty(dataImage.getData())) {
        return false;
      }
      String sha256 = Sha256Checksum.calculateToHex(dataImage.getData());
      String hasField = pairOfFields.getLeft();
      return stringEquals(sha256).test(hasField);
    };
  }

  protected void checkForGenericFieldSOR_997(@NonNull IFieldTypeEnum fieldType) {
    checkForOptionalButRepeatedSubfields(
        fieldType,
        StdNistValidatorErrorEnum.STD_ERR_SOR,
        SubfieldRule.of(
            "SRN",
            isNumberBetween(1, 255),
            newNistValidationError(
                STD_ERR_MANDATORY_NUMERIC_BETWEEN, recordType, fieldType, "SRN", asList(1, 255))),
        SubfieldRule.of(
            "RSP",
            optional(isNumberBetween(1, 99)),
            newNistValidationError(
                STD_ERR_OPTIONAL_NUMERIC_BETWEEN, recordType, fieldType, "RSP", asList(1, 99))));
  }

  protected void checkForGenericFieldGEO_998(@NonNull IFieldTypeEnum geo) {
    final RecordTypeEnum rt = this.recordType;
    checkForOptionalButUniqueSubfields(
        geo,
        StdNistValidatorErrorEnum.STD_ERR_GEO,
        SubfieldRule.of(
            "UTE",
            optional(isYYYYMMDDHHMMSSDateTime()),
            newNistValidationError(
                STD_ERR_OPTIONAL_DATETIME_FORMAT_YYYYMMDDHHMMSS, rt, geo, "UTE")),
        SubfieldRule.of(
            "LTD",
            optional(isRealNumberBetween(-90, 90)),
            newNistValidationError(
                STD_ERR_OPTIONAL_NUMERIC_BETWEEN, rt, geo, "LTD", asList(-90, 90))),
        SubfieldRule.of(
            "LTM",
            optional(isRealNumberBetween(0, 60)),
            newNistValidationError(
                STD_ERR_OPTIONAL_NUMERIC_BETWEEN, rt, geo, "LTM", asList(0, 60))),
        SubfieldRule.of(
            "LTS",
            optional(isRealNumberBetween(0, 60)),
            newNistValidationError(
                STD_ERR_OPTIONAL_REAL_NUMBER_BETWEEN, rt, geo, "LTS", asList(0, 60))),
        SubfieldRule.of(
            "LGD",
            optional(isRealNumberBetween(-180, 180)),
            newNistValidationError(
                STD_ERR_OPTIONAL_REAL_NUMBER_BETWEEN, rt, geo, "LGD", asList(-180, 180))),
        SubfieldRule.of(
            "LGM",
            optional(isRealNumberBetween(0, 60)),
            newNistValidationError(
                STD_ERR_OPTIONAL_REAL_NUMBER_BETWEEN, rt, geo, "LGM", asList(0, 60))),
        SubfieldRule.of(
            "LGS",
            optional(isRealNumberBetween(0, 60)),
            newNistValidationError(
                STD_ERR_OPTIONAL_REAL_NUMBER_BETWEEN, rt, geo, "LGS", asList(0, 60))),
        SubfieldRule.of(
            "ELE",
            optional(isRealNumberBetween(-422, 8848)),
            newNistValidationError(
                STD_ERR_OPTIONAL_REAL_NUMBER_BETWEEN, rt, geo, "ELE", asList(-422, 8848))),
        SubfieldRule.of("GDC", optional(isCharTypeWithMinMaxLength(AN, 3, 6))),
        SubfieldRule.of("GCM", optional(isCharTypeWithMinMaxLength(AN, 2, 3))),
        SubfieldRule.of(
            "GCS",
            optional(isNumberBetween(0, 999999)),
            newNistValidationError(
                STD_ERR_OPTIONAL_NUMERIC_BETWEEN, rt, geo, "GCS", asList(0, 999999))),
        SubfieldRule.of(
            "GCN",
            optional(isNumberBetween(0, 99999999)),
            newNistValidationError(
                STD_ERR_OPTIONAL_NUMERIC_BETWEEN, rt, geo, "GCN", asList(0, 99999999))),
        SubfieldRule.of("GRT", optional(isCharTypeWithMinMaxLength(U, 1, 150))),
        SubfieldRule.of("OSI", optional(isCharTypeWithMinMaxLength(U, 1, 150))),
        SubfieldRule.of("OCV", optional(isCharTypeWithMinMaxLength(U, 1, 126))));
  }
}
