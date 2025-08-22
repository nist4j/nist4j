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
public enum NistRefSubjectEyeColorEnum implements INistReferentielEnum {
  BLACK("BLK", "Black", ANSI_NIST_ITL_2000, null),
  BLUE("BLU", "Blue", ANSI_NIST_ITL_2000, null),
  BROWN("BRO", "Brown", ANSI_NIST_ITL_2000, null),
  GRAY("GRY", "Gray", ANSI_NIST_ITL_2000, null),
  GREEN("GRN", "Green", ANSI_NIST_ITL_2000, null),
  HAZEL("HAZ", "Hazel", ANSI_NIST_ITL_2000, null),
  MAROON("MAR", "Maroon", ANSI_NIST_ITL_2000, null),
  MULTICOLORED("MUL", "Multicolored", ANSI_NIST_ITL_2000, null),
  PINK("PNK", "Pink", ANSI_NIST_ITL_2000, null),
  UNKNOWN("XXX", "Unknown", ANSI_NIST_ITL_2000, null);

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
