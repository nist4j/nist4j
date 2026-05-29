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
package io.github.nist4j.enums.ref.image;

import static io.github.nist4j.enums.NistStandardEnum.*;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefFacialIMTImageTypeEnum implements INistReferentielEnum {
  SCAR(
      "SCAR",
      "Indicates a visible difference from standard tissue appearance on a body surface that is non-intentional or (quasi)medically induced",
      true,
      ANSI_NIST_ITL_2000,
      null),
  TATTOO("TATTOO", "Intentional placing of patterns on the skin", true, ANSI_NIST_ITL_2000, null),
  MARK("MARK", "Visible needle marks on the skin", true, ANSI_NIST_ITL_2000, null),
  FACE(
      "FACE",
      "A facial photograph. This may be used for facial recognition algorithms. Designation of FACE excludes the image from possibly being entered into systems for tattoo recognition. If a face has a tattoo on it, enter one image as a mugshot, indicated by FACE, and another marked TATTOO",
      false,
      ANSI_NIST_ITL_2000,
      null),
  FRONTAL_C("FRONTAL-C", "Frontal Body Image, Clothed", false, ANSI_NIST_ITL_2011, null),
  REAR_C("REAR-C", "Rear Body Image, Clothed", false, ANSI_NIST_ITL_2011, null),
  HEAD("HEAD", "Head Image", false, ANSI_NIST_ITL_2013, null),
  FRONTAL_N("FRONTAL-N", "Frontal Body Image, Nude", false, ANSI_NIST_ITL_2011, null),
  REAR_N("REAR-N", "Rear Body Image, Nude", false, ANSI_NIST_ITL_2011, null),
  TORSO_BACK("TORSO-BACK", "Torso Image, Back", false, ANSI_NIST_ITL_2011, null),
  TORSO_FRONT("TORSO-FRONT", "Torso Image, Front", false, ANSI_NIST_ITL_2011, null),
  CONDITION(
      "CONDITION",
      "Image of physical abnormality; must also use the appropriate NCIC code in Field 10.040: SMT / NCIC code",
      true,
      ANSI_NIST_ITL_2011,
      null),
  MISSING(
      "MISSING",
      "Shows the location on the body where the part would normally be",
      true,
      ANSI_NIST_ITL_2011,
      null),
  OTHER(
      "OTHER",
      "Image of a different NCIC SMT category; must also use the appropriate NCIC SMT code in Field 10.040: SMT / NCIC code",
      true,
      ANSI_NIST_ITL_2011,
      null),
  CHEST("CHEST", "Chest Image", false, ANSI_NIST_ITL_2011, null),
  FEET("FEET", "Foot or Feet Image", false, ANSI_NIST_ITL_2011, null),
  EXTRAORAL(
      "EXTRAORAL",
      "Exterior Mouth Area Image. Not intended for use on living individuals. FACE should be used for living individuals.",
      false,
      ANSI_NIST_ITL_2013,
      null),
  INTRAORAL(
      "INTRAORAL",
      "Interior Mouth Area Image. Need not be captured with the imager inserted into the oral cavity.",
      false,
      ANSI_NIST_ITL_2013,
      null),
  LIP("LIP", "Lip Image", false, ANSI_NIST_ITL_2013, null),
  HANDS_PALM("HANDS-PALM", "Hand Image, Palm Side", false, ANSI_NIST_ITL_2011, null),
  HANDS_BACK("HANDS-BACK", "Hand Image, Back", false, ANSI_NIST_ITL_2011, null),
  GENITALS("GENITALS", "Genital Image", false, ANSI_NIST_ITL_2011, null),
  BUTTOCKS("BUTTOCKS", "Buttocks Image", false, ANSI_NIST_ITL_2011, null),
  RIGHT_LEG("RIGHT LEG", "Right Leg Image", false, ANSI_NIST_ITL_2011, null),
  LEFT_LEG("LEFT LEG", "Left Leg Image", false, ANSI_NIST_ITL_2011, null),
  RIGHT_ARM("RIGHT ARM", "Right Arm Image", false, ANSI_NIST_ITL_2011, null),
  LEFT_ARM("LEFT ARM", "Left Arm Image", false, ANSI_NIST_ITL_2011, null),
  ;

  private final String code;
  private final String description;
  private final boolean isSMTOptional;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
