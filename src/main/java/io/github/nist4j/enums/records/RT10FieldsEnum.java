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
public enum RT10FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMT(3, "IMT", "Image Type", DataText.class, AS),
  SRC(4, "SRC", "Source Agency / ORI", DataText.class, U),
  PHD(5, "PHD", "Photo Date", DataText.class, N),
  HLL(6, "HLL", "horizontalLineLength", DataText.class, N),
  VLL(7, "VLL", "verticalLineLength", DataText.class, N),
  SLC(8, "SLC", "scaleUnits", DataText.class, N),
  HPS_LEGACY(9, "HPS", "Horizontal pixel scale", DataText.class, N),
  THPS(9, "THPS", "transmittedHorizontalPixelScale", DataText.class, N),
  VPS_LEGACY(10, "VPS", "Vertical pixel scale", DataText.class, N),
  TVPS(10, "TVPS", "transmittedVerticalPixelScale", DataText.class, N),
  CGA(11, "CGA", "compressionAlgorithm", DataText.class, A),
  CSP(12, "CSP", "color space", DataText.class, A),
  SAP(13, "SAP", "Subject Acquisition Profile", DataText.class, N),
  FIP(
      14,
      "FIP",
      "Facial Image Bounding box coordinates in Full Image",
      DataText.class,
      AN), // Not in 2007
  FPFI(15, "FPFI", "Face Image Path coordinates in Full Image", DataText.class, AN), // Not in 2007
  SHPS(16, "SHPS", "Scan Hor Pixel Scale", DataText.class, N),
  SVPS(17, "SVPS", "Scan Vert Pixel Scale", DataText.class, N),
  DIST(18, "DIST", "Distortion", DataText.class, A), // Not in 2007
  LAF(19, "LAF", "Lighting Artifacts", DataText.class, A), // Not in 2007
  POS(20, "POS", "Subject Pose", DataText.class, A),
  POA(21, "POA", "Pose Offset Angle", DataText.class, NS),
  PXS_LEGACY(22, "PXS", "Photo Designation", DataText.class, NS), // Only in 2007
  PAS(23, "PAS", "Photo Acquisition Source", DataText.class, U),
  SQS(24, "SQS", "Subject Quality Score", DataText.class, AN),
  SPA(25, "SPA", "Subject Pose Angles", DataText.class, NS),
  SXS(26, "SXS", "Subject Facial Description", DataText.class, AS),
  SEC(27, "SEC", "Subject Eye Color", DataText.class, A),
  SHC(28, "SHC", "Subject Air Color", DataText.class, A),
  SFP_LEGACY(29, "SFP", "Facial Feature points", DataText.class, ANS),
  FFP(29, "FFP", "2D Facial Feature Points", DataText.class, ANS), // not in 2007
  DMM(30, "DMM", "Device Monitoring Mode", DataText.class, A), // not in 2007
  TMC(31, "TMC", "Tiered Markup Collection", DataText.class, N), // not in 2007
  THREEDF(32, "3DF", "3D Facial Feature points", DataText.class, ANS), // not in 2007
  FEC(33, "FEC", "Feature Contours", DataText.class, AN), // not in 2007
  ICDR(34, "ICDR", "Image Capture Date Range Estimate", DataText.class, AN), // not in 2007
  /*35 to 37 RESERVED FOR FUTURE USE*/
  COM(38, "COM", "Comment", DataText.class, U),
  T10(39, "T10", "Type-10 reference number", DataText.class, N),
  SMT(40, "SMT", "NCIC Designation Code", DataText.class, AS),
  SMS(41, "SMS", "Scar / Mark / Tattoo size", DataText.class, N),
  SMD(42, "SMD", "SMT Descriptors", DataText.class, U),
  COL(43, "COL", "Colors Present", DataText.class, A),
  ITX(44, "ITX", "Image transformation", DataText.class, A), // Not in 2007
  OCC(45, "OCC", "Occlusions", DataText.class, AN), // Not in 2007
  SUB(46, "SUB", "Image Subject Condition", DataText.class, AN), // Not in 2007
  CON(47, "CON", "Capture organization name", DataText.class, U), // Not in 2007
  PID(48, "PID", "Suspected patterned injury detail", DataText.class, U), // Not in 2007
  CID(49, "CID", "Cheiloscopic Image Description", DataText.class, U), // Not in 2007
  VID(50, "VID", "Dental visual image Data Information", DataText.class, U), // Not in 2007
  RSP(51, "RSP", "Ruler or Scale présence", DataText.class, U), // Not in 2007
  /*52 to 199 RESERVED FOR FUTURE USE*/
  /*200 to 900 User defined fields*/
  /*901 RESERVED FOR FUTURE USE*/
  ANN(902, "ANN", "Annotation information", DataText.class, U), // Not in 2007
  DUI(903, "DUI", "Device Unique identifier", DataText.class, ANS), // Not in 2007
  MMS(904, "MMS", "Make / Model / Serial number", DataText.class, U), // Not in 2007
  /*905 to 991 RESERVED FOR FUTURE USE*/
  T2C(992, "T2C", "Type-2 record cross reference", DataText.class, N), // Not in 2007
  SAN(993, "SAN", "Source Agence name", DataText.class, U), // Not in 2007
  EFR(994, "EFR", "External file reference", DataText.class, U), // Not in 2007
  ASC(995, "ASC", "Associated context", DataText.class, AN), // Not in 2007
  HAS(996, "HAS", "HASH", DataText.class, H), // Not in 2007
  SOR(997, "SOR", "Source Representation", DataText.class, N), // Not in 2007
  GEO(998, "GEO", "Geographic Sample Acquisition Location", DataText.class, U), // Not in 2007
  DATA(GenericImageTypeEnum.DATA);

  private final String recordType = "RT10";
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
