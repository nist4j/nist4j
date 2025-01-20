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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord2;

import static io.github.nist4j.enums.RecordTypeEnum.RT2;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;

public class AllStdRT2Validator extends AbstractNistRecordValidator {

  public AllStdRT2Validator(NistOptions nistOptions) {
    super(nistOptions, RT2);
  }

  @Override
  public void rules() {
    // Common rules on fields
    checkForMandatoryAndRegexField(
        RT14FieldsEnum.LEN, StdNistValidatorErrorEnum.STD_ERR_LEN, "^[1-9]\\d{0,7}$");
    checkForMandatoryNumericFieldBetween(
        RT14FieldsEnum.IDC, StdNistValidatorErrorEnum.STD_ERR_IDC, 0, 99);
  }
}
