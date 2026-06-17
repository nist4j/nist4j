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
package io.github.nist4j.use_cases.helpers.validation.builder;

import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.function.Function;

@SuppressWarnings("unused")
public interface When<T, P, W extends When<T, P, W, N>, N extends Whenever<T, P, W, N>> {

  Code<T, P, W, N> withCode(final String code);

  Code<T, P, W, N> withCode(final Function<T, String> code);

  Message<T, P, W, N> withMessage(final String message);

  Message<T, P, W, N> withMessage(final Function<T, String> message);

  FieldType<T, P, W, N> withFieldType(final IFieldTypeEnum fieldType);

  FieldType<T, P, W, N> withFieldType(final Function<T, IFieldTypeEnum> fieldType);

  RecordType<T, P, W, N> withRecordType(final RecordTypeEnum recordType);

  RecordType<T, P, W, N> withRecordType(final Function<T, RecordTypeEnum> recordType);

  SubfieldName<T, P, W, N> withSubfieldName(final String subfieldName);

  SubfieldName<T, P, W, N> withSubfieldName(final Function<T, String> subfieldName);

  AttemptedValue<T, P, W, N> withAttemptedValue(final Object attemptedValue);

  AttemptedValue<T, P, W, N> withAttemptedValue(final Function<T, Object> attemptedValue);

  HandleInvalidField<T, P, W, N> handlerInvalidField(
      final HandlerInvalidField<P> handlerInvalidField);
}
