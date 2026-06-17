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

import static io.github.nist4j.enums.CharacterTypeEnum.*;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.PredicateBuilder.from;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

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

public class Std2011RT14Validator extends Std2007RT14Validator {

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
    checkForFieldLEN14_001();
    checkForFieldIDC14_002();
    // IMP 14_003() in sub validator
    checkForFieldSRC14_004();
    checkForFieldFCD14_005();
    // HLL 14_006 in sub Validator
    // VLL 14_007 in sub Validator
    checkForFieldSLC14_008();
    // THPS 14_009 in sub Validator
    // TVPS 14_010 in sub Validator
    // CGA 14_011 in sub Validator
    // BPX 14_012 in sub Validator
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
    checkForFieldSCF14_026(); // since 2011
    checkForFieldSIF14_027(); // since 2011
    // 14.028 - 14.029 reserved for future used
    checkForFieldDDM14_030();
    checkForFieldFAP14_031(); // since 2011
    // 14.032 - 14.199 reserved for future used
    // 14.200 - 14.900 USER-DEFINED FIELDS
    // 14.901 reserved for future used
    checkForFieldANN14_902(); // since 2011
    checkForFieldDUI14_903(); // since 2011
    checkForFieldMMS14_904(); // since 2011
    // 14.905 - 14.992 reserved for future used
    checkForFieldSAN14_993(); // since 2011
    // 14.994 reserved for future used
    checkForFieldASC14_995(); // since 2011
    checkForFieldHAS14_996(); // since 2011
    checkForFieldSOR14_997(); // since 2011
    checkForFieldGEO14_998(); // since 2011
    // DATA 14_999 in sub Validator

