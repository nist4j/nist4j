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
import static io.github.nist4j.enums.RecordTypeEnum.RT13;
import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum NistRefJointImageSegmentsTipAndFingerViewCodeEnum implements INistReferentielEnum {
  EJI(
      "EJI",
      "Entire joint image",
      asList(RT13, RT14),
      singletonList("FIC"),
      ANSI_NIST_ITL_2000,
      null),
  TIP(
      "TIP",
      "Latent fingertip",
      asList(RT13, RT14),
      asList("FIC", "FVC"),
      ANSI_NIST_ITL_2000,
      null),
  TPP(
      "TPP",
      "Plain tip image",
      singletonList(RT14),
      asList("FIC", "FVC"),
      ANSI_NIST_ITL_2000,
      null),
  FV1(
      "FV1",
      "Full finger rolled image",
      asList(RT13, RT14),
      asList("FIC", "FVC"),
      ANSI_NIST_ITL_2000,
      null),
  FV2(
      "FV2",
      "Full finger plain image – left side",
      asList(RT13, RT14),
      asList("FIC", "FVC"),
      ANSI_NIST_ITL_2000,
      null),
  FV3(
      "FV3",
      "Full finger plain image – ce",
      asList(RT13, RT14),
      asList("FIC", "FVC"),
      ANSI_NIST_ITL_2000,
      null),
  FV4(
      "FV4",
      "Full finger plain image – right side",
      asList(RT13, RT14),
      asList("FIC", "FVC"),
      ANSI_NIST_ITL_2000,
      null),
  PRX(
      "PRX",
      "Proximal segment",
      asList(RT13, RT14),
      asList("LOS", "FIC"),
      ANSI_NIST_ITL_2000,
      null),
  DST("DST", "Distal segment", asList(RT13, RT14), asList("LOS", "FIC"), ANSI_NIST_ITL_2000, null),
  MED("MED", "Medial segment", asList(RT13, RT14), asList("LOS", "FIC"), ANSI_NIST_ITL_2000, null),
  NA1(
      "NA",
      "Only a proximal, distal or medial segment is available",
      asList(RT13, RT14),
      singletonList("FVC"),
      ANSI_NIST_ITL_2000,
      null),
  NA2(
      "NA",
      "Image portion refers to a full finger view, tip or to the\n"
          + "entire joint image locations",
      asList(RT13, RT14),
      singletonList("LOS"),
      ANSI_NIST_ITL_2000,
      null),
  ;

  private final String code;
  private final String description;
  private final List<RecordTypeEnum> allowedRT;
  private final List<String> allowedSubfiels;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;

  public static List<NistRefJointImageSegmentsTipAndFingerViewCodeEnum> listForSubfield(
      RecordTypeEnum recordType, String subfieldName) {
    if (isNull(subfieldName)) {
      return Collections.emptyList();
    }
    return Arrays.stream(NistRefJointImageSegmentsTipAndFingerViewCodeEnum.values())
        .filter(ref -> ref.getAllowedSubfiels().contains(subfieldName))
        .filter(ref -> ref.getAllowedRT().contains(recordType))
        .collect(Collectors.toList());
  }
}
