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
import io.github.nist4j.use_cases.ReadNistFile;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class RT2UserDefinedDescriptionTextRecordSerializerImplUTest {

  final RT2UserDefinedDescriptionTextRecordSerializerImpl serializer =
      new RT2UserDefinedDescriptionTextRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void write_should_write_conform_to_spec() throws Exception {
    // Given
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    NistRecord record = Record2Fixtures.record2Cas1_basic_Record().build();
    byte[] expectedBinary = Record2Fixtures.record2Cas1_basic_Binary();

    // When
    serializer.write(outputStream, record);

    // Then
    assertThat(outputStream.toByteArray()).isNotEmpty();
    assertThat(outputStream.toByteArray())
        .isEqualTo(expectedBinary)
        .as("checks that the separator is place:");
  }
}
