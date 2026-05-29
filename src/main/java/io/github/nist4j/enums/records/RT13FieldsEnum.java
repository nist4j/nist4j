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
import lombok.Getter;

@SuppressWarnings("unused")
@Getter
public enum RT13FieldsEnum implements IFieldTypeEnum {
  LEN(GenericBinaryFieldsEnum.LEN),
  IDC(GenericBinaryFieldsEnum.IDC),
  IMP(GenericBinaryFieldsEnum.IMP),
  SRC(GenericFieldsEnum.SRC),
  LCD(5, "LCD", "Latent Capture Date", DataText.class, N),
  HLL(GenericBinaryFieldsEnum.HLL),
  VLL(GenericBinaryFieldsEnum.VLL),
  SLC(GenericFieldsEnum.SLC),
  THPS(GenericFieldsEnum.THPS),
  TVPS(GenericFieldsEnum.TVPS),
  CGA(GenericFieldsEnum.CGA),
  BPX(GenericFieldsEnum.BPX),
  FGP(13, "FGP", "Friction Ridge Generalized Position", DataText.class, N),
  SPD(14, "SPD", "Search Position Descriptors", DataText.class, AN),
  PPC(15, "PPC", "Print Position Coordinates", DataText.class, AN),
  SHPS(GenericFieldsEnum.SHPS),
  SVPS(GenericFieldsEnum.SVPS),
  RSP(18, "RSP", "Ruler or Scale Presence", DataText.class, U),
  REM(19, "REM", "Resolution Method", DataText.class, U),
  COM(20, "COM", "Comments", DataText.class, U),
  CSP(21, "CSP", "Color Space", DataText.class, U), // since 2025
  /* 13.022 – 13.023 Reserved for Future Use Only by ANSI/NIST-ITL */
  LQM(24, "LQM", "Latent Quality Metric", DataText.class, AN),
  /* 13.025 - 13.028 Reserved for Future Use Only by ANSI/NIST-ITL */
  FQC(29, "FQC", "Friction Ridge Quality Components", DataText.class, U), // since 2025
  /* 13.030 - 13.045 Reserved for Future Use Only by ANSI/NIST-ITL */
  SUB(GenericFieldsEnum.SUB),
  CON(GenericFieldsEnum.CON),
  /* 13.048 - 13.198 Reserved for Future Use Only by ANSI/NIST-ITL */
  BRI(GenericFieldsEnum.BRI),
  /* 13.200 – 13.900 UDF / User-Defined Fields */
  FCT(GenericFieldsEnum.FCT),
  ANN(GenericFieldsEnum.ANN),
  DUI(GenericFieldsEnum.DUI),
  MMS(GenericFieldsEnum.MMS),
  /* 13.905 – 13.992 Reserved for Future Use Only by ANSI/NIST-ITL */
  SAN(GenericFieldsEnum.SAN),
  EFR(GenericFieldsEnum.EFR),
  ASC(GenericFieldsEnum.ASC),
  HAS(GenericFieldsEnum.HAS),
  SOR(GenericFieldsEnum.SOR),
  GEO(GenericFieldsEnum.GEO),
  DATA(GenericBinaryFieldsEnum.DATA);

  private final RecordTypeEnum recordType = RecordTypeEnum.RT13;
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT13FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }

  RT13FieldsEnum(
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
