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
package io.github.nist4j.entities.record.impl;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.fixtures.OptionsFixtures;
import io.github.nist4j.use_cases.helpers.builders.records.RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl;
import org.junit.jupiter.api.Test;

class RT5LowResolutionBinaryFingerprintRecordImmutableImplTest {

  @Test
  void getIFieldTypeEnumValues() {
    // Given
    NistRecordBuilder builder =
        new RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl(
            OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD);
    // When
    NistRecord nistRecord = new RT5LowResolutionBinaryFingerprintRecordImmutableImpl(builder);

    // Then
    assertThat(nistRecord).isNotNull();
    assertThat(nistRecord.getIFieldTypeEnumValues().size()).isEqualTo(9);
  }
}
