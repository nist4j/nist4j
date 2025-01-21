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
package io.github.nist4j.use_cases;

import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.use_cases.helpers.builders.file.NistFileBuilderImpl;
import io.github.nist4j.use_cases.helpers.calculators.CalculateR1CNTAndLengthCallback;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateNistFile {

  public static final NistOptions DEFAULT_OPTIONS_FOR_CREATE =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(true)
          .isCalculateCNTOnBuild(true)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  private final NistOptions nistOptions;

  public CreateNistFile() {
    this(DEFAULT_OPTIONS_FOR_CREATE);
  }

  public CreateNistFile(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
  }

  public NistFileBuilder execute() {
    log.debug("Creating a nistFile");

    return new NistFileBuilderImpl(
        nistOptions,
        Collections.singletonList(new CalculateR1CNTAndLengthCallback(nistOptions)),
        Collections.emptyList());
  }
}
