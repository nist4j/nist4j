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

import static java.lang.String.format;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.converters.LongTo2BytesConverter;
import io.github.nist4j.use_cases.helpers.converters.LongTo4BytesConverter;
import io.github.nist4j.use_cases.helpers.converters.LongToStringConverter;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Getter(AccessLevel.PROTECTED)
@Slf4j
public abstract class AbstractRecordSerializer {

  protected static final int EMPTY_INT = 255;
  protected static final byte[] EMPTY_BYTES2 = new byte[] {0, 0};
  protected static final byte[] EMPTY_BYTES4 = new byte[] {0, 0, 0, 0};

  protected final NistOptions nistOptions;
  private final NistRecordBuilder nistRecordBuilder;
  protected final LongToStringConverter longToStringConverter;

  protected AbstractRecordSerializer(
      @NonNull NistOptions nistOptions, @NonNull NistRecordBuilder nistRecordBuilder) {
    this.nistOptions = nistOptions;
    this.nistRecordBuilder = nistRecordBuilder;
    this.longToStringConverter = new LongToStringConverter(nistOptions);
  }

  public abstract void write(OutputStream outputStream, NistRecord record)
      throws ErrorEncodingNist4jException;

  public abstract NistRecord read(NistDecoderHelper.Token token)
      throws ErrorDecodingNist4jException;

  protected NistDecoderHelper.Tag getTagInfo(NistDecoderHelper.Token token)
      throws ErrorDecodingNist4jException {
    String type = nextWord(token, NistDecoderHelper.TAG_SEP_DOT, 2);
    token.pos++;
    String field = nextWord(token, NistDecoderHelper.TAG_SEP_COLN, 10);
    token.pos++;

    return new NistDecoderHelper.Tag(
        Integer.parseInt(type.replace(",", "")), Integer.parseInt(field));
  }

  protected String nextWord(NistDecoderHelper.Token token, char[] sepList, int maxLen)
      throws ErrorDecodingNist4jException {
    int i = 0;
    while (i < maxLen
        && token.pos < token.buffer.length
        && token.buffer[token.pos] != sepList[0]
        && token.buffer[token.pos] != sepList[1]) {
      token.pos++;
      i++;
    }

    byte[] data = new byte[i];
    System.arraycopy(token.buffer, token.pos - i, data, 0, i);

    try {
      return String.valueOf(token.charsetDecoder.decode(ByteBuffer.wrap(data)));
    } catch (CharacterCodingException e) {
      log.error("Exception when reading the record ", e);
      throw new ErrorDecodingNist4jException(e.getMessage());
    }
  }

  protected long read4BytesAsInt(NistDecoderHelper.Token token) {
    return LongTo4BytesConverter.from4Bytes(token.buffer, token.pos);
  }

  protected long read2BytesAsInt(NistDecoderHelper.Token token, int offset) {
    return LongTo2BytesConverter.from2Bytes(token.buffer, token.pos + offset);
  }

  protected void checkIfPosOutOfIndex(NistDecoderHelper.Token token)
      throws ErrorDecodingNist4jException {
    if (token.pos >= token.buffer.length) {
      throw new ErrorDecodingNist4jException(
          format("Error decoding record type %s : position %s out of index", token.crt, token.pos));
    }
  }

  protected boolean checkRecordSizeLength(NistDecoderHelper.Token token, int offset) {
    return (token.pos + offset) < token.buffer.length;
  }

  protected void checkTypeInFieldName(int recordId, NistDecoderHelper.Tag tag)
      throws ErrorDecodingNist4jException {
    if (tag.type != recordId) {
      throw new ErrorDecodingNist4jException(
          format(
              "Decoding NIST - Invalid Record Type - expected : %s, received: %s",
              recordId, tag.type));
    }
  }
}
