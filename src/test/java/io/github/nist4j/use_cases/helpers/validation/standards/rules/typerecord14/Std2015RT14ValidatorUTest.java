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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14;

import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.fixtures.Record14Fixtures.*;
import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_RS;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.test_utils.AssertValidator;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Std2015RT14ValidatorUTest {

  private final Std2015RT14Validator validator = new Std2015RT14Validator();

  @Test
  void validate_should_return_empty_list_with_basic_and_valid_record14() {
    // Given
    NistRecord record = record14Cas1_basic_Record().build();

    // When
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_valid_record14_and_fingers_combination() {
    // Given
    NistRecord record = record14Cas3_fingers_combination_Record().build();

    // When
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_valid_record14_and_EJI_fingers() {
    // Given
    NistRecord record = record14Cas2_EJI_Record().build();

    // When
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_valid_record14_with_amputated_finger() {
    // Given
    NistRecord record = record14Cas4_amputed_finger_Record().build();

    // When
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_multiple_items_with_missing_mandatory_fields_record14() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        newRecordBuilderEnableCalculation(14)
            .withField(LEN, newFieldText(String.valueOf(1)))
            .withField(IDC, newFieldText("0"))
            .withField(DATA, newFieldImage(getFakeImage(64)));
    NistRecord record = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    AssertValidator.assertThatErrors(errorsNist)
        .containsExactlyInvalidFields(IMP, SRC, FCD, HLL, VLL, SLC, THPS, TVPS, CGA, BPX, FGP);
  }

  @Test
  void validate_should_return_multiple_items_with_basic_and_invalid_values_record14() {
    // Given

    NistRecordBuilder nistRecordBuilder = record14Cas1_basic_Record();
    nistRecordBuilder.withField(IDC, newFieldText("100")); // Invalid value - too big integer (<100)
    nistRecordBuilder.withField(
        SLC, newFieldText("3")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(
        FGP, newFieldText("190")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(SHPS, newFieldText("197564")); // Invalid value - too long
    nistRecordBuilder.withField(SVPS, newFieldText("197678")); // Invalid value - too long
    nistRecordBuilder.withField(
        AMP, newFieldText("XO")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(
        COM,
        newFieldText(
            "Comment just a bit too long for the field: this field is supposed to contain less than 126 characters but this text is 132 characters")); // Invalid value - too long
    nistRecordBuilder.withField(
        FQM,
        newFieldText(
            "1" + SEP_US + "101" + SEP_US + "0000" + SEP_US
                + "1")); // Invalid value -  QVU does not exist in reference
    nistRecordBuilder.withField(SCF, newFieldText("A")); // Invalid value - should be integer
    nistRecordBuilder.withField(
        DMM, newFieldText("TEST")); // Invalid value - should be in reference
    nistRecordBuilder.withField(CON, newFieldText("ABჄ")); // Invalid value - weird char

    // When
    NistRecord record = nistRecordBuilder.build();
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    AssertValidator.assertThatErrors(errorsNist)
        .containsInvalidFieldWithValue(IDC, "100")
        .containsInvalidFieldWithValue(SLC, "3")
        .containsInvalidFieldWithValue(FGP, "190")
        .containsInvalidFieldWithValue(SHPS, "197564")
        .containsInvalidFieldWithValue(SVPS, "197678")
        .containsInvalidFieldWithValue(AMP, "XO")
        .containsInvalidFieldWithValue(
            COM,
            "Comment just a bit too long for the field: this field is supposed to contain less than 126 characters but this text is 132 characters")
        .containsInvalidFieldWithValue(FQM, "1\u001F101\u001F0000\u001F1")
        .containsInvalidFieldWithValue(SCF, "A")
        .containsInvalidFieldWithValue(DMM, "TEST");
  }

  @Test
  void validate_should_return_multiple_items_with_basic_and_incompatibles_fields_record14() {
    // Given
    NistRecordBuilder nistRecordBuilder = record14Cas1_basic_Record();
    nistRecordBuilder.withField(SLC, newFieldText("2"));
    nistRecordBuilder.withField(
        FGP, newFieldText("1" + SEP_RS + "2")); // Only one value authorized since 2011
    nistRecordBuilder.withField(
        THPS, newFieldText("191")); // Unauthorized value, should be equal VPS, since SLC = 2
    nistRecordBuilder.withField(
        SUB,
        newFieldText(
            "A" + SEP_US + "A" + SEP_US + "1")); // Invalid value - Should be "D" to have 3 items

    // When
    NistRecord record = nistRecordBuilder.build();
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_SLC_COHERENCE_RT14)
        .containsError(STD_ERR_FGP_ONE_ALLOWED_RT14)
        .containsInvalidFieldWithValue(SUB, "A")
        .containsInvalidFieldWithValue(SUB, "1");
  }

  @Test
  void validate_should_return_multiple_items_with_invalid_record14_and_fingers_combination() {
    // Given
    NistRecordBuilder nistRecordBuilder = record14Cas3_fingers_combination_Record();

    nistRecordBuilder.withField(
        SQM,
        newFieldText(
            "1"
                + SEP_US
                + "101"
                + SEP_US
                + "0000"
                + SEP_US
                + "1"
                + SEP_RS
                + // Invalid value -  QVU does not exist in reference
                "2"
                + SEP_US
                + "0"
                + SEP_US
                + "0000"
                + SEP_US
                + "1" // Invalid value - FRQP does not exist in FRSP (14.021 ) nor FRAS (14.025)
            ));
    nistRecordBuilder.withField(
        SEG,
        newFieldText(
            "11" + SEP_US + "12" + SEP_US + "14" + SEP_US + "12" + SEP_US
                + "14")); // Invalid value -  FRSP does not exist in reference
    nistRecordBuilder.withField(
        ASEG,
        newFieldText(
            "1" + SEP_US + "2" + SEP_US + "0" + SEP_US + "0" + SEP_US + "100" + SEP_US
                + "104")); // Invalid value - should contain at least 3 points
    nistRecordBuilder.withField(SIF, newFieldText("A")); // Invalid value - should be Y
    nistRecordBuilder.withField(FAP, newFieldText("70")); // Invalid value - not in reference
    nistRecordBuilder.withField(
        SUB,
        newFieldText(
            "D" + SEP_US + "A" + SEP_US + "B")); // Invalid value - Third value should be 1 or 2

    // When
    NistRecord record = nistRecordBuilder.build();
    List<NistValidationError> errorsNist = validator.validate(record).getErrors();

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_SQM_RT14)
        .containsError(STD_ERR_SQM_UNALLOWED_FRQP_RT14)
        .containsError(STD_ERR_SEG_INVALID_RT14)
        .containsInvalidFieldWithValue(ASEG, "1\u001F2\u001F0\u001F0\u001F100\u001F104")
        .containsInvalidFieldWithValue(SIF, "A")
        .containsInvalidFieldWithValue(FAP, "70")
        .containsInvalidFieldWithValue(SUB, "B");
  }

  @ParameterizedTest
  @CsvSource({
    "'', withImg, error", // mandatory field
    "'', withoutImg, success", // not mandatory when no image
    "'0', withImg, success",
    "'1', withImg, success",
    "'8', withImg, success", // new in 2015
    "'2', withImg, error", // not in table
    "'24', withImg, success", // deprecated in 2025
    "'', withExtFile, error", // mandatory when EFR present
    "'0', withExtFile, success", // mandatory when EFR present
  })
  void validate_should_check_IMP_Field(
      String fieldIMPValue, String withImg, String expectedResult) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldIMPValue)) {
      rt14Builder.withField(IMP, newFieldText(fieldIMPValue));
    }
    if ("withImg".equals(withImg)) {
      rt14Builder.withField(DATA, newFieldImage(getFakeImage(5)));
    } else if ("withExtFile".equals(withImg)) {
      rt14Builder.withField(EFR, newFieldText("https://where-to-find.local/ref/"));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equals(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(IMP);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(IMP);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'1', withImg, success",
    "'', withImg, error", // SLC is mandatory when image
    "'', withExtFile, error", // SLC is mandatory when ext file
    "'2', withImg, success",
    "'3', withImg, error",
    "'0', withImg, success",
    "'0', withExtFile, success",
    "'', withExtFile, error",
    "'', withoutImg, success",
  })
  void validate_should_check_SLC14_008_Field(
      String fieldSLCValue, String withImg, String expectedResult) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();

    if (!isEmpty(fieldSLCValue)) {
      rt14Builder.withField(SLC, newFieldText(fieldSLCValue));
    }
    if ("withImg".equals(withImg)) {
      rt14Builder.withField(DATA, newFieldImage(getFakeImage(5)));
    } else if ("withExtFile".equals(withImg)) {
      rt14Builder.withField(EFR, newFieldText("https://where-to-find.local/ref/"));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(SLC);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(SLC);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'0', withImg, success", // valid unknown finger
    "'', withImg, error", // mandatory field when image
    "'', withExtFile, error", // mandatory field when extFile
    "'', nothing, error", // mandatory when no image neither extFile
    "'55', nothing, error", // not valid in 2015 only 2025
  })
  void validate_should_check_FGP_Field(
      String fieldFGPValue, String withImg, String expectedResult) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldFGPValue)) {
      rt14Builder.withField(FGP, newFieldText(fieldFGPValue));
    }
    if ("withImg".equals(withImg)) {
      rt14Builder.withField(DATA, newFieldImage(getFakeImage(5)));
    } else if ("withExtFile".equals(withImg)) {
      rt14Builder.withField(EFR, newFieldText("https://where-to-find.local/ref/"));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(FGP);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(FGP);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'', withImg, success, optional field",
    "'', withExtFile, success, optional field",
    "'', nothing, success, optional field",
    "'0', withImg, error, not a valid amputed code",
    "'1\u001FSR', nothing, success, simple valid",
    "'99\u001FSR', nothing, error, param1 not a valid subfield AMP.FRAP must FGP",
    "'1\u001FOO', nothing, error, param2 not a valid AMP.ABC must SR,XX,UP",
    "'1\u001FSR\u001E2\u001FXX\u001E3\u001FUP', nothing, success, full valid exemple in 2015",
    "'1\u001FSR\u001FA', nothing, error, 3 params is not allow",
  })
  void validate_should_check_AMP_Field(
      String fieldAMPValue, String withImg, String expectedResult, String reason) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldAMPValue)) {
      rt14Builder.withField(AMP, newFieldText(fieldAMPValue));
    }
    if ("withImg".equals(withImg)) {
      rt14Builder.withField(DATA, newFieldImage(getFakeImage(5)));
    } else if ("withExtFile".equals(withImg)) {
      rt14Builder.withField(EFR, newFieldText("https://where-to-find.local/ref/"));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(AMP);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(AMP);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'', '', '', success, optional field",
    "'0','0','0', error, not a SQM valid",
    "'1\u001F3\u001FFFFF\u001F65535', '1', '', success, all valid params",
    "'1\u001F3\u001FFFFF\u001F65535', '', '1', success, all valid params",
    "'1\u001F3\u001FFFFF\u001F65536', '', '1', error, param4 out of range",
    "'1\u001F3\u001FFFFG\u001F65535', '', '1', error, param3 out of range",
    "'1\u001F101\u001FFFFF\u001F65535', '', '1', error, param2 out of range",
    "'99\u001F3\u001FFFFF\u001F65535', '', '1', error, param1 out of range",
    "'1\u001F3\u001FFFFF\u001F65535\u001F1', '', '1', error, too many params",
  })
  void validate_should_check_SQM_Field(
      String fieldSQMValue,
      String fieldSEGValue,
      String fieldASEGValue,
      String expectedResult,
      String reason) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldSQMValue)) {
      rt14Builder.withField(SQM, newFieldText(fieldSQMValue));
    }
    if (!isEmpty(fieldSEGValue)) {
      rt14Builder.withField(SEG, newFieldText(fieldSEGValue));
    }
    if (!isEmpty(fieldASEGValue)) {
      rt14Builder.withField(ASEG, newFieldText(fieldASEGValue));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(SQM);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(SQM).containsValidMsg(SQM);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'', '', '', success, optional field",
    "'0','0','0', error, not a FQM valid",
    "'1\u001F3\u001FFFFF\u001F65535', '1', '', success, all valid params",
    "'1\u001F3\u001FFFFF\u001F65535', '', '1', success, all valid params",
    "'1\u001F3\u001FFFFF\u001F65536', '', '1', error, param4 out of range",
    "'1\u001F3\u001FFFFG\u001F65535', '', '1', error, param3 out of range",
    "'1\u001F101\u001FFFFF\u001F65535', '', '1', error, param2 out of range",
    "'99\u001F3\u001FFFFF\u001F65535', '', '1', error, param1 out of range",
    "'1\u001F3\u001FFFFF\u001F65535\u001F1', '', '1', error, too many params",
  })
  void validate_should_check_FQM_Field(
      String fieldFQMValue,
      String fieldSEGValue,
      String fieldASEGValue,
      String expectedResult,
      String reason) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldFQMValue)) {
      rt14Builder.withField(FQM, newFieldText(fieldFQMValue));
    }
    if (!isEmpty(fieldSEGValue)) {
      rt14Builder.withField(SEG, newFieldText(fieldSEGValue));
    }
    if (!isEmpty(fieldASEGValue)) {
      rt14Builder.withField(ASEG, newFieldText(fieldASEGValue));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(FQM);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(FQM);
    }
  }
}
