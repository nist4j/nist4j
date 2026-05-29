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
import static io.github.nist4j.enums.ref.image.NistRefFacialSMTonTACTattooClassEnum.*;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefFacialSMT_TSCTattooSubclassEnum implements INistReferentielEnum {
  // Table 29a Human tattoo subclasses
  MFACE("MFACE", "Male Face", HUMAN, ANSI_NIST_ITL_2000, null),
  FFACE("FFACE", "Female Face", HUMAN, ANSI_NIST_ITL_2000, null),
  ABFACE("ABFACE", "Abstract Face", HUMAN, ANSI_NIST_ITL_2000, null),
  MBODY("MBODY", "Male Body", HUMAN, ANSI_NIST_ITL_2000, null),
  FBODY("FBODY", "Female Body", HUMAN, ANSI_NIST_ITL_2000, null),
  ABBODY("ABBODY", "Abstract Body", HUMAN, ANSI_NIST_ITL_2000, null),
  ROLES("ROLES", "Roles (Knight, Witch, man, etc.)", HUMAN, ANSI_NIST_ITL_2000, null),
  SPORT_PLAYER(
      "SPORT", "Sports Figures (Football Player, Skier, etc.)", HUMAN, ANSI_NIST_ITL_2000, null),
  MBPART("MBPART", "Male Body Parts", HUMAN, ANSI_NIST_ITL_2000, null),
  FBPART("FBPART", "Female Body Parts", HUMAN, ANSI_NIST_ITL_2000, null),
  ABBPART("ABBPART", "Abstract Body Parts", HUMAN, ANSI_NIST_ITL_2000, null),
  SKULL("SKULL", "Skulls", HUMAN, ANSI_NIST_ITL_2000, null),
  MHUMAN("MHUMAN", "Miscellaneous Human Forms", HUMAN, ANSI_NIST_ITL_2000, null),
  // Table 29b Animal tattoo subclasses
  CAT("CAT", "Cats & Cat Heads", ANIMAL, ANSI_NIST_ITL_2000, null),
  DOG("DOG", "Dogs & Dog Heads", ANIMAL, ANSI_NIST_ITL_2000, null),
  DOMESTIC("DOMESTIC", "Other Domestic Animals", ANIMAL, ANSI_NIST_ITL_2000, null),
  VICIOUS("VICIOUS", "Vicious Animals (Lions, etc.)", ANIMAL, ANSI_NIST_ITL_2000, null),
  HORSE("HORSE", "Horses (Donkeys, Mules, etc.)", ANIMAL, ANSI_NIST_ITL_2000, null),
  WILD("WILD", "Other Wild Animals", ANIMAL, ANSI_NIST_ITL_2000, null),
  SNAKE("SNAKE", "Snakes", ANIMAL, ANSI_NIST_ITL_2000, null),
  DRAGON("DRAGON", "Dragons", ANIMAL, ANSI_NIST_ITL_2000, null),
  BIRD("BIRD", "Birds (Cardinal, Hawk, etc.)", ANIMAL, ANSI_NIST_ITL_2000, null),
  INSECT("INSECT", "Spiders, Bugs, and Insects", ANIMAL, ANSI_NIST_ITL_2000, null),
  ABSTRACT_ANIMAL("ABSTRACT", "Abstract Animals", ANIMAL, ANSI_NIST_ITL_2000, null),
  PARTS("PARTS", "Animal Parts", ANIMAL, ANSI_NIST_ITL_2000, null),
  MANIMAL("MANIMAL", "Miscellaneous Animal Forms", ANIMAL, ANSI_NIST_ITL_2000, null),
  // Table 29c Plant tattoo subclasses
  NARCOTICS("NARCOTICS", "Narcotics", PLANT, ANSI_NIST_ITL_2000, null),
  REDFL("REDFL", "Red Flowers", PLANT, ANSI_NIST_ITL_2000, null),
  BLUEFL("BLUEFL", "Blue Flowers", PLANT, ANSI_NIST_ITL_2000, null),
  YELFL("YELFL", "Yellow Flowers", PLANT, ANSI_NIST_ITL_2000, null),
  DRAW("DRAW", "Drawings of Flowers", PLANT, ANSI_NIST_ITL_2000, null),
  ROSE("ROSE", "Rose", PLANT, ANSI_NIST_ITL_2000, null),
  TULIP("TULIP", "Tulip", PLANT, ANSI_NIST_ITL_2000, null),
  LILY("LILY", "Lily", PLANT, ANSI_NIST_ITL_2000, null),
  MPLANT("MPLANT", "Miscellaneous Plants, Flowers, Vegetables", PLANT, ANSI_NIST_ITL_2000, null),
  // Table 29d Flags tattoo subclasses
  USA("USA", "American Flag", FLAG, ANSI_NIST_ITL_2000, null),
  STATE("STATE", "State Flag", FLAG, ANSI_NIST_ITL_2000, null),
  NAZI("NAZI", "Nazi Flag", FLAG, ANSI_NIST_ITL_2000, null),
  CONFED("CONFED", "Confederate Flag", FLAG, ANSI_NIST_ITL_2000, null),
  BRIT("BRIT", "British Flag", FLAG, ANSI_NIST_ITL_2000, null),
  MFLAG("MFLAG", "Miscellaneous Flags", FLAG, ANSI_NIST_ITL_2000, null),
  // Table 29e Objects tattoo subclasses
  FIRE("FIRE", "Fire", OBJECT, ANSI_NIST_ITL_2000, null),
  WEAP("WEAP", "Weapons(Guns, Arrows, etc.)", OBJECT, ANSI_NIST_ITL_2000, null),
  PLANE("PLANE", "Airplanes", OBJECT, ANSI_NIST_ITL_2000, null),
  VESSEL("VESSEL", "Boats, Ships, & Other Vessels", OBJECT, ANSI_NIST_ITL_2000, null),
  TRAIN("TRAIN", "Trains", OBJECT, ANSI_NIST_ITL_2000, null),
  VEHICLE("VEHICLE", "Cars, Trucks, and Vehicles", OBJECT, ANSI_NIST_ITL_2000, null),
  MYTH("MYTH", "Mythical (Unicorns, etc.)", OBJECT, ANSI_NIST_ITL_2000, null),
  SPORT(
      "SPORT", "Sporting Objects (Football, Ski, Hurdles, etc.)", OBJECT, ANSI_NIST_ITL_2000, null),
  NATURE(
      "NATURE",
      "Water & Nature Scenes(Rivers, Sky, Trees, etc.)",
      OBJECT,
      ANSI_NIST_ITL_2000,
      null),
  MOBJECTS("MOBJECTS", "Miscellaneous Objects", OBJECT, ANSI_NIST_ITL_2000, null),
  // Table 29f Abstract tattoo subclasses
  FIGURE("FIGURE", "Figure(s)", ABSTRACT, ANSI_NIST_ITL_2000, null),
  SLEEVE("SLEEVE", "Sleeve", ABSTRACT, ANSI_NIST_ITL_2000, null),
  BRACE("BRACE", "Bracelet", ABSTRACT, ANSI_NIST_ITL_2000, null),
  ANKLET("ANKLET", "Anklet", ABSTRACT, ANSI_NIST_ITL_2000, null),
  NECKLC("NECKLC", "Necklace", ABSTRACT, ANSI_NIST_ITL_2000, null),
  SHIRT("SHIRT", "Shirt", ABSTRACT, ANSI_NIST_ITL_2000, null),
  BODBND("BODBND", "Body Band", ABSTRACT, ANSI_NIST_ITL_2000, null),
  HEDBND("HEDBND", "Head Band", ABSTRACT, ANSI_NIST_ITL_2000, null),
  MABSTRACT("MABSTRACT", "Miscellaneous Abstract", ABSTRACT, ANSI_NIST_ITL_2000, null),
  // Table 29g Symbols tattoo subclasses
  NATION("NATION", "National Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  POLITIC("POLITIC", "Political Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  MILITARY("MILITARY", "Military Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  FRATERNAL("FRATERNAL", "Fraternal Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  PROFESS("PROFESS", "Professional Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  GANG("GANG", "Gang Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  MSYMBOLS("MSYMBOLS", "Miscellaneous Symbols", SYMBOL, ANSI_NIST_ITL_2000, null),
  // Table 29h Other tattoo subclasses
  WORDING("WORDING", "Wording (Mom, Dad, Mary, etc.)", OTHER, ANSI_NIST_ITL_2000, null),
  FREEFRM("FREEFRM", "Freeform Drawings", OTHER, ANSI_NIST_ITL_2000, null),
  MISC("MISC", "Miscellaneous Images", OTHER, ANSI_NIST_ITL_2000, null),
  ;

  private final String code;
  private final String description;
  private final NistRefFacialSMTonTACTattooClassEnum tattooClass;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
