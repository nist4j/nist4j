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
package io.github.nist4j.use_cases.helpers.builders.records;

import static io.github.nist4j.enums.records.RT1FieldsEnum.DAI;
import static io.github.nist4j.enums.records.RT1FieldsEnum.ORI;
import static io.github.nist4j.enums.records.RTDefaultFieldsEnum.IDC;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RTDefaultFieldsEnum;
import org.junit.jupiter.api.Test;

class DefaultNistTextRecordBuilderImplUTest {

  @Test
  void build_should_be_implemented() {
    // Given
    byte[] fakeImage = {3, 3, 3, 3, 3};

    // When
    NistRecord record =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD, 1)
            .withField(1, newFieldText(33))
            .withField(IDC, newFieldText("test2"))
            .withField(RTDefaultFieldsEnum.DATA, newFieldImage(fakeImage))
            .build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getRecordId()).isEqualTo(1);
    assertThat(record.getRecordName()).isEqualTo("RecordType 1");
    assertThat(record.getFieldText(1)).hasValue("33");
    assertThat(record.getFieldText(IDC)).hasValue("test2");
    assertThat(record.getFieldText(3)).isEmpty();
    assertThat(record.getFieldImage(RTDefaultFieldsEnum.DATA)).hasValue(fakeImage);
  }

  @Test
  void build_should_execute_beforeBuild() {
    // Given
    byte[] fakeImage = {3, 3, 3, 3, 3};

    // When
    int originalLEN = -1;
    NistRecord record =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, 12)
            .withField(1, newFieldText(originalLEN))
            .withField(RTDefaultFieldsEnum.LEN, newFieldText(originalLEN))
            .withField(IDC, newFieldText("test2"))
            .withField(RTDefaultFieldsEnum.DATA, newFieldImage(fakeImage))
            .build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getFieldAsInt(RTDefaultFieldsEnum.LEN))
        .hasValueSatisfying(resultLEN -> assertThat(resultLEN).isNotEqualTo(originalLEN));
  }

  @Test
  void build_should_not_execute_beforeBuild() {
    // Given
    byte[] fakeImage = {3, 3, 3, 3, 3};

    // When
    int originalLEN = -1;
    NistRecord record =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD, 12)
            .withField(1, newFieldText(originalLEN))
            .withField(RTDefaultFieldsEnum.LEN, newFieldText(originalLEN))
            .withField(IDC, newFieldText("test2"))
            .withField(RTDefaultFieldsEnum.DATA, newFieldImage(fakeImage))
            .build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getFieldAsInt(RTDefaultFieldsEnum.LEN)).hasValue(originalLEN);
  }

  @Test
  void build_should_be_immutable_when_reuse_builder() {
    // Given
    NistRecordBuilder builder =
        new AbstractNistRecordBuilderImpl(
            OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList());

    // When
    NistRecord record1 = builder.withField(IDC, newFieldText("1")).build();
    NistRecord record2 = builder.withField(IDC, newFieldText("2")).build();

    // Then
    assertThat(record1.getFieldText(IDC)).hasValue("1");
    assertThat(record2.getFieldText(IDC)).hasValue("2");
  }

  @Test
  void from_should_initiate_a_populate_builder() {
    // Given
    NistRecord nistRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, 7)
            .withField(2, newFieldText("TEST"))
            .withField(3, newFieldText(123))
            .build();

    // When
    NistRecord resultNistRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, 7)
            .from(nistRecord)
            .build();

    // Then
    assertThat(resultNistRecord).isNotSameAs(nistRecord);
    assertThat(resultNistRecord).isEqualTo(nistRecord);
  }

  @Test
  void from_should_not_create_a_new_entity() {
    // Given
    NistRecord nistRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, 7)
            .withField(2, newFieldText("TEST"))
            .withField(3, newFieldText(123))
            .build();

    // When
    NistRecordBuilder resultNistRecordBuilder =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, 7);
    resultNistRecordBuilder.from(nistRecord);

    NistRecord resultNistRecord = resultNistRecordBuilder.build();

    // Then
    assertThat(resultNistRecord).isNotSameAs(nistRecord);
    assertThat(resultNistRecord).isEqualTo(nistRecord);
  }

  @Test
  void removeField_and_withField_should_be_reversilble() {
    // Given
    NistRecordBuilder builder =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, 1)
            .withField(DAI, newFieldText("DAI"))
            .withField(ORI, newFieldText("ORI"));
    assertThat(builder).isNotNull();
    assertThat(builder.getFields().size()).isEqualTo(2);

    // When
    builder.removeField(DAI).removeField(ORI.getId());

    // Then
    assertThat(builder).isNotNull();
    assertThat(builder.getFields()).isEmpty();
  }

  @Test
  void newRecordBuilder_should_return_empty() {
    // Given
    NistOptions opt = NistOptionsImpl.builder().build();

    // When
    // Then
    for (RecordTypeEnum recordType : RecordTypeEnum.values()) {
      assertThat(
              DefaultNistTextRecordBuilderImpl.newRecordBuilder(opt, recordType.getNumber())
                  .getRecordId())
          .isEqualTo(recordType.getNumber());
    }
  }

  @Test
  void newRecordBuilder_with_field_should_return_empty() {
    // Given
    NistOptions opt = NistOptionsImpl.builder().build();

    // When
    // Then
    for (RecordTypeEnum recordType : RecordTypeEnum.values()) {
      assertThat(
              DefaultNistTextRecordBuilderImpl.newRecordBuilder(
                      opt, recordType.getNumber(), IDC, newFieldText(1))
                  .getRecordId())
          .isEqualTo(recordType.getNumber());
    }
  }
}
