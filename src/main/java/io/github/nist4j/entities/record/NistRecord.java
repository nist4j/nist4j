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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface NistRecord {
  Integer getRecordId();

  String getRecordName();

  Set<IFieldTypeEnum> getIFieldTypeEnumValues();

  List<Data> getAllFields();

  Optional<Data> getFieldData(IFieldTypeEnum field);

  Optional<Data> getFieldData(Integer id);

  Optional<String> getFieldText(IFieldTypeEnum field);

  Optional<String> getFieldText(Integer id);

  Optional<Integer> getFieldAsInt(IFieldTypeEnum field);

  Optional<Integer> getFieldAsInt(Integer id);

  Optional<byte[]> getFieldImage(IFieldTypeEnum field);

  Optional<byte[]> getFieldImage(Integer id);

  Map<Integer, Data> getFields();

  Optional<Integer> getFieldLength(IFieldTypeEnum field);

  Optional<Integer> getFieldLength(Integer id);

  Optional<IFieldTypeEnum> findFieldEnumById(Integer id);
}
