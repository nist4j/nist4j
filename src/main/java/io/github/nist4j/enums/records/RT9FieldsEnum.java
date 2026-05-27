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
package io.github.nist4j.enums.records;

import static io.github.nist4j.enums.CharacterTypeEnum.*;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("unused")
@AllArgsConstructor
@Getter
public enum RT9FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMP(3, "IMP", "Impression Type", DataText.class, N),
  FMT(4, "FMT", "Minutiae format", DataText.class, A),
  /*5 to 12 Legacy Fields see ANSI/NIST-ITL 1-2007 or ANSI/NIST-ITL 2-2008*/
  OFR_LEGACY(5, "OFR", "Originating fingerprint reading system", DataText.class, A),
  FGP_LEGACY(6, "FGP", "Finger position", DataText.class, N),
  FPC_LEGACY(7, "FPC", "Finger pattern classification", DataText.class, A),
  CRP_LEGACY(8, "CRP", "Core Position", DataText.class, N),
  DLT_LEGACY(9, "DLT", "Delta position", DataText.class, N),
  MIN_LEGACY(10, "MIN", "Number of Minutiae", DataText.class, N),
  RDG_LEGACY(11, "RDG", "Minutiae ridge count indicator", DataText.class, N),
  MRC_LEGACY(12, "MRC", "Minutiae and ridge count data", DataText.class, N),
  /*13 to 30 FBI IAFIS FEATURE SET*/
  /*31 to 55 COGENT FEATURE SET*/
  /*56 to 70 MOTOROLA FEATURE SET*/
  /*71 to 99 MORPHOTRAK FEATURE SET*/
  /*100 to 125 NEC FEATURE SET*/
  /*126 150 INCITS 378 FIELDS*/
  CBI(126, "CBI", "M1 CBEFF INFORMATION", DataText.class, AN),
  CEI(127, "CEI", "M1 CAPTURE EQUIPMENT ID", DataText.class, U),
  HLL(128, "HLL", "M1 HORIZONTAL LINE LENGTH", DataText.class, N),
  VLL(129, "VLL", "M1 VERTICAL LINE LENGTH", DataText.class, N),
  SLC(130, "SLC", "M1 SCALE UNITS", DataText.class, N),
  THPS(131, "THPS", "M1 TRANSMITTED HORIZONTAL PIXEL SCALE", DataText.class, N),
  TVPS(132, "TVPS", "M1 TRANSMITTED VERTICAL PIXEL SCALE", DataText.class, N),
  FVW(133, "FVW", "M1 FINGER VIEW", DataText.class, N),
  FGP(134, "FGP", "M1 FRICTION RIDGE GENERALIZED POSITION", DataText.class, N),
  FQD(135, "FQD", "M1 FRICTION RIDGE QUALITY DATA", DataText.class, AN),
  NOM(136, "NOM", "M1 NUMBER OF MINUTIAE", DataText.class, N),
  FMD(137, "FMD", "M1 FINGER MINUTIAE DATA", DataText.class, N),
  RCI(138, "RCI", "M1 RIDGE COUNT INFORMATION", DataText.class, N),
  CIN(139, "CIN", "M1 CORE INFORMATION", DataText.class, N),
  DIN(140, "DIN", "M1 DELTA INFORMATION", DataText.class, N),
  ADA(141, "ADA", "M1 ADDITIONAL DELTA ANGLES", DataText.class, N),
  /*151 175 L1 / IDENTIX FEATURE SET*/
  OOD(176, "OOD", "OTHER FEATURE SETS - OWNER OR DEVELOPER", DataText.class, U),
  PAG(177, "PAG", "OTHER FEATURE SETS – PROCESSING ALGORITHM", DataText.class, U),
  SOD(178, "SOD", "OTHER FEATURE SETS - SYSTEM OR DEVICE", DataText.class, U),
  DTX(179, "DTX", "OTHER FEATURE SETS – CONTACT INFORMATION", DataText.class, U),
  /*176 to 225 OTHER FEATURE SETS */
  /*226 to 299 RESERVED FOR FUTURE USE*/
  ROI(300, "ROI", "EFS REGION OF INTEREST", DataText.class, NS),
  ORT(301, "ORT", "EFS ORIENTATION", DataText.class, NS),
  FPP(302, "FPP", "EFS FINGER, PALM, PLANTAR POSITION", DataText.class, ANS),
  FSP(303, "FSP", "EFS FEATURE SET PROFILE", DataText.class, N),
  PAT(307, "PAT", "EFS PATTERN CLASSIFICATION", DataText.class, A),
  RQM(308, "RQM", "EFS RIDGE QUALITY MAP", DataText.class, H),
  RQF(309, "RQF", "EFS RIDGE QUALITY FORMAT", DataText.class, AN),
  RFM(310, "RFM", "EFS RIDGE FLOW MAP", DataText.class, BASE64),
  RFF(311, "RFF", "EFS RIDGE FLOW MAP FORMAT", DataText.class, AN),
  RWM(312, "RWM", "EFS RIDGE WAVELENGTH MAP", DataText.class, AN),
  RWF(313, "RWF", "EFS RIDGE WAVELENGTH MAP FORMAT", DataText.class, AN),
  TRV(314, "TRV", "EFS TONAL REVERSAL", DataText.class, A),
  PLR(315, "PLR", "EFS POSSIBLE LATERAL REVERSAL", DataText.class, A),
  FQM(316, "FQM", "EFS FRICTION RIDGE QUALITY METRIC", DataText.class, AN),
  PGS(317, "PGS", "EFS POSSIBLE GROWTH OR SHRINKAGE", DataText.class, U),
  COR(320, "COR", "EFS CORES", DataText.class, N),
  DEL(321, "DEL", "EFS DELTAS", DataText.class, AN),
  CDR(322, "CDR", "EFS CORE-DELTA RIDGE COUNTS", DataText.class, AN),
  CPR(323, "CPR", "EFS CENTER POINT OF REFERENCE", DataText.class, ANS),
  DIS(324, "DIS", "EFS DISTINCTIVE FEATURES", DataText.class, U),
  NCOR(325, "NCOR", "EFS NO CORES PRESENT", DataText.class, A),
  NDEL(326, "NDEL", "EFS NO DELTAS PRESENT", DataText.class, A),
  NDIS(327, "NDIS", "EFS NO DISTINCTIVE FEATURES PRESENT", DataText.class, A),
  /*9.328-9.330 RESERVED FOR FUTURE USE*/
  MIN(331, "MIN", "EFS MINUTIAE", DataText.class, AN),
  MRA(332, "MRA", "EFS MINUTIAE RIDGE COUNT ALGORITHM", DataText.class, AN),
  MRC(333, "MRC", "EFS MINUTIAE RIDGE COUNTS", DataText.class, N),
  NMIN(334, "NMIN", "EFS NO MINUTIA PRESENT", DataText.class, A),
  RCC(335, "RCC", "EFS RIDGE COUNT CONFIDENCE", DataText.class, AN),
  /*9.336-9.339 RESERVED FOR FUTURE USE*/
  DOT(340, "DOT", "EFS RIDGE COUNT CONFIDENCE", DataText.class, N),
  INR(341, "INR", "EFS INCIPIENT RIDGES", DataText.class, N),
  CLD(342, "CLD", "EFS CREASES AND LINEAR DISCONTINUITIES", DataText.class, AN),
  REF(343, "REF", "EFS RIDGE EDGE FEATURES", DataText.class, AN),
  NPOR(344, "NPOR", "EFS NO PORES PRESENT", DataText.class, A),
  POR(345, "POR", "EFS PORES", DataText.class, N),
  NDOT(346, "NDOT", "EFS NO DOTS PRESENT", DataText.class, A),
  NINR(347, "NINR", "EFS NO INCIPIENT RIDGES PRESENT", DataText.class, A),
  NCLD(348, "NCLD", "EFS NO CREASES PRESENT", DataText.class, A),
  NREF(349, "NREF", "EFS NO RIDGE EDG FEATURES PRESENT", DataText.class, A),
  MFD(350, "MFD", "EFS METHOD OF FEATURE DETECTION", DataText.class, U),
  COM(351, "COM", "EFS COMMENT", DataText.class, U),
  LPM(352, "LPM", "EFS LATENT PROCESSING METHOD", DataText.class, AN),
  EAA(353, "EAA", "EFS EXAMINER ANALYSIS ASSESSMENT", DataText.class, U),
  EOF(354, "EOF", "EFS EVIDENCE OF FRAUD", DataText.class, U),
  LSB(355, "LSB", "EFS LATENT SUBSTRATE", DataText.class, U),
  LMT(356, "LMT", "EFS LATENT MATRIX", DataText.class, U),
  LQI(357, "LQI", "EFS LOCAL QUALITY ISSUES", DataText.class, U),
  AOC(360, "AOC", "EFS AREA OF CORRESPONDENCE", DataText.class, U),
  CPF(361, "CPF", "EFS CORRESPONDING POINTS OR FEATURES", DataText.class, U),
  ECD(362, "ECD", "EFS EXAMINER COMPARISON DETERMINATION", DataText.class, U),
  RRC(363, "RRC", "EFS RELATIVE ROTATION OF CORRESPONDING PRINT", DataText.class, NS),
  /*RESERVED FOR FUTURE USE*/
  SIM(372, "SIM", "EFS SKELETONIZED IMAGE", DataText.class, BASE64),
  RPS(373, "RPS", "EFS RIDGE PATH SEGMENTS", DataText.class, NS),
  /*RESERVED FOR FUTURE USE*/
  TPL(380, "TPL", "EFS TEMPORARY LINES", DataText.class, AN),
  FCC(381, "FCC", "EFS FEATURE COLOR [2015a>] AND COMMENT", DataText.class, U),
  /*RESERVED FOR FUTURE USE*/
  ULA(901, "ULA", "UNIVERSAL LATENT ANNOTATION", DataText.class, ANS),
  ANN(902, "ANN", "ANNOTATION INFORMATION", DataText.class, U),
  DUI(903, "DUI", "DEVICE UNIQUE IDENTIFIER", DataText.class, ANS),
  MMS(904, "MMS", "MAKE/MODEL/SERIAL NUMBER", DataText.class, U),
/*905 to 999 RESERVED FOR FUTURE USE*/ ;

  private final String recordType = "RT9";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT9FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
