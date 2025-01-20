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

import static br.com.fluentvalidator.predicate.StringPredicate.*;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findValuesAllowedByStandard;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.*;
import static io.github.nist4j.use_cases.helpers.converters.NumericFieldConverter.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.getFieldStringOrNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.ref.fp.NistRefAmputationBandagedFPEnum;
import io.github.nist4j.enums.ref.fp.NistRefAquisitionProfilFPEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum;
import io.github.nist4j.enums.ref.image.NistRefDeviceMonitoringModeEnum;
import io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

public abstract class AbstractStdRT14Validator extends AbstractNistRecordValidator {

  protected static final List<String> SLC_ALLOWED_VALUES =
      Collections.unmodifiableList(Arrays.asList("0", "1", "2"));
  protected static final List<String> SIF_ALLOWED_VALUE = Collections.singletonList("Y");
  private static final Set<String> PPD_ALLOWED_VALUES_FOR_SECOND_SUBFIELD =
      new HashSet<>(
          Arrays.asList(
              "EJI", "FV1", "FV2", "FV3", "FV4", "TIP", "TPP", "PRX", "DST", "MED", "NA"));
  private static final Set<String> PPC_ALLOWED_VALUES_FOR_FIRST_SUBFIELD =
      new HashSet<>(Arrays.asList("FV1", "FV2", "FV3", "FV4", "TIP", "TPP", "NA"));
  private static final Set<String> PPC_ALLOWED_VALUES_FOR_SECOND_SUBFIELD =
      new HashSet<>(Arrays.asList("PRX", "DST", "MED", "NA"));
  private static final Set<String> SUB_ALLOWED_VALUES_SSC =
      new HashSet<>(
          Arrays.asList(
              "A", // Data obtained from a living person – such as a victim or person unable to
              // identify themselves
              "X", // Status of individual unknown
              "D" // Data obtained from a non-living person (deceased)
              ));

  private static final Set<String> SUB_ALLOWED_VALUES_SBSC =
      new HashSet<>(
          Arrays.asList(
              "1", // Whole
              "2" // Fragment
              ));

  private static final Set<String> SUB_ALLOWED_VALUES_SBCC =
      new HashSet<>(
          Arrays.asList(
              "1", // Natural Tissue
              "2" // Decomposed
              ));

  protected AbstractStdRT14Validator(NistOptions nistOptions) {
    super(nistOptions, RT14);
  }

