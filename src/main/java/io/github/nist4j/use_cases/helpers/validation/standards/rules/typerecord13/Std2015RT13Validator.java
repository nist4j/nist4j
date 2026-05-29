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

import static io.github.nist4j.enums.records.RT13FieldsEnum.IMP;
import static io.github.nist4j.enums.records.RT14FieldsEnum.EFR;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldPresent;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import java.util.function.Predicate;

public class Std2015RT13Validator extends Std2013RT13Validator {

  public Std2015RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  public Std2015RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2015;
  }

  @Override
  public void rules() {
    // Common rules on fields
    super.rules();
    // New Rules
    checkForFieldFCT13_901(); // since 2015
    checkForFieldEFR13_994(); // since 2015
  }

  protected void checkForFieldEFR13_994() {
    checkForGenericFieldEFR_994(EFR, getStandard());
  }

  protected void checkForFieldFCT13_901() {
    Predicate<NistRecord> conditionOptionalField = isFieldPresent(RT13FieldsEnum.FCT);
    checkForGenericFieldFCT_901(RT13FieldsEnum.FCT, IMP, getStandard(), conditionOptionalField);
  }
}
