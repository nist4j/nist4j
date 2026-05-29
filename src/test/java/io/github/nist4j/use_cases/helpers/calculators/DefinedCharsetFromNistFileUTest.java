/*
 * Copyright (C) 2026 Sopra Steria.
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
package io.github.nist4j.use_cases.helpers.calculators;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.records.RT1FieldsEnum.DCS;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromItems;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.fixtures.OptionsFixtures;
import io.github.nist4j.fixtures.Record1Fixtures;
import io.github.nist4j.use_cases.CreateNistFile;
import org.junit.jupiter.api.Test;

@SuppressWarnings("DataFlowIssue")
class DefinedCharsetFromNistFileUTest {
  final DefinedCharsetFromNistFile definedCharsetFromNistFile =
      new DefinedCharsetFromNistFile(OptionsFixtures.OPTIONS_FOR_VALIDATION);

  @Test
  void execute_should_handle_exception() {
    // Given
    NistRecord rt1Img =
        Record1Fixtures.record1Cas1_basic_Record()
            .withField(DCS, newFieldImage(new byte[] {1, 2, 3}))
            .build();
    NistFile nistFileImg = new CreateNistFile().execute().withRecord(RT1, rt1Img).build();

    // When
    // Then
    assertThrows(
        InvalidFormatNist4jException.class, () -> definedCharsetFromNistFile.execute(nistFileImg));
    assertThrows(NullPointerException.class, () -> definedCharsetFromNistFile.execute(null));
  }

  @Test
  void execute_should_return_default() {
    // Given
    NistRecord rt1 = Record1Fixtures.record1Cas1_basic_Record().removeField(DCS).build();
    NistFile nistFile = new CreateNistFile().execute().withRecord(RT1, rt1).build();

    NistRecord rt1notNumber =
        Record1Fixtures.record1Cas1_basic_Record()
            .withField(DCS, newSubfieldsFromItems("A", "UTF-8"))
            .build();
    NistFile nistFileNotNumber =
        new CreateNistFile().execute().withRecord(RT1, rt1notNumber).build();
    // When
    // Then
    assertThat(definedCharsetFromNistFile.execute(nistFile)).isEqualTo(CharsetEnum.getDefault());
    assertThat(definedCharsetFromNistFile.execute(nistFileNotNumber))
        .isEqualTo(CharsetEnum.getDefault());
  }

  @Test
  void execute_should_return_encoding() {
    // Given
    NistRecord rt1ASCII =
        Record1Fixtures.record1Cas1_basic_Record()
            .withField(DCS, newSubfieldsFromItems("0", "ASCII"))
            .build();
    NistFile nistFileASCII = new CreateNistFile().execute().withRecord(RT1, rt1ASCII).build();
    NistRecord rt1UTF8 =
        Record1Fixtures.record1Cas1_basic_Record()
            .withField(DCS, newSubfieldsFromItems("3", "UTF8"))
            .build();
    NistFile nistFileUTF8 = new CreateNistFile().execute().withRecord(RT1, rt1UTF8).build();
    NistRecord rt1UTF16 =
        Record1Fixtures.record1Cas1_basic_Record()
            .withField(DCS, newSubfieldsFromItems("2", "UTF16"))
            .build();
    NistFile nistFileUTF16 = new CreateNistFile().execute().withRecord(RT1, rt1UTF16).build();
    NistRecord rt1UTF32 =
        Record1Fixtures.record1Cas1_basic_Record()
            .withField(DCS, newSubfieldsFromItems("4", "UTF32"))
            .build();
    NistFile nistFileUTF32 = new CreateNistFile().execute().withRecord(RT1, rt1UTF32).build();

    // When
    // Then
    assertThat(definedCharsetFromNistFile.execute(nistFileASCII)).isEqualTo(CharsetEnum.ASCII);
    assertThat(definedCharsetFromNistFile.execute(nistFileUTF8)).isEqualTo(CharsetEnum.UTF_8);
    assertThat(definedCharsetFromNistFile.execute(nistFileUTF16)).isEqualTo(CharsetEnum.UTF_16);
    assertThat(definedCharsetFromNistFile.execute(nistFileUTF32)).isEqualTo(CharsetEnum.UTF_32);
  }
}
