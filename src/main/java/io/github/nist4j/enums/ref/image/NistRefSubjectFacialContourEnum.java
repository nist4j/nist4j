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

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2011;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefSubjectFacialContourEnum implements INistReferentielEnum {
  EYETOP("eyetop", "Bottom of upper eye lid", ANSI_NIST_ITL_2011, null),
  EYEBOTTOM("eyebottom", "Top of lower eye lid", ANSI_NIST_ITL_2011, null),
  UPPERLIPTOP("upperliptop", "Top of upper lip", ANSI_NIST_ITL_2011, null),
  UPPERLIPBOTTOM("upperlipbottom", "Bottom of upper lip", ANSI_NIST_ITL_2011, null),
  LOWERLIPTOP("lowerliptop", "Top of lower lip", ANSI_NIST_ITL_2011, null),
  LOWERLIPBOTTOM("lowerlipbottom", "Bottom of lower lip", ANSI_NIST_ITL_2011, null),
  RIGHTNOSTRIL("rightnostril", "Subject’s right nostril", ANSI_NIST_ITL_2011, null),
  LEFTNOSTRIL("leftnostril", "Subject’s left nostril", ANSI_NIST_ITL_2011, null),
  LEFTEYEBROW(
      "lefteyebrow", "Curvature of top of subject’s left eye socket", ANSI_NIST_ITL_2011, null),
  RIGHTEYEBROW(
      "righteyebrow", "Curvature of top of subject’s right eye socket", ANSI_NIST_ITL_2011, null),
  CHIN("chin", "Chin", ANSI_NIST_ITL_2011, null),
  FACEOUTLINE(
      "faceoutline",
      "Face outline includes the entire head, all facial hair, and ears",
      ANSI_NIST_ITL_2011,
      null);

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
