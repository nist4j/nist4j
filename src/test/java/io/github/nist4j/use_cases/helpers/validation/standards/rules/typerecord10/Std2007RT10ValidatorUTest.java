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

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.test_utils.AssertValidator.*;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromItems;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.fixtures.Record10Fixtures;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import java.util.List;
import org.junit.jupiter.api.Test;

class Std2007RT10ValidatorUTest {

  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;
  private final Std2007RT10Validator validator = new Std2007RT10Validator();

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
        .containsErrorWithValue(STD_ERR_IDC_RT10, null)
        .containsErrorWithValue(STD_ERR_IMT_RT10, null)
        .containsErrorWithValue(STD_ERR_SRC_RT10, null)
        .containsErrorWithValue(STD_ERR_PHD_RT10, null)
        .containsErrorWithValue(STD_ERR_HLL_RT10, null)
        .containsErrorWithValue(STD_ERR_VLL_RT10, null)
        .containsErrorWithValue(STD_ERR_SLC_RT10, null)
        .containsErrorWithValue(STD_ERR_HPS_RT10, null)
        .containsErrorWithValue(STD_ERR_VPS_RT10, null)
        .containsErrorWithValue(STD_ERR_CGA_RT10, null)
        .containsErrorWithValue(STD_ERR_CSP_RT10, null)
        .containsErrorWithValue(STD_ERR_SAP_RT10, null)
        // ifSMT .containsErrorWithValue(STD_ERR_SMT_RT10, null)
        .containsErrorWithValue(STD_ERR_DATA_RT10, null);
  }

  @Test
  void checkForFieldPOA10_021_should_allows_negative_numbers() {
    // Given

    AbstractValidator<NistRecord> testValidator =
        new Std2007RT10Validator() {
          @Override
          public void rules() {
            checkForFieldPOA10_021();
          }
        };
    NistRecord rt10_with_67 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText(67))
            .build();

    NistRecord rt10_with_180 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText(180))
            .build();

    NistRecord rt10_with_minus67 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText(-67))
            .build();

    NistRecord rt10_with_minus180 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText(-180))
            .build();

    NistRecord rt10_with_0 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText(0))
            .build();

    NistRecord rt10_with_181_should_failed =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText(181))
            .build();

    NistRecord rt10_with_badvalue_should_failed =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .withField(RT10FieldsEnum.POA, newFieldText("badvalue"))
            .build();

    NistRecord rt10_with_no_POA_should_be_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.POS, newFieldText("A"))
            .build();

    // When
    assertThat(testValidator.validate(rt10_with_67).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_minus67).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_180).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_minus180).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_0).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_no_POA_should_be_ok).isValid()).isTrue();

    assertThat(testValidator.validate(rt10_with_181_should_failed).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_badvalue_should_failed).isValid()).isFalse();
  }

  @Test
  void checkForFieldPAS10_023_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2007RT10Validator() {
          @Override
          public void rules() {
            checkForFieldPAS10_023();
          }
        };
    NistRecord rt10_with_PAS_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_PAS_missing_with_SAP_lower =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAP, newFieldText("39"))
            .build();
    NistRecord rt10_with_PAS_missing_with_SAP_greater =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAP, newFieldText("50"))
            .build();

    NistRecord rt10_with_PAS_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAP, newFieldText("40"))
            .withField(RT10FieldsEnum.PAS, newFieldText("SCANNER"))
            .build();
    NistRecord rt10_with_PAS_valid_with_VENDOR =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAP, newFieldText("40"))
            .withField(RT10FieldsEnum.PAS, newSubfieldsFromItems("VENDOR", "2345RETY"))
            .build();
    NistRecord rt10_with_PAS_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAP, newFieldText("40"))
            .withField(RT10FieldsEnum.PAS, newFieldText("bad value"))
            .build();
    NistRecord rt10_with_PAS_invalid_with_VENDOR =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SAP, newFieldText("40"))
            .withField(RT10FieldsEnum.PAS, newSubfieldsFromItems("VENDOR"))
            .build();

    // When
    assertThat(testValidator.validate(rt10_with_PAS_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PAS_valid).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PAS_valid_with_VENDOR).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PAS_missing_with_SAP_lower).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PAS_missing_with_SAP_greater).isValid()).isTrue();

    assertThat(testValidator.validate(rt10_with_PAS_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_PAS_invalid_with_VENDOR).isValid()).isFalse();
  }

  @Test
  void checkForFieldSQS10_024_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2007RT10Validator() {
          @Override
          public void rules() {
            checkForFieldSQS10_024();
          }
        };
    NistRecord rt10_with_SQS_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_SQS_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SQS, newSubfieldsFromItems("1", "FEFE", "123"))
            .build();
    NistRecord rt10_with_SQS_invalid_format =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SQS, newSubfieldsFromItems("A", "FEFE", "123"))
            .build();

    NistRecord rt10_with_SQS_invalid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SQS, newSubfieldsFromItems("2", "FEFG", "123"))
            .build();

    NistRecord rt10_with_SQS_invalid_format3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SQS, newSubfieldsFromItems("2", "FEFE", "A"))
            .build();

    // When
    assertThat(testValidator.validate(rt10_with_SQS_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SQS_valid).isValid()).isTrue();

    assertThat(testValidator.validate(rt10_with_SQS_invalid_format).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SQS_invalid_format2).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SQS_invalid_format3).isValid()).isFalse();
  }

  @Test
  void checkForFieldFFP10_029_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2007RT10Validator() {
          @Override
          public void rules() {
            checkForFieldFFP10_029();
          }
        };
    NistRecord rt10_with_FFP_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_FFP_valid_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("1", "1.12", "120", "320"))
            .build();
    NistRecord rt10_with_FFP_invalid_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("1", "1.123", "120", "320"))
            .build();
    NistRecord rt10_with_FFP_valid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("2", "op", "120", "320"))
            .build();
    NistRecord rt10_with_FFP_invalid_format2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("2", "OP", "120", "320"))
            .build();
    NistRecord rt10_with_FFP_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("1", "1.12", "120"))
            .build();
    NistRecord rt10_with_FFP_invalid_item1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("3", "1.12", "120", "320"))
            .build();
    NistRecord rt10_with_FFP_invalid_item3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("3", "1.12", "120000", "320"))
            .build();
    NistRecord rt10_with_FFP_invalid_item4 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("3", "1.12", "120", "badvalue"))
            .build();
    NistRecord rt10_with_FFP_invalid_item5 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FFP, newSubfieldsFromItems("3", "1.12", "120", "0"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FFP_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FFP_valid_format1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FFP_valid_format2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FFP_invalid))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_FFP_invalid_format1))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_FFP_invalid_format2))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_FFP_invalid_item1))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_FFP_invalid_item3))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_FFP_invalid_item4))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_FFP_invalid_item5))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(STD_ERR_FFP_RT10.getMessage()));
  }

  @Test
  void checkForFieldCOL10_043_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2007RT10Validator() {
          @Override
          public void rules() {
            checkForFieldCOL10_043();
          }
        };
    NistRecord rt10_with_FFP_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_FFP_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.COL, newFieldText("YELLOW"))
            .build();
    NistRecord rt10_with_FFP_valid2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.COL, newSubfieldsFromItems("YELLOW", "BROWN"))
            .build();
    NistRecord rt10_with_FFP_bad_coll =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.COL, newSubfieldsFromItems("YELLOW", "BAD"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FFP_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FFP_valid).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FFP_valid2).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FFP_bad_coll).isValid()).isFalse();
  }
}
