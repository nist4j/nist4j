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

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.enums.CharsetEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OptionsFixtures {

  public static final NistOptions OPTIONS_DONT_CHANGE_ON_BUILD =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.CP1256.getCharset())
          .build();

  public static final NistOptions OPTIONS_CALCULATE_ON_BUILD =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(true)
          .isCalculateCNTOnBuild(true)
          .charset(CharsetEnum.CP1256.getCharset())
          .build();

  public static final NistOptions OPTIONS_UTF8_CALCULATE_ON_BUILD =
      NistOptionsImpl.builder()
          .charset(CharsetEnum.UTF_8.getCharset())
          .isCalculateLENOnBuild(true)
          .isCalculateCNTOnBuild(true)
          .build();

  public static final NistOptions OPTIONS_FOR_VALIDATION = OPTIONS_DONT_CHANGE_ON_BUILD;
}
