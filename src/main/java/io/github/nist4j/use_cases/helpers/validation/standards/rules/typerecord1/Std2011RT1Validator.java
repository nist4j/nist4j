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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord1;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static io.github.nist4j.enums.RecordTypeEnum.RT4;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.enums.validation.RegexpCharacterTypeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.validation.NistValidationRegexBuilder;
import java.util.Collections;

public class Std2011RT1Validator extends AbstractRT1NistFileValidator {

  protected Std2011RT1Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2011RT1Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2011;
  }

  @Override
  public void rules() {
    // Common rules on R1 record
    ruleFor(NistFile::getRT1TransactionInformationRecord)
        .whenever(not(nullValue()))
        .withValidator(new Standard2011RT1CommonValidator(getNistOptions(), getStandard()));
    // Special rule for CNT
    checkForCNTField();
    // Rules dependent on special resolution record
    checkForSpecialResolutionFields(
        Collections.singletonList(
            RT4)); // From this standard, only type-4 counts (3, 5, 6, 7 deprecated)
  }

  public static class Standard2011RT1CommonValidator extends AbstractRT1RecordValidator {

    public Standard2011RT1CommonValidator(
        NistOptions nistOptions, NistStandardEnum nistStandardEnum) {
      super(nistOptions, nistStandardEnum);
    }

    @Override
    public void rules() {
      checkForMandatoryAndRegexField(
          RT1FieldsEnum.LEN, StdNistValidatorErrorEnum.STD_ERR_LEN, "^[1-9]\\d{0,7}$");
      checkForVERField();
      checkForMandatoryField(RT1FieldsEnum.CNT, StdNistValidatorErrorEnum.STD_ERR_CNT_FORMAT_RT1);
      checkForMandatoryAndRegexField(
          RT1FieldsEnum.TOT,
          StdNistValidatorErrorEnum.STD_ERR_TOT_RT1,
          new NistValidationRegexBuilder()
              .min(1)
              .max(16)
              .allowsChar(RegexpCharacterTypeEnum.ALPHABETIC)
              .build());
      checkForMandatoryDateField(RT1FieldsEnum.DAT, StdNistValidatorErrorEnum.STD_ERR_DAT_RT1);
      checkForOptionalButRegexField(
          RT1FieldsEnum.PRY, StdNistValidatorErrorEnum.STD_ERR_PRY_RT1, "^[1-9]$");
      checkForMandatoryAndRegexField(
          RT1FieldsEnum.DAI,
          StdNistValidatorErrorEnum.STD_ERR_DAI_RT1,
          NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH);
      checkForMandatoryAndRegexField(
          RT1FieldsEnum.ORI,
          StdNistValidatorErrorEnum.STD_ERR_ORI_RT1,
          NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH);
      checkForMandatoryAndRegexField(
          RT1FieldsEnum.TCN,
          StdNistValidatorErrorEnum.STD_ERR_TCN_RT1,
          NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH);
      checkForDOMField();
      checkForOptionalButDateTimeField(
          RT1FieldsEnum.GMT, StdNistValidatorErrorEnum.STD_ERR_GMT_RT1);
      checkForDCSField();
      checkForANMField();
    }
  }
}
