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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord10;

import static io.github.nist4j.enums.CharacterTypeEnum.ANS;
import static io.github.nist4j.enums.CharacterTypeEnum.U;
import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.image.NistRefFacialIMTImageTypeEnum.FACE;
import static io.github.nist4j.enums.ref.image.NistRefFacialIMTImageTypeEnum.TATTOO;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_PXS_LEGACY;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.StringCondition.areEquals;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isHexaCodeWithLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import io.github.nist4j.enums.ref.image.*;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Std2007RT10Validator extends AbstractStdRT10Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2007;
  }

  protected Std2007RT10Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2007RT10Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  public void rules() {
    checkForFieldLEN10_001();
    checkForFieldIDC10_002();
    checkForFieldIMT10_003();
    checkForFieldSRC10_004();
    checkForFieldPHD10_005();
    checkForFieldHLL10_006();
    checkForFieldVLL10_007();
    checkForFieldSLC10_008();
    checkForFieldHPS_LEGACY10_009();
    checkForFieldVPS_LEGACY10_010();
    checkForFieldCGA10_011();
    checkForFieldCSP10_012();
    checkForFieldSAP10_013();
    checkForFieldSHPS10_016();
    checkForFieldSVPS10_017();
    checkForFieldPOS10_020();
    checkForFieldPOA10_021();
    checkForFieldPXS_LEGACY10_022();
    checkForFieldPAS10_023();
    checkForFieldSQS10_024();
    checkForFieldSPA10_025();
    checkForFieldSXS10_026();
    checkForFieldSEC10_027();
    checkForFieldSHC10_028();
    checkForFieldFFP10_029();
    checkForFieldDMM10_030();
    checkForFieldSMT10_040();
    checkForFieldSMS10_041();
    checkForFieldSMD10_042();
    checkForFieldTCL10_043();
    checkForFieldDATA10_999();
  }

  protected void checkForFieldTCL10_043() {
    checkCustomPredicateOnField(
        TCL, StdNistValidatorErrorEnum.STD_ERR_TCL, validateFieldCOL(getStandard()));
  }

  protected Predicate<String> validateFieldCOL(NistStandardEnum nistStandard) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isEmpty(items) || items.stream().allMatch(validateFieldCOLItem(nistStandard));
    };
  }

  protected Predicate<String> validateFieldCOLItem(NistStandardEnum nistStandard) {
    return optional(
        stringInCollection(findCodesAllowedByStandard(NistRefColorsEnum.values(), nistStandard)));
  }

  protected void checkForFieldSMS10_041() {
    checkCustomPredicateOnField(SMS, StdNistValidatorErrorEnum.STD_ERR_SMS, validateFieldSMS());
  }

  protected Predicate<String> validateFieldSMS() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toItems(field);
      return isEmpty(items)
          || (items.size() == 2
              && isNumberBetween(0, 999).test(items.get(0))
              && isNumberBetween(0, 999).test(items.get(1)));
    };
  }

  protected void checkForFieldDMM10_030() {
    checkForOptionalButCharTypeAndMinMaxLengthField(DMM, ANS, 8, 11);
  }

  protected void checkForFieldFFP10_029() {
    checkCustomPredicateOnField(FFP, StdNistValidatorErrorEnum.STD_ERR_FFP, validateFieldFFP());
  }

  protected Predicate<String> validateFieldFFP() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldFFPItem());
    };
  }

  @SuppressWarnings("DuplicatedCode")
  protected Predicate<List<String>> validateFieldFFPItem() {
    return items -> {
      return items.size() == 4
          && items.get(0).length() == 1
          && stringInCollection(asList("1", "2")).test(items.get(0)) // FPT Feature point type
          && stringSizeBetween(1, 5).test(items.get(1))
          && (stringEquals("1").test(items.get(0)) // FPC Feature point code n.n or nn.nn
                  && stringMatches("^\\d{1,2}\\.\\d{1,2}$").test(items.get(1))
              || stringEquals("2").test(items.get(0)) // FPC Feature point code a or aa or aaa
                  && stringMatches("^[a-z]{1,4}$").test(items.get(1)))
          && isNumberBetween(0, 99999).test(items.get(2)) // HCX X coordinate
          && isNumberBetween(0, 99999).test(items.get(3)) // HCY Y coordinate
      ;
    };
  }

  protected void checkForFieldSHC10_028() {
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(SHC, validateFieldSHC(getStandard())))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SHC, StdNistValidatorErrorEnum.STD_ERR_SHC));
  }

  private Predicate<String> validateFieldSHC(NistStandardEnum nistStandard) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return isNotEmpty(items)
          && items.stream()
              .allMatch(
                  stringInCollection(
                      findCodesAllowedByStandard(
                          NistRefSubjectHairColorEnum.values(), nistStandard)));
    };
  }

  protected void checkForFieldSEC10_027() {
    ruleFor(r -> r)
        .must(
            handlePredicateOnTextField(
                SEC,
                stringInCollection(
                    findCodesAllowedByStandard(
                        NistRefSubjectEyeColorEnum.values(), getStandard()))))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SEC, StdNistValidatorErrorEnum.STD_ERR_SEC));
  }

  protected void checkForFieldSXS10_026() {
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(SXS, mandatory(validateFieldSXS(getStandard()))))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SXS, StdNistValidatorErrorEnum.STD_ERR_SXS));
  }

  protected Predicate<String> validateFieldSXS(NistStandardEnum nistStandard) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toListUsingSplitByRS(field);
      INistReferentielEnum[] allowValues = NistRefSubjectFacialDescriptionEnum.values();
      return isNotEmpty(items)
          && items.stream()
              .allMatch(stringInCollection(findCodesAllowedByStandard(allowValues, nistStandard)));
    };
  }

  /** if POS==D then SPA contains list of */
  protected void checkForFieldSPA10_025() {
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(SPA, validateFieldSPA()))
        .when(isFieldEquals(POS, NistRefSubjectPoseEnum.DETERMINED_3D_POSE.getCode()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SPA, StdNistValidatorErrorEnum.STD_ERR_SPA));
  }

  protected Predicate<String> validateFieldSPA() {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toList(field);
      return isNotEmpty(subFields)
          && subFields.size() == 6
          && isNumeric().and(isNumberBetween(-180, 180)).test(subFields.get(0)) // YAW
          && isNumeric().and(isNumberBetween(-90, 90)).test(subFields.get(1)) // PIT
          && isNumeric().and(isNumberBetween(-180, 180)).test(subFields.get(2)) // ROL
          && isNumeric().and(isNumberBetween(0, 90)).test(subFields.get(3)) // YAWU
          && isNumeric().and(isNumberBetween(0, 90)).test(subFields.get(4)) // PITU
          && isNumeric().and(isNumberBetween(0, 90)).test(subFields.get(5)); // ROLU
    };
  }

  /** If SQS present IMT=='FACE' must be set */
  protected void checkForFieldSQS10_024() {
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(SQS, validateFieldSQS()))
        .when(not(isFieldAbsent(SQS)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SQS, StdNistValidatorErrorEnum.STD_ERR_SQS));
  }

  protected Predicate<String> validateFieldSQS() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldSQSItems());
    };
  }

  protected Predicate<List<String>> validateFieldSQSItems() {
    return items -> {
      return items.size() == 3
          // image quality score 0 to 100 or 255 in case of error 254 not tested
          && isNumeric()
              .and(isNumberBetween(0, 100).or(isNumberBetween(254, 255)))
              .test(items.get(0))
          // ID of the vendor of the quality algorithm used
          && isHexaCodeWithLength(4).test(items.get(1))
          && isNumeric().and(isNumberBetween(1, 65535)).test(items.get(2)); // Product code
    };
  }

  protected void checkForFieldPAS10_023() {
    // is Optional with SAP>40
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(PAS, validateFieldPAS(getStandard())))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, PAS, StdNistValidatorErrorEnum.STD_ERR_PAS));
  }

  private Predicate<String> validateFieldPAS(NistStandardEnum nistStandard) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toItems(field);
      return isEmpty(items)
          || (items.size() == 1
                  && stringInCollection(getAllowedValuesForPAS(nistStandard)).test(items.get(0)))
              && !stringEquals(NistRefAcquisitionSourceTypeEnum.VENDOR.getCode()).test(items.get(0))
          || (items.size() == 2
              && stringEquals(NistRefAcquisitionSourceTypeEnum.VENDOR.getCode()).test(items.get(0))
              && stringSizeBetween(1, 64).test(items.get(1)));
    };
  }

  protected void checkForFieldPXS_LEGACY10_022() {
    checkCustomPredicateOnField(
        PXS_LEGACY,
        STD_ERR_PXS_LEGACY,
        not(stringEmptyOrNull()).or(validateFieldPXS_LEGACY(getStandard())));
  }

  protected Predicate<String> validateFieldPXS_LEGACY(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return isEmpty(subFields)
          || subFields.stream()
              .allMatch(
                  pxsItem ->
                      stringInCollection(
                              findCodesAllowedByStandard(
                                  NistRefPhotoDescriptorsEnum.values(), nistStandard))
                          .test(pxsItem));
    };
  }

  protected void checkForFieldPOA10_021() {
    // is Optional with POS=='A'
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(POA, optional((isNumberBetween(-180, 180)))))
        .when(isFieldEquals(POS, NistRefSubjectPoseEnum.ANGLED_POSE.getCode()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, POA, StdNistValidatorErrorEnum.STD_ERR_POA));
  }

  protected void checkForFieldPOS10_020() {
    checkForOptionalButInCollectionField(POS, getAllowedValuesForPOS(getStandard()));
  }

  protected void checkForFieldSVPS10_017() {
    checkForOptionalButNumericFieldBetween(SVPS, 1, 99999);
  }

  protected void checkForFieldSHPS10_016() {
    checkForOptionalButNumericFieldBetween(SHPS, 1, 99999);
  }

  protected void checkForFieldDATA10_999() {
    checkForMandatoryImageField(DATA);
  }

  protected void checkForFieldSMT10_040() {
    final List<String> conditionalOnIMT = getConditionalOnIMTforSMT(getStandard());

    StdNistValidatorErrorEnum error1 = StdNistValidatorErrorEnum.STD_ERR_SMT_1;
    String listOfIMT = String.join(",", conditionalOnIMT);
    String msg1 = ValidationMessage.format(error1, recordType, SMT, null, singletonList(listOfIMT));

    // It is not used for other images
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(SMT, stringEmptyOrNull()))
        .when(
            handlePredicateOnTextField(
                IMT, not(stringEmptyOrNull()).and(not(stringInCollection(conditionalOnIMT)))))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithMsg(
                this.recordType, SMT, StdNistValidatorErrorEnum.STD_ERR_SMT_1, msg1));

    // This field shall be used only when Field IMT
    StdNistValidatorErrorEnum error2 = StdNistValidatorErrorEnum.STD_ERR_SMT_2;
    String msg2 = ValidationMessage.format(error2, recordType, SMT, null, singletonList(listOfIMT));

    ruleFor(r -> r)
        .must(handlePredicateOnPairOfFields(IMT, SMT, validateFieldSMTDependingToIMT()))
        .when(handlePredicateOnTextField(IMT, stringInCollection(conditionalOnIMT)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithMsg(
                this.recordType, SMT, StdNistValidatorErrorEnum.STD_ERR_SMT_2, msg2));
  }

  protected Predicate<Pair<String, String>> validateFieldSMTDependingToIMT() {
    return pairOfFields -> {
      final String valIMT = pairOfFields.getLeft();
      final String valSMT = pairOfFields.getRight();
      final List<String> allowedValuesForSMT = getAllowedValuesForSMT(getStandard(), valIMT);

      for (String item : SubFieldToStringConverter.toList(valSMT)) {
        if (!optional(stringInCollection(allowedValuesForSMT)).test(item)) {
          return false;
        }
      }
      return true;
    };
  }

  protected void checkForFieldSMD10_042() {
    final List<String> conditionOnIMT = getConditionalOnIMTforSMT(getStandard());

    StdNistValidatorErrorEnum error1 = StdNistValidatorErrorEnum.STD_ERR_SMD_1;
    String listOfIMT = String.join(",", conditionOnIMT);
    String msg1 = ValidationMessage.format(error1, recordType, SMD, null, singletonList(listOfIMT));

    // It is not used for other images
    ruleFor(r -> r)
        .must(handlePredicateOnTextField(SMD, stringEmptyOrNull()))
        .when(
            handlePredicateOnTextField(
                IMT, stringEmptyOrNull().or(not(stringInCollection(conditionOnIMT)))))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithMsg(
                this.recordType, SMT, StdNistValidatorErrorEnum.STD_ERR_SMD_1, msg1));

    // This field shall be used only when Field IMT
    StdNistValidatorErrorEnum error2 = StdNistValidatorErrorEnum.STD_ERR_SMD_2;
    String msg2 = ValidationMessage.format(error2, recordType, SMD, null, singletonList(listOfIMT));

    ruleFor(r -> r)
        .must(
            handlePredicateOnPairOfFields(IMT, SMD, validateFieldsSMDDependingToIMT(getStandard())))
        .when(handlePredicateOnTextField(IMT, stringInCollection(conditionOnIMT)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithMsg(
                this.recordType, SMD, StdNistValidatorErrorEnum.STD_ERR_SMD_2, msg2));
  }

  protected Predicate<Pair<String, String>> validateFieldsSMDDependingToIMT(
      NistStandardEnum nistStd) {
    return pairOfFields -> {
      final String valIMT = pairOfFields.getLeft();
      final String valSMD = pairOfFields.getRight();
      final List<String> allowedValuesForSMD_SMI =
          getAllowedValuesForSMD_SMI(getStandard(), valIMT);

      for (List<String> itemsSMD : SubFieldToStringConverter.toListOfList(valSMD)) {
        // SMI eq SMT
        if (itemsSMD.isEmpty()) {
          log.debug("validateFieldsSMDDependingToIMT SMD.SMI is mandatory SMD:{}", itemsSMD);
          return false;
        }
        String itemSMI = itemsSMD.get(0);
        if (!mandatory(stringInCollection(allowedValuesForSMD_SMI)).test(itemSMI)) {
          log.debug(
              "validateFieldsSMDDependingToIMT SMD.SMI {} should be in collection SMD:{} ",
              itemSMI,
              itemsSMD);
          return false;
        }

        // TAC only for TATTOO
        if (itemsSMD.size() > 1) {
          final String itemTAC = itemsSMD.get(1);
          if (!areEquals(TATTOO.getCode(), valIMT)) {
            log.debug(
                "validateFieldsSMDDependingToIMT SMD.TAC {} is only apply for TATTOO IMT:{} ",
                itemTAC,
                valIMT);
            return false;
          }
          if (!stringInCollection(getAllowedValuesForSMD_TAC(nistStd)).test(itemTAC)) {
            log.debug(
                "validateFieldsSMDDependingToIMT SMD.TAC {} should be in collection", itemTAC);
            return false;
          }
          if (itemsSMD.size() > 2) {
            String itemTSC = itemsSMD.get(2);
            if (!stringInCollection(getAllowedValuesForSMD_TSC(nistStd, itemTAC)).test(itemTSC)) {
              log.debug(
                  "validateFieldsSMDDependingToIMT SMD.TSC {} should be in collection", itemTSC);
              return false;
            }
          }
        }
        if (itemsSMD.size() > 3) {
          final String itemTDS = itemsSMD.get(3);
          if (!isCharTypeWithMinLength(U, 1).test(itemTDS)) {
            return false;
          }
        }
        if (itemsSMD.size() > 4) {
          return false;
        }
      }
      return true;
    };
  }

  protected void checkForFieldSAP10_013() {
    ruleFor(r -> r)
        // is Mandatory when IMT == FACE
        .must(
            handlePredicateOnTextField(
                SAP, mandatory(stringInCollection(getAllowedValuesForSAP(getStandard())))))
        .when(handlePredicateOnTextField(IMT, stringEquals(FACE.getCode())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, SAP, StdNistValidatorErrorEnum.STD_ERR_SAP));
  }

  protected void checkForFieldCSP10_012() {
    checkForMandatoryInCollectionField(CSP, CSP_ALLOWED_VALUES);
  }

  protected void checkForFieldCGA10_011() {
    checkForMandatoryInCollectionField(CGA, getAllowedValuesForCGA(recordType, getStandard()));
  }

  protected void checkForFieldVPS_LEGACY10_010() {
    checkForMandatoryNumericFieldBetween(VPS_LEGACY, 1, 99999);
  }

  protected void checkForFieldHPS_LEGACY10_009() {
    checkForMandatoryNumericFieldBetween(HPS_LEGACY, 1, 99999);
  }

  protected void checkForFieldSLC10_008() {
    checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
  }

  protected void checkForFieldVLL10_007() {
    checkForMandatoryNumericFieldBetween(VLL, 1, 99999);
  }

  protected void checkForFieldHLL10_006() {
    checkForMandatoryNumericFieldBetween(HLL, 1, 99999);
  }

  protected void checkForFieldPHD10_005() {
    checkForMandatoryDateField(PHD);
  }

  protected void checkForFieldSRC10_004() {
    checkForMandatoryCharTypeAndMinMaxLengthField(SRC, ANS, 9, 36);
  }

  protected void checkForFieldIMT10_003() {
    checkForMandatoryInCollectionField(IMT, getAllowedValuesForIMT(getStandard()));
  }

  protected void checkForFieldIDC10_002() {
    checkForMandatoryNumericFieldBetween(IDC, 0, 99);
  }

  protected void checkForFieldLEN10_001() {
    checkForMandatoryLENField(LEN);
  }
}
