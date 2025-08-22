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
package io.github.nist4j.enums.ref.image;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2000;
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2011;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefSubjectAcquisitionProfilesEnum implements INistReferentielEnum {
  UNKNOWN_PROFILE("0", "Unknown profile", ANSI_NIST_ITL_2000, null),
  SURVEILLANCE_FACIAL("1", "Surveillance facial image", ANSI_NIST_ITL_2000, null),
  DRIVER_S_LICENSE("10", "Driver’s license image (AAMVA)", ANSI_NIST_ITL_2000, null),
  ANSI_FULL_FRONTAL_FACIAL(
      "11", "ANSI Full Frontal facial image (ANSI 385)", ANSI_NIST_ITL_2000, null),
  ANSI_TOKEN_FACIAL("12", "ANSI Token facial image (ANSI 385)", ANSI_NIST_ITL_2000, null),
  ISO_FULL_FRONTAL_FACIAL(
      "13", "ISO Full Frontal facial image (ISO/IEC 19794-5)", ANSI_NIST_ITL_2000, null),
  ISO_TOKEN_FACIAL("14", "ISO Token facial image (ISO/IEC 19794-5)", ANSI_NIST_ITL_2000, null),
  PIV_FACIAL("15", "PIV facial image (NIST SP 800-76)", ANSI_NIST_ITL_2000, null),
  LEGACY_MUGSHOT("20", "Legacy Mugshot", ANSI_NIST_ITL_2000, null),
  BEST_PRACTICE_APP_LVL_30("30", "Best Practice Application - Level 30", ANSI_NIST_ITL_2000, null),
  MOBILE_BEST_PRACTICE_LVL_32("32", "Mobile ID Best Practice - Level 32", ANSI_NIST_ITL_2011, null),
  BEST_PRACTICE_APP_LVL_40("40", "Best Practice Application - Level 40", ANSI_NIST_ITL_2000, null),
  MOBILE_BEST_PRACTICE_LVL_42("42", "Mobile ID Best Practice - Level 42", ANSI_NIST_ITL_2011, null),
  BEST_PRACTICE_APP_LVL_50("50", "Best Practice Application - Level 50", ANSI_NIST_ITL_2000, null),
  BEST_PRACTICE_APP_LVL_51("51", "Best Practice Application - Level 51", ANSI_NIST_ITL_2000, null),
  MOBILE_BEST_PRACTICE_LVL_52("52", "Mobile ID Best Practice - Level 52", ANSI_NIST_ITL_2011, null);

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
