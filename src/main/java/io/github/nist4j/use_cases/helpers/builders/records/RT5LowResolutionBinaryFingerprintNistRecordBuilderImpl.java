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
package io.github.nist4j.use_cases.helpers.builders.records;

import static io.github.nist4j.enums.RecordTypeEnum.RT5;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.use_cases.helpers.calculators.CalculateAndSetLENForBinaryRecordCallback;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl
    extends AbstractNistRecordBuilderImpl implements NistRecordBuilder {

  public RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl(NistOptions nistOptions) {
    super(
        nistOptions,
        RT5.getNumber(),
        RT5.getLabel(),
        Collections.singletonList(
            new CalculateAndSetLENForBinaryRecordCallback(
                nistOptions, 18, GenericImageTypeEnum.DATA)),
        Collections.emptyList());
  }
}
