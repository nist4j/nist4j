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
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2011;
import static io.github.nist4j.enums.records.RT13FieldsEnum.FGP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LQM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.PPC;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.EJI_OR_TIPS;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.LEFT_4_FINGERTIPS;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.UNKNOWN_FINGER;
import static io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum.WSQ20;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.LIVESCAN_PLANTAR;
import static io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum.PLAIN_CONTACT_FINGERPRINT;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListOfList;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromListUsingSplitByRS;
import static io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13.AbstractStdRT13Validator.isQualityOneFingerValid;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.fluentvalidator.Validator;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT13LatentImageDataNistRecordBuilderImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

public class AbstractStdRT13ValidatorUTest {

  private static final NistOptions OPTS = DEFAULT_OPTIONS_FOR_CREATE;

  @Getter
  protected enum FakeError implements INistValidationErrorEnum {
    ERR("Fake error", FakeFieldTypeEnum.F4T);
    private final String message;
    private final String code;
    private final String fieldName;
    private final IFieldTypeEnum fieldTypeEnum;

    FakeError(String message, IFieldTypeEnum defaultFieldsEnum) {
      this.code = this.name();
      this.message = message;
      this.fieldName = defaultFieldsEnum.getCode();
      this.fieldTypeEnum = defaultFieldsEnum;
    }
  }

  @Getter
  @AllArgsConstructor
  protected enum FakeFieldTypeEnum implements IFieldTypeEnum {
    F4T(101, "F4T", "Field for Test", DataTextImmutableImpl.class);

    private final String recordType = "RT0";
    private final int id;
    private final String code;
    private final String description;
    private final Class<? extends Data> typeClass;
  }

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
            checkForFGPField();
          }
        };

    Validator<NistRecord> validator2011 =
        new Std2011RT13Validator() {
          @Override
          public void rules() {
            checkForFGPField();
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
            checkForPPCField();
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

  @Test
  void isQualityOneFingerValid_should_check_field() {
    // Given
    List<String> validUnknownFinger = asList(UNKNOWN_FINGER.getCode(), "90", "0000", "1");
    List<String> validEIJorTIPSFinger = asList(EJI_OR_TIPS.getCode(), "95", "FFFF", "65535");
    List<String> invalidCause2013FPGCode =
        asList(LEFT_4_FINGERTIPS.getCode(), "95", "FFFF", "65535");
    List<String> invalidCauseMissingElements = asList(LEFT_4_FINGERTIPS.getCode(), "95", "FFFF");
    // When
    // Then
    assertThat(isQualityOneFingerValid(validUnknownFinger, ANSI_NIST_ITL_2011)).isTrue();
    assertThat(isQualityOneFingerValid(validEIJorTIPSFinger, ANSI_NIST_ITL_2011)).isTrue();
    assertThat(isQualityOneFingerValid(invalidCause2013FPGCode, ANSI_NIST_ITL_2007)).isFalse();
    assertThat(isQualityOneFingerValid(invalidCauseMissingElements, ANSI_NIST_ITL_2011)).isFalse();
  }

  @Test
  void checkForLQMField() {
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
            checkForLQMField();
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
            .withField(RT13FieldsEnum.IMP, newFieldText(PLAIN_CONTACT_FINGERPRINT.getCode()))
            .build();

    NistRecord badRecordValidSince2011 =
        new RT13LatentImageDataNistRecordBuilderImpl(OPTS)
            .withField(RT13FieldsEnum.IMP, newFieldText(LIVESCAN_PLANTAR.getCode()))
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
            checkForIMPField();
          }
        };

    Validator<NistRecord> validator2011 =
        new Std2011RT13Validator(OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForIMPField();
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
            checkForCGAField();
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
