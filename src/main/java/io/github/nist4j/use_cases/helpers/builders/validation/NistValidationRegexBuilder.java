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
package io.github.nist4j.use_cases.helpers.builders.validation;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import io.github.nist4j.enums.validation.RegexpCharacterTypeEnum;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;

public class NistValidationRegexBuilder {
  public static final String REGEXP_ANS_ANY_LENGTH =
      new NistValidationRegexBuilder().allowsCharANS().build();

  private List<RegexpCharacterTypeEnum> allowedChars = emptyList();
  private int min = 0;
  private int max = Integer.MAX_VALUE;

  public NistValidationRegexBuilder allowsCharANS() {
    this.allowedChars =
        Arrays.asList(
            RegexpCharacterTypeEnum.ALPHABETIC,
            RegexpCharacterTypeEnum.NUMERIC,
            RegexpCharacterTypeEnum.SPECIAL);
    return this;
  }

  public NistValidationRegexBuilder min(Integer min) {
    this.min = min;
    return this;
  }

  public NistValidationRegexBuilder max(Integer max) {
    this.max = max;
    return this;
  }

  public NistValidationRegexBuilder allowsChar(@NonNull RegexpCharacterTypeEnum characterType) {
    if (isEmpty(this.allowedChars)) {
      this.allowedChars = new ArrayList<>();
    }
    this.allowedChars.add(characterType);
    return this;
  }

  public String build() {
    String finalOccurrence =
        (this.max == Integer.MAX_VALUE) ? "+" : "{" + this.min + "," + this.max + "}";
    String regexp =
        this.allowedChars.stream()
            .map(RegexpCharacterTypeEnum::getRegex)
            .collect(Collectors.joining());

    return "^[" + regexp + "]" + finalOccurrence + "$";
  }
}
