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
package io.github.nist4j.use_cases.helpers.validation.standards.abstracts;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2007;
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2011;
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2013;
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2015;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.DATA;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.FGP;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.GCA;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.HLL;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.IDC;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.IMP;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.ISR;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.LEN;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.VLL;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromItems;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.fluentvalidator.Validator;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.records.DefaultNistTextRecordBuilderImpl;
import org.junit.jupiter.api.Test;

public class AbstractStdRT3to6ValidatorUTest {

  private static final NistOptions NIST_OPTIONS = DEFAULT_OPTIONS_FOR_VALIDATION;
  private static final INistValidationErrorEnum mockError = mock(INistValidationErrorEnum.class);
  private static final RecordTypeEnum mockRT;

  static {
    when(mockError.getFieldTypeEnum()).thenReturn(mock(IFieldTypeEnum.class));

    mockRT = mock(RecordTypeEnum.class);
    when(mockRT.getNumber()).thenReturn(55);
    when(mockRT.getLabel()).thenReturn("Mock RT55");
  }

  @Test
  void checkThatLENisValidForRT3to6_should_valid_integer() {
    // Given
    NistRecord recordOk =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(LEN, newFieldText(1))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseNegatif =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(LEN, newFieldText(-1))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(LEN, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatLENisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOk).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseNegatif).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatIDCisValidForRT3to6_should_be_integer_between_0_to_99() {
    // Given
    NistRecord recordOk =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(IDC, newFieldText(1))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseNegatif =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(IDC, newFieldText(-1))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(IDC, newFieldText("A"))
            .build();
    NistRecord recordBadBecauseToHigh =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(IDC, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatIDCisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOk).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseNegatif).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseToHigh).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatFGPisValidForRT3to6_sould_be_in_collection() {
    // Given
    NistRecord recordOk =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(FGP, newSubfieldsFromItems("1", "255", "255", "255", "255", "255"))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseMissingItem =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(FGP, newSubfieldsFromItems("1", "255", "255", "255", "255"))
            .build();
    NistRecord recordOnlyInvalidInStd2007 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(FGP, newSubfieldsFromItems("15", "255", "255", "255", "255"))
            .build();

    Validator<NistRecord> validator2007 =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatFGPisValidForRT3to6(mockError);
          }
        };

    Validator<NistRecord> validator2011 =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2011;
          }

          @Override
          public void rules() {
            checkThatFGPisValidForRT3to6(mockError);
          }
        };

    Validator<NistRecord> validator2013 =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2013;
          }

          @Override
          public void rules() {
            checkThatFGPisValidForRT3to6(mockError);
          }
        };

    Validator<NistRecord> validator2015 =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2015;
          }

          @Override
          public void rules() {
            checkThatFGPisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    for (Validator validator : asList(validator2007, validator2011, validator2013, validator2015)) {
      assertThat(validator.validate(recordOk).getErrors()).isEmpty();
      assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
      assertThat(validator.validate(recordBadBecauseMissingItem).getErrors()).isNotEmpty();
    }
    for (int fgp = 0; fgp <= 100; fgp++) {
      NistRecord recordWithRangeValues =
          new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
              .withField(
                  FGP, newSubfieldsFromItems(valueOf(fgp), "255", "255", "255", "255", "255"))
              .build();
      assertThat(validator2007.validate(recordWithRangeValues).getErrors().isEmpty())
          .as("In Std2007 must validate if 0 <= fgp <= 14 found " + fgp)
          .isEqualTo(fgp <= 14);
      assertThat(validator2011.validate(recordWithRangeValues).getErrors().isEmpty())
          .as("In Std2011 must validate if 0 <= fgp <= 15 found " + fgp)
          .isEqualTo(fgp <= 15);
      assertThat(validator2013.validate(recordWithRangeValues).getErrors().isEmpty())
          .as("In Std2013 must validate if 0 <= fgp <= 15 found " + fgp)
          .isEqualTo(fgp <= 15);
      assertThat(validator2015.validate(recordWithRangeValues).getErrors().isEmpty())
          .as("In Std2015 must validate if 0 <= fgp <= 15 found " + fgp)
          .isEqualTo(fgp <= 15);
    }
    assertThat(validator2007.validate(recordOnlyInvalidInStd2007).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatIMPisValidForRT3to6_sould_be_numeric_between_0_to_29() {
    // Given
    NistRecord recordOk =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(IMP, newFieldText(1))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(IMP, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatIMPisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOk).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();

    for (int imp = -1; imp <= 100; imp++) {
      NistRecord recordWithRangeValues =
          new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
              .withField(IMP, newFieldText(imp))
              .build();
      assertThat(validator.validate(recordWithRangeValues).getErrors().isEmpty())
          .as("must validate if 0 <= fgp <= 29 found " + imp)
          .isEqualTo(imp <= 29 && imp >= 0);
    }
  }

  @Test
  void checkThatISRisValidForRT3to6_should_be_0_or_1() {
    // Given
    NistRecord recordOkBecause0 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(ISR, newFieldText(0))
            .build();
    NistRecord recordOkBecause1 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(ISR, newFieldText(1))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecause2 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(ISR, newFieldText(2))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(ISR, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatISRisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOkBecause0).getErrors()).isEmpty();
    assertThat(validator.validate(recordOkBecause1).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecause2).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatHLLisValidForRT3to6_should_be_numeric_between_1_to_99999() {
    // Given
    NistRecord recordOkBecause1 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(HLL, newFieldText(1))
            .build();
    NistRecord recordOkBecause99999 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(HLL, newFieldText(99999))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseTooLow =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(HLL, newFieldText(0))
            .build();
    NistRecord recordBadBecauseTooHigh =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(HLL, newFieldText(99999 + 1))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(HLL, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatHLLisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOkBecause1).getErrors()).isEmpty();
    assertThat(validator.validate(recordOkBecause99999).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseTooHigh).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseTooLow).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatVLLisValidForRT3to6_should_be_numeric_between_1_to_99999() {
    // Given
    NistRecord recordOkBecause1 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(VLL, newFieldText(1))
            .build();
    NistRecord recordOkBecause99999 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(VLL, newFieldText(99999))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseTooLow =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(VLL, newFieldText(0))
            .build();
    NistRecord recordBadBecauseTooHigh =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(VLL, newFieldText(99999 + 1))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(VLL, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatVLLisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOkBecause1).getErrors()).isEmpty();
    assertThat(validator.validate(recordOkBecause99999).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseTooHigh).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseTooLow).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatGCAisValidForRT3to6_should_be_numeric_between_0_to_6() {
    // Given
    NistRecord recordOkBecause0 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(GCA, newFieldText(0))
            .build();
    NistRecord recordOkBecause6 =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(GCA, newFieldText(6))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();
    NistRecord recordBadBecauseTooLow =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(GCA, newFieldText(-1))
            .build();
    NistRecord recordBadBecauseTooHigh =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(GCA, newFieldText(6 + 1))
            .build();
    NistRecord recordBadBecauseText =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(GCA, newFieldText("A"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatGCAisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOkBecause0).getErrors()).isEmpty();
    assertThat(validator.validate(recordOkBecause6).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseTooHigh).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseTooLow).getErrors()).isNotEmpty();
    assertThat(validator.validate(recordBadBecauseText).getErrors()).isNotEmpty();
  }

  @Test
  void checkThatDATAisValidForRT3to6_should_be_present() {
    // Given
    NistRecord recordOk =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(DATA, newFieldImage(new byte[] {1, 2, 3}))
            .build();
    NistRecord recordBadBecauseEmpty =
        new DefaultNistTextRecordBuilderImpl(NIST_OPTIONS, 55)
            .withField(99, newFieldText("1"))
            .build();

    Validator<NistRecord> validator =
        new AbstractStdRT3to6Validator(NIST_OPTIONS, mockRT) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkThatDATAisValidForRT3to6(mockError);
          }
        };

    // When
    // Then
    assertThat(validator.validate(recordOk).getErrors()).isEmpty();
    assertThat(validator.validate(recordBadBecauseEmpty).getErrors()).isNotEmpty();
  }

  /*
  @Test
  void validateFieldFGP_on_std2007_should_allow_0_to_15_and_255() {
    // Given
    // When
    // Then
    String fgps;
    for (int fpg = 0; fpg <= 14; fpg++) {
      fgps = fromItems(valueOf(fpg), "255", "255", "255", "255", "255");
      assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2007).test(fgps))
          .as(ANSI_NIST_ITL_2007.getLabel() + " must accept fgp code: " + fpg)
          .isTrue();
    }
    fgps = fromItems(valueOf(255), "255", "255", "255", "255", "255");
    assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2007).test(fgps))
        .as(ANSI_NIST_ITL_2007.getLabel() + " must accept fgp code: " + "255")
        .isTrue();

    fgps = fromItems(valueOf(19), "255", "255", "255", "255", "255");
    assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2007).test(fgps))
        .as(ANSI_NIST_ITL_2007.getLabel() + " must reject fgp code: " + 15)
        .isFalse();

    fgps = fromItems(valueOf(15), "255", "255", "255", "255", "255");
    assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2007).test(fgps))
        .as(ANSI_NIST_ITL_2007.getLabel() + " must reject fgp code: " + 15)
        .isFalse();
  }

  @Test
  void validateFieldFGP_on_std2011_should_allow_0_to_15_and_255() {
    // Given
    // When
    // Then
    for (int fpg = 0; fpg <= 15; fpg++) {
      String fgps = fromItems(valueOf(fpg), "255", "255", "255", "255", "255");
      assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2011).test(fgps))
          .as(ANSI_NIST_ITL_2011.getLabel() + " must accept fgp code: " + fpg)
          .isTrue();
    }
    String fgps = fromItems(valueOf(255), "255", "255", "255", "255", "255");
    assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2011).test(fgps))
        .as(ANSI_NIST_ITL_2011.getLabel() + " must accept fgp code: " + "255")
        .isTrue();
  }

  @Test
  void validateFieldFGP_on_std2013_should_allow_0_to_15_and_255() {
    // Given
    // When
    // Then
    for (int fpg = 0; fpg <= 15; fpg++) {
      String fgps = fromItems(valueOf(fpg), "255", "255", "255", "255", "255");
      assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2013).test(fgps))
          .as(ANSI_NIST_ITL_2013.getLabel() + " must accept fgp code: " + fpg)
          .isTrue();
    }
    String fgps = fromItems(valueOf(255), "255", "255", "255", "255", "255");
    assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2013).test(fgps))
        .as(ANSI_NIST_ITL_2013.getLabel() + " must accept fgp code: " + "255")
        .isTrue();
  }

  @Test
  void validateFieldFGP_on_std2015_should_allow_0_to_15_and_255() {
    // Given
    // When
    // Then
    for (int fpg = 0; fpg <= 15; fpg++) {
      String fgps = fromItems(valueOf(fpg), "255", "255", "255", "255", "255");
      assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2015).test(fgps))
          .as(ANSI_NIST_ITL_2015.getLabel() + " must accept fgp code: " + fpg)
          .isTrue();
    }
    String fgps = fromItems(valueOf(255), "255", "255", "255", "255", "255");
    assertThat(validator.validateFieldFGP(ANSI_NIST_ITL_2015).test(fgps))
        .as(ANSI_NIST_ITL_2015.getLabel() + " must accept fgp code: " + "255")
        .isTrue();
  }*/
}
