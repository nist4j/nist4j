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
package io.github.nist4j.enums.ref;

import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.fp.NistRefAmputationBandagedFPEnum;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class NistReferentielHelperImplTest {

  @Test
  void findByCode_should_return_enum_item() {
    // When
    Optional<NistRefAmputationBandagedFPEnum> value =
        findByCode(NistRefAmputationBandagedFPEnum.values(), "XX");

    // Then
    assertThat(value).isPresent().contains(NistRefAmputationBandagedFPEnum.XX);
  }

  @Test
  void findValuesAllowedByStandard_should_return_list() {
    // When
    List<NistRefAmputationBandagedFPEnum> values =
        findValuesAllowedByStandard(
            NistRefAmputationBandagedFPEnum.values(), NistStandardEnum.ANSI_NIST_ITL_2007);

    // Then
    assertThat(values)
        .containsExactlyInAnyOrder(
            NistRefAmputationBandagedFPEnum.UP, NistRefAmputationBandagedFPEnum.XX);
  }

  @Test
  void findCodesAllowedByStandard_should_return_list_for_std2007() {
    // When
    List<String> values =
        findCodesAllowedByStandard(
            NistRefAmputationBandagedFPEnum.values(), NistStandardEnum.ANSI_NIST_ITL_2007);

    // Then
    assertThat(values).containsExactlyInAnyOrder("XX", "UP");
  }

  @Test
  void findCodesAllowedByStandard_should_return_list_for_std2015() {
    // When
    List<String> values =
        findCodesAllowedByStandard(
            NistRefAmputationBandagedFPEnum.values(), NistStandardEnum.ANSI_NIST_ITL_2015);

    // Then
    assertThat(values).containsExactlyInAnyOrder("XX", "UP", "SR");
  }
}
