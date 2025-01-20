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
package io.github.nist4j.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class NistStandardEnumTest {

  @Test
  void isBetweenStandards_should_return_true_if_equals_to_min() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2000.isBetweenStandards(
            NistStandardEnum.ANSI_NIST_ITL_2000, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(res).isTrue();
  }

  @Test
  void isBetweenStandards_should_return_true_if_superior_to_min_and_inferior_to_max() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2007.isBetweenStandards(
            NistStandardEnum.ANSI_NIST_ITL_2000, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(res).isTrue();
  }

  @Test
  void isBetweenStandards_should_return_false_if_equals_to_max() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2013.isBetweenStandards(
            NistStandardEnum.ANSI_NIST_ITL_2000, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(res).isFalse();
  }

  @Test
  void isBetweenStandards_should_return_false_if_superior_to_min_and_to_max() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2013.isBetweenStandards(
            NistStandardEnum.ANSI_NIST_ITL_2000, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(res).isFalse();
  }

  @Test
  void isPriorTo_should_return_true_if_inferior_to_the_limit() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2007.isPriorTo(NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(res).isTrue();
  }

  @Test
  void isPriorTo_should_return_false_if_equals_to_the_limit() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2013.isPriorTo(NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(res).isFalse();
  }

  @Test
  void isPriorTo_should_return_false_if_superior_to_the_limit() {
    boolean res =
        NistStandardEnum.ANSI_NIST_ITL_2013.isPriorTo(NistStandardEnum.ANSI_NIST_ITL_2011);

    assertThat(res).isFalse();
  }

  @Test
  void findByCode_should_return_null_if_code_does_not_exist() {
    Optional<NistStandardEnum> res = NistStandardEnum.findByCode("0100");

    assertThat(res).isNotPresent();
  }

  @Test
  void getNistStandardEnumByCode_should_return_standard_2007_if_code_is_0400() {
    Optional<NistStandardEnum> res = NistStandardEnum.findByCode("0400");

    assertThat(res).isPresent().contains(NistStandardEnum.ANSI_NIST_ITL_2007);
  }

  @Test
  void getNistStandardEnumByCode_should_return_standard_2011_if_code_is_0500() {
    Optional<NistStandardEnum> res = NistStandardEnum.findByCode("0500");

    assertThat(res).isPresent().contains(NistStandardEnum.ANSI_NIST_ITL_2011);
  }

  @Test
  void getNistStandardEnumByCode_should_return_standard_2013_if_code_is_0501() {
    Optional<NistStandardEnum> res = NistStandardEnum.findByCode("0501");

    assertThat(res).isPresent().contains(NistStandardEnum.ANSI_NIST_ITL_2013);
  }

  @Test
  void getNistStandardEnumByCode_should_return_standard_2015_if_code_is_0502() {
    Optional<NistStandardEnum> res = NistStandardEnum.findByCode("0502");

    assertThat(res).isPresent().contains(NistStandardEnum.ANSI_NIST_ITL_2015);
  }
}
