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
import static io.github.nist4j.enums.records.RTDefaultFieldsEnum.IDC;
import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistFileImmutableImpl;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.exceptions.Nist4jException;
import java.util.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Getter(AccessLevel.PROTECTED)
@Slf4j
public final class NistFileBuilderImpl implements NistFileBuilder {

  public static final String IDC_NOT_FOUND = "";
  private final NistOptions nistOptions;

  @Getter(AccessLevel.PUBLIC)
  private final Map<RecordTypeEnum, List<NistRecord>> mapOfAllRecords;

  private List<Callback<NistFileBuilder>> beforeBuild;
  private List<Callback<NistFile>> afterBuild;

  public NistFileBuilderImpl(
      @NonNull NistOptions nistOptions,
      @NonNull List<Callback<NistFileBuilder>> beforeBuildCallbacks,
      @NonNull List<Callback<NistFile>> afterBuildCallbacks) {
    this.nistOptions = nistOptions;
    this.mapOfAllRecords = new TreeMap<>();
    this.beforeBuild = beforeBuildCallbacks;
    this.afterBuild = afterBuildCallbacks;
  }

  @Override
  public NistFileBuilder withRecord(Integer recordId, NistRecord nistRecord) {
    return withRecord(RecordTypeEnum.findByRecordId(recordId), nistRecord);
  }

  @Override
  public NistFileBuilder withRecord(
      @NonNull RecordTypeEnum recordType, @NonNull NistRecord nistRecord) {
    List<NistRecord> records = mapOfAllRecords.get(recordType);
    if (isEmpty(records)) {
      records = new ArrayList<>();
      records.add(nistRecord);
      mapOfAllRecords.put(recordType, records);
    } else {
      if (recordType == RT1) {
        records.remove(0);
      }
      records.add(nistRecord);
    }
    return this;
  }

  @Override
  public NistFile build() {
    this.beforeBuild.forEach(callback -> callback.execute(this));

    NistFile nistFile = new NistFileImmutableImpl(this);

    this.afterBuild.forEach(callback -> callback.execute(nistFile));
    return nistFile;
  }

  @Override
  public NistFileBuilder from(NistFile nistFile) {
    this.mapOfAllRecords.clear();
    // inject a mutable copy of NistFile's records
    this.mapOfAllRecords.putAll(new TreeMap<>(nistFile.getMapOfAllrecords()));
    return this;
  }

  @Override
  public NistFileBuilder removeRecord(RecordTypeEnum recordType) {
    mapOfAllRecords.remove(recordType);
    return this;
  }

  @Override
  public NistFileBuilder removeRecord(@NonNull RecordTypeEnum recordType, @NonNull Integer idcId) {
    List<NistRecord> records = mapOfAllRecords.get(recordType);
    if (isNotEmpty(records)) {
      if (RT1.equals(recordType)) {
        mapOfAllRecords.remove(recordType);
      } else {
        findRecordIndexByIdc(records, idcId).ifPresent(i -> records.remove(i.intValue()));
        if (records.isEmpty()) {
          mapOfAllRecords.remove(recordType);
        } else {
          mapOfAllRecords.put(recordType, records);
        }
      }
    }
    return this;
  }

  @Override
  public NistFileBuilder withBeforeBuild(@NonNull Callback<NistFileBuilder> callback) {
    if (isEmpty(this.beforeBuild)) {
      this.beforeBuild = new ArrayList<>();
    }
    this.beforeBuild.add(callback);
    return this;
  }

  @Override
  public NistFileBuilder withAfterBuild(@NonNull Callback<NistFile> callback) {
    if (isEmpty(this.afterBuild)) {
      this.afterBuild = new ArrayList<>();
    }
    this.afterBuild.add(callback);
    return this;
  }

  @Override
  public Optional<NistRecord> getRecordByIdc(
      @NonNull RecordTypeEnum recordType, @NonNull Integer idcId) {

    List<NistRecord> records = this.mapOfAllRecords.get(recordType);
    if (isEmpty(records)) {
      return Optional.empty();
    }
    if (RT1.equals(recordType)) {
      return Optional.ofNullable(records.get(0));
    }
    return findRecordIndexByIdc(records, idcId).map(records::get);
  }

  @Override
  public NistFileBuilder replaceRecord(
      @NonNull RecordTypeEnum recordType, @NonNull Integer idcId, @NonNull NistRecord newRecord) {
    final List<NistRecord> originalRecords = mapOfAllRecords.get(recordType);
    final List<NistRecord> resultRecords = new ArrayList<>();
    final String idcIdStr = String.valueOf(idcId);

    if (RT1.equals(recordType)) {
      resultRecords.add(newRecord);
    } else {
      Optional<Integer> recordIdFound = findRecordIndexByIdc(originalRecords, idcId);
      if (!recordIdFound.isPresent()) {
        throw new Nist4jException(format("Record with idc %s not found", idcId));
      } else {
        for (int i = 0; i < originalRecords.size(); i++) {
          if (i == recordIdFound.get()) {
            if (!newRecord.getFieldText(IDC).isPresent()
                || !idcIdStr.equals(newRecord.getFieldText(IDC).get())) {
              throw new Nist4jException(format("IDC Field must be set to %s in newRecord", idcId));
            }
            resultRecords.add(newRecord);
          } else {
            resultRecords.add(originalRecords.get(i));
          }
        }
      }
    }
    mapOfAllRecords.put(recordType, resultRecords);
    return this;
  }

  protected Optional<Integer> findRecordIndexByIdc(
      @NonNull List<NistRecord> records, @NonNull Integer idcId) {
    final String idcIdStr = String.valueOf(idcId);
    if (!isEmpty(records)) {
      for (int i = 0; i < records.size(); i++) {
        final String idcValueFound = records.get(i).getFieldText(IDC).orElse(IDC_NOT_FOUND);
        if (idcIdStr.equals(idcValueFound)) {
          return Optional.of(i);
        }
      }
    }
    return Optional.empty();
  }
}
