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
package io.github.nist4j.use_cases.helpers.conditions;

public class StringCondition {

  public static final String EMPTY = "";

  public static boolean isNotBlank(CharSequence cs) {
    return !isBlank(cs);
  }

  public static boolean isBlank(CharSequence cs) {
    int strLen = length(cs);
    if (strLen != 0) {
      for (int i = 0; i < strLen; ++i) {
        if (!Character.isWhitespace(cs.charAt(i))) {
          return false;
        }
      }
    }
    return true;
  }

  public static int length(CharSequence cs) {
    return cs == null ? 0 : cs.length();
  }

  public static boolean areEquals(CharSequence cs1, CharSequence cs2) {
    if (cs1 == null && cs2 == null) {
      return true;
    } else if (cs1 == null) {
      return false;
    } else {
      return cs1.equals(cs2);
    }
  }
}
