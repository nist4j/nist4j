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

import static io.github.nist4j.enums.CharacterTypeEnum.ANS;
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

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.CharacterTypeEnum;
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

  @SuppressWarnings("DuplicatedCode")
  @Override
  public void rules() {
    checkForMandatoryLENField(LEN);
    checkForMandatoryNumericFieldBetween(IDC, 0, 99);
    checkForIMPField();
    checkForMandatoryCharTypeAndMinMaxLengthField(SRC, ANS, 1, 36);
    checkForMandatoryDateField(LCD);
    checkForMandatoryNumericFieldBetween(HLL, 1, 99999);
    checkForMandatoryNumericFieldBetween(VLL, 1, 99999);
    checkForMandatoryInCollectionField(SLC, SLC_ALLOWED_VALUES);
    checkForMandatoryNumericFieldBetween(THPS, 1, 99999);
    checkForMandatoryNumericFieldBetween(TVPS, 1, 99999);
    checkForCGAField();
    checkForMandatoryNumericFieldBetween(BPX, 8, 99);
    checkForFGPField();
    checkForPPCField(); // 13.015
    checkForOptionalButNumericFieldBetween(SHPS, 1, 99999);
    checkForOptionalButNumericFieldBetween(SVPS, 1, 99999);
    checkForOptionalButCharTypeAndMinMaxLengthField(COM, CharacterTypeEnum.AN, 1, 128);
    checkForLQMField();
    // LQM
    checkForMandatoryImageField(DATA);
  }
}
