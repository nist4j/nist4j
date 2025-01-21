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
package io.github.nist4j.entities.field;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

public class DataTypeEnumUTest {

  @Getter
  @AllArgsConstructor
  public enum FieldTypeEnumA implements IFieldTypeEnum {
    LEN(1, "LEN", "description LEN", DataTextImmutableImpl.class),
    F2(2, "F2", "description F2", DataImageImmutableImpl.class);

    private final String recordType = "RT0";
    private final int id;
    private final String code;
    private final String description;
    private final Class<? extends Data> typeClass;
  }

  @Test
  void testEnumDefinition() {
    assertThat(FieldTypeEnumA.LEN).isNotNull();
    assertThat(FieldTypeEnumA.LEN.getId()).isEqualTo(1);
    assertThat(FieldTypeEnumA.LEN.getCode()).isEqualTo("LEN");
    assertThat(FieldTypeEnumA.LEN.getDescription()).isEqualTo("description LEN");
    assertThat(FieldTypeEnumA.LEN.getTypeClass()).isSameAs(DataTextImmutableImpl.class);

    assertThat(FieldTypeEnumA.F2).isNotNull();
    assertThat(FieldTypeEnumA.F2.getId()).isEqualTo(2);
    assertThat(FieldTypeEnumA.F2.getCode()).isEqualTo("F2");
    assertThat(FieldTypeEnumA.F2.getDescription()).isEqualTo("description F2");
    assertThat(FieldTypeEnumA.F2.getTypeClass()).isSameAs(DataImageImmutableImpl.class);
  }
}
