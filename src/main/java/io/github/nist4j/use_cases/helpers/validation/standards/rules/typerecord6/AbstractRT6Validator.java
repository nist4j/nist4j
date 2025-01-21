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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord6;

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_DATA_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_FGP_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_GCA_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_HLL_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IDC_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_IMP_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_ISR_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_LEN_RT6;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_VLL_RT6;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.standards.abstracts.AbstractStdRT3to6Validator;

public abstract class AbstractRT6Validator extends AbstractStdRT3to6Validator {

  public AbstractRT6Validator(NistOptions nistOptions) {
    super(nistOptions, RecordTypeEnum.RT6);
  }

  protected abstract NistStandardEnum getStandard();

  @Override
  public void rules() {
    checkThatLENisValidForRT3to6(STD_ERR_LEN_RT6);
    checkThatIDCisValidForRT3to6(STD_ERR_IDC_RT6);
    checkThatFGPisValidForRT3to6(STD_ERR_FGP_RT6);
    checkThatIMPisValidForRT3to6(STD_ERR_IMP_RT6);
    checkThatISRisValidForRT3to6(STD_ERR_ISR_RT6);
    checkThatHLLisValidForRT3to6(STD_ERR_HLL_RT6);
    checkThatVLLisValidForRT3to6(STD_ERR_VLL_RT6);
    checkThatGCAisValidForRT3to6(STD_ERR_GCA_RT6);
    checkThatDATAisValidForRT3to6(STD_ERR_DATA_RT6);
  }
}
