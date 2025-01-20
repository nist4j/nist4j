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
import static io.github.nist4j.enums.records.RT2FieldsEnum.IDC;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.records.DefaultNistTextRecordBuilderImpl.newRecordBuilder;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.use_cases.helpers.builders.records.RT14VariableResolutionFingerprintNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class FieldCNTCalculatorUTest {

  final FieldCNTCalculator FieldCNTCalculator = new FieldCNTCalculator(OPTIONS_CALCULATE_ON_BUILD);

  @Test
  void fromNistFile_should_calculate_the_field_CTN() {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilderEnableCalculation()
            .withRecord(
                RT1,
                new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("1"))
                    .build())
            .withRecord(
                RT2,
                new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("0"))
                    .build())
            .withRecord(
                RT14,
                new RT14VariableResolutionFingerprintNistRecordBuilderImpl(
                        OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("3"))
                    .build())
            .withRecord(
                RT14,
                new RT14VariableResolutionFingerprintNistRecordBuilderImpl(
                        OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("4"))
                    .build())
            .withRecord(
                RT14,
                new RT14VariableResolutionFingerprintNistRecordBuilderImpl(
                        OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("5"))
                    .build())
            .build();

    // When
    List<String> tocList = FieldCNTCalculator.fromNistFile(nistFile);

    // Then
    assertThat(tocList)
        .isNotEmpty()
        .isEqualTo(Arrays.asList("1", "4", "2", "0", "14", "3", "14", "4", "14", "5"));
    // "1\u001F4\u001E2\u001F0\u001E14\u001F3\u001E14\u001F4\u001E14\u001F5");
  }

  @Test
  void fromNistFile_should_calculate_the_field_CTN_with_mapDefault() {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilderEnableCalculation()
            .withRecord(
                RT1,
                new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("1"))
                    .build())
            .withRecord(
                RT2,
                new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
                    .withField(IDC, newFieldText("0"))
                    .build())
            .withRecord(
                RT16,
                newRecordBuilder(OPTIONS_CALCULATE_ON_BUILD, 16, IDC, newFieldText("3")).build())
            .withRecord(
                RT16,
                newRecordBuilder(OPTIONS_CALCULATE_ON_BUILD, 16, IDC, newFieldText("4")).build())
            .withRecord(
                RT16,
                newRecordBuilder(OPTIONS_CALCULATE_ON_BUILD, 16, IDC, newFieldText("5")).build())
            .withRecord(
                RT16,
                newRecordBuilder(OPTIONS_CALCULATE_ON_BUILD, 16, IDC, newFieldText("6")).build())
            .build();

    // When
    List<String> tocList = FieldCNTCalculator.fromNistFile(nistFile);

    // Then
    assertThat(tocList)
        .isNotEmpty()
        .isEqualTo(Arrays.asList("1", "5", "2", "0", "16", "3", "16", "4", "16", "5", "16", "6"));
  }
}
