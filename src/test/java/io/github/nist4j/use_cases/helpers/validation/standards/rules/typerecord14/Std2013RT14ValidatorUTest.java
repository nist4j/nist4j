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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14;

import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.use_cases.CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE;
import static io.github.nist4j.use_cases.ValidateNistFileWithStandardFormat.DEFAULT_OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.use_cases.helpers.builders.records.RT14VariableResolutionFingerprintNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Std2013RT14ValidatorUTest {

  @ParameterizedTest
  @ValueSource(strings = {"", "1234", "AZER", "12AD!", "白"})
  void checkForCONField_should_return_true(String valueTest) {
    // Given
    Std2013RT14Validator validator =
        new Std2013RT14Validator(DEFAULT_OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldCON14_047();
          }
        };
    // When Then
    NistRecord testRecord =
        new RT14VariableResolutionFingerprintNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(CON, newFieldText(valueTest))
            .build();
    assertThat(validator.validate(testRecord).isValid()).isTrue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
        "ONE\u001FTWO\u001FTHREE", // Too many
        "ONE\u001F白", // bad format
        "1001CHARS_ZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBNDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890AZERTYIOPQSDFJKLMWXCVBN1234567890VBN1234567890FJKLMWXKLMW0VBN12345677890VBN0VBN1234567890", // too long
      })
  void checkForCONField_should_return_false(String valueTest) {
    Std2013RT14Validator validator =
        new Std2013RT14Validator(DEFAULT_OPTIONS_FOR_VALIDATION) {
          @Override
          public void rules() {
            checkForFieldCON14_047();
          }
        };
    // When Then
    NistRecord testRecord =
        new RT1TransactionInformationNistRecordBuilderImpl(DEFAULT_OPTIONS_FOR_CREATE)
            .withField(CON, newFieldText(valueTest))
            .build();
    assertThat(validator.validate(testRecord).isValid()).isFalse();
  }
}
