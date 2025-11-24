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

import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import org.junit.jupiter.api.Test;

class Std2013RT10ValidatorUTest {
  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;

  @Test
  void nistFormat_should_be_2013() {
    assertThat(new Std2013RT10Validator().getStandard().getLabel()).contains("2013");
  }

  @Test
  void checkForFieldSUB10_046_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2013RT10Validator() {
          @Override
          public void rules() {
            checkForFieldSUB10_046();
          }
        };
    NistRecord rt10_with_SUB_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_SUB_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SUB, newSubfieldsFromItems("X"))
            .build();
    NistRecord rt10_with_SUB_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SUB, newSubfieldsFromItems("X", "1"))
            .build();
    NistRecord rt10_with_SUB_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SUB, newSubfieldsFromItems("X", "1", "1"))
            .build();

    NistRecord rt10_with_SUB_bad_format1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SUB, newFieldText("BAD"))
            .build();
    NistRecord rt10_with_SUB_bad_too_high =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SUB, newSubfieldsFromItems("256", "1"))
            .build();
    NistRecord rt10_with_SUB_bad_numbers =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SUB, newSubfieldsFromItems("X", "1", "1", "1"))
            .build();
    NistRecord rt10_with_SUB_bad_unique =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.SUB,
                newSubfieldsFromListOfList(asList(asList("A", "1"), asList("X", "2"))))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_SUB_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SUB_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SUB_valid_2).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SUB_valid_3).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_SUB_bad_format1).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SUB_bad_numbers).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SUB_bad_too_high).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SUB_bad_unique).isValid()).isFalse();
  }

  @Test
  void checkForFieldCON10_047_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2013RT10Validator() {
          @Override
          public void rules() {
            checkForFieldCON10_047();
          }
        };
    NistRecord rt10_with_CON_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_CON_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CON, newSubfieldsFromItems("à白23456RTVBYNfghjn"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_CON_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_CON_valid_1).isValid()).isTrue();

    // expected failed tests
  }

  @Test
  void checkForFieldPID10_048_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2013RT10Validator() {
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
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("2345.54"))
            .build();
    NistRecord rt10_with_PID_valid_2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("2345_54", "B"))
            .build();
    NistRecord rt10_with_PID_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.PID,
                newSubfieldsFromListOfList(asList(asList("1", "B"), asList("2", "D"))))
            .build();

    NistRecord rt10_with_PID_bad_too_many_items =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("2", "B", "C"))
            .build();
    NistRecord rt10_with_PID_bad_format_only_in_std2015 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newSubfieldsFromItems("à白23456RTVBYNfghjn", "B", "C"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_PID_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_valid_2).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_valid_3).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_PID_bad_too_many_items).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_PID_bad_format_only_in_std2015).isValid())
        .isFalse();
  }

  @Test
  void checkForFieldCID10_049_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2013RT10Validator() {
          @Override
          public void rules() {
            checkForFieldCID10_049();
          }
        };
    NistRecord rt10_with_CID_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_CID_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CID, newSubfieldsFromItems("1"))
            .build();
    NistRecord rt10_with_CID_valid_all =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.CID,
                newSubfieldsFromItems(
                    "1", // LPW
                    "2", // LPH
                    "3", // PHW
                    "4", // PHH
                    "E!", // ULCL
                    "E!", // LLCL
                    "A", // LCLD
                    "白", // LPCT
                    "-1", // LPPL
                    "白", // LPPT
                    "0", // LPSL
                    "白", // LPST
                    "1", // LPMC
                    "白", // LPMT
                    "白", // FHDT
                    "白", // LPDT
                    "白", // LPMC
                    "白" // LPAT
                    ))
            .build();

    NistRecord rt10_with_CID_bad_length =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CID, newSubfieldsFromItems("12345"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_CID_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_CID_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_CID_valid_all).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_CID_bad_length).isValid()).isFalse();
  }

  @Test
  void checkForFieldVID10_050_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2013RT10Validator() {
          @Override
          public void rules() {
            checkForFieldVID10_050();
          }
        };
    NistRecord rt10_with_VID_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_VID_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.VID, newSubfieldsFromItems("ABC"))
            .build();
    NistRecord rt10_with_VID_valid_all =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.VID,
                newSubfieldsFromItems(
                    "ABC", // VIVC
                    "白", // VIDT
                    "白" // VICD
                    ))
            .build();

    NistRecord rt10_with_VID_bad_length =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.VID, newSubfieldsFromItems("12345"))
            .build();
    NistRecord rt10_with_VID_bad_repeated =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.VID,
                newSubfieldsFromListOfList(
                    asList(asList("ABC", "2", "3"), asList("DEF", "4", "5"))))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_VID_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_VID_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_VID_valid_all).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_VID_bad_length).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_VID_bad_repeated).isValid()).isFalse();
  }

  @Test
  void checkForFieldRSP10_051_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2013RT10Validator() {
          @Override
          public void rules() {
            checkForFieldRSP10_051();
          }
        };
    NistRecord rt10_with_RSP_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_RSP_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.RSP, newSubfieldsFromItems("AB"))
            .build();
    NistRecord rt10_with_RSP_valid_all =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.RSP,
                newSubfieldsFromItems(
                    "AB", // VIVC
                    "白", // RSPT
                    "白" // VICD
                    ))
            .build();

    NistRecord rt10_with_RSP_bad_length =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.RSP, newSubfieldsFromItems("ABCDE"))
            .build();
    NistRecord rt10_with_RSP_bad_repeated =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.RSP,
                newSubfieldsFromListOfList(asList(asList("AB", "2", "3"), asList("CD", "4", "5"))))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_RSP_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_RSP_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_RSP_valid_all).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_RSP_bad_length).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_RSP_bad_repeated).isValid()).isFalse();
  }
}
