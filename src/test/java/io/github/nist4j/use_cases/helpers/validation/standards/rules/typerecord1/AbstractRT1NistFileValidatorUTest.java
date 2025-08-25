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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord1;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2007;
import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.records.RT1FieldsEnum.*;
import static io.github.nist4j.use_cases.CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.fixtures.SampleType5Fixtures;
import io.github.nist4j.use_cases.helpers.builders.file.NistFileBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistFileValidator;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AbstractRT1NistFileValidatorUTest {

  @ParameterizedTest
  @ValueSource(
      strings = {
        "12200501", // the original
        "1220051", // ignore leading 0
        "122000501", // ignore leading 0
        "12501200", // ignore order change
      })
  void checkForCNTField_should_return_true(String valueTest) throws IOException {
    // Given
    AbstractNistFileValidator validator =
        new AbstractRT1NistFileValidator(DEFAULT_OPTIONS_FOR_VALIDATION) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkForCNTField();
          }
        };
    // When Then
    NistFile testNist = SampleType5Fixtures.createNistFile();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(DEFAULT_OPTIONS_FOR_VALIDATION, emptyList(), emptyList())
            .from(testNist);
    NistRecordBuilder rt1 =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_VALIDATION)
            .from(nistFileBuilder.getMapOfAllRecords().get(RT1).get(0));

    NistFile nist_valid =
        nistFileBuilder
            .removeRecord(RT1)
            .withRecord(RT1, rt1.withField(CNT, newFieldText(valueTest)).build())
            .build();

    assertThat(validator.validate(testNist).isValid()).isTrue();
    assertThat(validator.validate(nist_valid).isValid()).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "12200601", // bad last index RT number
        "12200502", // bad last index RT number
        "12200", // missing
        "", // empty
        "12200A01", // bad format
        "122005A", // bad format
        "12200", // missing a pair
        "122-00501", // negative value are forbidden
      })
  void checkForCNTField_should_return_false(String valueTest) throws IOException {
    // Given
    AbstractNistFileValidator validator =
        new AbstractRT1NistFileValidator(DEFAULT_OPTIONS_FOR_VALIDATION) {
          @Override
          protected NistStandardEnum getStandard() {
            return ANSI_NIST_ITL_2007;
          }

          @Override
          public void rules() {
            checkForCNTField();
          }
        };
    // When Then
    NistFile testNist = SampleType5Fixtures.createNistFile();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(DEFAULT_OPTIONS_FOR_VALIDATION, emptyList(), emptyList())
            .from(testNist);
    NistRecordBuilder rt1 =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_VALIDATION)
            .from(nistFileBuilder.getMapOfAllRecords().get(RT1).get(0));

    NistFile nist_invalid =
        nistFileBuilder
            .removeRecord(RT1)
            .withRecord(RT1, rt1.withField(CNT, newFieldText(valueTest)).build())
            .build();

    assertThat(validator.validate(nist_invalid).isValid()).isFalse();
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "1234", "AZER", "12AD!", "ONE\u001FTWO"})
  void checkForDOMField_should_return_true(String valueTest) {
    // Given
    AbstractNistRecordValidator validator =
        new AbstractRT1NistFileValidator.AbstractRT1RecordValidator(
            DEFAULT_OPTIONS_FOR_VALIDATION, ANSI_NIST_ITL_2007) {
          @Override
          public void rules() {
            checkForDOMField();
          }
        };
    // When Then
    NistRecord testRecord =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(DOM, newFieldText(valueTest))
            .build();
    assertThat(validator.validate(testRecord).isValid()).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "ONE\u001FTWO\u001FTHREE", // Too many
        "ONE\u001F白", // bad format
      })
  void checkForDOMField_should_return_false(String valueTest) {
    AbstractNistRecordValidator validator =
        new AbstractRT1NistFileValidator.AbstractRT1RecordValidator(
            DEFAULT_OPTIONS_FOR_VALIDATION, ANSI_NIST_ITL_2007) {
          @Override
          public void rules() {
            checkForDOMField();
          }
        };
    // When Then
    NistRecord testRecord =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(DOM, newFieldText(valueTest))
            .build();
    assertThat(validator.validate(testRecord).isValid()).isFalse();
    assertThat(AbstractRT1NistFileValidator.validateDOMField().test(valueTest)).isFalse();
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "1234", "AZER", "12AD!", "ONE\u001FTWO"})
  void checkForANMField_should_return_true(String valueTest) {
    // Given
    AbstractNistRecordValidator validator =
        new AbstractRT1NistFileValidator.AbstractRT1RecordValidator(
            DEFAULT_OPTIONS_FOR_VALIDATION, ANSI_NIST_ITL_2007) {
          @Override
          public void rules() {
            checkForANMField();
          }
        };
    // When Then
    NistRecord testRecord =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(ANM, newFieldText(valueTest))
            .build();
    assertThat(validator.validate(testRecord).isValid()).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "ONE\u001F白", // bad format
        "白\u001FTWO", // bad format
      })
  void checkForANMField_should_return_false(String valueTest) {
    AbstractNistRecordValidator validator =
        new AbstractRT1NistFileValidator.AbstractRT1RecordValidator(
            DEFAULT_OPTIONS_FOR_VALIDATION, ANSI_NIST_ITL_2007) {
          @Override
          public void rules() {
            checkForANMField();
          }
        };
    // When Then
    NistRecord testRecord =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(ANM, newFieldText(valueTest))
            .build();
    assertThat(validator.validate(testRecord).isValid()).isFalse();
  }
}
