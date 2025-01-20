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

import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import org.junit.jupiter.api.Test;

class DataTextImmutableImplUTest {

  @Test
  void getData() {
    Data field = new DataTextBuilder().withValue("test").build();
    assertThat(field).isNotNull();
    assertThat(field.getData()).isEqualTo("test");
  }

  @Test
  void getLength() {
    Data field = new DataTextBuilder().withValue("test").build();
    assertThat(field).isNotNull();
    assertThat(field.getLength()).isEqualTo(4);
  }

  @Test
  void toString_should_be_implemented() {
    Data field = new DataTextBuilder().withValue("test").build();
    assertThat(field).isNotNull();
    assertThat(field.toString()).isEqualTo("DataText(value=test)");
  }

  @Test
  void equals_should_be_implemented() {
    Data field1 = new DataTextBuilder().withValue("test 1").build();
    Data field2 = new DataTextBuilder().withValue("test" + " 1").build();
    Data field3 = new DataTextBuilder().withValue("test").build();

    assertThat(field1).isEqualTo(field2);
    assertThat(field1).isNotEqualTo(field3);
  }

  @Test
  void equals_should_ignorer_les_descriptions_de_fields() {
    Data field1 = new DataTextBuilder().withValue("test 1").build();
    Data field2 = new DataTextBuilder().withValue("test" + " 1").build();

    assertThat(field1).isEqualTo(field2);
  }

  @Test
  void hashCode_should_be_implemented() {
    Data field1 = new DataTextBuilder().withValue("test 1").build();
    Data field2 = new DataTextBuilder().withValue("test" + " 1").build();
    Data field3 = new DataTextBuilder().withValue("test").build();

    assertThat(field1.hashCode()).isEqualTo(field2.hashCode());
    assertThat(field1.hashCode()).isNotEqualTo(field3.hashCode());
  }

  @Test
  void deepCopy_should_copy_content() {
    // Given
    Data field1 = new DataTextBuilder().withValue("fake content").build();

    // When
    Data field2 = field1.deepCopy();

    // Then
    assertThat(field1).isEqualTo(field2);
    assertThat(field1.getData()).isEqualTo(field2.getData());
    assertThat(field1).isNotSameAs(field2);
    assertThat(field1.getData())
        .as("string is immutable so can be the same")
        .isSameAs(field2.getData());
  }
}
