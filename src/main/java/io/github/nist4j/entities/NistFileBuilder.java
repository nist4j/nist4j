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
package io.github.nist4j.entities;

import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.RecordTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;

public interface NistFileBuilder {
  Map<RecordTypeEnum, List<NistRecord>> getMapOfAllRecords();

  NistFileBuilder withRecord(Integer recordId, NistRecord nistRecord);

  NistFileBuilder withRecord(RecordTypeEnum recordType, NistRecord nistRecord);

  NistFile build();

  NistFileBuilder from(NistFile nistFile);

  NistFileBuilder removeRecord(RecordTypeEnum recordType);

  NistFileBuilder removeRecord(RecordTypeEnum recordType, Integer idcId);

  NistFileBuilder withBeforeBuild(Callback<NistFileBuilder> callback);

  NistFileBuilder withAfterBuild(Callback<NistFile> callback);

  Optional<NistRecord> getRecordByIdc(@NonNull RecordTypeEnum recordType, @NonNull Integer idcId);

  NistFileBuilder replaceRecord(RecordTypeEnum recordType, Integer idcId, NistRecord rtModify);
}
