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
package io.github.nist4j.use_cases.helpers.mappers;

import static io.github.nist4j.use_cases.helpers.mappers.ErrorMapper.*;
import static java.util.Objects.nonNull;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.entities.validation.impl.NistValidationErrorImpl;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NistValidationErrorMapper {

  private static final String EMPTY = null;

  public List<NistValidationError> fromValidationResult(ValidationResult validationResult) {

    return validationResult.getErrors().stream()
        .map(this::toNistValidationError)
        .collect(Collectors.toList());
  }

  protected NistValidationError toNistValidationError(Error error) {
    final String valueFound =
        nonNull(error.getAttemptedValue()) ? error.getAttemptedValue().toString() : EMPTY;

    return NistValidationErrorImpl.builder()
        .message(error.getMessage())
        .code(error.getCode())
        .valueFound(valueFound)
        .fieldName(getNistFieldFromError(error))
        .recordName(getNistRecordFromError(error))
        .build();
  }
}
