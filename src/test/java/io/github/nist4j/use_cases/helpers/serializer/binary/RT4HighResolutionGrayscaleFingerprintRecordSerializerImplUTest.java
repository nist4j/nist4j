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

import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromItems;
import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.toItems;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.ReadNistFile;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.converters.LongTo2BytesConverter;
import io.github.nist4j.use_cases.helpers.converters.LongTo4BytesConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

@Slf4j
class RT4HighResolutionGrayscaleFingerprintRecordSerializerImplUTest {

  public static final int BYTE_IMAGE = 3;
  public static final byte UNDEFINED_BYTE_VALUE = (byte) 255;

  final RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl serializer =
      new RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl(
          ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);
  final ReadNistFile readNistFile = new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  @Test
  void read_should_be_conform_to_the_spec() throws ErrorDecodingNist4jException {
    // Given
    int totalLen = 1024;
    byte[] expectedImage = getExpectedImage(totalLen - 18);
    byte[] buffer = getExpectedBuffer(totalLen);

    // When
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(buffer);
    token.crt = 4;
    NistRecord record = serializer.read(token);
    // Then
    assertThat(record.getFieldText(GenericImageTypeEnum.LEN)).hasValue(String.valueOf(totalLen));
    assertThat(record.getFieldText(GenericImageTypeEnum.IDC)).hasValue("4");
    assertThat(record.getFieldText(GenericImageTypeEnum.IMP)).hasValue("5");
    assertThat(record.getFieldText(GenericImageTypeEnum.FGP))
        .hasValue("6\u001F255\u001F255\u001F255\u001F255\u001F255");
    assertThat(record.getFieldText(GenericImageTypeEnum.ISR)).hasValue("12");
    assertThat(record.getFieldText(GenericImageTypeEnum.HLL)).hasValue("123");
    assertThat(record.getFieldText(GenericImageTypeEnum.VLL)).hasValue("987");
    assertThat(record.getFieldImage(GenericImageTypeEnum.DATA).orElse(new byte[] {}).length)
        .isEqualTo(totalLen - 18);
    assertThat(record.getFieldImage(GenericImageTypeEnum.DATA).orElse(new byte[] {}))
        .isEqualTo(expectedImage);
  }

  private byte[] getExpectedBuffer(int totalLen) {
    byte[] buffer = new byte[totalLen];
    byte[] len = LongTo4BytesConverter.to4Bytes(totalLen);
    byte[] hll = LongTo2BytesConverter.to2Bytes(123);
    byte[] vll = LongTo2BytesConverter.to2Bytes(987);
    buffer[0] = len[0];
    buffer[1] = len[1];
    buffer[2] = len[2];
    buffer[3] = len[3];
    buffer[4] = 4; // IDC
    buffer[5] = 5; // IMP
    buffer[6] = 6; // FGP
    buffer[7] = UNDEFINED_BYTE_VALUE; // FGP2
    buffer[8] = (byte) 255; // FGP3
    buffer[9] = (byte) 255; // FGP4
    buffer[10] = (byte) 255; // FGP5
    buffer[11] = (byte) 255; // FGP6
    buffer[12] = 12; // ISR
    buffer[13] = hll[0];
    buffer[14] = hll[1];
    buffer[15] = vll[0];
    buffer[16] = vll[1];
    buffer[17] = 17; // GCA
    for (int i = 18; i < totalLen; i++) {
      buffer[i] = BYTE_IMAGE;
    }
    return buffer;
  }

  private static byte[] getExpectedImage(int totalLen) {
    byte[] image = new byte[totalLen];
    Arrays.fill(image, (byte) BYTE_IMAGE);
    return image;
  }

