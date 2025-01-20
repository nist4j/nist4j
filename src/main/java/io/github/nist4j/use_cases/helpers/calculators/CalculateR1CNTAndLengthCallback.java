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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.records.RT1FieldsEnum.CNT;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.Callback;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;

public class CalculateR1CNTAndLengthCallback implements Callback<NistFileBuilder> {

  private final NistOptions nistOptions;
  private final FieldCNTCalculator calculatorNistFileContent;
  private final CalculateAndSetLENForTextRecordCallback calculatorLength;

  public CalculateR1CNTAndLengthCallback(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
    calculatorNistFileContent = new FieldCNTCalculator(nistOptions);
    calculatorLength = new CalculateAndSetLENForTextRecordCallback(nistOptions);
  }

  @Override
  public void execute(NistFileBuilder nistFileBuilder) {
    if (nistOptions.isCalculateCNTOnBuild()) {
      // Take the record1
      List<NistRecord> records1 = nistFileBuilder.getMapOfAllRecords().get(RT1);
      if (isNotEmpty(records1)) {
        NistRecord nistRecord1 = records1.get(0);
        NistRecordBuilder nistRecordBuilder1 =
            new RT1TransactionInformationNistRecordBuilderImpl(nistOptions).from(nistRecord1);

        // Calculate and set the CNT
        List<String> CNTarrays = calculatorNistFileContent.fromNistFileBuilder(nistFileBuilder);
        String CTNStr = SubFieldToStringConverter.fromList(CNTarrays);
        Data CTNData = new DataTextBuilder().withValue(CTNStr).build();
        nistRecordBuilder1.withField(CNT, CTNData);

        // Calculate and set the LEN
        calculatorLength.execute(nistRecordBuilder1);

        // Replace the R1 in the builder
        nistFileBuilder.removeRecord(RT1);
        nistFileBuilder.withRecord(RT1, nistRecordBuilder1.build());
      }
    }
  }
}
