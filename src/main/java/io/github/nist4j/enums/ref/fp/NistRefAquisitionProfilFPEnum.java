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
package io.github.nist4j.enums.ref.fp;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2011;
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2015;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NistRefAquisitionProfilFPEnum implements INistReferentielEnum {
  FP_10("10", ANSI_NIST_ITL_2011, null),
  FP_20("20", ANSI_NIST_ITL_2011, null),
  FP_30("30", ANSI_NIST_ITL_2011, null),
  FP_40("40", ANSI_NIST_ITL_2011, null),
  FP_45("45", ANSI_NIST_ITL_2011, null),
  FP_50("50", ANSI_NIST_ITL_2011, null),
  FP_60("60", ANSI_NIST_ITL_2011, null),
  FP_145("145", ANSI_NIST_ITL_2015, null),
  FP_150("150", ANSI_NIST_ITL_2015, null),
  FP_160("160", ANSI_NIST_ITL_2015, null),
  ;
  private final String code;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
