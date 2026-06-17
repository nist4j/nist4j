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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractValidator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
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

    builder.must(hasSize(2)).withMessage("test");

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
  public void testSuccessInvalidSingleRule() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.must(hasSize(1)).when(not(nullValue())).withMessage("test");

    assertTrue(builder.apply(null));
    assertTrue(builder.apply(Collections.singletonList("o")));
    assertFalse(builder.apply(Arrays.asList("o", "a")));
  }

  @Test
  public void testSuccessInvalidMultipleRule() {

    final RuleBuilderCollectionImpl<List<String>, String> builder1item =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);
    final RuleBuilderCollectionImpl<List<String>, String> builder2items =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder2items
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test");
    builder1item
        .must(hasSize(1))
        .when(not(nullValue()))
        .withMessage("test")
        .must(hasSize(2))
        .when(not(nullValue()))
        .withMessage("test");

    assertFalse(builder1item.apply(Collections.singletonList("o")));
    assertFalse(builder1item.apply(Collections.singletonList("oo")));
    assertFalse(builder1item.apply(Arrays.asList("o", "oo")));

    assertTrue(builder2items.apply(Arrays.asList("o", "oo")));
    assertTrue(builder2items.apply(Arrays.asList("o", "oo")));
    assertFalse(builder2items.apply(Collections.singletonList("o")));
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

    assertTrue(builder.apply(Collections.singletonList("o")));
    assertFalse(builder.apply(Arrays.asList("o", "oo")));
  }

  @Test
  public void testFailRuleValidator() {

    final RuleBuilderCollectionImpl<List<String>, String> builder =
        new RuleBuilderCollectionImpl<>(Collections::unmodifiableList);

    builder.whenever(not(nullValue())).withValidator(new ValidatorIdTestNist4j());

    assertTrue(builder.apply(Collections.singletonList("")));
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
          .must(stringSizeLessThan(1))
          .withMessage("rule 2");
    }
  }
}
