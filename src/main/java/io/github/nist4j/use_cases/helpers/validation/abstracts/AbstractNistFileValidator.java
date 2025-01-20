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

import static io.github.nist4j.use_cases.helpers.mappers.ErrorMapper.toErrorOnRecord;

import br.com.fluentvalidator.AbstractValidator;
import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.handler.HandlerInvalidField;
import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class AbstractNistFileValidator extends AbstractValidator<NistFile> {

  protected static final String EMPTY = null;

  protected static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  private final NistOptions nistOptions;

  protected AbstractNistFileValidator(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
  }

  protected HandlerInvalidField<Collection<NistRecord>> handlerInvalidRecordsWithError(
      @NonNull INistValidationErrorEnum error) {
    return new HandlerInvalidField<Collection<NistRecord>>() {
      @Override
      public Collection<Error> handle(Collection<NistRecord> attemptedValue) {
        String attemptedValueStr =
            attemptedValue.stream().findFirst().map(r -> "RT" + r.getRecordId()).orElse(null);
        return Collections.singletonList(toErrorOnRecord(error, attemptedValueStr));
      }
    };
  }
}
