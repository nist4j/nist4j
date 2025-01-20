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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord6;

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_DATA_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_FGP_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_GCA_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_HLL_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IDC_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IMP_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_ISR_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LEN_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_VLL_RT6;
import static io.github.nist4j.test_utils.AssertValidator.assertThatErrors;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT6FieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT6HighResolutionBinaryFingerprintNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Std2013RT6ValidatorUTest {
  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;
  private final Std2013RT6Validator validator = new Std2013RT6Validator(NIST_OPTIONS);
  private final NistValidationErrorMapper mapper = new NistValidationErrorMapper();

  @Test
  void validate_should_return_list_with_errors_with_missing_mandatory_field_in_record() {
    // Given
    NistRecord rt6 =
        new RT6HighResolutionBinaryFingerprintNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(99, newFieldText("1"))
            .build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(rt6));

    // Then
    assertThatErrors(errorsNist)
        .containsError(STD_ERR_LEN_RT6)
        .containsError(STD_ERR_IDC_RT6)
        .containsError(STD_ERR_IMP_RT6)
        .containsError(STD_ERR_FGP_RT6)
        .containsError(STD_ERR_ISR_RT6)
        .containsError(STD_ERR_HLL_RT6)
        .containsError(STD_ERR_VLL_RT6)
        .containsError(STD_ERR_GCA_RT6)
        .containsError(STD_ERR_DATA_RT6);
  }

  @Test
  void validate_should_validate_ok_rt6() {
    // Given
    NistRecord rt6 =
        new RT6HighResolutionBinaryFingerprintNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT6FieldsEnum.LEN, newFieldText(123))
            .withField(RT6FieldsEnum.IDC, newFieldText(1))
            .withField(RT6FieldsEnum.IMP, newFieldText(1))
            .withField(
                RT6FieldsEnum.FGP,
                DataTextBuilder.newSubfieldsFromItems(
                    asList("0", "255", "255", "255", "255", "255")))
            .withField(RT6FieldsEnum.ISR, newFieldText(1))
            .withField(RT6FieldsEnum.HLL, newFieldText(1))
            .withField(RT6FieldsEnum.VLL, newFieldText(1))
            .withField(RT6FieldsEnum.GCA, newFieldText(1))
            .withField(RT6FieldsEnum.DATA, newFieldImage(new byte[] {1, 2, 3, 4}))
            .build();

    // When
    List<NistValidationError> errorsNist = mapper.fromValidationResult(validator.validate(rt6));

    // Then
    assertThat(errorsNist).isEmpty();
  }
}
