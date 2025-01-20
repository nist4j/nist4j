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
package io.github.nist4j.entities.record;

import static io.github.nist4j.enums.records.RT2FieldsEnum.*;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class RT2UserDefinedDescriptionTextRecordImmutableImplUTest {

  @Test
  void withField_should_ignore_when_it_call_twice() {
    // Given
    // When
    NistRecordBuilder nistRecordBuilder =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
            .withField(13, newFieldText("Champ 13.1"))
            .withField(13, newFieldText("Champ 13.2"));

    // Then
    List<Data> allFields = new ArrayList<>(nistRecordBuilder.getFields().values());
    assertThat(allFields.size()).isEqualTo(1);
    assertThat(((DataTextImmutableImpl) allFields.get(0)).getData()).isEqualTo("Champ 13.2");
  }

  @Test
  void withField_should_trier_les_fields() {
    // Given
    byte[] expectedImage = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    // When
    NistRecordBuilder nistRecordBuilder =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
            .withField(1, newFieldText("Champ 1"))
            .withField(10, newFieldText("Champ 10"))
            .withField(10, newFieldText("Champ 10 again"))
            .withField(999, newFieldImage(expectedImage))
            .withField(2, newFieldText("Champ 2"));
    // Then
    List<Data> allFields = new ArrayList<>(nistRecordBuilder.getFields().values());
    assertThat(allFields.size()).isEqualTo(4);
    assertThat(((DataTextImmutableImpl) allFields.get(0)).getData()).isEqualTo("Champ 1");
    assertThat(((DataTextImmutableImpl) allFields.get(1)).getData()).isEqualTo("Champ 2");
    assertThat(((DataTextImmutableImpl) allFields.get(2)).getData()).isEqualTo("Champ 10 again");
    assertThat(((DataImageImmutableImpl) allFields.get(3)).getData()).isEqualTo(expectedImage);
  }

  @Test
  void getFieldText_d_une_image_should_throw_an_exception() {
    // Given
    byte[] expectedImage = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    NistRecordBuilder nistRecordBuilder =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
            .withField(999, newFieldImage(expectedImage))
            .withField(13, newFieldText("expectedText"));
    // When
    // Then
    List<Data> allFields = new ArrayList<>(nistRecordBuilder.getFields().values());
    assertThat(allFields.size()).isEqualTo(2);

    NistRecord nistRecord = nistRecordBuilder.build();
    Exception exception =
        assertThrows(InvalidFormatNist4jException.class, () -> nistRecord.getFieldText(999));
    assertThat(exception.getMessage()).isEqualTo("Invalid format 'Field 999 isn't in text format'");

    exception =
        assertThrows(InvalidFormatNist4jException.class, () -> nistRecord.getFieldImage(13));
    assertThat(exception.getMessage()).isEqualTo("Invalid format 'Field 13 isn't in image format'");
  }

  @Test
  void build_should_calculate_LEN() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(IDC, newFieldText("IDC"))
            .withField(5, newFieldText("DAR"));

    // When
    NistRecord record = nistRecordBuilder.build();
    Optional<String> oLEN = record.getFieldText(LEN);

    // Then
    assertThat(oLEN).isNotEmpty();
    assertThat(oLEN).hasValue("29");
    log.debug("record {} ", record);
  }
}
