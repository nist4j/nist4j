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
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringStartingWith;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import java.util.function.Predicate;
import lombok.NonNull;

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
    checkForOptionalButCharTypeAndMinMaxLengthField(T2C, N, 1, 2);
  }

  protected void checkForFieldRSP10_051() {
    checkForOptionalButUniqueSubfields(
        RSP,
        StdNistValidatorErrorEnum.STD_ERR_RSP,
        SubfieldRule.of("RSU", isCharTypeWithMinMaxLength(A, 2, 4)), //
        SubfieldRule.of("RSM", optional(isCharTypeWithMinMaxLength(U, 1, 50))), //
        SubfieldRule.of("RSO", optional(isCharTypeWithMinMaxLength(U, 1, 50))) //
        );
  }

  protected void checkForFieldVID10_050() {
    checkForOptionalButUniqueSubfields(
        VID,
        StdNistValidatorErrorEnum.STD_ERR_VID,
        SubfieldRule.of("VIVC", isCharTypeWithMinMaxLength(A, 3, 4)), // VIVC
        SubfieldRule.of("VIDT", optional(isCharTypeWithMinLength(U, 1))), // VIDT
        SubfieldRule.of("VICD", optional(isCharTypeWithMinLength(U, 1))) // VICD
        );
  }

  protected void checkForFieldCID10_049() {
    checkForOptionalButUniqueSubfields(
        CID,
        StdNistValidatorErrorEnum.STD_ERR_CID,
        SubfieldRule.of("LPW", optional(isCharTypeWithMinMaxLength(N, 1, 4))),
        SubfieldRule.of("LPH", optional(isCharTypeWithMinMaxLength(N, 1, 4))),
        SubfieldRule.of("PHW", optional(isCharTypeWithMinMaxLength(N, 1, 4))),
        SubfieldRule.of("PHH", optional(isCharTypeWithMinMaxLength(N, 1, 4))),
        SubfieldRule.of("ULCL", optional(separatedChatTypeListWithMinMaxLength(AS, 1, 3, "|"))),
        SubfieldRule.of("LLCL", optional(separatedChatTypeListWithMinMaxLength(AS, 1, 3, "|"))),
        SubfieldRule.of("LCLD", optional(isCharTypeWithMinMaxLength(A, 1, 1))),
        SubfieldRule.of("LPCT", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("LPPL", optional(separatedChatTypeListWithMinLength(NS, 1, "|"))),
        SubfieldRule.of("LPPT", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("LPSL", optional(separatedChatTypeListWithMinLength(NS, 1, "|"))),
        SubfieldRule.of("LPST", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("LPMC", optional(isCharTypeWithMinMaxLength(N, 1, 1))),
        SubfieldRule.of("LPMT", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("FHDT", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("LPDT", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("LPAT", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("LPCT", optional(isCharTypeWithMinLength(U, 1))));
  }

  @SuppressWarnings("SameParameterValue")
  private @NonNull Predicate<String> separatedChatTypeListWithMinMaxLength(
      @NonNull CharacterTypeEnum charType, int min, int max, @NonNull String separator) {
    return str -> {
      for (String elt : str.split(separator)) {
        boolean isValid = isCharTypeWithMinMaxLength(charType, min, max).test(elt);
        if (!isValid) {
          return false;
        }
      }
      return true;
    };
  }

  @SuppressWarnings("SameParameterValue")
  private @NonNull Predicate<String> separatedChatTypeListWithMinLength(
      @NonNull CharacterTypeEnum charType, int min, @NonNull String separator) {
    return str -> {
      for (String elt : str.split(separator)) {
        boolean isValid = isCharTypeWithMinLength(charType, min).test(elt);
        if (!isValid) {
          return false;
        }
      }
      return true;
    };
  }

  protected void checkForFieldPID10_048() {
    checkForOptionalButRepeatedSubfields(
        PID,
        StdNistValidatorErrorEnum.STD_ERR_PID,
        SubfieldRule.of(
            "PARC", optional(isCharTypeWithMinMaxLength(NS, 1, 30).or(stringStartingWith("ADA ")))),
        SubfieldRule.of("PADT", optional(isCharTypeWithMinLength(U, 1))));
  }

  protected void checkForFieldCON10_047() {
    checkForOptionalButCharTypeAndMinLengthField(CON, U, 1);
  }

  protected void checkForFieldSUB10_046() {
    checkForGenericFieldSUB_046(SUB, getStandard());
  }
}
