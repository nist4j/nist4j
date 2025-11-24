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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationErrorBuilder;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.empty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasSize;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.greaterThanOrEqual;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.lessThanOrEqual;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringContains;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import io.github.nist4j.use_cases.helpers.validation.model.Boy;
import io.github.nist4j.use_cases.helpers.validation.model.Child;
import io.github.nist4j.use_cases.helpers.validation.model.Girl;
import io.github.nist4j.use_cases.helpers.validation.model.Parent;
import io.github.nist4j.use_cases.helpers.validation.predicates.PredicateBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class ValidatorParent extends AbstractValidator<Parent> {

  @Override
  public void rules() {

    setPropertyOnContext("parent");

    ruleForEach(Parent::getChildren)
        .must(not(nullValue()))
        .handlerInvalidField(
            new HandlerInvalidField<Collection<Child>>() {
              @Override
              public Collection<NistValidationError> handle(
                  final Collection<Child> attemptedValue) {
                return Collections.singletonList(
                    newNistValidationErrorBuilder()
                        .withRecordType(RT1)
                        .withFieldType(RT1FieldsEnum.VER)
                        .withCode("555")
                        .withMessage("parent's children cannot be null")
                        .withAttemptedFound(attemptedValue)
                        .build());
              }
            })
        .must(not(empty()))
        .when(not(nullValue()))
        .withMessage("parent must have at least one child")
        .withFieldType(RT1FieldsEnum.VER)
        .whenever(not(nullValue()))
        .withValidator(new ValidatorChildNist4j())
        .critical()
        .whenever(not(nullValue()));

    ruleFor(Parent::getId)
        .whenever(not(nullValue()))
        .withValidator(new ValidatorIdNist4j())
        .critical();

    ruleFor(Parent::getAge)
        .must(greaterThanOrEqual(5))
        .when(not(nullValue()))
        .withMessage("age must be greater than or equal to 10")
        .withFieldType(RT1FieldsEnum.VER)
        .must(lessThanOrEqual(7))
        .when(not(nullValue()))
        .withMessage("age must be less than or equal to 7")
        .withCode("666")
        .withFieldType(RT1FieldsEnum.VER);

    ruleFor(Parent::getCities)
        .must(hasSize(10))
        .when(not(nullValue()))
        .withMessage("cities size must be 10")
        .withFieldType(RT1FieldsEnum.VER);

    ruleFor(Parent::getName)
        .must(stringContains("John"))
        .when(not(stringEmptyOrNull()))
        .withMessage("name must contains key John")
        .withFieldType(RT1FieldsEnum.VER);

    ruleForEach(parent -> extractGirls(parent.getChildren()))
        .whenever(PredicateBuilder.<Collection<Girl>>from(not(nullValue())).and(not(empty())))
        .withValidator(new ValidatorGirlNist4j())
        .critical();

    ruleForEach(parent -> extractBoys(parent.getChildren()))
        .whenever(PredicateBuilder.<Collection<Boy>>from(not(nullValue())).and(not(empty())))
        .withValidator(new ValidatorBoyNist4j())
        .critical();
  }

  private Collection<Girl> extractGirls(final Collection<Child> children) {
    return Optional.ofNullable(children).orElseGet(ArrayList::new).stream()
        .filter(Girl.class::isInstance)
        .map(Girl.class::cast)
        .collect(Collectors.toList());
  }

  private Collection<Boy> extractBoys(final Collection<Child> children) {
    return Optional.ofNullable(children).orElseGet(ArrayList::new).stream()
        .filter(Boy.class::isInstance)
        .map(Boy.class::cast)
        .collect(Collectors.toList());
  }
}
