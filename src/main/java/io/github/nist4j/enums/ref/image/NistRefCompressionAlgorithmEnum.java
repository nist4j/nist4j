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

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2007;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NistRefCompressionAlgorithmEnum implements INistReferentielEnum {
  NONE("NONE", "0", "Uncompressed", ANSI_NIST_ITL_2007, null),
  WSQ20("WSQ20", "1", "WSQ", ANSI_NIST_ITL_2007, null),
  JPEGB("JPEGB", "2", "JPEG ISO/IEC 10918 (Lossy)", ANSI_NIST_ITL_2007, null),
  JPEGL("JPEGL", "3", "JPEG ISO/IEC 10918 (Lossless)", ANSI_NIST_ITL_2007, null),
  JP2("JP2", "4", "JPEG 2000 ISO/IEC 15444-1 (Lossy)", ANSI_NIST_ITL_2007, null),
  JP2L("JP2L", "5", "JPEG 2000 ISO/IEC 15444-1 (Lossless)", ANSI_NIST_ITL_2007, null),
  PNG("PNG", "6", "Portable Network Graphics", ANSI_NIST_ITL_2007, null);

  private final String code;
  private final String codeBinary;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
