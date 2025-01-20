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
import lombok.Getter;

/**
 * Enum for deprecated RecordTypes : 3,5,6
 */
@Getter
public enum RT13FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMP(GenericImageTypeEnum.IMP),
  SRC(4, "SRC", "SOURCE AGENCY / ORI", DataText.class),
  LCD(5, "LCD", "LATENT CAPTURE DATE", DataText.class),
  HLL(6, "HLL", "HORIZONTAL LINE LENGTH", DataText.class),
  VLL(7, "VLL", "VERTICAL LINE LENGTH", DataText.class),
  SLC(8, "SLC", "SCALE UNITS", DataText.class),
  THPS(9, "THPS", "TRANSMITTED HORIZONTAL PIXEL SCALE", DataText.class),
  TVPS(10, "TVPS", "TRANSMITTED VERTICAL PIXEL SCALE", DataText.class),
  CGA(11, "CGA", "COMPRESSION ALGORITHM", DataText.class),
  BPX(12, "BPX", "BITS PER PIXEL", DataText.class),
  FGP(13, "FGP", "FINGER / PALM POSITION", DataText.class),
  SPD(14, "SPD", "SEARCH POSITION DESCRIPTORS", DataText.class),
  PPC(15, "PPC", "PRINT POSITION COORDINATES", DataText.class),
  SHPS(16, "SHPS", "SCANNED HORIZONTAL PIXEL SCALE", DataText.class),
  SVPS(17, "SVPS", "SCANNED VERTICAL PIXEL SCALE", DataText.class),
  RSP(18, "RSP", "RULER OR SCALE PRESENCE", DataText.class),
  REM(19, "REM", "RESOLUTION METHOD", DataText.class),
  COM(20, "COM", "COMMENT", DataText.class),
  /*21,22,23 "RESERVED FOR FUTURE DEFINITION"*/
  LQM(24, "LQM", "LATENT QUALITY METRIC", DataText.class),
  /*25,...45 "RESERVED FOR FUTURE DEFINITION"*/
  SUB(46, "SUB", "IMAGE SUBJECT CONDITION", DataText.class),
  CON(47, "CON", "CAPTURE ORGANIZATION NAME", DataText.class),
  /*48,...,199 "RESERVED FOR FUTURE DEFINITION"*/
  /*200,...,900 "USER-DEFINED FIELDS"*/
  FCT(901, "FCT", "FRICTION RIDGE CAPTURE TECHNOLOGY", DataText.class),
  ANN(902, "ANN", "ANNOTATION INFORMATION", DataText.class),
  DUI(903, "DUI", "DEVICE UNIQUE IDENTIFIER", DataText.class),
  MMS(904, "MMS", "MAKE/MODEL/SERIAL NUMBER", DataText.class),
  /*905,...,992 "RESERVED FOR FUTURE"*/
  SAN(993, "SAN", "SOURCE AGENCY NAME", DataText.class),
  EFR(994, "EFR", "EXTERNAL FILE REFERENCE", DataText.class),
  ASC(995, "ASC", "ASSOCIATED CONTEXT", DataText.class),
  HAS(996, "HAS", "HASH", DataText.class),
  SOR(997, "SOR", "SOURCE REPRESENTATION", DataText.class),
  GEO(998, "GEO", "GEOGRAPHIC SAMPLE ACQUISITION LOCATION", DataText.class),
  DATA(999, "DATA", "imageData", DataImage.class);

  private final String recordType = "RT13";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data> typeClass;

  <T extends IFieldTypeEnum> RT13FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass());
  }

  RT13FieldsEnum(int id, String code, String description, Class<? extends Data> typeClass) {
    this.id = id;
    this.code = code;
    this.description = description;
    this.typeClass = typeClass;
  }
}
