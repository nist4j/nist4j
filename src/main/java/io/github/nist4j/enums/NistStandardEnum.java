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
package io.github.nist4j.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

@Getter
public enum NistStandardEnum {
  // Be aware to keep the order of standards

  ANSI_NIST_ITL_2000("ANSI/NIST-ITL 1-2000", "0300"),

  ANSI_NIST_ITL_2007("ANSI/NIST-ITL 1-2007", "0400"),

  ANSI_NIST_ITL_2011("ANSI/NIST-ITL 1-2011", "0500"),

  ANSI_NIST_ITL_2013("ANSI/NIST-ITL 1-2011 Update 2013", "0501"),

  ANSI_NIST_ITL_2015("ANSI/NIST-ITL 1-2011 Update 2015", "0502");

  NistStandardEnum(String label, String code) {
    this.label = label;
    this.code = code;
  }

  private final String label;

  @Getter private final String code;

  public static Optional<NistStandardEnum> findByCode(String code) {
    return Arrays.stream(NistStandardEnum.values())
        .filter(nistStandardEnum -> nistStandardEnum.getCode().equals(code))
        .findFirst();
  }

  /**
   * Indique si le standard est chronologiquement défini entre les deux limites
   *
   * @param lowerStandard limite basse inclue dans l'intervalle
   * @param upperStandard limite haute exclus de l'intervalle
   * @return true si le standard est inclus dans l'intervalle de standards
   */
  public boolean isBetweenStandards(
      NistStandardEnum lowerStandard, NistStandardEnum upperStandard) {
    return (this.ordinal() >= lowerStandard.ordinal()) && isPriorTo(upperStandard);
  }

  /**
   * Inférieur strict au standard donné en argument
   */
  public boolean isPriorTo(NistStandardEnum nistStandardEnum) {
    return (nistStandardEnum == null || this.ordinal() < nistStandardEnum.ordinal());
  }
}
