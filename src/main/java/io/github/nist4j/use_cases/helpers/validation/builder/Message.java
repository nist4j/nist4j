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

import io.github.nist4j.use_cases.helpers.validation.exceptions.Nist4jValidationException;
import java.util.function.Function;

@SuppressWarnings("unused")
public interface Message<T, P, W extends When<T, P, W, N>, N extends Whenever<T, P, W, N>>
    extends RuleBuilder<T, P, W, N> {

  Code<T, P, W, N> withCode(final String code);

  Code<T, P, W, N> withCode(final Function<T, String> code);

  FieldName<T, P, W, N> withFieldName(final String fieldName);

  FieldName<T, P, W, N> withFieldName(final Function<T, String> fieldName);

  RecordName<T, P, W, N> withRecordName(final String recordName);

  RecordName<T, P, W, N> withRecordName(final Function<T, String> recordName);

  AttemptedValue<T, P, W, N> withAttemptedValue(final Object attemptedValue);

  AttemptedValue<T, P, W, N> withAttemptedValue(final Function<T, Object> attemptedValue);

  Critical<T, P, W, N> critical();

  Critical<T, P, W, N> critical(final Class<? extends Nist4jValidationException> clazz);
}
