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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord10;

import static io.github.nist4j.enums.RecordTypeEnum.RT10;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;
import static io.github.nist4j.use_cases.helpers.conditions.StringCondition.areEquals;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import io.github.nist4j.enums.ref.image.*;
import io.github.nist4j.enums.ref.smt.NistRefNCICCodeEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractStdRT10Validator extends AbstractNistRecordValidator {

  protected static final List<String> SLC_ALLOWED_VALUES =
      Collections.unmodifiableList(asList("0", "1", "2"));

  protected static final List<String> CSP_ALLOWED_VALUES =
      Collections.unmodifiableList(asList("UNK", "RGB", "GRAY", "SRGB", "YCC", "SYCC"));

  protected AbstractStdRT10Validator(NistOptions nistOptions) {
    super(nistOptions, RT10);
  }

  protected List<String> getAllowedValuesForIMT(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefFacialIMTImageTypeEnum.values(), nistStandard);
  }

  protected List<String> getConditionalOnIMTforSMT(NistStandardEnum nistStandard) {
    List<NistRefFacialIMTImageTypeEnum> valuesIMT =
        Arrays.stream(NistRefFacialIMTImageTypeEnum.values())
            .filter(NistRefFacialIMTImageTypeEnum::isSMTOptional)
            .collect(Collectors.toList());
    return findCodesAllowedByStandard(valuesIMT, nistStandard);
  }

  protected List<String> getAllowedValuesForSMT(NistStandardEnum nistStandard, String fieldIMTVal) {

    List<INistReferentielEnum> valuesSMT =
        Stream.concat(
                Arrays.stream(NistRefFacialSMTImageSubcodeEnum.values())
                    .filter(r -> areEquals(r.getImageType().getCode(), fieldIMTVal)),
                Arrays.stream(NistRefNCICCodeEnum.values()))
            .collect(Collectors.toList());
    return findCodesAllowedByStandard(valuesSMT, nistStandard);
  }

  protected List<String> getAllowedValuesForSMD_SMI(
      NistStandardEnum nistStandard, String fieldIMTVal) {

    List<INistReferentielEnum> valuesSMD_SMI =
        Arrays.stream(NistRefFacialSMTImageSubcodeEnum.values())
            .filter(r -> areEquals(r.getImageType().getCode(), fieldIMTVal))
            .collect(Collectors.toList());
    return findCodesAllowedByStandard(valuesSMD_SMI, nistStandard);
  }

  protected List<String> getAllowedValuesForSMD_TAC(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefFacialSMTonTACTattooClassEnum.values(), nistStandard);
  }

  protected List<String> getAllowedValuesForSMD_TSC(
      NistStandardEnum nistStandard, String fieldSMD_TACVal) {

    List<INistReferentielEnum> valuesSMD_TSC =
        Arrays.stream(NistRefFacialSMT_TSCTattooSubclassEnum.values())
            .filter(r -> areEquals(r.getTattooClass().getCode(), fieldSMD_TACVal))
            .collect(Collectors.toList());
    return findCodesAllowedByStandard(valuesSMD_TSC, nistStandard);
  }

  protected List<String> getAllowedValuesForPOS(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefSubjectPoseEnum.values(), nistStandard);
  }

  protected List<String> getAllowedValuesForPAS(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefAcquisitionSourceTypeEnum.values(), nistStandard);
  }

  protected List<String> getAllowedValuesForSAP(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefSubjectAcquisitionProfilesEnum.values(), nistStandard);
  }
}
