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

import static io.github.nist4j.enums.CharacterTypeEnum.U;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;

public class Std2011RT13Validator extends Std2007RT13Validator {

  public Std2011RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  public Std2011RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2011;
  }

  @Override
  public void rules() {
    // Common rules on fields
    super.rules();
    // new rules
    checkForFieldANN13_902();
    checkForFieldDUI13_903();
    checkForFieldMMS13_904();
    checkForFieldSAN13_993();
    checkForFieldASC13_995();
    checkForFieldHAS13_996();
    checkForFieldSOR13_997();
    checkForFieldGEO13_998();
  }

  protected void checkForFieldANN13_902() {
    // new in 2011
    checkForGenericFieldANN_902(RT13FieldsEnum.ANN);
  }

  protected void checkForFieldDUI13_903() {
    // new in 2011
    checkForGenericFieldDUI_903(RT13FieldsEnum.DUI);
  }

  protected void checkForFieldMMS13_904() {
    // new in 2011
    checkForGenericFieldMMS_904(RT13FieldsEnum.MMS);
  }

  protected void checkForFieldSAN13_993() {
    // new in 2011
    checkForOptionalButCharTypeAndMinMaxLengthField(RT13FieldsEnum.SAN, U, 1, 125);
  }

  protected void checkForFieldASC13_995() {
    // new in 2011
    checkForGenericFieldASC_995(RT13FieldsEnum.ASC);
  }

  protected void checkForFieldHAS13_996() {
    // new in 2011
    checkForGenericFieldHAS_996(RT13FieldsEnum.HAS);
  }

  protected void checkForFieldSOR13_997() {
    // new in 2011
    checkForGenericFieldSOR_997(RT13FieldsEnum.SOR);
  }

  protected void checkForFieldGEO13_998() {
    // new in 2011
    checkForGenericFieldGEO_998(RT13FieldsEnum.GEO);
  }
}
