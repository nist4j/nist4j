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
package io.github.nist4j.use_cases.helpers.converters;

import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.fromItems;
import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.fromListOfList;
import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.toItems;
import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.toListAndGetByIndex;
import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.toListOfList;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

class SubFieldToStringConverterUTest {

  @Test
  void fromList_should_add_un_string_with_separator() {
    // Given
    // When
    String result = SubFieldToStringConverter.fromList(Arrays.asList("A", "2", "B", "4"));

    // Then
    assertThat(result).isEqualTo("A\u001F2\u001EB\u001F4");
  }

  @Test
  void fromList_should_lever_exception_if_number_is_odd() {
    // Given
    List<String> arrays = Arrays.asList("A", "2", "B");
    // When
    // Then
    Exception exception =
        assertThrows(
            InvalidFormatNist4jException.class, () -> SubFieldToStringConverter.fromList(arrays));
    assertThat(exception.getMessage())
        .isEqualTo("Invalid format 'The list of subfields must be pair'");
  }

  @Test
  void toList_should_convert_en_list() {
    // Given
    // When
    List<String> result = SubFieldToStringConverter.toList("A\u001F2\u001EB\u001F4");

    // Then
    assertThat(result).isEqualTo(Arrays.asList("A", "2", "B", "4"));
  }

  @Test
  void toListUsingSplitByRS_should_convert_en_list() {
    // Given
    String fieldStr = "A\u001E2\u001EB\u001E4";
    // When
    List<String> result = SubFieldToStringConverter.toListUsingSplitByRS(fieldStr);
    // Then
    assertThat(result).isEqualTo(asList("A", "2", "B", "4"));
  }

  @Test
  void fromListUsingSplitByRS_should_convert_to_string() {
    // Given
    List<String> arrays = Arrays.asList("A", "2", "B", "4");
    // When
    String result = SubFieldToStringConverter.fromListUsingSplitByRS(arrays);
    // Then
    assertThat(result).isEqualTo("A\u001E2\u001EB\u001E4");
  }

  @Test
  void toListOfPairs_should_convert_en_list_of_pairs() {
    // Given
    List<Pair<String, String>> expectedPairs = Arrays.asList(Pair.of("A", "2"), Pair.of("B", "4"));
    // When
    List<Pair<String, String>> result =
        SubFieldToStringConverter.toListOfPairs("A\u001F2\u001EB\u001F4");

    // Then
    assertThat(result).isEqualTo(expectedPairs);
  }

  @Test
  void fromListOfPairs_should_convert_from_list_of_pair() {
    // Given
    String expectedPairs = "A\u001F2\u001EB\u001F4";
    List<Pair<String, String>> listOfPairs = asList(Pair.of("A", "2"), Pair.of("B", "4"));

    // When
    String result = SubFieldToStringConverter.fromListOfPairs(listOfPairs);

    // Then
    assertThat(result).isEqualTo(expectedPairs);
  }

  @Test
  void toListOfPairs_should_handle_errors_cases() {
    // Given
    // When
    // Then
    assertThat(SubFieldToStringConverter.toListOfPairs(null)).isEmpty();
    assertThat(SubFieldToStringConverter.toListOfPairs("")).isEmpty();
    assertThat(SubFieldToStringConverter.toListOfPairs("A\u001F"))
        .isEqualTo(singletonList(Pair.of("A", "")));
    assertThat(SubFieldToStringConverter.toListOfPairs("A"))
        .isEqualTo(singletonList(Pair.of("A", "")));
  }

