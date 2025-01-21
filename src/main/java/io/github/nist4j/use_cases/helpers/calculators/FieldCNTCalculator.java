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

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.*;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * From Specifications : "Specify and identify each of the records in the transaction by
 * record type and its IDC value".
 * Example :
 * NIST = 1 record Type-1, 1 record Type-2, 1 record Type-10, 4 records Type-14
 * 12                      | Type-1 + Nb tot records
 * 20                      | Type-2 + IDC record  (max 1 record of Type-2, one so "0")
 * 101                     | Type-10 + IDC record (1 to 99)
 * 141 142 143 144   | Type-14 + IDC record (1 to 99)
 */
@AllArgsConstructor
@Slf4j
public class FieldCNTCalculator {

  private final NistOptions nistOptions;

  public List<String> fromNistFile(@NonNull NistFile nistFile) {
    return fromMapOfRecords(nistFile.getMapOfAllrecords());
  }

  public List<String> fromNistFileBuilder(NistFileBuilder nistFileBuilder) {
    return fromMapOfRecords(nistFileBuilder.getMapOfAllRecords());
  }

  private List<String> fromMapOfRecords(Map<RecordTypeEnum, List<NistRecord>> mapOfAllRecords) {
    AtomicInteger nbToC = new AtomicInteger(0);
    List<String> tocList = new ArrayList<>();

    List<NistRecord> allRecords = new ArrayList<>();

    // Others than R1
    if (Objects.nonNull(mapOfAllRecords)) {
      mapOfAllRecords.forEach(
          (key, value) -> {
            if (key != RT1) allRecords.addAll(value);
          });
    }

    for (NistRecord nistRecord : allRecords) {
      tocList.add(String.valueOf(nistRecord.getRecordId()));
      tocList.add(nistRecord.getFieldText(2).orElse("0"));
      nbToC.incrementAndGet();
    }

    List<String> finalTocList = new ArrayList<>();
    // R1
    if (Objects.isNull(mapOfAllRecords) || Objects.isNull(mapOfAllRecords.get(RT1))) {
      throw new InvalidFormatNist4jException("Record 1 must not be null");
    }
    finalTocList.add(String.valueOf(RT1.getNumber()));
    finalTocList.add(String.valueOf(nbToC.get()));
    // others
    finalTocList.addAll(tocList);

    return finalTocList;
  }
}
