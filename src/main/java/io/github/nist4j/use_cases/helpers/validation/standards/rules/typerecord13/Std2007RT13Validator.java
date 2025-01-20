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

import static io.github.nist4j.enums.records.RT13FieldsEnum.BPX;
import static io.github.nist4j.enums.records.RT13FieldsEnum.COM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.DATA;
import static io.github.nist4j.enums.records.RT13FieldsEnum.HLL;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IDC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LCD;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LEN;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SHPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SLC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SRC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SVPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.THPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.TVPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.VLL;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_BPX_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_COM_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_DATA_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_HLL_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IDC;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LCD_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LEN_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SHPS_O_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SLC_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SRC_36;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SVPS_O_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_THPS_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_TVPS_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_VLL_MANDATORY_RT13;
import static io.github.nist4j.use_cases.helpers.builders.validation.NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;

public class Std2007RT13Validator extends AbstractStdRT13Validator {

  public Std2007RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2007RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2007;
  }

  @Override
  public void rules() {
    checkForMandatoryAndRegexField(LEN, STD_ERR_LEN_RT13, "^[1-9]\\d{0,7}$");
    checkForMandatoryNumericFieldBetween(IDC, STD_ERR_IDC, 0, 99);
    checkForIMPField();
    checkForMandatoryAlphaNumWithMinMaxLengthField(SRC, STD_ERR_SRC_36, 1, 36);
    checkForMandatoryDateField(LCD, STD_ERR_LCD_RT13);
    checkForMandatoryNumericFieldBetween(HLL, STD_ERR_HLL_MANDATORY_RT13, 1, 99999);
    checkForMandatoryNumericFieldBetween(VLL, STD_ERR_VLL_MANDATORY_RT13, 1, 99999);
    checkForMandatoryInCollectionField(SLC, STD_ERR_SLC_MANDATORY_RT13, SLC_ALLOWED_VALUES);
    checkForMandatoryNumericField(THPS, STD_ERR_THPS_MANDATORY_RT13);
    checkForMandatoryNumericField(TVPS, STD_ERR_TVPS_MANDATORY_RT13);
    checkForCGAField();
    checkForMandatoryNumericFieldBetween(BPX, STD_ERR_BPX_MANDATORY_RT13, 8, 99);
    checkForFGPField();
    checkForPPCField(); // 13.015
    checkForOptionalButRegexField(SHPS, STD_ERR_SHPS_O_RT13, "^\\d{1,5}$");
    checkForOptionalButRegexField(SVPS, STD_ERR_SVPS_O_RT13, "^\\d{1,5}$");
    checkForOptionalButRegexField(COM, STD_ERR_COM_RT13, REGEXP_ANS_ANY_LENGTH);
    checkForLQMField();
    // LQM
    checkForMandatoryDataField(DATA, STD_ERR_DATA_RT13);
  }
}
