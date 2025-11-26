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

import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static java.util.Collections.singletonList;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl;
import java.util.Collection;
import java.util.Optional;

public class HandlerInvalidFieldNistRecordWithMessage implements HandlerInvalidField<NistRecord> {

  private static final String EMPTY_VALUE = null;
  private final INistValidationErrorEnum error;
  private final RecordTypeEnum recordType;
  private final IFieldTypeEnum field;
  private final String subfieldName;
  private final String message;

  public HandlerInvalidFieldNistRecordWithMessage(
      RecordTypeEnum recordType,
      IFieldTypeEnum field,
      INistValidationErrorEnum error,
      String message) {
    this.recordType = recordType;
    this.field = field;
    this.error = error;
    this.subfieldName = EMPTY_VALUE;
    if (isEmpty(message)) {
      this.message = error.getMessage();
    } else {
      this.message = message;
    }
  }

  @Override
  public Collection<NistValidationError> handle(final NistRecord attemptedRecord) {
    // Get the value of the field specify in error
    // Or the value is absent
    String attemptedValueStr =
        Optional.of(this.field).map(f -> toStringValue(f, attemptedRecord)).orElse(EMPTY_VALUE);

    NistValidationError error =
        new NistValidationErrorBuilderImpl()
            .withRecordType(this.recordType)
            .withFieldType(this.field)
            .withSubfieldName(this.subfieldName)
            .withCode(this.error.getCode())
            .withMessage(message)
            .withAttemptedFound(attemptedValueStr)
            .build();

    return singletonList(error);
  }

  private String toStringValue(IFieldTypeEnum field, NistRecord attemptedRecord) {
    if (attemptedRecord.isFieldText(field)) {
      return attemptedRecord.getFieldText(field).orElse(EMPTY_VALUE);
    } else if (attemptedRecord.isFieldImage(field)) {
      return attemptedRecord
          .getFieldImage(field)
          .map(f -> "IMAGE with size " + f.length)
          .orElse(EMPTY_VALUE);
    } else {
      return EMPTY_VALUE;
    }
  }
}
