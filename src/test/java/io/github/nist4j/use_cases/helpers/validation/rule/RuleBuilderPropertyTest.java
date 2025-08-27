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
package io.github.nist4j.use_cases.helpers.validation.rule;

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.isFalse;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.isTrue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringSizeLessThan;
import static org.junit.jupiter.api.Assertions.*;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.use_cases.helpers.validation.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.exceptions.Nist4jValidationSampleException;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("StringOperationCanBeSimplified")
public class RuleBuilderPropertyTest {

  @AfterEach
  public void tearDown() {
    ValidationContext.remove();
  }

  @Test
  public void testFailWhenApplyNullValue() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder.must(stringSizeLessThan(2)).withMessage("rule 1").critical();

    assertFalse(builder.apply(null));
  }

  @Test
  public void testSuccessValidValue() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder.must(stringSizeLessThan(2)).when(not(nullValue())).withMessage("rule 1");

    assertTrue(builder.apply("o"));
  }

  @Test
  public void testSuccessInvalidSingleRuleWithoutCritical() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder.must(stringSizeLessThan(1)).when(not(nullValue())).withMessage("rule 1");

    assertTrue(builder.apply("o"));
  }

  @Test
  public void testSuccessInvalidMultipleRuleWithoutCritical() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 1")
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 2")
        .must(stringSizeLessThan(1))
        .when(not(nullValue()))
        .withMessage("rule 3")
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 4");

    assertTrue(builder.apply("o"));
  }

  @Test
  public void testSuccessRuleWithCritical() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder
        .must(stringSizeLessThan(1))
        .when(not(nullValue()))
        .withMessage("rule 1")
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 2")
        .critical();

    assertTrue(builder.apply("o"));
  }

  @Test
  public void testFailRuleWithCritical() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder
        .must(stringSizeLessThan(1))
        .when(not(nullValue()))
        .withMessage("rule 1")
        .must(stringSizeLessThan(1))
        .when(not(nullValue()))
        .withMessage("rule 2")
        .critical();

    assertFalse(builder.apply("o"));
  }

  @Test
  public void testSuccessRuleWithCriticalException() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder
        .must(stringSizeLessThan(1))
        .when(not(nullValue()))
        .withMessage("rule 1")
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 2")
        .critical(Nist4jValidationSampleException.class);

    assertTrue(builder.apply("o"));
  }

  @Test
  public void testFailRuleWithCriticalException() {

    assertThrows(
        Nist4jValidationSampleException.class,
        () -> {
          final RuleBuilderPropertyImpl<String, String> builder =
              new RuleBuilderPropertyImpl<>(String::new);

          builder
              .must(stringSizeLessThan(1))
              .when(not(nullValue()))
              .withMessage("rule 1")
              .must(stringSizeLessThan(1))
              .when(not(nullValue()))
              .withMessage("rule 2")
              .critical(Nist4jValidationSampleException.class);

          assertFalse(builder.apply("o"));
        });
  }

  @Test
  public void testSuccessRuleValidator() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder.whenever(not(nullValue())).withValidator(new ValidatorIdTestNist4j());

    assertTrue(builder.apply(""));
  }

  @Test
  public void testFailRuleValidatorWithCritical() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder.whenever(not(nullValue())).withValidator(new ValidatorIdTestNist4j()).critical();

    assertFalse(builder.apply("oo"));
  }

  @Test
  public void testFailRuleValidatorWithCriticalException() {

    assertThrows(
        Nist4jValidationSampleException.class,
        () -> {
          final RuleBuilderPropertyImpl<String, String> builder =
              new RuleBuilderPropertyImpl<>(String::new);

          builder
              .whenever(not(nullValue()))
              .withValidator(new ValidatorIdTestNist4j())
              .critical(Nist4jValidationSampleException.class);

          assertFalse(builder.apply("o"));
        });
  }

  @Test
  public void testFailInvalidMultipleRuleWithCritical() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 1")
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 2")
        .must(stringSizeLessThan(1))
        .when(not(nullValue()))
        .withMessage("rule 3")
        .critical()
        .must(stringSizeLessThan(2))
        .when(not(nullValue()))
        .withMessage("rule 4");

    assertFalse(builder.apply("o"));
  }

  @Test
  public void testFailInvalidMultipleRuleWithCriticalException() {

    assertThrows(
        Nist4jValidationSampleException.class,
        () -> {
          final RuleBuilderPropertyImpl<String, String> builder =
              new RuleBuilderPropertyImpl<>(String::new);

          builder
              .must(stringSizeLessThan(2))
              .when(not(nullValue()))
              .withMessage("rule 1")
              .must(stringSizeLessThan(2))
              .when(not(nullValue()))
              .withMessage("rule 2")
              .must(stringSizeLessThan(1))
              .when(not(nullValue()))
              .withMessage("rule 3")
              .critical(Nist4jValidationSampleException.class)
              .must(stringSizeLessThan(2))
              .when(not(nullValue()))
              .withMessage("rule 4");

          assertFalse(builder.apply("o"));
        });
  }

  @Test
  public void testSuccessDynamicProperties() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>("r1", "test", String::toUpperCase);

    builder
        .must(nullValue())
        .withMessage(String::intern)
        .must(nullValue())
        .withCode(String::intern)
        .must(nullValue())
        .withRecordName(String::intern)
        .withFieldName(String::intern)
        .must(nullValue())
        .withAttemptedValue(String::toLowerCase)
        .must(nullValue())
        .withAttemptedValue(new String())
        .must(nullValue())
        .handlerInvalidField(
            new HandlerInvalidField<String>() {
              @Override
              public List<NistValidationError> handle(final String attemptedValue) {
                return Collections.emptyList();
              }
            });

    assertTrue(builder.apply("oo"));
  }

  @Test
  public void testSuccessValidAndInvalidMultipleRule() {

    final RuleBuilderPropertyImpl<String, String> builder =
        new RuleBuilderPropertyImpl<>(String::new);

    builder
        .must(isFalse(fn -> false))
        .when(isTrue(fn -> true))
        .withMessage("ever enter here")
        .withCode("666")
        .withRecordName("rt0")
        .withFieldName("size")
        .must(isTrue(fn -> true))
        .when(isTrue(fn -> true))
        .withMessage("never enter here")
        .withCode("666")
        .withFieldName("size")
        .must(isTrue(fn -> true))
        .when(isFalse(fn -> false))
        .withMessage("never enter here")
        .withCode("666")
        .withFieldName("size")
        .must(isFalse(fn -> false))
        .when(isFalse(fn -> false))
        .withMessage("never enter here")
        .withCode("666")
        .withFieldName("size");

    assertTrue(builder.apply("o"));
  }

  static class ValidatorIdTestNist4j extends AbstractValidator<String> {

    @Override
    public void rules() {

      ruleFor(id -> id)
          .must(stringSizeLessThan(2))
          .withMessage("rule 1")
          .critical()
          .must(stringSizeLessThan(1))
          .withMessage("rule 2")
          .critical();
    }
  }

  @Test
  public void testSuccesInnerClass() {

    final Validator<ClassTest.InnerClass> builder = new InnerClassValidatorNist4j();

    final ValidationResult result = builder.validate(new ClassTest.InnerClass());

    assertFalse(result.isValid());
  }

  public static class InnerClassValidatorNist4j extends AbstractValidator<ClassTest.InnerClass> {

    @Override
    public void rules() {

      failFastRule();

      ruleFor(innerClass -> innerClass)
          .must(not(nullValue()))
          .withMessage("bla")
          .withFieldName("bla")
          .withRecordName("rt0")
          .critical();

      ruleFor(innerClass -> innerClass)
          .must(not(stringEmptyOrNull(ClassTest.InnerClass::getValue)))
          .when(innerClass -> "01".equals(innerClass.getCode()))
          .withMessage("bla")
          .withRecordName("rt0")
          .withFieldName("bla");

      ruleFor(innerClass -> innerClass)
          .must(not(stringEmptyOrNull(ClassTest.InnerClass::getValue)))
          .when(innerClass -> "02".equals(innerClass.getCode()))
          .withMessage("bla")
          .withRecordName("rt0")
          .withFieldName("bla");
    }
  }

  public static class ClassTest {

    @Getter
    public static class InnerClass {

      private final String name = "Name";

      private final String code = "01";

      private final String value = "";
    }
  }
}
