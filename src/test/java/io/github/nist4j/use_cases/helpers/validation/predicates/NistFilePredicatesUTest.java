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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT2;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFilePredicates.hasRecordsByType;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.fixtures.Record1Fixtures;
import org.junit.jupiter.api.Test;

class NistFilePredicatesUTest {

  @Test
  void hasRecordsByType_should_return_true_when_X() {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilderEnableCalculation()
            .withRecord(1, Record1Fixtures.record1Cas1_basic_Record().build())
            .build();
    // When
    // Then
    assertThat(hasRecordsByType(RT1).test(nistFile)).isTrue();
    assertThat(hasRecordsByType(RT2).test(nistFile)).isFalse();
  }
}
