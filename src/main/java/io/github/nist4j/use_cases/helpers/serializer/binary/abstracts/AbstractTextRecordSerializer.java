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
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTextRecordSerializer<Z extends NistRecordBuilder>
    extends AbstractRecordSerializer {

  public AbstractTextRecordSerializer(
      NistOptions nistOptions, NistRecordBuilder nistRecordBuilder) {
    super(nistOptions, nistRecordBuilder);
  }

  @Override
  public void write(OutputStream outputStream, NistRecord nistRecord)
      throws ErrorEncodingNist4jException {
    try {
      boolean isFirst = true;
      for (Map.Entry<Integer, Data> iDataEntry : nistRecord.getFields().entrySet()) {

        if (isFirst) {
          isFirst = false;
        } else {
          outputStream.write(NistDecoderHelper.SEP_GS);
        }

        if (iDataEntry.getValue() instanceof DataTextImmutableImpl) {
          byte[] buffer =
              generateFieldTextToken(
                  nistRecord.getRecordId(),
                  iDataEntry.getKey(),
                  ((DataTextImmutableImpl) iDataEntry.getValue()));
          outputStream.write(buffer);
        } else if (iDataEntry.getValue() instanceof DataImageImmutableImpl) {
          outputStream.write(
              generatePrefixFieldImageToken(nistRecord.getRecordId(), iDataEntry.getKey()));
          outputStream.write(((DataImageImmutableImpl) iDataEntry.getValue()).getData());
        } else {
          throw new InvalidFormatNist4jException("not implemented");
        }
      }

      // End the nistRecord with <FS>
      outputStream.write(NistDecoderHelper.SEP_FS);
    } catch (IOException e) {
      log.error("Exception when writing nistRecord 1", e);
      throw new ErrorEncodingNist4jException(e.getMessage());
    }
  }

  private byte[] generatePrefixFieldImageToken(Integer recordId, Integer key) {
    String start =
        new StringBuilder()
            .append(recordId)
            .append(NistDecoderHelper.TAG_SEP_DOT[0])
            .append(format("%03d", key))
            .append(NistDecoderHelper.TAG_SEP_COLN[0])
            .toString();
    return start.getBytes();
  }

  private byte[] generateFieldTextToken(
      Integer recordId, Integer key, DataTextImmutableImpl dataTextImmutableImpl) {

    return new StringBuilder()
        .append(recordId)
        .append(NistDecoderHelper.TAG_SEP_DOT[0])
        .append(format("%03d", key))
        .append(NistDecoderHelper.TAG_SEP_COLN[0])
        .append(dataTextImmutableImpl.getData())
        .toString()
        .getBytes();
  }

  @Override
  public NistRecord read(NistDecoderHelper.Token token) throws ErrorDecodingNist4jException {
    NistDecoderHelper.Tag tag;
    int start = token.pos;

    checkIfPosOutOfIndex(token);
    NistRecordBuilder nistRecordBuilder = getNistRecordBuilder().newBuilder();

    // Parse field LEN
    tag = getTagInfo(token);
    log.debug(
        "Decoding NIST - Record Type {} - position: {}, tag.field: {}",
        tag.type,
        token.pos,
        tag.field);
    checkTypeInFieldName(nistRecordBuilder.getRecordId(), tag);
    int length =
        Integer.parseInt(
            nextWord(token, NistDecoderHelper.TAG_SEP_GSFS, NistDecoderHelper.FIELD_MAX_LENGTH));
    Data dataText = new DataTextBuilder().withValue(longToStringConverter.toString(length)).build();
    nistRecordBuilder.withField(tag.field, dataText);
    log.debug(
        "Decoding NIST - Record Type {} - position: {}, length: {}", tag.type, token.pos, length);

    // Parse other fields
    token.pos++;
    do {
      tag = getTagInfo(token);
      log.debug(
          "Decoding NIST - Record Type {} - position: {}, tag.field: {}",
          tag.type,
          token.pos,
          tag.field);

      checkTypeInFieldName(nistRecordBuilder.getRecordId(), tag);

      if (tag.field == 999) {
        byte[] data = new byte[length - 1 - (token.pos - start)];
        System.arraycopy(token.buffer, token.pos, data, 0, data.length);
        token.pos = token.pos + data.length;
        if (token.buffer[token.pos] == NistDecoderHelper.SEP_FS) {
          token.pos++;
        }
        Data dataImage = new DataImageBuilder().withValue(data).build();
        nistRecordBuilder.withField(999, dataImage);
        break;
      } else {
        String value =
            nextWord(token, NistDecoderHelper.TAG_SEP_GSFS, NistDecoderHelper.FIELD_MAX_LENGTH - 1);
        dataText = new DataTextBuilder().withValue(value).build();
        nistRecordBuilder.withField(tag.field, dataText);
      }

    } while (token.buffer[token.pos++] != NistDecoderHelper.SEP_FS);
    return nistRecordBuilder.build();
  }
}
