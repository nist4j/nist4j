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
package io.github.nist4j.use_cases.helpers.validation.predicates;

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.getFieldStringOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.isFieldAbsent;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.isFieldEquals;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.isFieldInCollection;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class NistRecordPredicatesUTest {

  @Test
  void isFieldAbsent_should_return_expected_value() {
    // Given
    NistRecord nistRecord =
        new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(RT1FieldsEnum.DAI, newFieldText("test val"))
            .build();
    // When
    // Then
    assertThat(isFieldAbsent(RT1FieldsEnum.ORI).test(nistRecord)).isTrue();
    assertThat(isFieldAbsent(RT1FieldsEnum.DAI).test(nistRecord)).isFalse();
  }

  @Test
  void isFieldInCollection_should_return_expected_value() {
    // Given
    NistRecord nistRecord =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(RT1FieldsEnum.DAI, newFieldText("val"))
            .build();
    List<String> expectedList = Arrays.asList("bad", "val");
    List<String> unexpectedList = Arrays.asList("bad", "very bad");

    // When
    // Then
    assertThat(isFieldInCollection(RT1FieldsEnum.DAI, expectedList).test(nistRecord)).isTrue();
    assertThat(isFieldInCollection(RT1FieldsEnum.DAI, unexpectedList).test(nistRecord)).isFalse();
  }

  @Test
  void isFieldEquals_should_return_expected_value() {
    // Given
    NistRecord nistRecord =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(RT1FieldsEnum.DAI, newFieldText("val"))
            .build();
    // When
    // Then
    assertThat(isFieldEquals(RT1FieldsEnum.DAI, "val").test(nistRecord)).isTrue();
    assertThat(isFieldEquals(RT1FieldsEnum.DAI, "bad").test(nistRecord)).isFalse();
  }

  @Test
  void getFieldStringOrNull_should_return_expected_value() {
    // Given
    NistRecord nistRecord =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(RT1FieldsEnum.DAI, newFieldText("val"))
            .build();
    // When
    // Then
    assertThat(getFieldStringOrNull(RT1FieldsEnum.DAI, nistRecord)).isEqualTo("val");
    assertThat(getFieldStringOrNull(RT1FieldsEnum.ORI, nistRecord)).isNull();
  }
}
