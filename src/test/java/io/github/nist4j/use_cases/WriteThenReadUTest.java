/*
 * Copyright (C) 2026 Sopra Steria.
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
import static io.github.nist4j.enums.RecordTypeEnum.RT10;
import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.fixtures.CharacterFixtures.*;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.test_utils.AssertNist;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.helpers.builders.options.NistOptionsBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
public class WriteThenReadUTest {
  private static final String prefixStr = "__________";

  @Test
  void readWriteThenRead_a_nist_with_UTF8_char_should_be_identical() throws IOException {
    // Given
    String expectedStr = "two chinese characters: 華裔";
    byte[] text = concatenateBytes(chinesCharUTF8InBytes, chines2CharUTF8InBytes);
    assertThat(expectedStr.getBytes(CharsetEnum.UTF_8.getCharset())).contains(text);

    File inputFile = ImportFileUtils.getFileFromResource("/references/type-14-amp-nqm-utf8.an2");
    log.info(
        "two chinese character : {} - {}",
        new String(chinesCharUTF8InBytes, StandardCharsets.UTF_8),
        new String(chines2CharUTF8InBytes, StandardCharsets.UTF_8));
    assertThat(inputFile).binaryContent().containsSequence(text);

    // When read
    NistFile nistFileRead1;
    try (InputStream inputStream = Files.newInputStream(inputFile.toPath())) {
      nistFileRead1 = new ReadNistFile().execute(inputStream);

      AssertNist.assertThatNist(nistFileRead1).hasEncoding(CharsetEnum.UTF_8);
      assertThat(nistFileRead1.getRT2UserDefinedDescriptionTextRecords().get(0).getFieldText(3))
          .hasValue(expectedStr);
    }

    // Then write
    File outputFile =
        File.createTempFile("readWriteThenRead_a_nist_with_UTF8_char_should_be_identical", ".nist");

    try (OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {
      new WriteNistFile().execute(nistFileRead1, outputStream);
    }
    assertThat(outputFile).binaryContent().containsSequence(text);

    // Then read again
    NistFile nistFileRead2;
    try (InputStream inputStream = Files.newInputStream(outputFile.toPath())) {
      nistFileRead2 = new ReadNistFile().execute(inputStream);

      AssertNist.assertThatNist(nistFileRead2).hasEncoding(CharsetEnum.UTF_8);
      AssertNist.assertThatNist(nistFileRead2).hasTheSameRecord1(nistFileRead1);
      AssertNist.assertThatNist(nistFileRead2).hasTheSameRecord10(nistFileRead1);

      assertThat(nistFileRead2.getRT2UserDefinedDescriptionTextRecords().get(0).getFieldText(3))
          .hasValue(expectedStr);
    }

    FileUtils.deleteQuietly(outputFile);
  }

  private NistFile generateNistFileWithEncoding(
      CharsetEnum charsetEnum, String text, File outputFile) throws IOException {

    String dcs =
        SubFieldToStringConverter.fromItems(
            String.valueOf(charsetEnum.getDcsValue()), charsetEnum.getLabel(), "4.0");
    NistOptions nistOptions =
        NistOptionsBuilderImpl.newBuilder()
            .isCalculateLENOnBuild(true)
            .isCalculateCNTOnBuild(true)
            .isDCSfieldUsedToDetectCharset(true)
            .charset(charsetEnum.getCharset())
            .build();
    CreateNistFile createNistFile = new CreateNistFile(nistOptions);

    NistRecord rt1 =
        new RT1TransactionInformationNistRecordBuilderImpl(nistOptions)
            .withField(RT1FieldsEnum.LEN, newFieldText(String.valueOf(1)))
            .withField(
                RT1FieldsEnum.VER, newFieldText(NistStandardEnum.ANSI_NIST_ITL_2015.getCode()))
            .withField(RT1FieldsEnum.CNT, newFieldText("1\u001F2\u001E2\u001F0\u001E5\u001F1"))
            .withField(RT1FieldsEnum.TOT, newFieldText("AMN"))
            .withField(RT1FieldsEnum.DAT, newFieldText("20091117"))
            .withField(RT1FieldsEnum.DAI, newFieldText("DAI000000"))
            .withField(RT1FieldsEnum.ORI, newFieldText("MDNISTIMG"))
            .withField(RT1FieldsEnum.TCN, newFieldText("jck t4 and t14 slaps"))
            .withField(RT1FieldsEnum.NSR, newFieldText("00.00"))
            .withField(RT1FieldsEnum.NTR, newFieldText("00.00"))
            .withField(RT1FieldsEnum.DOM, newFieldText("NORAM\u001F1"))
            .withField(RT1FieldsEnum.GMT, newFieldText("20091117124523Z"))
            .withField(RT1FieldsEnum.DCS, newFieldText(dcs))
            .build();

    byte[] facialImage =
        Files.readAllBytes(getFileFromResource("/fake/sample_10_14_17_type10.jpg").toPath());

    NistRecord rt10 =
        new RT10FacialSMTImageNistRecordBuilderImpl(nistOptions)
            .withField(LEN, newFieldText(String.valueOf(1)))
            .withField(IDC, newFieldText("23"))
            .withField(IMT, newFieldText("FACE"))
            .withField(SRC, newFieldText(text))
            .withField(PHD, newFieldText("20250102"))
            .withField(HLL, newFieldText("202"))
            .withField(VLL, newFieldText("250"))
            .withField(SLC, newFieldText("1"))
            .withField(HPS_LEGACY, newFieldText("300"))
            .withField(VPS_LEGACY, newFieldText("300"))
            .withField(CGA, newFieldText("JPEGB"))
            .withField(CSP, newFieldText("RGB"))
            .withField(SAP, newFieldText("0"))
            .withField(SMT, newFieldText("TATTOO"))
            .withField(899, newFieldText(text))
            .withField(DATA, newFieldImage(facialImage))
            .build();

    NistFile nistFile =
        createNistFile.execute().withRecord(RT1, rt1).withRecord(RT10, rt10).build();

    try (OutputStream outputStream = Files.newOutputStream(outputFile.toPath())) {
      new WriteNistFile(nistOptions).execute(nistFile, outputStream);
    }
    return nistFile;
  }

  private static String toHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b : bytes) {
      sb.append(String.format("%02X ", b & 0xFF));
    }
    return sb.toString().trim();
  }

  private static Stream<Arguments> getAllCharsetAndChars() {
    List<String> stringOfChar =
        Arrays.asList("I", "~", "}", "|", "ç", new String(Character.toChars(0x1D11E)), "白", "~");
    List<Integer> charToPoint =
        Arrays.asList(0x0049, 0x007E, 0x007D, 0x007C, 0x00E7, 0x1D11E, 0x767D, 0x007E);
    return Stream.of(CharsetEnum.UTF_8, CharsetEnum.UTF_16, CharsetEnum.UTF_32)
        .flatMap(
            charset ->
                IntStream.range(0, stringOfChar.size())
                    .mapToObj(
                        i ->
                            Arguments.of(
                                charset.name() + " " + stringOfChar.get(i),
                                charset,
                                charToPoint.get(i))));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getAllCharsetAndChars")
  void writeThenRead_in_UTF_complexe_should_be_identical(
      String name, CharsetEnum charsetEnum, Integer charToPoint) throws IOException {
    // Given
    String textStr = new String(Character.toChars(charToPoint));
    String fullTextStr = prefixStr + textStr + prefixStr;

    byte[] expectedFullTextBytesInCharset = fullTextStr.getBytes(charsetEnum.getCharset());

    File outputFile =
        File.createTempFile(
            "writeThenRead_in_c_cedilla_should_be_identical" + charsetEnum, ".nist");

    // When
    NistFile nistFile = generateNistFileWithEncoding(charsetEnum, fullTextStr, outputFile);

    assertThat(outputFile)
        .binaryContent()
        .containsSequence(expectedFullTextBytesInCharset)
        .as(
            "The nistFile file should contains Hexa code '{}', '{}'",
            toHex(textStr.getBytes()),
            outputFile.getAbsolutePath());

    // Then
    try (InputStream inputStream = Files.newInputStream(outputFile.toPath())) {
      NistFile nistFileLecture = new ReadNistFile().execute(inputStream);

      AssertNist.assertThatNist(nistFileLecture).hasTheSameRecord1(nistFile);
      AssertNist.assertThatNist(nistFileLecture).hasTheSameRecord10(nistFile);
    }

    FileUtils.deleteQuietly(outputFile);
  }

  private static Stream<Arguments> getComplexeChars() {
    List<String> stringOfChar = Arrays.asList("ç", new String(Character.toChars(0x1D11E)), "白");
    List<Integer> charToPoint = Arrays.asList(0x00E7, 0x1D11E, 0x767D);
    return IntStream.range(0, stringOfChar.size())
        .mapToObj(i -> Arguments.of(stringOfChar.get(i), charToPoint.get(i)));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getComplexeChars")
  void writeThenRead_in_ASCII_with_complexe_chars_must_be_different(
      String name, Integer charToPoint) throws IOException {
    // Given
    CharsetEnum charsetEnum = CharsetEnum.ASCII;
    String textStr = new String(Character.toChars(charToPoint));
    String fullTextStr = prefixStr + textStr + prefixStr;

    byte[] expectedFullTextBytesInCharset = fullTextStr.getBytes(charsetEnum.getCharset());
    String expectedFullTextStringInCharset =
        new String(expectedFullTextBytesInCharset, charsetEnum.getCharset());

    File outputFile =
        File.createTempFile(
            "writeThenRead_in_ASCII_with_simple_must_be_identical" + charsetEnum, ".nist");

    // When
    NistFile nistFile = generateNistFileWithEncoding(charsetEnum, fullTextStr, outputFile);

    assertThat(outputFile)
        .binaryContent()
        .containsSequence(expectedFullTextBytesInCharset)
        .as(
            "The nistFile file should contains Hexa code '{}', '{}'",
            toHex(textStr.getBytes()),
            outputFile.getAbsolutePath());

    // Then
    try (InputStream inputStream = Files.newInputStream(outputFile.toPath())) {
      NistFile nistFileLecture = new ReadNistFile().execute(inputStream);

      AssertNist.assertThatNist(nistFileLecture).hasTheSameRecord1(nistFile);
      assertThat(nistFile.getRT10FacialAndSmtImageRecords().get(0).getFieldText(SRC))
          .hasValue(fullTextStr);
      assertThat(nistFileLecture.getRT10FacialAndSmtImageRecords().get(0).getFieldText(SRC))
          .hasValue(expectedFullTextStringInCharset);
      assertThat(fullTextStr).isNotEqualTo(expectedFullTextStringInCharset);

      assertThat(nistFile.getRT10FacialAndSmtImageRecords().get(0).getFieldText(899))
          .hasValue(fullTextStr);
      assertThat(nistFileLecture.getRT10FacialAndSmtImageRecords().get(0).getFieldText(899))
          .hasValue(expectedFullTextStringInCharset);
      assertThat(fullTextStr).isNotEqualTo(expectedFullTextStringInCharset);
    }

    FileUtils.deleteQuietly(outputFile);
  }

  private static Stream<Arguments> getSimpleChars() {
    List<String> stringOfChar = Arrays.asList("I", "~", "}", "|");
    List<Integer> charToPoint = Arrays.asList(0x0049, 0x007E, 0x007D, 0x007C);
    return IntStream.range(0, stringOfChar.size())
        .mapToObj(i -> Arguments.of(stringOfChar.get(i), charToPoint.get(i)));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getSimpleChars")
  void writeThenRead_in_ASCII_with_simple_must_be_identical(String name, Integer charToPoint)
      throws IOException {
    // Given
    CharsetEnum charsetEnum = CharsetEnum.ASCII;
    String textStr = new String(Character.toChars(charToPoint));
    String fullTextStr = prefixStr + textStr + prefixStr;

    byte[] expectedFullTextBytesInCharset = fullTextStr.getBytes(charsetEnum.getCharset());
    String expectedFullTextStringInCharset =
        new String(expectedFullTextBytesInCharset, charsetEnum.getCharset());

    File outputFile =
        File.createTempFile(
            "writeThenRead_in_ASCII_with_simple_must_be_identical" + charsetEnum, ".nist");

    // When
    NistFile nistFile = generateNistFileWithEncoding(charsetEnum, fullTextStr, outputFile);

    // Then
    assertThat(outputFile)
        .binaryContent()
        .containsSequence(expectedFullTextBytesInCharset)
        .as(
            "The nistFile file should contains Hexa code '{}', '{}'",
            toHex(textStr.getBytes()),
            outputFile.getAbsolutePath());

    try (InputStream inputStream = Files.newInputStream(outputFile.toPath())) {
      NistFile nistFileLecture = new ReadNistFile().execute(inputStream);

      AssertNist.assertThatNist(nistFileLecture).hasTheSameRecord1(nistFile);
      AssertNist.assertThatNist(nistFileLecture).hasTheSameRecord10(nistFile);
      assertThat(nistFile.getRT10FacialAndSmtImageRecords().get(0).getFieldText(SRC))
          .hasValue(fullTextStr);
      assertThat(nistFileLecture.getRT10FacialAndSmtImageRecords().get(0).getFieldText(SRC))
          .hasValue(expectedFullTextStringInCharset);

      assertThat(nistFile.getRT10FacialAndSmtImageRecords().get(0).getFieldText(899))
          .hasValue(fullTextStr);
      assertThat(nistFileLecture.getRT10FacialAndSmtImageRecords().get(0).getFieldText(899))
          .hasValue(expectedFullTextStringInCharset);
    }

    FileUtils.deleteQuietly(outputFile);
  }
}
