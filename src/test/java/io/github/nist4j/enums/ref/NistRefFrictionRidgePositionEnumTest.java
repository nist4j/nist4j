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
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.FINGERS_AND_PALMS;
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.TEN_FINGERS;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NistRefFrictionRidgePositionEnumTest {
  private final NistRefFrictionRidgePositionEnum[] values =
      NistRefFrictionRidgePositionEnum.values();

  private List<String> allowedFingers;
  private List<String> allowedFingersCombination;
  private List<String> allowedPalms;
  private List<String> allowedPlantar;
  private List<String> allowedUnknown;

  @BeforeEach
  void setUp() {
    allowedFingers = new ArrayList<>();
    allowedFingersCombination = new ArrayList<>();
    allowedPalms = new ArrayList<>();
    allowedPlantar = new ArrayList<>();
    allowedUnknown = new ArrayList<>();
  }

  @Test
  void
      allowedValuesByStandard_when_standard_2000_should_return_0to12_fingers_and_13to14_for_fingers_combination_and_20to30_for_palm() {
    allowedFingers = generateListIncrementedInt(0, 12);
    allowedFingersCombination = generateListIncrementedInt(13, 14);
    allowedPalms = generateListIncrementedInt(20, 30);

    List<NistRefFrictionRidgePositionEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2000);

    assertAllowedValuesList(allowedValues);
  }

  @Test
  void
      allowedValuesByStandard_when_standard_2007_should_return_0to15_19_for_fingers_and_20to36_for_palm() {
    allowedFingers = generateListIncrementedInt(0, 12);
    allowedFingersCombination = generateListIncrementedInt(13, 15);
    allowedFingersCombination.add("19");
    allowedPalms = generateListIncrementedInt(20, 36);

    List<NistRefFrictionRidgePositionEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2007);

    assertAllowedValuesList(allowedValues);
  }

  @Test
  void
      allowedValuesByStandard_when_standard_2011_should_return_0to12_plus_16to17_for_fingers_and_20to38_plus_81to86_for_palm_and_60to79_for_plantar_and_13to15_plus_19_plus_40to50_for_combination() {
    allowedFingers = generateListIncrementedInt(0, 12);
    allowedFingers.addAll(generateListIncrementedInt(16, 17));
    allowedFingersCombination = generateListIncrementedInt(13, 15);
    allowedFingersCombination.add("19");
    allowedFingersCombination.addAll(generateListIncrementedInt(40, 50));
    allowedPalms = generateListIncrementedInt(20, 38);
    allowedPalms.addAll(generateListIncrementedInt(81, 86));
    allowedPlantar = generateListIncrementedInt(60, 79);
    allowedUnknown = Collections.singletonList("18");

    List<NistRefFrictionRidgePositionEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2011);

    assertAllowedValuesList(allowedValues);
  }

  @Test
  void
      allowedValuesByStandard_when_standard_2013_should_return_0to12_plus_16to17_for_fingers_and_20to38_plus_81to86_for_palm_and_60to79_for_plantar_and_13to15_plus_19_plus_40to54_for_combination() {
    allowedFingers = generateListIncrementedInt(0, 12);
    allowedFingers.addAll(generateListIncrementedInt(16, 17));
    allowedFingersCombination = generateListIncrementedInt(13, 15);
    allowedFingersCombination.add("19");
    allowedFingersCombination.addAll(generateListIncrementedInt(40, 54));
    allowedPalms = generateListIncrementedInt(20, 38);
    allowedPalms.addAll(generateListIncrementedInt(81, 86));
    allowedPlantar = generateListIncrementedInt(60, 79);
    allowedUnknown = Collections.singletonList("18");

    List<NistRefFrictionRidgePositionEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2013);

    assertAllowedValuesList(allowedValues);
  }

  @Test
  void allowedValuesByStandard_when_standard_2015_should_be_the_same_as_2013() {
    List<NistRefFrictionRidgePositionEnum> allowedValues =
        findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2015);

    assertThat(allowedValues)
        .isEqualTo(findValuesAllowedByStandard(values, NistStandardEnum.ANSI_NIST_ITL_2013));
  }

  @Test
  void allowedBasicFingersByStandard_standard_2011_should_return_1to10_plus_16_plus_17() {
    List<String> allowedValues =
        NistReferentielHelperImpl.findCodesAllowedByStandard(
            TEN_FINGERS, NistStandardEnum.ANSI_NIST_ITL_2011);

    assertThat(allowedValues)
        .isNotEmpty()
        .allMatch(
            posStr -> {
              int pos = Integer.parseInt(posStr);
              return (pos >= 0 && pos <= 10) || pos == 16 || pos == 17;
            });
  }

  @Test
  void allowedFingersAndPalm_standard_2007_should_return_1to10_plus_16_plus_17_plus_20_to_36() {
    List<String> allowedValues =
        NistReferentielHelperImpl.findCodesAllowedByStandard(
            FINGERS_AND_PALMS, NistStandardEnum.ANSI_NIST_ITL_2007);

    assertThat(allowedValues)
        .isNotEmpty()
        .allMatch(
            posStr -> {
              int pos = Integer.parseInt(posStr);
              return (pos >= 0 && pos <= 10) || pos == 16 || pos == 17 || (pos >= 20 && pos <= 36);
            });
  }

  private void assertAllowedValuesList(List<NistRefFrictionRidgePositionEnum> allowedValues) {
    assertThat(allowedValues)
        .hasSize(
            allowedFingers.size()
                + allowedPalms.size()
                + allowedPlantar.size()
                + allowedFingersCombination.size()
                + allowedUnknown.size());

    // FINGERS

    assertThat(
            allowedValues.stream()
                .filter(
                    frp ->
                        NistRefFrictionRidgePositionEnum.FrictionRidgeType.FINGER.equals(
                            frp.getType()))
                .map(NistRefFrictionRidgePositionEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(allowedFingers);

    // PALMS
    assertThat(
            allowedValues.stream()
                .filter(
                    frp ->
                        NistRefFrictionRidgePositionEnum.FrictionRidgeType.PALM.equals(
                            frp.getType()))
                .map(NistRefFrictionRidgePositionEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(allowedPalms);

    // Plantar
    assertThat(
            allowedValues.stream()
                .filter(
                    frp ->
                        NistRefFrictionRidgePositionEnum.FrictionRidgeType.PLANTAR.equals(
                            frp.getType()))
                .map(NistRefFrictionRidgePositionEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(allowedPlantar);

    // Fingers combination
    assertThat(
            allowedValues.stream()
                .filter(
                    frp ->
                        NistRefFrictionRidgePositionEnum.FrictionRidgeType.FINGERS_COMBINATION
                            .equals(frp.getType()))
                .map(NistRefFrictionRidgePositionEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(allowedFingersCombination);

    // Unknown
    assertThat(
            allowedValues.stream()
                .filter(frp -> frp.getType() == null)
                .map(NistRefFrictionRidgePositionEnum::getCode)
                .collect(Collectors.toList()))
        .isEqualTo(allowedUnknown);
  }
}
