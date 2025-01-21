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
package io.github.nist4j.enums.ref;

import io.github.nist4j.enums.NistStandardEnum;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NistReferentielHelperImpl implements INistReferentielHelper {

  public static <R extends INistReferentielEnum> Optional<R> findByCode(R[] values, String code) {
    return Arrays.stream(values).filter(itemEnum -> itemEnum.getCode().equals(code)).findFirst();
  }

  public static <R extends INistReferentielEnum> List<R> findValuesAllowedByStandard(
      R[] values, NistStandardEnum nistStandardEnum) {
    return Arrays.stream(values)
        .filter(value -> value.isAllowedForStandard(nistStandardEnum))
        .collect(Collectors.toList());
  }

  public static <R extends INistReferentielEnum> List<String> findCodesAllowedByStandard(
      List<R> values, NistStandardEnum nistStandardEnum) {
    return values.stream()
        .filter(value -> value.isAllowedForStandard(nistStandardEnum))
        .map(INistReferentielEnum::getCode)
        .collect(Collectors.toList());
  }

  public static <R extends INistReferentielEnum> List<String> findCodesAllowedByStandard(
      R[] values, NistStandardEnum nistStandardEnum) {
    return findCodesAllowedByStandard(
        Arrays.stream(values).collect(Collectors.toList()), (nistStandardEnum));
  }
}
