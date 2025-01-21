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

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.enums.records.RTDefaultFieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.AbstractNistRecordBuilderImpl;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class NistRecordImmutableImplUTest {

  final byte[] fakeImage1 = {3, 3, 3, 3, 3};
  final byte[] fakeImage2 = {3, 3, 3, 3, 4};

  final Data dataImage1 = new DataImageBuilder().withValue(fakeImage1).build();

  final Data dataImage2 = new DataImageBuilder().withValue(fakeImage2).build();

  final Data dataText2 = new DataTextBuilder().withValue("test2").build();

  final Data dataText1 = new DataTextBuilder().withValue("test1").build();

  @Test
  void toString_should_be_implemented() {
    // Given
    NistRecord record =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(RTDefaultFieldsEnum.LEN, dataText1)
            .withField(RTDefaultFieldsEnum.IDC, dataText2)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();

    // When
    // Then
    assertThat(record.toString()).isNotNull();
    assertThat(record.toString()).doesNotContain(record.getClass().getName() + '@');
  }

  @Test
  void equals_should_be_implemented() {
    // Given
    NistRecord record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText1)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();
    NistRecord record2_same_as_record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText1)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();
    NistRecord record3_not_same_as_record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText1)
            .withField(RTDefaultFieldsEnum.DATA, dataImage2)
            .build();
    NistRecord record4_not_same_as_record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText2)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();

    // When
    // Then
    assertThat(record1).isEqualTo(record2_same_as_record1);
    assertThat(record1).isNotEqualTo(record3_not_same_as_record1);
    assertThat(record1).isNotEqualTo(record4_not_same_as_record1);
  }

  @Test
  void hashCode_should_be_implemented() {
    // Given
    NistRecord record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText1)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();
    NistRecord record2_same_as_record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText1)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();
    NistRecord record3_not_same_as_record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText1)
            .withField(RTDefaultFieldsEnum.DATA, dataImage2)
            .build();
    NistRecord record4_not_same_as_record1 =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(2, dataText2)
            .withField(RTDefaultFieldsEnum.DATA, dataImage1)
            .build();

    // When
    // Then
    assertThat(record1.hashCode()).isEqualTo(record2_same_as_record1.hashCode());
    assertThat(record1.hashCode()).isNotEqualTo(record3_not_same_as_record1.hashCode());
    assertThat(record1.hashCode()).isNotEqualTo(record4_not_same_as_record1.hashCode());
  }

  @Test
  void build_should_execute_beforeBuild() {
    // Given
    AtomicInteger beforeCount = new AtomicInteger(0);
    AtomicInteger afterCount = new AtomicInteger(0);

    NistRecordBuilder nistRecordBuilder =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(1, dataText1)
            .withBeforeBuild(b -> beforeCount.incrementAndGet())
            .withAfterBuild(b -> afterCount.incrementAndGet());

    // When
    NistRecord record = nistRecordBuilder.build();

    // Then
    assertThat(record).isNotNull();
    assertThat(beforeCount.get()).isEqualTo(1);
    assertThat(afterCount.get()).isEqualTo(1);
  }

  @Test
  void getFieldAsInt_should_throw_an_exception() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(3, newFieldText("123"))
            .withField(4, newFieldText("BAD_NUMBER"))
            .withField(RTDefaultFieldsEnum.LEN, newFieldText("DATA"));

    // When
    NistRecord record = nistRecordBuilder.build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getFieldAsInt(3)).hasValue(123);
    assertThrows(NumberFormatException.class, () -> record.getFieldAsInt(4));
  }

  @Test
  void getFieldData_should_return_un_data() {
    // Given
    NistRecordBuilder nistRecordBuilder =
        new AbstractNistRecordBuilderImpl(
                OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList())
            .withField(RTDefaultFieldsEnum.IDC, newFieldText("IDC"));

    // When
    NistRecord record = nistRecordBuilder.build();

    // Then
    assertThat(record).isNotNull();
    assertThat(record.getFieldData(RTDefaultFieldsEnum.IDC).orElse(null))
        .isEqualTo(newFieldText("IDC"));
  }

  @Test
  void unmodifiableMapOfCopies_should_return_copy_of_fields() {
    // Given
    NistRecordBuilder builder =
        new AbstractNistRecordBuilderImpl(
            OPTIONS_DONT_CHANGE_ON_BUILD, 1, "record name 1", emptyList(), emptyList());

    // When
    Data data = newFieldText("1");
    NistRecord record1 = builder.withField(RTDefaultFieldsEnum.IDC, data).build();
    NistRecord record2 = builder.withField(RTDefaultFieldsEnum.IDC, data).build();

    // Then
    for (Integer keyField : builder.getFields().keySet()) {
      assertThat(builder.getFields().get(keyField)).isNotSameAs(record1.getFields().get(keyField));
      assertThat(builder.getFields().get(keyField)).isEqualTo(record1.getFields().get(keyField));
      assertThat(record1.getFields().get(keyField)).isNotSameAs(record2.getFields().get(keyField));
      assertThat(record1.getFields().get(keyField)).isEqualTo(record2.getFields().get(keyField));
    }
  }
}
