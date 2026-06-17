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
    checkForFieldLEN14_001();
    checkForFieldIDC14_002();
    checkForFieldIMP14_003();
    checkForFieldSRC14_004();
    checkForFieldFCD14_005();
    checkForFieldHLL14_006();
    checkForFieldVLL14_007();
    checkForFieldSLC14_008();
    checkForFieldTHPS14_009();
    checkForFieldTVPS14_010();
    checkForFieldCGA14_011();
    checkForFieldBPX14_012();
    checkForFieldFGP14_013();
    checkForFieldPPD14_014();
    checkForFieldPPC14_015();
    checkForFieldSHPS14_016();
    checkForFieldSVPS14_017();
    checkForFieldAMP14_018();
    // 14.019 reserved for future used
    checkForFieldCOM14_020();
    checkForFieldSEG14_021();
    checkForFieldNQM14_022();
    checkForFieldSQM14_023();
    checkForFieldFQM14_024();
    checkForFieldASEG14_025();
    // 14.026 - 14.029 reserved for future used
    checkForFieldDDM14_030();
    // 14.031 - 14.199 reserved for future used
    // 14.200 - 14.998 USER-DEFINED FIELDS
    checkForFieldDATA14_999();
  }

  protected void checkForFieldDATA14_999() {
    checkForMandatoryImageField(DATA);
  }

  protected void checkForFieldLEN14_001() {
    checkForMandatoryLENField(LEN);
  }

  protected void checkForFieldDDM14_030() {
    checkForOptionalButInCollectionField(DMM, getAllowedValuesForDMM(getStandard()));
  }

  protected void checkForFieldSVPS14_017() {
    checkForOptionalButNumericFieldBetween(SVPS, 1, 99999);
  }

  protected void checkForFieldSHPS14_016() {
    checkForOptionalButNumericFieldBetween(SHPS, 1, 99999);
  }

  protected void checkForFieldIDC14_002() {
    checkForMandatoryNumericFieldBetween(IDC, 0, 99);
  }

  protected void checkForFieldIMP14_003() {
    checkForMandatoryInCollectionField(IMP, getAllowedValuesForIMP(getStandard()));
  }

  protected void checkForFieldSRC14_004() {
    checkForMandatoryCharTypeAndMinMaxLengthField(SRC, ANS, 1, 36);
  }

  protected void checkForFieldFCD14_005() {
    checkForMandatoryDateField(FCD);
  }

  protected void checkForFieldHLL14_006() {
    checkForMandatoryNumericFieldBetween(HLL, 10, 99999);
  }

  protected void checkForFieldVLL14_007() {
    checkForMandatoryNumericFieldBetween(VLL, 10, 99999);
  }

  protected void checkForFieldSLC14_008() {
    checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
  }

  protected void checkForFieldTHPS14_009() {
    checkForMandatoryNumericFieldBetween(THPS, 1, 99999);
  }

  protected void checkForFieldTVPS14_010() {
    checkForMandatoryNumericFieldBetween(TVPS, 1, 99999);
  }

  protected void checkForFieldCGA14_011() {
    checkForMandatoryInCollectionField(CGA, getAllowedValuesForCGA(recordType, getStandard()));
  }

  protected void checkForFieldBPX14_012() {
    checkForMandatoryNumericFieldBetween(BPX, 0, 99);
  }

  protected void checkForFieldCOM14_020() {
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, CharacterTypeEnum.AN, 1, 128);
  }

  protected void checkForFieldFGP14_013() {
    checkCustomPredicateOnField(
        FGP, StdNistValidatorErrorEnum.STD_ERR_FGP, mandatory(validateFieldFGP(getStandard())));
  }

  protected void checkForFieldPPD14_014() {
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

  protected void checkForFieldPPC14_015() {
    ruleFor(r -> r)
        // Should be present, if eji
        .must(isFieldPresent(PPC).and(validateFieldPPC(getStandard())))
        .when(isEJIFingerprint())
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PPC, StdNistValidatorErrorEnum.STD_ERR_PPC_1))
        // Should be absent, if not eji
        .must(isFieldAbsent(PPC))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PPC, StdNistValidatorErrorEnum.STD_ERR_PPC_1));
  }

  protected void checkForFieldAMP14_018() {
    checkCustomPredicateOnField(
        AMP,
        StdNistValidatorErrorEnum.STD_ERR_AMP_RT14,
        // match format, if present
        optional(validateFieldAMP(getStandard())));
  }

  protected void checkForFieldSEG14_021() {
    checkCustomPredicateOnField(
        SEG, StdNistValidatorErrorEnum.STD_ERR_SEQ_5_ITEMS_RT14, optional(validateFieldSEG()));
  }

  protected void checkForFieldNQM14_022() {
    checkCustomPredicateOnField(
        NQM,
        StdNistValidatorErrorEnum.STD_ERR_NQM_RT14,
        // match format, if present
        optional(validateFieldNQM(getStandard())));
  }

  protected void checkForFieldFQM14_024() {
    checkCustomPredicateOnField(
        FQM,
        StdNistValidatorErrorEnum.STD_ERR_FQM_RT14,
        // match format, if present
        optional(validateFieldFQM(getStandard())));
  }

  protected void checkForFieldSQM14_023() {
    ruleFor(r -> r)
        // match format, if present
        .must(isFieldAbsent(SQM).or(validateFieldSQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQM, StdNistValidatorErrorEnum.STD_ERR_SQM_RT14));
  }

  protected void checkForFieldASEG14_025() {
    checkCustomPredicateOnField(
        ASEG,
        StdNistValidatorErrorEnum.STD_ERR_ASEG_RT14,
        // match format, if present
        optional(validateFieldASEG(getStandard())));
  }

  protected Predicate<String> validateFieldSEG() {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(subfield -> SubFieldToStringConverter.toList(subfield).size() == 5);
    };
  }

  protected Predicate<String> validateFieldASEG(NistStandardEnum nistStandardEnum) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isASEGOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandardEnum));
    };
  }

  protected boolean isASEGOneFingerValid(List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() >= 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0))
        && isNumberBetween(3, 99).test(items.get(1)); // NOP
  }
}
