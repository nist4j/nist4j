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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14;

import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.PredicateBuilder.from;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.Arrays;
import java.util.Objects;

public class Std2011RT14Validator extends AbstractStdRT14Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2011;
  }

  protected Std2011RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2011RT14Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  public void rules() {
    // Common rules on fields
    checkForMandatoryLENField(LEN);
    checkForMandatoryNumericFieldBetween(IDC, 0, 99);
    checkForMandatoryInCollectionField(IMP, getAllowedValuesForIMP(getStandard()));
    checkForMandatoryField(SRC);
    checkForMandatoryDateField(FCD);
    checkForSLCField(); // 14.008
    checkForFGPField(); // 14.013
    checkForPPDField(); // 14.014
    checkForPPCField(); // 14.015
    checkForOptionalButNumericFieldBetween(SHPS, 1, 99999);
    checkForOptionalButNumericFieldBetween(SVPS, 1, 99999);
    checkForAMPField(); // 14.018
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, CharacterTypeEnum.U, 1, 126);
    checkForSEGField(); // 14.021
    checkForNQMField(); // 14.022
    checkForSQMField(); // 14.023
    checkForFQMField(); // 14.024
    checkForASEGField(); // 14.025
    checkForOptionalButNumericFieldBetween(SCF, 1, 255);
    checkForOptionalButInCollectionField(SIF, SIF_ALLOWED_VALUE);
    checkForOptionalButInCollectionField(DMM, getAllowedValuesForDMM(getStandard()));
    checkForOptionalButInCollectionField(FAP, getAllowedValuesForFAP(getStandard()));

    // Conditional rules
    ruleFor(r -> r)
        .whenever(Std2011RT14Validator::hasImage)
        .withValidator(new Standard2011RT14WithImageValidator(nistOptions, RT14))
        .whenever(r -> !hasImage(r))
        .withValidator(new Standard2011RT14WithoutImageValidator(nistOptions, RT14));
  }

  protected void checkForSLCField() {
    ruleFor(r -> r)
        .must(r -> Objects.equals(getFieldStringOrNull(THPS, r), getFieldStringOrNull(TVPS, r)))
        .when(isFieldInCollection(SLC, Arrays.asList("1", "2")))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SLC, StdNistValidatorErrorEnum.STD_ERR_SLC_COHERENCE_RT14));
  }

  protected void checkForSEGField() {
    ruleFor(r -> r)
        // Should be present with right pattern, if finger combination
        .must(validateFieldSEG(getStandard()))
        .when(isFieldInCollection(FGP, getFGPFingersCombinationExceptEJI(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SEG, StdNistValidatorErrorEnum.STD_ERR_SEG_INVALID_RT14))
        // Should be absent, if no finger combination
        .must(isFieldAbsent(SEG))
        .when(not(isFieldInCollection(FGP, getFGPFingersCombinationExceptEJI(getStandard()))))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SEG, StdNistValidatorErrorEnum.STD_ERR_SEG_NOT_ALLOWED_RT14));
  }

  protected void checkForPPDField() {
    ruleFor(r -> r)
        // Should be present, if eji
        .must(not(isFieldAbsent(PPD)).and(validateFieldPPD(getStandard())))
        .when(isEJIFingerprint())
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PPD, StdNistValidatorErrorEnum.STD_ERR_PPD))
        // Should be absent, if not eji
        .must(isFieldAbsent(PPD))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PPD, StdNistValidatorErrorEnum.STD_ERR_PPD));
  }

  protected void checkForPPCField() {
    ruleFor(r -> r)
        // Should be present, if eji
        .must(from(not(isFieldAbsent(PPC))).and(validateFieldPPC()))
        .when(isEJIFingerprint())
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PPC, StdNistValidatorErrorEnum.STD_ERR_PPC))
        // Should be absent, if not eji
        .must(isFieldAbsent(PPC))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PPC, StdNistValidatorErrorEnum.STD_ERR_PPC));
  }

  protected void checkForFGPField() {
    checkCustomPredicateOnField(
        FGP,
        StdNistValidatorErrorEnum.STD_ERR_FGP,
        // is Mandatory and is defined in collection
        not(stringEmptyOrNull()).and(validateFieldFGP(getStandard())));
    checkCustomPredicateOnField(
        FGP,
        StdNistValidatorErrorEnum.STD_ERR_FGP_ONE_ALLOWED_RT14,
        // only one value allowed  - new from standard 2011
        field -> SubFieldToStringConverter.toListUsingSplitByRS(field).size() == 1);
  }

  protected void checkForAMPField() {
    checkCustomPredicateOnField(
        AMP,
        StdNistValidatorErrorEnum.STD_ERR_AMP_RT14,
        // match format, if present
        optional(validateFieldAMP(getStandard())));
  }

  protected void checkForNQMField() {
    checkCustomPredicateOnField(
        NQM,
        StdNistValidatorErrorEnum.STD_ERR_NQM_RT14,
        // match format, if present
        optional(validateFieldNQM(getStandard())));
  }

  protected void checkForSQMField() {
    ruleFor(r -> r)
        // match format, if present
        .must(from(isFieldAbsent(SQM)).or(validateFieldSQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQM, StdNistValidatorErrorEnum.STD_ERR_SQM_RT14))
        // has values compatible with others fields
        .must(validateConsistencySQM())
        .when(not(isFieldAbsent(SQM)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQM, StdNistValidatorErrorEnum.STD_ERR_SQM_UNALLOWED_FRQP_RT14));
  }

  protected void checkForFQMField() {
    checkCustomPredicateOnField(
        FQM,
        StdNistValidatorErrorEnum.STD_ERR_FQM_RT14,
        // match format, if present
        optional(validateFieldFQM(getStandard())));
  }

  protected void checkForASEGField() {
    ruleFor(r -> r)
        // match format, if present
        .must(from(isFieldAbsent(ASEG)).or(validateConsistencyASEG(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, ASEG, StdNistValidatorErrorEnum.STD_ERR_ASEG_RT14));
  }

  private static boolean hasImage(NistRecord record14) {
    return record14.getFieldImage(DATA).isPresent();
  }

  public class Standard2011RT14WithImageValidator extends AbstractNistRecordValidator {

    protected Standard2011RT14WithImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      checkForMandatoryNumericFieldBetween(HLL, 10, 99999);
      checkForMandatoryNumericFieldBetween(VLL, 10, 99999);
      checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
      checkForMandatoryNumericFieldBetween(THPS, 1, 99999);
      checkForMandatoryNumericFieldBetween(TVPS, 1, 99999);
      checkForMandatoryInCollectionField(CGA, getAllowedValuesForCGA(getStandard()));
      checkForMandatoryNumericFieldBetween(BPX, 8, 99);
    }
  }

  public static class Standard2011RT14WithoutImageValidator extends AbstractNistRecordValidator {

    protected Standard2011RT14WithoutImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      checkForEmptyField(HLL);
      checkForEmptyField(VLL);
      checkForEmptyField(SLC);
      checkForEmptyField(THPS);
      checkForEmptyField(TVPS);
      checkForEmptyField(CGA);
      checkForEmptyField(BPX);
    }
  }
}
