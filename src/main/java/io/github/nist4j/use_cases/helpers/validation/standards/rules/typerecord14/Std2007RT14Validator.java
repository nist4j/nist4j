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
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringInCollection;
import static io.github.nist4j.use_cases.helpers.builders.validation.NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates.isNumberBetween;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import java.util.function.Predicate;

public class Std2007RT14Validator extends AbstractStdRT14Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2007;
  }

  protected Std2007RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2007RT14Validator(NistOptions nistOptions) {
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
    checkForMandatoryAlphaNumWithMinMaxLengthField(
        RT14FieldsEnum.SRC, StdNistValidatorErrorEnum.STD_ERR_SRC_36, 1, 36);
    checkForMandatoryDateField(RT14FieldsEnum.FCD, StdNistValidatorErrorEnum.STD_ERR_FCD_RT14);
    checkForMandatoryNumericFieldBetween(
        RT14FieldsEnum.HLL, StdNistValidatorErrorEnum.STD_ERR_HLL_MANDATORY_RT14, 1, 99999);
    checkForMandatoryNumericFieldBetween(
        RT14FieldsEnum.VLL, StdNistValidatorErrorEnum.STD_ERR_VLL_MANDATORY_RT14, 1, 99999);
    checkForMandatoryInCollectionField(
        RT14FieldsEnum.SLC,
        StdNistValidatorErrorEnum.STD_ERR_SLC_MANDATORY_RT14,
        SLC_ALLOWED_VALUES);
    checkForMandatoryNumericField(
        RT14FieldsEnum.THPS, StdNistValidatorErrorEnum.STD_ERR_THPS_MANDATORY_RT14);
    checkForMandatoryNumericField(
        RT14FieldsEnum.TVPS, StdNistValidatorErrorEnum.STD_ERR_TVPS_MANDATORY_RT14);
    checkForMandatoryInCollectionField(
        RT14FieldsEnum.CGA,
        StdNistValidatorErrorEnum.STD_ERR_CGA_MANDATORY_RT14,
        getAllowedValuesForCGA(getStandard()));
    checkForMandatoryNumericField(
        RT14FieldsEnum.BPX, StdNistValidatorErrorEnum.STD_ERR_BPX_MANDATORY_RT14);
    checkForFGPField();
    checkForPPDField(); // 14.014
    checkForPPCField(); // 14.015
    checkForOptionalButRegexField(
        RT14FieldsEnum.SHPS, StdNistValidatorErrorEnum.STD_ERR_SHPS_O_RT14, "^\\d{1,5}$");
    checkForOptionalButRegexField(
        RT14FieldsEnum.SVPS, StdNistValidatorErrorEnum.STD_ERR_SVPS_O_RT14, "^\\d{1,5}$");
    checkForAMPField(); // 14.018
    checkForOptionalButRegexField(
        RT14FieldsEnum.COM, StdNistValidatorErrorEnum.STD_ERR_COM_RT14, REGEXP_ANS_ANY_LENGTH);
    checkForSEGField();
    checkForNQMField();
    checkForFQMField();
    checkForSQMField();
    checkForASEGField();
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.DMM,
        StdNistValidatorErrorEnum.STD_ERR_DMM,
        getAllowedValuesForDMM(getStandard()));
  }

  protected void checkForFGPField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.FGP,
        StdNistValidatorErrorEnum.STD_ERR_FGP_RT14,
        // is Mandatory and is defined in collection
        not(stringEmptyOrNull()).and(validateFieldFGP(getStandard())));
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
        .must(not(isFieldAbsent(RT14FieldsEnum.PPC)).and(validateFieldPPC()))
        .when(isEJIFingerprint())
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PPC_RT14))
        // Should be absent, if not eji
        .must(isFieldAbsent(RT14FieldsEnum.PPC))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PPC_RT14));
  }

  protected void checkForAMPField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.AMP,
        StdNistValidatorErrorEnum.STD_ERR_AMP_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldAMP(getStandard())));
  }

  protected void checkForSEGField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.SEG,
        StdNistValidatorErrorEnum.STD_ERR_SEQ_5_ITEMS_RT14,
        stringEmptyOrNull().or(validateFieldSEG()));
  }

  protected void checkForNQMField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.NQM,
        StdNistValidatorErrorEnum.STD_ERR_NQM_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldNQM(getStandard())));
  }

  protected void checkForFQMField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.FQM,
        StdNistValidatorErrorEnum.STD_ERR_FQM_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldFQM(getStandard())));
  }

  protected void checkForSQMField() {
    ruleFor(r -> r)
        // match format, if present
        .must(isFieldAbsent(RT14FieldsEnum.SQM).or(validateFieldSQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SQM_RT14));
  }

  protected void checkForASEGField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.ASEG,
        StdNistValidatorErrorEnum.STD_ERR_ASEG_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldASEG(getStandard())));
  }

  private static Predicate<String> validateFieldSEG() {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(subfield -> SubFieldToStringConverter.toList(subfield).size() == 5);
    };
  }

  private static Predicate<String> validateFieldASEG(NistStandardEnum nistStandardEnum) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isASEGOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandardEnum));
    };
  }

  private static boolean isASEGOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() >= 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0))
        && isNumberBetween(3, 99).test(items.get(1)); // NOP
  }
}
