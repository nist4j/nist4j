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
package io.github.nist4j.use_cases.helpers.calculators;

import static java.lang.String.format;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class FieldLENRecordTextCalculator {

  private final NistOptions nistOptions;

  public int calculateLength(NistRecordBuilder nistRecordBuilder) {
    final int defaultPrefixLength = calculatePrefixLength(nistRecordBuilder.getRecordId());
    Map<Integer, Data> fields = nistRecordBuilder.getFields();

    final int allFieldsLengthWithoutLEN =
        fields.entrySet().stream()
            .filter(e -> e.getKey() != 1)
            .mapToInt(e -> e.getValue().getLength())
            .map(len -> len + defaultPrefixLength)
            .sum();

    int defaultLENSize = String.valueOf(allFieldsLengthWithoutLEN).length();
    int allFieldslengthWithLEN = allFieldsLengthWithoutLEN + defaultLENSize + defaultPrefixLength;

    // In case of bad length, juste add one digit
    if (String.valueOf(allFieldsLengthWithoutLEN).length()
        < String.valueOf(allFieldslengthWithLEN).length()) {
      allFieldslengthWithLEN += 1;
    }

    return allFieldslengthWithLEN;
  }

  public int calculatePrefixLength(Integer recordId) {
    // <GS>14.001:
    return format("%s%s.001:", NistDecoderHelper.SEP_GS, recordId).length();
  }
}
