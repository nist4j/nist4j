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

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ByteToStringConverterUTest {

  final ByteToStringConverter byteToStringConverter =
      new ByteToStringConverter(OPTIONS_DONT_CHANGE_ON_BUILD);

  @Test
  void toString_should_convert() {
    // Given
    // When
    // Then
    assertThat(byteToStringConverter.toString(13)).isEqualTo("13");
    assertThat(byteToStringConverter.toString(255)).isEqualTo("255");
    assertThat(byteToStringConverter.toString(0)).isEqualTo("0");
    assertThat(byteToStringConverter.toString(-1)).isEqualTo("255");
  }

  @Test
  void toString_should_pas_throw_an_exception() {
    // Given
    // When
    // Then
    assertThat(byteToStringConverter.toString(-2)).isEqualTo("254");
    assertThat(byteToStringConverter.toString(256)).isEqualTo("0");
  }

  @Test
  void fromString_should_convert() {
    assertThat(byteToStringConverter.fromString("13")).isEqualTo(13);
    assertThat(byteToStringConverter.fromString("255")).isEqualTo(255);
    assertThat(byteToStringConverter.fromString("0")).isEqualTo(0);
  }

  @Test
  void fromString_should_throw_an_exception() {
    assertThrows(InvalidFormatNist4jException.class, () -> byteToStringConverter.fromString("-1"));
    assertThrows(InvalidFormatNist4jException.class, () -> byteToStringConverter.fromString("256"));
    assertThrows(InvalidFormatNist4jException.class, () -> byteToStringConverter.fromString("BAD"));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getRandomListOfInt")
  void fromString_and_ToString_should_be_reversible(Integer intValue) {
    // Given
    // When
    String stringValue = byteToStringConverter.toString(intValue);
    int resultIntValue = byteToStringConverter.fromString(stringValue);
    // Then
    assertThat(resultIntValue).isEqualTo(intValue);
  }

  public static Stream<Arguments> getRandomListOfInt() {
    return new Random().ints(51, 0, 255).boxed().map(Arguments::of);
  }
}
