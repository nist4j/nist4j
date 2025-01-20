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

@Getter
public enum RTDefaultFieldsEnum implements IFieldTypeEnum {
  LEN(1, "LEN", "Logical record length", DataText.class),
  IDC(2, "IDC", "Image designation character", DataText.class),
  DATA(999, "DATA", "Image data", DataImage.class);

  private final String recordType = "RTx";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data> typeClass;

  RTDefaultFieldsEnum(int id, String code, String description, Class<? extends Data> typeClass) {
    this.id = id;
    this.code = code;
    this.description = description;
    this.typeClass = typeClass;
  }
}
