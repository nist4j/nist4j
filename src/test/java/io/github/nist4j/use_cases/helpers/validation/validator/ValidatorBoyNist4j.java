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
package io.github.nist4j.use_cases.helpers.validation.validator;

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.equalObject;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringContains;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.model.Boy;
import io.github.nist4j.use_cases.helpers.validation.model.Gender;

public class ValidatorBoyNist4j extends AbstractValidator<Boy> {

  @Override
  public void rules() {

    ruleFor(Boy::getGender)
        .must(equalObject(Gender.MALE))
        .when(not(nullValue()))
        .withMessage("gender of boy must be MALE")
        .withFieldType(RT1FieldsEnum.VER)
        .critical();

    ruleFor(Boy::getName)
        .must(stringContains("John"))
        .when(not(stringEmptyOrNull()))
        .withMessage("child name must contains key John")
        .withFieldType(RT1FieldsEnum.VER);
  }
}
