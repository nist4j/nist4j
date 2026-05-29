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
import static io.github.nist4j.enums.records.RT13FieldsEnum.IMP;
import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.fp.NistRefAmputationBandagedFPEnum.SR;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.TEN_FINGERS;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.UNKNOWN_FINGER;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.CONTACTLESS_CAPTURE;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldEquals;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldPresent;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.ref.fp.NistRefAmputationBandagedFPEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import io.github.nist4j.enums.ref.fp.NistRefMissingDetailReasonEnum;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Std2025RT14Validator extends Std2015RT14Validator {

  protected Std2025RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2025RT14Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2025;
  }

  @Override
  public void rules() {
    // Common rules on fields
    checkForFieldLEN14_001();
    checkForFieldIDC14_002();
    // IMP 14_003 in sub Validator
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
    // SHPS 14_016 in sub validator
    // SVPS 14_017 in sub validator
    checkForFieldAMP14_018();
    checkForFieldCSP14_019(); // Since 2025
    checkForFieldCOM14_020();
    checkForFieldSEG14_021();
    checkForFieldNQM14_022(); // Since 2025 this field is legacy use
    checkForFieldSQM14_023();
    checkForFieldFQM14_024();
    checkForFieldASEG14_025();
    checkForFieldSCF14_026();
    checkForFieldSIF14_027();
    // 14.028 - 14.028 reserved for future used
    checkForFieldFQC14_029(); // since 2025
    checkForFieldDDM14_030();
    checkForFieldFAP14_031();
    // 14.032 - 14.045 reserved for future used
    checkForFieldSUB14_046();
    checkForFieldCON14_047();
    // 14.048 - 14.198 reserved for future used
    checkForFieldBRI14_199(); // Since 2025 - New field
    // 14.200 - 14.900 USER-DEFINED FIELDS
    checkForFieldFCT14_901();
    checkForFieldANN14_902();
    checkForFieldDUI14_903();
    checkForFieldMMS14_904();
    // 14.905 - 14.992 reserved for future used
    checkForFieldSAN14_993();
    // 14.994 reserved for future used
    checkForFieldASC14_995();
    checkForFieldHAS14_996();
    checkForFieldSOR14_997();
    checkForFieldGEO14_998();

    // DATA 14_999 in sub Validator

    // Conditional rules
    ruleFor(r -> r)
        .whenever(r -> hasImageOrExternalFile(r))
        .withValidator(new Std2025RT14WithImageValidator(nistOptions, RT14))
        .whenever(r -> !hasImageOrExternalFile(r))
        .withValidator(new Std2025RT14WithoutImageValidator(nistOptions, RT14));
  }

  @Override
  protected void checkForFieldFCT14_901() {
    // since 2025 - New mandatory condition, required for contactless fingerprints
    Predicate<NistRecord> conditionMandatoryWhenIMPequals43 =
        isFieldPresent(IMP).and(isFieldEquals(IMP, CONTACTLESS_CAPTURE.getCode()));
    checkForGenericFieldFCT_901(FCT, IMP, getStandard(), conditionMandatoryWhenIMPequals43);
  }

  protected void checkForFieldBRI14_199() {
    // Since 2025
    checkForGenericFieldBRI_199(RT14FieldsEnum.BRI);
  }

  protected void checkForFieldFQC14_029() {
    // Since 2025
    List<NistRefFrictionRidgePositionEnum> allowedFGP =
        TEN_FINGERS.stream()
            .filter(fgp -> !UNKNOWN_FINGER.equals(fgp))
            .collect(Collectors.toList());
    checkForGenericFieldFQC_029(RT14FieldsEnum.FQC, allowedFGP, getStandard());
  }

  protected void checkForFieldCSP14_019() {
    // Since 2025
    checkForGenericFieldCSP_xxx(CSP, BPX, getStandard());
  }

  @Override
  protected boolean isQualityOneFingerValid(List<String> items, NistStandardEnum nistStandardEnum) {
    // Since 2025 - 14.023 SQM & 14.024 FQM :
    // - Added new information items to describe algorithms;
    // - Removed the upper limit of 45 subfields
    return items.size() >= 4
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0)) // FRMP
        && stringMatches("^(([1-9]?\\d{1})|100|254|255)$").test(items.get(1)) // QVU
        && isCharTypeWithMinMaxLength(H, 4, 4).test(items.get(2)) // QAV
        && isNumberBetween(1, 65535).test(items.get(3)) // QAP
        && (items.size() <= 4 || isCharTypeWithMinLength(U, 1).test(items.get(4))) // QPV
        && (items.size() <= 5 || isCharTypeWithMinLength(U, 1).test(items.get(5))) // QCM
        && items.size() <= 6;
  }

  protected boolean isAMPOneFingerValid(List<String> items, NistStandardEnum nistStandard) {
    // since 2025 on 14.018 AMP
    // - Added clarifying language on amputation codes and clarified legacy “placeholder” image.
    // - Added new information item to provide the reason for the missing friction ridge detail

    final List<String> allowedValuesForABC =
        findCodesAllowedByStandard(NistRefAmputationBandagedFPEnum.values(), nistStandard);
    final List<String> allowedValuesForMDC =
        findCodesAllowedByStandard(NistRefMissingDetailReasonEnum.values(), nistStandard);

    return items.size() >= 2 // 2 parameters are required
        && stringInCollection(getFGPUnitaryFingers(nistStandard)).test(items.get(0))
        && stringInCollection(allowedValuesForABC).test(items.get(1))
        && (items.size() <= 2
            || // param 3 is optional
            (stringInCollection(allowedValuesForMDC).test(items.get(2)))
                && !SR.getCode().equals(items.get(1)) // SR is not compatible with param3
        )
        && items.size() <= 3; // not more than 3 params
  }

  public class Std2025RT14WithImageValidator extends Std2015RT14WithImageValidator {

    @SuppressWarnings("SameParameterValue")
    protected Std2025RT14WithImageValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void rules() {
      super.rules();
    }
  }

  public class Std2025RT14WithoutImageValidator extends Std2011RT14WithoutImageValidator {
    @SuppressWarnings("SameParameterValue")
    protected Std2025RT14WithoutImageValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void rules() {
      super.rules();
    }
  }
}
