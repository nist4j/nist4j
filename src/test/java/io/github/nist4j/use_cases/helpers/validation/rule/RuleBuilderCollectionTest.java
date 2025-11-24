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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasSize;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.isFalse;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.isTrue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringSizeLessThan;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.exceptions.Nist4jValidationSampleException;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RuleBuilderCollectionTest {

  @AfterEach
  public void tearDown() {
    ValidationContext.remove();
  }

  @Test
  public void testFailWhenApplyNullValue() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.must(hasSize(2)).withMessage("test").critical();

    assertFalse(builder.apply(null));
  }

  @Test
  public void testSuccessValidValue() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.must(hasSize(2)).when(not(nullValue())).withMessage("test");

    assertTrue(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testSuccessInvalidSingleRuleWithoutCritical() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.must(hasSize(1)).when(not(nullValue())).withMessage("test");

    assertTrue(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testSuccessInvalidMultipleRuleWithoutCritical() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(1))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test");

    assertTrue(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testSuccessDynamicProperties() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(
            RT1, RT1FieldsEnum.VER, null, Collections::unmodifiableList);

    builder
        .must(hasSize(1))
        .withMessage(List::toString)
        .must(hasSize(1))
        .withCode(List::toString)
        .must(hasSize(1))
        .withFieldType(RT1FieldsEnum.VER)
        .must(hasSize(1))
        .withAttemptedValue(me -> me)
        .must(hasSize(1))
        .withAttemptedValue(Collections.emptyList())
        .must(hasSize(1))
        .when(not(nullValue()))
        .handlerInvalidField(
            new HandlerInvalidField<Collection<String>>() {
              public List<NistValidationError> handle(final Collection<String> attemptedValue) {
                return Collections.emptyList();
              }
            });

    assertTrue(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testSuccessInvalidSingleRuleWithCritical() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.must(hasSize(2)).when(not(nullValue())).withMessage("test").critical();

    assertTrue(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testFailInvalidSingleRuleWithCritical() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.must(hasSize(1)).when(not(nullValue())).withMessage("test").critical();

    assertFalse(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testSuccessInvalidSingleRuleWithCriticalException() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .critical(Nist4jValidationSampleException.class);

    assertTrue(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testFailInvalidSingleRuleWithCriticalException() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(hasSize(1))
        .when(not(nullValue()))
        .withMessage("test")
        .critical(Nist4jValidationSampleException.class);

    final Throwable throwable = catchThrowable(() -> builder.apply(Arrays.asList("o", "oo")));

    assertThat(throwable).isInstanceOf(Nist4jValidationSampleException.class);
  }

  @Test
  public void testFailRuleValidator() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.whenever(not(nullValue())).withValidator(new ValidatorIdTestNist4j());

    assertTrue(builder.apply(Collections.singletonList("")));
  }

  @Test
  public void testFailRuleValidatorWithCritical() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.whenever(not(nullValue())).withValidator(new ValidatorIdTestNist4j()).critical();

    assertFalse(builder.apply(Collections.singletonList("oo")));
  }

  @Test
  public void testFailRuleValidatorWithCriticalException() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .whenever(not(nullValue()))
        .withValidator(new ValidatorIdTestNist4j())
        .critical(Nist4jValidationSampleException.class);

    final Throwable throwable = catchThrowable(() -> builder.apply(Collections.singletonList("o")));

    assertThat(throwable).isInstanceOf(Nist4jValidationSampleException.class);
  }

  @Test
  public void testFailInvalidMultipleRuleWithCritical() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(1))
        .when(not(nullValue()))
        .withMessage("test")
        .critical()
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test");

    assertFalse(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testFailInvalidSingleWithCriticalException() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(hasSize(1))
        .when(not(nullValue()))
        .withMessage("test")
        .critical(Nist4jValidationSampleException.class);

    final Throwable throwable = catchThrowable(() -> builder.apply(Arrays.asList("o", "oo")));

    assertThat(throwable).isInstanceOf(Nist4jValidationSampleException.class);
  }

  @Test
  public void testFailInvalidMultipleWithCriticalException() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(1))
        .when(not(nullValue()))
        .withMessage("test")
        .critical(Nist4jValidationSampleException.class)
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test");

    final Throwable throwable = catchThrowable(() -> builder.apply(Arrays.asList("o", "oo")));

    assertThat(throwable).isInstanceOf(Nist4jValidationSampleException.class);
  }

  @Test
  public void testSuccessValidAndInvalidMultipleRule() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder
        .must(isFalse(fn -> false))
        .when(isTrue(fn -> true))
        .withMessage(fn -> "ever enter here")
        .withCode(fn -> "666")
        .withFieldType(fn -> RT1FieldsEnum.VER)
        .must(isTrue(fn -> true))
        .when(isTrue(fn -> true))
        .withMessage(fn -> "never enter here")
        .withCode(fn -> "666")
        .withFieldType(fn -> RT1FieldsEnum.VER)
        .must(isTrue(fn -> true))
        .when(isFalse(fn -> false))
        .withMessage(fn -> "never enter here")
        .withCode(fn -> "666")
        .withFieldType(fn -> RT1FieldsEnum.VER)
        .must(isFalse(fn -> false))
        .when(isFalse(fn -> false))
        .withMessage(fn -> "never enter here")
        .withCode(fn -> "666")
        .withRecordType(fn -> RT1)
        .withSubfieldName(fn -> null)
        .withFieldType(fn -> RT1FieldsEnum.VER);

    assertTrue(builder.apply(Collections.singletonList("o")));
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
}
