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
package io.github.nist4j.use_cases.helpers.serializer.binary;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.fixtures.Record2Fixtures;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import org.junit.jupiter.api.Test;

class RT2UserDefinedDescriptionTextRecordSerializerImplITest {

  final RT2UserDefinedDescriptionTextRecordSerializerImpl serializer =
      new RT2UserDefinedDescriptionTextRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_read_un_cas_reel() throws Exception {
    // Given
    byte[] inputRecord1 = Record2Fixtures.record2Cas1_basic_Binary();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(inputRecord1);
    NistRecord expectedRecord = Record2Fixtures.record2Cas1_basic_Record().build();

    // When
    NistRecord record = serializer.read(token);

    // Then
    assertThat(record).isNotNull();
    AssertNist.assertRecordEquals(record, expectedRecord);
  }
}
