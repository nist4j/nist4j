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
package io.github.nist4j.use_cases.helpers.checksum;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.nist4j.exceptions.Nist4jException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class Sha256ChecksumUTest {

  @Test
  void calculate_should_calculate_checksum() {
    // Given
    byte[] data1 = new byte[] {1, 2, 3, 4};
    byte[] data2 = new byte[] {9, 9, 3, 4};
    byte[] empty = new byte[] {};

    // When
    byte[] hash1 = Sha256Checksum.calculate(data1);
    byte[] hash2 = Sha256Checksum.calculate(data2);
    byte[] hashEmpty = Sha256Checksum.calculate(empty);

    // Then
    assertThat(hash1).isNotEmpty();
    assertThat(hash2).isNotEmpty();
    assertThat(hash1).isNotEqualTo(hash2);

    assertThat(Sha256Checksum.calculate(null)).isNull();
    assertThat(hashEmpty).isNotEmpty();
  }

  @Test
  void calculateToHex() {
    // Given
    byte[] data1 = new byte[] {1, 2, 3, 4};
    byte[] data2 = new byte[] {9, 9, 3, 4};
    byte[] empty = new byte[] {};

    // When
    String hash1 = Sha256Checksum.calculateToHex(data1);
    String hash2 = Sha256Checksum.calculateToHex(data2);
    String hashEmpty = Sha256Checksum.calculateToHex(empty);

    // Then
    assertThat(hash1).isNotEmpty();
    assertThat(hash2).isNotEmpty();
    assertThat(hash1).isNotEqualTo(hash2);
    assertThat(hash1).isEqualTo("9F64A747E1B97F131FABB6B447296C9B6F0201E79FB3C5356E6C77E89B6A806A");
    assertThat(hashEmpty)
        .isEqualTo("E3B0C44298FC1C149AFBF4C8996FB92427AE41E4649B934CA495991B7852B855");
  }

  @Test
  void calculate_should_throw_Exception_when_algorithm_fails() {
    byte[] data1 = new byte[] {1, 2, 3, 4};

    try (MockedStatic<MessageDigest> mocked = Mockito.mockStatic(MessageDigest.class)) {
      mocked
          .when(() -> MessageDigest.getInstance("SHA-256"))
          .thenThrow(new NoSuchAlgorithmException("fake"));

      Nist4jException ex =
          assertThrows(Nist4jException.class, () -> Sha256Checksum.calculate(data1));

      assertTrue(ex.getMessage().contains("not supported"));
    }
  }
}
