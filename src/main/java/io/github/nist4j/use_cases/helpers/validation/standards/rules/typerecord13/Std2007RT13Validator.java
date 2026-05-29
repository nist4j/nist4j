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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13;

import static io.github.nist4j.enums.CharacterTypeEnum.AN;
import static io.github.nist4j.enums.CharacterTypeEnum.ANS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.TEN_FINGERS;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringInCollection;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.image.NistRefJointImageSegmentsTipAndFingerViewCodeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import java.util.function.Predicate;

public class Std2007RT13Validator extends AbstractStdRT13Validator {

  public Std2007RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2007RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2007;
  }

  @Override
  public void rules() {
    checkForFieldLEN13_001();
    checkForFieldIDC13_002();
    checkForFieldIMP13_003();
    checkForFieldSRC13_004();
    checkForFieldLCD13_005();
    checkForFieldHLL13_006();
    checkForFieldVLL13_007();
    checkForFieldSLC13_008();
    checkForFieldTHPS13_009();
    checkForFieldTVPS13_010();
    checkForFieldCGA13_011();
    checkForFieldBPX13_012();
    checkForFieldFGP13_013();
    checkForFieldSPD13_014();
    checkForFieldPPC13_015();
    checkForFieldSHPS13_016();
    checkForFieldVHPS13_1017();
    /*13.018 - 13.019 - RESERVED FOR FUTURE DEFINITION */
    checkForFieldCOM13_020();
    /*13.021 - 13.023 - RESERVED FOR FUTURE DEFINITION */
    checkForFieldLQM13_024();
    /*13.021 - 13.199 - RESERVED FOR FUTURE DEFINITION */
    /*13.200 - 13.998 - USER-DEFINED FIELDS */
    checkForFieldDATA13_999();
  }

  protected void checkForFieldSPD13_014() {
    // can be present, if eji value
    ruleFor(r -> r)
        .must(validateFieldSPD(getStandard()))
        .when(isEJIFingerprint().and(isFieldPresent(SPD)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SPD, StdNistValidatorErrorEnum.STD_ERR_SPD_1));
    // Should be absent, if not eji
    ruleFor(r -> r)
        .must(isFieldAbsent(SPD))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SPD, StdNistValidatorErrorEnum.STD_ERR_SPD_2));
  }

  protected Predicate<NistRecord> validateFieldSPD(NistStandardEnum nistStandard) {
    return r -> {
      List<List<String>> subFields =
          SubFieldToStringConverter.toListOfList(getFieldStringOrNull(SPD, r));
      return subFields.stream().allMatch(subfield -> isSPDOneFingerValid(subfield, nistStandard));
    };
  }

  protected boolean isSPDOneFingerValid(List<String> items, NistStandardEnum nistStandard) {
    List<String> allowedFICValues =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(recordType, "FIC"),
            nistStandard);
    return items.size() == 2
        && stringInCollection(getFGPUnitaryFingers(nistStandard)).test(items.get(0)) // PDF
        && stringInCollection(allowedFICValues).test(items.get(1)) // FIC
    ;
  }

  protected List<String> getFGPUnitaryFingers(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(TEN_FINGERS, nistStandardEnum);
  }

  protected void checkForFieldDATA13_999() {
    checkForMandatoryImageField(DATA);
  }

  protected void checkForFieldCOM13_020() {
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, AN, 1, 128);
  }

  protected void checkForFieldVHPS13_1017() {
    checkForOptionalButNumericFieldBetween(SVPS, 1, 99999);
  }

  protected void checkForFieldSHPS13_016() {
    checkForOptionalButNumericFieldBetween(SHPS, 1, 99999);
  }

  protected void checkForFieldBPX13_012() {
    checkForMandatoryNumericFieldBetween(BPX, 8, 99);
  }

  protected void checkForFieldTVPS13_010() {
    checkForMandatoryNumericFieldBetween(TVPS, 1, 99999);
  }

  protected void checkForFieldTHPS13_009() {
    checkForMandatoryNumericFieldBetween(THPS, 1, 99999);
  }

  protected void checkForFieldSLC13_008() {
    checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
  }

  protected void checkForFieldVLL13_007() {
    checkForMandatoryNumericFieldBetween(VLL, 1, 99999);
  }

  protected void checkForFieldHLL13_006() {
    checkForMandatoryNumericFieldBetween(HLL, 1, 99999);
  }

  protected void checkForFieldLCD13_005() {
    checkForMandatoryDateField(LCD);
  }

  protected void checkForFieldSRC13_004() {
    checkForMandatoryCharTypeAndMinMaxLengthField(SRC, ANS, 1, 36);
  }

  protected void checkForFieldIDC13_002() {
    checkForMandatoryNumericFieldBetween(IDC, 0, 99);
  }

  protected void checkForFieldLEN13_001() {
    checkForMandatoryLENField(LEN);
  }
}
