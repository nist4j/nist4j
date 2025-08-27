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

import static io.github.nist4j.enums.CharacterTypeEnum.U;
import static io.github.nist4j.enums.records.RT10FieldsEnum.PID;
import static io.github.nist4j.enums.records.RT14FieldsEnum.EFR;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;

public class Std2015RT10Validator extends Std2013RT10Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2015;
  }

  protected Std2015RT10Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2015RT10Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  public void rules() {
    super.rules();
    checkForFieldEFR10_994();
  }

  protected void checkForFieldEFR10_994() {
    checkForOptionalButCharTypeAndMinMaxLengthField(
        EFR, StdNistValidatorErrorEnum.STD_ERR_EFR_RT10, U, 1, 200);
  }

  /*NS became U encoding*/
  @Override
  protected void checkForFieldPID10_048() {
    checkForOptionalButRepeatedSubfields(
        PID,
        StdNistValidatorErrorEnum.STD_ERR_PID_RT10,
        optional(isCharTypeWithMinMaxLength(U, 1, 30)), // PARC
        optional(isCharTypeWithMinLength(U, 1)) // PADT
        );
  }
}
