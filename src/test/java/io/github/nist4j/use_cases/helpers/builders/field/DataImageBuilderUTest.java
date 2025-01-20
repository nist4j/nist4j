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
package io.github.nist4j.use_cases.helpers.builders.field;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.field.DataImage;
import org.junit.jupiter.api.Test;

class DataImageBuilderUTest {

  @Test
  void from_should_cloner_les_donnees_dans_builder() {
    // Given
    DataImage data =
        (DataImage) new DataImageBuilder().withValue(new byte[] {1, 2, 3, 0, 9}).build();
    // When
    DataImage dataCopyFromClone = (DataImage) new DataImageBuilder().from(data).build();
    // Then
    assertThat(data.getData()).isEqualTo(dataCopyFromClone.getData());
    assertThat(data).isEqualTo(dataCopyFromClone);
  }

  @Test
  void equals_should_comparer_que_l_image() {
    // Given
    DataImage data1 =
        (DataImage) new DataImageBuilder().withValue(new byte[] {1, 2, 3, 0, 9}).build();
    DataImage data2_same_as_1 =
        (DataImage) new DataImageBuilder().withValue(new byte[] {1, 2, 3, 0, 9}).build();
    DataImage data3_not_same_as_1 =
        (DataImage) new DataImageBuilder().withValue(new byte[] {1, 1, 1, 1, 1}).build();

    // When
    // Then
    assertThat(data1).isEqualTo(data2_same_as_1);
    assertThat(data1).isNotEqualTo(data3_not_same_as_1);
  }
}
