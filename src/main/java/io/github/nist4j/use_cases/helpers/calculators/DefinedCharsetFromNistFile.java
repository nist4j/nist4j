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
package io.github.nist4j.use_cases.helpers.calculators;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class DefinedCharsetFromNistFile {
  @SuppressWarnings("unused")
  private final NistOptions nistOptions;

  public CharsetEnum execute(@NonNull NistFile nistFile) {
    Optional<String> fieldDCS =
        nistFile.getRT1TransactionInformationRecord().getFieldText(RT1FieldsEnum.DCS);
    if (fieldDCS.isPresent()) {
      try {
        String dcsField0 = SubFieldToStringConverter.toItems(fieldDCS.get()).get(0);
        int dcsId = Integer.parseInt(dcsField0);
        return CharsetEnum.findByCode(dcsId).orElse(CharsetEnum.getDefault());
      } catch (Exception e) {
        log.error("Exception while DefinedCharsetFromNistFile {}", e.getMessage());
        return CharsetEnum.getDefault();
      }
    } else {
      return CharsetEnum.getDefault();
    }
  }
}
