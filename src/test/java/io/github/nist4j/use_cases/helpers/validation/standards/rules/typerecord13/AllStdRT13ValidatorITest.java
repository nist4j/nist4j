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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT13;
import static io.github.nist4j.enums.records.RT13FieldsEnum.FGP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LQM;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_MISSING_STANDARD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.Record13Fixtures.record13Cas1_basic_Record;
import static io.github.nist4j.fixtures.Record1Fixtures.record1Cas1_basic_Record_withVersion;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_RS;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListOfList;
import static io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13.AbstractStdRT13Validator.validateFieldPPC;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.fixtures.Record13Fixtures;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat;
import io.github.nist4j.use_cases.helpers.builders.records.RT13LatentImageDataNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AllStdRT13ValidatorITest {

  private final NistValidationErrorMapper mapper = new NistValidationErrorMapper();
  private final ImportFileUtils importFileUtils = new ImportFileUtils();

  static Stream<Arguments> allStdValidators() {
    return Stream.of(
        Arguments.of(new Std2007RT13Validator()),
        Arguments.of(new Std2011RT13Validator()),
        Arguments.of(new Std2013RT13Validator()),
        Arguments.of(new Std2015RT13Validator()));
  }

  @ParameterizedTest
  @MethodSource("allStdValidators")
  void validate_that_all_validators_should_return_empty_list_with_basic_and_valid_record13(
      AbstractStdRT13Validator validator) {
    // Given
    NistRecord nistRecord = record13Cas1_basic_Record().build();

    // When
    List<NistValidationError> errorNist =
        mapper.fromValidationResult(validator.validate(nistRecord));

    // Then
    assertThat(errorNist).isEmpty();
  }

  @ParameterizedTest
  @MethodSource("allStdValidators")
  void
      validate_that_all_validators_should_return_empty_list_with_eji_among_multiple_fgp_in_record13(
          AbstractStdRT13Validator validator) {
    // Given
    NistRecordBuilder nistRecordBuilder = record13Cas1_basic_Record();
    nistRecordBuilder.withField(FGP, newFieldText("33" + SEP_RS + "20")); // Valid value- palm code
    nistRecordBuilder.withField(
        LQM,
        newFieldText(
            "33" + SEP_US + "1" + SEP_US + "0000" + SEP_US + "1")); // Valid value -  palm code
    NistRecord nistRecord = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(nistRecord));

    // Then
    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validateFieldPPC_should_return_expected_value() {
    // Given
    NistRecord okRecord =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("FV1", "NA", "101", "102", "103", "104"))))
            .build();
    NistRecord badRecordCauseF1Invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("F_V_1", "NA", "101", "102", "103", "104"))))
            .build();
    NistRecord badRecordCauseF2Invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("FV1", "N_A", "101", "102", "103", "104"))))
            .build();
    NistRecord badRecordCauseF3NotNumber =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("FV1", "NA", "BAD", "102", "103", "104"))))
            .build();
    NistRecord badRecordCauseF4NotNumber =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("FV1", "NA", "101", "BAD", "103", "104"))))
            .build();
    NistRecord badRecordCauseF5NotNumber =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("FV1", "NA", "101", "102", "BAD", "104"))))
            .build();
    NistRecord badRecordCauseF6NotNumber =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(
                RT13FieldsEnum.PPC,
                newSubfieldsFromListOfList(
                    singletonList(asList("FV1", "NA", "101", "102", "103", "BAD"))))
            .build();
    // When
    // Then
    assertThat(validateFieldPPC().test(okRecord)).isTrue();
    assertThat(validateFieldPPC().test(badRecordCauseF1Invalid)).isFalse();
    assertThat(validateFieldPPC().test(badRecordCauseF2Invalid)).isFalse();
    assertThat(validateFieldPPC().test(badRecordCauseF3NotNumber)).isFalse();
    assertThat(validateFieldPPC().test(badRecordCauseF4NotNumber)).isFalse();
    assertThat(validateFieldPPC().test(badRecordCauseF5NotNumber)).isFalse();
    assertThat(validateFieldPPC().test(badRecordCauseF6NotNumber)).isFalse();
  }

  @ParameterizedTest
  @MethodSource("allStdValidators")
  void validate_that_all_validators_should_identify_errors_on_ref_file(
      AbstractStdRT13Validator validator) throws IOException {
    // Given
    NistFile nist =
        importFileUtils.createNistFileFromFile(Record13Fixtures.referenceFailFile().toFile());

    // When
    List<NistValidationError> errorsNist =
        nist.getRT13VariableResolutionLatentImageRecords().stream()
            .map(validator::validate)
            .map(mapper::fromValidationResult)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    // Then
    assertThat(errorsNist).isNotEmpty();
    List<String> errorCodes =
        errorsNist.stream().map(NistValidationError::getCode).collect(Collectors.toList());

    // Then
    assertThat(errorCodes)
        .containsExactly(
            "STD_ERR_IMP_MANDATORY_RT13",
            "STD_ERR_LCD_RT13",
            "STD_ERR_BPX_MANDATORY_RT13",
            "STD_ERR_PPC_RT13",
            "STD_ERR_LQM_RT13");
  }

  @Test
  void validateANistFile_should_execute_no_validator_when_nistFile_does_not_match_to_standard() {

    // Given
    String wrongStandard = "500";
    ValidateNistFileWithStandardFormat validateNistFileWithStandardFormat =
        new ValidateNistFileWithStandardFormat();
    NistFile nist =
        NistFileFixtures.newNistFileBuilderEnableCalculation()
            .withRecord(RT1, record1Cas1_basic_Record_withVersion(wrongStandard).build())
            .withRecord(RT13, record13Cas1_basic_Record().build())
            .build();

    // When
    List<NistValidationError> errorsNist = validateNistFileWithStandardFormat.execute(nist);

    // Then
    assertThat(errorsNist).hasSize(1);
    assertThat(errorsNist.get(0).getCode()).isEqualTo(STD_ERR_MISSING_STANDARD.name());
    assertThat(errorsNist.get(0).getValueFound()).isEqualTo(wrongStandard);
  }
}
