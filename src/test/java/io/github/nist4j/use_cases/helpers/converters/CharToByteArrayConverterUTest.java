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

import java.util.Random;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

class CharToByteArrayConverterUTest {

  public static final CharToByteArrayConverter charToByteArrayConverter =
      new CharToByteArrayConverter(OPTIONS_DONT_CHANGE_ON_BUILD);

  @Test
  void toByteArray() throws Exception {
    // Given
    char[] charArrays = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
    byte[] expectedByteArray = new byte[] {'a', 'b', 'c', 'd', 'e', 'f'};
    // When
    byte[] resultByteArrays = charToByteArrayConverter.toByteArray(charArrays);
    // Then
    assertThat(resultByteArrays).isEqualTo(expectedByteArray);
  }

  @Test
  void toCharArray() throws Exception {
    // Given
    char[] expectedCharArray = new char[] {'a', 'b', 'c', 'd', 'e', 'f'};
    byte[] byteArray = new byte[] {'a', 'b', 'c', 'd', 'e', 'f'};
    // When
    char[] resultCharArrays = charToByteArrayConverter.toCharArray(byteArray);
    // Then
    assertThat(resultCharArrays).isEqualTo(expectedCharArray);
  }

  @Test
  void toCharArray_and_toByteArray_should_be_reversible() throws Exception {
    // Given
    byte[] originalByteArray = getRandomByteArrays();
    // When
    char[] resultCharArray = charToByteArrayConverter.toCharArray(originalByteArray);
    byte[] resultByteArray = charToByteArrayConverter.toByteArray(resultCharArray);
    // Then
    assertThat(resultByteArray).isEqualTo(originalByteArray);
  }

  public static byte[] getRandomByteArrays() {

    Byte[] bytes =
        new Random().ints(99, 0, 99).boxed().map(Integer::byteValue).toArray(Byte[]::new);

    return ArrayUtils.toPrimitive(bytes);
  }
}
