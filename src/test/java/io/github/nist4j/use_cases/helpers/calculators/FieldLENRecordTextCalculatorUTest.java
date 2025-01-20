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

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import org.junit.jupiter.api.Test;

/**
 * This calculator is only ok for record R1, R2 and >R7 (based on text serialisation)
 */
class FieldLENRecordTextCalculatorUTest {
  final FieldLENRecordTextCalculator fieldLENRecordTextCalculator =
      new FieldLENRecordTextCalculator(OPTIONS_CALCULATE_ON_BUILD);

  @Test
  void calculateLength_d_un_R1_should_calculate_the_length_including_him_self() {
    // Given
    NistRecordBuilder nistRecordBuilder1 =
        newRecordBuilderEnableCalculation(1).withField(2, newFieldText("123"));

    NistRecordBuilder nistRecordBuilder2 =
        newRecordBuilderEnableCalculation(1)
            .withField(1, newFieldText("123456789234567823456789345678"))
            .withField(2, newFieldText("123"));

    // When
    int length1 = fieldLENRecordTextCalculator.calculateLength(nistRecordBuilder1);
    int length2 = fieldLENRecordTextCalculator.calculateLength(nistRecordBuilder2);

    // Then
    assertThat(length1).isEqualTo(19);
    assertThat(length1).isEqualTo(length2);
  }

  @Test
  void calculateLength_d_un_R14_should_calculate_the_length_including_him_self() {
    // Given
    NistRecordBuilder nistRecordBuilder1 =
        newRecordBuilderEnableCalculation(14)
            .withField(2, newFieldText("123"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(new byte[] {3, 3, 3, 3, 3}));

    NistRecordBuilder nistRecordBuilder2 =
        newRecordBuilderEnableCalculation(14)
            .withField(1, newFieldText("123456789234567823456789345678"))
            .withField(2, newFieldText("123"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(new byte[] {3, 3, 3, 3, 3}));

    // When
    int length1 = fieldLENRecordTextCalculator.calculateLength(nistRecordBuilder1);
    int length2 = fieldLENRecordTextCalculator.calculateLength(nistRecordBuilder2);

    // Then
    assertThat(length1).isEqualTo(34);
    assertThat(length1).isEqualTo(length2);
  }
}
