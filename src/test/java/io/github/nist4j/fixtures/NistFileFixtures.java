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
import static java.util.Collections.emptyList;

import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.use_cases.CreateNistFile;
import io.github.nist4j.use_cases.helpers.builders.file.NistFileBuilderImpl;

public class NistFileFixtures {
  public static NistFileBuilder newNistFileBuilder(NistOptions nistOptions) {
    return new CreateNistFile(nistOptions).execute();
  }

  public static NistFileBuilder newNistFileBuilderEnableCalculation() {
    return newNistFileBuilder(OPTIONS_CALCULATE_ON_BUILD);
  }

  public static NistFileBuilder newNistFileBuilderDisableCalculation() {
    return newNistFileBuilder(OPTIONS_DONT_CHANGE_ON_BUILD);
  }

  public static NistFileBuilder newNistFileBuilderWithoutCallbacks() {
    return new NistFileBuilderImpl(OPTIONS_CALCULATE_ON_BUILD, emptyList(), emptyList());
  }
}
