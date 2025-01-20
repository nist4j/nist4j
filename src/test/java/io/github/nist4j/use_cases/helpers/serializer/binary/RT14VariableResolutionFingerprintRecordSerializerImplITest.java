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
import io.github.nist4j.enums.records.RT14FieldsEnum;
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
class RT14VariableResolutionFingerprintRecordSerializerImplITest {
  final RT14VariableResolutionFingerprintRecordSerializerImpl serializer =
      new RT14VariableResolutionFingerprintRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);
  final ReadNistFile readNistFile = new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_be_able_to_read_nist_reference_file() throws Exception {
    // Given
    File refNistFile = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_14);

    // When
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(refNistFile.toPath()));

    NistRecord resultRecord = originalNistFile.getRT14VariableResolutionFingerprintRecords().get(0);

    // Then
    assertThat(resultRecord).isNotNull();
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.LEN)).hasValue("50415");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.IDC)).hasValue("03");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.IMP)).hasValue("2");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.SRC)).hasValue("MDNISTIMG");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.FCD)).hasValue("20091105");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.HLL)).hasValue("804");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.VLL)).hasValue("1000");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.SLC)).hasValue("1");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.THPS)).hasValue("500");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.TVPS)).hasValue("500");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.CGA)).hasValue("WSQ20");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.BPX)).hasValue("8");
    assertThat(resultRecord.getFieldText(RT14FieldsEnum.FGP)).hasValue("15");
    assertThat(resultRecord.getFieldImage(RT14FieldsEnum.DATA)).isNotEmpty();
  }

  @Test
  void write_should_be_able_to_read_a_nistFile() throws Exception {
    // Given
    File refNistFile = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_14);
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(refNistFile.toPath()));

    NistRecord originalRecord =
        originalNistFile.getRT14VariableResolutionFingerprintRecords().get(0);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When I write
    serializer.write(outputStream, originalRecord);

    // Then
    assertThat(outputStream.toByteArray()).isNotNull();
    log.debug("originalRecord: \n{}", originalRecord);

    // And When I read
    byte[] resultBytes = outputStream.toByteArray();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(resultBytes);
    token.crt = 14;
    NistRecord resultRecord = serializer.read(token);

    // Then
    log.debug("resultRecord \n{}", resultRecord.toString());
    assertThat(resultRecord).isNotNull();

    AssertNist.assertRecordEquals(resultRecord, originalRecord);
  }
}