  @Test
  void write_should_calculate_the_length() {
    // Given
    int totalLen = 1024;
    byte[] expectedBuffer = getExpectedBuffer(totalLen);
    NistRecord record =
        newRecordBuilderEnableCalculation(4)
            .withField(GenericImageTypeEnum.LEN, newFieldText(1))
            .withField(GenericImageTypeEnum.IDC, newFieldText("4"))
            .withField(GenericImageTypeEnum.IMP, newFieldText("5"))
            .withField(GenericImageTypeEnum.FGP, newFieldText("6"))
            .withField(GenericImageTypeEnum.ISR, newFieldText("12"))
            .withField(GenericImageTypeEnum.HLL, newFieldText("123"))
            .withField(GenericImageTypeEnum.VLL, newFieldText("987"))
            .withField(GenericImageTypeEnum.GCA, newFieldText("17"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(getExpectedImage(totalLen - 18)))
            .build();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When
    serializer.write(outputStream, record);
    byte[] resultBytes = outputStream.toByteArray();

    // Then
    assertThat(resultBytes).isEqualTo(expectedBuffer);
  }

  @Test
  void write_should_be_conform_to_the_spec() {
    // Given
    int totalLen = 1024;
    byte[] expectedBuffer = getExpectedBuffer(totalLen);
    NistRecord record =
        newRecordBuilderEnableCalculation(4)
            .withField(GenericImageTypeEnum.LEN, newFieldText(String.valueOf(totalLen)))
            .withField(GenericImageTypeEnum.IDC, newFieldText("4"))
            .withField(GenericImageTypeEnum.IMP, newFieldText("5"))
            .withField(
                GenericImageTypeEnum.FGP,
                newSubfieldsFromItems("6", "255", "255", "255", "255", "255"))
            .withField(GenericImageTypeEnum.ISR, newFieldText("12"))
            .withField(GenericImageTypeEnum.HLL, newFieldText("123"))
            .withField(GenericImageTypeEnum.VLL, newFieldText("987"))
            .withField(GenericImageTypeEnum.GCA, newFieldText("17"))
            .withField(GenericImageTypeEnum.DATA, newFieldImage(getExpectedImage(totalLen - 18)))
            .build();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When
    serializer.write(outputStream, record);
    byte[] resultBytes = outputStream.toByteArray();

    // Then
    assertThat(resultBytes).isEqualTo(expectedBuffer);
  }

  @Test
  void write_should_be_able_to_write_after_reading() throws Exception {
    // Given
    File nistType4File = ImportFileUtils.getFileFromResource("/references/type-4-14-slaps.an2");
    NistFile originalNistFile = readNistFile.execute(Files.newInputStream(nistType4File.toPath()));

    NistRecord originalRecord =
        originalNistFile.getRT4HighResolutionGrayscaleFingerprintRecords().get(0);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    // When I write
    serializer.write(outputStream, originalRecord);

    // Then
    assertThat(outputStream.toByteArray()).isNotNull();
    log.debug("originalRecord: \n{}", originalRecord.toString());

    // And When I read
    byte[] resultBytes = outputStream.toByteArray();
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(resultBytes);
    token.crt = 4;
    NistRecord resultRecord = serializer.read(token);

    // Then
    log.debug("resultRecord \n{}", resultRecord.toString());
    assertThat(resultRecord).isNotNull();

    AssertNist.assertRecordEquals(resultRecord, originalRecord);
  }

  @Test
  void read_should_read_record_type_4() throws Exception {
    // Given
    String path = "/references/type-4-14-slaps.an2";
    byte[] nistFileBytes = Files.readAllBytes(getFileFromResource(path).toPath());
    log.debug("{} : length {}", path, nistFileBytes.length);
    NistDecoderHelper.Token token = new NistDecoderHelper.Token(nistFileBytes);
    token.crt = 4;
    token.pos = 252;

    // When
    NistRecord record = serializer.read(token);

    // Then
    AssertionsForClassTypes.assertThat(record).isNotNull();
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.LEN))
        .hasValue("104277");
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.IDC)).hasValue("1");
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.IMP)).hasValue("2");
    Optional<String> fieldFGP = record.getFieldText(GenericImageTypeEnum.FGP);
    assertThat(fieldFGP).isNotEmpty();
    assertThat(toItems(fieldFGP.get())).contains("14", "255", "255", "255", "255", "255");
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.ISR)).hasValue("0");
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.HLL))
        .hasValue("1608");
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.VLL))
        .hasValue("1000");
    AssertionsForClassTypes.assertThat(record.getFieldText(GenericImageTypeEnum.GCA)).hasValue("1");
    AssertionsForClassTypes.assertThat(record.getFieldImage(GenericImageTypeEnum.DATA))
        .isNotEmpty();
    AssertionsForClassTypes.assertThat(
            record.getFieldImage(GenericImageTypeEnum.DATA).orElse(new byte[] {}).length)
        .isEqualTo(104259);
  }
}
