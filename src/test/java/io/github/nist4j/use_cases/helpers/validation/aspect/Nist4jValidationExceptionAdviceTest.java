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
package io.github.nist4j.use_cases.helpers.validation.aspect;

import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.empty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext.Context;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.predicates.PredicateBuilder;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

// @formatter:off
public class Nist4jValidationExceptionAdviceTest {

  @Test
  public void validationMustBeSuccess() {
    final Validator<ObjectFrom> validatorParent = new ValidatorObjectFromNist4j();

    final ObjectFrom instance = new ObjectFrom();
    instance.setValue("123");
    instance.setValues(Collections.singletonList("123"));

    final ValidationResult result = validatorParent.validate(instance);

    assertTrue(result.isValid());
  }

  @Test
  public void validationMustBeFail() {
    final Validator<ObjectFrom> validatorParent = new ValidatorObjectFromNist4j();

    final Context contextBefore = ValidationContext.get();

    final ObjectFrom instance = new ObjectFrom();
    instance.setValue("111");
    instance.setValues(Collections.singletonList("321"));

    catchThrowableOfType(() -> validatorParent.validate(instance), RuntimeException.class);

    final Context contextAfter = ValidationContext.get();

    assertThat(contextBefore).isEqualTo(contextAfter);
    assertThat(contextAfter.getValidationResult().isValid()).isFalse();
  }

  public class ValidatorObjectFromNist4j extends AbstractValidator<ObjectFrom> {

    @Override
    public void rules() {

      ruleFor(ObjectFrom::getValue)
          .whenever(not(stringEmptyOrNull()))
          .withValidator(new ValidatorExceptionNist4j());

      ruleForEach(ObjectFrom::getValues)
          .whenever(not(empty()))
          .withValidator(new ValidatorExceptionNist4j());
    }
  }

  public static class ValidatorExceptionNist4j extends AbstractValidator<String> {

    @Override
    public void rules() {

      ruleFor(String::toString)
          .must(PredicateBuilder.<String>from(stringEquals("123")).or(stringEquals("456")))
          .withCode("fail")
          .must(not(stringEquals("321")))
          .withCode(
              fn -> {
                throw new RuntimeException();
              });
    }
  }

  @Setter
  @Getter
  public static class ObjectFrom {

    public String value;

    public List<String> values;
  }
}
// @formatter:on
