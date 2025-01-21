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
package io.github.nist4j.fixtures;

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.records.DefaultNistTextRecordBuilderImpl.newRecordBuilder;

import io.github.nist4j.entities.record.NistRecordBuilder;

public class RecordFixtures {

  public static NistRecordBuilder newRecordBuilderEnableCalculation(int recordId) {
    return newRecordBuilder(OPTIONS_CALCULATE_ON_BUILD, recordId);
  }

  public static NistRecordBuilder newRecordBuilderDisableCalculation(int recordId) {
    return newRecordBuilder(OPTIONS_DONT_CHANGE_ON_BUILD, recordId);
  }
}
