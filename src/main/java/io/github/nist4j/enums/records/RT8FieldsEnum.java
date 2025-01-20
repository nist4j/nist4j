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
public enum RT8FieldsEnum implements IFieldTypeEnum {
  LEN(GenericImageTypeEnum.LEN),
  IDC(GenericImageTypeEnum.IDC),
  SIG(3, "SIG", "Signature Type", DataText.class),
  SRT(4, "SRT", "Signature representation Type", DataText.class),
  ISR(5, "ISR", "Image scanning resolution", DataText.class),
  HLL(6, "HLL", "horizontal Line Length", DataText.class),
  VLL(7, "VLL", "vertical Line Length", DataText.class),
  DATA(999, "DATA", "imageData", DataImage.class);

  private final String recordType = "RT8";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data> typeClass;

  <T extends IFieldTypeEnum> RT8FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass());
  }
}
