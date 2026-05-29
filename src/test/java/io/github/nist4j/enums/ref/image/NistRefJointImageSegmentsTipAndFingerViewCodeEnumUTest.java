/*
 * Copyright (C) 2026 Sopra Steria.
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
package io.github.nist4j.enums.ref.image;

import static io.github.nist4j.enums.RecordTypeEnum.RT13;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.NistStandardEnum;
import java.util.List;
import org.junit.jupiter.api.Test;

class NistRefJointImageSegmentsTipAndFingerViewCodeEnumUTest {

  @Test
  void listForSubfield_should_be_valid_for_RT13_and_FIC() {
    // Given
    List<String> expectedResultatInAllStd =
        asList("EJI", "TIP", "FV1", "FV2", "FV3", "FV4", "PRX", "DST", "MED");

    // When
    List<String> results2007 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "FIC"),
            NistStandardEnum.ANSI_NIST_ITL_2007);
    List<String> results2011 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "FIC"),
            NistStandardEnum.ANSI_NIST_ITL_2011);
    List<String> results2025 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "FIC"),
            NistStandardEnum.ANSI_NIST_ITL_2025);

    // Then
    assertThat(results2007).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2011).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2025).containsExactlyElementsOf(expectedResultatInAllStd);
  }

  @Test
  void listForSubfield_should_be_valid_for_RT14_and_FIC() {
    // Given
    List<String> expectedResultatInAllStd =
        asList("EJI", "TIP", "TPP", "FV1", "FV2", "FV3", "FV4", "PRX", "DST", "MED");

    // When
    List<String> results2007 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT14, "FIC"),
            NistStandardEnum.ANSI_NIST_ITL_2007);
    List<String> results2011 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT14, "FIC"),
            NistStandardEnum.ANSI_NIST_ITL_2011);
    List<String> results2025 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT14, "FIC"),
            NistStandardEnum.ANSI_NIST_ITL_2025);

    // Then
    assertThat(results2007).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2011).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2025).containsExactlyElementsOf(expectedResultatInAllStd);
  }

  @Test
  void listForSubfield_should_be_valid_for_RT13_FVC() {
    // Given
    List<String> expectedResultatInAllStd = asList("TIP", "FV1", "FV2", "FV3", "FV4", "NA");

    // When
    List<String> results2007 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "FVC"),
            NistStandardEnum.ANSI_NIST_ITL_2007);
    List<String> results2011 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "FVC"),
            NistStandardEnum.ANSI_NIST_ITL_2011);
    List<String> results2025 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "FVC"),
            NistStandardEnum.ANSI_NIST_ITL_2025);

    // Then
    assertThat(results2007).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2011).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2025).containsExactlyElementsOf(expectedResultatInAllStd);
  }

  @Test
  void listForSubfield_should_be_valid_for_RT13_LOS() {
    // Given
    List<String> expectedResultatInAllStd = asList("PRX", "DST", "MED", "NA");

    // When
    List<String> results2007 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "LOS"),
            NistStandardEnum.ANSI_NIST_ITL_2007);
    List<String> results2011 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "LOS"),
            NistStandardEnum.ANSI_NIST_ITL_2011);
    List<String> results2025 =
        findCodesAllowedByStandard(
            NistRefJointImageSegmentsTipAndFingerViewCodeEnum.listForSubfield(RT13, "LOS"),
            NistStandardEnum.ANSI_NIST_ITL_2025);

    // Then
    assertThat(results2007).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2011).containsExactlyElementsOf(expectedResultatInAllStd);
    assertThat(results2025).containsExactlyElementsOf(expectedResultatInAllStd);
  }
}
