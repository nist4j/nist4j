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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LongTo4BytesConverterUTest {

  @Test
  void from4Bytes_should_convert_a_table() {
    // Given
    byte[] bytes = new byte[] {0, 1, -105, 85};

    // When
    long result = LongTo4BytesConverter.from4Bytes(bytes, 0);

    // Then
    assertEquals(104277, result);
  }

  @Test
  void convertLongAs4Bytes_should_convert_a_long() {
    // Given
    byte[] expectedBytes = new byte[] {0, 1, -105, 85};

    // When
    byte[] bytesResults = LongTo4BytesConverter.to4Bytes(104277);

    // Then
    assertThat(bytesResults).isEqualTo(expectedBytes);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getRandomListOfLong")
  void to4Bytes_and_from4Bytes_should_be_reversible(long testValue) {
    // Given
    // When
    byte[] bytes = LongTo4BytesConverter.to4Bytes(testValue);
    long result = LongTo4BytesConverter.from4Bytes(bytes, 0);
    // Then
    assertThat(testValue).isEqualTo(result);

    // And When
    byte[] resultBytes = LongTo4BytesConverter.to4Bytes(result);

    // Then
    assertThat(bytes).isEqualTo(resultBytes);
  }

  public static Stream<Arguments> getRandomListOfLong() {
    return new Random().ints(51, 0, 9999999).boxed().map(Arguments::of);
  }
}
