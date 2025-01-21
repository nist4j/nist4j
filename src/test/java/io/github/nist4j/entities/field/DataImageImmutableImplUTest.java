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

import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import org.junit.jupiter.api.Test;

class DataImageImmutableImplUTest {

  final byte[] fakeImage = new byte[] {1, 2, 3, 4};

  @Test
  void getData() {
    Data field = new DataImageBuilder().withValue(fakeImage).build();
    assertThat(field).isNotNull();
    assertThat(field.getData()).isEqualTo(fakeImage);
  }

  @Test
  void getLength() {
    Data field = new DataImageBuilder().withValue(fakeImage).build();
    assertThat(field).isNotNull();
    assertThat(field.getLength()).isEqualTo(4);
  }

  @Test
  void toString_should_be_implemented() {
    Data field = new DataImageBuilder().withValue(fakeImage).build();
    assertThat(field).isNotNull();
    assertThat(field.toString()).isEqualTo("DataImage(value=***, length=4)");
  }

  @Test
  void toString_with_field_should_be_implemented() {
    Data field = new DataImageBuilder().withValue(fakeImage).build();
    Data fieldEmpty = new DataImageBuilder().withValue(new byte[] {}).build();
    assertThat(field.toString()).isNotNull();
    assertThat(field.toString()).isEqualTo("DataImage(value=***, length=4)");
    assertThat(fieldEmpty.toString()).isEqualTo("DataImage(value=null, length=0)");
  }

  @Test
  void equals_should_be_implemented() {
    byte[] sameFakeImage = new byte[] {1, 2, 3, 4};
    byte[] wrongFakeImage3 = new byte[] {9, 9, 3, 4};
    Data field1 = new DataImageBuilder().withValue(fakeImage).build();
    Data field2 = new DataImageBuilder().withValue(sameFakeImage).build();
    Data field3 = new DataImageBuilder().withValue(wrongFakeImage3).build();

    assertThat(field1).isEqualTo(field2);
    assertThat(field1).isNotEqualTo(field3);
  }

  @Test
  void equals_should_ignorer_les_descriptions_de_fields() {
    byte[] sameFakeImage2 = new byte[] {1, 2, 3, 4};
    Data field1 = new DataImageBuilder().withValue(fakeImage).build();
    Data field2 = new DataImageBuilder().withValue(sameFakeImage2).build();

    //noinspection EqualsWithItself
    assertThat(field1).isEqualTo(field1); // cache verification
    assertThat(field1).isEqualTo(field2);
    assertThat(field1).isNotEqualTo(null);
    assertThat(field1).isNotEqualTo(new DataTextBuilder().build());
  }

  @Test
  void hashCode_should_be_implemented() {
    byte[] sameFakeImage2 = new byte[] {1, 2, 3, 4};
    byte[] wrongFakeImage3 = new byte[] {9, 9, 3, 4};

    Data field1 = new DataImageBuilder().withValue(fakeImage).build();
    Data field2 = new DataImageBuilder().withValue(sameFakeImage2).build();
    Data field3 = new DataImageBuilder().withValue(wrongFakeImage3).build();

    assertThat(field1.hashCode()).isEqualTo(field1.hashCode());
    assertThat(field1.hashCode()).isEqualTo(field2.hashCode());
    assertThat(field1.hashCode()).isNotEqualTo(field3.hashCode());
  }

  @Test
  void deepCopy_should_copy_content() {
    // Given
    Data field1 = new DataImageBuilder().withValue(fakeImage).build();

    // When
    Data field2 = field1.deepCopy();

    // Then
    assertThat(field1).isEqualTo(field2);
    assertThat(field1.getData()).isEqualTo(field2.getData());
    assertThat(field1).isNotSameAs(field2);
    assertThat(field1).as("image should be copy to").isNotSameAs(field2.getData());
  }
}
