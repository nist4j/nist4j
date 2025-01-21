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

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import lombok.NonNull;

/**
 * This Calculator manage to calculate the length of Text record like R1,R2,R14 but not R3,R4
 */
public class CalculateAndSetLENForTextRecordCallback implements Callback<NistRecordBuilder> {

  private final NistOptions nistOptions;
  private final FieldLENRecordTextCalculator fieldLENRecordTextCalculator;

  public CalculateAndSetLENForTextRecordCallback(@NonNull NistOptions nistOptions) {
    this.nistOptions = nistOptions;
    this.fieldLENRecordTextCalculator = new FieldLENRecordTextCalculator(nistOptions);
  }

  @Override
  public void execute(NistRecordBuilder nistRecordBuilder) {
    if (nistOptions.isCalculateLENOnBuild()) {
      int length = fieldLENRecordTextCalculator.calculateLength(nistRecordBuilder);
      Data dataLEN = new DataTextBuilder().withValue(String.valueOf(length)).build();
      nistRecordBuilder.withField(1, dataLEN);
    }
  }
}
