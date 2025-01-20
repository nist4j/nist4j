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

import static io.github.nist4j.enums.records.RT13FieldsEnum.BPX;
import static io.github.nist4j.enums.records.RT13FieldsEnum.CGA;
import static io.github.nist4j.enums.records.RT13FieldsEnum.COM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.DATA;
import static io.github.nist4j.enums.records.RT13FieldsEnum.FGP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.HLL;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IDC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IMP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LCD;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LQM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SHPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SLC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SVPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.THPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.TVPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.VLL;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.PLAIN_CONTACTLESS_MOVING_SUBJECT;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_BPX_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_CGA_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_COM_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_DATA_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_FGP_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_HLL_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IDC;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IMP_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LCD_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LQM_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SHPS_O_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SLC_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_SVPS_O_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_THPS_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_TVPS_MANDATORY_RT13;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_VLL_MANDATORY_RT13;
import static io.github.nist4j.fixtures.Record13Fixtures.record13Cas2_EJI_Record;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListOfList;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Std2007RT13ValidatorUTest {

  private final Std2007RT13Validator validator = new Std2007RT13Validator();
  private final NistValidationErrorMapper mapper = new NistValidationErrorMapper();

  @Test
  void validate_should_return_list_with_errors_with_invalid_values_in_record13() {
    // Given
    NistRecordBuilder nistRecordBuilder = record13Cas2_EJI_Record();
    nistRecordBuilder.withField(FGP, newFieldText("330" + SEP_US + "20")); // 330 too big integer
    nistRecordBuilder.withField(IDC, newFieldText("100")); // 100 too big integer
    nistRecordBuilder.withField(
        IMP,
        // Invalid value - value is not allowed for this standard
        newFieldText(PLAIN_CONTACTLESS_MOVING_SUBJECT.getCode()));
    nistRecordBuilder.withField(LCD, newFieldText("20009090")); // wrong date
    nistRecordBuilder.withField(HLL, newFieldText("1A00000")); // Invalid value - not  numerical
    nistRecordBuilder.withField(VLL, newFieldText("100000")); // Invalid value - too long
    nistRecordBuilder.withField(
        SLC, newFieldText("3")); // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(THPS, newFieldText("-1"));
    nistRecordBuilder.withField(TVPS, newFieldText("-1"));
    nistRecordBuilder.withField(CGA, newFieldText("99"));
    nistRecordBuilder.withField(BPX, newFieldText("A"));
    nistRecordBuilder.withField(SHPS, newFieldText("1234567"));
    nistRecordBuilder.withField(SVPS, newFieldText("1234567"));
    nistRecordBuilder.withField(COM, newFieldText("ABჄ"));
    nistRecordBuilder.withField(
        LQM,
        // Invalid value -  QVU does not exist in reference
        newSubfieldsFromListOfList(
            asList(asList("1", "101", "0000", "1"), asList("9", "1", "0000", "1"))));
    nistRecordBuilder.removeField(DATA); // DATA field is mandatory
    NistRecord nistRecord = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist =
        mapper.fromValidationResult(validator.validate(nistRecord));

    // Then
    AssertValidator.assertThatErrors(errorsNist)
        .containsErrorWithValue(STD_ERR_FGP_RT13, "330\u001F20")
        .containsErrorWithValue(STD_ERR_IDC, "100")
        .containsErrorWithValue(STD_ERR_IMP_MANDATORY_RT13, "41")
        .containsErrorWithValue(STD_ERR_LCD_RT13, "20009090")
        .containsErrorWithValue(STD_ERR_HLL_MANDATORY_RT13, "1A00000")
        .containsErrorWithValue(STD_ERR_VLL_MANDATORY_RT13, "100000")
        .containsErrorWithValue(STD_ERR_SLC_MANDATORY_RT13, "3")
        .containsErrorWithValue(STD_ERR_THPS_MANDATORY_RT13, "-1")
        .containsErrorWithValue(STD_ERR_TVPS_MANDATORY_RT13, "-1")
        .containsErrorWithValue(STD_ERR_CGA_MANDATORY_RT13, "99")
        .containsErrorWithValue(STD_ERR_BPX_MANDATORY_RT13, "A")
        .containsErrorWithValue(STD_ERR_SHPS_O_RT13, "1234567")
        .containsErrorWithValue(STD_ERR_SVPS_O_RT13, "1234567")
        .containsErrorWithValue(STD_ERR_COM_RT13, "ABჄ")
        .containsErrorWithValue(
            STD_ERR_LQM_RT13, "1\u001F101\u001F0000\u001F1\u001E9\u001F1\u001F0000\u001F1")
        .containsError(STD_ERR_DATA_RT13);
  }
}
