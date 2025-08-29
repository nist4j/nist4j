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

import static java.util.Objects.isNull;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class ByteToStringConverter {

  private final NistOptions nistOptions;

  private static final int INT_MAX_VALUE = 255;
  private static final int INT_MIN_VALUE = 0;

  public String toString(int smallIntValue) {
    return toString((byte) smallIntValue);
  }

  public String toString(byte byteValue) {
    int tmpIntValue = Byte.toUnsignedInt(byteValue);
    return String.valueOf(tmpIntValue);
  }

  public static Optional<String> toHex(byte[] bytes) {
    if (isNull(bytes)) {
      return Optional.empty();
    }
    try {
      StringBuilder hex = new StringBuilder();
      for (byte b : bytes) {
        hex.append(String.format("%02x", b));
      }
      return Optional.of(hex.toString().toUpperCase());
    } catch (NumberFormatException e) {
      return Optional.empty();
    }
  }

  public int fromString(@NonNull String stringValue) {
    try {
      int intValue = Integer.parseInt(stringValue);

      if (intValue < INT_MIN_VALUE || intValue > INT_MAX_VALUE) {
        throw new InvalidFormatNist4jException("Value out of range: " + stringValue);
      }
      return intValue;
    } catch (NumberFormatException e) {
      log.error("Error parsing int: {}", stringValue, e);
      throw new InvalidFormatNist4jException("Value out of range: " + stringValue);
    }
  }
}
