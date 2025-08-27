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
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringInCollection;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;

public class Std2013RT10Validator extends Std2011RT10Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2013;
  }

  protected Std2013RT10Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2013RT10Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  public void rules() {
    super.rules();
    checkForFieldSUB10_046();
    checkForFieldCON10_047();
    checkForFieldPID10_048();
    checkForFieldCID10_049();
    checkForFieldVID10_050();
    checkForFieldRSP10_051();
    checkForFieldT2C10_992();
  }

  protected void checkForFieldT2C10_992() {
    checkForOptionalButCharTypeAndMinMaxLengthField(
        T2C, StdNistValidatorErrorEnum.STD_ERR_T2C_RT10, N, 1, 2);
  }

  protected void checkForFieldRSP10_051() {
    checkForOptionalButUniqueSubfields(
        RSP,
        StdNistValidatorErrorEnum.STD_ERR_RSP_RT10,
        isCharTypeWithMinMaxLength(A, 2, 4), // RSU
        optional(isCharTypeWithMinMaxLength(U, 1, 50)), // RSM
        optional(isCharTypeWithMinMaxLength(U, 1, 50)) // RSO
        );
  }

  protected void checkForFieldVID10_050() {
    checkForOptionalButUniqueSubfields(
        VID,
        StdNistValidatorErrorEnum.STD_ERR_VID_RT10,
        isCharTypeWithMinMaxLength(A, 3, 4), // VIVC
        optional(isCharTypeWithMinLength(U, 1)), // VIDT
        optional(isCharTypeWithMinLength(U, 1)) // VICD
        );
  }

  protected void checkForFieldCID10_049() {
    checkForOptionalButUniqueSubfields(
        CID,
        StdNistValidatorErrorEnum.STD_ERR_CID_RT10,
        optional(isCharTypeWithMinMaxLength(N, 1, 4)), // LPW
        optional(isCharTypeWithMinMaxLength(N, 1, 4)), // LPH
        optional(isCharTypeWithMinMaxLength(N, 1, 4)), // PHW
        optional(isCharTypeWithMinMaxLength(N, 1, 4)), // PHH
        optional(isCharTypeWithMinMaxLength(AS, 1, 3)), // ULCL
        optional(isCharTypeWithMinMaxLength(AS, 1, 3)), // LLCL
        optional(isCharTypeWithMinMaxLength(A, 1, 1)), // LCLD
        optional(isCharTypeWithMinLength(U, 1)), // LPCT
        optional(isCharTypeWithMinMaxLength(NS, 1, 2)), // LPPL
        optional(isCharTypeWithMinLength(U, 1)), // LPPT
        optional(isCharTypeWithMinMaxLength(NS, 1, 1)), // LPSL
        optional(isCharTypeWithMinLength(U, 1)), // LPST
        optional(isCharTypeWithMinMaxLength(N, 1, 1)), // LPMC
        optional(isCharTypeWithMinLength(U, 1)), // LPMT
        optional(isCharTypeWithMinLength(U, 1)), // FHDT
        optional(isCharTypeWithMinLength(U, 1)), // LPDT
        optional(isCharTypeWithMinLength(U, 1)), // LPAT
        optional(isCharTypeWithMinLength(U, 1)) // LPCT
        );
  }

  protected void checkForFieldPID10_048() {
    checkForOptionalButRepeatedSubfields(
        PID,
        StdNistValidatorErrorEnum.STD_ERR_PID_RT10,
        optional(isCharTypeWithMinMaxLength(NS, 1, 30)), // PARC
        optional(isCharTypeWithMinLength(U, 1)) // PADT
        );
  }

  protected void checkForFieldCON10_047() {
    checkForOptionalButCharTypeAndMinLengthField(
        CON, StdNistValidatorErrorEnum.STD_ERR_CON_RT10, U, 1);
  }

  protected void checkForFieldSUB10_046() {
    checkForOptionalButUniqueSubfields(
        SUB,
        StdNistValidatorErrorEnum.STD_ERR_SUB_RT10,
        stringInCollection(asList("X", "A", "D")), // SSC
        optional(stringInCollection(asList("1", "2"))), // SBSC
        optional(stringInCollection(asList("1", "2", "3"))) // SBCC
        );
  }
}
