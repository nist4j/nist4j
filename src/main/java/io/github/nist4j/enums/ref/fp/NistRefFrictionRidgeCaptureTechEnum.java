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
import static io.github.nist4j.enums.RecordTypeEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefFrictionRidgeCaptureTechEnum implements INistReferentielEnum {
  UNKNOWN("0", "Unknown", asList(RT13, RT14), ANSI_NIST_ITL_2015, null),
  OTHER("1", "Other", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  SCANNED_INK_ON_PAPER("2", "Scanned ink on paper", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  OPTICAL_BRIGHT_FIELD(
      "3",
      "Optical – Total Internal Reflection (TIR) – bright field",
      singletonList(RT14),
      ANSI_NIST_ITL_2015,
      null),
  OPTICAL_DARK_FIELD(
      "4", "Optical – TIR – dark field", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  OPTICAL_DIRECT_IMG_NATIVE(
      "5", "Optical direct imaging - native", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  OPTICAL_DIRECT_IMG_LOW_FREQ(
      "6",
      "Optical direct imaging – low frequency unwrapped",
      singletonList(RT14),
      ANSI_NIST_ITL_2015,
      null),
  IMG_3D(
      "7",
      "3-dimensional imaging – high frequency unwrapped",
      singletonList(RT14),
      ANSI_NIST_ITL_2015,
      null),
  CAPACITIVE("9", "Capacitive", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  CAPACITIVE_RF(
      "10", "Capacitive – radio frequency (RF)", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  EL_OPTICAL_IMG(
      "11",
      "Electro-luminescent (EL) optical direct imaging",
      singletonList(RT14),
      ANSI_NIST_ITL_2015,
      null),
  REFLECTED_ULTASONIC_IMG(
      "12", "Reflected ultrasonic image", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  ULTRASONIC_IMPEDIOGRAPHY(
      "13", "Ultrasonic impediography", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  THERMAL_IMAGING("14", "Thermal imaging", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  DIRECT_PRESS("15", "Direct pressure sensitive", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  INDIRECT_PRESS("16", "Indirect pressure", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  LIVE_TAPE("17", "Live tape (one time use)", singletonList(RT14), ANSI_NIST_ITL_2015, null),
  LATENT_IMPRESSION(
      "18", "Latent impression", singletonList(RT13), ANSI_NIST_ITL_2015, ANSI_NIST_ITL_2025),
  LATENT_PHOTO("19", "Latent photo", singletonList(RT13), ANSI_NIST_ITL_2015, ANSI_NIST_ITL_2025),
  LATENT_MOLDED(
      "20",
      "Latent molded / cast impression",
      singletonList(RT13),
      ANSI_NIST_ITL_2015,
      ANSI_NIST_ITL_2025),
  LATENT_TRACING(
      "21", "Latent tracing", singletonList(RT13), ANSI_NIST_ITL_2015, ANSI_NIST_ITL_2025),
  LATENT_LIFT("22", "Latent lift", singletonList(RT13), ANSI_NIST_ITL_2015, ANSI_NIST_ITL_2025),
  ;

  private final String code;
  private final String technology;
  private final List<RecordTypeEnum> allowedRT;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
