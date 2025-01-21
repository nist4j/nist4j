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
package io.github.nist4j.use_cases;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.fixtures.SampleType5Fixtures;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class WriteNistFileITest {

  private final WriteNistFile writeNistFile =
      new WriteNistFile(WriteNistFile.DEFAULT_OPTIONS_FOR_WRITE);

  @Test
  void writeANistFile_should_generate_a_nist_file_identical_to_reference() throws Exception {
    // Given
    String expectedFileContent = SampleType5Fixtures.referenceFileContent();
    NistFile nistFile = SampleType5Fixtures.createNistFile();
    File outputFile = File.createTempFile("writeANistFile", ".nist");

    // When
    writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));
    String resultContent =
        new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.US_ASCII);
    log.debug("writeANistFile file: {}", outputFile.getAbsolutePath());

    // Then
    String[] resultRecords = resultContent.split("");
    String[] expectedRecords = expectedFileContent.split("");

    for (int i = 0; i < 2; i++) {
      assertThat(resultRecords[i]).isEqualTo(expectedRecords[i]).as("check result record {}", i);
    }
    String[] resultFieldsRecord1 = resultRecords[0].split("");
    String[] expectedFieldsRecord1 = expectedRecords[0].split("");

    for (int i = expectedFieldsRecord1.length - 1; i > 0; i--) {
      assertThat(resultFieldsRecord1.length).isGreaterThan(i);
      assertThat(resultFieldsRecord1[i])
          .isEqualTo(expectedFieldsRecord1[i])
          .as("check result field {} of record 1", i);
    }
  }
}
