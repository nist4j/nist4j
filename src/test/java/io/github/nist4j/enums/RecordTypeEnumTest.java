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
package io.github.nist4j.enums;

import static io.github.nist4j.enums.RecordTypeEnum.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class RecordTypeEnumTest {

  @Test
  void forbiddenRecordTypesByStandard_when_standard_2000_should_return_RT11_RT12_and_sup_RT17() {
    List<RecordTypeEnum> forbiddenValues =
        RecordTypeEnum.forbiddenRecordTypesByStandard(NistStandardEnum.ANSI_NIST_ITL_2000);

    assertThat(forbiddenValues)
        .isEqualTo(Arrays.asList(RT11, RT12, RT17, RT18, RT19, RT20, RT21, RT22, RT98, RT99));
  }

  @Test
  void forbiddenRecordTypesByStandard_when_standard_2007_should_return_RT11_RT12_sup_RT18() {
    List<RecordTypeEnum> forbiddenValues =
        RecordTypeEnum.forbiddenRecordTypesByStandard(NistStandardEnum.ANSI_NIST_ITL_2007);

    assertThat(forbiddenValues)
        .isEqualTo(Arrays.asList(RT11, RT12, RT18, RT19, RT20, RT21, RT22, RT98));
  }

  @Test
  void forbiddenRecordTypesByStandard_when_standard_2011_should_return_RT11_RT12_and_sup_RT17() {
    List<RecordTypeEnum> forbiddenValues =
        RecordTypeEnum.forbiddenRecordTypesByStandard(NistStandardEnum.ANSI_NIST_ITL_2011);

    assertThat(forbiddenValues).isEqualTo(Arrays.asList(RT3, RT5, RT6, RT11, RT12, RT22));
  }

  @Test
  void forbiddenRecordTypesByStandard_when_standard_2013_should_return_RT03_RT05_and_RT06() {
    List<RecordTypeEnum> forbiddenValues =
        RecordTypeEnum.forbiddenRecordTypesByStandard(NistStandardEnum.ANSI_NIST_ITL_2013);

    assertThat(forbiddenValues).isEqualTo(Arrays.asList(RT3, RT5, RT6));
  }

  @Test
  void forbiddenRecordTypesByStandard_when_standard_2015_should_return_RT03_RT05_and_RT06() {
    List<RecordTypeEnum> forbiddenValues =
        RecordTypeEnum.forbiddenRecordTypesByStandard(NistStandardEnum.ANSI_NIST_ITL_2015);

    assertThat(forbiddenValues).isEqualTo(Arrays.asList(RT3, RT5, RT6));
  }
}
