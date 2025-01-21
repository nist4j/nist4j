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

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class RT9FieldsHelperUTest {

  @Test
  void generateItemForLegacyMRC_should_generate_according_to_the_spec() {
    // Given
    Integer id = 1;
    Integer x = 2195;
    Integer y = 2030;
    Integer theta = 101;
    Integer quality = 0;
    String type = "D";
    List<String> ridges = asList("033,04", "255,15");

    // When
    List<String> result =
        RT9FieldsHelper.generateItemForLegacyMRC(id, x, y, theta, quality, type, ridges);

    // Then
    assertThat(result).containsExactly("001", "21952030101", "00", "D", "033,04", "255,15");
  }

  @Test
  void formatLegacyMRCField_should_convert_to_string() {
    // Given
    List<List<String>> itemsOfItems =
        asList(
            asList("001", "21952030101", "00", "D", "033,04", "255,15"),
            asList("002", "21492030096", "00", "D", "045,02"));
    // When
    String legacyMCR = RT9FieldsHelper.formatLegacyMRCField(itemsOfItems);

    // Then
    assertThat(legacyMCR)
        .isEqualTo(
            "001\u001F21952030101\u001F00\u001FD\u001F033,04\u001F255,15\u001E002\u001F21492030096\u001F00\u001FD\u001F045,02");
  }

  @Test
  void parseLegacyMRCField_should_convert_to_string() {
    // Given
    String legacyMRC =
        "001\u001F21952030101\u001F00\u001FD\u001F033,04\u001F255,15\u001E002\u001F21492030096\u001F00\u001FD\u001F045,02";

    List<List<String>> expectedItems =
        asList(
            asList("001", "21952030101", "00", "D", "033,04", "255,15"),
            asList("002", "21492030096", "00", "D", "045,02"));
    // When
    List<List<String>> legacyMCRListOfList = RT9FieldsHelper.parseLegacyMRCField(legacyMRC);

    // Then
    assertThat(legacyMCRListOfList.get(0)).containsAll(expectedItems.get(0));
  }
}