  protected static List<String> getAllowedValuesForIMP(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefImpressionTypeEnum.values(), nistStandard);
  }

  protected static List<String> getAllowedValuesForCGA(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefCompressionAlgorithmEnum.values(), nistStandard);
  }

  protected static List<String> getFGPFingersCombinationExceptEJI(NistStandardEnum nistStandard) {
    return findValuesAllowedByStandard(NistRefFrictionRidgePositionEnum.values(), nistStandard)
        .stream()
        .filter(
            frp ->
                NistRefFrictionRidgePositionEnum.FrictionRidgeType.FINGERS_COMBINATION.equals(
                        frp.getType())
                    && !EJI_OR_TIPS.getCode().equals(frp.getCode()))
        .map(NistRefFrictionRidgePositionEnum::getCode)
        .collect(Collectors.toList());
  }

  protected static List<String> getFGPUnitaryFingers(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(TEN_FINGERS, nistStandardEnum);
  }

  protected static List<String> getAllowedValuesForFAP(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(NistRefAquisitionProfilFPEnum.values(), nistStandardEnum);
  }

  protected static List<String> getAllowedValuesForDMM(NistStandardEnum standardEnum) {
    return findCodesAllowedByStandard(NistRefDeviceMonitoringModeEnum.values(), standardEnum);
  }

  protected static Predicate<NistRecord> isEJIFingerprint() {
    return r -> {
      String fgp = getFieldStringOrNull(RT14FieldsEnum.FGP, r);
      return fgp != null && fgp.contains(NistRefFrictionRidgePositionEnum.EJI_OR_TIPS.getCode());
    };
  }

  // 14.013
  protected static Predicate<String> validateFieldFGP(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return isNotEmpty(subFields)
          && subFields.stream()
              .allMatch(
                  fgpItem ->
                      stringInCollection(
                              findCodesAllowedByStandard(
                                  NistRefFrictionRidgePositionEnum.values(), nistStandard))
                          .test(fgpItem));
    };
  }

  // 14.021
  protected static Predicate<NistRecord> validateFieldSEG(NistStandardEnum nistStandard) {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              getFieldStringOrNull(RT14FieldsEnum.SEG, r));
      String hll = getFieldStringOrNull(RT14FieldsEnum.HLL, r);
      String vll = getFieldStringOrNull(RT14FieldsEnum.VLL, r);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isSEGOneFingerValid(
                      nistStandard, hll, vll, SubFieldToStringConverter.toList(subfield)));
    };
  }

  protected static Predicate<String> validateFieldSUB() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isNotEmpty(items)
              && stringInCollection(SUB_ALLOWED_VALUES_SSC).test(items.get(0))
              && Objects.equals(items.get(0), "D")
          ? isSUBValidForDeceasedPerson(items)
          : items.size() == 1;
    };
  }

  protected static Predicate<NistRecord> validateFieldPPD(NistStandardEnum nistStandardEnum) {
    return r -> {
      List<Pair<String, String>> subFields =
          SubFieldToStringConverter.toListOfPairs((getFieldStringOrNull(RT14FieldsEnum.PPD, r)));
      return subFields.stream()
          .allMatch(subfield -> isPPDOneFingerValid(subfield, nistStandardEnum));
    };
  }

  protected static Predicate<NistRecord> validateFieldPPC() {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              (getFieldStringOrNull(RT14FieldsEnum.PPC, r)));
      return subFields.stream()
          .allMatch(subfield -> isPPCOneFingerValid(SubFieldToStringConverter.toList(subfield)));
    };
  }

  protected static Predicate<String> validateFieldAMP(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isAMPOneFingerValid(SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected static Predicate<String> validateFieldNQM(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isNQMOneFingerValid(SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected static Predicate<String> validateFieldFQM(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isQualityOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected static Predicate<NistRecord> validateFieldSQM(NistStandardEnum nistStandard) {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              getFieldStringOrNull(RT14FieldsEnum.SQM, r));
      return subFields.stream()
          .allMatch(
              subfield ->
                  isQualityOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  public static Predicate<NistRecord> validateConsistencySQM() {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              getFieldStringOrNull(RT14FieldsEnum.SQM, r));
      List<String> allowedValues = extractAllFGPInField(r, RT14FieldsEnum.ASEG);
      allowedValues.addAll(extractAllFGPInField(r, RT14FieldsEnum.SEG));
      return subFields.stream()
          .allMatch(
              subfield ->
                  isSQMConsitent(SubFieldToStringConverter.toList(subfield), allowedValues));
    };
  }

  public static Predicate<NistRecord> validateConsistencyASEG(NistStandardEnum nistStandardEnum) {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              getFieldStringOrNull(RT14FieldsEnum.ASEG, r));
      String hll = getFieldStringOrNull(RT14FieldsEnum.HLL, r);
      String vll = getFieldStringOrNull(RT14FieldsEnum.VLL, r);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isASEGOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandardEnum, hll, vll));
    };
  }

  private static boolean isSEGOneFingerValid(
      NistStandardEnum nistStandard, String hll, String vll, List<String> items) {
    return items.size() == 5
        && stringInCollection(getFGPUnitaryFingers(nistStandard)).test(items.get(0))
        && isNumberBetween(0, tryParseIntOrDefault(hll, 0)).test(items.get(1))
        && isNumberBetween(tryParseIntOrDefault(items.get(1), 0), tryParseIntOrDefault(hll, 0))
            .test(items.get(2))
        && isNumberBetween(0, tryParseIntOrDefault(vll, 0)).test(items.get(3))
        && isNumberBetween(tryParseIntOrDefault(items.get(3), 0), tryParseIntOrDefault(vll, 0))
            .test(items.get(4));
  }

  private static boolean isSUBValidForDeceasedPerson(List<String> items) {
    return items.size() == 3
        && stringInCollection(SUB_ALLOWED_VALUES_SBSC).test(items.get(1))
        && stringInCollection(SUB_ALLOWED_VALUES_SBCC).test(items.get(2));
  }

  private static boolean isPPCOneFingerValid(List<String> items) {
    return items.size() == 6
        && stringInCollection(PPC_ALLOWED_VALUES_FOR_FIRST_SUBFIELD).test(items.get(0))
        && stringInCollection(PPC_ALLOWED_VALUES_FOR_SECOND_SUBFIELD).test(items.get(1))
        && isNumeric().test(items.get(2))
        && isNumeric().test(items.get(3))
        && isNumeric().test(items.get(4))
        && isNumeric().test(items.get(5));
  }

  private static boolean isPPDOneFingerValid(
      Pair<String, String> items, NistStandardEnum nistStandardEnum) {
    return stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.getKey())
        && stringInCollection(PPD_ALLOWED_VALUES_FOR_SECOND_SUBFIELD).test(items.getValue());
  }

  private static boolean isAMPOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() == 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0))
        && stringInCollection(
                findCodesAllowedByStandard(
                    NistRefAmputationBandagedFPEnum.values(), nistStandardEnum))
            .test(items.get(1));
  }

  private static boolean isNQMOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() == 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0))
        && stringMatches("^([1-5]|254|255)$").test(items.get(1));
  }

  private static boolean isQualityOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() >= 4
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0)) // FRMP
        && stringMatches("^(([1-9]?\\d{1})|100|254|255)$").test(items.get(1)) // QVU
        && stringSize(4).test(items.get(2)) // QAV
        && isNumberBetween(1, 65535).test(items.get(3)); // QAP
  }

  private static boolean isSQMConsitent(List<String> items, List<String> allowedValues) {
    return items.isEmpty() || stringInCollection(allowedValues).test(items.get(0)); // FRMP
  }

  private static List<String> extractAllFGPInField(
      NistRecord recordType, RT14FieldsEnum fieldEnum) {
    String field = recordType.getFieldText(fieldEnum).orElse(null);
    return SubFieldToStringConverter.toListUsingSplitByRS(field).stream()
        .map(subfield -> SubFieldToStringConverter.toList(subfield).get(0))
        .filter(StringUtils::isNotBlank)
        .collect(Collectors.toList());
  }

  private static boolean isASEGOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum, String hll, String vll) {
    int nop = items.size() >= 2 ? tryParseIntOrDefault(items.get(1), 0) : 0;
    return items.size() >= 2 + nop * 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0)) // FRAS
        && isNumberBetween(3, 99).test(items.get(1)) // NOP
        && IntStream.range(0, nop).allMatch(i -> isASEGOnePointValid(i, items, hll, vll));
  }

  private static boolean isASEGOnePointValid(int i, List<String> items, String hll, String vll) {
    return isNumberBetween(0, tryParseIntOrDefault(hll, 0)).test(items.get(2 + i * 2)) // HPO
        && isNumberBetween(0, tryParseIntOrDefault(vll, 0)).test(items.get(3 + i * 2)); // VPO
  }
}
