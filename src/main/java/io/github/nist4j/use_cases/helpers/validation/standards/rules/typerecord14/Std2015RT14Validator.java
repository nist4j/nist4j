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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14;

import static io.github.nist4j.enums.RecordTypeEnum.RT14;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;

public class Std2015RT14Validator extends Std2013RT14Validator {

  protected Std2015RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2015RT14Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2015;
  }

  @Override
  public void rules() {
    // Common rules on fields
    checkForMandatoryLENField(RT14FieldsEnum.LEN);
    checkForMandatoryNumericFieldBetween(RT14FieldsEnum.IDC, 0, 99);
    checkForMandatoryField(RT14FieldsEnum.SRC);
    checkForMandatoryDateField(RT14FieldsEnum.FCD);
    checkForSLCField(); // 14.008
    checkForFGPField(); // 14.013
    checkForPPDField(); // 14.014
    checkForPPCField(); // 14.015
    checkForAMPField(); // 14.018
    checkForOptionalButCharTypeAndMinMaxLengthField(
        RT14FieldsEnum.COM, CharacterTypeEnum.U, 1, 126);
    checkForSEGField(); // 14.021
    checkForNQMField(); // 14.022
    checkForSQMField(); // 14.023
    checkForFQMField(); // 14.024
    checkForASEGField(); // 14.025
    checkForOptionalButNumericFieldBetween(RT14FieldsEnum.SCF, 1, 255);
    checkForOptionalButInCollectionField(RT14FieldsEnum.SIF, SIF_ALLOWED_VALUE);
    checkForOptionalButInCollectionField(RT14FieldsEnum.DMM, getAllowedValuesForDMM(getStandard()));
    checkForOptionalButInCollectionField(RT14FieldsEnum.FAP, getAllowedValuesForFAP(getStandard()));

    checkForSUBField(); // 14.046
    checkForCONField(); // 14.047

    // Common rules on record

    // Conditional rules
    ruleFor(r -> r)
        .whenever(Std2015RT14Validator::hasImage)
        .withValidator(new Standard2015RT14WithImageValidator(nistOptions, RT14))
        .whenever(r -> !hasImage(r))
        .withValidator(new Standard2015RT14WithoutImageValidator(nistOptions, RT14));
  }

  private static boolean hasImage(NistRecord record14) {
    return record14.getFieldImage(RT14FieldsEnum.DATA).isPresent()
        || record14.getFieldImage(RT14FieldsEnum.EFR).isPresent();
  }

  public class Standard2015RT14WithImageValidator extends Standard2011RT14WithImageValidator {

    @SuppressWarnings("SameParameterValue")
    protected Standard2015RT14WithImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      super.rules();
      checkForMandatoryInCollectionField(RT14FieldsEnum.IMP, getAllowedValuesForIMP(getStandard()));
      checkForOptionalButNumericFieldBetween(RT14FieldsEnum.SHPS, 1, 99999);
      checkForOptionalButNumericFieldBetween(RT14FieldsEnum.SVPS, 1, 99999);
    }
  }

  public static class Standard2015RT14WithoutImageValidator
      extends Standard2011RT14WithoutImageValidator {
    @SuppressWarnings("SameParameterValue")
    protected Standard2015RT14WithoutImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      super.rules();
      checkForEmptyField(RT14FieldsEnum.IMP);
      checkForEmptyField(RT14FieldsEnum.SHPS);
      checkForEmptyField(RT14FieldsEnum.SVPS);
    }
  }
}
