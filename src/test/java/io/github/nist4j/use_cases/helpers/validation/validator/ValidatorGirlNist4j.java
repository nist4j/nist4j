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

import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.model.Gender;
import io.github.nist4j.use_cases.helpers.validation.model.Girl;
import io.github.nist4j.use_cases.helpers.validation.predicates.PredicateBuilder;

public class ValidatorGirlNist4j extends AbstractValidator<Girl> {

  @Override
  public void rules() {

    ruleFor(Girl::getGender)
        .must(PredicateBuilder.from(equalObject(Gender.FEMALE)))
        .when(not(nullValue()))
        .withMessage("gender of girl must be FEMALE")
        .withFieldName("gender");

    ruleFor(Girl::getName)
        .must(PredicateBuilder.from(stringContains("Ana")))
        .when(not(stringEmptyOrNull()))
        .withMessage("child name must contains key Ana")
        .withFieldName("name");
  }
}
