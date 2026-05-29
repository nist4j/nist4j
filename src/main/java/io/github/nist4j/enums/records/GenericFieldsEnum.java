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
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.Getter;

@Getter
public enum GenericFieldsEnum implements IFieldTypeEnum {
  LEN(1, "LEN", "Record Length", DataText.class, N),
  IDC(2, "IDC", "Image Designation Character", DataText.class, N),
  SRC(4, "SRC", "Source Agency", DataText.class, U),
  // 5
  SLC(8, "SLC", "Scale Units", DataText.class, N),
  THPS(9, "THPS", "Transmitted Horizontal Pixel Scale", DataText.class, N),
  TVPS(10, "TVPS", "Transmitted Vertical Pixel Scale", DataText.class, N),
  CGA(11, "CGA", "Compression Algorithm", DataText.class, AN),
  BPX(12, "BPX", "Bits Per Pixel", DataText.class, N),
  FGP(13, "FGP", "Friction Ridge Generalized Position", DataText.class, N),
  // 14...198
  SHPS(16, "SHPS", "Scanned Horizontal Pixel Scale", DataText.class, N),
  SVPS(17, "SVPS", "Scanned Vertical Pixel Scale", DataText.class, N),
  // 18...29
  DMM(30, "DMM", "Device Monitoring Mode", DataText.class, A),
  // ...
  SUB(46, "SUB", "Image Subject Condition", DataText.class, AN),
  CON(47, "CON", "Capture Organization Name", DataText.class, U),
  // 31...198
  BRI(199, "BRI", "Biometric Record Identifier", DataText.class, U),
  //
  FCT(901, "FCT", "Friction Ridge Capture Technology", DataText.class, N),
  ANN(902, "ANN", "Annotation Information", DataText.class, U),
  DUI(903, "DUI", "Device Unique Identifier", DataText.class, ANS),
  MMS(904, "MMS", "Make, Model, Serial Number", DataText.class, U),
  // ...
  SAN(993, "SAN", "Source Agency Name", DataText.class, U),
  EFR(994, "EFR", "External Data File References", DataText.class, U),
  ASC(995, "ASC", "Associated Context", DataText.class, AN),
  HAS(996, "HAS", "HASH", DataText.class, H),
  SOR(997, "SOR", "Source Representation", DataText.class, N),
  GEO(998, "GEO", "Geographic Sample Acquisition Location Field", DataText.class, U),
  DATA(999, "DATA", "Image or Binary Data", DataImage.class, B);

  private final RecordTypeEnum recordType = null;
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  GenericFieldsEnum(
      int id,
      String code,
      String description,
      Class<? extends Data<?>> typeClass,
      CharacterTypeEnum characterType) {
    this.id = id;
    this.code = code;
    this.description = description;
    this.typeClass = typeClass;
    this.characterType = characterType;
  }
}
