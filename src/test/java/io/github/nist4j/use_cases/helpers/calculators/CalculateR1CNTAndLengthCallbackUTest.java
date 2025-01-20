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
package io.github.nist4j.use_cases.helpers.calculators;

import static io.github.nist4j.enums.RecordTypeEnum.*;
import static io.github.nist4j.enums.records.RT1FieldsEnum.CNT;
import static io.github.nist4j.fixtures.NistFileFixtures.newNistFileBuilder;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.fixtures.OptionsFixtures;
import io.github.nist4j.use_cases.helpers.builders.records.RT14VariableResolutionFingerprintNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import org.junit.jupiter.api.Test;

class CalculateR1CNTAndLengthCallbackUTest {

  private static final Data NEW_FIELD_TEXT = newFieldText("field 2");

  @Test
  void execute_should_modifier_CNT_and_LEN_if_active() {
    // Given
    NistOptions nistOptions = OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;

    NistRecordBuilder r1Builder =
        new RT1TransactionInformationNistRecordBuilderImpl(nistOptions)
            .withField(2, NEW_FIELD_TEXT);

    NistRecordBuilder r2Builder =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(nistOptions)
            .withField(2, NEW_FIELD_TEXT);

    NistRecordBuilder r14Builder =
        new RT14VariableResolutionFingerprintNistRecordBuilderImpl(nistOptions)
            .withField(2, NEW_FIELD_TEXT);

    NistFileBuilder fileBuilder =
        newNistFileBuilder(nistOptions)
            .withRecord(RT1, r1Builder.build())
            .withRecord(RT2, r2Builder.build())
            .withRecord(RT14, r14Builder.build());

    // When
    new CalculateR1CNTAndLengthCallback(nistOptions).execute(fileBuilder);

    // Then
    assertThat(fileBuilder.getMapOfAllRecords().get(RT1)).isNotEmpty();
    NistRecord r1 = fileBuilder.getMapOfAllRecords().get(RT1).get(0);
    assertThat(fileBuilder.getMapOfAllRecords().get(RT2)).isNotEmpty();
    NistRecord r2 = fileBuilder.getMapOfAllRecords().get(RT2).get(0);
    assertThat(fileBuilder.getMapOfAllRecords().get(RT14)).isNotEmpty();
    NistRecord r14 = fileBuilder.getMapOfAllRecords().get(RT14).get(0);

    assertThat(r1.getFieldText(1)).isNotEmpty();
    assertThat(r1.getFieldText(CNT)).isNotEmpty();
    assertThat(r2.getFieldText(1)).isNotEmpty();
    assertThat(r14.getFieldText(1)).isNotEmpty();
  }

  @Test
  void execute_ne_should_pas_modifier_CNT_if_inactive() {
    // Given
    NistOptions nistOptions = OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;

    NistRecordBuilder r1Builder =
        new RT1TransactionInformationNistRecordBuilderImpl(nistOptions)
            .withField(2, NEW_FIELD_TEXT);

    NistRecordBuilder r2Builder =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(nistOptions)
            .withField(2, NEW_FIELD_TEXT);

    NistRecordBuilder r14Builder =
        new RT14VariableResolutionFingerprintNistRecordBuilderImpl(nistOptions)
            .withField(2, NEW_FIELD_TEXT);

    NistFileBuilder fileBuilder =
        newNistFileBuilder(nistOptions)
            .withRecord(RT1, r1Builder.build())
            .withRecord(RT2, r2Builder.build())
            .withRecord(RT14, r14Builder.build());

    // When
    new CalculateR1CNTAndLengthCallback(nistOptions).execute(fileBuilder);

    // Then
    assertThat(fileBuilder.getMapOfAllRecords().get(RT1)).isNotEmpty();
    NistRecord r1 = fileBuilder.getMapOfAllRecords().get(RT1).get(0);

    assertThat(fileBuilder.getMapOfAllRecords().get(RT2)).isNotEmpty();
    NistRecord r2 = fileBuilder.getMapOfAllRecords().get(RT2).get(0);

    assertThat(fileBuilder.getMapOfAllRecords().get(RT14)).isNotEmpty();
    NistRecord r14 = fileBuilder.getMapOfAllRecords().get(RT14).get(0);

    assertThat(r1.getFieldText(1)).isEmpty();
    assertThat(r1.getFieldText(CNT)).isEmpty();
    assertThat(r2.getFieldText(1)).isEmpty();
    assertThat(r14.getFieldText(1)).isEmpty();
  }
}
