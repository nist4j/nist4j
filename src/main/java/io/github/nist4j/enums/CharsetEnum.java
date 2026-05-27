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
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CharsetEnum {
  ASCII(
      "ASCII",
      0,
      "7-bit (Default) with zero added in high bit position",
      StandardCharsets.US_ASCII),
  LEGACY_ASCII(
      "8-bit ASCII",
      1,
      "Legacy only - Latin-1 character set (See ISO/IEC 8859-1",
      StandardCharsets.ISO_8859_1),
  UTF_8("UTF-8", 3, "See NWG 3629 and The Unicode standard", StandardCharsets.UTF_8),
  UTF_16("UTF-16", 2, "See ISO/IEC 10646-1 and The Unicode standard", StandardCharsets.UTF_16BE),
  UTF_32("UTF-32", 4, "See The Unicode standard", Charset.forName("UTF-32"));

  private final String label;
  private final int dcsValue;
  private final String description;
  private final Charset charset;

  @SuppressWarnings("SameReturnValue")
  public static CharsetEnum getDefault() {
    return ASCII;
  }

  public static Optional<CharsetEnum> findByCode(int dcsId) {
    for (CharsetEnum charsetEnum : CharsetEnum.values()) {
      if (charsetEnum.dcsValue == dcsId) {
        return Optional.of(charsetEnum);
      }
    }
    return Optional.empty();
  }
}
