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
public enum RT14FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMP(3, "IMP", "impressionType", DataText.class, N),
  SRC(4, "SRC", "sourceAgency", DataText.class, U),
  FCD(5, "FCD", "captureDate", DataText.class, N),
  HLL(6, "HLL", "horizontalLineLength", DataText.class, N),
  VLL(7, "VLL", "verticalLineLength", DataText.class, N),
  SLC(8, "SLC", "scaleUnits", DataText.class, N),
  THPS(9, "THPS", "transmittedHorizontalPixelScale", DataText.class, N),
  TVPS(10, "TVPS", "transmittedVerticalPixelScale", DataText.class, N),
  CGA(11, "CGA", "compressionAlgorithm", DataText.class, AN),
  BPX(12, "BPX", "bitsPerPixel", DataText.class, N),
  FGP(13, "FGP", "fingerPosition", DataText.class, N),
  PPD(14, "PPD", "printPositionDescriptors", DataText.class, N),
  PPC(15, "PPC", "printPositionCoordinates", DataText.class, AN),
  SHPS(16, "SHPS", "scannedHorizontalPixelScale", DataText.class, N),
  SVPS(17, "SVPS", "scannedVerticalPixelScale", DataText.class, N),
  AMP(18, "AMP", "amputatedOrBandaged", DataText.class, AN),
  COM(20, "COM", "comment", DataText.class, U),
  SEG(21, "SEG", "fingerprintSegmentationPosition", DataText.class, N),
  NQM(22, "NQM", "nistQualtyMetric", DataText.class, N),
  SQM(23, "SQM", "segmentationQualityMetric", DataText.class, H),
  FQM(24, "FQM", "fingerprintQualityMetric", DataText.class, H),
  ASEG(25, "ASEG", "alternateFingerSegmentPosition", DataText.class, N),
  SCF(26, "SCF", "simultaneousCapture", DataText.class, N),
  SIF(27, "SIF", "stitchedImageFlag", DataText.class, A),
  /* 14.028-14.029 reserved for future use */
  DMM(30, "DMM", "deviceMonitoringMode", DataText.class, A),
  FAP(31, "FAP", "frictionRidgeSegmentPosition", DataText.class, N),
  /* 14.032-14.045 reserved for future use */
  SUB(46, "SUB", "imageSubjectCondition", DataText.class, AN),
  CON(47, "CON", "captureOrganizationName", DataText.class, U),
  /* 14.048-14.199 reserved for future use */
  /* 14.200–14.900 user defined fields */
  FCT(901, "FCT", "frictionRidgeCaptureTechnology", DataText.class, N),
  ANN(902, "ANN", "annotationInformation", DataText.class, U),
  DUI(903, "DUI", "deviceUniqueIdentifier", DataText.class, ANS),
  MMS(904, "MMS", "makeModelSerialNumber", DataText.class, U),
  /* 14.905-14.992 reserved for future use */
  SAN(993, "SAN", "sourceAgencyName", DataText.class, U),
  EFR(994, "EFR", "externalFileReference", DataText.class, U),
  ASC(995, "ASC", "associatedContext", DataText.class, N),
  HAS(996, "HAS", "hash", DataText.class, H),
  SOR(997, "SOR", "sourceRepresentation", DataText.class, N),
  GEO(998, "GEO", "geographicSampleAcquisitionLocation", DataText.class, ANS),
  DATA(GenericImageTypeEnum.DATA);

  private final String recordType = "RT14";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT14FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
