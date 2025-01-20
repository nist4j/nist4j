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
package io.github.nist4j.use_cases.helpers.builders.records;

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import org.junit.jupiter.api.Test;

class RT7UserDefinedImageNistRecordBuilderImplUTest {
  @Test
  void build_should_calculate_the_length_including_him_self() {
    // Given
    // When
    NistRecord nistRecord =
        new RT7UserDefinedImageNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(2, newFieldText("123"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(new byte[] {3, 3, 3, 3, 3}))
            .build();

    // Then
    assertThat(nistRecord.getFieldText(1)).isNotEmpty();
    int LEN = nistRecord.getFieldAsInt(1).orElse(-1);
    assertThat(LEN).isEqualTo(33 + 5);
  }
}
