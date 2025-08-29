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
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.TimePredicate.isYYYYMMDDHHMMSSDateTime;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.image.NistRefFacialSMTImageTypeEnum;
import io.github.nist4j.enums.ref.image.NistRefImageTransformEnum;
import io.github.nist4j.enums.ref.image.NistRefSubjectFacialContourEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.checksum.Sha256Checksum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate;
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
    checkForFieldCOL10_043();
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
    checkForOptionalButUniqueSubfields(
        GEO,
        StdNistValidatorErrorEnum.STD_ERR_GEO_RT10,
        optional(isYYYYMMDDHHMMSSDateTime()), // UTE
        optional(isRealNumberBetween(-90, 90)), // LTD
        optional(isRealNumberBetween(0, 60)), // LTM
        optional(isRealNumberBetween(0, 60)), // LTS
        optional(isRealNumberBetween(-180, 180)), // LGD
        optional(isRealNumberBetween(0, 60)), // LGM
        optional(isRealNumberBetween(0, 60)), // LGS
        optional(isRealNumberBetween(-422, 8848)), // ELE
        optional(isCharTypeWithMinMaxLength(AN, 3, 6)), // GDC
        optional(isCharTypeWithMinMaxLength(AN, 2, 3)), // GCM
        optional(isNumberBetween(0, 999999)), // GCS
        optional(isNumberBetween(0, 99999999)), // GCN
        optional(isCharTypeWithMinMaxLength(U, 1, 150)), // GRT
        optional(isCharTypeWithMinMaxLength(U, 1, 150)), // OSI
        optional(isCharTypeWithMinMaxLength(U, 1, 126)) // OCV
        );
  }

  protected void checkForFieldSOR10_997() {
    checkForOptionalButRepeatedSubfields(
        SOR,
        StdNistValidatorErrorEnum.STD_ERR_SOR_RT10,
        isNumberBetween(1, 255), // SRN
        optional(isNumberBetween(1, 99)) // RSP
        );
  }

  protected void checkForFieldHAS10_996() {
    checkForOptionalButCharTypeAndMinMaxLengthField(
        HAS, StdNistValidatorErrorEnum.STD_ERR_HAS_RT10, H, 64, 64);

    ruleFor(r -> r)
        .must(handlePredicateOnFieldWithImage(HAS, validateFieldHASequalsToHashOfDATA()))
        .when(isFieldPresent(HAS))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_HAS_RT10));
  }

  private Predicate<Pair<String, DataImage>> validateFieldHASequalsToHashOfDATA() {
    return pairOfFields -> {
      DataImage dataImage = pairOfFields.getRight();
      if (isEmpty(dataImage) || isEmpty(dataImage.getData())) {
        return false;
      }
      String sha256 = Sha256Checksum.calculateToHex(dataImage.getData());
      String hasField = pairOfFields.getLeft();
      return stringEquals(sha256).test(hasField);
    };
  }

  protected void checkForFieldASC10_995() {
    checkForOptionalButRepeatedSubfields(
        ASC,
        StdNistValidatorErrorEnum.STD_ERR_ASC_RT10,
        isNumberBetween(1, 255), // ACN
        optional(isNumberBetween(1, 99)) // ASP
        );
  }

  protected void checkForFieldSAN10_993() {
    checkForOptionalButCharTypeAndMinMaxLengthField(
        SAN, StdNistValidatorErrorEnum.STD_ERR_SAN_RT10, U, 1, 125);
  }

  protected void checkForFieldMMS10_904() {
    checkForOptionalButUniqueSubfields(
        MMS,
        StdNistValidatorErrorEnum.STD_ERR_MMS_RT10,
        isCharTypeWithMinMaxLength(U, 1, 50), // MAK
        isCharTypeWithMinMaxLength(U, 1, 50), // MOD
        isCharTypeWithMinMaxLength(U, 1, 50) // SER
        );
  }

  protected void checkForFieldDUI10_903() {
    checkCustomPredicateOnField(
        DUI,
        StdNistValidatorErrorEnum.STD_ERR_DUI_RT10,
        stringEmptyOrNull()
            .or(
                isCharTypeWithMinMaxLength(ANS, 13, 16)
                    .and(stringStartingWith("M").or(stringStartingWith("P")))));
  }

  protected void checkForFieldANN10_902() {
    checkForOptionalButRepeatedSubfields(
        ANN,
        StdNistValidatorErrorEnum.STD_ERR_ANN_RT10,
        isYYYYMMDDHHMMSSDateTime(), // GMT
        isCharTypeWithMinMaxLength(U, 1, 64), // NAV
        isCharTypeWithMinMaxLength(U, 1, 64), // OWN
        isCharTypeWithMinMaxLength(U, 1, 255) // PRO
        );
  }

  protected void checkForFieldOCC10_045() {
    checkCustomPredicateOnField(
        OCC, StdNistValidatorErrorEnum.STD_ERR_OCC_RT10, validateFieldOCC());
  }

  private Predicate<String> validateFieldOCC() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldOCCItem());
    };
  }

  private Predicate<List<String>> validateFieldOCCItem() {
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
        ITX, StdNistValidatorErrorEnum.STD_ERR_ITX_RT10, validateFieldITX(getStandard()));
  }

  private Predicate<String> validateFieldITX(NistStandardEnum nistStandard) {
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

  @Override
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
    return items ->
        isCharTypeWithMinMaxLength(A, 3, 8).test(items.get(0)) // SMI Mandatory
            && (items.size() < 2 || isCharTypeWithMinMaxLength(A, 4, 8).test(items.get(1))) // TAC
            && (items.size() < 3 || isCharTypeWithMinMaxLength(A, 3, 8).test(items.get(2))) // TSC
            && (items.size() < 4 || isCharTypeWithMinMaxLength(U, 1, 256).test(items.get(3))) // TDS
            && items.size() < 5;
  }

  protected void checkForFieldCOM10_038() {
    checkForOptionalButUnicodeFieldWithMinMaxLengthField(
        COM, StdNistValidatorErrorEnum.STD_ERR_COM_RT10, 1, 126);
  }

  protected void checkForFieldFEC10_033() {
    checkCustomPredicateOnField(
        FEC, StdNistValidatorErrorEnum.STD_ERR_FEC_RT10, validateFieldFEC());
  }

  private Predicate<String> validateFieldFEC() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldFECItems());
    };
  }

  private Predicate<List<String>> validateFieldFECItems() {
    return items -> {
      return items.size() >= 4
          && stringInCollection(getAllowedValuesForFEC(getStandard())).test(items.get(0)) // FCC
          && isNumberBetween(3, 99).test(items.get(1)) // NOP
          && areNumbersBetween(0, 99999).test(items.subList(2, items.size())) // repeat HPO & VPO
      ;
    };
  }

  private Collection<String> getAllowedValuesForFEC(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefSubjectFacialContourEnum.values(), nistStandard);
  }

  protected void checkForField3DF10_032() {
    checkCustomPredicateOnField(
        THREEDF, StdNistValidatorErrorEnum.STD_ERR_3DF_RT10, validateField3DF());
  }

  private Predicate<String> validateField3DF() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateField3DFItems());
    };
  }

  @SuppressWarnings("DuplicatedCode")
  private Predicate<List<String>> validateField3DFItems() {
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
    checkForOptionalButNumericFieldBetween(TMC, StdNistValidatorErrorEnum.STD_ERR_TMC_RT10, 1, 999);
  }

  @Override
  protected void checkForFieldPXS_LEGACY10_022() {
    checkForEmptyField(PXS_LEGACY, StdNistValidatorErrorEnum.STD_ERR_PXS_LEGACY_RT10_DEPRECATED);
  }

  protected void checkForFieldLAF10_019() {
    checkCustomPredicateOnField(
        LAF, StdNistValidatorErrorEnum.STD_ERR_LAF_RT10, validateFieldLAFItems());
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
            handlePredicateOnField(IMT, stringEquals(NistRefFacialSMTImageTypeEnum.FACE.getCode())))
        .when(isFieldPresent(DIST))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                StdNistValidatorErrorEnum.STD_ERR_DIST_RT10_IMT_MUST_BE_FACE));

    checkCustomPredicateOnField(
        DIST, StdNistValidatorErrorEnum.STD_ERR_DIST_RT10, validateFieldDISTItems());
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
        FPFI, StdNistValidatorErrorEnum.STD_ERR_FPFI_RT10, validateFieldFPFIItems());
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

  /** SMT is optional on std2011 contains subfields */
  @Override
  protected void checkForFieldSMT10_040() {
    ruleFor(r -> r)
        .must(handlePredicateOnField(SMT, validateFieldSMT()))
        .when(isFieldPresent(SMT))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                StdNistValidatorErrorEnum.STD_ERR_SMT_RT10_FORMAT));
  }

  /**
   * In Std2011 charType 'A' is required but space is in 'A'
   * In Std2013 charType 'AS' is required and space is in 'S'
   * So let it simplify and consider SMT as a AS for Std2011 and after
   */
  protected static Predicate<String> validateFieldSMT() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isEmpty(items)
          || NistCharacterPredicate.areCharTypeWithMinMaxLength(AS, 3, 10).test(items);
    };
  }

  protected void checkForFieldFIP10_014() {
    checkCustomPredicateOnField(
        FIP, StdNistValidatorErrorEnum.STD_ERR_FIP_RT10, validateFieldFIPItems());

    ruleFor(r -> r)
        .must(handlePredicateOnPairOfFields(FIP, HLL, validateFieldFIPwithHLL()))
        .when(not(isFieldAbsent(FIP)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_FIP_RT10_1));

    ruleFor(r -> r)
        .must(handlePredicateOnPairOfFields(FIP, VLL, validateFieldFIPwithVLL()))
        .when(not(isFieldAbsent(FIP)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(StdNistValidatorErrorEnum.STD_ERR_FIP_RT10_2));
  }

  @SuppressWarnings("DuplicatedCode")
  protected static Predicate<Pair<String, String>> validateFieldFIPwithHLL() {
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

  @SuppressWarnings("DuplicatedCode")
  protected static Predicate<Pair<String, String>> validateFieldFIPwithVLL() {
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

  protected static Predicate<String> validateFieldFIPItems() {
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
    checkForMandatoryNumericFieldBetween(
        VPS_LEGACY, StdNistValidatorErrorEnum.STD_ERR_TVPS_RT10, 1, 99999);
  }

  protected void checkForFieldTHPS10_009() {
    checkForMandatoryNumericFieldBetween(
        HPS_LEGACY, StdNistValidatorErrorEnum.STD_ERR_THPS_RT10, 1, 99999);
  }

  protected void checkForFieldSRC10_004() {
    checkForMandatoryCharTypeAndMinLengthField(
        SRC, StdNistValidatorErrorEnum.STD_ERR_SRC_RT10_U, U, 1);
  }
}
