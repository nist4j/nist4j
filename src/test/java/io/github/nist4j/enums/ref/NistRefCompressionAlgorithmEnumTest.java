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
import io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class NistRefCompressionAlgorithmEnumTest {

  private final NistRefCompressionAlgorithmEnum[] values = NistRefCompressionAlgorithmEnum.values();

  @Test
  void allowedValuesByStandard_when_standard_2000_should_return_empty_list() {
    List<NistRefCompressionAlgorithmEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2000);

    assertThat(allowedValues).isEmpty();
  }

  @Test
  void allowedValuesByStandard_when_standard_2007_should_return_the_whole_list() {
    List<NistRefCompressionAlgorithmEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2007);

    assertThat(allowedValues).hasSize(NistRefCompressionAlgorithmEnum.values().length);
    assertThat(
            allowedValues.stream()
                .map(NistRefCompressionAlgorithmEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(
            Arrays.stream(NistRefCompressionAlgorithmEnum.values())
                .map(NistRefCompressionAlgorithmEnum::getCode)
                .collect(Collectors.toList()));
  }

  @Test
  void allowedValuesByStandard_when_standard_2013_should_return_2013_list() {
    List<NistRefCompressionAlgorithmEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(allowedValues).hasSize(NistRefCompressionAlgorithmEnum.values().length);
    assertThat(
            allowedValues.stream()
                .map(NistRefCompressionAlgorithmEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(
            Arrays.stream(NistRefCompressionAlgorithmEnum.values())
                .map(NistRefCompressionAlgorithmEnum::getCode)
                .collect(Collectors.toList()));
  }

  @Test
  void allowedValuesByStandard_when_standard_2015_should_return_2015_list() {
    List<NistRefCompressionAlgorithmEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2015);

    assertThat(allowedValues).hasSize(NistRefCompressionAlgorithmEnum.values().length);
    assertThat(
            allowedValues.stream()
                .map(NistRefCompressionAlgorithmEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(
            Arrays.stream(NistRefCompressionAlgorithmEnum.values())
                .map(NistRefCompressionAlgorithmEnum::getCode)
                .collect(Collectors.toList()));
  }
}
