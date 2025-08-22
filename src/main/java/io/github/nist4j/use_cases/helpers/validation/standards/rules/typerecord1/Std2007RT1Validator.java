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
import static io.github.nist4j.enums.RecordTypeEnum.*;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import java.util.Arrays;
import java.util.List;

public class Std2007RT1Validator extends AbstractRT1NistFileValidator {

  protected Std2007RT1Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2007RT1Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2007;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void rules() {
    // Common rules on R1 record
    ruleFor(NistFile::getRT1TransactionInformationRecord)
        .whenever(not(nullValue()))
        .withValidator(new Standard2007RT1CommonValidator(getNistOptions(), getStandard()));
    // Special rule for CNT
    checkForCNTField();
    // Rules dependent on special resolution record
    List<RecordTypeEnum> recordsWithSpecialResolution = Arrays.asList(RT3, RT4, RT5, RT6, RT7);
    checkForSpecialResolutionFields(recordsWithSpecialResolution);
  }

  public static class Standard2007RT1CommonValidator extends AbstractRT1RecordValidator {
    public Standard2007RT1CommonValidator(
        NistOptions nistOptions, NistStandardEnum nistStandardEnum) {
      super(nistOptions, nistStandardEnum);
    }

    @Override
    public void rules() {
      // Common rules on fields
      checkForMandatoryLENField(RT1FieldsEnum.LEN, StdNistValidatorErrorEnum.STD_ERR_LEN);
      checkForVERField();
      checkForMandatoryField(RT1FieldsEnum.CNT, StdNistValidatorErrorEnum.STD_ERR_CNT_FORMAT_RT1);
      checkForMandatoryCharTypeAndLengthField(
          RT1FieldsEnum.TOT, StdNistValidatorErrorEnum.STD_ERR_TOT_RT1, CharacterTypeEnum.A, 1, 4);
      checkForMandatoryDateField(RT1FieldsEnum.DAT, StdNistValidatorErrorEnum.STD_ERR_DAT_RT1);
      checkForOptionalButNumericFieldBetween(
          RT1FieldsEnum.PRY, StdNistValidatorErrorEnum.STD_ERR_PRY_RT1, 1, 9);
      checkForMandatoryCharTypeAndMinLengthField(
          RT1FieldsEnum.DAI, StdNistValidatorErrorEnum.STD_ERR_DAI_RT1, CharacterTypeEnum.ANS, 1);
      checkForMandatoryCharTypeAndMinLengthField(
          RT1FieldsEnum.ORI, StdNistValidatorErrorEnum.STD_ERR_ORI_RT1, CharacterTypeEnum.ANS, 1);
      checkForMandatoryCharTypeAndMinLengthField(
          RT1FieldsEnum.TCN, StdNistValidatorErrorEnum.STD_ERR_TCN_RT1, CharacterTypeEnum.ANS, 0);
      checkForDOMField();
      checkForOptionalButDateTimeField(
          RT1FieldsEnum.GMT, StdNistValidatorErrorEnum.STD_ERR_GMT_RT1);
    }
  }
}
