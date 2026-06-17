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

import static io.github.nist4j.enums.RecordTypeEnum.RT13;
import static io.github.nist4j.enums.records.RT13FieldsEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.FINGERS_PALMS_AND_COMBINATION;
import static io.github.nist4j.enums.ref.image.NistRefImpTypeGroupEnum.LATENT;
import static io.github.nist4j.enums.ref.image.NistRefImpTypeGroupEnum.NO_GROUP;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.mandatory;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.isNumberBetween;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.isNumeric;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringInCollection;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringMatches;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringSize;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum;
import io.github.nist4j.enums.ref.image.NistRefJointImageSegmentsTipAndFingerViewCodeEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractStdRT13Validator extends AbstractNistRecordValidator {

  protected static final List<String> SLC_ALLOWED_VALUES =
      Collections.unmodifiableList(asList("0", "1", "2"));

  protected abstract NistStandardEnum getStandard();

  protected AbstractStdRT13Validator(final NistOptions nistOptions) {
    super(nistOptions, RT13);
  }

  protected void checkForFieldIMP13_003() {
    checkForMandatoryInCollectionField(IMP, getAllowedValuesForIMP(getStandard()));
  }

  private List<String> getAllowedValuesForIMP(NistStandardEnum nistStandard) {
    List<NistRefImpressionTypeEnum> valuesOfIMP =
        NistRefImpressionTypeEnum.listByAnyGroups(LATENT, NO_GROUP);
    return findCodesAllowedByStandard(valuesOfIMP, nistStandard);
  }

  protected void checkForFieldCGA13_011() {
    checkForMandatoryInCollectionField(CGA, getAllowedValuesForCGA(this.recordType, getStandard()));
  }

  protected void checkForFieldFGP13_013() {
    checkCustomPredicateOnField(FGP, STD_ERR_FGP, mandatory(validateFieldFGP(getStandard())));
  }

  protected void checkForFieldLQM13_024() {
    ruleFor(r -> r)
        // match format, if present
        .must(isFieldAbsent(LQM).or(validateFieldLQM(getStandard())))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(this.recordType, LQM, STD_ERR_LQM_RT13));
  }

  protected void checkForFieldPPC13_015() {
    // Can be present, if eji(19) value in FGP
    ruleFor(r -> r)
        .must(validateFieldPPC(getStandard()))
        .when(isEJIFingerprint().and(isFieldPresent(PPC)))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(this.recordType, PPC, STD_ERR_PPC_1));
    // Should be absent, if FGP not equals to EJI(19)
    ruleFor(r -> r)
        .must(isFieldAbsent(PPC))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(this.recordType, PPC, STD_ERR_PPC_2));
  }

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
        && stringInCollection(allowedFVCValues).test(items.get(0)) // FVC
        && stringInCollection(allowedLOSValues).test(items.get(1)) // LOS
        && isNumeric().test(items.get(2)) // LHC
        && isNumeric().test(items.get(3)) // RHC
        && isNumeric().test(items.get(4)) // TVC
        && isNumeric().test(items.get(5)) // BVC
    ;
  }

  protected Predicate<NistRecord> validateFieldPPC(NistStandardEnum nistStandard) {
    return r -> {
      String ppcField = getFieldStringOrNull(RT13FieldsEnum.PPC, r);
      if (ppcField == null) {
        return false;
      }

      List<List<String>> subFields = SubFieldToStringConverter.toListOfList(ppcField);
      return subFields.stream().allMatch(subfield -> isPPCOneFingerValid(subfield, nistStandard));
    };
  }

  protected Predicate<NistRecord> isEJIFingerprint() {
    return r -> {
      String fgp = getFieldStringOrNull(RT13FieldsEnum.FGP, r);
      return fgp != null && fgp.contains(NistRefFrictionRidgePositionEnum.EJI_OR_TIPS.getCode());
    };
  }

  protected List<String> getFTPCombinationFingers(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(FINGERS_PALMS_AND_COMBINATION, nistStandardEnum);
  }

  protected boolean isQualityOneFingerValid(List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() >= 4
        && stringInCollection(getFTPCombinationFingers(nistStandardEnum)).test(items.get(0)) // FRMP
        && stringMatches("^(([1-9]?\\d{1})|100|254|255)$").test(items.get(1)) // QVU
        && stringSize(4).test(items.get(2)) // QAV
        && isNumberBetween(1, 65535).test(items.get(3)) // QAP
        && items.size() < 5;
  }

  protected Predicate<NistRecord> validateFieldLQM(NistStandardEnum nistStandard) {
    return r -> {
      List<String> subFields =
          SubFieldToStringConverter.toListUsingSplitByRS(
              getFieldStringOrNull(RT13FieldsEnum.LQM, r));
      return subFields.stream()
          .allMatch(
              subfield ->
                  isQualityOneFingerValid(
                      SubFieldToStringConverter.toList(subfield), nistStandard));
    };
  }
}
