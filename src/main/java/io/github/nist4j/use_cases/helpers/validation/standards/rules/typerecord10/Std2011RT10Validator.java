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

import static io.github.nist4j.enums.CharacterTypeEnum.*;
import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.equalTo;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.enums.ref.image.NistRefFacialIMTImageTypeEnum;
import io.github.nist4j.enums.ref.image.NistRefImageTransformEnum;
import io.github.nist4j.enums.ref.image.NistRefSubjectFacialContourEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class Std2011RT10Validator extends Std2007RT10Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2011;
  }

  protected Std2011RT10Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2011RT10Validator(NistOptions nistOptions) {
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
    checkForFieldTHPS10_009();
    checkForFieldTVPS10_010();
    checkForFieldCGA10_011();
    checkForFieldCSP10_012();
    checkForFieldSAP10_013();
    checkForFieldFIP10_014();
    checkForFieldFPFI10_015();
    checkForFieldSHPS10_016();
    checkForFieldSVPS10_017();
    checkForFieldDIST10_018();
    checkForFieldLAF10_019();
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
    checkForFieldTMC10_031();
    checkForField3DF10_032();
    checkForFieldFEC10_033();
    checkForFieldCOM10_038();
    checkForFieldSMT10_040();
    checkForFieldSMS10_041();
    checkForFieldSMD10_042();
    checkForFieldTCL10_043();
    checkForFieldITX10_044();
    checkForFieldOCC10_045();
    checkForFieldANN10_902();
    checkForFieldDUI10_903();
    checkForFieldMMS10_904();
    checkForFieldSAN10_993();
    checkForFieldASC10_995();
    checkForFieldHAS10_996();
    checkForFieldSOR10_997();
    checkForFieldGEO10_998();
    checkForFieldDATA10_999();
  }

  protected void checkForFieldGEO10_998() {
    checkForGenericFieldGEO_998(RT10FieldsEnum.GEO);
  }

  protected void checkForFieldSOR10_997() {
    checkForGenericFieldSOR_997(RT10FieldsEnum.SOR);
  }

  protected void checkForFieldHAS10_996() {
    checkForGenericFieldHAS_996(HAS);
  }

  protected void checkForFieldASC10_995() {
    checkForGenericFieldASC_995(ASC);
  }

  protected void checkForFieldSAN10_993() {
    checkForOptionalButCharTypeAndMinMaxLengthField(SAN, U, 1, 125);
  }

  protected void checkForFieldMMS10_904() {
    checkForGenericFieldMMS_904(MMS);
  }

  protected void checkForFieldDUI10_903() {
    checkForGenericFieldDUI_903(DUI);
  }

  protected void checkForFieldANN10_902() {
    checkForGenericFieldANN_902(ANN);
  }

  protected void checkForFieldOCC10_045() {
    checkCustomPredicateOnField(OCC, StdNistValidatorErrorEnum.STD_ERR_OCC, validateFieldOCC());
  }

  protected Predicate<String> validateFieldOCC() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldOCCItem());
    };
  }

  protected Predicate<List<String>> validateFieldOCCItem() {
    return items ->
        items.size() >= 5
            && stringInCollection(asList("T", "I", "L", "S")).test(items.get(0)) // OCY
            && stringInCollection(asList("H", "S", "C", "R", "O")).test(items.get(1)) // OCT
            && isCharTypeWithMinMaxLength(N, 1, 2).test(items.get(2)) // NOP
            && areNumbersBetween(0, 99999).test(items.subList(3, items.size())) // repeat HPO & VPO
    ;
  }

  protected void checkForFieldITX10_044() {
    checkCustomPredicateOnField(
        ITX, StdNistValidatorErrorEnum.STD_ERR_ITX, validateFieldITX(getStandard()));
  }

  protected Predicate<String> validateFieldITX(NistStandardEnum nistStandard) {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isEmpty(items)
          || items.stream()
              .allMatch(
                  stringInCollection(
                      findCodesAllowedByStandard(
                          NistRefImageTransformEnum.values(), nistStandard)));
    };
  }

  protected void checkForFieldCOM10_038() {
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, U, 1, 126);
  }

  protected void checkForFieldFEC10_033() {
    checkCustomPredicateOnField(FEC, StdNistValidatorErrorEnum.STD_ERR_FEC, validateFieldFEC());
  }

  protected Predicate<String> validateFieldFEC() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldFECItems());
    };
  }

  protected Predicate<List<String>> validateFieldFECItems() {
    final int nopMinVal = 3;
    final int nopMaxVal = 99;

    return items -> {
      List<String> listOfHPO_VPO = emptyList();
      if (items.size() >= 4) {
        listOfHPO_VPO = items.subList(2, items.size());
      }
      return items.size() >= 4
          && stringInCollection(getAllowedValuesForFEC(getStandard())).test(items.get(0)) // FCC
          && isNumberBetween(nopMinVal, nopMaxVal).test(items.get(1)) // NOP
          && equalTo(0).test(listOfHPO_VPO.size() % 2) // must be a pair of values
          && areNumbersBetween(0, 99999).test(items.subList(2, items.size())) // repeat HPO & VPO
      ;
    };
  }

  protected Collection<String> getAllowedValuesForFEC(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefSubjectFacialContourEnum.values(), nistStandard);
  }

  protected void checkForField3DF10_032() {
    checkCustomPredicateOnField(THREEDF, StdNistValidatorErrorEnum.STD_ERR_3DF, validateField3DF());
  }

  protected Predicate<String> validateField3DF() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateField3DFItems());
    };
  }

  @SuppressWarnings("DuplicatedCode")
  protected Predicate<List<String>> validateField3DFItems() {
    return items -> {
      return items.size() == 5
          && stringInCollection(asList("1", "2")).test(items.get(0)) // FPT Feature point type
          && stringSizeBetween(1, 5).test(items.get(1))
          && (stringEquals("1").test(items.get(0)) // FPC Feature point code n.n or nn.nn
                  && stringMatches("^\\d{1,2}\\.\\d{1,2}$").test(items.get(1))
              || stringEquals("2").test(items.get(0)) // FPC Feature point code a or aa or aaa
                  && stringMatches("^[a-z]{1,4}$").test(items.get(1)))
          && isNumberBetween(0, 99999).test(items.get(2)) // HCX X coordinate
          && isNumberBetween(0, 99999).test(items.get(3)) // HCY Y coordinate
          && isNumberBetween(0, 99999).test(items.get(4)) // HCZ Z coordinate
      ;
    };
  }

  protected void checkForFieldTMC10_031() {
    checkForOptionalButNumericFieldBetween(TMC, 1, 999);
  }

  @Override
  protected void checkForFieldPXS_LEGACY10_022() {
    checkForEmptyField(PXS_LEGACY);
  }

  protected void checkForFieldLAF10_019() {
    checkCustomPredicateOnField(
        LAF, StdNistValidatorErrorEnum.STD_ERR_LAF, validateFieldLAFItems());
  }

  private Predicate<String> validateFieldLAFItems() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isEmpty(items) || areInCollection(asList("F", "H", "R")).test(items);
    };
  }

  protected void checkForFieldDIST10_018() {
    ruleFor(r -> r)
        .must(
            handlePredicateOnTextField(
                IMT, stringEquals(NistRefFacialIMTImageTypeEnum.FACE.getCode())))
        .when(isFieldPresent(DIST))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, DIST, StdNistValidatorErrorEnum.STD_ERR_DIST_IMT_MUST_BE_FACE));

    checkCustomPredicateOnField(
        DIST, StdNistValidatorErrorEnum.STD_ERR_DIST, validateFieldDISTItems());
  }

  private Predicate<String> validateFieldDISTItems() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toItems(field);
      if (isEmpty(items)) {
        return true;
      }
      return items.size() == 3
          && stringInCollection(asList("Barrel", "Inflated", "Pincushion"))
              .test(items.get(0)) // IDK
          && stringInCollection(asList("E", "C")).test(items.get(1)) // IDM
          && stringInCollection(asList("Mild", "Moderate", "Severe")).test(items.get(2)) // DSC
      ;
    };
  }

  protected void checkForFieldFPFI10_015() {
    checkCustomPredicateOnField(
        FPFI, StdNistValidatorErrorEnum.STD_ERR_FPFI, validateFieldFPFIItems());
  }

  private Predicate<String> validateFieldFPFIItems() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toItems(field);
      if (isEmpty(items)) {
        return true;
      }
      return items.size() >= 4
          && stringInCollection(asList("C", "E", "P"))
              .test(items.get(0)) // BYC boundary code circle, elipse, polygone
          && isNumberBetween(1, 99).test(items.get(1)) // NOP number of point
          && areNumbersBetween(0, 99999).test(items.subList(2, items.size())) // HPO & VPO
      ;
    };
  }

  protected void checkForFieldFIP10_014() {
    checkCustomPredicateOnField(
        FIP, StdNistValidatorErrorEnum.STD_ERR_FIP, validateFieldFIPItems());

    ruleFor(r -> r)
        .must(handlePredicateOnPairOfFields(FIP, HLL, validateFieldFIPwithHLL()))
        .when(not(isFieldAbsent(FIP)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, FIP, StdNistValidatorErrorEnum.STD_ERR_FIP_1));

    ruleFor(r -> r)
        .must(handlePredicateOnPairOfFields(FIP, VLL, validateFieldFIPwithVLL()))
        .when(not(isFieldAbsent(FIP)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                this.recordType, FIP, StdNistValidatorErrorEnum.STD_ERR_FIP_2));
  }

  protected Predicate<Pair<String, String>> validateFieldFIPwithHLL() {
    return pairOfFields -> {
      String fip = pairOfFields.getLeft();
      List<String> items = SubFieldToStringConverter.toItems(fip);
      if (isEmpty(items)) {
        return true;
      }
      String hll = pairOfFields.getRight();
      if (!isNumeric().test(hll)) {
        return false;
      }
      int hllInt = Integer.parseInt(hll);
      return items.size() >= 4
          && isNumberBetween(0, hllInt).test(items.get(0)) // LHC
          && isNumberBetween(Integer.parseInt(items.get(0)), hllInt).test(items.get(1)) // RHC
      // TVC
      // BVC
      ; // BBC
    };
  }

  protected Predicate<Pair<String, String>> validateFieldFIPwithVLL() {
    return pairOfFields -> {
      String fip = pairOfFields.getLeft();
      List<String> items = SubFieldToStringConverter.toItems(fip);
      if (isEmpty(items)) {
        return true;
      }
      String vll = pairOfFields.getRight();
      if (!isNumeric().test(vll)) {
        return false;
      }
      int vllInt = Integer.parseInt(vll);
      return items.size() >= 4
          // LHC
          // RHC
          && isNumberBetween(0, vllInt).test(items.get(2)) // TVC
          && isNumberBetween(Integer.parseInt(items.get(2)), vllInt).test(items.get(3)) // BVC
      ; // BBC
    };
  }

  protected Predicate<String> validateFieldFIPItems() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      if (isEmpty(items)) {
        return true;
      }
      return (items.size() == 4 || items.size() == 5)
          && isNumberBetween(0, 99999).test(items.get(0)) // LHC
          && isNumberBetween(0, 99999).test(items.get(1)) // RHC
          && isNumberBetween(0, 99999).test(items.get(2)) // TVC
          && isNumberBetween(0, 99999).test(items.get(3)) // BVC
          && (items.size() == 4
              || stringInCollection(asList("S", "H", "F", "N", "X")).test(items.get(4))); // BBC
    };
  }

  protected void checkForFieldTVPS10_010() {
    checkForMandatoryNumericFieldBetween(VPS_LEGACY, 1, 99999);
  }

  protected void checkForFieldTHPS10_009() {
    checkForMandatoryNumericFieldBetween(HPS_LEGACY, 1, 99999);
  }

  protected void checkForFieldSRC10_004() {
    checkForMandatoryCharTypeAndMinLengthField(SRC, U, 1);
  }
}
