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

import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.toItems;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
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
class RT5LowResolutionBinaryFingerprintRecordSerializerImplITest {
  final RT5LowResolutionBinaryFingerprintRecordSerializerImpl serializer =
      new RT5LowResolutionBinaryFingerprintRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);
  final ReadNistFile readNistFile = new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_be_able_to_read_a_reference_nistFile() throws Exception {
    // Given
    File nistType5File = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_5);

    // When
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(nistType5File.toPath()));

    NistRecord resultRecord = originalNistFile.getRT5LowResolutionBinaryFingerprintRecords().get(0);

    // Then
    assertThat(resultRecord).isNotNull();
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.LEN)).hasValue("19194");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.IDC)).hasValue("1");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.IMP)).hasValue("3");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.FGP)).isNotEmpty();
    assertThat(toItems(resultRecord.getFieldText(GenericImageTypeEnum.FGP).orElse(null)))
        .contains("2", "255");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.ISR)).hasValue("0");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.HLL)).hasValue("402");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.VLL)).hasValue("376");
    assertThat(resultRecord.getFieldText(GenericImageTypeEnum.GCA)).hasValue("0");
    assertThat(resultRecord.getFieldImage(GenericImageTypeEnum.DATA)).isNotEmpty();
  }

  @Test
  void write_should_be_able_to_read_a_nistFile() throws Exception {
    // Given
    File nistType5File = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_5);
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(nistType5File.toPath()));

    NistRecord originalRecord =
        originalNistFile.getRT5LowResolutionBinaryFingerprintRecords().get(0);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When I write
    serializer.write(outputStream, originalRecord);

    // Then
    assertThat(outputStream.toByteArray()).isNotNull();
    log.debug("originalRecord: \n{}", originalRecord.toString());

    // And When I read
    byte[] resultBytes = outputStream.toByteArray();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(resultBytes);
    token.crt = 3;
    NistRecord resultRecord = serializer.read(token);

    // Then
    log.debug("resultRecord \n{}", resultRecord.toString());
    assertThat(resultRecord).isNotNull();

    AssertNist.assertRecordEquals(resultRecord, originalRecord);
  }
}
