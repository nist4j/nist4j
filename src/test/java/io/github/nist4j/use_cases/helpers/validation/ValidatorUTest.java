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
package io.github.nist4j.use_cases.helpers.validation;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.model.Bill;
import io.github.nist4j.use_cases.helpers.validation.model.Boy;
import io.github.nist4j.use_cases.helpers.validation.model.Girl;
import io.github.nist4j.use_cases.helpers.validation.model.Parent;
import io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate;
import io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate;
import io.github.nist4j.use_cases.helpers.validation.validator.ValidatorBillNist4j;
import io.github.nist4j.use_cases.helpers.validation.validator.ValidatorParent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class ValidatorUTest {

  @Test
  public void validationMustBeSuccess() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent.setChildren(Arrays.asList(new Boy("John", 5), new Girl("Ana", 5)));

    final ValidationResult result = validatorParent.validate(parent);

    assertTrue(result.isValid());
    assertThat(result.getErrors()).isEmpty();
  }

  @Test
  public void validationMustBeFailWhenFieldOfParentAreInvalid() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(10);
    parent.setName("Ana");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent.setChildren(singletonList(new Boy("John", 5)));

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(3);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getAge());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("age must be less than or equal to 7");
    assertThat(result.getErrors().stream().map(NistValidationError::getCode)).contains("666");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getCities());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("cities size must be 10");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getName());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("name must contains key John");
  }

  @Test
  public void validationMustBeFailWhenFieldOfParentAreInvalidValidation() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setId("invalid");
    parent.setAge(10);
    parent.setName("Ana");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent.setChildren(singletonList(new Boy("John", 5)));

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(4);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getId());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("id not matching the pattern of a UUID");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getName());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("name must contains key John");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getCities());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("cities size must be 10");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getId());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("age must be less than or equal to 7");
  }

  @Test
  public void validationMustBeFailWhenChildAgeGreateThanParentAgeInvalid() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent.setChildren(singletonList(new Boy("John", 6)));

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(1);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getAge());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("child age must be less than age parent");
  }

  @Test
  public void validationTwiceDiferentParentMustBeSuccess() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent1 = new Parent();

    parent1.setAge(6);
    parent1.setName("John Gow");
    parent1.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent1.setChildren(singletonList(new Boy("John", 5)));

    final Parent parent2 = new Parent();

    parent2.setAge(10);
    parent2.setName("Ana");
    parent2.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent2.setChildren(singletonList(new Boy("John", 5)));

    final ValidationResult result1 = validatorParent.validate(parent1);
    final ValidationResult result2 = validatorParent.validate(parent2);

    assertTrue(result1.isValid());
    assertThat(result1.getErrors()).isEmpty();

    assertFalse(result2.isValid());
    assertThat(result2.getErrors()).isNotEmpty();
    assertThat(result2.getErrors()).hasSize(3);

    assertThat(result2.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result2.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent2.getAge());
    assertThat(result2.getErrors().stream().map(NistValidationError::getMessage))
        .contains("age must be less than or equal to 7");
    assertThat(result2.getErrors().stream().map(NistValidationError::getCode)).contains("666");

    assertThat(result2.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result2.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent2.getCities());
    assertThat(result2.getErrors().stream().map(NistValidationError::getMessage))
        .contains("cities size must be 10");

    assertThat(result2.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result2.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent2.getName());
    assertThat(result2.getErrors().stream().map(NistValidationError::getMessage))
        .contains("name must contains key John");
  }

  @Test
  public void validationCollectionParentMustBeSuccess() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent1 = new Parent();

    parent1.setAge(6);
    parent1.setName("John Gow");
    parent1.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent1.setChildren(singletonList(new Boy("John", 5)));

    final Parent parent2 = new Parent();

    parent2.setAge(10);
    parent2.setName("Ana");
    parent2.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent2.setChildren(singletonList(new Boy("John", 5)));

    final List<ValidationResult> result = validatorParent.validate(Arrays.asList(parent1, parent2));

    assertTrue(result.get(0).isValid());
    assertThat(result.get(0).getErrors()).isEmpty();

    assertFalse(result.get(1).isValid());
    assertThat(result.get(1).getErrors()).isNotEmpty();
    assertThat(result.get(1).getErrors()).hasSize(3);

    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent2.getAge());
    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getMessage))
        .contains("age must be less than or equal to 7");
    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getCode))
        .contains("666");

    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getMessage))
        .contains("cities size must be 10");

    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent2.getName());
    assertThat(result.get(1).getErrors().stream().map(NistValidationError::getMessage))
        .contains("name must contains key John");
  }

  @Test
  public void validationMustBeFalseWhenChildrenIsNull() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(1);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .isEqualTo(singletonList(null));

    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("parent's children cannot be null");
    assertThat(result.getErrors().stream().map(NistValidationError::getCode)).contains("555");
  }

  @Test
  public void validationMustBeFalseWhenChildrenIsEmpty() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent.setChildren(new ArrayList<>());

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(1);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .isEqualTo(singletonList(emptyList()));
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("parent must have at least one child");
  }

  @Test
  public void validationMustBeFalseWhenChildrenIsInvalid() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent.setChildren(singletonList(new Girl("Barbara", 4)));

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(2);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains("Barbara");
    assertThat(
            result.getErrors().stream()
                .map(NistValidationError::getMessage)
                .filter(m -> m.contains("name must contains key Ana"))
                .collect(Collectors.toList()))
        .hasSize(1);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound)).contains(4);
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("child age must be greater than or equal to 5");
  }

  @Test
  public void validationMustBeFalseWhenParentAndChildrenIsInvalid() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent.setChildren(singletonList(new Girl("Barbara", 6)));

    final ValidationResult result = validatorParent.validate(parent);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).isNotEmpty();
    assertThat(result.getErrors()).hasSize(3);

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound)).contains(6);
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("child age must be less than age parent");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains(parent.getCities());
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("cities size must be 10");

    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
    assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
        .contains("Barbara");
    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("child name must contains key Ana");
  }

  @Test
  public void validationMultiThreadMustBeTrue() throws InterruptedException {

    final int CONCURRENT_RUNNABLE = 100000;

    final List<String> cities;
    final Collection<ValidationResult> resultsOne;
    final Collection<ValidationResult> resultsTwo;
    ExecutorService executor = Executors.newFixedThreadPool(100);

    cities = Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8");

    resultsOne = new ConcurrentLinkedQueue<>();
    resultsTwo = new ConcurrentLinkedQueue<>();

    for (int i = 0; i < CONCURRENT_RUNNABLE; i++) {

      executor.submit(
          () -> {
            final Validator<Parent> validatorParent = new ValidatorParent();

            final Parent parent = new Parent();

            parent.setAge(6);
            parent.setName("John Gow");
            parent.setCities(
                Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
            parent.setChildren(singletonList(new Boy("John", 5)));

            resultsOne.add(validatorParent.validate(parent));
          });

      executor.submit(
          () -> {
            final Validator<Parent> validatorParent = new ValidatorParent();
            final Parent parent = new Parent();

            parent.setAge(10);
            parent.setName("Ana");
            parent.setCities(cities);
            parent.setChildren(singletonList(new Boy("John", 5)));

            resultsTwo.add(validatorParent.validate(parent));
          });
    }

    executor.shutdown();

    boolean resultExit = executor.awaitTermination(10, TimeUnit.MINUTES);
    assertThat(resultExit).isTrue();

    assertThat(resultsOne).hasSize(CONCURRENT_RUNNABLE);
    assertThat(resultsTwo).hasSize(CONCURRENT_RUNNABLE);

    for (final ValidationResult result : resultsOne) {
      assertTrue(result.isValid());
      assertThat(result.getErrors()).isEmpty();
    }

    for (final ValidationResult result : resultsTwo) {
      assertFalse(result.isValid());
      assertThat(result.getErrors()).isNotEmpty();
      assertThat(result.getErrors()).hasSize(3);

      assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
          .contains(RT1FieldsEnum.VER);
      assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
          .contains(10);
      assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
          .contains("age must be less than or equal to 7");
      assertThat(result.getErrors().stream().map(NistValidationError::getCode)).contains("666");

      assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
          .contains(RT1FieldsEnum.VER);
      assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
          .contains(cities);
      assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
          .contains("cities size must be 10");

      assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
          .contains(RT1FieldsEnum.VER);
      assertThat(result.getErrors().stream().map(NistValidationError::getAttemptedFound))
          .contains("Ana");
      assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
          .contains("name must contains key John");
    }
  }

  @Test
  public void testSuccessWithFailFastOrDefault() {
    // Given
    final StringValidatorNist4j validatorDefault = new StringValidatorNist4j();
    final StringValidatorNist4j validatorFailFast = new StringValidatorNist4j();
    validatorFailFast.failFastRule();

    // When
    final ValidationResult resultDefault = validatorDefault.validate("bla");
    final ValidationResult resultFailFast = validatorFailFast.validate("bla");

    // Then
    assertFalse(resultDefault.isValid());
    assertFalse(resultFailFast.isValid());

    assertThat(resultDefault.getErrors()).hasSize(3);
    assertThat(resultFailFast.getErrors()).hasSize(1);

    assertThat(resultFailFast.getErrors().stream().map(NistValidationError::getMessage))
        .contains("group 1 rule 1");
    assertThat(resultDefault.getErrors().stream().map(NistValidationError::getMessage))
        .contains("group 2 rule 1");
    assertThat(resultDefault.getErrors().stream().map(NistValidationError::getMessage))
        .contains("group 3 rule 1");
  }

  @Test
  public void testFailWhenValidatePropertyNullValue() {

    final String2ValidatorNist4j validator = new String2ValidatorNist4j();

    final ValidationResult result = validator.validate((String) null);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).hasSize(1);

    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("group 1 rule 1");
    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
  }

  @Test
  public void testFailWhenValidateCollectionNullValue() {

    final String3ValidatorNist4j validator = new String3ValidatorNist4j();

    final ValidationResult result = validator.validate((List<String>) null);

    assertFalse(result.isValid());
    assertThat(result.getErrors()).hasSize(1);

    assertThat(result.getErrors().stream().map(NistValidationError::getMessage))
        .contains("group 1 rule 1");
    assertThat(result.getErrors().stream().map(NistValidationError::getFieldType))
        .contains(RT1FieldsEnum.VER);
  }

  @Test
  public void testSuccessWhenBillIsCorrect() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) 100.00, LocalDate.now().plusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertTrue(validate.isValid());
  }

  @Test
  public void testFailWhenBillDescriptionIsNull() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill(null, (float) 100.00, LocalDate.now().plusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).contains("description is required");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.DAI);
  }

  @Test
  public void testFailWhenBillDescriptionIsEmpty() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("", (float) 100.00, LocalDate.now().plusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).contains("description is required");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.DAI);
  }

  @Test
  public void testFailWhenBillValueIsNull() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", null, LocalDate.now().plusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).contains("value must be provided");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.VER);
  }

  @Test
  public void testFailWhenBillValueIsZero() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) 0, LocalDate.now().plusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).contains("value must be greather than 0");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.VER);
  }

  @Test
  public void testFailWhenBillValueIsNegative() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) -1, LocalDate.now().plusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).contains("value must be greather than 0");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.VER);
  }

  @Test
  public void testFailWhenBillDueDateIsToday() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) 100.00, LocalDate.now());

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).isEqualTo("Only future bills are allowed");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.GMT);
  }

  @Test
  public void testFailWhenBillDueDateIsPast() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) 100.00, LocalDate.now().minusDays(1));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).isEqualTo("Only future bills are allowed");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.GMT);
  }

  @Test
  public void testFailWhenBillDueDateIsFarTooAhead() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) 100.00, LocalDate.now().plusYears(4));

    final ValidationResult validate = validatorBill.validate(bill);

    assertFalse(validate.isValid());
    assertThat(validate.getErrors()).hasSize(1);

    final NistValidationError nistValidationErrorImpl = validate.getErrors().iterator().next();

    assertThat(nistValidationErrorImpl.getMessage()).isEqualTo("Max due date is 3 years ahead");
    assertThat(nistValidationErrorImpl.getFieldType()).isEqualTo(RT1FieldsEnum.DAT);
  }

  @Test
  public void testSuccessWhenBillDueDateIsExactlyThreeYears() {
    final ValidatorBillNist4j validatorBill = new ValidatorBillNist4j();
    final Bill bill = new Bill("Energy bill", (float) 100.00, LocalDate.now().plusYears(3));

    final ValidationResult validate = validatorBill.validate(bill);

    assertTrue(validate.isValid());
  }

  static class StringValidatorNist4j extends AbstractValidator<String> {

    @Override
    public void rules() {

      ruleFor(str -> str)
          .must(not(ComparablePredicate.equalTo("bla")))
          .withMessage("group 1 rule 1");

      ruleFor(str -> str)
          .must(not(ComparablePredicate.equalTo("bla")))
          .withMessage("group 2 rule 1")
          .must(not(ComparablePredicate.equalTo("bla")))
          .withMessage("group 2 rule 2");

      ruleFor(str -> str)
          .must(not(ComparablePredicate.equalTo("bla")))
          .withMessage("group 3 rule 1");
    }
  }

  static class String2ValidatorNist4j extends AbstractValidator<String> {

    @Override
    public void rules() {

      ruleFor(RT1, RT1FieldsEnum.VER, null, str -> str)
          .must(not(stringEmptyOrNull()))
          .withMessage("group 1 rule 1");
    }
  }

  static class String3ValidatorNist4j extends AbstractValidator<List<String>> {

    @Override
    public void rules() {

      ruleForEach(RT1, RT1FieldsEnum.VER, null, str -> str)
          .must(not(CollectionPredicate.empty()))
          .withMessage("group 1 rule 1");
    }
  }
}
