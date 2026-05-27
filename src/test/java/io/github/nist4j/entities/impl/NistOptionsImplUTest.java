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
package io.github.nist4j.entities.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.use_cases.helpers.builders.options.NistOptionsBuilderImpl;
import org.junit.jupiter.api.Test;

class NistOptionsImplUTest {

  @Test
  void build_should_build() {
    NistOptions nistOptions = NistOptionsBuilderImpl.newBuilder().build();
    assertNotNull(nistOptions);
  }

  @Test
  void equalsHashCode_should_be_implemented() {
    NistOptions nistOptions = NistOptionsBuilderImpl.newBuilder().build();
    NistOptions nistOptions2 = NistOptionsBuilderImpl.newBuilder().build();
    assertThat(nistOptions).isEqualTo(nistOptions2);
    assertThat(nistOptions.hashCode()).isNotNull();
  }

  @Test
  void toString_should_be_implemented() {
    NistOptions nistOptions = NistOptionsBuilderImpl.newBuilder().build();
    assertThat(nistOptions.toString()).doesNotContain("NistOptionsImpl@");
    assertThat(nistOptions.toString()).doesNotContain("NistOptions@");
  }
}
