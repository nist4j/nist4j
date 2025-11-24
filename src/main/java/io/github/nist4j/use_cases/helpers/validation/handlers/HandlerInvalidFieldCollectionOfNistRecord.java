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
package io.github.nist4j.use_cases.helpers.validation.handlers;

import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationErrorBuilder;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.Collection;
import java.util.stream.Collectors;

public class HandlerInvalidFieldCollectionOfNistRecord
    implements HandlerInvalidField<Collection<NistRecord>> {

  private final INistValidationErrorEnum error;
  private final RecordTypeEnum recordType;

  public HandlerInvalidFieldCollectionOfNistRecord(
      RecordTypeEnum recordType, INistValidationErrorEnum error) {
    this.recordType = recordType;
    this.error = error;
  }

  @Override
  public Collection<NistValidationError> handle(Collection<NistRecord> attemptedRecords) {
    return attemptedRecords.stream()
        .map(r -> newNistValidationErrorBuilder(recordType, error).withAttemptedFound(r).build())
        .collect(Collectors.toList());
  }
}
