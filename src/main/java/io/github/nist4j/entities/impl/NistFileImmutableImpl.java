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
import static io.github.nist4j.enums.records.RTDefaultFieldsEnum.IDC;
import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@EqualsAndHashCode(cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY, of = "mapOfAllrecords")
@ToString
@Slf4j
public final class NistFileImmutableImpl implements NistFile {

  private final Map<RecordTypeEnum, List<NistRecord>> mapOfAllrecords;

  public NistFileImmutableImpl(NistFileBuilder nistFileBuilder) {
    this.mapOfAllrecords = unmodifiableMap(nistFileBuilder.getMapOfAllRecords());
  }

  public List<NistRecord> getRecordListByRecordTypeEnum(RecordTypeEnum recordTypeEnum) {
    log.debug("getRecordListByRecordTypeEnum {}", recordTypeEnum.getNumber());
    return ofNullable(this.mapOfAllrecords)
        .map(enumMap -> enumMap.get(recordTypeEnum))
        .orElse(emptyList());
  }

  @Override
  public Optional<NistRecord> getRecordByTypeAndIdc(RecordTypeEnum recordType, Integer idcId) {
    return ofNullable(this.getMapOfAllrecords().get(recordType)).orElse(emptyList()).stream()
        .filter(r -> checkIfRT1orIdcIdIsEquals(r, idcId))
        .findFirst();
  }

  private boolean checkIfRT1orIdcIdIsEquals(NistRecord record, Integer idcId) {
    Integer recordIdcId = record.getFieldText(IDC).map(Integer::parseInt).orElse(-1);
    return record.getRecordId().equals(RT1.getNumber()) || idcId.equals(recordIdcId);
  }

  @Override
  public Map<RecordTypeEnum, List<NistRecord>> getRxMapDefaultRecords() {
    Map<RecordTypeEnum, List<NistRecord>> partialMap = new TreeMap<>();
    Arrays.stream(RecordTypeEnum.values())
        .filter(r -> r.getNumber() > 17)
        .forEach(
            r -> partialMap.put(r, ofNullable(getMapOfAllrecords().get(r)).orElse(emptyList())));
    return partialMap;
  }

  @Override
  public NistRecord getRT1TransactionInformationRecord() {
    List<NistRecord> records = getMapOfAllrecords().get(RT1);
    if (records == null || records.size() != 1) {
      throw new InvalidFormatNist4jException("Only one record of type 1 is supported");
    }
    return records.get(0);
  }

  @Override
  public List<NistRecord> getRT2UserDefinedDescriptionTextRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT2);
  }

  @Override
  public List<NistRecord> getRT3LowResolutionGrayscaleFingerprintRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT3);
  }

  @Override
  public List<NistRecord> getRT4HighResolutionGrayscaleFingerprintRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT4);
  }

  @Override
  public List<NistRecord> getRT5LowResolutionBinaryFingerprintRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT5);
  }

  @Override
  public List<NistRecord> getRT6HighResolutionBinaryFingerprintRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT6);
  }

  @Override
  public List<NistRecord> getRT7UserDefinedImageRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT7);
  }

  @Override
  public List<NistRecord> getRT8SignatureImageRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT8);
  }

  @Override
  public List<NistRecord> getRT9MinutiaeDataRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT9);
  }

  @Override
  public List<NistRecord> getRT10FacialAndSmtImageRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT10);
  }

  @Override
  public List<NistRecord> getRT11thRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT11);
  }

  @Override
  public List<NistRecord> getRT12thRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT12);
  }

  @Override
  public List<NistRecord> getRT13VariableResolutionLatentImageRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT13);
  }

  @Override
  public List<NistRecord> getRT14VariableResolutionFingerprintRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT14);
  }

  @Override
  public List<NistRecord> getRT15VariableResolutionPalmprintRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT15);
  }

  @Override
  public List<NistRecord> getRT16UserDefinedTestingImageRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT16);
  }

  @Override
  public List<NistRecord> getRT17IrisImageRecords() {
    return getNistRecordOrEmptyList(RecordTypeEnum.RT17);
  }

  private List<NistRecord> getNistRecordOrEmptyList(RecordTypeEnum recordType) {
    return ofNullable(getMapOfAllrecords().get(recordType)).orElse(emptyList());
  }
}
