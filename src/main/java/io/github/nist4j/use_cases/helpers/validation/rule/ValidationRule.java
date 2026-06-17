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
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
interface ValidationRule<T, P> extends Rule<P> {

  void when(final Predicate<P> when);

  void must(final Predicate<P> must);

  void withFieldType(final Function<?, IFieldTypeEnum> fieldType);

  void withRecordType(final Function<?, RecordTypeEnum> recordType);

  void withSubfieldName(final Function<?, String> subfieldName);

  void withMessage(final Function<?, String> message);

  void withCode(final Function<?, String> code);

  void withAttemptedValue(final Function<?, Object> attemptedValue);

  void withHandlerInvalidField(final HandlerInvalidField<P> handleInvalid);

  void whenever(final Predicate<P> whenever);

  void withValidator(final Validator<T> validator);
}
