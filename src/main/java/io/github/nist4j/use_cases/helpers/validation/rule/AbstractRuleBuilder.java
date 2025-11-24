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
package io.github.nist4j.use_cases.helpers.validation.rule;

import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.builder.*;
import java.util.function.Function;

abstract class AbstractRuleBuilder<T, P, W extends When<T, P, W, N>, N extends Whenever<T, P, W, N>>
    implements Must<T, P, W, N>,
        Message<T, P, W, N>,
        SubfieldName<T, P, W, N>,
        FieldType<T, P, W, N>,
        RecordType<T, P, W, N>,
        Code<T, P, W, N>,
        Critical<T, P, W, N>,
        WithValidator<T, P, W, N>,
        HandleInvalidField<T, P, W, N>,
        AttemptedValue<T, P, W, N>,
        Rule<T> {

  @SuppressWarnings("unused")
  protected final Function<T, RecordTypeEnum> recordType;

  protected final Function<T, IFieldTypeEnum> fieldType;
  protected final Function<T, String> subfieldName;
  protected final Function<T, P> function;

  protected AbstractRuleBuilder(
      final Function<T, RecordTypeEnum> recordType,
      final Function<T, IFieldTypeEnum> fieldType,
      final Function<T, String> subfieldName,
      final Function<T, P> function) {
    this.recordType = recordType;
    this.fieldType = fieldType;
    this.subfieldName = subfieldName;
    this.function = function;
  }

  protected AbstractRuleBuilder(
      final RecordTypeEnum recordType,
      final IFieldTypeEnum fieldType,
      final String subfieldName,
      final Function<T, P> function) {
    this(obj -> recordType, obj -> fieldType, obj -> subfieldName, function);
  }

  protected AbstractRuleBuilder(final Function<T, P> function) {
    this(obj -> null, obj -> null, obj -> null, function);
  }
}
