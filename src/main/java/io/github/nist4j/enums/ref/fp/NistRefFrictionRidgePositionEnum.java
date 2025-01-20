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
  UNKNOWN_FINGER("0", FrictionRidgeType.FINGER, "Unknown finger", ANSI_NIST_ITL_2000, null),
  RIGHT_THUMB("1", FrictionRidgeType.FINGER, "Right thumb", ANSI_NIST_ITL_2000, null),
  RIGHT_INDEX("2", FrictionRidgeType.FINGER, "Right index finger", ANSI_NIST_ITL_2000, null),
  RIGHT_MIDDLE("3", FrictionRidgeType.FINGER, "Right middle finger", ANSI_NIST_ITL_2000, null),
  RIGHT_RING("4", FrictionRidgeType.FINGER, "Right ring finger", ANSI_NIST_ITL_2000, null),
  RIGHT_LITTLE("5", FrictionRidgeType.FINGER, "Right little finger", ANSI_NIST_ITL_2000, null),
  LEFT_THUMB("6", FrictionRidgeType.FINGER, "Left thumb", ANSI_NIST_ITL_2000, null),
  LEFT_INDEX("7", FrictionRidgeType.FINGER, "Left index finger", ANSI_NIST_ITL_2000, null),
  LEFT_MIDDLE("8", FrictionRidgeType.FINGER, "Left middle finger", ANSI_NIST_ITL_2000, null),
  LEFT_RING("9", FrictionRidgeType.FINGER, "Left ring finger", ANSI_NIST_ITL_2000, null),
  LEFT_LITTLE("10", FrictionRidgeType.FINGER, "Left little finger", ANSI_NIST_ITL_2000, null),
  PLAIN_RIGHT_THUMB("11", FrictionRidgeType.FINGER, "Plain right thumb", ANSI_NIST_ITL_2000, null),
  PLAIN_LEFT_THUMB("12", FrictionRidgeType.FINGER, "Plain left thumb", ANSI_NIST_ITL_2000, null),
  PLAIN_RIGHT_FOUR_FINGERS(
      "13",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Plain right four fingers",
      ANSI_NIST_ITL_2000,
      null),
  PLAIN_LEFT_FOUR_FINGERS(
      "14",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Plain left four fingers",
      ANSI_NIST_ITL_2000,
      null),
  LEFT_AND_RIGHT_THUMBS(
      "15", FrictionRidgeType.FINGERS_COMBINATION, "Left & right thumbs", ANSI_NIST_ITL_2007, null),
  RIGHT_EXTRA_DIGIT("16", FrictionRidgeType.FINGER, "Right extra digit", ANSI_NIST_ITL_2011, null),
  LEFT_EXTRA_DIGIT("17", FrictionRidgeType.FINGER, "Left extra digit", ANSI_NIST_ITL_2011, null),
  UNKNOWN("18", null, "Unknown friction ridge", ANSI_NIST_ITL_2011, null),
  EJI_OR_TIPS("19", FrictionRidgeType.FINGERS_COMBINATION, "EJI or tip", ANSI_NIST_ITL_2007, null),
  UNKNOWN_PALM("20", FrictionRidgeType.PALM, "Unknown palm", ANSI_NIST_ITL_2000, null),
  RIGHT_FULL_PALM("21", FrictionRidgeType.PALM, "Right Full Palm", ANSI_NIST_ITL_2000, null),
  RIGHT_WRITER_PALM("22", FrictionRidgeType.PALM, "Right Writer's Palm", ANSI_NIST_ITL_2000, null),
  LEFT_FULL_PALM("23", FrictionRidgeType.PALM, "Left Full Palm", ANSI_NIST_ITL_2000, null),
  LEFT_WRITER_PALM("24", FrictionRidgeType.PALM, "Left Writer's palm", ANSI_NIST_ITL_2000, null),
  RIGHT_LOWER_PALM("25", FrictionRidgeType.PALM, "Right lower palm", ANSI_NIST_ITL_2000, null),
  RIGHT_UPPER_PALM("26", FrictionRidgeType.PALM, "Right upper palm", ANSI_NIST_ITL_2000, null),
  LEFT_LOWER_PALM("27", FrictionRidgeType.PALM, "Left lower palm", ANSI_NIST_ITL_2000, null),
  LEFT_UPPER_PALM("28", FrictionRidgeType.PALM, "Left upper palm", ANSI_NIST_ITL_2000, null),
  RIGHT_OTHER_PALM("29", FrictionRidgeType.PALM, "Right other palm", ANSI_NIST_ITL_2000, null),
  LEFT_OTHER_PALM("30", FrictionRidgeType.PALM, "Left other palm", ANSI_NIST_ITL_2000, null),
  RIGHT_INTERDIGITAL_PALM(
      "31", FrictionRidgeType.PALM, "Right Interdigital palm", ANSI_NIST_ITL_2007, null),
  RIGHT_THENAR_PALM("32", FrictionRidgeType.PALM, "Right Thenar palm", ANSI_NIST_ITL_2007, null),
  RIGHT_HYPOTHENAR_PALM(
      "33", FrictionRidgeType.PALM, "Right Hypothenar palm", ANSI_NIST_ITL_2007, null),
  LEFT_INTERDIGITAL_PALM(
      "34", FrictionRidgeType.PALM, "Left Interdigital palm", ANSI_NIST_ITL_2007, null),
  LEFT_THENAR_PALM("35", FrictionRidgeType.PALM, "Left Thenar palm", ANSI_NIST_ITL_2007, null),
  LEFT_HYPOTHENAR_PALM(
      "36", FrictionRidgeType.PALM, "Left Hypothenar palm", ANSI_NIST_ITL_2007, null),
  RIGHT_GRASP_PALM("37", FrictionRidgeType.PALM, "Right grasp palm", ANSI_NIST_ITL_2011, null),
  LEFT_GRASP_PALM("38", FrictionRidgeType.PALM, "Left grasp palm", ANSI_NIST_ITL_2011, null),
  RIGHT_CARPAL_DELTA_PALM(
      "81", FrictionRidgeType.PALM, "Right carpal delta area", ANSI_NIST_ITL_2011, null),
  LEFT_CARPAL_DELTA_PALM(
      "82", FrictionRidgeType.PALM, "Left carpal delta area", ANSI_NIST_ITL_2011, null),
  RIGHT_ROLLED_FULL_PALM(
      "83",
      FrictionRidgeType.PALM,
      "Right full palm, including writer's palm - hand is rolled",
      ANSI_NIST_ITL_2011,
      null),
  LEFT_ROLLED_FULL_PALM(
      "84",
      FrictionRidgeType.PALM,
      "Left full palm, including writer's palm - hand is rolled",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_WRIST_BRACELET(
      "85", FrictionRidgeType.PALM, "Right wrist bracelet", ANSI_NIST_ITL_2011, null),
  LEFT_WRIST_BRACELET(
      "86", FrictionRidgeType.PALM, "Left wrist bracelet", ANSI_NIST_ITL_2011, null),
  UNKNOWN_SOLE("60", FrictionRidgeType.PLANTAR, "Unknown sole", ANSI_NIST_ITL_2011, null),
  RIGHT_FOOT("61", FrictionRidgeType.PLANTAR, "Sole – right foot", ANSI_NIST_ITL_2011, null),
  LEFT_FOOT("62", FrictionRidgeType.PLANTAR, "Sole – left foot", ANSI_NIST_ITL_2011, null),
  UNKNOWN_TOE("63", FrictionRidgeType.PLANTAR, "Unknown toe", ANSI_NIST_ITL_2011, null),
  RIGHT_BIG_TOE("64", FrictionRidgeType.PLANTAR, "Right big toe", ANSI_NIST_ITL_2011, null),
  RIGHT_SECOND_TOE("65", FrictionRidgeType.PLANTAR, "Right second toe", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_TOE("66", FrictionRidgeType.PLANTAR, "Right middle toe", ANSI_NIST_ITL_2011, null),
  RIGHT_FOURTH_TOE("67", FrictionRidgeType.PLANTAR, "Right fourth toe", ANSI_NIST_ITL_2011, null),
  RIGHT_LITTLE_TOE("68", FrictionRidgeType.PLANTAR, "Right little toe", ANSI_NIST_ITL_2011, null),
  LEFT_BIG_TOE("69", FrictionRidgeType.PLANTAR, "Left big toe", ANSI_NIST_ITL_2011, null),
  LEFT_SECOND_TOE("70", FrictionRidgeType.PLANTAR, "Left second toe", ANSI_NIST_ITL_2011, null),
  LEFT_MIDDLE_TOE("71", FrictionRidgeType.PLANTAR, "Left middle toe", ANSI_NIST_ITL_2011, null),
  LEFT_FOURTH_TOE("72", FrictionRidgeType.PLANTAR, "Left fourth toe", ANSI_NIST_ITL_2011, null),
  LEFT_LITTLE_TOE("73", FrictionRidgeType.PLANTAR, "Left little toe", ANSI_NIST_ITL_2011, null),
  FRONT_RIGHT_FOOT(
      "74", FrictionRidgeType.PLANTAR, "Front / ball of right foot", ANSI_NIST_ITL_2011, null),
  BACK_RIGHT_FOOT(
      "75", FrictionRidgeType.PLANTAR, "Back / heel of right foot", ANSI_NIST_ITL_2011, null),
  FRONT_LEFT_FOOT(
      "76", FrictionRidgeType.PLANTAR, "Front / ball of left foot", ANSI_NIST_ITL_2011, null),
  BACK_LEFT_FOOT(
      "77", FrictionRidgeType.PLANTAR, "Back / ball of left foot", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_FOOT(
      "78",
      FrictionRidgeType.PLANTAR,
      "Right middle of foot ( arch and/or outside (fibular hypothenar) areas of the feet",
      ANSI_NIST_ITL_2011,
      null),
  LEFT_MIDDLE_FOOT(
      "79",
      FrictionRidgeType.PLANTAR,
      "Left middle of foot ( arch and/or outside (fibular hypothenar) areas of the feet",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_INDEX_MIDDLE(
      "40", FrictionRidgeType.FINGERS_COMBINATION, "Right index/middle", ANSI_NIST_ITL_2011, null),
  RIGHT_MIDDLE_RING(
      "41", FrictionRidgeType.FINGERS_COMBINATION, "Right middle/ring", ANSI_NIST_ITL_2011, null),
  RIGHT_RING_LITTLE(
      "42", FrictionRidgeType.FINGERS_COMBINATION, "Right ring/little", ANSI_NIST_ITL_2011, null),
  LEFT_INDEX_MIDDLE(
      "43", FrictionRidgeType.FINGERS_COMBINATION, "Left index/middle", ANSI_NIST_ITL_2011, null),
  LEFT_MIDDLE_RING(
      "44", FrictionRidgeType.FINGERS_COMBINATION, "Left middle/ring", ANSI_NIST_ITL_2011, null),
  LEFT_RING_LITTLE(
      "45", FrictionRidgeType.FINGERS_COMBINATION, "Left ring/little", ANSI_NIST_ITL_2011, null),
  RIGHT_LEFT_INDEX(
      "46",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Right index and left index",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_INDEX_MIDDLE_RING(
      "47",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Right index/middle/ring",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_MIDDLE_RING_LITTLE(
      "48",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Right middle/ring/little",
      ANSI_NIST_ITL_2011,
      null),
  LEFT_INDEX_MIDDLE_RING(
      "49",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Left index/middle/ring",
      ANSI_NIST_ITL_2011,
      null),
  LEFT_MIDDLE_RING_LITTLE(
      "50",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Left middle/ring/little",
      ANSI_NIST_ITL_2011,
      null),
  RIGHT_4_FINGERTIPS(
      "51",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Fingertips (4 fingers simultaneously – no thumb – right hand - plain) ",
      ANSI_NIST_ITL_2013,
      null),
  LEFT_4_FINGERTIPS(
      "52",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Fingertips (4 fingers simultaneously – no thumb – left hand - plain) ",
      ANSI_NIST_ITL_2013,
      null),

  RIGHT_5_FINGERTIPS(
      "53",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Fingertips (4 fingers and thumb simultaneously – right hand - plain)",
      ANSI_NIST_ITL_2013,
      null),
  LEFT_5_FINGERTIPS(
      "54",
      FrictionRidgeType.FINGERS_COMBINATION,
      "Fingertips (4 fingers and thumb simultaneously – left hand - plain) ",
      ANSI_NIST_ITL_2013,
      null),
  ;

  private final String code;
  private final FrictionRidgeType type;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;

  /**
   * List use for validators Some attributes are allowed to specify only on unitary fingers
   */
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
