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

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.calculators.CalculateAndSetLENForTextRecordCallback;
import java.util.Collections;

public final class DefaultNistTextRecordBuilderImpl extends AbstractNistRecordBuilderImpl
    implements NistRecordBuilder {

  public DefaultNistTextRecordBuilderImpl(NistOptions nistOptions, Integer recordId) {
    super(
        nistOptions,
        recordId,
        "RecordType " + recordId,
        Collections.singletonList(new CalculateAndSetLENForTextRecordCallback(nistOptions)),
        Collections.emptyList());
  }

  public static NistRecordBuilder newRecordBuilder(NistOptions nistOptions, int recordId) {
    switch (recordId) {
      case 1:
        return new RT1TransactionInformationNistRecordBuilderImpl(nistOptions);
      case 2:
        return new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(nistOptions);
      case 3:
        return new RT3LowResolutionGrayscaleFingerprintNistRecordBuilderImpl(nistOptions);
      case 4:
        return new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(nistOptions);
      case 5:
        return new RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl(nistOptions);
      case 6:
        return new RT6HighResolutionBinaryFingerprintNistRecordBuilderImpl(nistOptions);
      case 7:
        return new RT7UserDefinedImageNistRecordBuilderImpl(nistOptions);
      case 8:
        return new RT8SignatureImageNistRecordBuilderImpl(nistOptions);
      case 9:
        return new RT9MinutiaeDataNistRecordBuilderImpl(nistOptions);
      case 14:
        return new RT14VariableResolutionFingerprintNistRecordBuilderImpl(nistOptions);
      default:
        return new DefaultNistTextRecordBuilderImpl(nistOptions, recordId);
    }
  }

  public static NistRecordBuilder newRecordBuilder(
      NistOptions nistOptions, int recordId, IFieldTypeEnum field, Data... dataFields) {
    NistRecordBuilder recordBuilder = new DefaultNistTextRecordBuilderImpl(nistOptions, recordId);
    for (Data data : dataFields) {
      recordBuilder.withField(field, data);
    }
    return recordBuilder;
  }
}
