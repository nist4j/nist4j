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
package io.github.nist4j.use_cases.helpers.builders.file;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.RecordTypeEnum.RT2;
import static io.github.nist4j.enums.RecordTypeEnum.RT4;
import static io.github.nist4j.enums.records.RT1FieldsEnum.VER;
import static io.github.nist4j.enums.records.RTDefaultFieldsEnum.IDC;
import static io.github.nist4j.fixtures.NistFileFixtures.newNistFileBuilderWithoutCallbacks;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.RecordFixtures.newRecordBuilderEnableCalculation;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT4FieldsEnum;
import io.github.nist4j.exceptions.Nist4jException;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.fixtures.OptionsFixtures;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class NistFileBuilderImplUTest {

  final Data fieldText1 = new DataTextBuilder().withValue("test1").build();
  final Data fieldText3_same_as_1 = new DataTextBuilder().withValue("test1").build();
  final Data fieldText2 = new DataTextBuilder().withValue("test2").build();
  final Data fieldImage1 = new DataImageBuilder().withValue(new byte[] {3, 3, 3, 3, 3}).build();

  final NistRecord nistRecord1 =
      newRecordBuilderEnableCalculation(1)
          .withField(1, fieldText1)
          .withField(2, fieldText2)
          .build();

  final NistRecord nistRecord2 =
      newRecordBuilderEnableCalculation(1)
          .withField(1, fieldText3_same_as_1)
          .withField(2, fieldText2)
          .build();

  final NistRecord nistRecord3 =
      newRecordBuilderEnableCalculation(2)
          .withField(1, fieldText1)
          .withField(999, fieldImage1)
          .build();

  @Test
  void build_should_builder() {
    NistFile nistCas1 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord1).build();
    assertThat(nistCas1).isNotNull();
  }

  @Test
  void equals_should_be_implemented() {
    NistFile nistCas1 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord1).build();
    NistFile nistCas2 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord2).build();
    NistFile nistCas3 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord3).build();

    assertThat(nistCas1).isNotNull();
    assertThat(nistCas2).isNotNull();
    assertThat(nistCas3).isNotNull();
    assertThat(nistCas1).isEqualTo(nistCas2);
    assertThat(nistCas1).isNotEqualTo(nistCas3);
  }

  @Test
  void hashCode_should_be_implemented() {
    NistFile nistCas1 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord1).build();
    NistFile nistCas2 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord2).build();
    NistFile nistCas3 = newNistFileBuilderWithoutCallbacks().withRecord(1, nistRecord3).build();

    assertThat(nistCas1.hashCode()).isNotNull();
    assertThat(nistCas2.hashCode()).isNotNull();
    assertThat(nistCas3.hashCode()).isNotNull();
    assertThat(nistCas1.hashCode()).isEqualTo(nistCas2.hashCode());
    assertThat(nistCas1.hashCode()).isNotEqualTo(nistCas3.hashCode());
  }

  @Test
  void toString_should_be_implemented() {
    NistFile nistCas1 =
        NistFileFixtures.newNistFileBuilderEnableCalculation()
            .withRecord(1, nistRecord1)
            .withRecord(2, nistRecord2)
            .withRecord(2, nistRecord3)
            .build();

    assertThat(nistCas1.toString()).isNotNull();
    assertThat(nistCas1.toString()).doesNotContain(nistCas1.getClass().getName() + '@');
  }

  @Test
  void withRecord_should_add_only_one_RT1() {
    NistFile nistCas_multipleR1 =
        newNistFileBuilderWithoutCallbacks()
            .withRecord(RT1, nistRecord1)
            .withRecord(RT1, nistRecord1)
            .build();

    assertThat(nistCas_multipleR1).isNotNull();
    assertThat(nistCas_multipleR1.getMapOfAllrecords().get(RT1).size()).isEqualTo(1);
  }

  @Test
  void withRecord_should_add_multiple_RT2() {
    NistFile nistCas_multipleR1 =
        newNistFileBuilderWithoutCallbacks()
            .withRecord(RT2, nistRecord3)
            .withRecord(2, nistRecord3)
            .build();

    assertThat(nistCas_multipleR1).isNotNull();
    assertThat(nistCas_multipleR1.getMapOfAllrecords().get(RT2).size()).isEqualTo(2);
  }

  @Test
  void build_should_execute_afterBuild() {
    Callback<NistFileBuilder> callbackBefore = mock(Callback.class);
    Callback<NistFile> callbackAfter = mock(Callback.class);

    NistFileBuilder builder =
        new NistFileBuilderImpl(
                OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD,
                Collections.singletonList(callbackBefore),
                Collections.singletonList(callbackAfter))
            .withRecord(RT2, nistRecord3)
            .withRecord(2, nistRecord3);

    // When
    NistFile nistFile = builder.build();

    // Then
    verify(callbackBefore, times(1)).execute(builder);
    verify(callbackAfter, times(1)).execute(nistFile);
  }

  @Test
  void withBeforeBuild_should_execute_beforeBuild() {
    Callback<NistFileBuilder> callbackBefore = mock(Callback.class);
    Callback<NistFile> callbackAfter = mock(Callback.class);

    NistFileBuilder builder =
        new NistFileBuilderImpl(
                OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT2, nistRecord3)
            .withRecord(2, nistRecord3)
            .withBeforeBuild(callbackBefore)
            .withAfterBuild(callbackAfter);

    // When
    NistFile nistFile = builder.build();

    // Then
    assertThat(((NistFileBuilderImpl) builder).getBeforeBuild().get(0)).isEqualTo(callbackBefore);
    assertThat(((NistFileBuilderImpl) builder).getAfterBuild().get(0)).isEqualTo(callbackAfter);
    assertThat(((NistFileBuilderImpl) builder).getNistOptions())
        .isEqualTo(OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD);
    verify(callbackBefore, times(1)).execute(builder);
    verify(callbackAfter, times(1)).execute(nistFile);
  }

  @Test
  void from_should_be_an_new_instance_but_with_same_values() {
    // Given
    NistRecord nistRecord2 =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(7, newFieldText("TEST"))
            .withField(8, newFieldText(234))
            .build();
    NistFile originalNistFile =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT2, nistRecord2)
            .build();

    // When
    NistFile cloneNistFile =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .from(originalNistFile)
            .build();

    // Then
    Assertions.assertThat(cloneNistFile).isNotSameAs(originalNistFile);
    Assertions.assertThat(cloneNistFile).isEqualTo(originalNistFile);
  }

  @Test
  void from_should_not_create_a_new_entity() {
    // Given
    NistRecord nistRecord2 =
        new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
            .withField(7, newFieldText("TEST"))
            .withField(8, newFieldText(234))
            .build();
    NistFile originalNistFile =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT2, nistRecord2)
            .build();

    // When
    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList());
    nistFileBuilder.from(originalNistFile);
    NistFile cloneNistFile = nistFileBuilder.build();

    // Then
    Assertions.assertThat(cloneNistFile).isNotSameAs(originalNistFile);
    Assertions.assertThat(cloneNistFile).isEqualTo(originalNistFile);
  }

  @Test
  void getRecordByIdc_should_return_matching_record_if_foundTypeAnd() {
    // Given
    NistRecord nistRecord4idc1 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();
    NistRecord nistRecord4idc2 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(2)).build();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1)
            .withRecord(RT4, nistRecord4idc2);

    // When
    // Then
    assertThat(nistFileBuilder.getRecordByIdc(RT14, 0)).isEmpty();
    assertThat(nistFileBuilder.getRecordByIdc(RT1, 0)).isPresent();
    assertThat(nistFileBuilder.getRecordByIdc(RT4, 1)).isPresent();
    assertThat(nistFileBuilder.getRecordByIdc(RT4, 3)).isEmpty();
    assertThat(nistFileBuilder.getRecordByIdc(RT14, 4)).isEmpty();
  }

  @Test
  void replaceRecord_should_replace_has_expected() {
    // Given
    NistRecord newNistRecord1 =
        newRecordBuilderEnableCalculation(1)
            .withField(VER, newFieldText(NistStandardEnum.ANSI_NIST_ITL_2011.getCode()))
            .build();
    NistRecord nistRecord4idc1 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();
    NistRecord nistRecord4idc2 =
        newRecordBuilderEnableCalculation(4)
            .withField(IDC, newFieldText(2))
            .withField(RT4FieldsEnum.FGP, newFieldText("1"))
            .build();
    NistRecord nistRecord4idc3 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(3)).build();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1)
            .withRecord(RT4, nistRecord4idc2)
            .withRecord(RT4, nistRecord4idc3);

    NistRecord newNistRecord4idc2 =
        newRecordBuilderEnableCalculation(4)
            .withField(IDC, newFieldText(2))
            .withField(RT4FieldsEnum.FGP, newFieldText("2"))
            .build();

    assertThat(nistFileBuilder.getMapOfAllRecords().get(RT4).get(1).getFieldText(RT4FieldsEnum.FGP))
        .hasValue("1");

    // When
    nistFileBuilder.replaceRecord(RT4, 2, newNistRecord4idc2);
    nistFileBuilder.replaceRecord(RT1, 0, newNistRecord1);

    // Then
    assertThat(nistFileBuilder.getMapOfAllRecords().size()).isEqualTo(2);
    assertThat(nistFileBuilder.getMapOfAllRecords().get(RT4).size()).isEqualTo(3);
    assertThat(nistFileBuilder.getMapOfAllRecords().get(RT4).get(1).getFieldText(RT4FieldsEnum.FGP))
        .hasValue("2");
    assertThat(nistFileBuilder.getMapOfAllRecords().get(RT4).get(0).getFieldText(RT4FieldsEnum.IDC))
        .hasValue("1");
    assertThat(nistFileBuilder.getMapOfAllRecords().get(RT4).get(1).getFieldText(RT4FieldsEnum.IDC))
        .hasValue("2");
    assertThat(nistFileBuilder.getMapOfAllRecords().get(RT4).get(2).getFieldText(RT4FieldsEnum.IDC))
        .hasValue("3");
  }

  @Test
  void replaceRecord_should_throw_exception_if_error() {
    // Given
    NistRecord nistRecord4idc1 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();
    NistRecord newNistRecord4withoutIdc = newRecordBuilderEnableCalculation(4).build();
    NistRecord newNistRecord4withBadIdc =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(99)).build();

    NistFileBuilder nistFileBuilder =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1);

    // When
    // Then
    // idcId not found
    assertThrows(Nist4jException.class, () -> nistFileBuilder.replaceRecord(RT4, 2, nistRecord3));
    // newRecord doesn't have idcId
    assertThrows(
        Nist4jException.class,
        () ->
            nistFileBuilder.replaceRecord(
                RT4, 1, newNistRecord4withoutIdc)); // newRecord doesn't have idcId
    // newRecord doesn't have the same idcId
    assertThrows(
        Nist4jException.class,
        () -> nistFileBuilder.replaceRecord(RT4, 1, newNistRecord4withBadIdc));
  }

  @Test
  void removeRecord_should_remove_record_if_found() {
    // Given
    NistRecord nistRecord4idc1 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();
    NistRecord nistRecord4idc2 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(2)).build();

    // When
    NistFileBuilder builderRemoveAbsentRT =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1)
            .removeRecord(RT14, 1);

    NistFileBuilder builderRemoveAbsentRTIdc =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1)
            .removeRecord(RT4, 2);

    NistFileBuilder builderRemoveLastRT =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1)
            .removeRecord(RT4, 1);

    NistFileBuilder builderRemoveOneRT =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, nistRecord1)
            .withRecord(RT4, nistRecord4idc1)
            .withRecord(RT4, nistRecord4idc2)
            .removeRecord(RT4, 1);

    // Then
    assertThat(builderRemoveAbsentRT.getMapOfAllRecords().size()).isEqualTo(2);
    assertThat(builderRemoveAbsentRT.getMapOfAllRecords().get(RT1)).isNotNull();
    assertThat(builderRemoveAbsentRT.getMapOfAllRecords().get(RT4)).isNotNull();

    assertThat(builderRemoveAbsentRTIdc.getMapOfAllRecords().size()).isEqualTo(2);
    assertThat(builderRemoveAbsentRTIdc.getMapOfAllRecords().get(RT1)).isNotNull();
    assertThat(builderRemoveAbsentRTIdc.getMapOfAllRecords().get(RT4)).isNotNull();

    assertThat(builderRemoveLastRT.getMapOfAllRecords().size()).isEqualTo(1);
    assertThat(builderRemoveLastRT.getMapOfAllRecords().get(RT1).size()).isEqualTo(1);
    assertThat(builderRemoveLastRT.getMapOfAllRecords().get(RT4)).isNull();

    assertThat(builderRemoveOneRT.getMapOfAllRecords().size()).isEqualTo(2);
    assertThat(builderRemoveOneRT.getMapOfAllRecords().get(RT1).size()).isEqualTo(1);
    assertThat(builderRemoveOneRT.getMapOfAllRecords().get(RT4).size()).isEqualTo(1);
  }

  @Test
  void removeRecord_on_record_1_should_remove_it_if_found() {
    // Given
    NistRecord rt1 =
        newRecordBuilderEnableCalculation(1)
            .withField(VER, newFieldText(NistStandardEnum.ANSI_NIST_ITL_2011.getCode()))
            .build();
    NistRecord nistRecord4idc2 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();

    // When
    NistFileBuilder builderRemoveRT1 =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, rt1)
            .withRecord(RT4, nistRecord4idc2)
            .removeRecord(RT1, 0);

    // Then
    assertThat(builderRemoveRT1.getMapOfAllRecords().size()).isEqualTo(1);
    assertThat(builderRemoveRT1.getMapOfAllRecords().get(RT1)).isNull();
    assertThat(builderRemoveRT1.getMapOfAllRecords().get(RT4)).isNotNull();
  }

  @Test
  void findRecordIdByIdc_should_find_record_index() {
    // Given
    NistRecord nistRecord4idc1 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();
    NistRecord nistRecord4idc2 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(2)).build();
    NistRecord nistRecord4idc3 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(3)).build();

    NistFileBuilderImpl nistFileBuilder =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList());

    List<NistRecord> records = asList(nistRecord4idc1, nistRecord4idc2, nistRecord4idc3);

    // When
    // Then
    assertThat(nistFileBuilder.findRecordIndexByIdc(records, 1)).hasValue(0);
    assertThat(nistFileBuilder.findRecordIndexByIdc(records, 2)).hasValue(1);
    assertThat(nistFileBuilder.findRecordIndexByIdc(records, 3)).hasValue(2);
    assertThat(nistFileBuilder.findRecordIndexByIdc(records, 0)).isEmpty();
    assertThat(nistFileBuilder.findRecordIndexByIdc(records, 5)).isEmpty();
  }
}
