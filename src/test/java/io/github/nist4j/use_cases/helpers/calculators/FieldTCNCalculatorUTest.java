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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FieldTCNCalculatorUTest {

  @Test
  void calculateTCNLastCharacter_should_calculate_correctly() {
    // Given
    long yy = 24;
    long ssss = 1995014;
    char expectedChar = 'V';
    // When
    char resultChar = FieldTCNCalculator.calculateTCNLastCharacter(yy, ssss);
    // Then
    assertThat(resultChar).isEqualTo(expectedChar);
  }

  @Test
  void isValid_should_return_ok() {
    // Given
    // When
    // Then
    assertThat(FieldTCNCalculator.isValid("2401995014V")).isTrue();
  }

  @Test
  void isValid_should_return_failure_when_error() {
    // Given
    // When
    // Then
    assertThat(FieldTCNCalculator.isValid("2401995014J")).isFalse();
    assertThat(FieldTCNCalculator.isValid("2401995014")).isFalse();
    assertThat(FieldTCNCalculator.isValid("2A01995014J")).isFalse();
    assertThat(FieldTCNCalculator.isValid("24019B5014J")).isFalse();
    assertThat(FieldTCNCalculator.isValid("")).isFalse();
    assertThat(FieldTCNCalculator.isValid(null)).isFalse();
    assertThat(FieldTCNCalculator.isValid("123")).isFalse();
  }

  @Test
  void calculateTCNLastCharacter_should_return_with_le_check_digit() {
    // Given
    // When
    // Then
    assertThat(FieldTCNCalculator.calculateTCNLastCharacter("2401995014")).isEqualTo("2401995014V");
  }

  @Test
  void calculateTCNLastCharacter_should_return_une_exception_if_format_incorrect() {
    // Given
    // When
    // Then
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> FieldTCNCalculator.calculateTCNLastCharacter("2401995014J"));
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> FieldTCNCalculator.calculateTCNLastCharacter("123456789"));
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> FieldTCNCalculator.calculateTCNLastCharacter("A23456789"));
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> FieldTCNCalculator.calculateTCNLastCharacter("12345678B"));
    assertThrows(
        InvalidFormatNist4jException.class,
        () -> FieldTCNCalculator.calculateTCNLastCharacter(null));
  }

  private static Stream<Arguments> getRandomYYAndSSS() {
    return new Random().ints(51, 1, 999999).boxed().map(val -> Arguments.of(val % 98 + 1, val));
  }

  @ParameterizedTest
  @MethodSource("getRandomYYAndSSS")
  void calculateTCNLastCharacter_should_be_valid(long yy, long ssssss) {
    // Given
    // When
    char checkDigit = FieldTCNCalculator.calculateTCNLastCharacter(yy, ssssss);
    final String TCNWithoutCheck = String.format("%02d%08d", yy, ssssss);
    // Then
    assertThat(TCNWithoutCheck.length()).isEqualTo(10);
    assertThat(FieldTCNCalculator.isValid(TCNWithoutCheck + checkDigit)).isTrue();
    assertThat(FieldTCNCalculator.calculateTCNLastCharacter(TCNWithoutCheck))
        .isEqualTo(TCNWithoutCheck + checkDigit);
  }
}
