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
package io.github.nist4j.use_cases.helpers.validation.standards;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.use_cases.helpers.validation.standards.abstracts.AbstractStdValidator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord1.Std2013RT1Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord10.Std2013RT10Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13.Std2013RT13Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14.Std2013RT14Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord2.AllStdRT2Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord3.Std2013RT3Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord4.Std2013RT4Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord5.Std2013RT5Validator;
import io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord6.Std2013RT6Validator;
import lombok.Getter;

/**
 * Specification for Standard 2011 Update 2013
 */
@Getter
public class Std2013Validator extends AbstractStdValidator {

  private final Std2013RT1Validator validatorForRT1Records;
  private final AllStdRT2Validator validatorForRT2Records;
  private final Std2013RT3Validator validatorForRT3Records;
  private final Std2013RT4Validator validatorForRT4Records;
  private final Std2013RT5Validator validatorForRT5Records;
  private final Std2013RT6Validator validatorForRT6Records;
  private final Std2013RT10Validator validatorForRT10Records;
  private final Std2013RT13Validator validatorForRT13Records;
  private final Std2013RT14Validator validatorForRT14Records;

  @SuppressWarnings("unused")
  public Std2013Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2013Validator(NistOptions nistOptions) {
    super(nistOptions);
    this.validatorForRT1Records = new Std2013RT1Validator(nistOptions);
    this.validatorForRT2Records = new AllStdRT2Validator(nistOptions);
    this.validatorForRT3Records = new Std2013RT3Validator(nistOptions);
    this.validatorForRT4Records = new Std2013RT4Validator(nistOptions);
    this.validatorForRT5Records = new Std2013RT5Validator(nistOptions);
    this.validatorForRT6Records = new Std2013RT6Validator(nistOptions);
    this.validatorForRT10Records = new Std2013RT10Validator(nistOptions);
    this.validatorForRT13Records = new Std2013RT13Validator(nistOptions);
    this.validatorForRT14Records = new Std2013RT14Validator(nistOptions);
  }

  @Override
  public NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2013;
  }
}
