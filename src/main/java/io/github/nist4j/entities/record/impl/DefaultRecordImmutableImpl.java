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
package io.github.nist4j.entities.record.impl;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.entities.record.abstracts.AbstractRecordImmutable;
import io.github.nist4j.enums.records.RTDefaultFieldsEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class DefaultRecordImmutableImpl extends AbstractRecordImmutable
    implements NistRecord {

  public DefaultRecordImmutableImpl(Integer recordId, NistRecordBuilder nistRecordBuilder) {
    super(recordId, "RecordType " + recordId, nistRecordBuilder);
  }

  @Override
  public Set<IFieldTypeEnum> getIFieldTypeEnumValues() {
    return new HashSet<>(Arrays.asList(RTDefaultFieldsEnum.values()));
  }
}
