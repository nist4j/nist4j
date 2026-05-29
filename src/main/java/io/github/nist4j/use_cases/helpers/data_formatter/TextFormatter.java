/*
 * Copyright (C) 2026 Sopra Steria.
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
package io.github.nist4j.use_cases.helpers.data_formatter;

import io.github.nist4j.exceptions.InvalidDataFormatNist4jException;

public class TextFormatter {

  public static final String ZERO = "0";

  public static String leadZeroText(int intVal, int length) {
    if (length < 0 || length > 999) {
      throw new InvalidDataFormatNist4jException("length out of range [0,999]");
    }
    if (intVal < 0) {
      throw new InvalidDataFormatNist4jException("intVal must be positive");
    }
    final StringBuilder strVal = new StringBuilder(String.valueOf(intVal));
    while (strVal.length() < length) {
      strVal.insert(0, ZERO);
    }
    return strVal.toString();
  }
}
