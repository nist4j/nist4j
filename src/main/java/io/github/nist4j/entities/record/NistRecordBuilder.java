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

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import java.util.Map;

public interface NistRecordBuilder {
  NistRecordBuilder newBuilder();

  NistRecordBuilder from(NistRecord record);

  NistRecordBuilder withField(Integer fieldTypeId, Data data);

  NistRecordBuilder withField(IFieldTypeEnum fieldType, Data data);

  Map<Integer, Data> getFields();

  NistRecord build();

  Integer getRecordId();

  String getRecordName();

  NistRecordBuilder withBeforeBuild(Callback<NistRecordBuilder> callback);

  NistRecordBuilder withAfterBuild(Callback<NistRecord> callback);

  NistRecordBuilder removeField(IFieldTypeEnum fieldType);

  NistRecordBuilder removeField(Integer fieldId);
}
