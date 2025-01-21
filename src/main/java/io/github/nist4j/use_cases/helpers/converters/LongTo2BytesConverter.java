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
package io.github.nist4j.use_cases.helpers.converters;

import io.github.nist4j.entities.NistOptions;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class LongTo2BytesConverter {

  private final NistOptions nistOptions;

  public static long from2Bytes(byte[] buffer, int offset) {
    byte byte1 = buffer[offset];
    byte byte2 = buffer[offset + 1];

    return (0xffL & byte1) << 8 | (0xffL & byte2);
  }

  public static byte[] to2Bytes(long value) {
    return new byte[] {(byte) (value >> 8), (byte) value};
  }
}
