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
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LongToStringConverterTest {

  final LongToStringConverter longToStringConverter =
      new LongToStringConverter(OPTIONS_DONT_CHANGE_ON_BUILD);

  @Test
  void toString_should_convert() {
    // Given
    // When
    // Then
    assertThat(longToStringConverter.toString(13)).isEqualTo("13");
    assertThat(longToStringConverter.toString(255)).isEqualTo("255");
    assertThat(longToStringConverter.toString(0)).isEqualTo("0");
    assertThat(longToStringConverter.toString(-1)).isEqualTo("-1");
    assertThat(longToStringConverter.toString(-2)).isEqualTo("-2");
    assertThat(longToStringConverter.toString(256)).isEqualTo("256");
  }

  @Test
  void fromString_should_convert() {
    assertThat(longToStringConverter.fromString("13")).isEqualTo(13);
    assertThat(longToStringConverter.fromString("255")).isEqualTo(255);
    assertThat(longToStringConverter.fromString("0")).isEqualTo(0);
    assertThat(longToStringConverter.fromString("-1")).isEqualTo(-1);
    assertThat(longToStringConverter.fromString("8000")).isEqualTo(8000);
    assertThat(longToStringConverter.fromString("-123456")).isEqualTo(-123456);
  }

  @Test
  void fromString_should_throw_an_exception() {
    assertThrows(
        InvalidFormatNist4jException.class, () -> longToStringConverter.fromString("ZERTYUI"));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getRandomListOfLong")
  void fromString_and_ToString_should_be_reversible(Long longValue) {
    // Given
    // When
    String stringValue = longToStringConverter.toString(longValue);
    long resultIntValue = longToStringConverter.fromString(stringValue);
    // Then
    assertThat(resultIntValue).isEqualTo(longValue);
  }

  public static Stream<Arguments> getRandomListOfLong() {
    return new Random().longs(51, -10000, 10000).boxed().map(Arguments::of);
  }
}
