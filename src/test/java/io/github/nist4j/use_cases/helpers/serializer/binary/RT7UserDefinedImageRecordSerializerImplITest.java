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
import io.github.nist4j.enums.records.RT7FieldsEnum;
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
class RT7UserDefinedImageRecordSerializerImplITest {
  final RT7UserDefinedImageRecordSerializerImpl serializer =
      new RT7UserDefinedImageRecordSerializerImpl(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);
  final ReadNistFile readNistFile = new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_be_able_to_read_a_reference_nistFile() throws Exception {
    // Given
    File refFile = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_7);

    // When
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(refFile.toPath()));

    NistRecord resultRecord = originalNistFile.getRT7UserDefinedImageRecords().get(0);

    // Then
    assertThat(resultRecord).isNotNull();
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.LEN)).hasValue("614418");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IDC)).hasValue("1");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMT)).hasValue("6");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMD)).hasValue("8");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.PCN)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.PCN2)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.PCN3)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.PCN4)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.PCN5)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR)).hasValue("0");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR2)).hasValue("248");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR3)).hasValue("245");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR4)).hasValue("247");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR5)).hasValue("252");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR6)).hasValue("251");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR7)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR8)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR9)).hasValue("252");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR10)).hasValue("251");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.IMR11)).hasValue("255");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.HLL)).hasValue("65535");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.VLL)).hasValue("65531");
    assertThat(resultRecord.getFieldText(RT7FieldsEnum.GCA)).hasValue("252");
    assertThat(resultRecord.getFieldImage(RT7FieldsEnum.DATA)).isNotEmpty();
  }

  @Test
  void write_should_be_able_to_read_a_nistFile() throws Exception {
    // Given
    File nistType3File = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_7);
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(nistType3File.toPath()));

    NistRecord originalRecord = originalNistFile.getRT7UserDefinedImageRecords().get(0);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When I write
    serializer.write(outputStream, originalRecord);

    // Then
    assertThat(outputStream.toByteArray()).isNotNull();
    log.debug("originalRecord: \n{}", originalRecord.toString());

    // And When I read
    byte[] resultBytes = outputStream.toByteArray();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(resultBytes);
    token.crt = 7;
    NistRecord resultRecord = serializer.read(token);

    // Then
    log.debug("resultRecord \n{}", resultRecord.toString());
    assertThat(resultRecord).isNotNull();

    AssertNist.assertRecordEquals(resultRecord, originalRecord);
  }
}
