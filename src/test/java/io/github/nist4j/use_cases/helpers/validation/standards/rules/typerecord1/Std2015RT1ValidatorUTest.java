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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord1;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2015;
import static io.github.nist4j.enums.RecordTypeEnum.*;
import static io.github.nist4j.enums.records.RT1FieldsEnum.*;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.fixtures.Record1Fixtures.record1Cas1_basic_Record_withVersion;
import static io.github.nist4j.fixtures.Record1Fixtures.record1Cas3_full_Record;
import static io.github.nist4j.fixtures.Record2Fixtures.record2Cas1_basic_Record;
import static io.github.nist4j.fixtures.Record4Fixtures.record4Cas2_with_real_image_WSQ_Record1;
import static io.github.nist4j.fixtures.RecordFixtures.newRecordBuilderDisableCalculation;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Std2015RT1ValidatorUTest {

  private final Std2015RT1Validator validator = new Std2015RT1Validator();
  private final NistValidationErrorMapper mapper = new NistValidationErrorMapper();

  private NistFileBuilder nistBuilder;

  @BeforeEach
  void setUp() {
    NistRecordBuilder nistRecordBuilder1 =
        record1Cas1_basic_Record_withVersion(ANSI_NIST_ITL_2015.getCode());

    nistRecordBuilder1.withField(CNT, newFieldText("1\u001F1\u001E2\u001F57"));

    nistBuilder =
        NistFileFixtures.newNistFileBuilderDisableCalculation()
            .withRecord(RT1, nistRecordBuilder1.build())
            .withRecord(RT2, record2Cas1_basic_Record().build());
  }

  @Test
  void validate_should_return_empty_list_with_basic_and_valid_record1() {
    // Given
    NistFile nist = nistBuilder.build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(nist));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_empty_list_with_record4_and_valid_fields_in_record1()
      throws IOException {
    // Given
    NistRecordBuilder nistRecordBuilder =
        record1Cas3_full_Record()
            .withField(VER, newFieldText(ANSI_NIST_ITL_2015.getCode()))
            .withField(CNT, newFieldText("1\u001F2\u001E2\u001F57\u001E4\u001F1"));

    NistFile nist =
        nistBuilder
            .removeRecord(RT1)
            .withRecord(RT1, nistRecordBuilder.build())
            .withRecord(RT4, record4Cas2_with_real_image_WSQ_Record1().build())
            .build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(nist));

    assertThat(errorsNist).isEmpty();
  }

  @Test
  void validate_should_return_multiple_items_with_missing_mandatory_fields_record1() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        newRecordBuilderDisableCalculation(1).withField(LEN, newFieldText(1));

    NistFile nist =
        nistBuilder.removeRecord(RT1).withRecord(RT1, nistRecordBuilder.build()).build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(nist));

    AssertValidator.assertThatErrors(errorsNist)
        .containsErrorWithValue(STD_ERR_VER_RT1, null)
        .containsErrorWithValue(STD_ERR_CNT_FORMAT_RT1, null)
        .containsErrorWithValue(STD_ERR_TOT_RT1, null)
        .containsErrorWithValue(STD_ERR_DAT_RT1, null)
        .containsErrorWithValue(STD_ERR_DAI_RT1, null)
        .containsErrorWithValue(STD_ERR_ORI_RT1, null)
        .containsErrorWithValue(STD_ERR_TCN_RT1, null);
  }

  @Test
  void validate_should_return_multiple_items_with_basic_and_invalid_values_record1() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        newRecordBuilderDisableCalculation(1)
            .from(nistBuilder.getMapOfAllRecords().get(RT1).get(0));
    nistRecordBuilder.withField(
        VER, newFieldText("1000")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(TOT, newFieldText("100")); // Invalid value - contains numbers
    nistRecordBuilder.withField(
        CNT,
        newFieldText("1\u001F2\u001E2\u001F57\u001E10\u001F1")); // Invalid value - missing type 10
    nistRecordBuilder.withField(
        DAT, newFieldText("20201302")); // Invalid value - invalid date yyyymmdd
    nistRecordBuilder.withField(PRY, newFieldText("10")); // Invalid value - <= 9
    nistRecordBuilder.withField(DAI, newFieldText("ABჄ")); // Invalid value - weird char
    nistRecordBuilder.withField(ORI, newFieldText("ABჄ")); // Invalid value - weird char
    nistRecordBuilder.withField(TCN, newFieldText("ABჄ")); // Invalid value - weird char
    nistRecordBuilder.withField(NSR, newFieldText("11.11")); // Invalid value - should be 00.00
    nistRecordBuilder.withField(NTR, newFieldText("11.11")); // Invalid value - should be 00.00
    nistRecordBuilder.withField(
        DCS, newFieldText("0")); // Invalid value - should be at least two items
    nistRecordBuilder.withField(ANM, newFieldText("BBჄ\u001FBCჄ")); // Invalid value - weird char
    nistRecordBuilder.withField(
        GNS, newFieldText("ISOP")); // Invalid value - value does not exist in reference

    NistFile nist = nistBuilder.build();
    NistFile copyNist =
        NistFileFixtures.newNistFileBuilderDisableCalculation()
            .from(nist)
            .removeRecord(RT1)
            .withRecord(RT1, nistRecordBuilder.build())
            .build();

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(copyNist));

    AssertValidator.assertThatErrors(errorsNist)
        .containsErrorWithValue(STD_ERR_VER_RT1, "1000")
        .containsErrorWithValue(STD_ERR_CNT_CONTENT_RT1, "1\u001F2\u001E2\u001F57\u001E10\u001F1")
        .containsErrorWithValue(STD_ERR_TOT_RT1, "100")
        .containsErrorWithValue(STD_ERR_DAT_RT1, "20201302")
        .containsErrorWithValue(STD_ERR_PRY_RT1, "10")
        .containsErrorWithValue(STD_ERR_DAI_RT1, "ABჄ")
        .containsErrorWithValue(STD_ERR_ORI_RT1, "ABჄ")
        .containsErrorWithValue(STD_ERR_TCN_RT1, "ABჄ")
        .containsErrorWithValue(STD_ERR_NSR_NO_RT4_RT1, "11.11")
        .containsErrorWithValue(STD_ERR_NTR_NO_RT4_RT1, "11.11")
        .containsErrorWithValue(STD_ERR_DCS_RT1, "0")
        .containsErrorWithValue(STD_ERR_ANM_DAN_RT1, "BBჄ\u001FBCჄ")
        .containsErrorWithValue(STD_ERR_ANM_OAN_RT1, "BBჄ\u001FBCჄ")
        .containsErrorWithValue(STD_ERR_GNS_RT1, "ISOP");
  }

  @Test
  void validate_should_return_multiple_items_with_record4_and_invalid_fields_in_record1()
      throws IOException {
    // Given
    NistRecordBuilder nistRecordBuilder = record1Cas3_full_Record();
    nistRecordBuilder.withField(VER, newFieldText("0500")); // Valid
    nistRecordBuilder.withField(
        CNT, newFieldText("1\u001F2\u001E2\u001F57\u001E4\u001F1")); // Valid
    nistRecordBuilder.withField(
        NSR, newFieldText("1111")); // Invalid value - <= invalid format XX.XX
    nistRecordBuilder.withField(
        NTR, newFieldText("1111")); // Invalid value - <= invalid format XX.XX
    NistFile nist =
        nistBuilder
            .removeRecord(RT1)
            .withRecord(RT1, nistRecordBuilder.build())
            .withRecord(RT4, record4Cas2_with_real_image_WSQ_Record1().build())
            .build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(nist));

    AssertValidator.assertThatErrors(errorsNist)
        .containsError(STD_ERR_NSR_WITH_RT4_RT1)
        .containsError(STD_ERR_NTR_WITH_RT4_RT1);
  }
}
