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

import static io.github.nist4j.enums.CharacterTypeEnum.H;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findValuesAllowedByStandard;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.*;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.FrictionRidgeType.FINGERS_COMBINATION;
import static io.github.nist4j.enums.ref.image.NistRefImpTypeGroupEnum.*;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.converters.NumericFieldConverter.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.getFieldStringOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.ref.fp.NistRefAmputationBandagedFPEnum;
import io.github.nist4j.enums.ref.fp.NistRefAquisitionProfilFPEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import io.github.nist4j.enums.ref.image.NistRefDeviceMonitoringModeEnum;
import io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum;
import io.github.nist4j.enums.ref.image.NistRefJointImageSegmentsTipAndFingerViewCodeEnum;
import io.github.nist4j.use_cases.helpers.conditions.StringCondition;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class AbstractStdRT14Validator extends AbstractNistRecordValidator {

  protected static final List<String> SLC_ALLOWED_VALUES =
      Collections.unmodifiableList(Arrays.asList("0", "1", "2"));
  protected static final List<String> SIF_ALLOWED_VALUE = Collections.singletonList("Y");

  protected AbstractStdRT14Validator(NistOptions nistOptions) {
    super(nistOptions, RT14);
  }

  protected List<String> getAllowedValuesForIMP(NistStandardEnum nistStandard) {
    List<NistRefImpressionTypeEnum> valuesOfIMP =
        NistRefImpressionTypeEnum.listByAnyGroups(FINGER, NO_GROUP);
    return findCodesAllowedByStandard(valuesOfIMP, nistStandard);
  }

  protected List<String> getFGPFingersCombinationExceptEJI(NistStandardEnum nistStandard) {
    return findValuesAllowedByStandard(NistRefFrictionRidgePositionEnum.values(), nistStandard)
        .stream()
        .filter(
            frp ->
                FINGERS_COMBINATION.equals(frp.getType())
                    && !EJI_OR_TIPS.getCode().equals(frp.getCode()))
        .map(NistRefFrictionRidgePositionEnum::getCode)
        .collect(Collectors.toList());
  }

  protected List<String> getFGPUnitaryFingers(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(TEN_FINGERS, nistStandardEnum);
  }

  protected List<String> getAllowedValuesForFAP(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(NistRefAquisitionProfilFPEnum.values(), nistStandardEnum);
  }

  protected List<String> getAllowedValuesForDMM(NistStandardEnum standardEnum) {
    return findCodesAllowedByStandard(NistRefDeviceMonitoringModeEnum.values(), standardEnum);
  }

  protected Predicate<NistRecord> isEJIFingerprint() {
    return r -> {
      String fgp = getFieldStringOrNull(RT14FieldsEnum.FGP, r);
      //noinspection ConstantValue
      return fgp != null && fgp.contains(NistRefFrictionRidgePositionEnum.EJI_OR_TIPS.getCode());
    };
  }

  // 14.013
  protected Predicate<String> validateFieldFGP(NistStandardEnum nistStandard) {
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
  protected Predicate<NistRecord> validateFieldSEG(NistStandardEnum nistStandard) {
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

  protected Predicate<NistRecord> validateFieldPPD(NistStandardEnum nistStandardEnum) {
    return r -> {
      List<Pair<String, String>> subFields =
          SubFieldToStringConverter.toListOfPairs((getFieldStringOrNull(RT14FieldsEnum.PPD, r)));
      return subFields.stream()
          .allMatch(subfield -> isPPDOneFingerValid(subfield, nistStandardEnum));
    };
  }

  protected Predicate<NistRecord> validateFieldPPC(NistStandardEnum nistStandard) {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              (getFieldStringOrNull(RT14FieldsEnum.PPC, r)));
      return subFields.stream()
          .allMatch(
              subfield ->
                  isPPCOneFingerValid(SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected Predicate<String> validateFieldAMP(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isAMPOneFingerValid(SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected Predicate<String> validateFieldNQM(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isNQMOneFingerValid(SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected Predicate<String> validateFieldFQM(NistStandardEnum nistStandard) {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield ->
                  isQualityOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }

  protected Predicate<NistRecord> validateFieldSQM(NistStandardEnum nistStandard) {
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

  protected Predicate<NistRecord> validateConsistencySQM() {
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

  protected Predicate<NistRecord> validateConsistencyASEG(NistStandardEnum nistStandardEnum) {
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

  protected boolean isSEGOneFingerValid(
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

  @SuppressWarnings("DuplicatedCode")
  protected boolean isPPCOneFingerValid(List<String> items, NistStandardEnum nistStandard) {
    List<String> allowedFVCValues =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(recordType, "FVC"),
            nistStandard);

    List<String> allowedLOSValues =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(recordType, "LOS"),
            nistStandard);

    return items.size() == 6
        && stringInCollection(allowedFVCValues).test(items.get(0))
        && stringInCollection(allowedLOSValues).test(items.get(1))
        && isNumeric().test(items.get(2))
        && isNumeric().test(items.get(3))
        && isNumeric().test(items.get(4))
        && isNumeric().test(items.get(5));
  }

  protected boolean isPPDOneFingerValid(
      Pair<String, String> items, NistStandardEnum nistStandardEnum) {
    List<String> allowedFICValues =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(recordType, "FIC"),
            nistStandardEnum);

    return stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.getKey())
        && stringInCollection(allowedFICValues).test(items.getValue());
  }

  protected boolean isAMPOneFingerValid(List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() == 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0))
        && stringInCollection(
                findCodesAllowedByStandard(
                    NistRefAmputationBandagedFPEnum.values(), nistStandardEnum))
            .test(items.get(1));
  }

  protected boolean isNQMOneFingerValid(List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() == 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0))
        && stringMatches("^([1-5]|254|255)$").test(items.get(1));
  }

  protected boolean isQualityOneFingerValid(List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() == 4
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0)) // FRMP
        && stringMatches("^(([1-9]?\\d{1})|100|254|255)$").test(items.get(1)) // QVU
        && isCharTypeWithMinMaxLength(H, 4, 4).test(items.get(2)) // QAV
        && isNumberBetween(1, 65535).test(items.get(3)); // QAP
  }

  protected boolean isSQMConsitent(List<String> items, List<String> allowedValues) {
    return items.isEmpty() || stringInCollection(allowedValues).test(items.get(0)); // FRMP
  }

  protected List<String> extractAllFGPInField(NistRecord recordType, RT14FieldsEnum fieldEnum) {
    String field = recordType.getFieldText(fieldEnum).orElse(null);
    return SubFieldToStringConverter.toListUsingSplitByRS(field).stream()
        .map(subfield -> SubFieldToStringConverter.toList(subfield).get(0))
        .filter(StringCondition::isNotBlank)
        .collect(Collectors.toList());
  }

  protected boolean isASEGOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum, String hll, String vll) {
    int nop = items.size() >= 2 ? tryParseIntOrDefault(items.get(1), 0) : 0;
    return items.size() >= 2 + nop * 2
        && stringInCollection(getFGPUnitaryFingers(nistStandardEnum)).test(items.get(0)) // FRAS
        && isNumberBetween(3, 99).test(items.get(1)) // NOP
        && IntStream.range(0, nop).allMatch(i -> isASEGOnePointValid(i, items, hll, vll));
  }

  protected boolean isASEGOnePointValid(int i, List<String> items, String hll, String vll) {
    return isNumberBetween(0, tryParseIntOrDefault(hll, 0)).test(items.get(2 + i * 2)) // HPO
        && isNumberBetween(0, tryParseIntOrDefault(vll, 0)).test(items.get(3 + i * 2)); // VPO
  }
}
