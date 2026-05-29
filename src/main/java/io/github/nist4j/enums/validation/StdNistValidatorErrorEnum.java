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

import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StdNistValidatorErrorEnum implements INistValidationErrorEnum {
  STD_ERR_MISSING_STANDARD("NIST standard invalid"),
  STD_ERR_UNIMPLEMENTED_STANDARD("NIST standard not implemented"),
  STD_ERR_FORBIDDEN_RT("Record type not allowed for this NIST standard"),
  STD_ERR_LEN(
      "{recordType}.{fieldId} {fieldName} is mandatory and should respect the format : 1 to 10 digits"),
  STD_ERR_FCT(
      "{recordType}.{fieldId} {fieldName} is mandatory depending to '{param0}' should be in collection"),

  // Errors for Record Type 1
  STD_ERR_VER_RT1("{recordType}.{fieldId} {fieldName} invalid field \"VER\""),
  STD_ERR_CNT_CONTENT_RT1("{recordType}.{fieldId} {fieldName} the content is not valid"),
  STD_ERR_DOM_RT1(
      "{recordType}.{fieldId} {fieldName} invalid format for field \"DOM\". Should contain domain, with optionally a version"),
  STD_ERR_NSR_NO_RT1("{recordType}.{fieldId} {fieldName} the expected value is : 00.00"),
  STD_ERR_NSR_WITH_RT1("{recordType}.{fieldId} {fieldName} the expected value should match XX.XX"),
  STD_ERR_NTR_NO_RT1("{recordType}.{fieldId} {fieldName} the expected value is : 00.00"),
  STD_ERR_NTR_WITH_RT1("{recordType}.{fieldId} {fieldName} the expected value should match XX.XX"),
  STD_ERR_DCS_RT1("{recordType}.{fieldId} {fieldName} the expected format is : CSI<US>CSN<US>CSV"),
  STD_ERR_ANM_DAN_RT1(
      "{recordType}.{fieldId} {fieldName} the expected format is : DAN<US>OAN, incorrect value for DAN"),
  STD_ERR_ANM_OAN_RT1(
      "{recordType}.{fieldId} {fieldName} the expected format is : DAN<US>OAN, incorrect value for OAN"),

  STD_ERR_FGP(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a list of subfields containing friction ridge positions"),
  STD_ERR_CSP(
      "{recordType}.{fieldId} {fieldName} is mandatory when BPX > 8 and must be in collection '{param0}'"),
  STD_ERR_EFR("{recordType}.{fieldId} {fieldName} must but must contains subfields EFL<US>EFF"),
  STD_ERR_EFR_WITH_DATA("{recordType}.{fieldId} {fieldName} must be empty id DATA is present"),

  // Errors for Record Type 10
  STD_ERR_FIP("{recordType}.{fieldId} {fieldName} is optional, but must be a list of values"),
  STD_ERR_FIP_1(
      "{recordType}.{fieldId} {fieldName} is optional, but 1 < LHC < HLL and LHC < RHC < HLL "),
  STD_ERR_FIP_2(
      "{recordType}.{fieldId} {fieldName} is optional, but 1 < TVC < VLL and TVC < BVC < VLL"),
  STD_ERR_FPFI("{recordType}.{fieldId} {fieldName} is optional, but must contains subfields"),
  STD_ERR_SAP(
      "{recordType}.{fieldId} {fieldName} is mandatory, and should be one of allowed values"),
  STD_ERR_DIST("{recordType}.{fieldId} {fieldName} is optional but must contains subfields"),
  STD_ERR_DIST_IMT_MUST_BE_FACE(
      "{recordType}.{fieldId} {fieldName} is optional but can only be used if IMT is FACE"),
  STD_ERR_LAF("{recordType}.{fieldId} {fieldName} is optional but must contains subfields"),
  STD_ERR_POA(
      "{recordType}.{fieldId} {fieldName} is optional but must be a numerical field between -180 and 180"),
  STD_ERR_PXS_LEGACY(
      "{recordType}.{fieldId} {fieldName} is optional but must be in collection (GLASSES, HAT, SCARF, PHYSICAL, OTHER)"),
  STD_ERR_PAS(
      "{recordType}.{fieldId} {fieldName} is optional but must be in collection of acquisition source types codes"),
  STD_ERR_SQS("{recordType}.{fieldId} {fieldName} is optional but must be a list quality score"),
  STD_ERR_SPA(
      "{recordType}.{fieldId} {fieldName} is optional but must be a list separated with US"),
  STD_ERR_SXS(
      "{recordType}.{fieldId} {fieldName} is mandatory if SAP>=40 and must be a list in collection"),
  STD_ERR_SEC(
      "{recordType}.{fieldId} {fieldName} is mandatory if SAP>=40 and must be a value of collection"),
  STD_ERR_SHC(
      "{recordType}.{fieldId} {fieldName} is mandatory if SAP>=40 and must be a value of collection"),
  STD_ERR_FFP("{recordType}.{fieldId} {fieldName} is optional but must be a list of points"),
  STD_ERR_3DF("{recordType}.{fieldId} {fieldName} is optional but must contains subfields"),
  STD_ERR_FEC("{recordType}.{fieldId} {fieldName} is optional but must contains subfields"),
  STD_ERR_SMS(
      "{recordType}.{fieldId} {fieldName} is optional but must be a pair of numbers between 1 to 999"),
  STD_ERR_TCL("{recordType}.{fieldId} {fieldName} is optional but must be a list of colors"),
  STD_ERR_ITX("{recordType}.{fieldId} {fieldName} is optional but must be a list of transform"),
  STD_ERR_OCC("{recordType}.{fieldId} {fieldName} is optional but must be a list of items"),
  STD_ERR_SUB_1(
      "{recordType}.{fieldId} {fieldName} {subfieldName} when SSC eq to 'D', other subfield can be used : SSC<US>SBSC<US>SBCC"),
  STD_ERR_SUB_2(
      "{recordType}.{fieldId} {fieldName} {subfieldName} when SSC not eq to 'D', other subfield should be empty : SSC<US>SBSC<US>SBCC"),
  STD_ERR_PID(
      "{recordType}.{fieldId} {fieldName} is optional but must be a repeated list of items"),
  STD_ERR_CID("{recordType}.{fieldId} {fieldName} is optional but must be a unique list of items"),
  STD_ERR_VID("{recordType}.{fieldId} {fieldName} is optional but must be a unique list of items"),
  STD_ERR_RSP("{recordType}.{fieldId} {fieldName} is optional but must be a unique list of items"),
  STD_ERR_ANN("{recordType}.{fieldId} {fieldName} is optional but must be a list of items"),
  STD_ERR_DUI(
      "{recordType}.{fieldId} {fieldName} is optional but must ANS field starting with M or P"),
  STD_ERR_MMS("{recordType}.{fieldId} {fieldName} is optional but must be a list of items"),
  STD_ERR_ASC("{recordType}.{fieldId} {fieldName} is optional but must be a list of items"),
  STD_ERR_HAS(
      "{recordType}.{fieldId} {fieldName} is optional but must be a hexa string with length 64"),
  STD_ERR_SOR("{recordType}.{fieldId} {fieldName} is optional but must be a list of items"),
  STD_ERR_GEO("{recordType}.{fieldId} {fieldName} is optional but must be a unique list of items"),
  STD_ERR_PPC_1(
      "{recordType}.{fieldId} {fieldName} should a list with each with format : FVC<US>LOS<US>LHC<US>RHC<US>TVC<US>BVC"),
  STD_ERR_PPC_2("{recordType}.{fieldId} {fieldName} should be present only if FGP = 19"),
  STD_ERR_PPD("{recordType}.{fieldId} {fieldName} should be present only if FGP = 19"),
  STD_ERR_FSB(
      "{recordType}.{fieldId} {fieldName} is optional but should contains subfields : QNQ<US>QAV<US>QAP<US>QPV<US>QCM<US>QCK"),
  STD_ERR_TIF(
      "{recordType}.{fieldId} {fieldName} is mandatory when CGA=MEDIA, optional otherwise and should contains subfields : FTY<US>DEI"),
  STD_ERR_SMT_1("{recordType}.{fieldId} {fieldName} should be used only when IMT in '({param0})'"),
  STD_ERR_SMT_2(
      "{recordType}.{fieldId} {fieldName} should be in collection respecting IMT is '{param0}'"),
  STD_ERR_SMD_1(
      "{recordType}.{fieldId} {fieldName} should be in collection respecting SMT is '{param0}'"),
  STD_ERR_SMD_2(
      "{recordType}.{fieldId} {fieldName} should respect pattern and references SMI<US>TAC<US>TSC<US>TSD is '{param0}'"),
  STD_ERR_FQC(
      "{recordType}.{fieldId} {fieldName} should be a list with each with format : FRP<US>QNQ<US>QAV<US>QAP<US>QPV<US>QCM<US>QCK"),

  // Errors for Record Type 13,
  STD_ERR_LQM_RT13(
      "{recordType}.{fieldId} {fieldName} should be a list with each with format : FRMP<US>QVU<US>QAV<US>QAP"),
  STD_ERR_SPD_1(
      "{recordType}.{fieldId} {fieldName} should a list with each with format : PDF<US>FIC (PDF is a finger pos and FIC a valid table value)"),
  STD_ERR_SPD_2("{recordType}.{fieldId} {fieldName} should be empty if FGP is not 19"),
  STD_ERR_RSP_1(
      "{recordType}.{fieldId} {fieldName} with RSF value must have RSU, RSM and RSO empty"),
  STD_ERR_RSP_2(
      "{recordType}.{fieldId} {fieldName} without RSF value must have RSU, RSM and RSO set"),
  STD_ERR_REM_1(
      "{recordType}.{fieldId} {fieldName} when MDR equals to 'RULER' subfields are mandatory (MDR<US>KSL<US>KSU<US>SXA<US>SYA<US>SXB<US>SYB<US>COM)"),
  STD_ERR_REM_2(
      "{recordType}.{fieldId} {fieldName} when MDR equals to 'FORM' subfields are optional (MDR<US>KSL<US>KSU<US>SXA<US>SYA<US>SXB<US>SYB<US>COM)"),
  STD_ERR_REM_3(
      "{recordType}.{fieldId} {fieldName} when MDR not equals to 'RULER' or 'FORM' subfields should be empty (MDR<US>KSL<US>KSU<US>SXA<US>SYA<US>SXB<US>SYB<US>COM)"),

  // Errors for Record Type 14
  STD_ERR_SLC_COHERENCE_RT14(
      "{recordType}.{fieldId} {fieldName} With value 1 or 2, THPS and TVPS should be equals"),
  STD_ERR_FGP_ONE_ALLOWED_RT14("{recordType}.{fieldId} {fieldName} only one subfield is allowed"),
  STD_ERR_AMP_RT14(
      "{recordType}.{fieldId} {fieldName} should be a list with unique element with format : FRAP<US>ABC"),
  STD_ERR_NQM_RT14(
      "{recordType}.{fieldId} {fieldName} should be a list with each element at format : FRNP<US>IQS with ISQ = [1,5] or 254 or 255"),
  STD_ERR_SEG_NOT_ALLOWED_RT14(
      "{recordType}.{fieldId} {fieldName} should be not be present for this type of Friction Ridge Position"),
  STD_ERR_SEG_INVALID_RT14(
      "{recordType}.{fieldId} {fieldName} should be multiple subfields with format : FRSP<US>LHC<US>RHC<US>TVC<US>BVC"),
  STD_ERR_SEQ_5_ITEMS_RT14(
      "{recordType}.{fieldId} {fieldName} should be a list with each containing 5 items"),
  STD_ERR_FQM_RT14(
      "{recordType}.{fieldId} {fieldName} should be a list with each element at format : FRMP<US>QVU<US>QAV<US>QAP"),
  STD_ERR_SQM_RT14(
      "{recordType}.{fieldId} {fieldName} should be a list with each with format : FRQP<US>QVU<US>QAV<US>QAP"),
  STD_ERR_SQM_UNALLOWED_FRQP_RT14(
      "{recordType}.{fieldId} {fieldName} should be in the set of either the SEG.FRSP or ASEG.FRAS values contained in this record"),
  STD_ERR_ASEG_RT14(
      "{recordType}.{fieldId} {fieldName} should be a list with each with format : FRAS<US>NOP{<US>HPO<US>VPO}"),

  // Generic message
  STD_ERR_MANDATORY_FIELD(
      "{recordType}.{fieldId} {fieldName} is mandatory and should not be absent or null"),
  STD_ERR_EMPTY_FIELD("{recordType}.{fieldId} {fieldName} should be empty or absent"),
  STD_ERR_MANDATORY_NUMERIC_BETWEEN_VALUES(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a numerical field between {param0} and {param1}"),
  STD_ERR_OPTIONAL_NUMERIC_BETWEEN(
      "{recordType}.{fieldId} {fieldName} is optional but should be a numerical field between {param0} and {param1}"),
  STD_ERR_MANDATORY_NUMERIC_BETWEEN(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a numerical field between {param0} and {param1}"),
  STD_ERR_OPTIONAL_REAL_NUMBER_BETWEEN(
      "{recordType}.{fieldId} {fieldName} is optional but should be a real number field between {param0} and {param1}"),
  STD_ERR_OPTIONAL_DATETIME_FORMAT_YYYYMMDDHHMMSS(
      "{recordType}.{fieldId} {fieldName} is optional but should be a date-time with YYYYMMDDHHMMSS format"),
  STD_ERR_MANDATORY_DATETIME_FORMAT_YYYYMMDDHHMMSS(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a date-time with YYYYMMDDHHMMSS format"),
  STD_ERR_MANDATORY_DATE_FORMAT_YYYYMMDD(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a date-time with YYYYMMDDHHMMSS format"),
  STD_ERR_OPTIONAL_CHAR_FORMAT_WITH_MIN_MAX_LENGTH(
      "{recordType}.{fieldId} {fieldName} is optional but should be a character type '{param0}' format with size between {param1} {param2}"),
  STD_ERR_MANDATORY_CHAR_FORMAT_WITH_MIN_MAX_LENGTH(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a character type '{param0}' format with size between {param1} {param2}"),
  STD_ERR_OPTIONAL_CHAR_FORMAT_WITH_MIN_LENGTH(
      "{recordType}.{fieldId} {fieldName} is optional but should be a character type '{param0}' format with min length {param1}"),
  STD_ERR_MANDATORY_CHAR_FORMAT_WITH_MIN_LENGTH(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be a character type '{param0}' format with min length {param1}"),
  STD_ERR_OPTIONAL_AND_MATCHS_REGEX_FORMAT_PATTERN(
      "{recordType}.{fieldId} {fieldName} is optional but should be match regex pattern '{param0}'"),
  STD_ERR_MANDATORY_AND_MATCHS_REGEX_FORMAT_PATTERN(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be match regex pattern '{param0}'"),
  STD_ERR_OPTIONAL_MATCHS_COLLECTION(
      "{recordType}.{fieldId} {fieldName} is optional but should match a collection [{param0}]"),
  STD_ERR_MANDATORY_MATCHS_COLLECTION(
      "{recordType}.{fieldId} {fieldName} is mandatory and should match a collection [{param0}]"),
  STD_ERR_MANDATORY_AND_EXACT_MATCH(
      "{recordType}.{fieldId} {fieldName} is mandatory and should be exactly matchs '{param0}'"),
  STD_ERR_MANDATORY_DATA_FIELD(
      "{recordType}.{fieldId} {fieldName} is mandatory and should contains data field"),
  STD_ERR_OPTIONAL_BUT_DATA_FIELD(
      "{recordType}.{fieldId} {fieldName} is optional but should contains data field"),
  STD_ERR_TOO_MANY_SUBFIELDS_FOUNDED(
      "{recordType}.{fieldId} {fieldName} contains too many subfields");

  private final String message;
  private final String code;

  StdNistValidatorErrorEnum(String message) {
    this.code = this.name();
    this.message = message;
  }
}
