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
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.PLAIN_CONTACTLESS_MOVING_SUBJECT;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.fixtures.Record14Fixtures.record14Cas1_basic_Record;
import static io.github.nist4j.fixtures.Record14Fixtures.record14Cas2_EJI_Record;
import static io.github.nist4j.fixtures.RecordFixtures.newRecordBuilderEnableCalculation;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_RS;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class Std2007RT14ValidatorUTest {

  private final Std2007RT14Validator validator = new Std2007RT14Validator();
  private final NistValidationErrorMapper mapper = new NistValidationErrorMapper();

  @Test
  void validate_should_return_empty_list_with_basic_and_valid_record14() {
    // Given
    NistRecord nistRecord = record14Cas1_basic_Record().build();

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(nistRecord));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_eji_among_multiple_fgp_in_record14() {
    // Given
    NistRecordBuilder nistRecordBuilder = record14Cas2_EJI_Record();
    nistRecordBuilder.withField(FGP, newFieldText("1" + SEP_RS + "19"));
    NistRecord nistRecord = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(nistRecord));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_multiple_errors_with_invalid_record14() {
    // Given
    NistRecordBuilder nistRecordBuilder = record14Cas1_basic_Record();
    nistRecordBuilder.withField(IDC, newFieldText("100")); // Invalid value - too big integer (<100)
    nistRecordBuilder.withField(
        IMP,
        newFieldText(
            PLAIN_CONTACTLESS_MOVING_SUBJECT
                .getCode())); // Invalid value - value is not allowed for this standard
    nistRecordBuilder.withField(
        SRC, newFieldText("123456789AZERTYUIOPQSDFGHJKLMWXCVBN12")); // Invalid value - too long
    nistRecordBuilder.withField(FCD, newFieldText("20001232")); // Invalid date
    nistRecordBuilder.withField(VLL, newFieldText("100000")); // Invalid value - too long
    nistRecordBuilder.withField(HLL, newFieldText("1A0000")); // Invalid value - not  numerical
    nistRecordBuilder.withField(
        SLC, newFieldText("3")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(
        CGA, newFieldText("99")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(BPX, newFieldText("A")); // Invalid value - not  numerical
    nistRecordBuilder.withField(
        FGP,
        newFieldText("1" + SEP_RS + "99")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(SHPS, newFieldText("197564")); // Invalid value - too long
    nistRecordBuilder.withField(SVPS, newFieldText("197678")); // Invalid value - too long
    nistRecordBuilder.withField(
        AMP, newFieldText("XO")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(COM, newFieldText("ABჄ")); // Invalid value - weird char
    nistRecordBuilder.withField(
        SEG,
        newFieldText(
            "1" + SEP_US + "101" + SEP_US + "0000")); // Invalid value -  (5 items are necessary)
    nistRecordBuilder.withField(
        NQM, newFieldText("1" + SEP_US + "256")); // Invalid second item - should be <= 255
    nistRecordBuilder.withField(
        SQM,
        newFieldText(
            "1" + SEP_US + "101" + SEP_US + "0000" + SEP_US
                + "1")); // Invalid value -  QVU does not exist in reference
    nistRecordBuilder.withField(
        FQM,
        newFieldText(
            "1" + SEP_US + "101" + SEP_US + "0000" + SEP_US
                + "2")); // Invalid value -  QVU does not exist in reference
    nistRecordBuilder.withField(
        DMM, newFieldText("TEST")); // Invalid value - should be in reference
    nistRecordBuilder.withField(
        ASEG, newFieldText("1")); // Invalid value - should be at least 2 elements

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(nistRecordBuilder.build()));

    AssertValidator.assertThatErrors(errorsNist)
        .containsErrorWithValue(STD_ERR_IDC, "100")
        .containsErrorWithValue(STD_ERR_IMP_MANDATORY_RT14, "41")
        .containsErrorWithValue(STD_ERR_SRC_36, "123456789AZERTYUIOPQSDFGHJKLMWXCVBN12")
        .containsErrorWithValue(STD_ERR_FCD_RT14, "20001232")
        .containsErrorWithValue(STD_ERR_VLL_MANDATORY_RT14, "100000")
        .containsErrorWithValue(STD_ERR_HLL_MANDATORY_RT14, "1A0000")
        .containsErrorWithValue(STD_ERR_SLC_MANDATORY_RT14, "3")
        .containsErrorWithValue(STD_ERR_CGA_MANDATORY_RT14, "99")
        .containsErrorWithValue(STD_ERR_BPX_MANDATORY_RT14, "A")
        .containsErrorWithValue(STD_ERR_FGP_RT14, "1\u001E99")
        .containsErrorWithValue(STD_ERR_SHPS_O_RT14, "197564")
        .containsErrorWithValue(STD_ERR_SVPS_O_RT14, "197678")
        .containsErrorWithValue(STD_ERR_AMP_RT14, "XO")
        .containsErrorWithValue(STD_ERR_COM_RT14, "ABჄ")
        .containsErrorWithValue(STD_ERR_SEQ_5_ITEMS_RT14, "1\u001F101\u001F0000")
        .containsErrorWithValue(STD_ERR_NQM_RT14, "1\u001F256")
        .containsErrorWithValue(STD_ERR_SQM_RT14, "1\u001F101\u001F0000\u001F1")
        .containsErrorWithValue(STD_ERR_FQM_RT14, "1\u001F101\u001F0000\u001F2")
        .containsErrorWithValue(STD_ERR_ASEG_RT14, "1")
        .containsErrorWithValue(STD_ERR_DMM, "TEST");
  }

  @Test
  void validate_should_return_multiple_errors_with_missing_mandatory_fields_record14() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        newRecordBuilderEnableCalculation(14)
            .withField(LEN, newFieldText(String.valueOf(1)))
            .withField(IDC, newFieldText("0"));
    NistRecord nistRecord = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(nistRecord));

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_IMP_MANDATORY_RT14)
        .containsError(STD_ERR_SRC_36)
        .containsError(STD_ERR_FCD_RT14)
        .containsError(STD_ERR_VLL_MANDATORY_RT14)
        .containsError(STD_ERR_HLL_MANDATORY_RT14)
        .containsError(STD_ERR_THPS_MANDATORY_RT14)
        .containsError(STD_ERR_TVPS_MANDATORY_RT14)
        .containsError(STD_ERR_SLC_MANDATORY_RT14)
        .containsError(STD_ERR_CGA_MANDATORY_RT14)
        .containsError(STD_ERR_BPX_MANDATORY_RT14)
        .containsError(STD_ERR_FGP_RT14);
  }
}
