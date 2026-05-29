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
package io.github.nist4j.use_cases;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT4;
import static io.github.nist4j.enums.records.RT1FieldsEnum.VER;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_MISSING_STANDARD;
import static io.github.nist4j.use_cases.CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.enums.records.RT4FieldsEnum;
import io.github.nist4j.test_utils.AssertValidator;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl;
import java.util.List;
import org.junit.jupiter.api.Test;

class ValidateNistFileWithStandardFormatUTest {

  private final ValidateNistFileWithStandardFormat validateNistFile =
      new ValidateNistFileWithStandardFormat();

  final CreateNistFile createNistFile = new CreateNistFile();

  @Test
  void execute_should_execute_even_on_unknown_format() {
    // Given
    NistRecord rt1 =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(VER, newFieldText("0101"))
            .build();
    NistRecord rt4 =
        new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(RT4FieldsEnum.HLL, newFieldText(12))
            .build();
    NistFile nistFile = createNistFile.execute().withRecord(RT1, rt1).withRecord(RT4, rt4).build();

    // When
    List<NistValidationError> errorsNist = validateNistFile.execute(nistFile);

    // Then
    assertThat(errorsNist).isNotEmpty();
    assertThat(errorsNist).hasSize(1);
    assertThat(errorsNist.get(0).getCode()).isEqualTo(STD_ERR_MISSING_STANDARD.getCode());
  }

  @Test
  void execute_should_validate_on_format_2025() {
    // Given
    NistRecord rt1 =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(VER, newFieldText("0600"))
            .build();
    NistRecord rt4 =
        new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .build();
    NistFile nistFile = createNistFile.execute().withRecord(RT1, rt1).withRecord(RT4, rt4).build();

    // When
    List<NistValidationError> errorsNist = validateNistFile.execute(nistFile);

    // Then
    assertThat(errorsNist).isNotEmpty();
    AssertValidator.assertThatErrors(errorsNist)
        .containsExactlyInvalidFields(
            RT1FieldsEnum.TOT,
            RT1FieldsEnum.DAT,
            RT1FieldsEnum.DAI,
            RT1FieldsEnum.ORI,
            RT1FieldsEnum.TCN,
            RT1FieldsEnum.NSR,
            RT1FieldsEnum.NTR,
            RT1FieldsEnum.ORI,
            // RT4 Mandatory fields
            RT4FieldsEnum.IDC,
            RT4FieldsEnum.IMP,
            RT4FieldsEnum.FGP,
            RT4FieldsEnum.ISR,
            RT4FieldsEnum.VLL,
            RT4FieldsEnum.HLL,
            RT4FieldsEnum.GCA,
            RT4FieldsEnum.DATA);
  }
}
