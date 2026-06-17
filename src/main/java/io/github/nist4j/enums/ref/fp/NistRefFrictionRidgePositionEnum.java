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
import static io.github.nist4j.enums.ref.fp.NistRefFrictionRidgePositionEnum.FrictionRidgeType.*;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefFrictionRidgePositionEnum implements INistReferentielEnum {
  UNKNOWN_FINGER("0", FINGER, "Unknown finger", ANSI_NIST_ITL_2000, null),
  RIGHT_THUMB("1", FINGER, "Right thumb", ANSI_NIST_ITL_2000, null),
  RIGHT_INDEX("2", FINGER, "Right index finger", ANSI_NIST_ITL_2000, null),
  RIGHT_MIDDLE("3", FINGER, "Right middle finger", ANSI_NIST_ITL_2000, null),
  RIGHT_RING("4", FINGER, "Right ring finger", ANSI_NIST_ITL_2000, null),
  RIGHT_LITTLE("5", FINGER, "Right little finger", ANSI_NIST_ITL_2000, null),
  LEFT_THUMB("6", FINGER, "Left thumb", ANSI_NIST_ITL_2000, null),
  LEFT_INDEX("7", FINGER, "Left index finger", ANSI_NIST_ITL_2000, null),
  LEFT_MIDDLE("8", FINGER, "Left middle finger", ANSI_NIST_ITL_2000, null),
  LEFT_RING("9", FINGER, "Left ring finger", ANSI_NIST_ITL_2000, null),
  LEFT_LITTLE("10", FINGER, "Left little finger", ANSI_NIST_ITL_2000, null),
  PLAIN_RIGHT_THUMB("11", FINGER, "Plain right thumb", ANSI_NIST_ITL_2000, null),
  PLAIN_LEFT_THUMB("12", FINGER, "Plain left thumb", ANSI_NIST_ITL_2000, null),
  PLAIN_RIGHT_FOUR_FINGERS(
      "13", FINGERS_COMBINATION, "Plain right four fingers", ANSI_NIST_ITL_2000, null),
  PLAIN_LEFT_FOUR_FINGERS(
      "14", FINGERS_COMBINATION, "Plain left four fingers", ANSI_NIST_ITL_2000, null),
  LEFT_AND_RIGHT_THUMBS("15", FINGERS_COMBINATION, "Left & right thumbs", ANSI_NIST_ITL_2007, null),
  RIGHT_EXTRA_DIGIT("16", FINGER, "Right extra digit", ANSI_NIST_ITL_2011, null),
  LEFT_EXTRA_DIGIT("17", FINGER, "Left extra digit", ANSI_NIST_ITL_2011, null),
  UNKNOWN("18", null, "Unknown friction ridge", ANSI_NIST_ITL_2011, null),
  EJI_OR_TIPS("19", FINGERS_COMBINATION, "EJI or tip", ANSI_NIST_ITL_2007, null),
  UNKNOWN_PALM("20", PALM, "Unknown palm", ANSI_NIST_ITL_2000, null),
  RIGHT_FULL_PALM("21", PALM, "Right Full Palm", ANSI_NIST_ITL_2000, null),
  RIGHT_WRITER_PALM("22", PALM, "Right Writer's Palm", ANSI_NIST_ITL_2000, null),
  LEFT_FULL_PALM("23", PALM, "Left Full Palm", ANSI_NIST_ITL_2000, null),
  LEFT_WRITER_PALM("24", PALM, "Left Writer's palm", ANSI_NIST_ITL_2000, null),
  RIGHT_LOWER_PALM("25", PALM, "Right lower palm", ANSI_NIST_ITL_2000, null),
  RIGHT_UPPER_PALM("26", PALM, "Right upper palm", ANSI_NIST_ITL_2000, null),
  LEFT_LOWER_PALM("27", PALM, "Left lower palm", ANSI_NIST_ITL_2000, null),
  LEFT_UPPER_PALM("28", PALM, "Left upper palm", ANSI_NIST_ITL_2000, null),
  RIGHT_OTHER_PALM("29", PALM, "Right other palm", ANSI_NIST_ITL_2000, null),
  LEFT_OTHER_PALM("30", PALM, "Left other palm", ANSI_NIST_ITL_2000, null),
  RIGHT_INTERDIGITAL_PALM("31", PALM, "Right Interdigital palm", ANSI_NIST_ITL_2007, null),
  RIGHT_THENAR_PALM("32", PALM, "Right Thenar palm", ANSI_NIST_ITL_2007, null),
  RIGHT_HYPOTHENAR_PALM("33", PALM, "Right Hypothenar palm", ANSI_NIST_ITL_2007, null),
  LEFT_INTERDIGITAL_PALM("34", PALM, "Left Interdigital palm", ANSI_NIST_ITL_2007, null),
  LEFT_THENAR_PALM("35", PALM, "Left Thenar palm", ANSI_NIST_ITL_2007, null),
  LEFT_HYPOTHENAR_PALM("36", PALM, "Left Hypothenar palm", ANSI_NIST_ITL_2007, null),
  RIGHT_GRASP_PALM("37", PALM, "Right grasp palm", ANSI_NIST_ITL_2011, null),
  LEFT_GRASP_PALM("38", PALM, "Left grasp palm", ANSI_NIST_ITL_2011, null),
  RIGHT_CARPAL_DELTA_PALM("81", PALM, "Right carpal delta area", ANSI_NIST_ITL_2011, null),
  LEFT_CARPAL_DELTA_PALM("82", PALM, "Left carpal delta area", ANSI_NIST_ITL_2011, null),
  RIGHT_ROLLED_FULL_PALM(
      "83",
      PALM,
      "Right full palm, including writer's palm - hand is rolled",
      ANSI_NIST_ITL_2011,
      null),
  LEFT_ROLLED_FULL_PALM(
      "84",
      PALM,
      "Left full palm, including writer's palm - hand is rolled",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_WRIST_BRACELET("85", PALM, "Right wrist bracelet", ANSI_NIST_ITL_2011, null),
  LEFT_WRIST_BRACELET("86", PALM, "Left wrist bracelet", ANSI_NIST_ITL_2011, null),
  UNKNOWN_SOLE("60", PLANTAR, "Unknown sole", ANSI_NIST_ITL_2011, null),
  RIGHT_FOOT("61", PLANTAR, "Sole – right foot", ANSI_NIST_ITL_2011, null),
  LEFT_FOOT("62", PLANTAR, "Sole – left foot", ANSI_NIST_ITL_2011, null),
  UNKNOWN_TOE("63", PLANTAR, "Unknown toe", ANSI_NIST_ITL_2011, null),
  RIGHT_BIG_TOE("64", PLANTAR, "Right big toe", ANSI_NIST_ITL_2011, null),
  RIGHT_SECOND_TOE("65", PLANTAR, "Right second toe", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_TOE("66", PLANTAR, "Right middle toe", ANSI_NIST_ITL_2011, null),
  RIGHT_FOURTH_TOE("67", PLANTAR, "Right fourth toe", ANSI_NIST_ITL_2011, null),
  RIGHT_LITTLE_TOE("68", PLANTAR, "Right little toe", ANSI_NIST_ITL_2011, null),
  LEFT_BIG_TOE("69", PLANTAR, "Left big toe", ANSI_NIST_ITL_2011, null),
  LEFT_SECOND_TOE("70", PLANTAR, "Left second toe", ANSI_NIST_ITL_2011, null),
  LEFT_MIDDLE_TOE("71", PLANTAR, "Left middle toe", ANSI_NIST_ITL_2011, null),
  LEFT_FOURTH_TOE("72", PLANTAR, "Left fourth toe", ANSI_NIST_ITL_2011, null),
  LEFT_LITTLE_TOE("73", PLANTAR, "Left little toe", ANSI_NIST_ITL_2011, null),
  FRONT_RIGHT_FOOT("74", PLANTAR, "Front / ball of right foot", ANSI_NIST_ITL_2011, null),
  BACK_RIGHT_FOOT("75", PLANTAR, "Back / heel of right foot", ANSI_NIST_ITL_2011, null),
  FRONT_LEFT_FOOT("76", PLANTAR, "Front / ball of left foot", ANSI_NIST_ITL_2011, null),
  BACK_LEFT_FOOT("77", PLANTAR, "Back / ball of left foot", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_FOOT(
      "78",
      PLANTAR,
      "Right middle of foot ( arch and/or outside (fibular hypothenar) areas of the feet",
      ANSI_NIST_ITL_2011,
      null),
  LEFT_MIDDLE_FOOT(
      "79",
      PLANTAR,
      "Left middle of foot ( arch and/or outside (fibular hypothenar) areas of the feet",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_INDEX_MIDDLE("40", FINGERS_COMBINATION, "Right index/middle", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_RING("41", FINGERS_COMBINATION, "Right middle/ring", ANSI_NIST_ITL_2011, null),
  RIGHT_RING_LITTLE("42", FINGERS_COMBINATION, "Right ring/little", ANSI_NIST_ITL_2011, null),
  LEFT_INDEX_MIDDLE("43", FINGERS_COMBINATION, "Left index/middle", ANSI_NIST_ITL_2011, null),
  LEFT_MIDDLE_RING("44", FINGERS_COMBINATION, "Left middle/ring", ANSI_NIST_ITL_2011, null),
  LEFT_RING_LITTLE("45", FINGERS_COMBINATION, "Left ring/little", ANSI_NIST_ITL_2011, null),
  RIGHT_LEFT_INDEX(
      "46", FINGERS_COMBINATION, "Right index and left index", ANSI_NIST_ITL_2011, null),
  RIGHT_INDEX_MIDDLE_RING(
      "47", FINGERS_COMBINATION, "Right index/middle/ring", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_RING_LITTLE(
      "48", FINGERS_COMBINATION, "Right middle/ring/little", ANSI_NIST_ITL_2011, null),
  LEFT_INDEX_MIDDLE_RING(
      "49", FINGERS_COMBINATION, "Left index/middle/ring", ANSI_NIST_ITL_2011, null),
  LEFT_MIDDLE_RING_LITTLE(
      "50", FINGERS_COMBINATION, "Left middle/ring/little", ANSI_NIST_ITL_2011, null),
  RIGHT_4_FINGERTIPS(
      "51",
      FINGERS_COMBINATION,
      "Fingertips (4 fingers simultaneously – no thumb – right hand - plain) ",
      ANSI_NIST_ITL_2013,
      null),
  LEFT_4_FINGERTIPS(
      "52",
      FINGERS_COMBINATION,
      "Fingertips (4 fingers simultaneously – no thumb – left hand - plain) ",
      ANSI_NIST_ITL_2013,
      null),
  RIGHT_5_FINGERTIPS(
      "53",
      FINGERS_COMBINATION,
      "Fingertips (4 fingers and thumb simultaneously – right hand - plain)",
      ANSI_NIST_ITL_2013,
      null),
  LEFT_5_FINGERTIPS(
      "54",
      FINGERS_COMBINATION,
      "Fingertips (4 fingers and thumb simultaneously – left hand - plain)",
      ANSI_NIST_ITL_2013,
      null),
  RIGHT_LEFT_INDEX_MIDDLE(
      "55",
      FINGERS_COMBINATION,
      "Right index/middle / Left index/middle (4 fingers simultaneously)",
      ANSI_NIST_ITL_2025,
      null),
  ;

  private final String code;
  private final FrictionRidgeType type;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;

  /** List use for validators Some attributes are allowed to specify only on unitary fingers */
  public static final List<NistRefFrictionRidgePositionEnum> ALL =
      Collections.unmodifiableList(Arrays.asList(NistRefFrictionRidgePositionEnum.values()));

  public static final List<NistRefFrictionRidgePositionEnum> TEN_FINGERS =
      Collections.unmodifiableList(
          Arrays.asList(
              UNKNOWN_FINGER,
              RIGHT_THUMB,
              RIGHT_INDEX,
              RIGHT_MIDDLE,
              RIGHT_RING,
              RIGHT_LITTLE,
              LEFT_THUMB,
              LEFT_INDEX,
              LEFT_MIDDLE,
              LEFT_RING,
              LEFT_LITTLE,
              RIGHT_EXTRA_DIGIT,
              LEFT_EXTRA_DIGIT));

  public static final List<NistRefFrictionRidgePositionEnum> FINGERS_AND_PALMS =
      Collections.unmodifiableList(
          Arrays.asList(
              UNKNOWN_FINGER,
              RIGHT_THUMB,
              RIGHT_INDEX,
              RIGHT_MIDDLE,
              RIGHT_RING,
              RIGHT_LITTLE,
              LEFT_THUMB,
              LEFT_INDEX,
              LEFT_MIDDLE,
              LEFT_RING,
              LEFT_LITTLE,
              RIGHT_EXTRA_DIGIT,
              LEFT_EXTRA_DIGIT,
              UNKNOWN_PALM,
              RIGHT_FULL_PALM,
              RIGHT_WRITER_PALM,
              LEFT_FULL_PALM,
              LEFT_WRITER_PALM,
              RIGHT_LOWER_PALM,
              RIGHT_UPPER_PALM,
              LEFT_LOWER_PALM,
              LEFT_UPPER_PALM,
              RIGHT_OTHER_PALM,
              LEFT_OTHER_PALM,
              RIGHT_INTERDIGITAL_PALM,
              RIGHT_THENAR_PALM,
              RIGHT_HYPOTHENAR_PALM,
              LEFT_INTERDIGITAL_PALM,
              LEFT_THENAR_PALM,
              LEFT_HYPOTHENAR_PALM));

  public static final List<NistRefFrictionRidgePositionEnum> FINGERS_PALMS_AND_COMBINATION =
      Collections.unmodifiableList(Arrays.asList(NistRefFrictionRidgePositionEnum.values()));

  public enum FrictionRidgeType {
    FINGER, // For one finger
    FINGERS_COMBINATION, // For combination of at least two fingers
    PALM,
    PLANTAR,
  }
}
