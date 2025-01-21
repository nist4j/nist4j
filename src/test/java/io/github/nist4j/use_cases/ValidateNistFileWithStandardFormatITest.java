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
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_MISSING_STANDARD;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_UNIMPLEMENTED_STANDARD;
import static io.github.nist4j.fixtures.Record1Fixtures.record1Cas1_basic_Record_withVersion;
import static io.github.nist4j.fixtures.Record2Fixtures.record2Cas1_basic_Record;
import static io.github.nist4j.test_utils.ImportFileUtils.getFilesFromResources;
import static io.github.nist4j.use_cases.CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.records.DefaultNistTextRecordBuilderImpl.newRecordBuilder;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.helpers.builders.file.NistFileBuilderImpl;
import io.github.nist4j.use_cases.helpers.calculators.CalculateR1CNTAndLengthCallback;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ValidateNistFileWithStandardFormatITest {

  private final ValidateNistFileWithStandardFormat validateNistFileWithStandardFormat =
      new ValidateNistFileWithStandardFormat();

  private final ImportFileUtils importFileUtils = new ImportFileUtils();

  @ParameterizedTest(name = "{0}")
  @MethodSource("getPassFiles2007")
  void execute_should_return_a_empty_list_when_nistFile_is_valid_to_standard_2007(
      String filename, File file) throws IOException, ErrorDecodingNist4jException {
    // Given
    NistFile nist = importFileUtils.createNistFileFromFile(file);

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).isEmpty();
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getPassFiles2011")
  void execute_should_return_a_empty_list_when_nistFile_is_valid_to_standard_2011(
      String filename, File file) throws IOException, ErrorDecodingNist4jException {
    // Given
    NistFile nist = importFileUtils.createNistFileFromFile(file);

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).isEmpty();
    // Fix this error : STD_ERR_PPC_RT13
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getFailFiles2011")
  void execute_should_return_a_list_non_vide_when_file_is_invalid_to_standard_2011(
      String filename, File file) throws IOException, ErrorDecodingNist4jException {
    // Given
    NistFile nist = importFileUtils.createNistFileFromFile(file);

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).isNotEmpty();
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getPassFiles2013")
  void execute_should_return_a_empty_list_when_nistFile_is_valid_to_standard_2013(
      String filename, File file) throws IOException, ErrorDecodingNist4jException {
    // Given
    NistFile nist = importFileUtils.createNistFileFromFile(file);

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).isEmpty();
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getFailFiles2013")
  void execute_should_return_a_list_non_vide_when_file_is_invalid_to_standard_2013(
      String filename, File file) throws IOException, ErrorDecodingNist4jException {
    // Given
    NistFile nist = importFileUtils.createNistFileFromFile(file);

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).isNotEmpty();
  }

  @Test
  void execute_should_execute_no_validator_when_nistFile_does_not_match_to_standard() {
    // Given
    String wrongStandard = "500";
    NistFile nist =
        NistFileFixtures.newNistFileBuilderEnableCalculation()
            .withRecord(RT1, record1Cas1_basic_Record_withVersion(wrongStandard).build())
            .withRecord(RT2, record2Cas1_basic_Record().build())
            .build();

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).hasSize(1);
    assertThat(errorsNist.get(0).getCode()).isEqualTo(STD_ERR_MISSING_STANDARD.name());
    assertThat(errorsNist.get(0).getValueFound()).isEqualTo(wrongStandard);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getPassFiles2015")
  void execute_should_return_a_empty_list_when_nistFile_is_valid(String filename, File file)
      throws IOException, ErrorDecodingNist4jException {
    // Given
    NistFile nist = importFileUtils.createNistFileFromFile(file);

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void execute_should_return_a_error_when_nistFile_has_unimplemented_standard()
      throws ErrorDecodingNist4jException {
    // Given
    List<Callback<NistFileBuilder>> beforeBuildCallbacks =
        Collections.singletonList(new CalculateR1CNTAndLengthCallback(DEFAULT_OPTIONS_FOR_CREATE));

    NistRecord recordRT1 =
        newRecordBuilder(DEFAULT_OPTIONS_FOR_CREATE, RT1.getNumber())
            .withField(RT1FieldsEnum.VER, newFieldText("0300"))
            .build();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE, beforeBuildCallbacks, emptyList())
            .withRecord(RT1, recordRT1);
    NistFile nist = nistFileBuilder.build();
    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).hasSize(1);
    assertThat(errorsNist.get(0).getCode()).isEqualTo(STD_ERR_UNIMPLEMENTED_STANDARD.getCode());
  }

  @Test
  void test_should_return_a_error_when_nistFile_has_invalid_standard()
      throws ErrorDecodingNist4jException {
    // Given
    List<Callback<NistFileBuilder>> beforeBuildCallbacks =
        Collections.singletonList(new CalculateR1CNTAndLengthCallback(DEFAULT_OPTIONS_FOR_CREATE));

    NistRecord recordRT1 =
        newRecordBuilder(DEFAULT_OPTIONS_FOR_CREATE, RT1.getNumber())
            .withField(RT1FieldsEnum.VER, newFieldText("INVALID"))
            .build();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE, beforeBuildCallbacks, emptyList())
            .withRecord(RT1, recordRT1);
    NistFile nist = nistFileBuilder.build();
    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    assertThat(errorsNist).hasSize(1);
    assertThat(errorsNist.get(0).getCode()).isEqualTo(STD_ERR_MISSING_STANDARD.getCode());
  }

  private static Stream<Arguments> getPassFiles2007() {
    String DIRECTORY_FILES = "/references";
    return getFilesFromResources(DIRECTORY_FILES, "^type.*$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }

  private static Stream<Arguments> getPassFiles2011() {
    String DIRECTORY_FILES = "/standards/AN2011/Traditional_Encoding";
    return getFilesFromResources(DIRECTORY_FILES + "/pass", "^pass.*$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }

  private static Stream<Arguments> getFailFiles2011() {
    String DIRECTORY_FILES = "/standards/AN2011/Traditional_Encoding";
    return getFilesFromResources(DIRECTORY_FILES + "/fail_validator", "^fail.*$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }

  private static Stream<Arguments> getPassFiles2013() {
    String DIRECTORY_FILES = "/standards/AN2013/Traditional_Encoding";
    return getFilesFromResources(DIRECTORY_FILES + "/pass", "^pass-.*$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }

  private static Stream<Arguments> getFailFiles2013() {
    String DIRECTORY_FILES = "/standards/AN2013/Traditional_Encoding";
    return getFilesFromResources(DIRECTORY_FILES + "/fail_validator", "^fail-.*$").stream()
        .filter(
            file ->
                !file.getName()
                    .contains(
                        "fail-all-supported-types-long-data.an2")) // TODO this file should failed
        // too
        .map(file -> Arguments.of(file.getName(), file));
  }

  private static Stream<Arguments> getPassFiles2015() {
    String DIRECTORY_FILES = "/standards/AN2015";
    return getFilesFromResources(DIRECTORY_FILES + "/pass", "^pass.*$").stream()
        .map(file -> Arguments.of(file.getName(), file));
  }
}
