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

import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.greaterThan;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;

import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.model.Bill;
import io.github.nist4j.use_cases.helpers.validation.predicates.LocalDatePredicate;
import java.time.LocalDate;

public class ValidatorBillNist4j extends AbstractValidator<Bill> {

  @Override
  public void rules() {
    ruleFor(Bill::getDescription)
        .must(not(stringEmptyOrNull()))
        .withMessage("A description is required")
        .withFieldType(RT1FieldsEnum.DAI)
        .withAttemptedValue(Bill::getDescription);

    ruleFor(Bill::getValue)
        .must(not(nullValue()))
        .withMessage("A value must be provided")
        .withFieldType(RT1FieldsEnum.VER)
        .withAttemptedValue(Bill::getValue)
        .must(greaterThan((float) 0))
        .withMessage("Bill value must be greather than 0")
        .withFieldType(RT1FieldsEnum.VER)
        .withAttemptedValue(Bill::getValue);

    ruleFor(bill -> bill)
        .must(LocalDatePredicate.localDateAfterToday(Bill::getDueDate))
        .withMessage("Only future bills are allowed")
        .withFieldType(RT1FieldsEnum.GMT)
        .withAttemptedValue(Bill::getDueDate)
        .must(
            LocalDatePredicate.localDateBetweenOrEqual(
                Bill::getDueDate, LocalDate.now(), LocalDate.now().plusYears(3)))
        .withMessage("Max due date is 3 years ahead")
        .withFieldType(RT1FieldsEnum.DAT)
        .withAttemptedValue(Bill::getDueDate);
  }
}
