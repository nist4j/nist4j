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

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RT14FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMP(3, "IMP", "impressionType", DataText.class),
  SRC(4, "SRC", "sourceAgency", DataText.class),
  FCD(5, "FCD", "captureDate", DataText.class),
  HLL(6, "HLL", "horizontalLineLength", DataText.class),
  VLL(7, "VLL", "verticalLineLength", DataText.class),
  SLC(8, "SLC", "scaleUnits", DataText.class),
  THPS(9, "THPS", "transmittedHorizontalPixelScale", DataText.class),
  TVPS(10, "TVPS", "transmittedVerticalPixelScale", DataText.class),
  CGA(11, "CGA", "compressionAlgorithm", DataText.class),
  BPX(12, "BPX", "bitsPerPixel", DataText.class),
  FGP(13, "FGP", "fingerPosition", DataText.class),
  PPD(14, "PPD", "printPositionDescriptors", DataText.class),
  PPC(15, "PPC", "printPositionCoordinates", DataText.class),
  SHPS(16, "SHPS", "scannedHorizontalPixelScale", DataText.class),
  SVPS(17, "SVPS", "scannedVerticalPixelScale", DataText.class),
  AMP(18, "AMP", "amputatedOrBandaged", DataText.class),
  COM(20, "COM", "comment", DataText.class),
  SEG(21, "SEG", "fingerprintSegmentationPosition", DataText.class),
  NQM(22, "NQM", "nistQualtyMetric", DataText.class),
  SQM(23, "SQM", "segmentationQualityMetric", DataText.class),
  FQM(24, "FQM", "fingerprintQualityMetric", DataText.class),
  ASEG(25, "ASEG", "alternateFingerSegmentPosition", DataText.class),
  SCF(26, "SCF", "simultaneousCapture", DataText.class),
  SIF(27, "SIF", "stitchedImageFlag", DataText.class),
  DMM(30, "DMM", "deviceMonitoringMode", DataText.class),
  FAP(31, "FAP", "frictionRidgeSegmentPosition", DataText.class),
  SUB(46, "SUB", "imageSubjectCondition", DataText.class),
  CON(47, "CON", "captureOrganizationName", DataText.class),
  FCT(901, "FCT", "frictionRidgeCaptureTechnology", DataText.class),
  ANN(902, "ANN", "annotationInformation", DataText.class),
  DUI(903, "DUI", "deviceUniqueIdentifier", DataText.class),
  MMS(904, "MMS", "makeModelSerialNumber", DataText.class),
  SAN(993, "SAN", "sourceAgencyName", DataText.class),
  EFR(994, "EFR", "externalFileReference", DataText.class),
  ASC(995, "ASC", "associatedContext", DataText.class),
  HAS(996, "HAS", "hash", DataText.class),
  SOR(997, "SOR", "sourceRepresentation", DataText.class),
  GEO(998, "GEO", "geographicSampleAcquisitionLocation", DataText.class),
  DATA(999, "DATA", "imageData", DataImage.class);

  private final String recordType = "RT14";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data> typeClass;

  <T extends IFieldTypeEnum> RT14FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass());
  }
}
