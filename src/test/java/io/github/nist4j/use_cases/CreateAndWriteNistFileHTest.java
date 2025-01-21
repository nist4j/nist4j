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

import static io.github.nist4j.enums.RecordTypeEnum.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.fixtures.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class CreateAndWriteNistFileHTest {

  final WriteNistFile writeNistFile = new WriteNistFile();

  @BeforeEach
  void setUp() throws IOException {
    Files.createDirectories(Paths.get("target/test_results"));
  }

  @Test
  void writeNistFile_with_RT1() throws Exception {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD)
            .withRecord(RT1, Record1Fixtures.record1Cas3_lecture_nistviewer_Record().build())
            .build();
    File resultFile = new File("target/test_results/writeNistFile_with_RT1.nist");
    log.debug("Result file writeNistFile_with_RT1 : {}", resultFile.getAbsolutePath());

    // When
    OutputStream outputStream =
        writeNistFile.execute(nistFile, Files.newOutputStream(resultFile.toPath()));

    // Then
    assertThat(outputStream).isNotNull();
    assertThat(resultFile).exists().isNotEmpty();
  }

  @Test
  void writeNistFile_with_RT1_and_RT2() throws Exception {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD)
            .withRecord(RT1, Record1Fixtures.record1Cas3_lecture_nistviewer_Record().build())
            .withRecord(RT2, Record2Fixtures.record2Cas1_basic_Record().build())
            .build();
    File resultFile = new File("target/test_results/writeNistFile_with_RT1_and_RT2.nist");
    log.debug("Result file writeNistFile_with_RT1_and_RT2 : {}", resultFile.getAbsolutePath());

    // When
    OutputStream outputStream =
        writeNistFile.execute(nistFile, Files.newOutputStream(resultFile.toPath()));

    // Then
    assertThat(outputStream).isNotNull();
    assertThat(resultFile).exists().isNotEmpty();
  }

  @Test
  void writeNistFile_with_RT1_and_RT4() throws Exception {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD)
            .withRecord(RT1, Record1Fixtures.record1Cas3_lecture_nistviewer_Record().build())
            .withRecord(RT4, Record4Fixtures.record4Cas2_with_real_image_WSQ_Record1().build())
            .withRecord(RT4, Record4Fixtures.record4Cas3_with_real_image_WSQ_Record2().build())
            .build();
    File resultFile = new File("target/test_results/writeNistFile_with_RT1_and_RT4.nist");
    log.debug("Result file writeNistFile_with_RT1_and_RT4 : {}", resultFile.getAbsolutePath());

    // When
    OutputStream outputStream =
        writeNistFile.execute(nistFile, Files.newOutputStream(resultFile.toPath()));

    // Then
    assertThat(outputStream).isNotNull();
    assertThat(resultFile).exists().isNotEmpty();
  }

  @Test
  void writeNistFile_with_RT1_and_RT14() throws Exception {
    // Given
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD)
            .withRecord(RT1, Record1Fixtures.record1Cas3_lecture_nistviewer_Record().build())
            .withRecord(RT14, Record14Fixtures.record14Cas2_with_real_image_WSQ_Record().build())
            .withRecord(RT14, Record14Fixtures.record14Cas3_with_real_image_PNG_Record().build())
            .build();
    File resultFile = new File("target/test_results/writeNistFile_with_RT1_and_RT14.nist");
    log.debug("Result file writeNistFile_with_RT1_and_RT14 : {}", resultFile.getAbsolutePath());

    // When
    OutputStream outputStream =
        writeNistFile.execute(nistFile, Files.newOutputStream(resultFile.toPath()));

    // Then
    assertThat(outputStream).isNotNull();
    assertThat(resultFile).exists().isNotEmpty();
  }
}
