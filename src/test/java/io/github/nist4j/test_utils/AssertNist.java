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
package io.github.nist4j.test_utils;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.RecordTypeEnum;
import java.util.*;
import lombok.NonNull;

/**
 * Utils class to compare NistFile with an expected object
 * /!\ Scope TEST : Should only be used in a test class
 */
public class AssertNist {

  private static final byte[] EMPTY_IMAGE = null;
  private final NistFile nistFile;

  public AssertNist(NistFile nistFile) {
    this.nistFile = nistFile;
  }

  public static AssertNist assertThatNist(@NonNull NistFile nistFile) {
    return new AssertNist(nistFile);
  }

  public static void assertRecordEquals(NistRecord resultRecord, NistRecord expectedRecord) {

    Set<Map.Entry<Integer, Data>> entrySet = expectedRecord.getFields().entrySet();
    for (Map.Entry<Integer, Data> field : entrySet) {
      if (field.getValue() instanceof DataTextImmutableImpl) {
        DataTextImmutableImpl expectedValue = (DataTextImmutableImpl) field.getValue();

        assertThat(resultRecord.getFieldText(field.getKey()))
            .hasValue(expectedValue.getData())
            .as("check record %s field %s", resultRecord.getRecordId(), field.getKey());

      } else if (field.getValue() instanceof DataImageImmutableImpl) {
        DataImageImmutableImpl expectedValue = (DataImageImmutableImpl) field.getValue();

        assertThat(resultRecord.getFieldImage(field.getKey()).orElse(EMPTY_IMAGE))
            .isEqualTo(expectedValue.getData());

      } else {
        throw new RuntimeException("field is neither DataText nor DataImage");
      }
    }
    assertThat(resultRecord.getAllFields().size())
        .as("check nb fields of record %s", resultRecord.getRecordId())
        .isEqualTo(expectedRecord.getAllFields().size());
  }

  public AssertNist isEqualTo(NistFile expectedFile) {
    return this.hasTheSameRecord1(expectedFile)
        .hasTheSameRecord2(expectedFile)
        .hasTheSameRecord3(expectedFile)
        .hasTheSameRecord4(expectedFile)
        .hasTheSameRecord5(expectedFile)
        .hasTheSameRecord6(expectedFile)
        .hasTheSameRecord14(expectedFile);
  }

  public AssertNist hasTheSameRecord1(NistFile expectedFile) {
    if (Objects.nonNull(expectedFile.getRT1TransactionInformationRecord())) {
      assertThat(expectedFile.getRT1TransactionInformationRecord().getFields())
          .isEqualTo(nistFile.getRT1TransactionInformationRecord().getFields());
    }
    return this;
  }

  public AssertNist hasTheSameRecord2(NistFile expectedFile) {
    RecordTypeEnum recordTypeEnum = RecordTypeEnum.RT2;
    if (isNotEmpty(expectedFile.getRecordListByRecordTypeEnum(recordTypeEnum))) {
      recordAreEquals(
          getRecordList(nistFile, recordTypeEnum), getRecordList(expectedFile, recordTypeEnum));
    }
    return this;
  }

  public AssertNist hasTheSameRecord3(NistFile expectedFile) {
    RecordTypeEnum recordTypeEnum = RecordTypeEnum.RT3;
    if (isNotEmpty(expectedFile.getRecordListByRecordTypeEnum(recordTypeEnum))) {
      recordAreEquals(
          getRecordList(nistFile, recordTypeEnum), getRecordList(expectedFile, recordTypeEnum));
    }
    return this;
  }

  public AssertNist hasTheSameRecord4(NistFile expectedFile) {
    RecordTypeEnum recordTypeEnum = RecordTypeEnum.RT4;
    if (isNotEmpty(expectedFile.getRecordListByRecordTypeEnum(recordTypeEnum))) {
      recordAreEquals(
          getRecordList(nistFile, recordTypeEnum), getRecordList(expectedFile, recordTypeEnum));
    }
    return this;
  }

  public AssertNist hasTheSameRecord5(NistFile expectedFile) {
    RecordTypeEnum recordTypeEnum = RecordTypeEnum.RT5;
    if (isNotEmpty(expectedFile.getRecordListByRecordTypeEnum(recordTypeEnum))) {
      recordAreEquals(
          getRecordList(nistFile, recordTypeEnum), getRecordList(expectedFile, recordTypeEnum));
    }
    return this;
  }

  public AssertNist hasTheSameRecord6(NistFile expectedFile) {
    RecordTypeEnum recordTypeEnum = RecordTypeEnum.RT6;
    if (isNotEmpty(expectedFile.getRecordListByRecordTypeEnum(recordTypeEnum))) {
      recordAreEquals(
          getRecordList(nistFile, recordTypeEnum), getRecordList(expectedFile, recordTypeEnum));
    }
    return this;
  }

  public AssertNist hasTheSameRecord14(NistFile expectedFile) {
    RecordTypeEnum recordTypeEnum = RecordTypeEnum.RT14;
    if (isNotEmpty(expectedFile.getRecordListByRecordTypeEnum(recordTypeEnum))) {
      recordAreEquals(
          getRecordList(nistFile, recordTypeEnum), getRecordList(expectedFile, recordTypeEnum));
    }
    return this;
  }

  private List<NistRecord> getRecordList(NistFile currentFile, RecordTypeEnum recordTypeEnum) {
    return new ArrayList<>(currentFile.getRecordListByRecordTypeEnum(recordTypeEnum));
  }

  private static void recordAreEquals(List<NistRecord> results, List<NistRecord> expectedRecords) {
    assertThat(results).isNotEmpty();
    assertThat(results.size()).isEqualTo(expectedRecords.size());

    for (int i = 0; i < expectedRecords.size(); i++) {
      NistRecord result = results.get(i);
      NistRecord expected = expectedRecords.get(i);

      assertThat(result.getFields()).isEqualTo(expected.getFields());
    }
  }
}
