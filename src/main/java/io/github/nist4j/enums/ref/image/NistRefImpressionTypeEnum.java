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

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefImpressionTypeEnum implements INistReferentielEnum {
  PLAIN_CONTACT_FINGERPRINT(
      "0",
      "Plain fingerprint with contact - Former \"Livescan Plain fingerprint\"",
      ANSI_NIST_ITL_2000,
      null),
  ROLLED_CONTACT_FINGERPRINT(
      "1",
      "Rolled fingerprint with contact - Former \"Livescan Rolled fingerprint\"",
      ANSI_NIST_ITL_2000,
      null),
  NON_LIVESCAN_OF_PLAIN_FINGERPRINT(
      "2", "Nonlive-scan plain", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  NON_LIVESCAN_OF_ROLLED_FINGERPRINT(
      "3", "Nonlive-scan roll", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LATENT_IMAGE(
      "4", "Latent image or impression - Former \"Latent impression\"", ANSI_NIST_ITL_2000, null),
  LATENT_TRACING("5", "Latent tracing", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LATENT_PHOTO("6", "Latent photo", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LATENT_LIFT("7", "Latent lift", ANSI_NIST_ITL_2000, ANSI_NIST_ITL_2015),
  LIVESCAN_SWIPE_FP(
      "8",
      "Finger swiped on platen - Former \"Livescan Vertical Swipe fingerprint\"",
      ANSI_NIST_ITL_2007,
      null),
  LIVESCAN_PALM("10", "Livescan Palm", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  NON_LIVESCAN_PALM("11", "Non Livescan Palm", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_IMPRESSION("12", "Latent Palm Impression", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_TRACING("13", "Latent Palm Tracing", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_PHOTO("14", "Latent Palm Photo", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LATENT_PALM_LIFT("15", "Latent Palm Lift", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LIVESCAN_OPTICAL_CONTACT_PLAIN(
      "20", "Live-scan optical contact plain", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LIVESCAN_OPTICAL_CONTACT_ROLLED(
      "21", "Live-scan optical contact rolled", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LIVESCAN_NON_OPTICAL_CONTACT_PLAIN(
      "22", "Live-scan non optical contact plain", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LIVESCAN_NON_OPTICAL_CONTACT_ROLLED(
      "23", "Live-scan non optical contact rolled", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  PLAIN_CONTACTLESS_STATIONARY_SUBJECT(
      "24",
      "Plain contactless – stationary subject - Former \"Livescan Optical contacless plain fingerprint\"",
      ANSI_NIST_ITL_2007,
      null),
  ROLLED_CONTACTLESS_STATIONARY_SUBJECT(
      "25",
      "Rolled contactless – stationary subject - Former \"Livescan Optical contacless rolled fingerprint\"",
      ANSI_NIST_ITL_2007,
      null),
  LIVESCAN_NON_OPTICAL_CONTACTLESS_PLAIN(
      "26", "Live-scan non optical contactless plain", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  LIVESCAN_NON_OPTICAL_CONTACTLESS_ROLLED(
      "27", "Live-scan non optical contactless rolled", ANSI_NIST_ITL_2007, ANSI_NIST_ITL_2015),
  OTHER("28", "Other", ANSI_NIST_ITL_2007, null),
  UNKNOWN("29", "Unknown", ANSI_NIST_ITL_2007, null),
  LIVESCAN_PLANTAR(
      "30",
      "Livescan (type unknown or unspecified) plantar",
      ANSI_NIST_ITL_2011,
      ANSI_NIST_ITL_2015),
  NON_LIVESCAN_PLANTAR("31", "Non livescan plantar", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_IMPRESSION(
      "32", "Latent Plantar Impression", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_TRACING("33", "Latent Plantar Tracing", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_PHOTO("34", "Latent Plantar Photo", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_PLANTAR_LIFT("35", "Latent Plantar Lift", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_IMPRESSION(
      "36", "Latent Unknown friction ridge Impression", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_TRACING(
      "37", "Latent Unknown friction ridge Tracing", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_PHOTO(
      "38", "Latent Unknown friction ridge Photo", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  LATENT_UNKNOWN_FRICTION_LIFT(
      "39", "Latent Unknown friction ridge Lift", ANSI_NIST_ITL_2011, ANSI_NIST_ITL_2015),
  PLAIN_CONTACTLESS_MOVING_SUBJECT(
      "41", "Rolled contactless – moving subject", ANSI_NIST_ITL_2015, null),
  ROLLED_CONTACTLESS_MOVING_SUBJECT(
      "42", "Plain contactless – moving subject", ANSI_NIST_ITL_2015, null),
  ;

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
