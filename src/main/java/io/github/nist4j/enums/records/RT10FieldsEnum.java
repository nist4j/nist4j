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
public enum RT10FieldsEnum implements IFieldTypeEnum {
  LEN(GenericBinaryFieldsEnum.LEN),
  IDC(GenericBinaryFieldsEnum.IDC),
  IMT(3, "IMT", "Image Type", DataText.class, AS),
  SRC(GenericFieldsEnum.SRC),
  PHD(5, "PHD", "Photo Capture Date", DataText.class, N),
  HLL(GenericBinaryFieldsEnum.HLL),
  VLL(GenericBinaryFieldsEnum.VLL),
  SLC(GenericFieldsEnum.SLC),
  HPS_LEGACY(9, "HPS", "Horizontal Pixel Scale (legacy)", DataText.class, N),
  THPS(GenericFieldsEnum.THPS),
  VPS_LEGACY(10, "VPS", "Vertical Pixel Scale (legacy)", DataText.class, N),
  TVPS(GenericFieldsEnum.TVPS),
  CGA(GenericFieldsEnum.CGA),
  CSP(12, "CSP", "Color Space", DataText.class, A),
  SAP(13, "SAP", "Subject Acquisition Profile", DataText.class, N),
  FIP(
      14,
      "FIP",
      "Face Image Bounding Coordinates in Full Image",
      DataText.class,
      AN), // Not in 2007
  FPFI(15, "FPFI", "Face Image Path Coordinates in Full Image", DataText.class, AN), // Not in 2007
  SHPS(GenericFieldsEnum.SHPS),
  SVPS(GenericFieldsEnum.SVPS),
  DIST(18, "DIST", "Distortion", DataText.class, A), // Not in 2007
  LAF(19, "LAF", "Lighting Artifacts", DataText.class, A), // Not in 2007
  POS(20, "POS", "Subject Pose", DataText.class, A),
  POA(21, "POA", "Pose Offset Angle", DataText.class, NS),
  PXS_LEGACY(22, "PXS", "Photo Designation (legacy)", DataText.class, NS), // Only in 2007
  PAS(23, "PAS", "Photo Acquisition Source", DataText.class, U),
  SQS(24, "SQS", "Subject Quality Score", DataText.class, AN),
  SPA(25, "SPA", "Subject Pose Angles", DataText.class, NS),
  SXS(26, "SXS", "Subject Facial Description", DataText.class, AS),
  SEC(27, "SEC", "Subject Eye Color", DataText.class, A),
  SHC(28, "SHC", "Subject Hair Color", DataText.class, A),
  SFP_LEGACY(29, "SFP", "Facial Feature Points (legacy)", DataText.class, ANS),
  FFP(29, "FFP", "2D Facial Feature Points", DataText.class, ANS), // not in 2007
  DMM(GenericFieldsEnum.DMM), // not in 2007
  TMC(31, "TMC", "Tiered Markup Collection", DataText.class, N), // not in 2007
  THREEDF(32, "3DF", "3D Facial Feature Points", DataText.class, ANS), // not in 2007
  FEC(33, "FEC", "Feature Contours", DataText.class, AN), // not in 2007
  ICDR(34, "ICDR", "Image Capture Date Range Estimate", DataText.class, AN), // not in 2007
  FSB(35, "FSB", "Face/SMT/Body Image Quality Components", DataText.class, U), // since 2025
  TIF(36, "TIF", "Image Format", DataText.class, U), // since 2025
  /* 10.037 Reserved for Future Use Only by ANSI/NIST-ITL */
  COM(38, "COM", "Comments", DataText.class, U),
  T10(39, "T10", "Type-10 Reference Number", DataText.class, N),
  SMT(40, "SMT", "NCIC Code", DataText.class, AS),
  SMS(41, "SMS", "SMT Size of Size of Injury or Identifying Characteristic", DataText.class, N),
  SMD(42, "SMD", "SMT Descriptors", DataText.class, U),
  TCL(43, "TCL", "Tattoo Color", DataText.class, A), // change since 2025 name COL became TCL
  ITX(44, "ITX", "Image Transform", DataText.class, A), // Not in 2007
  OCC(45, "OCC", "Occlusions", DataText.class, AN), // Not in 2007
  SUB(GenericFieldsEnum.SUB), // Not in 2007
  CON(GenericFieldsEnum.CON), // Not in 2007
  PID(
      48,
      "PID",
      "Suspected Patterned Injury Detail (deprecated)",
      DataText.class,
      U), // Not in 2007, deprecated since 2025
  CID(
      49,
      "CID",
      "Cheiloscopic Image Description (deprecated)",
      DataText.class,
      U), // Not in 2007, deprecated since 2025
  VID(50, "VID", "Dental Visual Image Data Information", DataText.class, U), // Not in 2007
  RSP(51, "RSP", "Ruler or Scale Presence", DataText.class, U), // Not in 2007
  /* 10.052 – 10.198 Reserved for Future Use Only by ANSI/NIST-ITL */
  BRI(GenericFieldsEnum.BRI), // since 2025
  /* 10.200 – 10.900 UDF / User-Defined Fields */
  /* 10.901 Reserved for Future Use Only by ANSI/NIST-ITL */
  ANN(GenericFieldsEnum.ANN), // Not in 2007
  DUI(GenericFieldsEnum.DUI), // Not in 2007
  MMS(GenericFieldsEnum.MMS), // Not in 2007
  /* 10.905 – 10.991 Reserved for Future Use Only by ANSI/NIST-ITL */
  T2C(992, "T2C", "Record Cross Reference", DataText.class, N), // Not in 2007
  SAN(GenericFieldsEnum.SAN), // Not in 2007
  EFR(GenericFieldsEnum.EFR), // Not in 2007
  ASC(GenericFieldsEnum.ASC), // Not in 2007
  HAS(GenericFieldsEnum.HAS), // Not in 2007
  SOR(GenericFieldsEnum.SOR), // Not in 2007
  GEO(GenericFieldsEnum.GEO), // Not in 2007
  DATA(GenericBinaryFieldsEnum.DATA);

  private final RecordTypeEnum recordType = RecordTypeEnum.RT10;
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT10FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
