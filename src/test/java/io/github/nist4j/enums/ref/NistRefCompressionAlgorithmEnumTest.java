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

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2007;
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2013;
import static io.github.nist4j.enums.RecordTypeEnum.*;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findValuesAllowedByStandard;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.ref.image.NistRefCompressionAlgorithmEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class NistRefCompressionAlgorithmEnumTest {

  private static List<NistRefCompressionAlgorithmEnum> getCGAListForRT(RecordTypeEnum recordType) {
    return Arrays.stream(NistRefCompressionAlgorithmEnum.values())
        .filter(cga -> cga.getAllowedRT().contains(recordType))
        .collect(Collectors.toList());
  }

  @Test
  void allowedValuesByStandard_when_standard_2000_should_return_empty_list() {
    List<NistRefCompressionAlgorithmEnum> allowedValues =
        findValuesAllowedByStandard(
            NistRefCompressionAlgorithmEnum.values(), NistStandardEnum.ANSI_NIST_ITL_2000);

    assertThat(allowedValues).isEmpty();
  }

  @Test
  void allowedValuesByStandard_when_standard_2007_for_RT4_RT14_should_return_expoected_list() {
    // Given
    List<String> expectedList = asList("NONE", "WSQ20", "JPEGB", "JPEGL", "JP2", "JP2L", "PNG");
    // When
    List<String> allowedCGAValuesForRT4 =
        findCodesAllowedByStandard(getCGAListForRT(RT4), ANSI_NIST_ITL_2007);
    List<String> allowedCGAValuesForRT14 =
        findCodesAllowedByStandard(getCGAListForRT(RT14), ANSI_NIST_ITL_2007);
    // Then
    assertThat(allowedCGAValuesForRT4).isEqualTo(expectedList);
    assertThat(allowedCGAValuesForRT14).isEqualTo(expectedList);
  }

  @Test
  void allowedValuesByStandard_when_standard_2007_for_RT10_should_return_expoected_list() {
    // Given
    List<NistRefCompressionAlgorithmEnum> cgaListForRT = getCGAListForRT(RT10);
    List<String> expectedList = asList("NONE", "JPEGB", "JPEGL", "JP2", "JP2L", "PNG");
    // When
    List<String> allowedValues = findCodesAllowedByStandard(cgaListForRT, ANSI_NIST_ITL_2007);
    // Then
    assertThat(allowedValues).isEqualTo(expectedList);
  }

  @Test
  void allowedValuesByStandard_when_standard_2013_should_return_2013_list() {
    // Given
    List<String> expectedList = asList("NONE", "WSQ20", "JPEGB", "JPEGL", "JP2", "JP2L", "PNG");
    List<NistRefCompressionAlgorithmEnum> cgaListForRT14 = getCGAListForRT(RT14);
    // When
    List<String> allowedValuesRT14 = findCodesAllowedByStandard(cgaListForRT14, ANSI_NIST_ITL_2013);

    // Then
    assertThat(allowedValuesRT14).isEqualTo(expectedList);
  }

  @Test
  void allowedValuesByStandard_when_standard_2013_for_RT13_should_return_2013_list() {
    // Given
    // spec p.104
    // Allowable values for compression entered in Field 13.011: Compression algorithm CGA are NONE,
    // JPEGL, JP2L, or PNG
    List<String> expectedList = asList("NONE", "WSQ20", "JPEGL", "JP2L", "PNG");
    List<NistRefCompressionAlgorithmEnum> cgaListForRT13 = getCGAListForRT(RT13);
    // When
    List<String> allowedValuesRT13 = findCodesAllowedByStandard(cgaListForRT13, ANSI_NIST_ITL_2013);

    // Then
    assertThat(allowedValuesRT13).isEqualTo(expectedList);
  }

  @Test
  void allowedValuesByStandard_when_standard_2015_should_return_2015_list() {
    // Given
    List<String> expectedList = asList("NONE", "WSQ20", "JPEGB", "JPEGL", "JP2", "JP2L", "PNG");
    // When
    List<String> allowedValues =
        findCodesAllowedByStandard(getCGAListForRT(RT14), NistStandardEnum.ANSI_NIST_ITL_2015);

    // Then
    assertThat(allowedValues).isEqualTo(expectedList);
  }
}
