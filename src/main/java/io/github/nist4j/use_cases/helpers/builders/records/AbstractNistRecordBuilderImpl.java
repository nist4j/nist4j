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

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.record.impl.DefaultRecordImmutableImpl;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
@AllArgsConstructor
public class AbstractNistRecordBuilderImpl implements NistRecordBuilder {

  private final NistOptions nistOptions;
  private final Integer recordId;
  private final String recordName;
  private final Map<Integer, Data> fields;
  private List<Callback<NistRecordBuilder>> beforeBuild;
  private List<Callback<NistRecord>> afterBuild;

  public AbstractNistRecordBuilderImpl(
      @NonNull NistOptions nistOptions,
      @NonNull Integer recordId,
      @NonNull String recordName,
      @NonNull List<Callback<NistRecordBuilder>> beforeBuildCallbacks,
      @NonNull List<Callback<NistRecord>> afterBuildCallbacks) {
    this.nistOptions = nistOptions;
    this.recordId = recordId;
    this.recordName = recordName;
    this.fields = new TreeMap<>();
    this.beforeBuild = beforeBuildCallbacks;
    this.afterBuild = afterBuildCallbacks;
  }

  @Override
  public NistRecordBuilder from(NistRecord record) {
    // Copy immutable to mutable map
    TreeMap<Integer, Data> mutableFields = new TreeMap<>(record.getFields());
    this.fields.clear(); // Clear actual data with new
    this.fields.putAll(mutableFields);
    return this;
  }

  @Override
  public NistRecordBuilder withField(@NonNull Integer fieldTypeId, @NonNull Data data) {
    fields.put(fieldTypeId, data);
    return this;
  }

  @Override
  public NistRecordBuilder withField(@NonNull IFieldTypeEnum fieldType, @NonNull Data data) {
    return withField(fieldType.getId(), data);
  }

  @Override
  public NistRecordBuilder removeField(@NonNull IFieldTypeEnum fieldType) {
    return removeField(fieldType.getId());
  }

  @Override
  public NistRecordBuilder removeField(@NonNull Integer fieldId) {
    this.fields.remove(fieldId);
    return this;
  }

  @Override
  public NistRecord build() {
    this.beforeBuild.forEach(callback -> callback.execute(this));

    NistRecord nistRecord = new DefaultRecordImmutableImpl(this.recordId, this);

    this.afterBuild.forEach(callback -> callback.execute(nistRecord));
    return nistRecord;
  }

  @Override
  public NistRecordBuilder withBeforeBuild(@NonNull Callback<NistRecordBuilder> callback) {
    if (isEmpty(this.beforeBuild)) {
      this.beforeBuild = new ArrayList<>();
    }
    this.beforeBuild.add(callback);
    return this;
  }

  @Override
  public NistRecordBuilder withAfterBuild(@NonNull Callback<NistRecord> callback) {
    if (isEmpty(this.afterBuild)) {
      this.afterBuild = new ArrayList<>();
    }
    this.afterBuild.add(callback);
    return this;
  }

  @Override
  public NistRecordBuilder newBuilder() {
    return new AbstractNistRecordBuilderImpl(
        nistOptions, recordId, recordName, new TreeMap<>(), beforeBuild, afterBuild);
  }
}
