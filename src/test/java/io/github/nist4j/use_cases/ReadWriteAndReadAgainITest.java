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

import static io.github.nist4j.test_utils.ImportFileUtils.getFilesFromResources;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.test_utils.AssertJnbisNist;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.test_utils.ImportFileUtils;
import java.io.File;
import java.nio.file.Files;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
class ReadWriteAndReadAgainITest {

  private static final String DIRECTORY_FILES = "/references";

  private static Stream<Arguments> getPassFiles() {
    return getFilesFromResources(DIRECTORY_FILES, "^.*.an2$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getPassFiles")
  void readWriteAndReadAgain_should_be_identical(String filename, File file) throws Exception {
    // Given
    File outputFile = File.createTempFile("readWriteAndReadAgain_should_be_identical", ".nist");
    WriteNistFile writeNistFile = new WriteNistFile();
    ReadNistFile readNistFile = new ReadNistFile();

    // When
    log.debug("outputFile: {}", outputFile.toPath());
    NistFile nistFileLecture1 = readNistFile.execute(Files.newInputStream(file.toPath()));

    writeNistFile.execute(nistFileLecture1, Files.newOutputStream(outputFile.toPath()));
    NistFile nistFileLecture2 = readNistFile.execute(Files.newInputStream(outputFile.toPath()));

    // Then
    Nist nist = Jnbis.nist().decode(outputFile);
    assertThat(nist).isNotNull();
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecord1(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords2(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords3(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords4(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords5(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords6(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords7(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords8(nist);
    AssertNist.assertThatNist(nistFileLecture2).isEqualTo(nistFileLecture1);

    FileUtils.deleteQuietly(outputFile);
  }

  @Test
  void readUtf8WriteAndReadAgain_should_be_identical() throws Exception {
    // Given
    ReadNistFile readNistFile = new ReadNistFile();
    WriteNistFile writeNistFile = new WriteNistFile();
    File utf8File = ImportFileUtils.getFileFromResource("/references/type-14-amp-nqm-utf8.an2");
    assertThat(utf8File).isNotNull();

    // When
    File outputFile = File.createTempFile("readUtf8WriteAndReadAgain_should_be_identical", ".nist");

    // When
    log.debug("outputFile: {}", outputFile.toPath());
    NistFile nistFileLecture1 = readNistFile.execute(Files.newInputStream(utf8File.toPath()));
    assertThat(
            nistFileLecture1.getRT1TransactionInformationRecord().getFieldText(RT1FieldsEnum.DCS))
        .isNotEmpty()
        .hasValueSatisfying(txt -> assertThat(txt).contains(CharsetEnum.UTF_8.getLabel()))
        .hasValueSatisfying(txt -> assertThat(txt).contains("00" + CharsetEnum.UTF_8.getDcsValue()))
        .as("NistFile Has a DCS field");

    writeNistFile.execute(nistFileLecture1, Files.newOutputStream(outputFile.toPath()));
    NistFile nistFileLecture2 = readNistFile.execute(Files.newInputStream(outputFile.toPath()));

    // Then
    Nist nist = Jnbis.nist().decode(outputFile);
    assertThat(nist).isNotNull();
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecord1(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords2(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords3(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords4(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords5(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords6(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords7(nist);
    AssertJnbisNist.assertThatNist(nistFileLecture1).hasTheSameJnbisRecords8(nist);
    AssertNist.assertThatNist(nistFileLecture2).isEqualTo(nistFileLecture1);

    FileUtils.deleteQuietly(outputFile);
  }
}
