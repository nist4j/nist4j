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

import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.equalObject;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import java.util.Collection;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class RuleProcessorStrategyTest {

  @Test
  public void testDefaultSuccessSingleRule() {

    final StringValidationRule rule = new StringValidationRule();
    rule.must(equalObject("o"));

    assertTrue(RuleProcessorStrategy.getDefault().process("o", rule));

    final ValidationResult validationResult = ValidationContext.get().getValidationResult();

    assertTrue(validationResult.isValid());
  }

  @Test
  public void testDefaultSuccessMultipleRules() {

    StringValidationRule rule1 = new StringValidationRule();
    StringValidationRule rule2 = new StringValidationRule();
    rule1.must(equalObject("o"));
    rule2.must(equalObject("o"));

    assertTrue(RuleProcessorStrategy.getDefault().process("o", asList(rule1, rule2)));
    assertFalse(RuleProcessorStrategy.getDefault().process("a", asList(rule1, rule2)));

    final ValidationResult validationResult = ValidationContext.get().getValidationResult();

    assertFalse(validationResult.isValid());
    assertEquals(2, validationResult.getErrors().size());
  }

  @Test
  public void testFailFastSuccessMultipleRules() {

    final Collection<Rule<String>> rules = new LinkedList<>();

    rules.add(new StringValidationRule());
    rules.add(new StringValidationRule());
    rules.add(new StringValidationRule());

    assertTrue(RuleProcessorStrategy.getFailFast().process("o", rules));

    final ValidationResult validationResult = ValidationContext.get().getValidationResult();

    assertTrue(validationResult.isValid());
  }

  @Test
  public void testFailFastFailedMultipleRules() {

    StringValidationRule rule1 = new StringValidationRule();
    StringValidationRule rule2 = new StringValidationRule();
    rule1.must(equalObject("o"));
    rule2.must(equalObject("o"));

    assertTrue(RuleProcessorStrategy.getFailFast().process("o", asList(rule1, rule2)));
    assertFalse(RuleProcessorStrategy.getFailFast().process("a", asList(rule1, rule2)));

    final ValidationResult validationResult = ValidationContext.get().getValidationResult();

    assertFalse(validationResult.isValid());
    assertEquals(1, validationResult.getErrors().size());
  }

  @Test
  public void testDefaultSuccessSingleRuleAndMultipleValues() {

    final StringValidationRule rule = new StringValidationRule();
    rule.must(equalObject("o"));

    final Collection<String> values = asList("o", "oo");

    assertFalse(RuleProcessorStrategy.getDefault().process(values, rule));

    final ValidationResult validationResult = ValidationContext.get().getValidationResult();

    assertFalse(validationResult.isValid());

    assertThat(validationResult.getErrors()).isNotEmpty();
    assertThat(validationResult.getErrors()).hasSize(1);
  }

  @Test
  public void testFailFastSuccessSingleRuleAndMultipleValues() {

    final StringValidationRule rule = new StringValidationRule();
    rule.must(equalObject("o"));

    final Collection<String> values = asList("o", "oo");

    assertFalse(RuleProcessorStrategy.getFailFast().process(values, rule));

    final ValidationResult validationResult = ValidationContext.get().getValidationResult();

    assertFalse(validationResult.isValid());

    assertThat(validationResult.getErrors()).isNotEmpty();
    assertThat(validationResult.getErrors()).hasSize(1);
  }

  static class StringValidationRule extends AbstractValidationRule<String, String> {

    public StringValidationRule() {
      super();
    }

    @Override
    public boolean apply(final String instance) {
      final boolean apply = getMust().test(instance);

      if (!apply) {
        ValidationContext.get().addErrors(getHandlerInvalid().handle(instance, instance));
      }

      return apply;
    }

    @Override
    public boolean support(final String instance) {
      return getWhen().test(instance);
    }
  }
}
