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
package io.github.nist4j.enums.validation;

import io.github.nist4j.enums.records.*;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StdNistValidatorErrorEnum implements INistValidationErrorEnum {
  STD_ERR_MISSING_STANDARD("NIST standard invalid", RT1FieldsEnum.VER),
  STD_ERR_UNIMPLEMENTED_STANDARD("NIST standard not implemented", RT1FieldsEnum.VER),
  STD_ERR_MISSING_RT("Record Type is missing for this NIST standard", RT1FieldsEnum.TOT),
  STD_ERR_BAD_RT("Record Type is not conform with the NIST standard", RT1FieldsEnum.TOT),
  STD_ERR_FORBIDDEN_RT("Record type not allowed for this NIST standard", RT1FieldsEnum.TOT),
  STD_ERR_LEN("The expected format is : 1 to 10 digits", RT1FieldsEnum.LEN),
  STD_ERR_IDC("The expected value is between 0 and 99", RT2FieldsEnum.IDC),
  STD_ERR_SRC("SRC is unlimited in size and is \"U\" character type", RT14FieldsEnum.SRC),
  STD_ERR_SRC_36("The field should contain maximum 36 AN-characters", RT14FieldsEnum.SRC),
  STD_ERR_DMM("DMM should be present in reference", RT14FieldsEnum.DMM),

  // Errors for Record Type 1
  STD_ERR_VER_RT1("Invalid field \"VER\"", RT1FieldsEnum.VER),
  STD_ERR_CNT_CONTENT_RT1("The content is not valid", RT1FieldsEnum.CNT),
  STD_ERR_CNT_FORMAT_RT1("The field is mandatory", RT1FieldsEnum.CNT),
  STD_ERR_TOT_RT1("Invalid field \"TOT\". Does not match regex", RT1FieldsEnum.TOT),
  STD_ERR_DAI_RT1(
      "Invalid format for field \"DAI\". Should match : At least one character, and only characters from type A, N and S",
      RT1FieldsEnum.DAI),
  STD_ERR_ORI_RT1(
      "Invalid format for field \"ORI\". Should match : At least one character, and only characters from type A, N and S",
      RT1FieldsEnum.ORI),
  STD_ERR_DAT_RT1("The expected format is : YYYYMMDD", RT1FieldsEnum.DAT),
  STD_ERR_DOM_RT1(
      "Invalid format for field \"DOM\". Should contain domain, with optionally a version",
      RT1FieldsEnum.DOM),
  STD_ERR_PRY_RT1("The expected value is : between 1 and 9", RT1FieldsEnum.PRY),
  STD_ERR_TCN_RT1(
      "Invalid format for field \"TCN\". Should match : At least one character, and only characters from type A, N and S",
      RT1FieldsEnum.TCN),
  STD_ERR_NSR_NO_RT4_RT1("The expected value is : 00.00", RT1FieldsEnum.NSR),
  STD_ERR_NSR_WITH_RT4_RT1("The expected value should match XX.XX", RT1FieldsEnum.NSR),
  STD_ERR_NTR_NO_RT4_RT1("The expected value is : 00.00", RT1FieldsEnum.NTR),
  STD_ERR_NTR_WITH_RT4_RT1("The expected value should match XX.XX", RT1FieldsEnum.NTR),
  STD_ERR_GMT_RT1("The expected format is : YYYYMMDDHHMMSSZ", RT1FieldsEnum.GMT),
  STD_ERR_DCS_RT1("The expected format is : CSI<US>CSN<US>CSV", RT1FieldsEnum.DCS),
  STD_ERR_ANM_DAN_RT1(
      "The expected format is : DAN<US>OAN, incorrect value for DAN", RT1FieldsEnum.ANM),
  STD_ERR_ANM_OAN_RT1(
      "The expected format is : DAN<US>OAN, incorrect value for OAN", RT1FieldsEnum.ANM),
  STD_ERR_GNS_RT1("Allowed values are ISO and GENC", RT1FieldsEnum.GNS),

  // Errors for Record Type 3
  STD_ERR_LEN_RT3("The expected format is : 1 to 10 digits", RT3FieldsEnum.LEN),
  STD_ERR_IDC_RT3("The expected value is between 0 and 99", RT3FieldsEnum.IDC),
  STD_ERR_IMP_RT3(
      "IMP value is mandatory and should be one of Impression Type allowed values",
      RT3FieldsEnum.IMP),
  STD_ERR_FGP_RT3(
      "FGP is mandatory and should be a list of subfields containing friction ridge positions",
      RT3FieldsEnum.FGP),
  STD_ERR_ISR_RT3(
      "Is mandatory and should be a numerical field between 0 and 1", RT3FieldsEnum.ISR),
  STD_ERR_HLL_RT3(
      "Is mandatory and should be a numerical field between 1 and 99999", RT3FieldsEnum.HLL),
  STD_ERR_VLL_RT3(
      "Is mandatory and should be a numerical field between 1 and 99999", RT3FieldsEnum.VLL),
  STD_ERR_GCA_RT3(
      "GCA/CGA Is mandatory, and should be one of Compression Algorithms", RT3FieldsEnum.GCA),
  STD_ERR_DATA_RT3(
      "DATA value is mandatory and should be one of Impression Type allowed values",
      RT3FieldsEnum.DATA),

  // Errors for Record Type 4
  STD_ERR_LEN_RT4("The expected format is : 1 to 10 digits", RT4FieldsEnum.LEN),
  STD_ERR_IDC_RT4("The expected value is between 0 and 99", RT4FieldsEnum.IDC),
  STD_ERR_IMP_RT4(
      "IMP value is mandatory and should be one of Impression Type allowed values",
      RT4FieldsEnum.IMP),
  STD_ERR_FGP_RT4(
      "FGP is mandatory and should be a list of subfields containing friction ridge positions",
      RT4FieldsEnum.FGP),
  STD_ERR_ISR_RT4(
      "Is mandatory and should be a numerical field between 0 and 1", RT4FieldsEnum.ISR),
  STD_ERR_HLL_RT4(
      "Is mandatory and should be a numerical field between 1 and 99999", RT4FieldsEnum.HLL),
  STD_ERR_VLL_RT4(
      "Is mandatory and should be a numerical field between 1 and 99999", RT4FieldsEnum.VLL),
  STD_ERR_GCA_RT4(
      "GCA/BCA Is mandatory, and should be one of Compression Algorithms", RT4FieldsEnum.GCA),
  STD_ERR_DATA_RT4(
      "DATA value is mandatory and should be one of Impression Type allowed values",
      RT4FieldsEnum.DATA),

  // Errors for Record Type 5
  STD_ERR_LEN_RT5("The expected format is : 1 to 10 digits", RT5FieldsEnum.LEN),
  STD_ERR_IDC_RT5("The expected value is between 0 and 99", RT5FieldsEnum.IDC),
  STD_ERR_IMP_RT5(
      "IMP value is mandatory and should be one of Impression Type allowed values",
      RT5FieldsEnum.IMP),
  STD_ERR_FGP_RT5(
      "FGP is mandatory and should be a list of subfields containing friction ridge positions",
      RT5FieldsEnum.FGP),
  STD_ERR_ISR_RT5(
      "Is mandatory and should be a numerical field between 0 and 1", RT5FieldsEnum.ISR),
  STD_ERR_HLL_RT5(
      "Is mandatory and should be a numerical field between 1 and 99999", RT5FieldsEnum.HLL),
  STD_ERR_VLL_RT5(
      "Is mandatory and should be a numerical field between 1 and 99999", RT5FieldsEnum.VLL),
  STD_ERR_GCA_RT5(
      "GCA/CGA Is mandatory, and should be one of Compression Algorithms", RT5FieldsEnum.GCA),
  STD_ERR_DATA_RT5(
      "DATA value is mandatory and should be one of Impression Type allowed values",
      RT5FieldsEnum.DATA),

  // Errors for Record Type 6
  STD_ERR_LEN_RT6("The expected format is : 1 to 10 digits", RT6FieldsEnum.LEN),
  STD_ERR_IDC_RT6("The expected value is between 0 and 99", RT6FieldsEnum.IDC),
  STD_ERR_IMP_RT6(
      "IMP value is mandatory and should be one of Impression Type allowed values",
      RT6FieldsEnum.IMP),
  STD_ERR_FGP_RT6(
      "FGP is mandatory and should be a list of subfields containing friction ridge positions",
      RT6FieldsEnum.FGP),
  STD_ERR_ISR_RT6(
      "Is mandatory and should be a numerical field between 0 and 1", RT6FieldsEnum.ISR),
  STD_ERR_HLL_RT6(
      "Is mandatory and should be a numerical field between 1 and 99999", RT6FieldsEnum.HLL),
  STD_ERR_VLL_RT6(
      "Is mandatory and should be a numerical field between 1 and 99999", RT6FieldsEnum.VLL),
  STD_ERR_GCA_RT6(
      "GCA/CGA Is mandatory, and should be one of Compression Algorithms", RT6FieldsEnum.GCA),
  STD_ERR_DATA_RT6(
      "DATA value is mandatory and should be one of Impression Type allowed values",
      RT6FieldsEnum.DATA),

  // Errors for Record Type 13
  STD_ERR_LEN_RT13("The expected format is : 1 to 10 digits", RT13FieldsEnum.LEN),
  STD_ERR_LCD_RT13("The expected format is : YYYYMMDD", RT13FieldsEnum.LCD),
  STD_ERR_IMP_MANDATORY_RT13(
      "IMP value is mandatory and should be one of Impression Type allowed values",
      RT13FieldsEnum.IMP),
  STD_ERR_FGP_RT13(
      "FGP is mandatory and should be a list of subfields containing friction ridge positions",
      RT13FieldsEnum.FGP),
  STD_ERR_HLL_MANDATORY_RT13(
      "Is mandatory and should be a numerical field between 1 and 99999", RT13FieldsEnum.HLL),
  STD_ERR_VLL_MANDATORY_RT13(
      "Is mandatory and should be a numerical field between 1 and 99999", RT13FieldsEnum.VLL),
  STD_ERR_SLC_MANDATORY_RT13("Is mandatory, and value should be 0, 1 or 2", RT13FieldsEnum.SLC),
  STD_ERR_THPS_MANDATORY_RT13(
      "Is mandatory, and value should be a positive integer", RT13FieldsEnum.THPS),
  STD_ERR_TVPS_MANDATORY_RT13(
      "Is mandatory, and value should be a positive integer", RT13FieldsEnum.TVPS),
  STD_ERR_CGA_MANDATORY_RT13(
      "Is mandatory, and should be one of Compression Algorithms", RT13FieldsEnum.CGA),
  STD_ERR_BPX_MANDATORY_RT13("Is mandatory numerical field between 8 and 99", RT13FieldsEnum.BPX),
  STD_ERR_SHPS_O_RT13(
      "SHPS value is optional and, if filled, it should be a positive integer with maximum 5 characters",
      RT13FieldsEnum.SHPS),
  STD_ERR_SVPS_O_RT13(
      "SVPS value is optional and, if filled, it should be a positive integer with maximum 5 characters",
      RT13FieldsEnum.SVPS),
  STD_ERR_COM_RT13("Should contains max 126 characters", RT13FieldsEnum.COM),
  STD_ERR_PPC_RT13("Should be present only if FGP = 19", RT13FieldsEnum.PPC),
  STD_ERR_LQM_RT13(
      "Should be a list with each with format : FRMP<US>QVU<US>QAV<US>QAP", RT13FieldsEnum.LQM),
  STD_ERR_DATA_RT13(
      "DATA value is mandatory and should be one of Impression Type allowed values",
      RT13FieldsEnum.DATA),

  // Errors for Record Type 14
  STD_ERR_IMP_MANDATORY_RT14(
      "IMP value is mandatory and should be one of Impression Type allowed values",
      RT14FieldsEnum.IMP),
  STD_ERR_IMP_NOT_ALLOWED_RT14(
      "IMP value is optional and, if filled, it should be one of Impression Type allowed values",
      RT14FieldsEnum.IMP),
  STD_ERR_FCD_RT14("The expected format is : YYYYMMDD", RT14FieldsEnum.FCD),
  STD_ERR_HLL_RT14(
      "Present only if there is an image, and value should be between 10 and 99999",
      RT14FieldsEnum.HLL),
  STD_ERR_HLL_MANDATORY_RT14(
      "Is mandatory and should be a numerical field between 1 and 99999", RT14FieldsEnum.HLL),
  STD_ERR_VLL_RT14(
      "Present only if there is an image, and value should be between 10 and 99999",
      RT14FieldsEnum.VLL),
  STD_ERR_VLL_MANDATORY_RT14(
      "Is mandatory and should be a numerical field between 1 and 99999", RT14FieldsEnum.VLL),
  STD_ERR_SLC_RT14(
      "Present only if there is an image, and value should be 0, 1 or 2", RT14FieldsEnum.SLC),
  STD_ERR_SLC_MANDATORY_RT14("Is mandatory, and value should be 0, 1 or 2", RT14FieldsEnum.SLC),
  STD_ERR_THPS_RT14(
      "Present only if there is an image, and value should be a positive integer",
      RT14FieldsEnum.THPS),
  STD_ERR_TVPS_RT14(
      "Present only if there is an image, and value should be a positive integer",
      RT14FieldsEnum.TVPS),
  STD_ERR_THPS_MANDATORY_RT14(
      "Is mandatory, and value should be a positive integer", RT14FieldsEnum.THPS),
  STD_ERR_TVPS_MANDATORY_RT14(
      "Is mandatory, and value should be a positive integer", RT14FieldsEnum.TVPS),
  STD_ERR_SLC_COHERENCE_RT14(
      "With value 1 or 2, THPS and TVPS should be equals", RT14FieldsEnum.SLC),
  STD_ERR_CGA_RT14(
      "Present only if there is an image, and should be one of Compression Algorithms",
      RT14FieldsEnum.CGA),
  STD_ERR_BPX_RT14(
      "Present only if there is an image, and should be between 8 and 99", RT14FieldsEnum.BPX),
  STD_ERR_CGA_MANDATORY_RT14(
      "Is mandatory, and should be one of Compression Algorithms", RT14FieldsEnum.CGA),
  STD_ERR_BPX_MANDATORY_RT14("Is mandatory numerical field", RT14FieldsEnum.BPX),
  STD_ERR_FGP_RT14(
      "FGP is mandatory and should be a list of subfields containing friction ridge positions",
      RT14FieldsEnum.FGP),
  STD_ERR_FGP_ONE_ALLOWED_RT14("Only one subfield is allowed", RT14FieldsEnum.FGP),
  STD_ERR_PPD_RT14("Should be present only if FGP = 19", RT14FieldsEnum.PPD),
  STD_ERR_PPC_RT14("Should be present only if FGP = 19", RT14FieldsEnum.PPC),
  STD_ERR_SHPS_O_RT14(
      "SHPS value is optional and, if filled, it should be a positive integer with maximum 5 characters",
      RT14FieldsEnum.SHPS),
  STD_ERR_SHPS_NOT_ALLOWED_RT14("Should only be present if there is an image", RT14FieldsEnum.SHPS),
  STD_ERR_SVPS_O_RT14(
      "SVPS value is optional and, if filled, it should be a positive integer with maximum 5 characters",
      RT14FieldsEnum.SVPS),
  STD_ERR_SVPS_NOT_ALLOWED_RT14("Should only be present if there is an image", RT14FieldsEnum.SVPS),
  STD_ERR_AMP_RT14(
      "Should be a list with unique element with format : FRAP<US>ABC", RT14FieldsEnum.AMP),
  STD_ERR_COM_RT14("Should contains max 126 characters", RT14FieldsEnum.COM),
  STD_ERR_NQM_RT14(
      "Should be a list with each element at format : FRNP<US>IQS with ISQ = [1,5] or 254 or 255",
      RT14FieldsEnum.NQM),
  STD_ERR_SEG_NOT_ALLOWED_RT14(
      "Should be not be present for this type of Friction Ridge Position", RT14FieldsEnum.SEG),
  STD_ERR_SEG_INVALID_RT14(
      "Should be multiple subfields with format : FRSP<US>LHC<US>RHC<US>TVC<US>BVC",
      RT14FieldsEnum.SEG),
  STD_ERR_SEQ_5_ITEMS_RT14("Should be a list with each containing 5 items", RT14FieldsEnum.SEG),
  STD_ERR_FQM_RT14(
      "Should be a list with each element at format : FRMP<US>QVU<US>QAV<US>QAP",
      RT14FieldsEnum.FQM),
  STD_ERR_SQM_RT14(
      "Should be a list with each with format : FRQP<US>QVU<US>QAV<US>QAP", RT14FieldsEnum.SQM),
  STD_ERR_SQM_UNALLOWED_FRQP_RT14(
      "FRQP Should be in the set of either the FRSP or FRAS values contained in this record",
      RT14FieldsEnum.SQM),
  STD_ERR_ASEG_RT14(
      "Should be a list with each with format : FRAS<US>NOP{<US>HPO<US>VPO}", RT14FieldsEnum.ASEG),
  STD_ERR_SCF_RT14("Should be an integer between 1 and 255", RT14FieldsEnum.SCF),
  STD_ERR_SIF_RT14("Should be 'Y' if present", RT14FieldsEnum.SIF),
  STD_ERR_FAP_RT14("Should be in referenced values", RT14FieldsEnum.FAP),
  STD_ERR_SUB_RT14("Should be with format : SSC<US>SBSC<US>SBCC", RT14FieldsEnum.SUB),
  STD_ERR_CON_RT14("Should contains maximum 1000 ANS characters", RT14FieldsEnum.CON),
  ;

  private final String message;
  private final IFieldTypeEnum fieldTypeEnum;
  private final String code;

  StdNistValidatorErrorEnum(String message, IFieldTypeEnum fieldTypeEnum) {
    this.code = this.name();
    this.message = message;
    this.fieldTypeEnum = fieldTypeEnum;
  }

  public String getFieldName() {
    return this.fieldTypeEnum.getCode();
  }
}
