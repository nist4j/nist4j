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

import static java.util.Objects.nonNull;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import lombok.AllArgsConstructor;

/**
 * This Calculator manage to calculate the length of Text record like R1,R2,R14 but not R3,R4
 */
@AllArgsConstructor
public class CalculateAndSetLENForBinaryRecordCallback implements Callback<NistRecordBuilder> {

  public static final int FIELD_LEN_ID = 1;

  private final NistOptions nistOptions;
  private final int sizeOfHeaderFields;
  private final IFieldTypeEnum binaryField;

  @Override
  public void execute(NistRecordBuilder nistRecordBuilder) {
    if (nistOptions.isCalculateLENOnBuild()) {
      int totalRecordLength = sizeOfHeaderFields;
      Data data = nistRecordBuilder.getFields().get(binaryField.getId());
      if (nonNull(data)) {
        totalRecordLength += data.getLength();
      }
      Data dataLEN = new DataTextBuilder().withValue(String.valueOf(totalRecordLength)).build();
      nistRecordBuilder.withField(FIELD_LEN_ID, dataLEN);
    }
  }
}
