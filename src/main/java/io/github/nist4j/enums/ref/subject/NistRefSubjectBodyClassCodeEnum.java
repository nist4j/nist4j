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
package io.github.nist4j.enums.ref.subject;

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2013;
import static io.github.nist4j.enums.RecordTypeEnum.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefSubjectBodyClassCodeEnum implements INistReferentielEnum {
  NATURAL_TISSUE("1", "Natural Tissue", asList(RT10, RT13, RT14), ANSI_NIST_ITL_2013, null),
  DECOMPOSED("2", "Decomposed", asList(RT10, RT13, RT14), ANSI_NIST_ITL_2013, null),
  SKELETAL("3", "Skeletal", singletonList(RT10), ANSI_NIST_ITL_2013, null),
  ;

  private final String code;
  private final String description;
  private final List<RecordTypeEnum> allowedRT;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;

  public static List<NistRefSubjectBodyClassCodeEnum> listForRT(RecordTypeEnum recordType) {
    return Arrays.stream(NistRefSubjectBodyClassCodeEnum.values())
        .filter(ref -> ref.getAllowedRT().contains(recordType))
        .collect(Collectors.toList());
  }
}
