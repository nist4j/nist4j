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
package io.github.nist4j.test_utils;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AssertValidator {

  private final List<NistValidationError> errorsList;

  public static AssertValidator assertThatErrors(@NonNull List<NistValidationError> errorsList) {
    return new AssertValidator(errorsList);
  }

  public AssertValidator containsError(INistValidationErrorEnum errorEnum) {
    assertThat(toErrorCodesList(errorsList)).contains(errorEnum.getCode());
    return this;
  }

  public AssertValidator containsErrorWithValue(
      INistValidationErrorEnum errorEnum, String receivedValue) {
    assertThat(toErrorCodesList(errorsList)).contains(errorEnum.getCode());
    Optional<NistValidationError> errorNistOptional = getErrorFromCode(errorsList, errorEnum);
    assertThat(errorNistOptional.map(NistValidationError::getValueFound).orElse(null))
        .isEqualTo(receivedValue);
    return this;
  }

  private static List<String> toErrorCodesList(List<NistValidationError> errorsNist) {
    return errorsNist.stream().map(NistValidationError::getCode).collect(Collectors.toList());
  }

  private static Optional<NistValidationError> getErrorFromCode(
      List<NistValidationError> errorsNist, INistValidationErrorEnum error) {
    return errorsNist.stream()
        .filter(e -> Objects.equals(e.getCode(), error.getCode()))
        .findFirst();
  }

  public AssertValidator doesNotContainsError(INistValidationErrorEnum errorEnum) {
    assertThat(toErrorCodesList(errorsList)).doesNotContain(errorEnum.getCode());
    return this;
  }
}
