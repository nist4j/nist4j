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

import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ImmutableSubfieldRuleImpl implements SubfieldRule {
  private final String subfieldName;
  private final Predicate<String> validator;
  private final INistValidationErrorEnum error;

  private static final long serialVersionUID = -4993393567139500104L;

  public static ImmutableSubfieldRuleImpl of(
      final String subfieldName,
      final Predicate<String> validator,
      final INistValidationErrorEnum error) {

    return new ImmutableSubfieldRuleImpl(subfieldName, validator, error);
  }
}
