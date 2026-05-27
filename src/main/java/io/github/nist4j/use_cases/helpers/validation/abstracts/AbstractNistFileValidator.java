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
package io.github.nist4j.use_cases.helpers.validation.abstracts;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.options.NistOptionsBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidFieldCollectionOfNistRecord;
import java.util.Collection;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class AbstractNistFileValidator extends AbstractValidator<NistFile> {

  protected static final String EMPTY = null;

  protected static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsBuilderImpl.DefaultOpts.TO_VALIDATE.getOptions();

  private final NistOptions nistOptions;

  protected AbstractNistFileValidator(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
  }

  @SuppressWarnings("SameParameterValue")
  protected HandlerInvalidField<Collection<NistRecord>> handlerInvalidRecordsWithError(
      RecordTypeEnum recordType, @NonNull INistValidationErrorEnum errorEnum) {
    return new HandlerInvalidFieldCollectionOfNistRecord(recordType, errorEnum);
  }
}
