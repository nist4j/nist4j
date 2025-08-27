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

import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_PXS_LEGACY_RT10;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isHexaCodeWithLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import io.github.nist4j.enums.ref.image.*;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import java.util.function.Predicate;

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
    checkForFieldCOL10_043();
    checkForFieldDATA10_999();
  }

  protected void checkForFieldCOL10_043() {
    checkCustomPredicateOnField(
        COL, StdNistValidatorErrorEnum.STD_ERR_COL_RT10, validateFieldCOL(getStandard()));
  }

  private Predicate<String> validateFieldCOL(NistStandardEnum nistStandard) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isEmpty(items) || items.stream().allMatch(validateFieldCOLItem(nistStandard));
    };
  }

  private static Predicate<String> validateFieldCOLItem(NistStandardEnum nistStandard) {
    return stringInCollection(findCodesAllowedByStandard(NistRefColorsEnum.values(), nistStandard));
  }

  protected void checkForFieldSMD10_042() {
    checkCustomPredicateOnField(
        SMD, StdNistValidatorErrorEnum.STD_ERR_SMD_RT10, validateFieldSMD());
  }

  private Predicate<String> validateFieldSMD() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldSMDItem());
    };
  }

  private Predicate<List<String>> validateFieldSMDItem() {
    return items -> (items.size() == 3 || items.size() == 4);
  }

  protected void checkForFieldSMS10_041() {
    checkCustomPredicateOnField(
        SMS, StdNistValidatorErrorEnum.STD_ERR_SMS_RT10, validateFieldSMS());
  }

  private Predicate<String> validateFieldSMS() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toItems(field);
      return isEmpty(items)
          || (items.size() == 2
              && isNumberBetween(0, 999).test(items.get(0))
              && isNumberBetween(0, 999).test(items.get(1)));
    };
  }

  protected void checkForFieldDMM10_030() {
    checkForOptionalButAlphaNumWithMinMaxLengthField(
        DMM, StdNistValidatorErrorEnum.STD_ERR_DMM_RT10, 8, 11);
  }

  protected void checkForFieldFFP10_029() {
    checkCustomPredicateOnField(
        FFP, StdNistValidatorErrorEnum.STD_ERR_FFP_RT10, validateFieldFFP());
  }

  private Predicate<String> validateFieldFFP() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldFFPItem());
    };
  }

  @SuppressWarnings("DuplicatedCode")
  private Predicate<List<String>> validateFieldFFPItem() {
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
        .must(handlePredicateOnField(SHC, validateFieldSHC(getStandard())))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SHC_RT10));
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
            handlePredicateOnField(
                SEC,
                stringInCollection(
                    findCodesAllowedByStandard(
                        NistRefSubjectEyeColorEnum.values(), getStandard()))))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SEC_RT10));
  }

  protected void checkForFieldSXS10_026() {
    ruleFor(r -> r)
        .must(handlePredicateOnField(SXS, validateFieldSXS(getStandard())))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SXS_RT10));
  }

  protected static Predicate<String> validateFieldSXS(NistStandardEnum nistStandard) {
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
        .must(handlePredicateOnField(SPA, validateFieldSPA()))
        .when(isFieldEquals(POS, NistRefSubjectPoseEnum.DETERMINED_3D_POSE.getCode()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SPA_RT10));
  }

  protected static Predicate<String> validateFieldSPA() {
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
        .must(handlePredicateOnField(SQS, validateFieldSQS()))
        .when(not(isFieldAbsent(SQS)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_SQS_RT10));
  }

  protected static Predicate<String> validateFieldSQS() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldSQSItems());
    };
  }

  private static Predicate<List<String>> validateFieldSQSItems() {
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
        .must(handlePredicateOnField(PAS, validateFieldPAS(getStandard())))
        .when(isFieldNumberBetween(SAP, 40, 99999))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_PAS_RT10));
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
        STD_ERR_PXS_LEGACY_RT10,
        not(stringEmptyOrNull()).or(validateFieldPXS_LEGACY(getStandard())));
  }

  protected static Predicate<String> validateFieldPXS_LEGACY(NistStandardEnum nistStandard) {
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
        .must(handlePredicateOnField(POA, optional((isNumberBetween(-180, 180)))))
        .when(isFieldEquals(POS, NistRefSubjectPoseEnum.ANGLED_POSE.getCode()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_POA_RT10));
  }

  protected void checkForFieldPOS10_020() {
    checkForOptionalButInCollectionField(
        POS, StdNistValidatorErrorEnum.STD_ERR_POS_RT10, getAllowedValuesForPOS(getStandard()));
  }

  protected void checkForFieldSVPS10_017() {
    checkForOptionalButNumericFieldBetween(
        SVPS, StdNistValidatorErrorEnum.STD_ERR_SVPS_RT10, 1, 99999);
  }

  protected void checkForFieldSHPS10_016() {
    checkForOptionalButNumericFieldBetween(
        SHPS, StdNistValidatorErrorEnum.STD_ERR_SHPS_RT10, 1, 99999);
  }

  protected void checkForFieldDATA10_999() {
    checkForMandatoryDataField(DATA, StdNistValidatorErrorEnum.STD_ERR_DATA_RT10);
  }

  protected void checkForFieldSMT10_040() {
    checkForMandatoryAlphaNumWithMinMaxLengthField(
        SMT, StdNistValidatorErrorEnum.STD_ERR_SMT_RT10, 4, 11);
  }

  protected void checkForFieldSAP10_013() {
    checkForMandatoryInCollectionField(
        SAP, StdNistValidatorErrorEnum.STD_ERR_SAP_RT10, getAllowedValuesForSAP(getStandard()));
  }

  protected void checkForFieldCSP10_012() {
    checkForMandatoryInCollectionField(
        CSP, StdNistValidatorErrorEnum.STD_ERR_CSP_RT10, CSP_ALLOWED_VALUES);
  }

  protected void checkForFieldCGA10_011() {
    checkForMandatoryInCollectionField(
        CGA, StdNistValidatorErrorEnum.STD_ERR_CGA_RT10, getAllowedValuesForCGA(getStandard()));
  }

  protected void checkForFieldVPS_LEGACY10_010() {
    checkForMandatoryNumericFieldBetween(
        VPS_LEGACY, StdNistValidatorErrorEnum.STD_ERR_VPS_RT10, 1, 99999);
  }

  protected void checkForFieldHPS_LEGACY10_009() {
    checkForMandatoryNumericFieldBetween(
        HPS_LEGACY, StdNistValidatorErrorEnum.STD_ERR_HPS_RT10, 1, 99999);
  }

  protected void checkForFieldSLC10_008() {
    checkForMandatoryInCollectionField(
        SLC, StdNistValidatorErrorEnum.STD_ERR_SLC_RT10, SLC_ALLOWED_VALUES);
  }

  protected void checkForFieldVLL10_007() {
    checkForMandatoryNumericFieldBetween(VLL, StdNistValidatorErrorEnum.STD_ERR_VLL_RT10, 1, 99999);
  }

  protected void checkForFieldHLL10_006() {
    checkForMandatoryNumericFieldBetween(HLL, StdNistValidatorErrorEnum.STD_ERR_HLL_RT10, 1, 99999);
  }

  protected void checkForFieldPHD10_005() {
    checkForMandatoryDateField(PHD, StdNistValidatorErrorEnum.STD_ERR_PHD_RT10);
  }

  protected void checkForFieldSRC10_004() {
    checkForMandatoryAlphaNumWithMinMaxLengthField(
        SRC, StdNistValidatorErrorEnum.STD_ERR_SRC_RT10, 9, 36);
  }

  protected void checkForFieldIMT10_003() {
    checkForMandatoryInCollectionField(
        IMT, StdNistValidatorErrorEnum.STD_ERR_IMT_RT10, getAllowedValuesForIMT(getStandard()));
  }

  protected void checkForFieldIDC10_002() {
    checkForMandatoryNumericFieldBetween(IDC, StdNistValidatorErrorEnum.STD_ERR_IDC_RT10, 0, 99);
  }

  protected void checkForFieldLEN10_001() {
    checkForMandatoryLENField(LEN, StdNistValidatorErrorEnum.STD_ERR_LEN);
  }
}
