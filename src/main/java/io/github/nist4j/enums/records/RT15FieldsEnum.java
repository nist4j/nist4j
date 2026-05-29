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
public enum RT15FieldsEnum implements IFieldTypeEnum {
  LEN(GenericBinaryFieldsEnum.LEN),
  IDC(GenericBinaryFieldsEnum.IDC),
  IMP(GenericBinaryFieldsEnum.IMP),
  SRC(GenericFieldsEnum.SRC),
  PCD(5, "PCD", "Palm Print Capture Date", DataText.class, N),
  HLL(GenericBinaryFieldsEnum.HLL),
  VLL(GenericBinaryFieldsEnum.VLL),
  SLC(GenericFieldsEnum.SLC),
  THPS(GenericFieldsEnum.THPS),
  TVPS(GenericFieldsEnum.TVPS),
  CGA(GenericFieldsEnum.CGA),
  BPX(GenericFieldsEnum.BPX),
  FGP(13, "FGP", "Friction Ridge Generalized Position", DataText.class, N),
  // no 14
  CSP(15, "CSP", "Color Space", DataText.class, N), // since 2025
  SHPS(GenericFieldsEnum.SHPS),
  SVPS(GenericFieldsEnum.SVPS),
  AMP(18, "AMP", "Amputated or Bandaged", DataText.class, AN),
  /* 15.019 Reserved for Future Use Only by ANSI/NIST-ITL */
  COM(20, "COM", "Comments", DataText.class, U),
  SEG(21, "SEG", "Palm Segment Position", DataText.class, N),
  /* 15.022 – 15.023 Reserved for Future Use Only by ANSI/NIST-ITL */
  PQM(24, "PQM", "Palm Quality Metric", DataText.class, U),
  /* 15.025 – 15.028 Reserved for Future Use Only by ANSI/NIST-ITL */
  FQC(29, "FQC", "Friction Ridge Quality Components", DataText.class, U), // since 2025
  DMM(GenericFieldsEnum.DMM),
  PAP(31, "PAP", "Subject Acquisition Profile – Palm Print", DataText.class, N),
  /* 15.032 – 15.045 Reserved for Future Use Only by ANSI/NIST-ITL */
  SUB(GenericFieldsEnum.SUB),
  CON(GenericFieldsEnum.CON),
  /* 15.048 – 15.198 Reserved for Future Use Only by ANSI/NIST-ITL */
  BRI(GenericFieldsEnum.BRI), // since 2025
  /* 15.200 – 15.900 UDF / User Defined Fields */
  FCT(GenericFieldsEnum.FCT),
  ANN(GenericFieldsEnum.ANN),
  DUI(GenericFieldsEnum.DUI),
  MMS(GenericFieldsEnum.MMS),
  /* 15.905 – 15.992 Reserved for Future Use Only by ANSI/NIST-ITL */
  SAN(GenericFieldsEnum.SAN),
  EFR(GenericFieldsEnum.EFR),
  ASC(GenericFieldsEnum.ASC),
  HAS(GenericFieldsEnum.HAS),
  SOR(GenericFieldsEnum.SOR),
  GEO(GenericFieldsEnum.GEO),
  DATA(GenericBinaryFieldsEnum.DATA);

  private final RecordTypeEnum recordType = RecordTypeEnum.RT15;
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
