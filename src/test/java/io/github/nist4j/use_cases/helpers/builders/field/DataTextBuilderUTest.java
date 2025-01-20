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

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

class DataTextBuilderUTest {

  @Test
  void from_should_cloner_les_donnees_dans_builder() {
    // Given
    DataText data = (DataText) new DataTextBuilder().withValue("test str").build();
    // When
    DataText dataCopyFromClone = (DataText) new DataTextBuilder().from(data).build();
    // Then
    assertThat(data.getData()).isEqualTo(dataCopyFromClone.getData());
    assertThat(data).isEqualTo(dataCopyFromClone);
  }

  @Test
  void equals_should_comparer_que_l_image() {
    // Given
    DataText data1 = (DataText) new DataTextBuilder().withValue("test str").build();
    DataText data2_same_as_1 =
        (DataText) new DataTextBuilder().withValue(format("test %s", "str")).build();
    DataText data3_not_same_as_1 = (DataText) new DataTextBuilder().withValue("test str 2").build();

    // When
    // Then
    assertThat(data1).isEqualTo(data2_same_as_1);
    assertThat(data1).isNotEqualTo(data3_not_same_as_1);
  }

  @Test
  void withValues_should_build_with_list_separated_with_US_and_RS() {
    // Given
    List<String> list = asList("A", "1", "C", "2");
    // When
    DataText data = (DataText) new DataTextBuilder().withValues(list).build();
    // Then
    assertThat(data.getData()).isEqualTo("A\u001F1\u001EC\u001F2");
  }

  @Test
  void withValues_should_throw_exception_if_odd_values() {
    // Given
    // When
    // Then
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> new DataTextBuilder().withValues(asList("A", "1", "C")));
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> new DataTextBuilder().withValues(singletonList("A")));
  }

  @Test
  void withListOfList_should_build_data_text_with_list_of_list() {
    // Given
    List<List<String>> listOfList = asList(asList("A", "1"), asList("B", "1", "2"));
    // When
    DataText data = (DataText) new DataTextBuilder().withListOfList(listOfList).build();
    // Then
    assertThat(data.getData()).isEqualTo("A\u001F1\u001EB\u001F1\u001F2");
  }

  @Test
  void withPairs_should_build_data_text_with_pairs() {
    // Given
    List<Pair<String, String>> pairs = asList(Pair.of("A", "1"), Pair.of("B", "2"));
    // When
    DataText data = (DataText) new DataTextBuilder().withPairs(pairs).build();
    // Then
    assertThat(data.getData()).isEqualTo("A\u001F1\u001EB\u001F2");
  }

  @Test
  void withItems_should_build_data_text_with_items() {
    // Given
    List<String> list = asList("A", "1", "C", "2");
    // When
    DataText data = (DataText) new DataTextBuilder().withItems(list).build();
    // Then
    assertThat(data.getData()).isEqualTo("A\u001F1\u001FC\u001F2");
  }

  @Test
  void withListUsingSplitByRS() {
    // Given
    List<String> list = asList("A", "1", "C", "2");
    // When
    DataText data = (DataText) new DataTextBuilder().withListUsingSplitByRS(list).build();
    // Then
    assertThat(data.getData()).isEqualTo("A\u001E1\u001EC\u001E2");
  }
}
