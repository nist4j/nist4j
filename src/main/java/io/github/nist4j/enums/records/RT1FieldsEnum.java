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
public enum RT1FieldsEnum implements IFieldTypeEnum {
  LEN(RTDefaultFieldsEnum.LEN),
  VER(2, "VER", "Version Number", DataText.class, N),
  CNT(3, "CNT", "Transaction Content", DataText.class, N),
  TOT(4, "TOT", "Type Of Transaction", DataText.class, A),
  DAT(5, "DAT", "Date", DataText.class, N),
  PRY(6, "PRY", "Priority", DataText.class, N),
  DAI(7, "DAI", "Destination Agency Identifier", DataText.class, ANS),
  ORI(8, "ORI", "Originating Agency Identifier", DataText.class, ANS),
  TCN(9, "TCN", "Transaction Control Number", DataText.class, ANS),
  TCR(10, "TCR", "Transaction Control Reference Number", DataText.class, ANS),
  NSR(11, "NSR", "Native Scanning Resolution", DataText.class, NS),
  NTR(12, "NTR", "Nominal Transmitting Resolution", DataText.class, NS),
  DOM(13, "DOM", "Domain Name", DataText.class, ANS),
  GMT(14, "GMT", "Greenwich Mean Time", DataText.class, AN),
  DCS(15, "DCS", "Character Encoding", DataText.class, ANS),
  APS(16, "APS", "Application Profile Specifications", DataText.class, ANS),
  ANM(17, "ANM", "Agency Names", DataText.class, ANS),
  GNS(18, "GNS", "Geographic Name Set", DataText.class, AN);

  private final String recordType = "RT1";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  @SuppressWarnings("SameParameterValue")
  <T extends IFieldTypeEnum> RT1FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
