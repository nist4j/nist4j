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

import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ObjectConditionUTest {

  @Test
  void isEmpty_should_return_true_and_isNotEmpty_dont() {

    assertTrue(ObjectCondition.isEmpty(null));
    assertFalse(ObjectCondition.isNotEmpty(null));

    assertTrue(ObjectCondition.isEmpty(""));
    assertFalse(ObjectCondition.isNotEmpty(""));

    assertTrue(ObjectCondition.isEmpty(emptyList()));
    assertFalse(ObjectCondition.isNotEmpty(emptyList()));

    assertTrue(ObjectCondition.isEmpty(new byte[] {}));
    assertFalse(ObjectCondition.isNotEmpty(new byte[] {}));

    assertTrue(ObjectCondition.isEmpty(new HashMap<>()));
    assertFalse(ObjectCondition.isNotEmpty(new HashMap<>()));

    assertTrue(ObjectCondition.isEmpty(Optional.empty()));
    assertFalse(ObjectCondition.isNotEmpty(Optional.empty()));

    assertTrue(ObjectCondition.isEmpty(newFieldText("")));
    assertFalse(ObjectCondition.isEmpty(newFieldText("1")));

    assertTrue(ObjectCondition.isEmpty(newFieldImage(new byte[0])));
    assertFalse(ObjectCondition.isEmpty(newFieldImage(new byte[] {0, 2})));
  }

  @Test
  void isEmpty_should_return_false_and_isNotEmpty_dont() {

    assertFalse(ObjectCondition.isEmpty(123));
    assertTrue(ObjectCondition.isNotEmpty(123));
  }
}
