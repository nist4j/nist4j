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

import static io.github.nist4j.enums.RecordTypeEnum.RT13;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.fixtures.FixturesNistReferenceFiles;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RT13LatentImageDataRecordSerializerImplITest {

  final DefaultTextRecordSerializer serializer =
      new DefaultTextRecordSerializer(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE, RT13.getNumber());

  final ReadNistFile readNistFile = new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_read_standard_file() throws IOException {
    // Given
    File nistType13File = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_13);

    // When
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(nistType13File.toPath()));

    NistRecord resultRecord = originalNistFile.getRT13VariableResolutionLatentImageRecords().get(0);

    // Then
    assertThat(resultRecord).isNotNull();
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.LEN)).hasValue("6281");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.IDC)).hasValue("01");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.IMP)).hasValue("4");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.SRC)).hasValue("MDNISTIMG");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.LCD)).hasValue("20091105");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.HLL)).hasValue("344");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.VLL)).hasValue("370");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.SLC)).hasValue("1");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.THPS)).hasValue("500");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.TVPS)).hasValue("500");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.CGA)).hasValue("WSQ20");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.BPX)).hasValue("8");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.FGP)).hasValue("19");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.SPD)).hasValue("7\u001FTIP");
    assertThat(resultRecord.getFieldText(RT13FieldsEnum.PPC))
        .hasValue("TIP\u001FNA\u001F102\u001F317\u001F108\u001F307");
    assertThat(resultRecord.getFieldImage(RT13FieldsEnum.DATA)).isNotEmpty();
  }

  @Test
  void write_should_be_able_to_read_a_nistFile() throws Exception {
    // Given
    File refNistFile = ImportFileUtils.getFileFromResource(FixturesNistReferenceFiles.TYPE_13);
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(refNistFile.toPath()));

    NistRecord originalRecord =
        originalNistFile.getRT13VariableResolutionLatentImageRecords().get(0);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When I write
    serializer.write(outputStream, originalRecord);

    // Then
    assertThat(outputStream.toByteArray()).isNotNull();
    log.debug("originalRecord: \n{}", originalRecord);

    // And When I read
    byte[] resultBytes = outputStream.toByteArray();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(resultBytes);
    token.crt = 13;
    NistRecord resultRecord = serializer.read(token);

    // Then
    log.debug("resultRecord \n{}", resultRecord.toString());
    assertThat(resultRecord).isNotNull();

    AssertNist.assertRecordEquals(resultRecord, originalRecord);
  }
}
