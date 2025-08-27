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
package io.github.nist4j.entities.tuple;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PairUTest {

  @Test
  void of() {
    assertThat(Pair.of(1, 2)).isEqualTo(Pair.of(1, 2));
    assertThat(Pair.of(null, 2)).isEqualTo(Pair.of(null, 2));
    assertThat(Pair.of(1, null)).isEqualTo(Pair.of(1, null));
    assertThat(Pair.of(null, null)).isEqualTo(Pair.of(null, null));
  }

  @Test
  void testHashCode() {
    assertThat(Pair.of(1, 2).hashCode()).isNotNull();
    assertThat(Pair.of(1, "2").hashCode()).isEqualTo(Pair.of(1, "2").hashCode());
  }

  @Test
  void getLeft() {
    assertThat(Pair.of(1, 2).getLeft()).isEqualTo(1);
    assertThat(Pair.of(1, "2").hashCode()).isEqualTo(Pair.of(1, "2").hashCode());
  }

  @Test
  void getRight() {
    assertThat(Pair.of(1, 2).getRight()).isEqualTo(2);
  }

  @Test
  void testToString() {
    assertThat(Pair.of(1, 2).toString()).isNotNull();
    assertThat(Pair.of(1, 2).toString()).isEqualTo("(1,2)");
  }
}
