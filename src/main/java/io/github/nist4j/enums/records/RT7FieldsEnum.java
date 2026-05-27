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
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("unused")
@AllArgsConstructor
@Getter
public enum RT7FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  IMT(3, "IMT", "Image Type", DataText.class, N),
  IMD(4, "IMD", "Image Description", DataText.class, N),
  PCN(5, "PCN", "Pattern Classification", DataText.class, N),
  PCN2(6, "PCN2", "Second Pattern Classification", DataText.class, N),
  PCN3(7, "PCN3", "Third Pattern Classification", DataText.class, N),
  PCN4(8, "PCN4", "Fourth Pattern Classification", DataText.class, N),
  PCN5(9, "PCN5", "Fifth Pattern Classification", DataText.class, N),
  IMR(10, "IMR", "Image Capture Resolution", DataText.class, N),
  IMR2(102, "IMR2", "Image Capture Resolution", DataText.class, N),
  IMR3(103, "IMR3", "Image Capture Resolution", DataText.class, N),
  IMR4(104, "IMR4", "Image Capture Resolution", DataText.class, N),
  IMR5(105, "IMR5", "Image Capture Resolution", DataText.class, N),
  IMR6(106, "IMR6", "Image Capture Resolution", DataText.class, N),
  IMR7(107, "IMR7", "Image Capture Resolution", DataText.class, N),
  IMR8(108, "IMR8", "Image Capture Resolution", DataText.class, N),
  IMR9(109, "IMR9", "Image Capture Resolution", DataText.class, N),
  IMR10(110, "IMR10", "Image Capture Resolution", DataText.class, N),
  IMR11(111, "IMR11", "Image Capture Resolution", DataText.class, N),
  HLL(11, "HLL", "horizontal Line Length", DataText.class, N),
  VLL(12, "VLL", "vertical Line Length", DataText.class, N),
  GCA(13, "GCA", "Grayscale Compression Algorithm", DataText.class, N),
  DATA(GenericImageTypeEnum.DATA);

  private final String recordType = "RT7";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data<?>> typeClass;
  private final CharacterTypeEnum characterType;

  <T extends IFieldTypeEnum> RT7FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass(),
        parentEnum.getCharacterType());
  }
}
