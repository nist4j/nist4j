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

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.fixtures.FixturesNistReferenceFiles;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RT4HighResolutionGrayscaleFingerprintRecordSerializerImplITest {

  final RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl serializer =
      new RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);
  final ReadNistFile readNistFile = new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void write_should_be_the_same_after_reading() throws Exception {
    // Given
    File nistType4File = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_4);
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(nistType4File.toPath()));

    NistRecord originalRecord =
        originalNistFile.getRT4HighResolutionGrayscaleFingerprintRecords().get(0);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When I write
    serializer.write(outputStream, originalRecord);

    // Then
    assertThat(outputStream.toByteArray()).isNotNull();
    log.debug("originalRecord: \n{}", originalRecord.toString());

    // And When I read
    byte[] resultBytes = outputStream.toByteArray();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(resultBytes);
    token.crt = 4;
    NistRecord resultRecord = serializer.read(token);

    // Then
    log.debug("resultRecord \n{}", resultRecord.toString());
    assertThat(resultRecord).isNotNull();

    AssertNist.assertRecordEquals(resultRecord, originalRecord);
  }
}
