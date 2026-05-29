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
import static io.github.nist4j.fixtures.Record14Fixtures.*;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.test_utils.AssertValidator;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Std2025RT14ValidatorUTest {

  private final Std2025RT14Validator validator = new Std2025RT14Validator();

  @ParameterizedTest
  @CsvSource({
    "'', withImg, error", // mandatory field
    "'', withoutImg, success", // not mandatory when no image
    "'0', withImg, success",
    "'1', withImg, success",
    "'8', withImg, success", // new in 2015
    "'2', withImg, error", // not in table
    "'24', withImg, error", // deprecated in 2025
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
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(IMP);
    } else {
      assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(IMP).containsValidMsg(IMP);
    }
  }

  // 14.011 CGA - Added new codes ‘WSQ’, ‘PNM’, ‘PNG’, designated WSQ20 as Legacy use only
  @ParameterizedTest
  @CsvSource({
    "'PNG', withImg, success", // mandatory field when image
    "'', withImg, error", // mandatory field when image
    "'', nothing, success", // optional when no image neither extFile
    "'PNG', nothing, error", // forbidden when no image neither extFile
    "'', withExtFile, error", // mandatory field when extFile
    "'WSQ', withExtFile, success", // since 2025 WSQ is included
    "'PNM', withImg, success", // since 2025 WSQ is included
    "'WSQ20', withImg, error", // since 2025 WSQ20 is deprecated
  })
  void validate_should_check_CGA_Field(
      String fieldCGAValue, String withImg, String expectedResult) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldCGAValue)) {
      rt14Builder.withField(CGA, newFieldText(fieldCGAValue));
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
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(CGA);
    } else {
      assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(CGA).containsValidMsg(CGA);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'0', withImg, success", // valid unknown finger
    "'', withImg, error", // mandatory field when image
    "'', withExtFile, error", // mandatory field when extFile
    "'', nothing, error", // mandatory when no image neither extFile
    "'55', nothing, success", // since 2025 new value
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
      assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(FGP).containsValidMsg(FGP);
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
    "'1\u001FXX\u001FPA', nothing, success, since 2025 3 params is allow",
    "'1\u001FXX\u001FAA', nothing, error, since 2025 3 params is allow but on list",
    "'1\u001FSR\u001FPA', nothing, error, since 2025 3 params is not allow with SR",
    "'1\u001FSR\u001E3\u001FUP\u001FPA\u001E4\u001FXX\u001FFA', nothing, success, full valid exemple in 2025",
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
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(AMP).containsValidMsg(AMP);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'', '8', withImg, success, optional field when BPX<8",
    "'', '8', withExtFile, success, optional field when BPX<8",
    "'', '', nothing, success, optional field when BPX<8",
    "'', '16', withImg, error, mandatory when BPX>8",
    "'UNK', '16', withImg, success, mandatory when BPX>8",
    "'BAD', '16', withImg, error, mandatory when BPX>8 but in list",
  })
  void validate_should_check_CSP_Field(
      String fieldCSPValue,
      String fieldBPXValue,
      String withImg,
      String expectedResult,
      String reason) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldBPXValue)) {
      rt14Builder.withField(BPX, newFieldText(fieldBPXValue));
    }
    if (!isEmpty(fieldCSPValue)) {
      rt14Builder.withField(CSP, newFieldText(fieldCSPValue));
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
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(CSP);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(CSP).containsValidMsg(CSP);
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
    "'1\u001F3\u001FFFFF\u001F65535\u001Fç', '', '1', success, since 2025 valid 5 params",
    "'1\u001F3\u001FFFFF\u001F65535\u001Fç\u001Fç', '', '1', success, since 2025 valid 6 params",
    "'1\u001F3\u001FFFFF\u001F65535\u001Fç\u001Fç\u001F1', '', '1', error, since 2025 valid 7 params",
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
    "'1\u001F3\u001FFFFF\u001F65535\u001Fç', '', '1', success, since 2025 valid 5 params",
    "'1\u001F3\u001FFFFF\u001F65535\u001Fç\u001Fç', '', '1', success, since 2025 valid 6 params",
    "'1\u001F3\u001FFFFF\u001F65535\u001Fç\u001Fç\u001F1', '', '1', error, since 2025 valid 7 params",
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
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(FQM).containsValidMsg(FQM);
    }
  }

  @ParameterizedTest
  @CsvSource(
      value = {
        "'', success, optional field",
        "'2', error, param1 is mandatory",
        "'2\u001F1.123\u001F0A9F\u001F123', success, all valid params",
        "'0\u001F1.123\u001F0A9F\u001F123\u001Fç ok\u001F€ ok\u001F0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF', error, invalid param1",
        "'9\u001Fà\u001F0A9F\u001F123\u001Fç ok\u001F€ ok\u001F0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF', error, invalid param2",
        "'9\u001F1.123\u001F00\u001F123\u001Fç ok\u001F€ ok\u001F0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF', error, invalid param3",
        "'9\u001F1.123\u001F0A9F\u001F0\u001Fç ok\u001F€ ok\u001F0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF', error, invalid param4",
        "'9\u001F1.123\u001F0A9F\u001F123\u001F\u001F€ ok\u001F0A9F', error, invalid param4",
        "'9\u001F1.123\u001F0A9F\u001F123\u001Fç ok\u001F\u001F0A9F', error, invalid param5",
        "'9\u001F1.123\u001F0A9F\u001F123\u001Fç ok\u001F€ ok\u001F0A9F', error, invalid param6",
        "'9\u001F1.123\u001F0A9F\u001F123\u001Fç ok\u001F€ ok\u001F0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF\u001F1', error, too many params",
      })
  void validate_should_check_FQC_Field(String fieldFQCValue, String expectedResult, String reason) {
    // Given
    NistRecordBuilder rt14Builder = record14_empty();
    if (!isEmpty(fieldFQCValue)) {
      rt14Builder.withField(FQC, newFieldText(fieldFQCValue));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt14Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(FQC);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(FQC);
    }
  }
}
