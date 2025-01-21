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
import io.github.nist4j.fixtures.Record14Fixtures;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.io.ByteArrayOutputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RT14VariableResolutionFingerprintRecordSerializerImplUTest {

  final RT14VariableResolutionFingerprintRecordSerializerImpl serializer =
      new RT14VariableResolutionFingerprintRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void write_should_write_conform_to_spec() throws Exception {
    // Given
    NistRecord record = Record14Fixtures.record14Cas2_EJI_Record().build();
    byte[] expected = Record14Fixtures.record14Cas2_EJI_Binary();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // When
    serializer.write(outputStream, record);

    // Then
    assertThat(outputStream.toByteArray())
        .isEqualTo(expected)
        .as("checks that the separator is place:");
  }

  @Test
  void read_should_read_conform_to_spec() {
    // Given
    NistRecord expectedRecord = Record14Fixtures.record14Cas2_EJI_Record().build();
    NistDecoderHelper.Token token = writeToToken(expectedRecord);

    // When
    NistRecord resultRecord = serializer.read(token);

    // Then
    assertThat(resultRecord).isNotNull();
    AssertNist.assertRecordEquals(resultRecord, expectedRecord);
  }

  private NistDecoderHelper.Token writeToToken(NistRecord expectedRecord) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    serializer.write(outputStream, expectedRecord);
    byte[] buffer = outputStream.toByteArray();

    NistDecoderHelper.Token token = new NistDecoderHelper.Token(buffer);
    token.crt = 14;
    return token;
  }
}
