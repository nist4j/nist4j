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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14;

import static br.com.fluentvalidator.predicate.StringPredicate.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.validation.NistValidationRegexBuilder;

public class Std2013RT14Validator extends Std2011RT14Validator {

  protected Std2013RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2013RT14Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2013;
  }

  @Override
  public void rules() {
    //  Inherit from 2011
    super.rules();
    checkForSUBField();
    checkForCONField();
  }

  protected void checkForSUBField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.SUB,
        StdNistValidatorErrorEnum.STD_ERR_SUB_RT14,
        // match format, if present
        stringEmptyOrNull().or(validateFieldSUB()));
  }

  protected void checkForCONField() {
    checkCustomPredicateOnField(
        RT14FieldsEnum.CON,
        StdNistValidatorErrorEnum.STD_ERR_CON_RT14,
        // match format, if present
        stringEmptyOrNull()
            .or(
                stringSizeBetween(1, 1000)
                    .and(stringMatches(NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH))));
  }
}
