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
public enum RT14FieldsEnum implements IFieldTypeEnum {
  LEN(GenericBinaryFieldsEnum.LEN),
  IDC(GenericBinaryFieldsEnum.IDC),
  IMP(GenericBinaryFieldsEnum.IMP),
  SRC(GenericFieldsEnum.SRC),
  FCD(5, "FCD", "Fingerprint Capture Date", DataText.class, N),
  HLL(GenericBinaryFieldsEnum.HLL),
  VLL(GenericBinaryFieldsEnum.VLL),
  SLC(GenericFieldsEnum.SLC),
  THPS(GenericFieldsEnum.THPS),
  TVPS(GenericFieldsEnum.TVPS),
  CGA(GenericFieldsEnum.CGA),
  BPX(GenericFieldsEnum.BPX),
  FGP(13, "FGP", "Friction Ridge Generalized Position", DataText.class, N),
  PPD(14, "PPD", "Print Position Descriptors", DataText.class, N),
  PPC(15, "PPC", "Print Position Coordinates", DataText.class, AN),
  SHPS(GenericFieldsEnum.SHPS),
  SVPS(GenericFieldsEnum.SVPS),
  AMP(18, "AMP", "Amputated or Bandaged", DataText.class, AN),
  CSP(19, "CSP", "Color Space", DataText.class, A), // since 2025
  COM(20, "COM", "Comments", DataText.class, U),
  SEG(21, "SEG", "Finger Segment Position", DataText.class, N),
  NQM(22, "NQM", "Nist Qualty Metric (legacy)", DataText.class, N),
  SQM(23, "SQM", "Segmentation Quality Metric", DataText.class, H),
  FQM(24, "FQM", "Fingerprint Quality Metric", DataText.class, U),
  ASEG(25, "ASEG", "Alternate Finger Segment Positions", DataText.class, N),
  SCF(26, "SCF", "Simultaneous Capture", DataText.class, N),
  SIF(27, "SIF", "Stitched Image Flag", DataText.class, A),
  /* 14.028 Reserved for Future Use Only by ANSI/NIST-ITL */
  FQC(29, "FQC", "Friction Ridge Quality Components", DataText.class, U), // since 2025
  DMM(GenericFieldsEnum.DMM),
  FAP(31, "FAP", "Subject Acquisition Profile – Fingerprint", DataText.class, N),
  /* 14.032 – 14.045 Reserved for Future Use Only by ANSI/NIST-ITL */
  SUB(GenericFieldsEnum.SUB),
  CON(GenericFieldsEnum.CON),
  /* 14.048 - 14.198 Reserved for Future Use Only by ANSI/NIST-ITL */
  BRI(GenericFieldsEnum.BRI), // since 2025
  /* 14.200 – 14.900 UDF / User-Defined Fields */
  FCT(GenericFieldsEnum.FCT),
  ANN(GenericFieldsEnum.ANN),
  DUI(GenericFieldsEnum.DUI),
  MMS(GenericFieldsEnum.MMS),
  /* 14.905 – 14.992 Reserved for Future Use Only by ANSI/NIST-ITL */
  SAN(GenericFieldsEnum.SAN),
  EFR(GenericFieldsEnum.EFR),
  ASC(GenericFieldsEnum.ASC),
  HAS(GenericFieldsEnum.HAS),
  SOR(GenericFieldsEnum.SOR),
  GEO(GenericFieldsEnum.GEO),
  DATA(GenericBinaryFieldsEnum.DATA);

  private final RecordTypeEnum recordType = RecordTypeEnum.RT14;
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
