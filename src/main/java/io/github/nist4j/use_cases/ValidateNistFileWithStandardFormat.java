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
package io.github.nist4j.use_cases;

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static java.util.Collections.singletonList;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.entities.validation.impl.NistValidationErrorImpl;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.mappers.NistValidationErrorMapper;
import io.github.nist4j.use_cases.helpers.validation.standards.Std2007Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.Std2011Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.Std2013Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.Std2015Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.abstracts.AbstractStdValidator;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidateNistFileWithStandardFormat {

  public static final NistOptions DEFAULT_OPTIONS_FOR_VALIDATION =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  public static final String EMPTY_VALUE = null;
  private final Std2007Validator std2007Validator;
  private final Std2011Validator std2011Validator;
  private final Std2013Validator std2013Validator;
  private final Std2015Validator std2015Validator;
  private final NistValidationErrorMapper nistValidationErrorMapper;

  public ValidateNistFileWithStandardFormat() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public ValidateNistFileWithStandardFormat(NistOptions nistOptions) {
    this.std2007Validator = new Std2007Validator(nistOptions);
    this.std2011Validator = new Std2011Validator(nistOptions);
    this.std2013Validator = new Std2013Validator(nistOptions);
    this.std2015Validator = new Std2015Validator(nistOptions);
    this.nistValidationErrorMapper = new NistValidationErrorMapper();
  }

  public List<NistValidationError> execute(@NonNull NistFile nist) {
    Optional<String> stdVersion = extractStandardVersion(nist);
    log.debug("Validate a NistFile : checking nistStandardEnum {}", stdVersion);
    Optional<NistStandardEnum> nistStandardEnum = stdVersion.flatMap(NistStandardEnum::findByCode);

    if (!nistStandardEnum.isPresent()) {
      return singletonList(
          NistValidationErrorImpl.builder()
              .code(STD_ERR_MISSING_STANDARD.getCode())
              .message(STD_ERR_MISSING_STANDARD.getMessage())
              .fieldName(STD_ERR_MISSING_STANDARD.getFieldName())
              .valueFound(stdVersion.orElse(EMPTY_VALUE))
              .build());
    } else {
      Optional<AbstractStdValidator> stdValidator = getValidatorByStandard(nistStandardEnum.get());
      if (!stdValidator.isPresent()) {
        return singletonList(
            NistValidationErrorImpl.builder()
                .code(STD_ERR_UNIMPLEMENTED_STANDARD.getCode())
                .message(STD_ERR_UNIMPLEMENTED_STANDARD.getMessage())
                .fieldName(STD_ERR_UNIMPLEMENTED_STANDARD.getFieldName())
                .valueFound(stdVersion.orElse(EMPTY_VALUE))
                .build());
      } else {
        return nistValidationErrorMapper.fromValidationResult(stdValidator.get().validate(nist));
      }
    }
  }

  private Optional<AbstractStdValidator> getValidatorByStandard(NistStandardEnum standard) {
    switch (standard) {
      case ANSI_NIST_ITL_2007:
        return Optional.of(std2007Validator);
      case ANSI_NIST_ITL_2011:
        return Optional.of(std2011Validator);
      case ANSI_NIST_ITL_2013:
        return Optional.of(std2013Validator);
      case ANSI_NIST_ITL_2015:
        return Optional.of(std2015Validator);
      default:
        log.error("Validate nistFile - ERROR: the standard is not implemented");
        return Optional.empty();
    }
  }

  private Optional<String> extractStandardVersion(NistFile nist) {
    return Optional.ofNullable(nist)
        .map(NistFile::getRT1TransactionInformationRecord)
        .flatMap(recordBuilder -> recordBuilder.getFieldText(RT1FieldsEnum.VER));
  }
}
