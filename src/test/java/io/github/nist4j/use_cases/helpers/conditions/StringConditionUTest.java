/*
 * Copyright (C) 2025 Sopra Steria.
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
package io.github.nist4j.use_cases.helpers.conditions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringConditionUTest {

  @Test
  void isBlank_should_return_true_but_isNotBlank_dont() {
    assertTrue(StringCondition.isBlank(null));
    assertFalse(StringCondition.isNotBlank(null));

    assertTrue(StringCondition.isBlank(""));
    assertFalse(StringCondition.isNotBlank(""));

    assertTrue(StringCondition.isBlank(" "));
    assertFalse(StringCondition.isNotBlank(" "));
  }

  @Test
  void isBlank_should_return_false_but_isNotBlank_dont() {
    assertFalse(StringCondition.isBlank("123"));
    assertTrue(StringCondition.isNotBlank("123"));

    assertFalse(StringCondition.isBlank(" A"));
    assertTrue(StringCondition.isNotBlank(" A"));
  }

  @Test
  void length_should_return_the_length_of_the_string() {
    assertThat(StringCondition.length("")).isEqualTo(0);
    assertThat(StringCondition.length(null)).isEqualTo(0);
    assertThat(StringCondition.length("123")).isEqualTo(3);
  }
}
