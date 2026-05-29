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
import static io.github.nist4j.enums.ref.image.NistRefImpTypeGroupEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Since Std2025 : <br>
 * - Updated descriptions of Impression Codes so that they explicitly include all friction <br>
 * - Deprecated old contactless codes and added a new one
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefImpressionTypeEnum implements INistReferentielEnum {
  PLAIN_CONTACT_FINGERPRINT(
      "0",
      "Plain fingerprint with contact - Former \"Livescan Plain fingerprint\"",
      asList(FINGER, FINGER_PLAIN, LIVESCAN),
      ANSI_NIST_ITL_2000,
      null),
  ROLLED_CONTACT_FINGERPRINT(
      "1",
      "Rolled fingerprint with contact - Former \"Livescan Rolled fingerprint\"",
      asList(FINGER, FINGER_ROLLED, LIVESCAN),
      ANSI_NIST_ITL_2000,
      null),
  NON_LIVESCAN_OF_PLAIN_FINGERPRINT(
      "2",
      "Nonlive-scan plain",
      asList(FINGER, FINGER_PLAIN, NON_LIVESCAN),
      ANSI_NIST_ITL_2000,
      ANSI_NIST_ITL_2015),
  NON_LIVESCAN_OF_ROLLED_FINGERPRINT(
      "3",
      "Nonlive-scan roll",
      asList(FINGER, FINGER_ROLLED, NON_LIVESCAN),
      ANSI_NIST_ITL_2000,
      ANSI_NIST_ITL_2015),
  LATENT_IMAGE(
      "4",
      "Latent image or impression - Former \"Latent impression\"",
      asList(FINGER, LATENT),
      ANSI_NIST_ITL_2000,
      null),
  LATENT_TRACING(
      "5", "Latent tracing", asList(FINGER, LATENT), ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LATENT_PHOTO("6", "Latent photo", asList(FINGER, LATENT), ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LATENT_LIFT("7", "Latent lift", asList(FINGER, LATENT), ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LIVESCAN_SWIPE_FP(
      "8",
      "Finger swiped on platen - Former \"Livescan Vertical Swipe fingerprint\"",
      asList(FINGER, FINGER_PLAIN, LIVESCAN),
      ANSI_NIST_ITL_2007,
      null),
  LIVESCAN_PALM("10", "Livescan Palm", singletonList(PALM), ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  NON_LIVESCAN_PALM(
      "11",
      "Non Livescan Palm",
      asList(PALM, NON_LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  LATENT_PALM_IMPRESSION(
      "12", "Latent Palm Impression", asList(PALM, LATENT), ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_TRACING(
      "13", "Latent Palm Tracing", asList(PALM, LATENT), ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_PHOTO(
      "14", "Latent Palm Photo", asList(PALM, LATENT), ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_LIFT(
      "15", "Latent Palm Lift", asList(PALM, LATENT), ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LIVESCAN_OPTICAL_CONTACT_PLAIN(
      "20",
      "Live-scan optical contact plain",
      asList(FINGER, FINGER_PLAIN, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  LIVESCAN_OPTICAL_CONTACT_ROLLED(
      "21",
      "Live-scan optical contact rolled",
      asList(FINGER, FINGER_ROLLED, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  LIVESCAN_NON_OPTICAL_CONTACT_PLAIN(
      "22",
      "Live-scan non optical contact plain",
      asList(FINGER, FINGER_PLAIN, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  LIVESCAN_NON_OPTICAL_CONTACT_ROLLED(
      "23",
      "Live-scan non optical contact rolled",
      asList(FINGER, FINGER_ROLLED, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  PLAIN_CONTACTLESS_STATIONARY_SUBJECT(
      "24",
      "Plain contactless – stationary subject - Former \"Livescan Optical contacless plain fingerprint\"",
      asList(FINGER, FINGER_PLAIN, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2025),
  ROLLED_CONTACTLESS_STATIONARY_SUBJECT(
      "25",
      "Rolled contactless – stationary subject - Former \"Livescan Optical contacless rolled fingerprint\"",
      asList(FINGER, FINGER_ROLLED, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2025),
  LIVESCAN_NON_OPTICAL_CONTACTLESS_PLAIN(
      "26",
      "Live-scan non optical contactless plain",
      asList(FINGER, FINGER_PLAIN, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  LIVESCAN_NON_OPTICAL_CONTACTLESS_ROLLED(
      "27",
      "Live-scan non optical contactless rolled",
      asList(FINGER, FINGER_ROLLED, LIVESCAN),
      ANSI_NIST_ITL_2007,
      ANSI_NIST_ITL_2015),
  OTHER("28", "Other", singletonList(NO_GROUP), ANSI_NIST_ITL_2007, null),
  UNKNOWN("29", "Unknown", singletonList(NO_GROUP), ANSI_NIST_ITL_2007, null),
  LIVESCAN_PLANTAR(
      "30",
      "Livescan (type unknown or unspecified) plantar",
      asList(PLANTAR, LIVESCAN),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  NON_LIVESCAN_PLANTAR(
      "31",
      "Non livescan plantar",
      asList(PLANTAR, NON_LIVESCAN),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_IMPRESSION(
      "32",
      "Latent Plantar Impression",
      asList(PLANTAR, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_TRACING(
      "33",
      "Latent Plantar Tracing",
      asList(PLANTAR, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_PHOTO(
      "34",
      "Latent Plantar Photo",
      asList(PLANTAR, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_LIFT(
      "35", "Latent Plantar Lift", asList(PLANTAR, LATENT), ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_IMPRESSION(
      "36",
      "Latent Unknown friction ridge Impression",
      asList(UNKNOWN_FRICTION_RIDGE, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_TRACING(
      "37",
      "Latent Unknown friction ridge Tracing",
      asList(UNKNOWN_FRICTION_RIDGE, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_PHOTO(
      "38",
      "Latent Unknown friction ridge Photo",
      asList(UNKNOWN_FRICTION_RIDGE, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_LIFT(
      "39",
      "Latent Unknown friction ridge Lift",
      asList(UNKNOWN_FRICTION_RIDGE, LATENT),
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  ROLLED_CONTACTLESS_MOVING_SUBJECT(
      "41",
      "Rolled contactless – moving subject",
      asList(FINGER, FINGER_ROLLED, PALM, PLANTAR),
      ANSI_NIST_ITL_2015,
      ANSI_NIST_ITL_2025),
  PLAIN_CONTACTLESS_MOVING_SUBJECT(
      "42",
      "Plain contactless – moving subject",
      asList(FINGER, FINGER_PLAIN, PALM, PLANTAR),
      ANSI_NIST_ITL_2015,
      ANSI_NIST_ITL_2025),
  CONTACTLESS_CAPTURE(
      "43", "Contactless capture", singletonList(NO_GROUP), ANSI_NIST_ITL_2025, null),
  ;

  private final String code;
  private final String description;
  private final List<NistRefImpTypeGroupEnum> groups;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;

  public static List<NistRefImpressionTypeEnum> listByAllGroups(NistRefImpTypeGroupEnum... groups) {
    if (isNull(groups)) {
      return Collections.emptyList();
    }
    Set<NistRefImpTypeGroupEnum> whiteList = new HashSet<>(Arrays.asList(groups));
    return Arrays.stream(NistRefImpressionTypeEnum.values())
        .filter(imp -> whiteList.containsAll(imp.getGroups()))
        .collect(Collectors.toList());
  }

  public static List<NistRefImpressionTypeEnum> listByAnyGroups(NistRefImpTypeGroupEnum... groups) {
    if (isNull(groups)) {
      return Collections.emptyList();
    }
    Set<NistRefImpTypeGroupEnum> whiteList = new HashSet<>(Arrays.asList(groups));
    return Arrays.stream(NistRefImpressionTypeEnum.values())
        .filter(imp -> containsAnyOf(imp.getGroups(), whiteList))
        .collect(Collectors.toList());
  }

  private static <T> boolean containsAnyOf(Collection<T> groups1, Collection<T> groups2) {
    if (isNull(groups1) || isNull(groups2)) {
      return false;
    }
    return groups1.stream().anyMatch(groups2::contains);
  }
}
