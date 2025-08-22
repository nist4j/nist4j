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

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefAcquisitionSourceTypeEnum implements INistReferentielEnum {
  UNSPECIFIED("UNSPECIFIED", "Unspecified or unknown", ANSI_NIST_ITL_2000, null),
  UNKNOWN_PHOTO(
      "UNKNOWN PHOTO", "Static photograph from an unknown source", ANSI_NIST_ITL_2000, null),
  DIGITAL_CAMERA(
      "DIGITAL CAMERA",
      "Static photograph from a digital still-image camera",
      ANSI_NIST_ITL_2000,
      null),
  SCANNER("SCANNER", "Static photograph from a scanner", ANSI_NIST_ITL_2000, null),
  UNKNOWN_VIDEO(
      "UNKNOWN VIDEO", "Single video frame from an unknown source", ANSI_NIST_ITL_2000, null),
  ANALOGUE_VIDEO(
      "ANALOGUE VIDEO",
      "Single video frame from an analogue video camera",
      ANSI_NIST_ITL_2000,
      null),
  DIGITAL_VIDEO(
      "DIGITAL VIDEO", "Single video frame from a digital video camera", ANSI_NIST_ITL_2000, null),
  VENDOR("VENDOR", "Vendor Specific source", ANSI_NIST_ITL_2000, null);

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
