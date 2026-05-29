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
package io.github.nist4j.use_cases.helpers.validation.transform;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.model.Boy;
import io.github.nist4j.use_cases.helpers.validation.model.Girl;
import io.github.nist4j.use_cases.helpers.validation.model.Parent;
import io.github.nist4j.use_cases.helpers.validation.validator.ValidatorParent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;

public class ValidationResultTransformUTest {

  @Test
  public void validationTransformMustBeSuccess() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(6);
    parent.setName("John Gow");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent.setChildren(Arrays.asList(new Boy("John", 5), new Girl("Ana", 5)));

    final String result = validatorParent.validate(parent, new ValidationResultTestTransform());

    assertThat(result).isEmpty();
  }

  @Test
  public void validationTransformMustBeFailWhenFieldOfParentAreInvalid() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent = new Parent();

    parent.setAge(10);
    parent.setName("Ana");
    parent.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent.setChildren(Collections.singletonList(new Boy("John", 5)));

    final String result = validatorParent.validate(parent, new ValidationResultTestTransform());

    assertThat(result).isNotEmpty();
    assertThat(result).contains("age must be less than or equal to 7");
    assertThat(result).contains("cities size must be 10");
    assertThat(result).contains("name must contains key John");
  }

  @Test
  public void validationTransformCollectionParentMustBeSuccess() {
    final Validator<Parent> validatorParent = new ValidatorParent();

    final Parent parent1 = new Parent();

    parent1.setAge(6);
    parent1.setName("John Gow");
    parent1.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
    parent1.setChildren(Collections.singletonList(new Boy("John", 5)));

    final Parent parent2 = new Parent();

    parent2.setAge(10);
    parent2.setName("Ana");
    parent2.setCities(Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8"));
    parent2.setChildren(Collections.singletonList(new Boy("John", 5)));

    final List<String> result =
        validatorParent.validate(
            Arrays.asList(parent1, parent2), new ValidationResultTestTransform());

    assertThat(result.get(0)).isEmpty();

    assertThat(result.get(1)).isNotEmpty();
    assertThat(result.get(1)).contains("age must be less than or equal to 7");
    assertThat(result.get(1)).contains("cities size must be 10");
    assertThat(result.get(1)).contains("name must contains key John");
  }

  @Test
  public void validationTransformMultiThreadMustBeTrue() throws InterruptedException {

    final int CONCURRENT_RUNNABLE = 100000;

    ExecutorService executor = Executors.newFixedThreadPool(100);

    final List<String> cities = Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8");

    final Collection<String> resultsOne = new ConcurrentLinkedQueue<>();
    final Collection<String> resultsTwo = new ConcurrentLinkedQueue<>();

    for (int i = 0; i < CONCURRENT_RUNNABLE; i++) {

      executor.submit(
          () -> {
            final Validator<Parent> validatorParent = new ValidatorParent();

            final Parent parent = new Parent();

            parent.setAge(6);
            parent.setName("John Gow");
            parent.setCities(
                Arrays.asList("c0", "c1", "c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9"));
            parent.setChildren(Collections.singletonList(new Boy("John", 5)));

            resultsOne.add(validatorParent.validate(parent, new ValidationResultTestTransform()));
          });

      executor.submit(
          () -> {
            final Validator<Parent> validatorParent = new ValidatorParent();

            final Parent parent = new Parent();

            parent.setAge(10);
            parent.setName("Ana");
            parent.setCities(cities);
            parent.setChildren(Collections.singletonList(new Boy("John", 5)));

            resultsTwo.add(validatorParent.validate(parent, new ValidationResultTestTransform()));
          });
    }

    executor.shutdown();

    boolean resultExit = executor.awaitTermination(10, TimeUnit.MINUTES);
    assertThat(resultExit).isTrue();

    assertThat(resultsOne).hasSize(CONCURRENT_RUNNABLE);
    assertThat(resultsTwo).hasSize(CONCURRENT_RUNNABLE);

    for (final String result : resultsOne) {
      assertThat(result).isEmpty();
    }

    for (final String result : resultsTwo) {
      assertThat(result).isNotEmpty();
      assertThat(result).contains("age must be less than or equal to 7");
      assertThat(result).contains("cities size must be 10");
      assertThat(result).contains("name must contains key John");
    }
  }

  static class ValidationResultTestTransform implements ValidationResultTransform<String> {

    @Override
    public String transform(final ValidationResult validationResult) {
      final StringBuilder sb = new StringBuilder();
      for (final NistValidationError nistValidationError : validationResult.getErrors()) {
        sb.append(String.format("%s\n", nistValidationError.getMessage()));
      }
      return sb.toString();
    }
  }
}
