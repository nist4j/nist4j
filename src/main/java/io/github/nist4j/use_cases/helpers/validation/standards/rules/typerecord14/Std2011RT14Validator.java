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

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.PredicateBuilder.from;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
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
    checkForMandatoryAndRegexField(
        RT14FieldsEnum.LEN, StdNistValidatorErrorEnum.STD_ERR_LEN, "^[1-9]\\d{0,7}$");
    checkForMandatoryNumericFieldBetween(
        RT14FieldsEnum.IDC, StdNistValidatorErrorEnum.STD_ERR_IDC, 0, 99);
    checkForMandatoryInCollectionField(
        RT14FieldsEnum.IMP,
        StdNistValidatorErrorEnum.STD_ERR_IMP_MANDATORY_RT14,
        getAllowedValuesForIMP(getStandard()));
    checkForMandatoryField(RT14FieldsEnum.SRC, StdNistValidatorErrorEnum.STD_ERR_SRC);
    checkForMandatoryDateField(RT14FieldsEnum.FCD, StdNistValidatorErrorEnum.STD_ERR_FCD_RT14);
    checkForSLCField(); // 14.008
    checkForFGPField(); // 14.013
    checkForPPDField(); // 14.014
    checkForPPCField(); // 14.015
    checkForOptionalButRegexField(
        RT14FieldsEnum.SHPS, StdNistValidatorErrorEnum.STD_ERR_SHPS_O_RT14, "^\\d{1,5}$");
    checkForOptionalButRegexField(
        RT14FieldsEnum.SVPS, StdNistValidatorErrorEnum.STD_ERR_SVPS_O_RT14, "^\\d{1,5}$");
    checkForAMPField(); // 14.018
    checkForOptionalButAlphaNumWithMinMaxLengthField(
        RT14FieldsEnum.COM, StdNistValidatorErrorEnum.STD_ERR_COM_RT14, 1, 26);
    checkForSEGField(); // 14.021
    checkForNQMField(); // 14.022
    checkForSQMField(); // 14.023
    checkForFQMField(); // 14.024
    checkForASEGField(); // 14.025
    checkForOptionalButNumericFieldBetween(
        RT14FieldsEnum.SCF, StdNistValidatorErrorEnum.STD_ERR_SCF_RT14, 1, 255);
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.SIF, StdNistValidatorErrorEnum.STD_ERR_SIF_RT14, SIF_ALLOWED_VALUE);
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.DMM,
        StdNistValidatorErrorEnum.STD_ERR_DMM,
        getAllowedValuesForDMM(getStandard()));
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.FAP,
        StdNistValidatorErrorEnum.STD_ERR_FAP_RT14,
        getAllowedValuesForFAP(getStandard()));

    // Conditional rules
    ruleFor(r -> r)
        .whenever(Std2011RT14Validator::hasImage)
        .withValidator(new Standard2011RT14WithImageValidator(nistOptions, RT14))
        .whenever(r -> !hasImage(r))
        .withValidator(new Standard2011RT14WithoutImageValidator(nistOptions, RT14));
  }

  protected void checkForSLCField() {
    ruleFor(r -> r)
        .must(
            r ->
                Objects.equals(
                    getFieldStringOrNull(RT14FieldsEnum.THPS, r),
                    getFieldStringOrNull(RT14FieldsEnum.TVPS, r)))
        .when(isFieldInCollection(RT14FieldsEnum.SLC, Arrays.asList("1", "2")))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                StdNistValidatorErrorEnum.STD_ERR_SLC_COHERENCE_RT14));
  }

  protected void checkForSEGField() {
    ruleFor(r -> r)
        // Should be present with right pattern, if finger combination
        .must(validateFieldSEG(getStandard()))
        .when(
            isFieldInCollection(
                RT14FieldsEnum.FGP, getFGPFingersCombinationExceptEJI(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                StdNistValidatorErrorEnum.STD_ERR_SEG_INVALID_RT14))
        // Should be absent, if no finger combination
        .must(isFieldAbsent(RT14FieldsEnum.SEG))
        .when(
            not(
                isFieldInCollection(
                    RT14FieldsEnum.FGP, getFGPFingersCombinationExceptEJI(getStandard()))))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                StdNistValidatorErrorEnum.STD_ERR_SEG_NOT_ALLOWED_RT14));
  }

  protected void checkForPPDField() {
    ruleFor(r -> r)
        // Should be present, if eji
        .must(not(isFieldAbsent(RT14FieldsEnum.PPD)).and(validateFieldPPD(getStandard())))
        .when(isEJIFingerprint())
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PPD_RT14))
        // Should be absent, if not eji
        .must(isFieldAbsent(RT14FieldsEnum.PPD))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PPD_RT14));
  }

  protected void checkForPPCField() {
    ruleFor(r -> r)
        // Should be present, if eji
        .must(from(not(isFieldAbsent(RT14FieldsEnum.PPC))).and(validateFieldPPC()))
        .when(isEJIFingerprint())
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PPC_RT14))
        // Should be absent, if not eji
        .must(isFieldAbsent(RT14FieldsEnum.PPC))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PPC_RT14));
  }

  protected void checkForFGPField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.FGP,
        StdNistValidatorErrorEnum.STD_ERR_FGP_RT14,
        // is Mandatory and is defined in collection
        not(stringEmptyOrNull()).and(validateFieldFGP(getStandard())));
    checkCustomPredicateOnField(
        RT14FieldsEnum.FGP,
        StdNistValidatorErrorEnum.STD_ERR_FGP_ONE_ALLOWED_RT14,
        // only one value allowed  - new from standard 2011
        field -> SubFieldToStringConverter.toListUsingSplitByRS(field).size() == 1);
  }

  protected void checkForAMPField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.AMP,
        StdNistValidatorErrorEnum.STD_ERR_AMP_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldAMP(getStandard())));
  }

  protected void checkForNQMField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.NQM,
        StdNistValidatorErrorEnum.STD_ERR_NQM_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldNQM(getStandard())));
  }

  protected void checkForSQMField() {
    ruleFor(r -> r)
        // match format, if present
        .must(from(isFieldAbsent(RT14FieldsEnum.SQM)).or(validateFieldSQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SQM_RT14))
        // has values compatible with others fields
        .must(validateConsistencySQM())
        .when(not(isFieldAbsent(RT14FieldsEnum.SQM)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                StdNistValidatorErrorEnum.STD_ERR_SQM_UNALLOWED_FRQP_RT14));
  }

  protected void checkForFQMField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.FQM,
        StdNistValidatorErrorEnum.STD_ERR_FQM_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldFQM(getStandard())));
  }

  protected void checkForASEGField() {
    ruleFor(r -> r)
        // match format, if present
        .must(from(isFieldAbsent(RT14FieldsEnum.ASEG)).or(validateConsistencyASEG(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_ASEG_RT14));
  }

  private static boolean hasImage(NistRecord record14) {
    return record14.getFieldImage(RT14FieldsEnum.DATA).isPresent();
  }

  public class Standard2011RT14WithImageValidator extends AbstractNistRecordValidator {

    protected Standard2011RT14WithImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      checkForMandatoryNumericFieldBetween(
          RT14FieldsEnum.HLL, StdNistValidatorErrorEnum.STD_ERR_HLL_MANDATORY_RT14, 10, 99999);
      checkForMandatoryNumericFieldBetween(
          RT14FieldsEnum.VLL, StdNistValidatorErrorEnum.STD_ERR_VLL_MANDATORY_RT14, 10, 99999);
      checkForMandatoryInCollectionField(
          RT14FieldsEnum.SLC, StdNistValidatorErrorEnum.STD_ERR_SLC_RT14, SLC_ALLOWED_VALUES);
      checkForMandatoryNumericField(
          RT14FieldsEnum.THPS, StdNistValidatorErrorEnum.STD_ERR_THPS_RT14);
      checkForMandatoryNumericField(
          RT14FieldsEnum.TVPS, StdNistValidatorErrorEnum.STD_ERR_TVPS_RT14);
      checkForMandatoryInCollectionField(
          RT14FieldsEnum.CGA,
          StdNistValidatorErrorEnum.STD_ERR_CGA_RT14,
          getAllowedValuesForCGA(getStandard()));
      checkForMandatoryNumericFieldBetween(
          RT14FieldsEnum.BPX, StdNistValidatorErrorEnum.STD_ERR_BPX_RT14, 8, 99);
    }
  }

  public static class Standard2011RT14WithoutImageValidator extends AbstractNistRecordValidator {

    protected Standard2011RT14WithoutImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      checkForEmptyField(RT14FieldsEnum.HLL, StdNistValidatorErrorEnum.STD_ERR_HLL_RT14);
      checkForEmptyField(RT14FieldsEnum.VLL, StdNistValidatorErrorEnum.STD_ERR_VLL_RT14);
      checkForEmptyField(RT14FieldsEnum.SLC, StdNistValidatorErrorEnum.STD_ERR_SLC_RT14);
      checkForEmptyField(RT14FieldsEnum.THPS, StdNistValidatorErrorEnum.STD_ERR_THPS_RT14);
      checkForEmptyField(RT14FieldsEnum.TVPS, StdNistValidatorErrorEnum.STD_ERR_TVPS_RT14);
      checkForEmptyField(RT14FieldsEnum.CGA, StdNistValidatorErrorEnum.STD_ERR_CGA_RT14);
      checkForEmptyField(RT14FieldsEnum.BPX, StdNistValidatorErrorEnum.STD_ERR_BPX_RT14);
    }
  }
}
