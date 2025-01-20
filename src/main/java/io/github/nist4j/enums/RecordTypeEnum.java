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

import static io.github.nist4j.enums.NistStandardEnum.*;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RecordTypeEnum {
  RT1(1, "Type 1 - Transaction Information", ANSI_NIST_ITL_2000, null),
  RT2(2, "Type 2 - Descriptive Text (User)", ANSI_NIST_ITL_2000, null),
  @Deprecated
  RT3(3, "Type 3 - Low-resolution grayscale", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2011),
  RT4(4, "Type 4 - High-resolution grayscale", ANSI_NIST_ITL_2000, null),
  @Deprecated
  RT5(5, "Type 5 - Low-resolution binary", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2011),
  @Deprecated
  RT6(6, "Type 6 - High-resolution binary", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2011),
  RT7(7, "Type 7 - User-defined Image data", ANSI_NIST_ITL_2000, null),
  RT8(8, "Type 8 - Signature image data", ANSI_NIST_ITL_2000, null),
  RT9(9, "Type 9 - Minutiae data", ANSI_NIST_ITL_2000, null),
  RT10(10, "Type 10 - Facial and SMT image data", ANSI_NIST_ITL_2000, null),
  RT11(11, "Type 11 -  Forensic and investigatory voice record", ANSI_NIST_ITL_2013, null),
  RT12(12, "Type 12 - Forensic dental and oral record", ANSI_NIST_ITL_2013, null),
  RT13(13, "Type 13 - Latent image data (VR)", ANSI_NIST_ITL_2000, null),
  RT14(14, "Type 14 - Tenprint Fingerprint (VR)", ANSI_NIST_ITL_2000, null),
  RT15(15, "Type 15 - Palm print image data (VR)", ANSI_NIST_ITL_2000, null),
  RT16(16, "Type 16 - User-defined testing image record", ANSI_NIST_ITL_2000, null),
  RT17(17, "Type 17 - Iris image data", ANSI_NIST_ITL_2007, null),
  RT18(18, "Type 18 - DNA data", ANSI_NIST_ITL_2011, null),
  RT19(19, "Type 19 - Plantar image data", ANSI_NIST_ITL_2011, null),
  RT20(20, "Type 20 - Source representation", ANSI_NIST_ITL_2011, null),
  RT21(21, "Type 21 - Associated context", ANSI_NIST_ITL_2011, null),
  RT22(22, "Type 22 - Non-photographic imagery data", ANSI_NIST_ITL_2013, null),
  RT98(98, "Type 98 - Information assurance record", ANSI_NIST_ITL_2011, null),
  RT99(99, "Type 99 - CBEFF biometric data record", ANSI_NIST_ITL_2007, null);

  private final int number;
  private final String label;
  // Standard à partir duquel le type de record a été créé
  private final NistStandardEnum createdFromStandard;
  // Standard à partir duquel le type de record a été supprimé
  private final NistStandardEnum deprecatedFromStandard;

  private boolean isRecordTypeAllowedForStandard(NistStandardEnum nistStandardEnum) {
    return nistStandardEnum.isBetweenStandards(
        this.createdFromStandard, this.deprecatedFromStandard);
  }

  public static List<RecordTypeEnum> forbiddenRecordTypesByStandard(
      NistStandardEnum nistStandardEnum) {
    return Arrays.stream(RecordTypeEnum.values())
        .filter(recordTypeEnum -> !recordTypeEnum.isRecordTypeAllowedForStandard(nistStandardEnum))
        .collect(Collectors.toList());
  }

  public static RecordTypeEnum findByRecordId(int recordId) {
    return Arrays.stream(RecordTypeEnum.values())
        .filter(rTenum -> rTenum.getNumber() == recordId)
        .findFirst()
        .orElseThrow(
            () -> new InvalidFormatNist4jException("Record inconnu pour recordId: " + recordId));
  }
}
