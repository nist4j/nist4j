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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CharsetEnum {
  DEFAULT("DEFAULT", Charset.forName("cp1256")),
  CP1256("CP1256", Charset.forName("cp1256")),
  UTF_8("UTF_8", StandardCharsets.UTF_8),
  UTF_16("UTF_16", StandardCharsets.UTF_16);

  private final String name;
  private final Charset charset;
}
