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
package io.github.nist4j.use_cases.helpers.builders.field;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT9FieldsEnum;
import io.github.nist4j.fixtures.FixturesNistReferenceFiles;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.ReadNistFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.Test;

class RT9FieldsHelperITest {

  @Test
  void parseLegacyMRCField_should_read_real_file() throws IOException {
    // Given
    File refNistFile = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_9);
    NistFile nistFile = new ReadNistFile().execute(Files.newInputStream(refNistFile.toPath()));
    NistRecord nistRecord9 = nistFile.getRT9MinutiaeDataRecords().get(0);
    String legacyMRC = nistRecord9.getFieldText(RT9FieldsEnum.MRC_LEGACY).orElse(null);
    assertThat(legacyMRC).isNotBlank();

    // When
    List<List<String>> resultat = RT9FieldsHelper.parseLegacyMRCField(legacyMRC);

    // Then
    assertThat(resultat).hasSize(48);
    assertThat(resultat.get(0)).hasSize(12);
    assertThat(resultat.get(0))
        .containsExactly(
            "001",
            "21952030101",
            "00",
            "D",
            "033,04",
            "255,15",
            "255,15",
            "009,06",
            "005,01",
            "047,02",
            "002,02",
            "045,03");
  }

  @Test
  void parseMRCField_and_newLegacyMRCField_should_be_reversible() throws IOException {
    // Given
    File refNistFile = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_9);
    NistFile nistFile = new ReadNistFile().execute(Files.newInputStream(refNistFile.toPath()));
    NistRecord nistRecord9 = nistFile.getRT9MinutiaeDataRecords().get(0);
    String originalLegacyMRC = nistRecord9.getFieldText(RT9FieldsEnum.MRC_LEGACY).orElse(null);
    assertThat(originalLegacyMRC).isNotBlank();

    // When
    List<List<String>> resultatListOfList = RT9FieldsHelper.parseLegacyMRCField(originalLegacyMRC);
    String resultLegacyMRC = RT9FieldsHelper.formatLegacyMRCField(resultatListOfList);

    // Then
    assertThat(resultatListOfList).hasSize(48);
    assertThat(resultatListOfList.get(0)).hasSize(12);
    assertThat(resultLegacyMRC).isEqualTo(originalLegacyMRC);
  }
}
