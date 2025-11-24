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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord10;

import static io.github.nist4j.enums.RecordTypeEnum.RT10;
import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.test_utils.AssertValidator.*;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.fixtures.Record10Fixtures;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.checksum.Sha256Checksum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import java.util.List;
import org.junit.jupiter.api.Test;

class Std2011RT10ValidatorUTest {

  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;
  private final Std2011RT10Validator validator = new Std2011RT10Validator();

  @Test
  void validate_should_return_empty_list_with_basic_and_valid_record14() {
    // Given
    NistRecord nistRecord = Record10Fixtures.basicRecordWithLENChangeDigit().build();

    // When
    List<NistValidationError> errorsNist = validator.validate(nistRecord).getErrors();

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_with_empty_record_should_return_full_list_of_mandatory_errors() {
    // Given
    NistRecord nistRecord = Record10Fixtures.recordWithMissingMandatoryFields().build();

    // When
    List<NistValidationError> errorsNist = validator.validate(nistRecord).getErrors();

    assertThat(errorsNist).isNotEmpty();
    AssertValidator.assertThatErrors(errorsNist)
        .containsInvalidFieldWithValue(IDC, null)
        .containsInvalidFieldWithValue(IMT, null)
        .containsInvalidFieldWithValue(SRC, null)
        .containsInvalidFieldWithValue(PHD, null)
        .containsInvalidFieldWithValue(HLL, null)
        .containsInvalidFieldWithValue(VLL, null)
        .containsInvalidFieldWithValue(SLC, null)
        .containsInvalidFieldWithValue(HPS_LEGACY, null)
        .containsInvalidFieldWithValue(VPS_LEGACY, null)
        .containsInvalidFieldWithValue(CGA, null)
        .containsInvalidFieldWithValue(CSP, null)
        .containsInvalidFieldWithValue(DATA, null);
  }

  @Test
  void checkForFieldFIP10_014_should_validate_using_vll_and_hll() {
    // Given
    String expectedMsgFIP = ValidationMessage.format(STD_ERR_FIP, RT10, FIP);
    String expectedMsgFIP1 = ValidationMessage.format(STD_ERR_FIP_1, RT10, FIP);
    String expectedMsgFIP2 = ValidationMessage.format(STD_ERR_FIP_2, RT10, FIP);
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldFIP10_014();
          }
        };
    NistRecord rt10_with_FIP_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_FIP_badvalue =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FIP, newFieldText("badvalue"))
            .build();
    NistRecord rt10_with_FIP_badvalue2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newFieldText("badvalue"))
            .build();
    NistRecord rt10_with_FIP_ok_value =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("10", "20", "9", "100", "H"))
            .build();
    NistRecord rt10_with_FIP_bad_5_value =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("10", "20", "9", "100", "1"))
            .build();
    NistRecord rt10_with_FIP_ok_without_5_value =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("10", "20", "9", "100"))
            .build();
    NistRecord rt10_with_FIP_bad_cause_hll =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("601", "20", "9", "100"))
            .build();
    NistRecord rt10_with_FIP_bad_cause_vll =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("10", "20", "9", "501"))
            .build();
    NistRecord rt10_with_FIP_ok_big_values =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("598", "599", "498", "499"))
            .build();
    NistRecord rt10_with_FIP_bad_values =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("599", "598", "498", "499"))
            .build();
    NistRecord rt10_with_FIP_bad_values2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HLL, newFieldText(600))
            .withField(RT10FieldsEnum.VLL, newFieldText(500))
            .withField(RT10FieldsEnum.FIP, newSubfieldsFromItems("598", "599", "499", "498"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FIP_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FIP_ok_value).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FIP_ok_without_5_value).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FIP_ok_big_values).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FIP_badvalue))
        .matches(isNotValid())
        .matches(errorsNumberIs(3))
        .matches(errorsContainsMessage(expectedMsgFIP))
        .matches(errorsContainsMessage(expectedMsgFIP1))
        .matches(errorsContainsMessage(expectedMsgFIP2));
    assertThat(testValidator.validate(rt10_with_FIP_badvalue2))
        .matches(isNotValid())
        .matches(errorsNumberIs(3))
        .matches(errorsContainsMessage(expectedMsgFIP))
        .matches(errorsContainsMessage(expectedMsgFIP1))
        .matches(errorsContainsMessage(expectedMsgFIP2));
    assertThat(testValidator.validate(rt10_with_FIP_bad_5_value))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsgFIP));
    assertThat(testValidator.validate(rt10_with_FIP_bad_cause_hll))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsgFIP1));
    assertThat(testValidator.validate(rt10_with_FIP_bad_cause_vll))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsgFIP2));
    assertThat(testValidator.validate(rt10_with_FIP_bad_values))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsgFIP1));
    assertThat(testValidator.validate(rt10_with_FIP_bad_values2))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsgFIP2));
  }

  @Test
  void checkForFieldFPFI10_015_should_validate() {
    // Given
    String expectedMsg = ValidationMessage.format(STD_ERR_FPFI, RT10, RT10FieldsEnum.FPFI);
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldFPFI10_015();
          }
        };
    NistRecord rt10_with_FPFI_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_FPFI_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(RT10FieldsEnum.FPFI, newSubfieldsFromItems("C", "1", "10", "20"))
            .build();
    NistRecord rt10_with_FPFI_badvalue =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(RT10FieldsEnum.FPFI, newSubfieldsFromItems("Z", "1", "10", "20"))
            .build();
    NistRecord rt10_with_FPFI_badvalue2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(RT10FieldsEnum.FPFI, newSubfieldsFromItems("C", "1", "badvalue", "20"))
            .build();
    NistRecord rt10_with_FPFI_badvalue3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(RT10FieldsEnum.FPFI, newSubfieldsFromItems("C", "1", "10", "badvalue"))
            .build();
    NistRecord rt10_with_FPFI_valid_2pts =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(RT10FieldsEnum.FPFI, newSubfieldsFromItems("C", "1", "10", "20", "11", "21"))
            .build();
    NistRecord rt10_with_FPFI_valid_Elipse =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(RT10FieldsEnum.FPFI, newSubfieldsFromItems("E", "1", "10", "20", "11", "21"))
            .build();
    NistRecord rt10_with_FPFI_valid_Polygon =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .withField(
                RT10FieldsEnum.FPFI,
                newSubfieldsFromItems(
                    "P", "1", "10", "20", "11", "21", "10", "20", "11", "21", "10", "20", "11",
                    "21", "10", "20", "11", "21"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FPFI_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FPFI_valid).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FPFI_valid_2pts).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FPFI_valid_Elipse).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FPFI_valid_Polygon).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FPFI_badvalue))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_FPFI_badvalue2))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_FPFI_badvalue3))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
  }

  @Test
  void checkForFieldDIST10_018_should_validate() {
    // Given
    String expectedMsg = ValidationMessage.format(STD_ERR_DIST, RT10, RT10FieldsEnum.DIST);
    String expectedMsgIMT =
        ValidationMessage.format(STD_ERR_DIST_IMT_MUST_BE_FACE, RT10, RT10FieldsEnum.DIST);
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldDIST10_018();
          }
        };
    NistRecord rt10_with_DIST_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_DIST_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("FACE"))
            .withField(RT10FieldsEnum.DIST, newSubfieldsFromItems("Barrel", "E", "Mild"))
            .build();
    NistRecord rt10_with_DIST_invalid_missing_IMT =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DIST, newSubfieldsFromItems("Barrel", "E", "Mild"))
            .build();
    NistRecord rt10_with_DIST_invalid_format =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("FACE"))
            .withField(RT10FieldsEnum.DIST, newSubfieldsFromItems("Barrel", "X", "Mild"))
            .build();
    NistRecord rt10_with_DIST_invalid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("FACE"))
            .withField(RT10FieldsEnum.DIST, newSubfieldsFromItems("badvalue", "E", "Mild"))
            .build();
    NistRecord rt10_with_DIST_invalid_format3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("FACE"))
            .withField(RT10FieldsEnum.DIST, newSubfieldsFromItems("Barrel", "E", "badvalue"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_DIST_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_DIST_valid).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_DIST_invalid_missing_IMT))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsgIMT));
    assertThat(testValidator.validate(rt10_with_DIST_invalid_format))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_DIST_invalid_format2))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_DIST_invalid_format3))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
  }

  @Test
  void checkForFieldLAF10_019_should_validate() {
    // Given
    String expectedMsg = ValidationMessage.format(STD_ERR_LAF, RT10, RT10FieldsEnum.LAF);
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldLAF10_019();
          }
        };
    NistRecord rt10_with_LAF_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_LAF_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.LAF, newFieldText("F"))
            .build();
    NistRecord rt10_with_LAF_valid_2subfields =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.LAF, newSubfieldsFromItems("F", "H"))
            .build();
    NistRecord rt10_with_LAF_valid_3subfields =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.LAF, newSubfieldsFromItems("F", "H", "R"))
            .build();
    NistRecord rt10_with_LAF_invalid_format =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.LAF, newFieldText("bad"))
            .build();
    NistRecord rt10_with_LAF_invalid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.LAF, newSubfieldsFromItems("F", "bad", "R"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_LAF_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_LAF_valid).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_LAF_valid_2subfields).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_LAF_valid_3subfields).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_LAF_invalid_format))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_LAF_invalid_format2))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
  }

  @Test
  void checkForField3DF10_032_should_validate() {
    // Given
    String expectedMsg = ValidationMessage.format(STD_ERR_3DF, RT10, RT10FieldsEnum.THREEDF);
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForField3DF10_032();
          }
        };
    NistRecord rt10_with_3DF_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_3DF_valid_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.THREEDF, newSubfieldsFromItems("1", "1.12", "120", "320", "23"))
            .build();
    NistRecord rt10_with_3DF_invalid_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.THREEDF, newSubfieldsFromItems("1", "1.123", "120", "320", "23"))
            .build();
    NistRecord rt10_with_3DF_valid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.THREEDF, newSubfieldsFromItems("2", "op", "120", "320", "23"))
            .build();
    NistRecord rt10_with_3DF_invalid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.THREEDF, newSubfieldsFromItems("2", "OP", "120", "320", "23"))
            .build();
    NistRecord rt10_with_3DF_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.THREEDF, newSubfieldsFromItems("1", "1.12", "120", "320"))
            .build();
    NistRecord rt10_with_3DF_invalid_item1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.THREEDF, newSubfieldsFromItems("3", "1.12", "120", "320", "23"))
            .build();
    NistRecord rt10_with_3DF_invalid_item3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.THREEDF, newSubfieldsFromItems("3", "1.12", "120000", "320", "23"))
            .build();
    NistRecord rt10_with_3DF_invalid_item4 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.THREEDF, newSubfieldsFromItems("3", "1.12", "120", "badvalue", "23"))
            .build();
    NistRecord rt10_with_3DF_invalid_item5 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.THREEDF, newSubfieldsFromItems("3", "1.12", "120", "320", "0"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_3DF_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_3DF_valid_format1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_3DF_valid_format2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_3DF_invalid))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_3DF_invalid_format1))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_3DF_invalid_format2))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_3DF_invalid_item1))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_3DF_invalid_item3))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_3DF_invalid_item4))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_3DF_invalid_item5))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
  }

  @Test
  void checkForFieldFEC10_033_should_validate() {
    // Given
    String expectedMsg = ValidationMessage.format(STD_ERR_FEC, RT10, RT10FieldsEnum.FEC);
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldFEC10_033();
          }
        };
    NistRecord rt10_with_FEC_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_FEC_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("eyetop", "3", "13", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("badvalue", "3", "13", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_invalid2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("eyetop", "2", "13", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_invalid3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("eyetop", "2", "-1", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_invalid4 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC,
                newSubfieldsFromItems("eyetop", "2", "1", "1000000", "15", "53"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FEC_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FEC_valid).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FEC_invalid))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_FEC_invalid2).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_FEC_invalid3).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_FEC_invalid4).isValid()).isFalse();
  }

  @Test
  void checkForFieldSMD10_042_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldSMD10_042();
          }
        };
    NistRecord rt10_with_SMD_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_SMD_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newFieldText("ABC"))
            .build();
    NistRecord rt10_with_SMD_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("ABC", "ABCD"))
            .build();
    NistRecord rt10_with_SMD_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("ABC", "ABCD", "ABC"))
            .build();
    NistRecord rt10_with_SMD_valid_4 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("ABC", "ABCD", "ABC", "à"))
            .build();
    NistRecord rt10_with_SMD_valid_5 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.SMD,
                newSubfieldsFromListOfList(
                    asList(
                        asList("ABC", "ABCD", "ABC", "à"),
                        asList("ABC", "ABCD", "ABC", "à"),
                        singletonList("ABC"))))
            .build();

    NistRecord rt10_with_SMD_bad_format =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newFieldText("AB1"))
            .build();
    NistRecord rt10_with_SMD_bad_length =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newFieldText("AB"))
            .build();
    NistRecord rt10_with_SMD_bad_nb_items =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("ABC", "ABCD", "ABC", "à", "1"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_SMD_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_2).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_3).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_4).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_5).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_SMD_bad_format).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SMD_bad_length).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SMD_bad_nb_items).isValid()).isFalse();
  }

  @Test
  void checkForFieldITX10_044_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldITX10_044();
          }
        };
    NistRecord rt10_with_ITX_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_ITX_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ITX, newFieldText("AGE"))
            .build();
    NistRecord rt10_with_ITX_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ITX, newSubfieldsFromItems("AGE", "DOWNSAMPLE"))
            .build();

    NistRecord rt10_with_ITX_bad_format =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ITX, newFieldText("AB1"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_ITX_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ITX_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ITX_valid_2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_ITX_bad_format).isValid()).isFalse();
  }

  @Test
  void checkForFieldOCC10_045_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldOCC10_045();
          }
        };
    NistRecord rt10_with_OCC_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_OCC_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.OCC, newSubfieldsFromItems("T", "H", "10", "123", "456"))
            .build();
    NistRecord rt10_with_OCC_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.OCC,
                newSubfieldsFromItems("T", "H", "10", "123", "456", "0", "99999"))
            .build();

    NistRecord rt10_with_OCC_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.OCC, newSubfieldsFromItems("BAD", "H", "10", "123", "456"))
            .build();
    NistRecord rt10_with_OCC_bad_items =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.OCC, newFieldText("T"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_OCC_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_OCC_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_OCC_valid_2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_OCC_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_OCC_bad_items).isValid()).isFalse();
  }

  @Test
  void checkForFieldANN10_902_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldANN10_902();
          }
        };
    NistRecord rt10_with_ANN_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_ANN_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.ANN, newSubfieldsFromItems("20250821150601Z", "ABC", "ABC", "ABC"))
            .build();
    NistRecord rt10_with_ANN_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.ANN,
                newSubfieldsFromListOfList(
                    asList(
                        asList("20250821150601Z", "ABC", "ABC", "ABC"),
                        asList("20250821150601Z", "à白", "3456VBN@!白", "白"))))
            .build();

    NistRecord rt10_with_ANN_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ANN, newSubfieldsFromItems("BAD", "H", "10", "123", "456"))
            .build();
    NistRecord rt10_with_ANN_bad_items =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ANN, newSubfieldsFromItems("BAD", "H", "10", "123"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_ANN_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ANN_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ANN_valid_2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_ANN_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_ANN_bad_items).isValid()).isFalse();
  }

  @Test
  void checkForFieldDUI10_903_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldDUI10_903();
          }
        };
    NistRecord rt10_with_DUI_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_DUI_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DUI, newFieldText("M1234567890ABC"))
            .build();
    NistRecord rt10_with_DUI_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DUI, newFieldText("P1234567890ABC"))
            .build();

    NistRecord rt10_with_DUI_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DUI, newFieldText("B1234567890ABC"))
            .build();
    NistRecord rt10_with_DUI_bad_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DUI, newFieldText("B1234567白C"))
            .build();
    NistRecord rt10_with_DUI_bad_too_short =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DUI, newFieldText("M1234567890"))
            .build();
    NistRecord rt10_with_DUI_bad_too_long =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.DUI, newFieldText("M1234567890AZERTYUIO"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_DUI_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_DUI_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_DUI_valid_2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_DUI_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_DUI_bad_format2).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_DUI_bad_too_short).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_DUI_bad_too_long).isValid()).isFalse();
  }

  @Test
  void checkForFieldMMS10_904_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldMMS10_904();
          }
        };
    NistRecord rt10_with_MMS_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_MMS_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.MMS, newSubfieldsFromItems("1", "1", "1"))
            .build();
    NistRecord rt10_with_MMS_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.MMS, newSubfieldsFromItems("à", "*", "白"))
            .build();

    NistRecord rt10_with_MMS_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.MMS, newFieldText("MISSING"))
            .build();
    NistRecord rt10_with_MMS_bad_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.MMS,
                newSubfieldsFromListOfList(asList(asList("1", "1", "1"), asList("2", "2", "2"))))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_MMS_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_MMS_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_MMS_valid_2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_MMS_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_MMS_bad_format2).isValid()).isFalse();
  }

  @Test
  void checkForFieldASC10_995_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldASC10_995();
          }
        };
    NistRecord rt10_with_ASC_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_ASC_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ASC, newSubfieldsFromItems("1"))
            .build();
    NistRecord rt10_with_ASC_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ASC, newSubfieldsFromItems("1", "1"))
            .build();
    NistRecord rt10_with_ASC_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.ASC,
                newSubfieldsFromListOfList(asList(asList("1", "1"), asList("255", "99"))))
            .build();

    NistRecord rt10_with_ASC_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ASC, newFieldText("BAD"))
            .build();
    NistRecord rt10_with_ASC_bad_too_high =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.ASC, newSubfieldsFromItems("256", "1"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_ASC_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ASC_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ASC_valid_2).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_ASC_valid_3).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_ASC_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_ASC_bad_too_high).isValid()).isFalse();
  }

  @Test
  void checkForFieldSAN10_993_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldSAN10_993();
          }
        };
    NistRecord rt10_with_SAN_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_SAN_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAN, newFieldText("白&ércs234567bubbbBVNIB"))
            .build();

    NistRecord rt10_with_SAN_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.SAN,
                newFieldText(
                    "TOOLONG1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_SAN_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SAN_valid_1).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_SAN_bad_format1).isValid()).isFalse();
  }

  @Test
  void checkForFieldHAS10_996_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldHAS10_996();
          }
        };
    NistRecord rt10_with_HAS_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    byte[] imageData = new byte[] {0x01, 0x02, 0x03, 0x04};
    String expectedhash = Sha256Checksum.calculateToHex(imageData);
    NistRecord rt10_with_HAS_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HAS, newFieldText(expectedhash))
            .withField(RT10FieldsEnum.DATA, newFieldImage(imageData))
            .build();

    NistRecord rt10_with_HAS_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HAS, newFieldText("1234567890ABCDEF"))
            .withField(RT10FieldsEnum.DATA, newFieldImage(imageData))
            .build();
    NistRecord rt10_with_HAS_bad_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.HAS,
                newFieldText("G234567890ABCDEF1234567890ABCDEF1234567890ABCDEF1234567890ABCDEF"))
            .withField(RT10FieldsEnum.DATA, newFieldImage(imageData))
            .build();
    NistRecord rt10_with_HAS_bad_checksum =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.HAS, newFieldText(expectedhash))
            .withField(RT10FieldsEnum.DATA, newFieldImage(new byte[] {0x01, 0x02, 0x03, 0x09}))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_HAS_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_HAS_valid_1).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_HAS_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_HAS_bad_format2).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_HAS_bad_checksum).isValid()).isFalse();
  }

  @Test
  void checkForFieldSOR10_997_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldSOR10_997();
          }
        };
    NistRecord rt10_with_SOR_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_SOR_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SOR, newSubfieldsFromItems("1"))
            .build();
    NistRecord rt10_with_SOR_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SOR, newSubfieldsFromItems("1", "1"))
            .build();
    NistRecord rt10_with_SOR_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.SOR,
                newSubfieldsFromListOfList(asList(asList("1", "1"), asList("255", "99"))))
            .build();

    NistRecord rt10_with_SOR_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SOR, newFieldText("BAD"))
            .build();
    NistRecord rt10_with_SOR_bad_too_high =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SOR, newSubfieldsFromItems("256", "1"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_SOR_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SOR_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SOR_valid_2).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SOR_valid_3).isValid()).isTrue();

    // expected failed tests
    ValidationResult validate_with_SOR_bad_format1 =
        testValidator.validate(rt10_with_SOR_bad_format1);
    assertThat(validate_with_SOR_bad_format1.isValid()).isFalse();
    assertThatErrors(validate_with_SOR_bad_format1.getErrors()).containsErrorOn(RT10, SOR, "SRN");
    assertThat(testValidator.validate(rt10_with_SOR_bad_too_high).isValid()).isFalse();
  }

  @Test
  void checkForFieldGEO10_998_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2011RT10Validator() {
          @Override
          public void rules() {
            checkForFieldGEO10_998();
          }
        };
    NistRecord rt10_with_GEO_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_GEO_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.GEO, newSubfieldsFromItems("20250821150601Z"))
            .build();
    NistRecord rt10_with_GEO_valid_all =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.GEO,
                newSubfieldsFromItems(
                    "20250821150601Z",
                    "-90",
                    "0",
                    "1",
                    "-180",
                    "0",
                    "0",
                    "-422",
                    "INT",
                    "UK",
                    "0",
                    "0",
                    "白",
                    "白",
                    "白"))
            .build();
    NistRecord rt10_with_GEO_valid_all2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.GEO,
                newSubfieldsFromItems(
                    "20250821150601Z",
                    "-90",
                    "0",
                    "1",
                    "-180",
                    "0",
                    "0",
                    "124.001",
                    "INT",
                    "UK",
                    "0",
                    "0",
                    "白",
                    "白",
                    "白"))
            .build();

    NistRecord rt10_with_GEO_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.GEO, newFieldText("BAD"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_GEO_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_GEO_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_GEO_valid_all).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_GEO_valid_all2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_GEO_bad_format1).isValid()).isFalse();
  }
}
