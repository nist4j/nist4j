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

import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findValuesAllowedByStandard;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.fp.NistRefAmputationBandagedFPEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class NistRefAmputationBandagedFPEnumTest {

  private final NistRefAmputationBandagedFPEnum[] values = NistRefAmputationBandagedFPEnum.values();

  @Test
  void allowedValuesByStandard_when_standard_2000_should_return_empty_list() {
    List<NistRefAmputationBandagedFPEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2000);

    assertThat(allowedValues).isEmpty();
  }

  @Test
  void allowedValuesByStandard_when_standard_2007_should_return_XX_and_UP() {
    List<NistRefAmputationBandagedFPEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2007);

    assertThat(
            allowedValues.stream()
                .map(NistRefAmputationBandagedFPEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(Arrays.asList("XX", "UP"));
  }

  @Test
  void allowedValuesByStandard_when_standard_2011_should_return_XX_and_UP() {
    List<NistRefAmputationBandagedFPEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2011);

    assertThat(
            allowedValues.stream()
                .map(NistRefAmputationBandagedFPEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(Arrays.asList("XX", "UP"));
  }

  @Test
  void allowedValuesByStandard_when_standard_2013_should_return_XX_and_UP() {
    List<NistRefAmputationBandagedFPEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(
            allowedValues.stream()
                .map(NistRefAmputationBandagedFPEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(Arrays.asList("XX", "UP"));
  }

  @Test
  void allowedValuesByStandard_when_standard_2015_should_return_XX_and_UP_and_SR() {
    List<NistRefAmputationBandagedFPEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2015);

    assertThat(
            allowedValues.stream()
                .map(NistRefAmputationBandagedFPEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(Arrays.asList("XX", "UP", "SR"));
  }
}
