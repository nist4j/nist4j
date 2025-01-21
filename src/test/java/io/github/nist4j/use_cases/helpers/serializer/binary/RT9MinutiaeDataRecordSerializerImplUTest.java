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

import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

class RT9MinutiaeDataRecordSerializerImplUTest {

  final RT9MinutiaeDataRecordSerializerImpl serializer =
      new RT9MinutiaeDataRecordSerializerImpl(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void calculateRecordLength_should_return_the_length_including_him_self() {
    // Given
    NistRecord record =
        newRecordBuilderEnableCalculation(9)
            .withField(2, newFieldText("123"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(new byte[] {3, 3, 3, 3, 3}))
            .build();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // When
    int length = record.getFieldAsInt(1).orElse(-1);
    serializer.write(outputStream, record);

    // Then
    assertThat(length).isEqualTo(outputStream.size());
    assertThat(outputStream.toByteArray()[length - 1]).isEqualTo((byte) NistDecoderHelper.SEP_FS);
  }

  @Test
  void calculateRecordLength_sans_image_should_return_the_length_including_him_self() {
    // Given
    NistRecord record =
        newRecordBuilderEnableCalculation(9)
            .withField(2, newFieldText("123"))
            .withField(5, newFieldText("FIELD 5"))
            .build();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // When
    int length = record.getFieldAsInt(1).orElse(-1);
    serializer.write(outputStream, record);

    // Then
    assertThat(length).isEqualTo(outputStream.size());
    assertThat(outputStream.toByteArray()[length - 1]).isEqualTo((byte) NistDecoderHelper.SEP_FS);
  }
}
