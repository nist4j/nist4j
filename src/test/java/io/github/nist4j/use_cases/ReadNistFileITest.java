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
import static io.github.nist4j.use_cases.helpers.builders.options.NistOptionsBuilderImpl.DefaultOpts.DONT_MODIFY_RT_BUT_RESPECT_ENCODING;
import static io.github.nist4j.use_cases.helpers.builders.options.NistOptionsBuilderImpl.DefaultOpts.DONT_MODIFY_RT_FORCE_UTF8;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.test_utils.AssertJnbisNist;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.test_utils.ImportFileUtils;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.stream.Stream;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ReadNistFileITest {

  private static final String DIRECTORY_FILES = "/references";

  private static Stream<Arguments> getFiles() {
    return getFilesFromResources(DIRECTORY_FILES, ".*.an2$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getFiles")
  void readANistFile_should_generate_a_nistFile_equals_to_jnbis(String filename, File file)
      throws Exception {
    // Given
    Nist nist = Jnbis.nist().decode(file);
    assertThat(nist).isNotNull();

    // When
    NistFile nistFile = new ReadNistFile().execute(Files.newInputStream(file.toPath()));

    // Then
    assertThat(nistFile).isNotNull();
    AssertJnbisNist.assertThatNist(nistFile).isEqualsTo(nist);
  }

  @Test
  void readANistFile_with_accent_and_encoding_UTF8_should_read_correctly() throws Exception {
    // Given
    File utf8File = ImportFileUtils.getFileFromResource("/references/type-14-amp-nqm-utf8.an2");
    assertThat(utf8File).isNotNull();
    InputStream inputStream = Files.newInputStream(utf8File.toPath());

    // When
    NistFile nistFile = new ReadNistFile().execute(inputStream);

    // Then
    assertThat(nistFile).isNotNull();
    assertThat(nistFile.getRT2UserDefinedDescriptionTextRecords()).isNotEmpty();
    assertThat(nistFile.getRT2UserDefinedDescriptionTextRecords().get(0).getFieldText(3))
        .hasValue("two chinese characters: 華裔");
  }

  @Test
  void readNistFile_with_or_without_encoding_should_be_identical() throws Exception {
    // Given
    File utf8File = ImportFileUtils.getFileFromResource("/references/type-14-amp-nqm-utf8.an2");
    assertThat(utf8File).isNotNull();

    // When
    NistFile nistFileByDefault =
        new ReadNistFile().execute(Files.newInputStream(utf8File.toPath()));
    NistFile nistFileByDefault2 =
        new ReadNistFile(DONT_MODIFY_RT_BUT_RESPECT_ENCODING.getOptions())
            .execute(Files.newInputStream(utf8File.toPath()));
    NistFile nistFileForcingUTF8 =
        new ReadNistFile(DONT_MODIFY_RT_FORCE_UTF8.getOptions())
            .execute(Files.newInputStream(utf8File.toPath()));

    // Then
    AssertNist.assertThatNist(nistFileByDefault).hasEncoding(CharsetEnum.UTF_8);
    AssertNist.assertThatNist(nistFileByDefault2).hasEncoding(CharsetEnum.UTF_8);
    AssertNist.assertThatNist(nistFileForcingUTF8).hasEncoding(CharsetEnum.UTF_8);

    AssertNist.assertThatNist(nistFileByDefault)
        .hasTheSameRecord1(nistFileByDefault2)
        .hasTheSameRecord2(nistFileByDefault2)
        .hasTheSameRecord14(nistFileByDefault2);

    AssertNist.assertThatNist(nistFileByDefault)
        .hasTheSameRecord1(nistFileForcingUTF8)
        .hasTheSameRecord2(nistFileForcingUTF8)
        .hasTheSameRecord14(nistFileForcingUTF8);
  }
}
