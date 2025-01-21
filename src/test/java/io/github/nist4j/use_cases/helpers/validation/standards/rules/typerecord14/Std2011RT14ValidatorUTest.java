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
import static io.github.nist4j.fixtures.RecordFixtures.newRecordBuilderEnableCalculation;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_RS;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class Std2011RT14ValidatorUTest {

  private final Std2011RT14Validator validator = new Std2011RT14Validator();
  private final NistValidationErrorMapper mapper = new NistValidationErrorMapper();

  @Test
  void validate_should_return_empty_list_with_basic_and_valid_record14() {
    // Given
    NistRecord record = record14Cas1_basic_Record().build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_valid_record14_and_fingers_combination() {
    // Given
    NistRecord record = record14Cas3_fingers_combination_Record().build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_valid_record14_and_EJI_fingers() {
    // Given
    NistRecord record = record14Cas2_EJI_Record().build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_valid_record14_with_amputated_finger() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        record14Cas4_amputed_finger_Record().withField(IMP, newFieldText("0"));

    NistRecord record = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

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
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_IMP_MANDATORY_RT14)
        .containsError(STD_ERR_SRC)
        .containsError(STD_ERR_SLC_RT14)
        .containsError(STD_ERR_THPS_RT14)
        .containsError(STD_ERR_TVPS_RT14)
        .containsError(STD_ERR_FGP_RT14);
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

    NistRecord record = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    AssertValidator.assertThatErrors(errorsNist)
        .containsErrorWithValue(STD_ERR_IDC, "100")
        .containsErrorWithValue(STD_ERR_SLC_RT14, "3")
        .containsErrorWithValue(STD_ERR_FGP_RT14, "190")
        .containsErrorWithValue(STD_ERR_SHPS_O_RT14, "197564")
        .containsErrorWithValue(STD_ERR_SVPS_O_RT14, "197678")
        .containsErrorWithValue(STD_ERR_AMP_RT14, "XO")
        .containsErrorWithValue(
            STD_ERR_COM_RT14,
            "Comment just a bit too long for the field: this field is supposed to contain less than 126 characters but this text is 132 characters")
        .containsErrorWithValue(STD_ERR_FQM_RT14, "1\u001F101\u001F0000\u001F1")
        .containsErrorWithValue(STD_ERR_SCF_RT14, "A")
        .containsErrorWithValue(STD_ERR_DMM, "TEST");
  }

  @Test
  void validate_should_return_multiple_items_with_basic_and_incompatibles_fields_record14() {
    // Given
    NistRecordBuilder nistRecordBuilder = record14Cas1_basic_Record();
    nistRecordBuilder.withField(SLC, newFieldText("2"));
    nistRecordBuilder.withField(
        FGP, newFieldText("1" + SEP_RS + "2")); // Only one value authorized since 2011
    nistRecordBuilder.withField(
        THPS, newFieldText("191")); // Unauthorized value, should be equals VPS, since SLC = 2

    NistRecord record = nistRecordBuilder.build();
    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_SLC_COHERENCE_RT14)
        .containsError(STD_ERR_FGP_ONE_ALLOWED_RT14);
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

    NistRecord record = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(record));

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_SEG_INVALID_RT14)
        .containsError(STD_ERR_SQM_RT14)
        .containsError(STD_ERR_SQM_UNALLOWED_FRQP_RT14)
        .containsErrorWithValue(STD_ERR_ASEG_RT14, "1\u001F2\u001F0\u001F0\u001F100\u001F104")
        .containsErrorWithValue(STD_ERR_SIF_RT14, "A")
        .containsErrorWithValue(STD_ERR_FAP_RT14, "70");
  }
}
