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
import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2025;
import static io.github.nist4j.enums.RecordTypeEnum.*;
import static java.util.Arrays.asList;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * History:<br>
 * - Std2025: 13.011 CGA, Added new code ‘PNM’, designated WSQ20 as Legacy use only.<br>
 * - Std2025: 13.011 CGA, Deprecated old contactless codes and added a new one<br>
 * Exceptions:<br>
 * - NOTE_001: WSQ20 in RT13 seems to be formidden according to the spec but is present in reference
 * cases 'type-13-tip-eji-wsq.an2'<br>
 */
@SuppressWarnings("deprecation")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NistRefCompressionAlgorithmEnum implements INistReferentielEnum {
  NONE(
      "NONE",
      "0",
      "Uncompressed",
      asList(RT3, RT4, RT5, RT6, RT10, RT13, RT14, RT15, RT16, RT17, RT19, RT20),
      ANSI_NIST_ITL_2007,
      null),
  WSQ(
      "WSQ",
      null,
      "WSQa (Version 3.1:2010) 500 ppi Only. Preferred Code for WSQ compression",
      asList(RT14, RT15, RT16, RT19, RT20),
      ANSI_NIST_ITL_2025,
      null),
  WSQ20(
      "WSQ20",
      "1",
      "WSQ - Legacy Use Only",
      asList(RT3, RT4, RT5, RT6, RT13 /*NOTE_001*/, RT14, RT15, RT16, RT19, RT20),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2025),
  JPEGB(
      "JPEGB",
      "2",
      "JPEG ISO/IEC 10918 (Lossy) - Legacy Only 500 ppi Only",
      asList(RT3, RT4, RT5, RT6, RT10, RT14, RT15, RT16, RT19, RT20),
      ANSI_NIST_ITL_2007,
      null),
  JPEGL(
      "JPEGL",
      "3",
      "JPEG ISO/IEC 10918 (Lossless) – Legacy Use Only 500 ppi Only",
      asList(RT3, RT4, RT5, RT6, RT10, RT13, RT14, RT15, RT16, RT19, RT20),
      ANSI_NIST_ITL_2007,
      null),
  JP2(
      "JP2",
      "4",
      "JPEG 2000 ISO/IEC 15444-1 (Lossy) - 1000 ppi Only",
      asList(RT3, RT4, RT5, RT6, RT10, RT14, RT15, RT17, RT19, RT20),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2025),
  JPEG2(
      "JPEG2",
      null,
      "JPEG 2000 ISO/IEC 15444-1 (Lossy) - 1000 ppi Only",
      asList(RT10, RT16),
      ANSI_NIST_ITL_2025,
      null),
  JP2L(
      "JP2L",
      "5",
      "JPEG 2000 ISO/IEC 15444-1 (Lossless)",
      asList(RT3, RT4, RT5, RT6, RT10, RT13, RT14, RT15, RT16, RT17, RT19),
      ANSI_NIST_ITL_2007,
      null),
  PNG(
      "PNG",
      "6",
      "Portable Network Graphics",
      asList(RT3, RT4, RT5, RT6, RT10, RT13, RT14, RT15, RT16, RT17, RT19, RT20),
      ANSI_NIST_ITL_2007,
      null),
  PNM(
      "PNM",
      null,
      "Portable Anymap Format (Netpbm)",
      asList(RT10, RT13, RT14, RT15, RT16, RT17, RT19, RT20),
      ANSI_NIST_ITL_2025,
      null),
  MEDIA(
      "MEDIA",
      null,
      "A compression type referenced from the IANA registry",
      asList(RT10, RT16, RT20),
      ANSI_NIST_ITL_2025,
      null),
  ;

  private final String code;
  private final String codeBinary;
  private final String description;
  private final List<RecordTypeEnum> allowedRT;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
