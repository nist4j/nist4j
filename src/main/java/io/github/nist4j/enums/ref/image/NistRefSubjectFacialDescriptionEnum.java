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
public enum NistRefSubjectFacialDescriptionEnum implements INistReferentielEnum {
  UNKNOWN("UNKNOWN", "Expression unspecified", ANSI_NIST_ITL_2000, null),
  NEUTRAL(
      "NEUTRAL",
      "Neutral (non-smiling) with both eyes open and mouth closed)",
      ANSI_NIST_ITL_2000,
      null),
  SMILE(
      "SMILE",
      "Smiling where the inside of the mouth and/or teeth is not exposed (closed jaw)",
      ANSI_NIST_ITL_2000,
      null),
  MOUTH_OPEN("MOUTH OPEN", "Subject Having Mouth open", ANSI_NIST_ITL_2000, null),
  TEETH_VISIBLE("TEETH VISIBLE", "Having Teeth visible", ANSI_NIST_ITL_2000, null),
  RAISED_BROWS("RAISED BROWS", "Raising eyebrows", ANSI_NIST_ITL_2000, null),
  FROWNING("FROWNING", "Frowning", ANSI_NIST_ITL_2000, null),
  EYES_AWAY("EYES AWAY", "Looking away from the camera", ANSI_NIST_ITL_2000, null),
  SQUINTING("SQUINTING", "Squinting", ANSI_NIST_ITL_2000, null),
  LEFT_EYE_PATCH("LEFT EYE PATCH", "Subject Wearing Left Eye Patch", ANSI_NIST_ITL_2000, null),
  RIGHT_EYE_PATCH("RIGHT EYE PATCH", "Subject Wearing Right Eye Patch", ANSI_NIST_ITL_2000, null),
  CLEAR_GLASSES("CLEAR GLASSES ", "Subject Wearing Clear Glasses", ANSI_NIST_ITL_2000, null),
  DARK_GLASSE(
      "DARK GLASSE",
      "Subject Wearing Dark or Visible Colored Glasses \n" + "(medical)",
      ANSI_NIST_ITL_2000,
      null),
  HAT("HAT", "Head covering/hat", ANSI_NIST_ITL_2000, null),
  SCARF("SCARF", "Wearing Scarf", ANSI_NIST_ITL_2000, null),
  MOUSTACHE("MOUSTACHE", "Having Moustache ", ANSI_NIST_ITL_2000, null),
  BEARD("BEARD", "Having Beard", ANSI_NIST_ITL_2000, null),
  NO_EAR("NO EAR", "Ear(s) obscured by hair", ANSI_NIST_ITL_2000, null),
  BLINK("BLINK", "Blinking (either or both eyes closed)", ANSI_NIST_ITL_2000, null),
  DISTORTING_CONDITION(
      "DISTORTING CONDITION",
      "Having Distorting Medical Condition impacting, Feature Point detection",
      ANSI_NIST_ITL_2000,
      null);

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
