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

import static io.github.nist4j.enums.TestReferenceUtils.generateListIncrementedInt;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findValuesAllowedByStandard;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.image.NistRefImpressionTypeEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class NistRefImpressionTypeEnumTest {
  private final NistRefImpressionTypeEnum[] values = NistRefImpressionTypeEnum.values();

  @Test
  void allowedValuesByStandard_when_standard_2000_should_return_0to7_list() {
    List<NistRefImpressionTypeEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2000);

    assertThat(
            allowedValues.stream()
                .map(NistRefImpressionTypeEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(generateListIncrementedInt(0, 7));
  }

  @Test
  void allowedValuesByStandard_when_standard_2007_should_return_0to7_and_10to15_and_20to29_list() {
    List<NistRefImpressionTypeEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2007);

    List<String> expectedCodes = generateListIncrementedInt(0, 8);
    expectedCodes.addAll(generateListIncrementedInt(10, 15));
    expectedCodes.addAll(generateListIncrementedInt(20, 29));

    assertThat(
            allowedValues.stream()
                .map(NistRefImpressionTypeEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(expectedCodes);
  }

  @Test
  void allowedValuesByStandard_when_standard_2013_should_return_0to7_and_10to15_and_20to39_list() {
    List<NistRefImpressionTypeEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2013);
    List<String> expectedCodes = generateListIncrementedInt(0, 8);
    expectedCodes.addAll(generateListIncrementedInt(10, 15));
    expectedCodes.addAll(generateListIncrementedInt(20, 39));

    assertThat(
            allowedValues.stream()
                .map(NistRefImpressionTypeEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(expectedCodes);
  }

  @Test
  void allowedValuesByStandard_when_standard_2015_should_return_2015_list() {
    List<NistRefImpressionTypeEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2015);

    assertThat(
            allowedValues.stream()
                .map(NistRefImpressionTypeEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(Arrays.asList("0", "1", "4", "8", "24", "25", "28", "29", "41", "42"));
  }
}
