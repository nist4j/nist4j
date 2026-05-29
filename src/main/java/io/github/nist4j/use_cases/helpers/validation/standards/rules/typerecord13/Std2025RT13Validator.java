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

import static io.github.nist4j.enums.CharacterTypeEnum.*;
import static io.github.nist4j.enums.records.RT13FieldsEnum.*;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.CONTACTLESS_CAPTURE;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LQM_RT13;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldEquals;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldPresent;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import java.util.List;
import java.util.function.Predicate;

public class Std2025RT13Validator extends Std2015RT13Validator {

  public Std2025RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  public Std2025RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2025;
  }

  @Override
  public void rules() {
    // Common rules on fields
    super.rules();
    // New rules in 2025
    checkForFieldCSP13_021(); // since 2025 - New field
    checkForFieldFQC13_029(); // since 2025 - New field
    checkForFieldBRI13_199(); // since 2025 - New field
  }

  protected void checkForFieldCSP13_021() {
    checkForGenericFieldCSP_xxx(RT13FieldsEnum.CSP, RT13FieldsEnum.BPX, getStandard());
  }

  protected void checkForFieldFQC13_029() {
    checkForGenericFieldFQC_029(
        RT13FieldsEnum.FQC, NistRefFrictionRidgePositionEnum.ALL, getStandard());
  }

  protected void checkForFieldBRI13_199() {
    checkForGenericFieldBRI_199(RT13FieldsEnum.BRI);
  }

  @Override
  protected void checkForFieldLQM13_024() {
    // 13.024 LQM - Added new information items to describe algorithms.
    // Removed the upper limit of 9 subfields
    List<String> allowedFTP = getFTPCombinationFingers(getStandard());
    checkForOptionalButRepeatedSubfields(
        LQM,
        STD_ERR_LQM_RT13,
        SubfieldRule.of("FRMP", stringInCollection(allowedFTP)),
        SubfieldRule.of("QVU", stringMatches("^(([1-9]?\\d{1})|100|254|255)$")),
        SubfieldRule.of("QAV", isHexaCodeWithLength(4)),
        SubfieldRule.of("QAP", isNumberBetween(1, 65535)),
        SubfieldRule.of("QPV", optional(isCharTypeWithMinMaxLength(U, 1, 256))),
        SubfieldRule.of("QCM", optional(isCharTypeWithMinMaxLength(U, 1, 256))),
        SubfieldRule.of("QCK", optional(isHexaCodeWithLength(64))));
  }

  @Override
  protected void checkForFieldFCT13_901() {
    // since 2025 - New mandatory condition, required for contactless fingerprints
    Predicate<NistRecord> conditionMandatoryWhenIMPequals43 =
        isFieldPresent(IMP).and(isFieldEquals(IMP, CONTACTLESS_CAPTURE.getCode()));
    checkForGenericFieldFCT_901(FCT, IMP, getStandard(), conditionMandatoryWhenIMPequals43);
  }
}
