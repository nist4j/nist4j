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
package io.github.nist4j.entities.validation.impl;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
@AllArgsConstructor
@Builder
public class NistValidationErrorImpl implements NistValidationError {

  private String code;
  private String recordName;
  private String fieldName;
  private String message;
  private String valueFound;

  @Override
  public String toString() {
    return "NistValidationError{"
        + "code='"
        + code
        + '\''
        + ", record='"
        + recordName
        + '\''
        + ", fieldName='"
        + fieldName
        + '\''
        + ", message='"
        + message
        + '\''
        + ", valueFound='"
        + valueFound
        + '\''
        + '}';
  }

  public NistValidationErrorImpl(INistValidationErrorEnum iNistValidationErrorEnum) {
    this.code = iNistValidationErrorEnum.getCode();
    this.message = iNistValidationErrorEnum.getMessage();
    this.fieldName = iNistValidationErrorEnum.getFieldName();
    Optional.ofNullable(iNistValidationErrorEnum.getFieldTypeEnum())
        .ifPresent(fieldType -> this.recordName = fieldType.getRecordType());
  }
}
