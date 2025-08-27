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

import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.ref.NistReferentielHelperImpl.findCodesAllowedByStandard;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.image.*;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractStdRT10Validator extends AbstractNistRecordValidator {

  protected static final List<String> SLC_ALLOWED_VALUES =
      Collections.unmodifiableList(Arrays.asList("0", "1", "2"));

  protected static final List<String> CSP_ALLOWED_VALUES =
      Collections.unmodifiableList(Arrays.asList("UNK", "RGB", "GRAY", "SRGB", "YCC", "SYCC"));

  protected AbstractStdRT10Validator(NistOptions nistOptions) {
    super(nistOptions, RT14);
  }

  protected static List<String> getAllowedValuesForIMT(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefFacialSMTImageTypeEnum.values(), nistStandard);
  }

  protected static List<String> getAllowedValuesForCGA(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefCompressionAlgorithmEnum.values(), nistStandard)
        .stream()
        .filter(cga -> !cga.startsWith("WSQ"))
        .collect(Collectors.toList());
  }

  protected static List<String> getAllowedValuesForPOS(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefSubjectPoseEnum.values(), nistStandard);
  }

  protected static List<String> getAllowedValuesForPAS(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefAcquisitionSourceTypeEnum.values(), nistStandard);
  }

  protected static List<String> getAllowedValuesForSAP(NistStandardEnum nistStandard) {
    return findCodesAllowedByStandard(NistRefSubjectAcquisitionProfilesEnum.values(), nistStandard);
  }
}
