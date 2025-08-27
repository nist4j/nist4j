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
package io.github.nist4j.use_cases.helpers.validation.playground.validator;

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.playground.model.Student;

// @formatter:off
public class StudentValidatorAnotherWay01Nist4j extends AbstractValidator<Student> {

  @Override
  public void rules() {

    ruleFor(n -> n)
        .must(not(stringEmptyOrNull(n -> n.getId())))
        .when(stringEmptyOrNull(n -> n.getEnrolmentId()))
        .withMessage("Enrolment Id is null, Id must not be null");

    ruleFor(n -> n)
        .must(not(stringEmptyOrNull(n -> n.getEnrolmentId())))
        .when(stringEmptyOrNull(n -> n.getId()))
        .withMessage("Id is null, Enrolment Id must not be null");
  }
}
// @formatter:on
