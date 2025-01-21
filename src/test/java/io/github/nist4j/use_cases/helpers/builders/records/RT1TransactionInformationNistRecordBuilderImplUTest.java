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
import io.github.nist4j.fixtures.Record1Fixtures;
import org.junit.jupiter.api.Test;

class RT1TransactionInformationNistRecordBuilderImplUTest {

  final NistRecordBuilder builder =
      new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD);

  @Test
  void build_should_calculate_the_length_including_him_self() {
    // Given
    NistRecordBuilder builderWithCalculate =
        builder.from(Record1Fixtures.record1Cas1_basic_Record().build());
    builderWithCalculate.getFields().remove(1); // remove LEN if exists

    // When
    NistRecord nistRecord = builderWithCalculate.build();

    // Then
    assertThat(nistRecord.getFieldText(1)).isNotEmpty();
    int LEN = nistRecord.getFieldAsInt(1).orElse(-1);
    assertThat(LEN).isEqualTo(180);
  }
}
