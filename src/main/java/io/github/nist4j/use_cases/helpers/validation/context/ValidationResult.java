/*
 * Copyright (C) 2019 Sopra Steria.
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
package io.github.nist4j.use_cases.helpers.validation.context;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.use_cases.helpers.validation.exceptions.Nist4jValidationException;
import java.util.*;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class ValidationResult {

  private final boolean valid;

  private final List<NistValidationError> errors;

  public static ValidationResult ok() {
    return new ValidationResult(true, new ArrayList<>());
  }

  public static ValidationResult fail(final List<NistValidationError> messages) {
    return new ValidationResult(false, Optional.ofNullable(messages).orElse(new ArrayList<>()));
  }

  private ValidationResult(final boolean valid, final List<NistValidationError> messages) {
    this.valid = valid;
    this.errors = Collections.unmodifiableList(messages);
  }

  public <T extends Nist4jValidationException> void isInvalidThrow(final Class<T> clazz) {
    if (!isValid()) {
      throw Nist4jValidationException.create(clazz, this);
    }
  }
}
