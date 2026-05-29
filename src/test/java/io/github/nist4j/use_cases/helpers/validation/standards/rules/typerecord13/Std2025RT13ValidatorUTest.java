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
import static io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum.*;
import static io.github.nist4j.fixtures.CharacterFixtures.musicCharInUnicode;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.fixtures.Record13Fixtures.record13Cas2_EJI_Record;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromItems;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListOfList;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.enums.ref.NistRefColorSpaceEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgeCaptureTechEnum;
import io.github.nist4j.fixtures.CharacterFixtures;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.builders.records.RT13LatentImageDataNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import java.util.List;
import org.junit.jupiter.api.Test;

public class Std2025RT13ValidatorUTest {
  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;
  private final Std2025RT13Validator validator =
      new Std2025RT13Validator(DEFAULT_OPTIONS_FOR_VALIDATION);
  private static final String HEXA64 = CharacterFixtures.repeat("00FF11EE", 8);

  @Test
  void validate_should_return_list_with_errors_with_invalid_values_in_record13() {
    // Given
    NistRecordBuilder nistRecordBuilder = record13Cas2_EJI_Record();
    nistRecordBuilder.withField(FGP, newSubfieldsFromItems("330", "20")); // 330 too big integer
    nistRecordBuilder.withField(IDC, newFieldText("100")); // 100 too big integer
    // Invalid value - value is not allowed for this standard
    nistRecordBuilder.withField(IMP, newFieldText(44));
    nistRecordBuilder.withField(LCD, newFieldText("20009090")); // wrong date
    nistRecordBuilder.withField(HLL, newFieldText("1A00000")); // Invalid value - not  numerical
    nistRecordBuilder.withField(VLL, newFieldText("100000")); // Invalid value - too long
    // Invalid value - value does not exist in reference
    nistRecordBuilder.withField(SLC, newFieldText("3"));
    nistRecordBuilder.withField(THPS, newFieldText("-1"));
    nistRecordBuilder.withField(TVPS, newFieldText("-1"));
    nistRecordBuilder.withField(CGA, newFieldText("99"));
    nistRecordBuilder.withField(BPX, newFieldText("A"));
    nistRecordBuilder.withField(SHPS, newFieldText("1234567"));
    nistRecordBuilder.withField(SVPS, newFieldText("1234567"));
    nistRecordBuilder.withField(COM, newFieldText("ABჄ"));
    nistRecordBuilder.withField(
        LQM,
        newSubfieldsFromListOfList( // Invalid value -  QVU does not exist in reference
            asList(asList("1", "101", "0000", "1"), asList("9", "1", "0000", "1"))));
    nistRecordBuilder.removeField(DATA); // DATA field is mandatory
    NistRecord nistRecord = nistRecordBuilder.build();

    // When
    List<NistValidationError> errorsNist = validator.validate(nistRecord).getErrors();

    // Then
    AssertValidator.assertThatErrors(errorsNist)
        .containsInvalidFieldWithValue(FGP, "330\u001F20")
        .containsInvalidFieldWithValue(IDC, "100")
        .containsInvalidFieldWithValue(IMP, "44")
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
        .containsInvalidSubfieldWithValue(LQM, "QVU", "101")
        .containsInvalidFields(DATA);
  }

  @Test
  void checkForCGAField_should_validate_the_field() {
    // Given
    NistRecord okRecord =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.CGA, newFieldText(PNG.getCode()))
            .build();

