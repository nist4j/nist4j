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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT2;
import static io.github.nist4j.enums.records.RT1FieldsEnum.DCS;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_UTF8_CALCULATE_ON_BUILD;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.fixtures.Record1Fixtures;
import io.github.nist4j.fixtures.Record2Fixtures;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CreateAndWriteAndReadNistFileITest {

  private static final NistOptions nistOptions =
      NistOptionsImpl.builder()
          .isCalculateCNTOnBuild(true)
          .isCalculateLENOnBuild(true)
          .charset(CharsetEnum.UTF_8.getCharset())
          .build();

  @Test
  void createAndWriteAndReadNistFile_with_RT1_and_RT4_encode_in_utf8() throws Exception {
    // Given
    NistRecord r1 =
        new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .from(Record1Fixtures.record1Cas4_encoding_UTF8_Record().build())
            .build();
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(OPTIONS_UTF8_CALCULATE_ON_BUILD)
            .withRecord(RT1, r1)
            .withRecord(RT2, Record2Fixtures.record2Cas3_with_accents_utf8().build())
            .build();
    Files.createDirectories(Paths.get("target/test_results"));
    File resultFile =
        new File(
            "target/test_results/createAndWriteAndReadNistFile_with_RT1_and_RT4_encode_in_utf8.nist");
    log.debug("Result file : {}", resultFile.getAbsolutePath());

    // When I write it
    try (OutputStream outputStream = Files.newOutputStream(resultFile.toPath())) {
      new WriteNistFile(nistOptions).execute(nistFile, Files.newOutputStream(resultFile.toPath()));
      // Then
      assertThat(outputStream).isNotNull();
      assertThat(resultFile).exists().isNotEmpty();
    }

    // And When I read it
    try (InputStream inputStream = Files.newInputStream(resultFile.toPath())) {
      NistFile resultNistFile = new ReadNistFile(nistOptions).execute(inputStream);
      // Then
      assertThat(resultNistFile).isNotNull();
      AssertNist.assertThatNist(resultNistFile).isEqualTo(nistFile);
      assertThat(resultNistFile.getRT1TransactionInformationRecord().getFieldText(DCS))
          .hasValue("003");
      assertThat(resultNistFile).isEqualTo(nistFile);
    }
  }
}
