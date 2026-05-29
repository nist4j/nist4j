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
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("unused")
@AllArgsConstructor
@Getter
public enum RT5FieldsEnum implements IFieldTypeEnum {
  LEN(GenericBinaryFieldsEnum.LEN),
  IDC(GenericBinaryFieldsEnum.IDC),
  IMP(GenericBinaryFieldsEnum.IMP),
  FGP(GenericBinaryFieldsEnum.FGP),
  ISR(GenericBinaryFieldsEnum.ISR),
  HLL(GenericBinaryFieldsEnum.HLL),
  VLL(GenericBinaryFieldsEnum.VLL),
  GCA(GenericBinaryFieldsEnum.GCA),
  DATA(GenericBinaryFieldsEnum.DATA);

  @SuppressWarnings("deprecation")
  private final RecordTypeEnum recordType = RecordTypeEnum.RT5;

  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT5FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
