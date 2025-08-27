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
package io.github.nist4j.use_cases.helpers.validation.predicates;

import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.isFalse;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.isTrue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

@SuppressWarnings("StringOperationCanBeSimplified")
public class LogicalPredicateUTest {

  @Test
  public void testLogicalPredicates() {
    assertTrue(not(Integer.class::isInstance).test(new String()));
    assertTrue(isTrue().test(true));
    assertTrue(isFalse().test(false));
    assertTrue(isTrue().test(Boolean.TRUE));
    assertTrue(isFalse().test(Boolean.FALSE));
    assertFalse(isTrue().test(null));
    assertFalse(isFalse().test(null));
  }

  @Test
  public void testLogicalPredicatesIsTrue() {
    assertTrue(isTrue(fn -> true).test(new String()));
    assertFalse(isTrue(fn -> false).test(new String()));
    assertFalse(isTrue(fn -> false).test(null));
  }

  @Test
  public void testLogicalPredicatesIsFalse() {
    assertTrue(isFalse(fn -> false).test(new String()));
    assertFalse(isFalse(fn -> true).test(new String()));
    assertFalse(isFalse(fn -> true).test(null));
  }
}
