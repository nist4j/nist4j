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
package io.github.nist4j.entities.record;

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromPairs;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

@Slf4j
public class RT1TransactionInformationRecordImmutableImplUTest {

  @Test
  void getFieldText_should_return_the_expected_field() {
    // Given
    // When
    NistRecord record =
        new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
            .withField(RT1FieldsEnum.VER, newFieldText("0400"))
            .build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getFieldText(RT1FieldsEnum.VER).orElse(null)).isEqualTo("0400");
    assertThat(record.getFieldText(RT1FieldsEnum.ORI)).isEqualTo(empty());
  }

  @Test
  void addSubFields_should_add_a_string_with_separator() {
    // Given
    // When
    NistRecord record =
        new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
            .withField(RT1FieldsEnum.VER, newFieldText("0400"))
            .withField(
                RT1FieldsEnum.CNT,
                newSubfieldsFromPairs(Arrays.asList(Pair.of("A", "2"), Pair.of("B", "4"))))
            .build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getFieldText(RT1FieldsEnum.CNT)).hasValue("A\u001F2\u001EB\u001F4");
  }
}
