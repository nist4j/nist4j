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
import static io.github.nist4j.enums.records.RT1FieldsEnum.CNT;
import static io.github.nist4j.fixtures.Record1Fixtures.record1Cas1_basic_Record_withVersion;
import static io.github.nist4j.fixtures.Record2Fixtures.record2Cas1_basic_Record;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromPairs;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.test_utils.AssertJnbisNist;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jnbis.api.Jnbis;
import org.jnbis.api.model.Nist;
import org.junit.jupiter.api.Test;

@Slf4j
class WriteNistFileThenReadItWithJnbisITest {

  private final WriteNistFile writeNistFile =
      new WriteNistFile(WriteNistFile.DEFAULT_OPTIONS_FOR_WRITE);

  @Test
  void writeANistFile_then_read_with_jnbis_should_be_the_same() throws IOException {
    // Given
    List<Pair<String, String>> cnt = asList(Pair.of("1", "2"), Pair.of("2", "57"));
    NistRecord rt1 =
        record1Cas1_basic_Record_withVersion("0500")
            .withField(CNT, newSubfieldsFromPairs(cnt))
            .build();
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(WriteNistFile.DEFAULT_OPTIONS_FOR_WRITE)
            .withRecord(RT1, rt1)
            .withRecord(RT2, record2Cas1_basic_Record().build())
            .build();
    File outputFile = File.createTempFile("nistFileBuilder", ".nist");

    // When
    log.debug("outputFile: {}", outputFile.toPath());
    writeNistFile.execute(nistFile, Files.newOutputStream(outputFile.toPath()));

    // Then
    Nist nist = Jnbis.nist().decode(outputFile);
    assertThat(nist).isNotNull();
    AssertJnbisNist.assertThatNist(nistFile).hasTheSameJnbisRecord1(nist);
    AssertJnbisNist.assertThatNist(nistFile).hasTheSameJnbisRecords2(nist);

    FileUtils.deleteQuietly(outputFile);
  }
}
