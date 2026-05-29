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

import static io.github.nist4j.enums.RecordTypeEnum.RT10;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_FEC;
import static io.github.nist4j.fixtures.CharacterFixtures.musicCharInUnicode;
import static io.github.nist4j.test_utils.AssertValidator.*;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.fixtures.CharacterFixtures;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.format.ValidationMessage;
import org.junit.jupiter.api.Test;

class Std2025RT10ValidatorUTest {
  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;

  @Test
  void nistFormat_should_be_2025() {
    assertThat(new Std2025RT10Validator().getStandard().getLabel()).contains("2025");
  }

  @Test
  void checkForFieldFEC10_033_should_validate() {
    // Given
    String expectedMsg = ValidationMessage.format(STD_ERR_FEC, RT10, RT10FieldsEnum.FEC);
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldFEC10_033();
          }
        };
    NistRecord rt10_with_FEC_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_FEC_valid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("eyetop", "2", "13", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_param1_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("badvalue", "3", "13", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_param2_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("eyetop", "1", "13", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_param3_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC, newSubfieldsFromItems("eyetop", "3", "-1", "43", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_param3_invalid2 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC,
                newSubfieldsFromItems("eyetop", "3", "1", "1000000", "15", "53"))
            .build();
    NistRecord rt10_with_FEC_param_not_pairs_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FEC,
                newSubfieldsFromItems("eyetop", "3", "13", "43", "15", "53", "13"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FEC_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FEC_valid).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FEC_param1_invalid))
        .matches(isNotValid())
        .matches(errorsNumberIs(1))
        .matches(errorsContainsMessage(expectedMsg));
    assertThat(testValidator.validate(rt10_with_FEC_param2_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_FEC_param3_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_FEC_param3_invalid2).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_FEC_param_not_pairs_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldFSB10_035_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldFSB10_035();
          }
        };
    NistRecord rt10_with_FSB_missing_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt10_with_FSB_all_fields_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FSB,
                newSubfieldsFromItems(
                    "A",
                    "0000",
                    "10",
                    "ç",
                    "ç",
                    "1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF"))
            .build();
    NistRecord rt10_with_FSB_only_required_fields_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FSB, newSubfieldsFromItems("A", "0000", "10"))
            .build();
    NistRecord rt10_with_FSB_empty_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FSB, newFieldText(""))
            .build();
    NistRecord rt10_with_FSB_missing_1_required_fields_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FSB, newSubfieldsFromItems("A", "0000"))
            .build();
    NistRecord rt10_with_FSB_missing_2_required_fields_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.FSB, newSubfieldsFromItems("A"))
            .build();
    NistRecord rt10_with_FSB_bad_fotmat_field_1_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FSB,
                newSubfieldsFromItems(
                    "ç",
                    "0000",
                    "10",
                    "ç",
                    "ç",
                    "1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF"))
            .build();
    NistRecord rt10_with_FSB_bad_fotmat_field_2_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FSB,
                newSubfieldsFromItems(
                    "A",
                    "Z000",
                    "10",
                    "ç",
                    "ç",
                    "1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF"))
            .build();
    NistRecord rt10_with_FSB_bad_fotmat_field_3_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FSB,
                newSubfieldsFromItems(
                    "A",
                    "0000",
                    "0",
                    "ç",
                    "ç",
                    "1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF"))
            .build();
    NistRecord rt10_with_FSB_bad_fotmat_field_6_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FSB,
                newSubfieldsFromItems("A", "0000", "10", "ç", "ç", "1234567890ABCCEF12"))
            .build();
    NistRecord rt10_with_FSB_too_many_fields_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.FSB,
                newSubfieldsFromItems(
                    "A",
                    "0000",
                    "10",
                    "ç",
                    "ç",
                    "1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF1234567890ABCCEF",
                    "1"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_FSB_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FSB_all_fields_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_FSB_only_required_fields_ok).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_FSB_empty_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_missing_1_required_fields_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_missing_2_required_fields_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_bad_fotmat_field_1_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_bad_fotmat_field_2_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_bad_fotmat_field_3_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_bad_fotmat_field_6_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_FSB_too_many_fields_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldTIF10_036_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldCGA10_011();
            checkForFieldTIF10_036();
          }
        };
    NistRecord rt10_with_TIF_missing_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CGA, newFieldText("PNG"))
            .build();
    NistRecord rt10_with_TIF_missing_but_CGA_media_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CGA, newFieldText("MEDIA"))
            .build();
    NistRecord rt10_with_TIF_present_with_CGA_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CGA, newFieldText("MEDIA"))
            .withField(RT10FieldsEnum.TIF, newFieldText("bmp"))
            .build();
    NistRecord rt10_with_TIF_present2_with_CGA_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CGA, newFieldText("MEDIA"))
            .withField(
                RT10FieldsEnum.TIF,
                newSubfieldsFromItems(
                    "bmp", "additional decoding instructions " + musicCharInUnicode()))
            .build();
    NistRecord rt10_with_TIF_empty_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.TIF, newFieldText(""))
            .build();
    NistRecord rt10_with_TIF_bad_format_param1_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.TIF, newFieldText("12"))
            .build();
    NistRecord rt10_with_TIF_bad_format_param2_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.TIF,
                newSubfieldsFromItems("bmp", CharacterFixtures.repeat("1234567890", 101)))
            .build();
    NistRecord rt10_with_TIF_bad_format_param3_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.TIF, newSubfieldsFromItems("bmp", "123", "1234"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_TIF_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_TIF_present_with_CGA_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_TIF_present2_with_CGA_ok).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_TIF_empty_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_TIF_missing_but_CGA_media_invalid).isValid())
        .isFalse();
    assertThat(testValidator.validate(rt10_with_TIF_bad_format_param1_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_TIF_bad_format_param2_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_TIF_bad_format_param3_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldSMD10_042_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldSMD10_042();
          }
        };
    NistRecord rt10_with_SMD_missing =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord rt10_with_SMD_valid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("SCAR"))
            .withField(RT10FieldsEnum.SMD, newFieldText("ZABIBA"))
            .build();
    NistRecord rt10_with_SMD_valid_3 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("TATTOO"))
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("CHEMICAL", "SYMBOL", "FRATERNAL"))
            .build();
    NistRecord rt10_with_SMD_valid_4 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("TATTOO"))
            .withField(
                RT10FieldsEnum.SMD,
                newSubfieldsFromItems("CHEMICAL", "SYMBOL", "FRATERNAL", "Peace ☮"))
            .build();

    NistRecord rt10_with_SMD_bad_format =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newFieldText("SCARY"))
            .build();
    NistRecord rt10_with_SMD_bad_length =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("SCAR", "ABCDEFGHI"))
            .build();
    NistRecord rt10_with_SMD_scar_dont_have_tac_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.IMT, newFieldText("SCAR"))
            .withField(RT10FieldsEnum.SMD, newSubfieldsFromItems("SCAR", "SYMBOL"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_SMD_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_1).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_3).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_SMD_valid_4).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_SMD_bad_format).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SMD_bad_length).isValid()).isFalse();
    assertThat(testValidator.validate(rt10_with_SMD_scar_dont_have_tac_invalid).isValid())
        .isFalse();
  }

  @Test
  void checkForFieldPID10_048_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldPID10_048();
          }
        };
    NistRecord rt10_with_PID_missing_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt10_with_PID_empty_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newFieldText(""))
            .build();
    NistRecord rt10_with_PID_present_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.PID, newFieldText("should be empty"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_PID_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_PID_empty_ok).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_PID_present_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldCID10_049_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldCID10_049();
          }
        };
    NistRecord rt10_with_CID_missing_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt10_with_CID_empty_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CID, newFieldText(""))
            .build();
    NistRecord rt10_with_CID_present_invalid =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.CID, newFieldText("should be empty"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_CID_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_CID_empty_ok).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_CID_present_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldBRI10_199_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
          @Override
          public void rules() {
            checkForFieldBRI10_199();
          }
        };
    NistRecord rt10_with_BRI_missing_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt10_with_BRI_empty_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.BRI, newFieldText(""))
            .build();
    NistRecord rt10_with_BRI_unicode_ok =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT10FieldsEnum.BRI, newFieldText("unicode:" + musicCharInUnicode()))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_BRI_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_BRI_empty_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_BRI_unicode_ok).isValid()).isTrue();

    // expected failed tests
  }

  @Test
  void checkForFieldEFR10_994_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT10Validator() {
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
            .withField(
                RT10FieldsEnum.EFR,
                newSubfieldsFromItems("à白23456RTVBYNfghjn", "à白23456RTVBYNfghjn"))
            .build();
    NistRecord rt10_with_EFR_invalid_1 =
        new RT10FacialSMTImageNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT10FieldsEnum.EFR, newFieldText("à白23456RTVBYNfghjn")) // should have 2 params
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt10_with_EFR_missing).isValid()).isTrue();
    assertThat(testValidator.validate(rt10_with_EFR_valid_1).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt10_with_EFR_invalid_1).isValid()).isFalse();
  }
}