    NistRecord deprecatedWSQ20Record =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.CGA, newFieldText(WSQ20.getCode()))
            .build();

    NistRecord okRecord_with_PNM =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.CGA, newFieldText(PNM.getCode()))
            .build();

    NistRecord deprecatedJPEGBRecord =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.CGA, newFieldText(JPEGB.getCode()))
            .build();

    NistRecord badRecordCauseEmpty =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.CGA, newFieldText(""))
            .build();

    NistRecord badRecordCauseMissing =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.IDC, newFieldText(1))
            .build();

    NistRecord badRecordCauseGIFIsNotSupported =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.CGA, newFieldText("GIF"))
            .build();

    Validator<NistRecord> validator =
        new Std2025RT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldCGA13_011();
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(okRecord_with_PNM).isValid()).isTrue();

    assertThat(validator.validate(deprecatedWSQ20Record).isValid()).isFalse();
    assertThat(validator.validate(deprecatedJPEGBRecord).isValid()).isFalse();
    assertThat(validator.validate(badRecordCauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordCauseEmpty).isValid()).isFalse();
    assertThat(validator.validate(badRecordCauseGIFIsNotSupported).isValid()).isFalse();
  }

  @Test
  void checkForFieldCSP13_021_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT13Validator() {
          @Override
          public void rules() {
            checkForFieldCSP13_021();
          }
        };
    NistRecord rt13_with_CSP_missing_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt13_with_CSP_with_BPX16_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(BPX, newFieldText(16))
            .withField(CSP, newFieldText(NistRefColorSpaceEnum.GRAY.getCode()))
            .build();
    NistRecord rt13_with_no_CSP_but_BPX8_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(BPX, newFieldText(8))
            .build();
    NistRecord rt13_with_no_CSP_but_BPX16_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(BPX, newFieldText(16))
            .build();
    NistRecord rt13_with_CSP_with_BPX16_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(BPX, newFieldText(16))
            .withField(CSP, newFieldText("BAD_COLOR"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt13_with_CSP_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_no_CSP_but_BPX8_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_CSP_with_BPX16_ok).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt13_with_no_CSP_but_BPX16_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_CSP_with_BPX16_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldLQM13_024() {
    NistRecord okRecordCanBeAbsent =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS).build();

    NistRecord okRecordLQMIsValid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                LQM,
                newSubfieldsFromListOfList(
                    asList(asList("0", "90", "0000", "1"), asList("19", "95", "FFFF", "65535"))))
            .build();

    NistRecord okRecordLQMIsValid2 =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                LQM, newSubfieldsFromListOfList(singletonList(asList("0", "90", "0000", "1"))))
            .build();

    NistRecord badRecordCauseMustBe4items =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(LQM, newSubfieldsFromListOfList(singletonList(asList("0", "90", "0000"))))
            .build();

    NistRecord okRecordWith7items =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                LQM,
                newSubfieldsFromListOfList(
                    singletonList(asList("0", "90", "0000", "1", "ç", "ç", HEXA64))))
            .build();

    NistRecord badRecordCauseBadFPGNumber =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("99", "90", "0000", "1"))))
            .build();

    NistRecord badRecordCauseNotF2Number =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("0", "A", "0000", "1"))))
            .build();

    NistRecord badRecordCauseNotF3Number =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("0", "90", "A", "1"))))
            .build();

    NistRecord badRecordCauseNotF4Number =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("0", "90", "0000", "A"))))
            .build();

    NistRecord badRecordCauseNotF7length =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(
                    singletonList(asList("0", "90", "0000", "A", "ç", "ç", "00FF"))))
            .build();

    Validator<NistRecord> validator2025 =
        new Std2025RT13Validator() {
          @Override
          public void rules() {
            checkForFieldLQM13_024();
          }
        };

    // When
    // Then
    assertThat(validator2025.validate(okRecordCanBeAbsent).isValid()).isTrue();
    assertThat(validator2025.validate(okRecordLQMIsValid).isValid()).isTrue();
    assertThat(validator2025.validate(okRecordLQMIsValid2).isValid()).isTrue();
    assertThat(validator2025.validate(okRecordWith7items).isValid()).isTrue();

    assertThat(validator2025.validate(badRecordCauseMustBe4items).isValid()).isFalse();
    assertThat(validator2025.validate(badRecordCauseBadFPGNumber).isValid()).isFalse();
    assertThat(validator2025.validate(badRecordCauseNotF2Number).isValid()).isFalse();
    assertThat(validator2025.validate(badRecordCauseNotF3Number).isValid()).isFalse();
    assertThat(validator2025.validate(badRecordCauseNotF4Number).isValid()).isFalse();
    assertThat(validator2025.validate(badRecordCauseNotF7length).isValid()).isFalse();
  }

  @Test
  void checkForFieldFQC13_029_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT13Validator() {
          @Override
          public void rules() {
            checkForFieldFQC13_029();
          }
        };
    NistRecord rt13_with_FQC_missing_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt13_with_FQC_required_params_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(FQC, newSubfieldsFromItems("12", "1.123", "0A9F", "123"))
            .build();
    NistRecord rt13_with_FQC_all_params_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                FQC, newSubfieldsFromItems("12", "1.123", "0A9F", "123", "ç ok", "€ ok", HEXA64))
            .build();
    NistRecord rt13_with_FQC_empty_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(FQC, newFieldText(""))
            .build();
    NistRecord rt13_with_FQC_param1_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                FQC, newSubfieldsFromItems("123", "1.123", "0A9F", "123", "ç ok", "€ ok", HEXA64))
            .build();
    NistRecord rt13_with_FQC_param2_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(FQC, newSubfieldsFromItems("12", "à", "0A9F", "123", "ç ok", "€ ok", HEXA64))
            .build();
    NistRecord rt13_with_FQC_param3_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                FQC, newSubfieldsFromItems("12", "1.123", "00", "123", "ç ok", "€ ok", HEXA64))
            .build();
    NistRecord rt13_with_FQC_param4_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                FQC, newSubfieldsFromItems("12", "1.123", "0A9F", "0", "ç ok", "€ ok", HEXA64))
            .build();
    NistRecord rt13_with_FQC_param7_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                FQC, newSubfieldsFromItems("12", "1.123", "0A9F", "123", "ç ok", "€ ok", "0A9F"))
            .build();
    NistRecord rt13_with_FQC_too_many_params_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(
                FQC,
                newSubfieldsFromItems("12", "1.123", "0A9F", "123", "ç ok", "€ ok", HEXA64, "1"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt13_with_FQC_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_FQC_required_params_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_FQC_all_params_ok).isValid()).isTrue();

    // expected failed tests
    assertThat(testValidator.validate(rt13_with_FQC_empty_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_FQC_too_many_params_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_FQC_param1_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_FQC_param2_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_FQC_param3_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_FQC_param4_invalid).isValid()).isFalse();
    assertThat(testValidator.validate(rt13_with_FQC_param7_invalid).isValid()).isFalse();
  }

  @Test
  void checkForFieldBRI10_199_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT13Validator() {
          @Override
          public void rules() {
            checkForFieldBRI13_199();
          }
        };
    NistRecord rt13_with_BRI_missing_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt13_with_BRI_empty_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.BRI, newFieldText(""))
            .build();
    NistRecord rt13_with_BRI_unicode_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.BRI, newFieldText("unicode:" + musicCharInUnicode()))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt13_with_BRI_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_BRI_empty_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_BRI_unicode_ok).isValid()).isTrue();

    // expected failed tests
  }

  @Test
  void checkForFieldFCT13_901_should_validate() {
    // Given
    AbstractValidator<NistRecord> testValidator =
        new Std2025RT13Validator() {
          @Override
          public void rules() {
            checkForFieldFCT13_901();
          }
        };
    NistRecord rt13_with_FCT_missing_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS).build();
    NistRecord rt13_with_FCT_missing_when_IMP_not_43_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.IMP, newFieldText("2"))
            .build();
    NistRecord rt13_with_FCT_present_when_IMP_43_ok =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.IMP, newFieldText("43"))
            .withField(
                RT13FieldsEnum.FCT,
                newFieldText(NistRefFrictionRidgeCaptureTechEnum.DIRECT_PRESS.getCode()))
            .build();
    NistRecord rt13_with_FCT_missing_but_IMP_43_invalid =
        new RT13LatentImageDataNistRecordBuilderImpl(NIST_OPTIONS)
            .withField(RT13FieldsEnum.IMP, newFieldText("43"))
            .build();

    // When
    // expected ok tests
    assertThat(testValidator.validate(rt13_with_FCT_missing_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_FCT_missing_when_IMP_not_43_ok).isValid()).isTrue();
    assertThat(testValidator.validate(rt13_with_FCT_present_when_IMP_43_ok).isValid()).isTrue();

    // expected failed tests
    ValidationResult validate = testValidator.validate(rt13_with_FCT_missing_but_IMP_43_invalid);
    assertThat(validate.isValid()).isFalse();
    assertThat(validate.getErrors().get(0).getMessage())
        .isEqualTo("13.901 FCT is mandatory depending to 'IMP' should be in collection");
  }
}
