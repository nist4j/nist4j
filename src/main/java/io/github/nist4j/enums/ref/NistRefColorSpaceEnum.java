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
package io.github.nist4j.enums.ref;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2025;

import io.github.nist4j.enums.NistStandardEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NistRefColorSpaceEnum implements INistReferentielEnum {
  UNK("UNK", "Undefined", ANSI_NIST_ITL_2025, null),
  GRAY(
      "GRAY",
      "For use when describing a grayscale image in a record which requires CSP",
      ANSI_NIST_ITL_2025,
      null),
  RGB("RGB", "Undetermined color space", ANSI_NIST_ITL_2025, null),
  SRGB("SRGB", "Undetermined color space", ANSI_NIST_ITL_2025, null),
  YCC("YCC", "Legacy only", ANSI_NIST_ITL_2025, null),
  SYCC("SYCC", "YCbCr (JPEG 2000 compressed)", ANSI_NIST_ITL_2025, null),
  ;

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
