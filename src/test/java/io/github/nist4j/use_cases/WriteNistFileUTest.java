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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.fixtures.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class WriteNistFileUTest {

  private final WriteNistFile writeNistFile =
      new WriteNistFile(WriteNistFile.DEFAULT_OPTIONS_FOR_WRITE);

  @Test
  void writeANistFile_d_un_nistFile_vide_should_throw_an_exception() throws Exception {
    // Given
    NistFile nistFile = NistFileFixtures.newNistFileBuilderEnableCalculation().build();
    File outputFile = File.createTempFile("writeANistFile", ".nist");

    // When
    // Then
    assertThrows(
        ErrorEncodingNist4jException.class,
        () -> writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath())));
  }

  @Test
  void writeANistFile_d_un_nistFile_containing_RT9_and_13_should_write_the_corresponding_file()
      throws Exception {
    // Given
    NistFile nistFile = SampleType9_13Fixtures.createNistFile();
    File outputFile = File.createTempFile("writeANistFile9_13", ".nist");

    // When
    writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));
    String resultContent =
        new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.US_ASCII);

    // Then
    assertThat(resultContent).contains(SampleType9_13Fixtures.expectedRecord9());
    assertThat(resultContent).contains(SampleType9_13Fixtures.expectedPartialRecord13());
  }

  @Test
  void writeANistFile_d_un_nistFile_containing_RT10_and_17_should_write_the_corresponding_file()
      throws Exception {
    // Given
    NistFile nistFile = SampleType10_14_17Fixtures.createNistFile();
    File outputFile = File.createTempFile("writeANistFile10_17", ".nist");

    // When
    writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));
    String resultContent =
        new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.US_ASCII);

    // Then
    assertThat(resultContent).contains(SampleType10_14_17Fixtures.expectedStartRecord10());
    assertThat(resultContent).contains(SampleType10_14_17Fixtures.expectedStartRecord17());
  }

  @Test
  void writeANistFile_d_un_nistFile_containing_RT15_should_write_the_corresponding_file()
      throws Exception {
    // Given
    NistFile nistFile = SampleType15Fixtures.createNistFile();
    File outputFile = File.createTempFile("writeANistFile15", ".nist");

    // When
    writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));
    String resultContent =
        new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.US_ASCII);

    // Then
    assertThat(resultContent).contains(SampleType15Fixtures.expectedStartRecord15_1());
    assertThat(resultContent).contains(SampleType15Fixtures.expectedStartRecord15_2());
  }

  @Test
  void writeANistFile_d_un_nistFile_containing_RT16_should_write_the_corresponding_file()
      throws Exception {
    // Given
    NistFile nistFile = SampleType16Fixtures.createNistFile();
    File outputFile = File.createTempFile("writeANistFile16", ".nist");

    // When
    writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));
    String resultContent =
        new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.US_ASCII);

    // Then
    assertThat(resultContent).contains(SampleType16Fixtures.expectedStartRecord16());
  }

  @Test
  void writeANistFile_should_throw_an_exception() {
    // Given
    NistFile nistFile = mock(NistFile.class);

    when(nistFile.getRT1TransactionInformationRecord()).thenThrow(new NullPointerException());

    // When
    // Then
    assertThrows(
        ErrorEncodingNist4jException.class,
        () -> writeNistFile.execute(nistFile, new ByteArrayOutputStream()));
  }
}
