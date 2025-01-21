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
public enum GenericImageTypeEnum implements IFieldTypeEnum {
  LEN(RTDefaultFieldsEnum.LEN),
  IDC(RTDefaultFieldsEnum.IDC),
  IMP(3, "IMP", "impressionType", DataText.class),
  FGP(4, "FGP", "fingerPosition", DataText.class),
  ISR(5, "ISR", "image Scanning Resolution", DataText.class),
  HLL(6, "HLL", "horizontal Line Length", DataText.class),
  VLL(7, "VLL", "vertical Line Length", DataText.class),
  GCA(8, "GCA", "Greyscale Compression Algorithm", DataText.class),
  DATA(999, "DATA", "imageData", DataImage.class);

  private final String recordType = "RTx";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data> typeClass;

  <T extends IFieldTypeEnum> GenericImageTypeEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass());
  }

  GenericImageTypeEnum(int id, String code, String description, Class<? extends Data> typeClass) {
    this.id = id;
    this.code = code;
    this.description = description;
    this.typeClass = typeClass;
  }
}
