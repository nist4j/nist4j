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
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.fixtures.Record2Fixtures;
import org.junit.jupiter.api.Test;

class RT2UserDefinedDescriptionTextNistRecordBuilderImplUTest {
  final NistRecordBuilder builder =
      new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD);

  @Test
  void build_should_calculate_th_length_including_him_self() {
    // Given
    // recreate the build based on a builder that calculate
    NistRecordBuilder builderWithCalculate =
        builder.from(Record2Fixtures.record2Cas1_basic_Record().build());
    builderWithCalculate.getFields().remove(1); // remove LEN if exists

    // When
    NistRecord nistRecord = builderWithCalculate.build();

    // Then
    assertThat(nistRecord.getFieldText(1)).isNotEmpty();
    String LEN = nistRecord.getFieldText(1).orElse(null);
    assertThat(LEN).isEqualTo("66");
  }
}
