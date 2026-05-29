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

import static io.github.nist4j.enums.records.RT13FieldsEnum.*;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.PLAIN_CONTACTLESS_MOVING_SUBJECT;
import static io.github.nist4j.fixtures.Record13Fixtures.record13Cas2_EJI_Record;
import static io.github.nist4j.fixtures.Record13Fixtures.record13_empty;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListOfList;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.test_utils.AssertValidator;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Std2013RT13ValidatorUTest {

  private final Std2013RT13Validator validator =
      new Std2013RT13Validator(DEFAULT_OPTIONS_FOR_VALIDATION);

  @Test
  void validate_should_return_list_with_errors_with_invalid_values_in_record13() {
    // Given
    NistRecordBuilder nistRecordBuilder = record13Cas2_EJI_Record();
    nistRecordBuilder.withField(FGP, newFieldText("330" + SEP_US + "20")); // 330 too big integer
    nistRecordBuilder.withField(IDC, newFieldText("100")); // 100 too big integer
    nistRecordBuilder.withField(
        IMP,
        newFieldText(
            PLAIN_CONTACTLESS_MOVING_SUBJECT
                .getCode())); // Invalid value - value is not allowed for this standard
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
        newSubfieldsFromListOfList(
            asList( // Invalid value -  QVU does not exist in reference
                asList("1", "101", "0000", "1"), asList("9", "1", "0000", "1"))));
    nistRecordBuilder.removeField(DATA); // DATA field is mandatory
    NistRecord nistRecord = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist = validator.validate(nistRecord).getErrors();

    // Then
    AssertValidator.assertThatErrors(errorsNist)
        .containsInvalidFieldWithValue(FGP, "330\u001F20")
        .containsInvalidFieldWithValue(IDC, "100")
        .containsInvalidFieldWithValue(IMP, "42")
        .containsInvalidFieldWithValue(LCD, "20009090")
        .containsInvalidFieldWithValue(HLL, "1A00000")
        .containsInvalidFieldWithValue(VLL, "100000")
        .containsInvalidFieldWithValue(SLC, "3")
        .containsInvalidFieldWithValue(THPS, "-1")
        .containsInvalidFieldWithValue(TVPS, "-1")
        .containsInvalidFieldWithValue(CGA, "99")
        .containsInvalidFieldWithValue(BPX, "A")
        .containsInvalidFieldWithValue(SHPS, "1234567")
        .containsInvalidFieldWithValue(SVPS, "1234567")
        .containsInvalidFieldWithValue(COM, "ABჄ")
        .containsInvalidFieldWithValue(
            LQM, "1\u001F101\u001F0000\u001F1\u001E9\u001F1\u001F0000\u001F1")
        .containsInvalidFields(DATA);
  }

  @ParameterizedTest
  @CsvSource({
    "'', success, optional field",
    "'IN\u001Fç\u001Fç', success, valid case RSF not used",
    "'\u001F\u001F\u001Fç', success, valid case RSF is used",
    "'IN', error, missing params",
    "'INCH\u001Fç\u001Fç', error, param1 not in collection",
    "'IN\u001F\u001Fç', error, param2 should not be empty",
    "'IN\u001Fç\u001F', error, param3 should not be empty",
    "'IN\u001Fç\u001Fç\u001Fç', error, param4 should be empty",
  })
  void checkForFieldRSP13_018_should_validate_the_field(
      String fieldRSPValue, String expectedResult, String reason) {
    // Given
    NistRecordBuilder rt13Builder = record13_empty();
    if (!isEmpty(fieldRSPValue)) {
      rt13Builder.withField(RSP, newFieldText(fieldRSPValue));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt13Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(RSP);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(RSP).containsValidMsg(RSP);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'', success, optional field",
    "'RULER\u001F0.03\u001FIN\u001F123\u001F123\u001F300\u001F300\u001Fç', success, valid all params",
    "'RULER\u001F0.03\u001FIN\u001F123\u001F123\u001F300\u001F300\u001Fç\u001Fbad', error, too many params",
    "'BAD\u001F0.03\u001FIN\u001F123\u001F123\u001F300\u001F300\u001Fç', error, invalid param1",
    "'RULER\u001FBAD\u001FIN\u001F123\u001F123\u001F300\u001F300\u001Fç', error, invalid param2",
    "'RULER\u001F0.03\u001FBAD\u001F123\u001F123\u001F300\u001F300\u001Fç', error, invalid param3",
    "'RULER\u001F0.03\u001FIN\u001FBAD\u001F123\u001F300\u001F300\u001Fç', error, invalid param4",
    "'RULER\u001F0.03\u001FIN\u001F123\u001FBAD\u001F300\u001F300\u001Fç', error, invalid param5",
    "'RULER\u001F0.03\u001FIN\u001F123\u001F123\u001FBAD\u001F300\u001Fç', error, invalid param6",
    "'RULER\u001F0.03\u001FIN\u001F123\u001F123\u001F300\u001FBAD\u001F', error, invalid param7",
    "'FORM\u001F0.03\u001FIN\u001F123\u001F123\u001F300\u001F300\u001Fç', success, when FORM params are optional",
    "'FORM\u001F\u001F\u001F\u001F\u001F\u001F\u001Fç', success, when FORM params are optional",
    "'FLATBED\u001F\u001F\u001F\u001F\u001F\u001F\u001Fç', success, when not FORM,RULER params are forbidden",
    "'FLATBED\u001F0.03\u001F\u001F\u001F\u001F\u001F\u001Fç', error, when not FORM,RULER params are forbidden",
  })
  void checkForFieldREM13_019_should_validate_the_field(
      String fieldREMValue, String expectedResult, String reason) {
    // Given
    NistRecordBuilder rt13Builder = record13_empty();
    if (!isEmpty(fieldREMValue)) {
      rt13Builder.withField(REM, newFieldText(fieldREMValue));
    }

    // When
    List<NistValidationError> errorsNist = validator.validate(rt13Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(REM);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(REM).containsValidMsg(REM);
    }
  }
}
