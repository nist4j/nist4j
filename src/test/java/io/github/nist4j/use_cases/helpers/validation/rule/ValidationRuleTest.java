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

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class ValidationRuleTest {

  @AfterEach
  public void tearDown() {
    ValidationContext.remove();
  }

  @Test
  public void testSuccessWhen() {

    final StringValidationRule rule = new StringValidationRule();

    assertTrue(rule.getWhen().test(null));
    assertTrue(rule.getWhen().test("o"));
  }

  @Test
  public void testSuccessWhenExplicited() {

    final StringValidationRule rule = new StringValidationRule();
    rule.when(not(nullValue()));

    assertFalse(rule.getWhen().test(null));
    assertTrue(rule.getWhen().test("o"));
  }

  @Test
  public void testSuccessWhenever() {

    final StringValidationRule rule = new StringValidationRule();

    assertTrue(rule.getWhenever().test(null));
    assertTrue(rule.getWhenever().test("o"));
  }

  @Test
  public void testSuccessWheneverExplicited() {

    final StringValidationRule rule = new StringValidationRule();
    rule.whenever(not(nullValue()));

    assertFalse(rule.getWhenever().test(null));
    assertTrue(rule.getWhenever().test("o"));
  }

  @Test
  public void testSuccessMust() {

    final StringValidationRule rule = new StringValidationRule();

    assertTrue(rule.getMust().test(null));
    assertTrue(rule.getMust().test("o"));
  }

  @Test
  public void testSuccessMustExplicited() {

    final StringValidationRule rule = new StringValidationRule();
    rule.must(not(nullValue()));

    assertFalse(rule.getMust().test(null));
    assertTrue(rule.getMust().test("o"));
  }

  @Test
  public void testSuccessApply() {

    final StringValidationRule rule = new StringValidationRule();
    rule.must(not(nullValue()));

    assertFalse(rule.apply(null));
    assertTrue(rule.apply("o"));
  }

  static class StringValidationRule extends AbstractValidationRule<String, String> {

    @Override
    public boolean apply(final String instance) {
      return getMust().test(instance);
    }

    @Override
    public boolean support(final String instance) {
      return getWhen().test(instance);
    }
  }
}
