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
package io.github.nist4j.entities;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.nist4j.fixtures.NistFileFixtures;
import org.junit.jupiter.api.Test;

class NistFileUTest {

  @Test
  void builder_should_builder() {
    NistFile nistFile = NistFileFixtures.newNistFileBuilderEnableCalculation().build();
    assertThat(nistFile).isNotNull();
  }

  @Test
  void builder_sans_callback_should_builder() {
    NistFile nistFile = NistFileFixtures.newNistFileBuilderWithoutCallbacks().build();
    assertThat(nistFile).isNotNull();
  }
}
