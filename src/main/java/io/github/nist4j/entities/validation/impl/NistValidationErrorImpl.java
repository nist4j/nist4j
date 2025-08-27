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
package io.github.nist4j.entities.validation.impl;

import static java.util.Objects.isNull;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.entities.validation.NistValidationErrorBuilder;
import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class NistValidationErrorImpl implements NistValidationError {

  private final String recordName;
  private final String fieldName;
  private final String code;
  private final String message;
  private final Object attemptedFound;

  public NistValidationErrorImpl(NistValidationErrorBuilder builder) {
    this(
        builder.getRecordName(),
        builder.getFieldName(),
        builder.getCode(),
        builder.getMessage(),
        builder.getAttemptedFound());
  }

  @Override
  public String getValueFound() {
    if (isNull(this.attemptedFound)) {
      return null;
    } else {
      return this.attemptedFound.toString();
    }
  }

  @Override
  public String toString() {
    return "NistValidationError{"
        + "code='"
        + this.code
        + '\''
        + ", record='"
        + this.recordName
        + '\''
        + ", fieldName='"
        + this.fieldName
        + '\''
        + ", message='"
        + this.message
        + '\''
        + ", valueFound='"
        + this.getValueFound()
        + '\''
        + '}';
  }
}
