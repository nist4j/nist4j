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

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LEN_RT10;
import static io.github.nist4j.test_utils.AssertValidator.*;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import org.junit.jupiter.api.Test;

class Std2015RT10ValidatorUTest {
  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;

  @Test
  void nistFormat_should_be_2015() {
    assertThat(new Std2015RT10Validator().getStandard().getLabel()).contains("2015");
  }

  @Test
  void rules_should_validate_LEN() {
    Std2015RT10Validator testValidator = new Std2015RT10Validator();

    NistRecord rt10_with_LEN_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_LEN_present =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.LEN, newFieldText("1234"))
            .build();

    assertThat(testValidator.validate(rt10_with_LEN_missing))
        .matches(isNotValid())
        .matches(errorsContainsMessage(STD_ERR_LEN_RT10.getMessage()));
    assertThat(testValidator.validate(rt10_with_LEN_present))
        .matches(isNotValid())
        .doesNotMatch(errorsContainsMessage(STD_ERR_LEN_RT10.getMessage()));
  }

  @Test
  void checkForFieldPID10_048_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2015RT10Validator() {
          @Override
          public void rules() {
            checkForFieldPID10_048();
          }
        };
    NistRecord rt10_with_PID_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_PID_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("à白23456RTVBYNfghjn"))
            .build();
    NistRecord rt10_with_PID_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("à白23456RTVBYNfghjn", "B"))
            .build();
    NistRecord rt10_with_PID_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.PID,
                newSubfieldsFromListOfList(asList(asList("A", "B"), asList("C", "D"))))
            .build();

    NistRecord rt10_with_PID_bad_too_many_items =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("A", "B", "C"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_PID_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_valid_2).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_valid_3).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_PID_bad_too_many_items).isValid()).isFalse();
  }

  @Test
  void checkForFieldEFR10_994_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2015RT10Validator() {
          @Override
          public void rules() {
            checkForFieldEFR10_994();
          }
        };
    NistRecord rt10_with_EFR_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_EFR_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.EFR, newFieldText("à白23456RTVBYNfghjn"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_EFR_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_EFR_valid_1).isValid()).isTrue();

    // expected failed tests
  }
}
