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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;

public class Std2011RT13Validator extends Std2007RT13Validator {

  public Std2011RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  public Std2011RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2011;
  }

  @Override
  public void rules() {
    // Common rules on fields
    super.rules();
  }
}
