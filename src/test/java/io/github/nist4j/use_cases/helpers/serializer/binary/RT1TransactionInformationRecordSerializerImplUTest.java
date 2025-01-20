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
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.fixtures.Record1Fixtures;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class RT1TransactionInformationRecordSerializerImplUTest {

  final RT1TransactionInformationRecordSerializerImpl serializer =
      new RT1TransactionInformationRecordSerializerImpl(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_read_un_cas_reel() throws Exception {
    // Given
    byte[] inputRecord1 = Record1Fixtures.record1Cas3_full_Binary();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(inputRecord1);
    NistRecord expectedRecord = Record1Fixtures.record1Cas3_full_Record().build();

    // When
    NistRecord record = serializer.read(token);

    // Then
    assertThat(record).isNotNull();
    AssertNist.assertRecordEquals(record, expectedRecord);
  }

  @Test
  void write_should_write_conform_to_spec() throws Exception {
    // Given
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    NistRecord record = Record1Fixtures.record1Cas3_full_Record().build();
    byte[] expectedBinary = Record1Fixtures.record1Cas3_full_Binary();

    // When
    serializer.write(outputStream, record);

    // Then
    byte[] resultByteArray = outputStream.toByteArray();
    assertThat(resultByteArray).isNotEmpty();
    assertThat(new String(resultByteArray))
        .isEqualTo(new String(expectedBinary))
        .as("checks that the separator is place:");
    assertThat(resultByteArray).isEqualTo(expectedBinary).as("checks that the separator is place:");
  }

  @Test
  void read_should_read_un_cas_basic() throws Exception {
    // Given
    byte[] inputRecord1 = Record1Fixtures.record1Cas3_full_Binary();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(inputRecord1);
    NistRecord expectedRecord = Record1Fixtures.record1Cas3_full_Record().build();

    // When
    NistRecord record = serializer.read(token);

    // Then
    assertThat(record).isNotNull();
    AssertNist.assertRecordEquals(record, expectedRecord);
  }

  @Test
  void read_should_read_the_field_DCS_pour_changer_encoding_UTF8() throws Exception {
    // Given
    byte[] inputRecord1 = Record1Fixtures.record1Cas4_encoding_UTF8_Binary();
    NistRecord expectedRecord = Record1Fixtures.record1Cas4_encoding_UTF8_Record().build();

    // When
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(inputRecord1);
    NistRecord record = serializer.read(token);

    // Then
    assertThat(record).isNotNull();
    AssertNist.assertRecordEquals(record, expectedRecord);
    assertThat(token.charsetDecoder.getClass())
        .isEqualTo(CharsetEnum.UTF_8.getCharset().newDecoder().getClass());
  }

  @Test
  void read_should_read_the_field_DCS_pour_changer_encoding_UTF16() throws Exception {
    // Given
    byte[] inputRecord1 = Record1Fixtures.record1Cas4_encoding_UTF16_Binary();
    NistRecord expectedRecord = Record1Fixtures.record1Cas4_encoding_UTF16_Record().build();

    // When
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(inputRecord1);
    NistRecord record = serializer.read(token);

    // Then
    assertThat(record).isNotNull();
    AssertNist.assertRecordEquals(record, expectedRecord);
    assertThat(token.charsetDecoder.getClass())
        .isEqualTo(CharsetEnum.UTF_16.getCharset().newDecoder().getClass());
  }
}
