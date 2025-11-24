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

import static io.github.nist4j.enums.RecordTypeEnum.RT4;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
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
        .whenever(notNullValue())
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
      checkForMandatoryLENField(RT1FieldsEnum.LEN);
      checkForVERField();
      checkForMandatoryField(RT1FieldsEnum.CNT);
      checkForMandatoryCharTypeAndMinMaxLengthField(RT1FieldsEnum.TOT, CharacterTypeEnum.A, 1, 16);
      checkForMandatoryDateField(RT1FieldsEnum.DAT);
      checkForOptionalButNumericFieldBetween(RT1FieldsEnum.PRY, 1, 9);
      checkForMandatoryCharTypeAndMinLengthField(RT1FieldsEnum.DAI, CharacterTypeEnum.ANS, 1);
      checkForMandatoryCharTypeAndMinLengthField(RT1FieldsEnum.ORI, CharacterTypeEnum.ANS, 1);
      checkForMandatoryCharTypeAndMinLengthField(RT1FieldsEnum.TCN, CharacterTypeEnum.ANS, 1);
      checkForDOMField();
      checkForOptionalButDateTimeField(RT1FieldsEnum.GMT);
      checkForDCSField();
      checkForANMField();
    }
  }
}
