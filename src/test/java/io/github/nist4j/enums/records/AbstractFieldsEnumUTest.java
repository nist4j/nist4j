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

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

abstract class AbstractFieldsEnumUTest {

  abstract IFieldTypeEnum[] fieldTypeValues();

  // abstract List<IFieldTypeEnum> fieldTypeValues();
  abstract String expectedRT();

  abstract int expectedNumberOfFields();

  abstract List<String> exceptionsInNames();

  Stream<IFieldTypeEnum> getFieldTypeStream() {
    return Arrays.stream(fieldTypeValues());
  }

  @Test
  void names_should_be_equals_to_code() {
    getFieldTypeStream().forEach((f) -> assertThat(f.getRecordType()).isEqualTo(expectedRT()));
  }

  @Test
  void numberOfFields_should_be_has_expected() {
    assertThat(fieldTypeValues()).hasSize(expectedNumberOfFields());
  }

  @Test
  void names_and_code_must_be_the_same() {
    getFieldTypeStream().forEach(this::compareNameAndCode);
  }

  private void compareNameAndCode(IFieldTypeEnum field) {
    if (!exceptionsInNames().contains(field.name())) {
      assertThat(field.getCode()).isEqualTo(field.name().replace("_LEGACY", ""));
    }
  }
}
