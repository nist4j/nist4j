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
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("unused")
@AllArgsConstructor
@Getter
public enum RT9FieldsEnum implements IFieldTypeEnum {
  LEN(GenericFieldsEnum.LEN),
  IDC(GenericFieldsEnum.IDC),
  IMP(GenericBinaryFieldsEnum.IMP),
  FMT(4, "FMT", "Minutiae Format", DataText.class, A),
  /*5 to 12 Legacy Fields see ANSI/NIST-ITL 1-2007 or ANSI/NIST-ITL 2-2008*/
  OFR_LEGACY(5, "OFR", "Originating Fingerprint Reading System (legacy)", DataText.class, A),
  FGP_LEGACY(6, "FGP", "Finger Position (legacy)", DataText.class, N),
  FPC_LEGACY(7, "FPC", "Finger Pattern Classifcation (legacy)", DataText.class, A),
  CRP_LEGACY(8, "CRP", "Core Position (legacy)", DataText.class, N),
  DLT_LEGACY(9, "DLT", "Delta position (legacy)", DataText.class, N),
  MIN_LEGACY(10, "MIN", "Number of Minutiae (legacy)", DataText.class, N),
  RDG_LEGACY(11, "RDG", "Minutiae Ridge Count Indicator (legacy)", DataText.class, N),
  MRC_LEGACY(12, "MRC", "Minutiae and Ridge Count Data (legacy)", DataText.class, N),
  /*13 to 30 FBI IAFIS Feature SET*/
  /*31 to 55 COGENT Feature SET*/
  /*56 to 70 MOTOROLA Feature SET*/
  /*71 to 99 MORPHOTRAK Feature SET*/
  /*100 to 125 NEC Feature SET*/
  /*126 150 INCITS 378 FIELDS*/
  CBI(126, "CBI", "M1 CBEFF Information", DataText.class, AN),
  CEI(127, "CEI", "M1 Capture Equipment Identification", DataText.class, U),
  HLL(128, "HLL", "M1 Horizontal Line Length", DataText.class, N),
  VLL(129, "VLL", "M1 Vertical Line Length", DataText.class, N),
  SLC(130, "SLC", "M1 Scale Units", DataText.class, N),
  THPS(131, "THPS", "M1 Transmitted Horizontal Pixel Scale", DataText.class, N),
  TVPS(132, "TVPS", "M1 Transmitted Vertical Pixel Scale", DataText.class, N),
  FVW(133, "FVW", "M1 Finger View", DataText.class, N),
  FGP(134, "FGP", "M1 Friction Ridge Generalized Position", DataText.class, N),
  FQD(135, "FQD", "M1 Friction Ridge Quality Data", DataText.class, AN),
  NoM(136, "NoM", "M1 Number of Minutiae", DataText.class, N),
  FMD(137, "FMD", "M1 Finger Minutiae Data", DataText.class, N),
  RCI(138, "RCI", "M1 Ridge Count Information", DataText.class, N),
  CIN(139, "CIN", "M1 Core Information", DataText.class, N),
  DIN(140, "DIN", "M1 Delta Information", DataText.class, N),
  ADA(141, "ADA", "M1 Additional Delta Angles", DataText.class, N),
  /* 151 175 L1 / IDENTIX Feature SET - Deprecated */
  OOD(176, "OOD", "Other Feature Sets - Owner or Developer", DataText.class, U),
  PAG(177, "PAG", "Other Feature Sets - Processing Algorithm", DataText.class, U),
  SOD(178, "SOD", "Other Feature Sets - System or Device", DataText.class, U),
  DTX(179, "DTX", "Other Feature Sets – Contact Information", DataText.class, U),
  /* 180 to 225 Other Feature Sets - User Defined Fields */
  /* 226 to 299 RESERVED FOR FUTURE USE */
  ROI(300, "ROI", "EFS Region of Interest", DataText.class, NS),
  ORT(301, "ORT", "EFS Orientation", DataText.class, NS),
  FPP(302, "FPP", "EFS Finger, Palm, Plantar Position", DataText.class, ANS),
  FSP(303, "FSP", "EFS Feature Set Profile", DataText.class, N),
  /* 9.305 – 9.306 Reserved for Future Use Only by ANSI/NIST-ITL */
  PAT(307, "PAT", "EFS Pattern Classifcation", DataText.class, A),
  RQM(308, "RQM", "EFS Ridge Quality/Confidence Map", DataText.class, H),
  RQF(309, "RQF", "EFS Ridge Quality Map Format", DataText.class, AN),
  RFM(310, "RFM", "EFS Ridge Flow Map", DataText.class, BASE64),
  RFF(311, "RFF", "EFS Ridge Flow Map Format", DataText.class, AN),
  RWM(312, "RWM", "EFS Ridge Wavelength Map", DataText.class, AN),
  RWF(313, "RWF", "EFS Ridge Wavelength Map Format", DataText.class, AN),
  TRV(314, "TRV", "EFS Tonal Reversal", DataText.class, A),
  PLR(315, "PLR", "EFS Possible Lateral Reversal", DataText.class, A),
  FQM(316, "FQM", "EFS Friction Ridge Quality Metric", DataText.class, AN),
  PGS(317, "PGS", "EFS Possible Growth or Shrinkage", DataText.class, U),
  COR(320, "COR", "EFS Cores", DataText.class, N),
  DEL(321, "DEL", "EFS Deltas", DataText.class, AN),
  CDR(322, "CDR", "EFS Core-Delta Ridge Counts", DataText.class, AN),
  CPR(323, "CPR", "EFS Center Point of Reference", DataText.class, ANS),
  DIS(324, "DIS", "EFS Distinctive Features", DataText.class, U),
  NCOR(325, "NCOR", "EFS No Cores Present", DataText.class, A),
  NDEL(326, "NDEL", "EFS No Deltas Present", DataText.class, A),
  NDIS(327, "NDIS", "EFS No Distinctive Features Present", DataText.class, A),
  /* 9.328-9.330 RESERVED FOR FUTURE USE */
  MIN(331, "MIN", "EFS Minutiae", DataText.class, AN),
  MRA(332, "MRA", "EFS Minutiae Ridge Count Algorithm", DataText.class, AN),
  MRC(333, "MRC", "EFS Minutiae Ridge Counts", DataText.class, N),
  NMIN(334, "NMIN", "EFS No Minutia Present", DataText.class, A),
  RCC(335, "RCC", "EFS Ridge Count Confidence", DataText.class, AN),
  /* 9.336-9.339 RESERVED FOR FUTURE USE */
  DOT(340, "DOT", "EFS Ridge Count Confidence", DataText.class, N),
  INR(341, "INR", "EFS Incipient Ridges", DataText.class, N),
  CLD(342, "CLD", "EFS Creases and Linear Discontinuities", DataText.class, AN),
  REF(343, "REF", "EFS Ridge Edge Features", DataText.class, AN),
  NPOR(344, "NPOR", "EFS No Pores Present", DataText.class, A),
  POR(345, "POR", "EFS Pores", DataText.class, N),
  NDOT(346, "NDOT", "EFS No Dots Present", DataText.class, A),
  NINR(347, "NINR", "EFS No Incipient Ridges Present", DataText.class, A),
  NCLD(348, "NCLD", "EFS No Creases or Linear Discontinuities Present", DataText.class, A),
  NREF(349, "NREF", "EFS No Ridge Edge Features Present", DataText.class, A),
  MFD(350, "MFD", "EFS Method of Feature Detection", DataText.class, U),
  COM(351, "COM", "EFS Comments", DataText.class, U),
  LPM(352, "LPM", "EFS Latent Processing Method", DataText.class, AN),
  EAA(353, "EAA", "EFS Examiner Analysis Assessment", DataText.class, U),
  EOF(354, "EOF", "EFS Evidence of Fraud", DataText.class, U),
  LSB(355, "LSB", "EFS Latent Substrate", DataText.class, U),
  LMT(356, "LMT", "EFS Latent Matrix", DataText.class, U),
  LQI(357, "LQI", "EFS Local Quality Issues", DataText.class, U),
  AOC(360, "AOC", "EFS Area of Correspondence", DataText.class, U),
  CPF(361, "CPF", "EFS Corresponding Points or Features", DataText.class, U),
  ECD(362, "ECD", "EFS Examiner Comparaison Determination", DataText.class, U),
  RRC(363, "RRC", "EFS Relative Rotation of Corresponding Print", DataText.class, NS),
  /* 9.364 – 9.371 Reserved for Future Use Only by ANSI/NIST-ITL */
  SIM(372, "SIM", "EFS Skeletonized Image", DataText.class, BASE64),
  RPS(373, "RPS", "EFS Ridge Path Segments", DataText.class, NS),
  /* 9.374 – 9.379 Reserved for Future Use Only by ANSI/NIST-ITL */
  TPL(380, "TPL", "EFS Temporary Lines", DataText.class, AN),
  FCC(381, "FCC", "EFS Feature Color and Comments", DataText.class, U),
  /* 9.382 – 9.399 Reserved for Future Use Only by ANSI/NIST-ITL */
  /* 9.400 – 9.900 Reserved for Future Use Only by ANSI/NIST-ITL */
  ULA(901, "ULA", "Universal Latent Workstation Annotation Information", DataText.class, ANS),
  ANN(GenericFieldsEnum.ANN),
  DUI(GenericFieldsEnum.DUI),
  MMS(GenericFieldsEnum.MMS),
  FCT(905, "FCT", "Friction Ridge Capture Technology", DataText.class, N), // since 2025
/* 9.906 – 9.999 Reserved for Future Use Only by ANSI/NIST-ITL */ ;

  private final RecordTypeEnum recordType = RecordTypeEnum.RT9;
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
