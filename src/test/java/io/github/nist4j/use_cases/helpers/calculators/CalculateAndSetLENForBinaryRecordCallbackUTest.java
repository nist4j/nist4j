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

import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.fixtures.OptionsFixtures;
import io.github.nist4j.use_cases.helpers.builders.records.RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl;
import org.junit.jupiter.api.Test;

class CalculateAndSetLENForBinaryRecordCallbackUTest {

  public static final byte[] FAKE_IMAGE = {1, 2, 3, 4};

  @Test
  void execute_should_modifier_LEN_if_active() {
    // Given
    NistOptions nistOptions = OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;

    NistRecordBuilder recordBuilder =
        new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(nistOptions)
            .withField(2, newFieldText("field 2"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(FAKE_IMAGE));
    // When
    new CalculateAndSetLENForBinaryRecordCallback(nistOptions, 10, GenericImageTypeEnum.DATA)
        .execute(recordBuilder);

    // Then
    assertThat(recordBuilder.getFields().get(1)).isNotNull();
    assertThat(recordBuilder.getFields().get(1).getData())
        .isEqualTo(String.valueOf(10 + FAKE_IMAGE.length));
  }

  @Test
  void execute_ne_should_pas_modifier_LEN_if_inactive() {
    // Given
    NistOptions nistOptions = OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;

    NistRecordBuilder recordBuilder =
        new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(nistOptions)
            .withField(2, newFieldText("field 2"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(FAKE_IMAGE));
    // When
    new CalculateAndSetLENForBinaryRecordCallback(nistOptions, 10, GenericImageTypeEnum.DATA)
        .execute(recordBuilder);

    // Then
    assertThat(recordBuilder.getFields().get(1)).isNull();
  }
}
