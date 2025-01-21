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
package io.github.nist4j.use_cases.helpers.serializer.binary.abstracts;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper.Tag;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper.Token;
import io.github.nist4j.use_cases.helpers.builders.records.DefaultNistTextRecordBuilderImpl;
import java.io.OutputStream;
import org.junit.jupiter.api.Test;

class AbstractRecordSerializerUTest {

  final NistOptions nistOptions = NistOptionsImpl.builder().build();
  final NistRecordBuilder builder = new DefaultNistTextRecordBuilderImpl(nistOptions, 80);
  final AbstractRecordSerializer serializer =
      new AbstractRecordSerializer(nistOptions, builder) {
        @Override
        public void write(OutputStream outputStream, NistRecord record)
            throws ErrorEncodingNist4jException {}

        @Override
        public NistRecord read(Token token) throws ErrorDecodingNist4jException {
          return null;
        }
      };

  @Test
  void checkTypeInFieldName_should_return_exception() {
    // Given
    Tag tag = new NistDecoderHelper.Tag(80, 1);

    // When
    // Then
    assertThrows(
        ErrorDecodingNist4jException.class, () -> serializer.checkTypeInFieldName(81, tag));
  }

  @Test
  void checkIfPosOutOfIndex_should_return_exception() {
    // Given
    Token token = new NistDecoderHelper.Token("1.001:12".getBytes());
    token.pos = 20;

    // When
    // Then
    assertThrows(ErrorDecodingNist4jException.class, () -> serializer.checkIfPosOutOfIndex(token));
  }
}