  @Test
  void toListOfList_should_convert_limit_cases() {
    // Given
    // When
    List<List<String>> zeroLineCase = toListOfList("");
    List<List<String>> oneLineCase = toListOfList("1\u001FA\u001FB\u001FC");
    List<List<String>> noItemInLineCase = toListOfList("1\u001FA\u001FB\u001FC\u001E");
    List<List<String>> oneLineOneElementCase = toListOfList("1");

    // Then
    assertThat(zeroLineCase).isEmpty();
    assertThat(oneLineCase).hasSize(1);
    assertThat(oneLineCase.get(0)).containsExactly("1", "A", "B", "C");
    assertThat(noItemInLineCase).hasSize(1);
    assertThat(noItemInLineCase.get(0)).containsExactly("1", "A", "B", "C");
    assertThat(oneLineOneElementCase).hasSize(1);
    assertThat(oneLineOneElementCase.get(0)).containsExactly("1");
  }

  @Test
  void toListAndGetByIndex_should() {
    // Given
    String subfieldSeparateByUS = "A\u001FB\u001FC\u001FD";
    String subfieldSeparateByUSAndRS = "1\u001FA\u001E2\u001FB";

    // When
    // Then
    assertThat(toListAndGetByIndex(null, 1)).isEmpty();
    assertThat(toListAndGetByIndex("", 1)).isEmpty();
    assertThat(toListAndGetByIndex(subfieldSeparateByUS, 1)).hasValue("B");
    assertThat(toListAndGetByIndex(subfieldSeparateByUS, 2)).hasValue("C");
    assertThat(toListAndGetByIndex(subfieldSeparateByUS, 3)).hasValue("D");

    assertThat(toListAndGetByIndex(subfieldSeparateByUSAndRS, 1)).hasValue("A");
    assertThat(toListAndGetByIndex(subfieldSeparateByUSAndRS, 2)).hasValue("2");
    assertThat(toListAndGetByIndex(subfieldSeparateByUSAndRS, 3)).hasValue("B");
  }

  @Test
  void fromListOfList_should_convert_limit_cases() {
    // Given
    // When
    String nullLineCase = fromListOfList(null);
    String zeroLineCase = fromListOfList(emptyList());
    String oneLineCase = fromListOfList(singletonList(asList("1", "A")));
    String oneLineOneElementCase = fromListOfList(singletonList(singletonList("1")));

    // Then
    assertThat(nullLineCase).isEqualTo(EMPTY);
    assertThat(zeroLineCase).isEqualTo(EMPTY);
    assertThat(oneLineCase).isEqualTo("1\u001FA");
    assertThat(oneLineOneElementCase).isEqualTo("1");
  }

  @Test
  void fromListOfList_should_convert_simple_case() {
    // Given
    String expectedSubfields = "1\u001FA\u001FB\u001FC\u001E2\u001FD\u001FE\u001FF";

    List<List<String>> items = asList(asList("1", "A", "B", "C"), asList("2", "D", "E", "F"));
    // When
    String resultSubfield = fromListOfList(items);

    // Then
    assertThat(resultSubfield).isEqualTo(expectedSubfields);
  }

  @Test
  void fromItems_from_list_should_convert_simple_case() {
    // Given
    String expectedSubfields = "1\u001F2\u001F3\u001F4";

    List<String> items = asList("1", "2", "3", "4");
    // When
    String resultSubfield = fromItems(items);

    // Then
    assertThat(resultSubfield).isEqualTo(expectedSubfields);
    assertThat(fromItems((List<String>) null)).isEmpty();
  }

  @Test
  void fromItems_from_table_should_convert_simple_case() {
    // Given
    String expectedSubfields = "1\u001F2\u001F3\u001F4";

    // When
    String resultSubfield = fromItems("1", "2", "3", "4");

    // Then
    assertThat(resultSubfield).isEqualTo(expectedSubfields);
    assertThat(fromItems((String) null)).isEmpty();
  }

  @Test
  void toItems_should_convert_simple_case() {
    // Given
    List<String> expectedSubfields = asList("1", "2", "3", "4");

    String items = "1\u001F2\u001F3\u001F4";
    // When
    List<String> resultSubfield = toItems(items);

    // Then
    assertThat(resultSubfield).isEqualTo(expectedSubfields);
    assertThat(toItems(null)).isEmpty();
  }
}
