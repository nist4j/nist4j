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

import static java.util.Collections.singletonList;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl;
import java.util.Collection;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HandlerInvalidNistSubfield implements HandlerInvalidField<String> {
  private final INistValidationErrorEnum error;
  private final RecordTypeEnum recordType;
  private final IFieldTypeEnum fieldType;
  private final String subfieldName;

  @Override
  public Collection<NistValidationError> handle(final String attemptedvalue) {

    return singletonList(
        new NistValidationErrorBuilderImpl()
            .withRecordType(this.recordType)
            .withFieldType(this.fieldType)
            .withSubfieldName(this.subfieldName)
            .withCode(this.error.getCode())
            .withMessage(this.error.getMessage())
            .withAttemptedFound(attemptedvalue)
            .build());
  }
}
