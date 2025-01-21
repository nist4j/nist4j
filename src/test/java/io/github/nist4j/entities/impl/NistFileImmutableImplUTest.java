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
package io.github.nist4j.entities.impl;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.RecordTypeEnum.RT18;
import static io.github.nist4j.enums.RecordTypeEnum.RT19;
import static io.github.nist4j.enums.RecordTypeEnum.RT2;
import static io.github.nist4j.enums.RecordTypeEnum.RT20;
import static io.github.nist4j.enums.RecordTypeEnum.RT21;
import static io.github.nist4j.enums.RecordTypeEnum.RT22;
import static io.github.nist4j.enums.RecordTypeEnum.RT4;
import static io.github.nist4j.enums.RecordTypeEnum.RT98;
import static io.github.nist4j.enums.RecordTypeEnum.RT99;
import static io.github.nist4j.enums.records.RT1FieldsEnum.VER;
import static io.github.nist4j.enums.records.RTDefaultFieldsEnum.IDC;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.RecordFixtures.newRecordBuilderEnableCalculation;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.fixtures.NistFileFixtures;
import io.github.nist4j.fixtures.OptionsFixtures;
import io.github.nist4j.fixtures.Record1Fixtures;
import io.github.nist4j.use_cases.CreateNistFile;
import io.github.nist4j.use_cases.helpers.builders.file.NistFileBuilderImpl;
import org.junit.jupiter.api.Test;

class NistFileImmutableImplUTest {

  @Test
  void builder_should_builder() {
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(CreateNistFile.DEFAULT_OPTIONS_FOR_CREATE).build();
    assertThat(nistFile).isNotNull();
  }

  @Test
  void getRecordListByRecordTypeEnum_should_return_a_list() {
    // Given
    // When
    NistFile nistFile =
        NistFileFixtures.newNistFileBuilder(OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD)
            .withRecord(RT1, Record1Fixtures.record1Cas1_basic_Record().build())
            .build();

    // Then
    assertThat(nistFile).isNotNull();
    assertThat(nistFile.getRecordListByRecordTypeEnum(RT1)).isNotNull();
    assertThat(nistFile.getRecordListByRecordTypeEnum(RT2)).isEmpty();
  }

  @Test
  void getRecordByIdc_should_return_record_if_foundTypeAnd() {
    // Given
    NistRecord rt1 =
        newRecordBuilderEnableCalculation(1)
            .withField(VER, newFieldText(NistStandardEnum.ANSI_NIST_ITL_2015.getCode()))
            .build();
    NistRecord rt4idc1 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(1)).build();
    NistRecord rt4idc2 =
        newRecordBuilderEnableCalculation(4).withField(IDC, newFieldText(2)).build();

    NistFile nistFile =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, rt1)
            .withRecord(RT4, rt4idc1)
            .withRecord(RT4, rt4idc2)
            .build();

    // When
    // Then
    assertThat(nistFile.getRecordByTypeAndIdc(RT14, 0)).isEmpty();
    assertThat(nistFile.getRecordByTypeAndIdc(RT1, 0)).isPresent();
    assertThat(nistFile.getRecordByTypeAndIdc(RT4, 1)).isPresent();
    assertThat(nistFile.getRecordByTypeAndIdc(RT4, 3)).isEmpty();
  }

  @Test
  void getRxMapDefaultRecords_should_return_others_record() {
    // Given
    NistRecord rt1 =
        newRecordBuilderEnableCalculation(1)
            .withField(VER, newFieldText(NistStandardEnum.ANSI_NIST_ITL_2015.getCode()))
            .build();
    NistRecord rt18idc1 =
        newRecordBuilderEnableCalculation(18).withField(IDC, newFieldText(1)).build();
    NistRecord rt99idc2 =
        newRecordBuilderEnableCalculation(99).withField(IDC, newFieldText(2)).build();

    NistFile nistFileWithRT1 =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, rt1)
            .build();

    NistFile nistFileWithRT18_99 =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(RT1, rt1)
            .withRecord(RT18, rt18idc1)
            .withRecord(RT99, rt99idc2)
            .build();

    // When
    // Then
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT18)).isEmpty();
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT19)).isEmpty();
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT20)).isEmpty();
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT21)).isEmpty();
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT22)).isEmpty();
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT98)).isEmpty();
    assertThat(nistFileWithRT1.getRxMapDefaultRecords().get(RT99)).isEmpty();

    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT18)).isNotEmpty();
    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT19)).isEmpty();
    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT20)).isEmpty();
    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT21)).isEmpty();
    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT22)).isEmpty();
    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT98)).isEmpty();
    assertThat(nistFileWithRT18_99.getRxMapDefaultRecords().get(RT99)).isNotEmpty();
  }

  @Test
  void getRTx_should_return_empty() {
    // Given
    NistFile nistFile =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList()).build();

    // When
    // Then
    assertThat(nistFile.getRT2UserDefinedDescriptionTextRecords()).isEmpty();
    assertThat(nistFile.getRT3LowResolutionGrayscaleFingerprintRecords()).isEmpty();
    assertThat(nistFile.getRT4HighResolutionGrayscaleFingerprintRecords()).isEmpty();
    assertThat(nistFile.getRT5LowResolutionBinaryFingerprintRecords()).isEmpty();
    assertThat(nistFile.getRT6HighResolutionBinaryFingerprintRecords()).isEmpty();
    assertThat(nistFile.getRT7UserDefinedImageRecords()).isEmpty();
    assertThat(nistFile.getRT8SignatureImageRecords()).isEmpty();
    assertThat(nistFile.getRT9MinutiaeDataRecords()).isEmpty();
    assertThat(nistFile.getRT10FacialAndSmtImageRecords()).isEmpty();
    assertThat(nistFile.getRT11thRecords()).isEmpty();
    assertThat(nistFile.getRT12thRecords()).isEmpty();
    assertThat(nistFile.getRT13VariableResolutionLatentImageRecords()).isEmpty();
    assertThat(nistFile.getRT14VariableResolutionFingerprintRecords()).isEmpty();
    assertThat(nistFile.getRT15VariableResolutionPalmprintRecords()).isEmpty();
    assertThat(nistFile.getRT16UserDefinedTestingImageRecords()).isEmpty();
    assertThat(nistFile.getRT17IrisImageRecords()).isEmpty();
  }

  @Test
  void getRT1TransactionInformationRecord_should_throw_exception_if_missing_RT1() {
    // Given
    NistFile nistFile =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList()).build();
    NistRecord rt1 = Record1Fixtures.record1Cas1_basic_Record().build();
    NistFile nistFileWithRT1 =
        new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList())
            .withRecord(1, rt1)
            .build();

    // When
    // Then
    assertThrows(InvalidFormatNist4jException.class, nistFile::getRT1TransactionInformationRecord);
    assertThat(nistFileWithRT1.getRT1TransactionInformationRecord()).isEqualTo(rt1);
  }
}
