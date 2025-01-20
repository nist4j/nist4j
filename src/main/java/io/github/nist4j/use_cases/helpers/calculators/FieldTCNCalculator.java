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
package io.github.nist4j.use_cases.helpers.calculators;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;

public class FieldTCNCalculator {

  private static final char[] CHECK_DIGIT_TABLE = new char[23];
  public static final int FIELD_SIZE = 11;
  public static final int YY_SIZE = 2;

  static {
    int i = 0;
    CHECK_DIGIT_TABLE[i++] = 'Z'; // 0
    CHECK_DIGIT_TABLE[i++] = 'A'; // 1
    CHECK_DIGIT_TABLE[i++] = 'B';
    CHECK_DIGIT_TABLE[i++] = 'C';
    CHECK_DIGIT_TABLE[i++] = 'D';
    CHECK_DIGIT_TABLE[i++] = 'E';
    CHECK_DIGIT_TABLE[i++] = 'F';
    CHECK_DIGIT_TABLE[i++] = 'G';
    CHECK_DIGIT_TABLE[i++] = 'H'; // 8
    CHECK_DIGIT_TABLE[i++] = 'J'; // 9
    CHECK_DIGIT_TABLE[i++] = 'K';
    CHECK_DIGIT_TABLE[i++] = 'L';
    CHECK_DIGIT_TABLE[i++] = 'M';
    CHECK_DIGIT_TABLE[i++] = 'N';
    CHECK_DIGIT_TABLE[i++] = 'P';
    CHECK_DIGIT_TABLE[i++] = 'Q';
    CHECK_DIGIT_TABLE[i++] = 'R'; // 16
    CHECK_DIGIT_TABLE[i++] = 'T'; // 17
    CHECK_DIGIT_TABLE[i++] = 'U';
    CHECK_DIGIT_TABLE[i++] = 'V';
    CHECK_DIGIT_TABLE[i++] = 'W';
    CHECK_DIGIT_TABLE[i++] = 'X';
    CHECK_DIGIT_TABLE[i] = 'Y'; // 22
  }

  private static final long pow10_8 = (long) Math.pow(10, 8); // 10^8

  public static char calculateTCNLastCharacter(long yy, long ssssssss) {
    long sum = (yy * pow10_8) + ssssssss;
    int modulo = (int) (sum % 23);
    return CHECK_DIGIT_TABLE[modulo];
  }

  public static String calculateTCNLastCharacter(String TCNWithoutCheck) {
    if (TCNWithoutCheck == null || TCNWithoutCheck.length() != FIELD_SIZE - 1) {
      throw new InvalidFormatNist4jException("Field should be 10 char long");
    }
    try {
      long yy = Long.parseLong(TCNWithoutCheck.substring(0, YY_SIZE));
      long sssss = Long.parseLong(TCNWithoutCheck.substring(YY_SIZE));

      return TCNWithoutCheck + calculateTCNLastCharacter(yy, sssss);
    } catch (NumberFormatException e) {
      throw new InvalidFormatNist4jException("Field should contain only number");
    }
  }

  public static boolean isValid(String TCN) {
    if (TCN == null || TCN.length() != FIELD_SIZE) {
      return false; // bad format should be 11 chars
    }
    try {
      long yy = Long.parseLong(TCN.substring(0, YY_SIZE));
      long sssss = Long.parseLong(TCN.substring(YY_SIZE, FIELD_SIZE - 1));
      char checkDigit = TCN.charAt(FIELD_SIZE - 1);

      return checkDigit == calculateTCNLastCharacter(yy, sssss);
    } catch (NumberFormatException e) {
      return false; // bad format on yy or sssss
    }
  }
}
