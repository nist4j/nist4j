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

import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.greaterThanOrEqual;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.model.Child;
import io.github.nist4j.use_cases.helpers.validation.model.Parent;

public class ValidatorChildNist4j extends AbstractValidator<Child> {

  @Override
  public void rules() {

    setPropertyOnContext("child");

    ruleFor(Child::getAge)
        .must(not(nullValue()))
        .withMessage("child age must be not null")
        .withFieldName("age")
        .critical()
        .must(greaterThanOrEqual(5))
        .when(not(nullValue()))
        .withMessage("child age must be greater than or equal to 5")
        .withFieldName("age")
        .must(this::checkAgeConstraintChild)
        .when(not(nullValue()))
        .withMessage("child age must be less than age parent")
        .withFieldName("age")
        .critical();

    ruleFor(Child::getName)
        .must(not(stringEmptyOrNull()))
        .withMessage("child name must be not null or empty")
        .withFieldName("name");
  }

  private boolean checkAgeConstraintChild(final Integer age) {
    return age < getPropertyOnContext("parent", Parent.class).getAge();
  }
}
