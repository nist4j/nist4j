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

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2007;
import static io.github.nist4j.enums.records.RT13FieldsEnum.*;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.EJI_OR_TIPS;
import static io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum.WSQ20;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.*;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.fixtures.Record13Fixtures.record13_empty;
import static io.github.nist4j.use_cases.CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListOfList;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListUsingSplitByRS;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.builders.records.RT13LatentImageDataNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import java.util.List;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AbstractStdRT13ValidatorUTest {

  private static final NistOptions OPTS = DEFAULT_OPTIONS_FOR_CREATE;

  @Test
  void validateFieldFGP_should_validate_the_FPG_field() {
    // Given
    NistRecord okRecord =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newSubfieldsFromListUsingSplitByRS("0", "1", "2", "3", "4", "5"))
            .build();
    NistRecord okRecordWith2subfields =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newSubfieldsFromListUsingSplitByRS("0", "14"))
            .build();
    NistRecord recordWithFGP16ValidAfter2011 =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newSubfieldsFromListUsingSplitByRS("0", "16"))
            .build();
    NistRecord badRecordBecauseEmptyFGP =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.IDC, newFieldText("1"))
            .build();
    NistRecord recordWithFGP19 =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText("19"))
            .build();

    Validator<NistRecord> validator2007 =
        new Std2007RT13Validator() {
          @Override
          public void rules() {
            checkForFieldFGP13_013();
          }
        };

    Validator<NistRecord> validator2011 =
        new Std2011RT13Validator() {
          @Override
          public void rules() {
            checkForFieldFGP13_013();
          }
        };
    // When
    // Then
    assertThat(validator2007.validate(okRecord).isValid()).isTrue();
    assertThat(validator2007.validate(okRecordWith2subfields).isValid()).isTrue();
    assertThat(validator2011.validate(recordWithFGP16ValidAfter2011).isValid()).isTrue();
    assertThat(validator2007.validate(recordWithFGP19).isValid()).isTrue();

    assertThat(validator2007.validate(badRecordBecauseEmptyFGP).isValid()).isFalse();
    assertThat(validator2007.validate(recordWithFGP16ValidAfter2011).isValid()).isFalse();
  }

  @Test
  void checkForPPCField_should_validate_the_PPC_field() {
    // Given
    NistRecord okRecordCaseEIJPresentPPCCanBeMissing =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText(EJI_OR_TIPS.getCode()))
            .withField(PPC, newFieldText(""))
            .build();

    List<List<String>> okFieldPPC = singletonList(asList("FV1", "NA", "101", "102", "103", "104"));
    NistRecord okRecord_EIJPresent_PPCIsValid =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText(EJI_OR_TIPS.getCode()))
            .withField(PPC, newSubfieldsFromListOfList(okFieldPPC))
            .build();

    NistRecord okRecord_NotEIJ_PPC_MustBeAbsent =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText("15"))
            .build();

    NistRecord badRecord_NotEIJ_PPCMustBeAbsent =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText("15"))
            .withField(PPC, newSubfieldsFromListUsingSplitByRS("0", "1", "2", "3", "4", "5"))
            .build();

    List<List<String>> badFieldPPCBecauseNot6elements =
        singletonList(asList("FV1", "NA", "101", "102", "103"));
    NistRecord badRecordPPCBecauseNot6elements =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText(EJI_OR_TIPS.getCode()))
            .withField(PPC, newSubfieldsFromListOfList(badFieldPPCBecauseNot6elements))
            .build();

    List<List<String>> badFieldPPCBecauseFV5notValid =
        singletonList(asList("FV5", "NA", "101", "102", "103", "104"));
    NistRecord badRecordBecauseFV5notValid =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText(EJI_OR_TIPS.getCode()))
            .withField(PPC, newSubfieldsFromListOfList(badFieldPPCBecauseFV5notValid))
            .build();

    List<List<String>> badFieldPPCBecauseBADnotValid =
        singletonList(asList("FV1", "BAD", "101", "102", "103", "104"));
    NistRecord badRecordBecauseBADnotValid =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(FGP, newFieldText(EJI_OR_TIPS.getCode()))
            .withField(PPC, newSubfieldsFromListOfList(badFieldPPCBecauseBADnotValid))
            .build();

    Validator<NistRecord> validator2007 =
        new Std2007RT13Validator() {
          @Override
          public void rules() {
            checkForFieldPPC13_015();
          }
        };
    // When
    // Then
    assertThat(validator2007.validate(okRecordCaseEIJPresentPPCCanBeMissing).isValid()).isTrue();
    assertThat(validator2007.validate(okRecord_EIJPresent_PPCIsValid).isValid()).isTrue();
    assertThat(validator2007.validate(okRecord_NotEIJ_PPC_MustBeAbsent).isValid()).isTrue();

    assertThat(validator2007.validate(badRecord_NotEIJ_PPCMustBeAbsent).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordPPCBecauseNot6elements).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordBecauseFV5notValid).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordBecauseBADnotValid).isValid()).isFalse();
  }

  @ParameterizedTest
  @CsvSource({
    "'0\u001F90\u001F0000\u001F1', '0', success, unknow finger",
    "'19\u001F95\u001FFFFF\u001F65535', '19', success, unknow finger",
    "'52\u001F95\u001FFFFF\u001F65535', '0', error, finger out of collection",
    "'19\u001F95\u001FFFFF', '0', error, too few params",
    "'0\u001F95\u001FFFFF\u001F65535\u001F1', '0', error, too many params",
  })
  void checkForFieldLQM13_024_should_return_expected_value(
      String fieldLQMValue, String fieldFGPValue, String expectedResult, String reason) {
    // Given
    NistRecordBuilder rt13Builder = record13_empty();
    if (!isEmpty(fieldFGPValue)) {
      rt13Builder.withField(FGP, newFieldText(fieldFGPValue));
    }
    if (!isEmpty(fieldLQMValue)) {
      rt13Builder.withField(LQM, newFieldText(fieldLQMValue));
    }
    Validator<NistRecord> validator =
        new AbstractStdRT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldLQM13_024();
          }

          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }
        };

    // When
    List<NistValidationError> errorsNist = validator.validate(rt13Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(LQM);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(LQM).containsValidMsg(LQM);
    }
  }

  @ParameterizedTest
  @CsvSource({
    "'FV1\u001FNA\u001F101\u001F102\u001F103\u001F104', '19', success, allow when FGP eq 19",
    "'FV1\u001FNA\u001F101\u001F102\u001F103\u001F104', '1', error, must be with FGP eq 19",
    "'', '1', success, dont use when FGP neq 19",
    "'FV1\u001FNA\u001F102\u001F103\u001F104', '19', error, param1 should be FGP",
    "'FV9\u001FF_V_1\u001FNA\u001F102\u001F103\u001F104', '19', error, param2 value out of collection",
    "'FV1\u001FFV1\u001FBAD\u001F102\u001F103\u001F104', '19', error, param3 value should be num",
    "'FV1\u001FFV1\u001F101\u001FBAD\u001F103\u001F104', '19', error, param4 value should be num",
    "'FV1\u001FFV1\u001F101\u001F102\u001FBAD\u001F104', '19', error, param5 value should be num",
    "'FV1\u001FFV1\u001F101\u001F102\u001F103\u001FBAD', '19', error, param6 value should be num",
    "'FV1\u001FFV1\u001F101\u001F102\u001F103\u001F104\u001F105', '19', error, too many params",
    "'', '19', success, can be absent",
  })
  void checkForFieldPPC13_015_should_return_expected_value(
      String fieldPPCValue, String fieldFGPValue, String expectedResult, String reason) {
    // Given
    NistRecordBuilder rt13Builder = record13_empty();
    if (!isEmpty(fieldFGPValue)) {
      rt13Builder.withField(FGP, newFieldText(fieldFGPValue));
    }
    if (!isEmpty(fieldPPCValue)) {
      rt13Builder.withField(PPC, newFieldText(fieldPPCValue));
    }
    Validator<NistRecord> validator =
        new AbstractStdRT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldPPC13_015();
          }

          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }
        };

    // When
    List<NistValidationError> errorsNist = validator.validate(rt13Builder.build()).getErrors();

    // Then
    if ("success".equalsIgnoreCase(expectedResult)) {
      AssertValidator.assertThatErrors(errorsNist).doesNotContainsInvalidFields(PPC);
    } else {
      AssertionsForClassTypes.assertThat(expectedResult).isEqualToIgnoringCase("error");
      AssertValidator.assertThatErrors(errorsNist).containsInvalidFields(PPC).containsValidMsg(PPC);
    }
  }

  @Test
  void checkForFieldLQM13_024() {
    NistRecord okRecordCanBeAbsent =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS).withField(LQM, newFieldText("")).build();

    NistRecord okRecordLQMIsValid =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(
                LQM,
                newSubfieldsFromListOfList(
                    asList(asList("0", "90", "0000", "1"), asList("19", "95", "FFFF", "65535"))))
            .build();

    NistRecord okRecordLQMIsValid2 =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(
                LQM, newSubfieldsFromListOfList(singletonList(asList("0", "90", "0000", "1"))))
            .build();

    NistRecord badRecordCauseMustBe4items =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(LQM, newSubfieldsFromListOfList(singletonList(asList("0", "90", "0000"))))
            .build();

    NistRecord badRecordCauseBadFPGNumber =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_FOR_VALIDATION)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("99", "90", "0000", "1"))))
            .build();

    NistRecord badRecordCauseNotF2Number =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_FOR_VALIDATION)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("0", "A", "0000", "1"))))
            .build();

    NistRecord badRecordCauseNotF3Number =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_FOR_VALIDATION)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("0", "90", "A", "1"))))
            .build();

    NistRecord badRecordCauseNotF4Number =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_FOR_VALIDATION)
            .withField(
                RT13FieldsEnum.LQM,
                newSubfieldsFromListOfList(singletonList(asList("0", "90", "0000", "A"))))
            .build();

    Validator<NistRecord> validator2007 =
        new Std2007RT13Validator() {
          @Override
          public void rules() {
            checkForFieldLQM13_024();
          }
        };

    // When
    // Then
    assertThat(validator2007.validate(okRecordCanBeAbsent).isValid()).isTrue();
    assertThat(validator2007.validate(okRecordLQMIsValid).isValid()).isTrue();
    assertThat(validator2007.validate(okRecordLQMIsValid2).isValid()).isTrue();

    assertThat(validator2007.validate(badRecordCauseMustBe4items).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordCauseBadFPGNumber).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordCauseNotF2Number).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordCauseNotF3Number).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordCauseNotF4Number).isValid()).isFalse();
  }

  @Test
  void checkForIMPField_should_validate_the_field() {
    // Given
    NistRecord okRecord =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.IMP, newFieldText(LATENT_PALM_IMPRESSION.getCode()))
            .build();

    NistRecord badRecordValidSince2011 =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(
                RT13FieldsEnum.IMP, newFieldText(LATENT_UNKNOWN_FRICTION_IMPRESSION.getCode()))
            .build();

    NistRecord badRecordCauseMissing =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.IDC, newFieldText(1))
            .build();

    NistRecord badRecordCauseEmpty =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.IMP, newFieldText(""))
            .build();

    Validator<NistRecord> validator2007 =
        new Std2007RT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldIMP13_003();
          }
        };

    Validator<NistRecord> validator2011 =
        new Std2011RT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldIMP13_003();
          }
        };
    // When
    // Then
    assertThat(validator2007.validate(okRecord).isValid()).isTrue();

    assertThat(validator2007.validate(badRecordCauseMissing).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordCauseEmpty).isValid()).isFalse();
    assertThat(validator2007.validate(badRecordValidSince2011).isValid()).isFalse();
    assertThat(validator2011.validate(badRecordValidSince2011).isValid()).isTrue();
  }

  @Test
  void checkForCGAField_should_validate_the_field() {
    // Given
    NistRecord okRecord =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.CGA, newFieldText(WSQ20.getCode()))
            .build();

    NistRecord badRecordCauseEmpty =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.CGA, newFieldText(""))
            .build();

    NistRecord badRecordCauseMissing =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.IDC, newFieldText(1))
            .build();

    NistRecord badRecordCauseGIFIsNotSupported =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.CGA, newFieldText("GIF"))
            .build();

    Validator<NistRecord> validator =
        new Std2007RT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldCGA13_011();
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();

    assertThat(validator.validate(badRecordCauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordCauseEmpty).isValid()).isFalse();
    assertThat(validator.validate(badRecordCauseGIFIsNotSupported).isValid()).isFalse();
  }
}
