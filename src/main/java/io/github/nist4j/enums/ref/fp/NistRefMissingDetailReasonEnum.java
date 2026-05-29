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

import static io.github.nist4j.enums.NistStandardEnum.*;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NistRefMissingDetailReasonEnum implements INistReferentielEnum {
  PA("PA", "Partial amputation of fingertip (distal phalanx)", ANSI_NIST_ITL_2025, null),
  FA("FA", "Full amputation of fingertip (distal phalanx)", ANSI_NIST_ITL_2025, null),
  SR("BM", "Bandaged or other medical issue", ANSI_NIST_ITL_2025, null),
  ;
  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
