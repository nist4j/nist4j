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
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;

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
    checkForMandatoryAndRegexField(
        RT14FieldsEnum.LEN, StdNistValidatorErrorEnum.STD_ERR_LEN, "^[1-9]\\d{0,7}$");
    checkForMandatoryNumericFieldBetween(
        RT14FieldsEnum.IDC, StdNistValidatorErrorEnum.STD_ERR_IDC, 0, 99);
    checkForMandatoryField(RT14FieldsEnum.SRC, StdNistValidatorErrorEnum.STD_ERR_SRC);
    checkForMandatoryDateField(RT14FieldsEnum.FCD, StdNistValidatorErrorEnum.STD_ERR_FCD_RT14);
    checkForSLCField(); // 14.008
    checkForFGPField(); // 14.013
    checkForPPDField(); // 14.014
    checkForPPCField(); // 14.015
    checkForAMPField(); // 14.018
    checkForOptionalButAlphaNumWithMinMaxLengthField(
        RT14FieldsEnum.COM, StdNistValidatorErrorEnum.STD_ERR_COM_RT14, 1, 26);
    checkForSEGField(); // 14.021
    checkForNQMField(); // 14.022
    checkForSQMField(); // 14.023
    checkForFQMField(); // 14.024
    checkForASEGField(); // 14.025
    checkForOptionalButNumericFieldBetween(
        RT14FieldsEnum.SCF, StdNistValidatorErrorEnum.STD_ERR_SCF_RT14, 1, 255);
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.SIF, StdNistValidatorErrorEnum.STD_ERR_SIF_RT14, SIF_ALLOWED_VALUE);
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.DMM,
        StdNistValidatorErrorEnum.STD_ERR_DMM,
        getAllowedValuesForDMM(getStandard()));
    checkForOptionalButInCollectionField(
        RT14FieldsEnum.FAP,
        StdNistValidatorErrorEnum.STD_ERR_FAP_RT14,
        getAllowedValuesForFAP(getStandard()));

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

    protected Standard2015RT14WithImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      super.rules();
      checkForMandatoryInCollectionField(
          RT14FieldsEnum.IMP,
          StdNistValidatorErrorEnum.STD_ERR_IMP_MANDATORY_RT14,
          getAllowedValuesForIMP(getStandard()));
      checkForOptionalButRegexField(
          RT14FieldsEnum.SHPS, StdNistValidatorErrorEnum.STD_ERR_SHPS_O_RT14, "^\\d{1,5}$");
      checkForOptionalButRegexField(
          RT14FieldsEnum.SVPS, StdNistValidatorErrorEnum.STD_ERR_SVPS_O_RT14, "^\\d{1,5}$");
    }
  }

  public static class Standard2015RT14WithoutImageValidator
      extends Standard2011RT14WithoutImageValidator {
    protected Standard2015RT14WithoutImageValidator(
        NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      super.rules();
      checkForEmptyField(
          RT14FieldsEnum.IMP, StdNistValidatorErrorEnum.STD_ERR_IMP_NOT_ALLOWED_RT14);
      checkForEmptyField(
          RT14FieldsEnum.SHPS, StdNistValidatorErrorEnum.STD_ERR_SHPS_NOT_ALLOWED_RT14);
      checkForEmptyField(
          RT14FieldsEnum.SVPS, StdNistValidatorErrorEnum.STD_ERR_SVPS_NOT_ALLOWED_RT14);
    }
  }
}
