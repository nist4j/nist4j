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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord4;

import static io.github.nist4j.enums.records.RT4FieldsEnum.*;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.standards.abstracts.AbstractStdRT3To6Validator;

public abstract class Nist4jAbstractRT4Validator extends AbstractStdRT3To6Validator {

  public Nist4jAbstractRT4Validator(NistOptions nistOptions) {
    super(nistOptions, RecordTypeEnum.RT4);
  }

  protected abstract NistStandardEnum getStandard();

  @SuppressWarnings("DuplicatedCode")
	@Override
  public void rules() {
    checkThatLENisValidForRT3to6(LEN);
    checkThatIDCisValidForRT3to6(IDC);
    checkThatFGPisValidForRT3to6(FGP);
    checkThatIMPisValidForRT3to6(IMP);
    checkThatISRisValidForRT3to6(ISR);
    checkThatHLLisValidForRT3to6(HLL);
    checkThatVLLisValidForRT3to6(VLL);
    checkThatGCAisValidForRT3to6(GCA);
    checkThatDATAisValidForRT3to6(DATA);
  }
}
