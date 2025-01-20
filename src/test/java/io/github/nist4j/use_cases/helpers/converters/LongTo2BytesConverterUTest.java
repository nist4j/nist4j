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
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LongTo2BytesConverterUTest {

  @Test
  void convert4BytesAsLong_should_convert_a_table() {
    // Given
    byte[] bytes = new byte[] {38, 3};

    // When
    long result = LongTo2BytesConverter.from2Bytes(bytes, 0);

    // Then
    assertEquals(9731, result);
  }

  @Test
  void convertLongAs4Bytes_should_convert_a_long() {
    // Given
    byte[] expectedBytes = new byte[] {38, 3};

    // When
    byte[] bytesResults = LongTo2BytesConverter.to2Bytes(9731);

    // Then
    assertThat(bytesResults).isEqualTo(expectedBytes);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("getRandomListOfInt")
  void convert4BytesAsLong_should_be_reversible(int testValue) {
    // Given
    // When
    byte[] bytes = LongTo2BytesConverter.to2Bytes(testValue);
    long result = LongTo2BytesConverter.from2Bytes(bytes, 0);
    // Then
    assertThat(result).isEqualTo(testValue);

    // And When
    byte[] resultBytes = LongTo2BytesConverter.to2Bytes(result);

    // Then
    assertThat(resultBytes).isEqualTo(bytes);
  }

  public static Stream<Arguments> getRandomListOfInt() {
    return new Random().ints(23, 0, 9999).boxed().map(Arguments::of);
  }
}
