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

import static io.github.nist4j.enums.CharacterTypeEnum.N;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.Getter;

/**
 * Enum for deprecated RecordTypes : 3,5,6
 */
@Getter
public enum GenericBinaryFieldsEnum implements IFieldTypeEnum {
  LEN(GenericFieldsEnum.LEN),
  IDC(GenericFieldsEnum.IDC),
  IMP(3, "IMP", "Impression Type", DataText.class, N),
  FGP(4, "FGP", "Finger Position", DataText.class, N),
  ISR(5, "ISR", "Image Scanning Resolution", DataText.class, N),
  HLL(6, "HLL", "Horizontal Line Length", DataText.class, N),
  VLL(7, "VLL", "Vertical Line Length", DataText.class, N),
  GCA(8, "GCA", "Greyscale Compression Algorithm", DataText.class, N),
  DATA(GenericFieldsEnum.DATA);

  private final RecordTypeEnum recordType = null;
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> GenericBinaryFieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }

  GenericBinaryFieldsEnum(
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
