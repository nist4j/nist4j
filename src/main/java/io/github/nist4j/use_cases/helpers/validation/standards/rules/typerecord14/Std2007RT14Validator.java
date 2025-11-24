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

import static io.github.nist4j.enums.CharacterTypeEnum.ANS;
import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import java.util.function.Predicate;

public class Std2007RT14Validator extends AbstractStdRT14Validator {

  @SuppressWarnings("SameReturnValue")
  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2007;
  }

  protected Std2007RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2007RT14Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @SuppressWarnings("DuplicatedCode")
  @Override
  public void rules() {
    // Common rules on fields
    checkForMandatoryLENField(LEN);
    checkForMandatoryNumericFieldBetween(IDC, 0, 99);
    checkForMandatoryInCollectionField(IMP, getAllowedValuesForIMP(getStandard()));
    checkForMandatoryCharTypeAndMinMaxLengthField(SRC, ANS, 1, 36);
    checkForMandatoryDateField(FCD);
    checkForMandatoryNumericFieldBetween(HLL, 1, 99999);
    checkForMandatoryNumericFieldBetween(VLL, 1, 99999);
    checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
    checkForMandatoryNumericFieldBetween(THPS, 1, 99999);
    checkForMandatoryNumericFieldBetween(TVPS, 1, 99999);
    checkForMandatoryInCollectionField(CGA, getAllowedValuesForCGA(getStandard()));
    checkForMandatoryNumericFieldBetween(BPX, 0, 99);
    checkForFGPField();
    checkForPPDField(); // 14.014
    checkForPPCField(); // 14.015
    checkForOptionalButNumericFieldBetween(SHPS, 1, 99999);
    checkForOptionalButNumericFieldBetween(SVPS, 1, 99999);
    checkForAMPField(); // 14.018
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, CharacterTypeEnum.AN, 1, 128);
    checkForSEGField();
    checkForNQMField();
    checkForFQMField();
    checkForSQMField();
    checkForASEGField();
    checkForOptionalButInCollectionField(DMM, getAllowedValuesForDMM(getStandard()));
  }

  protected void checkForFGPField() {
    checkCustomPredicateOnField(
        FGP, StdNistValidatorErrorEnum.STD_ERR_FGP, mandatory(validateFieldFGP(getStandard())));
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
        .must(isFieldPresent(PPC).and(validateFieldPPC()))
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

  protected void checkForAMPField() {
    checkCustomPredicateOnField(
        AMP,
        StdNistValidatorErrorEnum.STD_ERR_AMP_RT14,
        // match format, if present
        optional(validateFieldAMP(getStandard())));
  }

  protected void checkForSEGField() {
    checkCustomPredicateOnField(
        SEG, StdNistValidatorErrorEnum.STD_ERR_SEQ_5_ITEMS_RT14, optional(validateFieldSEG()));
  }

  protected void checkForNQMField() {
    checkCustomPredicateOnField(
        NQM,
        StdNistValidatorErrorEnum.STD_ERR_NQM_RT14,
        // match format, if present
        optional(validateFieldNQM(getStandard())));
  }

  protected void checkForFQMField() {
    checkCustomPredicateOnField(
        FQM,
        StdNistValidatorErrorEnum.STD_ERR_FQM_RT14,
        // match format, if present
        optional(validateFieldFQM(getStandard())));
  }

  protected void checkForSQMField() {
    ruleFor(r -> r)
        // match format, if present
        .must(isFieldAbsent(SQM).or(validateFieldSQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQM, StdNistValidatorErrorEnum.STD_ERR_SQM_RT14));
  }

  protected void checkForASEGField() {
    checkCustomPredicateOnField(
        ASEG,
        StdNistValidatorErrorEnum.STD_ERR_ASEG_RT14,
        // match format, if present
        optional(validateFieldASEG(getStandard())));
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
