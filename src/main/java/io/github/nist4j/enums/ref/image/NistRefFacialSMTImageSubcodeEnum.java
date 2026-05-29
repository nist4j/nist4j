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

import static io.github.nist4j.enums.NistStandardEnum.*;
import static io.github.nist4j.enums.ref.image.NistRefFacialIMTImageTypeEnum.*;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefFacialSMTImageSubcodeEnum implements INistReferentielEnum {
  // Scar codes,
  CODE_SCAR(
      "SCAR",
      "Healed tissue that was the result of an accident or medical procedure",
      SCAR,
      ANSI_NIST_ITL_2000,
      null),
  BIRTHMARK(
      "BIRTHMARK",
      "Tissue that is differentiated from normal tissue but is not the result of an accident or medical procedure, such as  a 'blood stain' birthmark on part of the face.",
      SCAR,
      ANSI_NIST_ITL_2015,
      null),
  PIERCING(
      "PIERCING",
      "A medical or quasi-medically induced hole in or through the skin – often to allow the insertion of jewelry.",
      SCAR,
      ANSI_NIST_ITL_2000,
      null),
  ZABIBA(
      "ZABIBA",
      "Commonly referred to as a 'prayer bump' on the forehead.",
      SCAR,
      ANSI_NIST_ITL_2015,
      null),
  IMPLANT(
      "IMPLANT",
      "Sub-dermal implants that are visible as distinct shapes in the skin. This category may also be used to indicate jewelry that has been (semi-)permanently affixed to the body – such as plugs in the earlobes or a microdermal implant that has a jewel above the skin.",
      SCAR,
      ANSI_NIST_ITL_2015,
      null),
  // TATTOO
  TATTOO_CODE(
      "TATTOO",
      "An image on the skin resulting from pricking of the skin with a coloring material. This also includes tattoos that have been removed but still leave a pattern on the skin. Note that some tattoos may be visible only in ultra-violet light.",
      TATTOO,
      ANSI_NIST_ITL_2000,
      null),
  CHEMICAL(
      "CHEMICAL",
      "Creation of an image on the skin by using chemicals.",
      TATTOO,
      ANSI_NIST_ITL_2000,
      null),
  BRANDED(
      "BRANDED",
      "Creation of an image on the skin by using a branding iron or other form of heat.",
      TATTOO,
      ANSI_NIST_ITL_2000,
      null),
  CUT(
      "CUT",
      "Creation of an image resulting from patterned cutting of the skin and resultant healing.",
      TATTOO,
      ANSI_NIST_ITL_2000,
      null),
  // MARK
  MARK_CODE("MARK", "Patterns of needle marks on the skin.", MARK, ANSI_NIST_ITL_2000, null),
  ;

  private final String code;
  private final String description;
  private final NistRefFacialIMTImageTypeEnum imageType;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
