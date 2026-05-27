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
public enum RT15FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMP(3, "IMP", "impressionType", DataText.class, N),
  SRC(4, "SRC", "sourceAgency", DataText.class, U),
  PCD(5, "PCD", "captureDate", DataText.class, N),
  HLL(6, "HLL", "horizontalLineLength", DataText.class, N),
  VLL(7, "VLL", "verticalLineLength", DataText.class, N),
  SLC(8, "SLC", "scaleUnits", DataText.class, N),
  THPS(9, "THPS", "transmittedHorizontalPixelScale", DataText.class, N),
  TVPS(10, "TVPS", "transmittedVerticalPixelScale", DataText.class, N),
  CGA(11, "CGA", "compressionAlgorithm", DataText.class, AN),
  BPX(12, "BPX", "bitsPerPixel", DataText.class, N),
  FGP(13, "FGP", "palmPosition", DataText.class, N),
  SHPS(16, "SHPS", "scannedHorizontalPixelScale", DataText.class, N),
  SVPS(17, "SVPS", "scannedVerticalPixelScale", DataText.class, N),
  AMP(18, "AMP", "amputatedOrBandaged", DataText.class, AN),
  COM(20, "COM", "comment", DataText.class, U),
  SEG(21, "SEG", "palmSegmentationPosition", DataText.class, N),
  /* 15.022-15.023 reserved for future used */
  PQM(24, "PQM", "palmQualityMetric", DataText.class, H),
  /* 15.025-15.029 reserved for future used */
  DMM(30, "DMM", "DEVICE MONITORING MODE", DataText.class, A),
  PAP(31, "PAP", "SUBJECT ACQUISITION PROFILE _ PALM PRINT", DataText.class, N),
  /* 15.032-15.045 reserved for future used */
  SUB(46, "SUB", "imageSubjectCondition", DataText.class, AN),
  CON(47, "CON", "captureOrganizationName", DataText.class, U),
  /* 15.048-15.199 reserved for future used */
  /* 15.200-15.900 used defined fields */
  FCT(901, "FCT", "frictionRidgeCaptureTechnology", DataText.class, N),
  ANN(902, "ANN", "annotationInformation", DataText.class, U),
  DUI(903, "DUI", "deviceUniqueIdentifier", DataText.class, ANS),
  MMS(904, "MMS", "makeModelSerialNumber", DataText.class, U),
  /* 15.905-15.992 reserved for future used */
  SAN(993, "SAN", "sourceAgencyName", DataText.class, U),
  EFR(994, "EFR", "externalFileReference", DataText.class, U),
  ASC(995, "ASC", "associatedContext", DataText.class, N),
  HAS(996, "HAS", "hash", DataText.class, H),
  SOR(997, "SOR", "sourceRepresentation", DataText.class, N),
  GEO(998, "GEO", "geographicSampleAcquisitionLocation", DataText.class, U),
  DATA(GenericImageTypeEnum.DATA);

  private final String recordType = "RT15";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT15FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
