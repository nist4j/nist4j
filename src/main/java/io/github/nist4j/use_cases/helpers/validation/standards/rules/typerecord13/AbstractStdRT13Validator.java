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

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.isNumeric;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringInCollection;
import static br.com.fluentvalidator.predicate.StringPredicate.stringMatches;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSize;
import static io.github.nist4j.enums.RecordTypeEnum.RT13;
import static io.github.nist4j.enums.records.RT13FieldsEnum.CGA;
import static io.github.nist4j.enums.records.RT13FieldsEnum.FGP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IMP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LQM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.PPC;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.FINGERS_PALMS_AND_COMBINATION;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_CGA_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_FGP_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IMP_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LQM_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_PPC_RT13;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates.isNumberBetween;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.getFieldStringOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.isFieldAbsent;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum;
import io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractStdRT13Validator extends AbstractNistRecordValidator {

  protected static final List<String> SLC_ALLOWED_VALUES =
      Collections.unmodifiableList(Arrays.asList("0", "1", "2"));
  private static final Set<String> PPC_ALLOWED_VALUES_FOR_FIRST_SUBFIELD =
      new HashSet<>(Arrays.asList("FV1", "FV2", "FV3", "FV4", "TIP", "TPP", "NA"));
  private static final Set<String> PPC_ALLOWED_VALUES_FOR_SECOND_SUBFIELD =
      new HashSet<>(Arrays.asList("PRX", "DST", "MED", "NA"));

  protected abstract NistStandardEnum getStandard();

  protected AbstractStdRT13Validator(final NistOptions nistOptions) {
    super(nistOptions, RT13);
  }

  protected void checkForIMPField() {
    checkForMandatoryInCollectionField(
        IMP, STD_ERR_IMP_MANDATORY_RT13, getAllowedValuesForIMP(getStandard()));
  }

  private static List<String> getAllowedValuesForIMP(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefImpressionTypeEnum.values(), nistStandard);
  }

  protected void checkForCGAField() {
    checkForMandatoryInCollectionField(
        CGA, STD_ERR_CGA_MANDATORY_RT13, getAllowedValuesForCGA(getStandard()));
  }

  private static List<String> getAllowedValuesForCGA(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefCompressionAlgorithmEnum.values(), nistStandard);
  }

  protected void checkForFGPField() {
    checkCustomPredicateOnField(
        FGP, STD_ERR_FGP_RT13, not(stringEmptyOrNull()).and(validateFieldFGP(getStandard())));
  }

  protected void checkForLQMField() {
    ruleFor(r -> r)
        // match format, if present
        .must(isFieldAbsent(LQM).or(validateFieldLQM(getStandard())))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(STD_ERR_LQM_RT13));
  }

  protected void checkForPPCField() {
    ruleFor(r -> r)
        // Can be present, if eji(19) value in FGP
        .must(isFieldAbsent(PPC).or(validateFieldPPC()))
        .when(isEJIFingerprint())
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(STD_ERR_PPC_RT13))
        // Should be absent, if FGP not equals to EJI(19)
        .must(isFieldAbsent(PPC))
        .when(not(isEJIFingerprint()))
        .handlerInvalidField(handlerInvalidFieldInRecordWithError(STD_ERR_PPC_RT13));
  }

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

  private static boolean isPPCOneFingerValid(List<String> items) {
    return items.size() == 6
        && stringInCollection(PPC_ALLOWED_VALUES_FOR_FIRST_SUBFIELD).test(items.get(0))
        && stringInCollection(PPC_ALLOWED_VALUES_FOR_SECOND_SUBFIELD).test(items.get(1))
        && isNumeric().test(items.get(2))
        && isNumeric().test(items.get(3))
        && isNumeric().test(items.get(4))
        && isNumeric().test(items.get(5));
  }

  protected static Predicate<NistRecord> validateFieldPPC() {
    return r -> {
      String ppcField = getFieldStringOrNull(RT13FieldsEnum.PPC, r);
      if (ppcField == null) {
        return false;
      }

      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(ppcField);
      return subFields.stream()
          .allMatch(subfield -> isPPCOneFingerValid(SubFieldToStringConverter.toList(subfield)));
    };
  }

  protected static Predicate<NistRecord> isEJIFingerprint() {
    return r -> {
      String fgp = getFieldStringOrNull(RT13FieldsEnum.FGP, r);
      return fgp != null && fgp.contains(NistRefFrictionRidgePositionEnum.EJI_OR_TIPS.getCode());
    };
  }

  protected static List<String> getFTPCombinationFingers(NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(FINGERS_PALMS_AND_COMBINATION, nistStandardEnum);
  }

  protected static boolean isQualityOneFingerValid(
      List<String> items, NistStandardEnum nistStandardEnum) {
    return items.size() >= 4
        && stringInCollection(getFTPCombinationFingers(nistStandardEnum)).test(items.get(0)) // FRMP
        && stringMatches("^(([1-9]?\\d{1})|100|254|255)$").test(items.get(1)) // QVU
        && stringSize(4).test(items.get(2)) // QAV
        && isNumberBetween(1, 65535).test(items.get(3)); // QAP
  }

  protected static Predicate<NistRecord> validateFieldLQM(NistStandardEnum nistStandard) {
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