    // Conditional rules
    ruleFor(r -> r)
        .whenever(Std2011RT14Validator::hasImage)
        .withValidator(new Std2011RT14WithImageValidator(nistOptions, RT14))
        .whenever(r -> !hasImage(r))
        .withValidator(new Std2011RT14WithoutImageValidator(nistOptions, RT14));
  }

  @Override
  protected void checkForFieldDATA14_999() {
    // since 2011 RT14 can have no image data
    checkForOptionalButImageField(DATA);
  }

  protected void checkForFieldGEO14_998() {
    // new in 2011
    checkForGenericFieldGEO_998(GEO);
  }

  protected void checkForFieldSOR14_997() {
    // new in 2011
    checkForGenericFieldSOR_997(SOR);
  }

  protected void checkForFieldHAS14_996() {
    // new in 2011
    checkForGenericFieldHAS_996(HAS);
  }

  protected void checkForFieldASC14_995() {
    // new in 2011
    checkForOptionalButCharTypeAndMinLengthField(ASC, N, 1);
  }

  protected void checkForFieldSAN14_993() {
    // new in 2011
    checkForOptionalButCharTypeAndMinMaxLengthField(SAN, U, 1, 125);
  }

  protected void checkForFieldMMS14_904() {
    // new in 2011
    checkForOptionalButCharTypeAndMinLengthField(MMS, U, 1);
  }

  protected void checkForFieldDUI14_903() {
    // new in 2011
    checkForGenericFieldDUI_903(DUI);
  }

  protected void checkForFieldANN14_902() {
    // new in 2011
    checkForOptionalButCharTypeAndMinLengthField(ANN, U, 1);
  }

  protected void checkForFieldFAP14_031() {
    // new in 2011
    checkForOptionalButInCollectionField(FAP, getAllowedValuesForFAP(getStandard()));
  }

  protected void checkForFieldSIF14_027() {
    // new in 2011
    checkForOptionalButInCollectionField(SIF, SIF_ALLOWED_VALUE);
  }

  protected void checkForFieldSCF14_026() {
    // new in 2011
    checkForOptionalButNumericFieldBetween(SCF, 1, 255);
  }

  @Override
  protected void checkForFieldCOM14_020() {
    // passed to Unicode
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, U, 1, 126);
  }

  @Override
  protected void checkForFieldSRC14_004() {
    // passed to Unicode
    checkForMandatoryCharTypeAndMinLengthField(SRC, U, 1);
  }

  protected void checkForFieldSLC14_008() {
    ruleFor(r -> r)
        .must(r -> Objects.equals(getFieldStringOrNull(THPS, r), getFieldStringOrNull(TVPS, r)))
        .when(isFieldInCollection(SLC, Arrays.asList("1", "2")))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SLC, StdNistValidatorErrorEnum.STD_ERR_SLC_COHERENCE_RT14));
  }

  protected void checkForFieldSEG14_021() {
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
        .must(from(not(isFieldAbsent(PPC))).and(validateFieldPPC(getStandard())))
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

  protected void checkForFieldFGP14_013() {
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

  protected void checkForFieldAMP14_018() {
    checkCustomPredicateOnField(
        AMP,
        StdNistValidatorErrorEnum.STD_ERR_AMP_RT14,
        // match format, if present
        optional(validateFieldAMP(getStandard())));
  }

  protected void checkForFieldNQM14_022() {
    checkCustomPredicateOnField(
        NQM,
        StdNistValidatorErrorEnum.STD_ERR_NQM_RT14,
        // match format, if present
        optional(validateFieldNQM(getStandard())));
  }

  protected void checkForFieldSQM14_023() {
    ruleFor(r -> r)
        // match format, if present
        .must(from(isFieldAbsent(SQM)).or(validateFieldSQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQM, StdNistValidatorErrorEnum.STD_ERR_SQM_RT14));
    ruleFor(r -> r)
        // has values compatible with others fields
        .must(validateConsistencySQM())
        .when(not(isFieldAbsent(SQM)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQM, StdNistValidatorErrorEnum.STD_ERR_SQM_UNALLOWED_FRQP_RT14));
  }

  protected void checkForFieldFQM14_024() {
    checkCustomPredicateOnField(
        FQM,
        StdNistValidatorErrorEnum.STD_ERR_FQM_RT14,
        // match format, if present
        optional(validateFieldFQM(getStandard())));
  }

  protected void checkForFieldASEG14_025() {
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

  public class Std2011RT14WithImageValidator extends AbstractNistRecordValidator {

    protected Std2011RT14WithImageValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      // calling parent class method generates errors
      subCheckForFieldIMP14_003();
      subCheckForFieldVLL14_007();
      subCheckForFieldHLL14_006();
      subCheckForFieldSLC14_008();
      subCheckForFieldTHPS14_009();
      subCheckForFieldTVPS14_010();
      subCheckForFieldCGA14_011();
      subCheckForFieldBPX14_012();
    }

    protected void subCheckForFieldBPX14_012() {
      checkForMandatoryNumericFieldBetween(BPX, 8, 99);
    }

    protected void subCheckForFieldCGA14_011() {
      checkForMandatoryInCollectionField(CGA, getAllowedValuesForCGA(recordType, getStandard()));
    }

    protected void subCheckForFieldTVPS14_010() {
      checkForMandatoryNumericFieldBetween(TVPS, 1, 99999);
    }

    protected void subCheckForFieldTHPS14_009() {
      checkForMandatoryNumericFieldBetween(THPS, 1, 99999);
    }

    protected void subCheckForFieldSLC14_008() {
      checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
    }

    protected void subCheckForFieldHLL14_006() {
      checkForMandatoryNumericFieldBetween(VLL, 10, 99999);
    }

    private void subCheckForFieldVLL14_007() {
      checkForMandatoryNumericFieldBetween(HLL, 10, 99999);
    }

    protected void subCheckForFieldIMP14_003() {
      checkForMandatoryInCollectionField(RT14FieldsEnum.IMP, getAllowedValuesForIMP(getStandard()));
    }
  }

  @SuppressWarnings("InnerClassMayBeStatic")
  public class Std2011RT14WithoutImageValidator extends AbstractNistRecordValidator {

    protected Std2011RT14WithoutImageValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
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
