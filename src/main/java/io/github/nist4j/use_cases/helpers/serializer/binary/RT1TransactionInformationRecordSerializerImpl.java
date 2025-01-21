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
package io.github.nist4j.use_cases.helpers.serializer.binary;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.serializer.RecordReader;
import io.github.nist4j.use_cases.helpers.serializer.RecordWriter;
import io.github.nist4j.use_cases.helpers.serializer.binary.abstracts.AbstractTextRecordSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RT1TransactionInformationRecordSerializerImpl
    extends AbstractTextRecordSerializer<RT1TransactionInformationNistRecordBuilderImpl>
    implements RecordReader, RecordWriter {

  public RT1TransactionInformationRecordSerializerImpl(NistOptions nistOptions) {
    // This record is always write in plain text encoding
    super(nistOptions, new RT1TransactionInformationNistRecordBuilderImpl(nistOptions));
  }

  @Override
  public NistRecord read(NistDecoderHelper.Token token) throws ErrorDecodingNist4jException {
    checkIfPosOutOfIndex(token);
    NistRecordBuilder nistRecordBuilder = getNistRecordBuilder().newBuilder();

    do {
      NistDecoderHelper.Tag tag = getTagInfo(token);

      if (tag.type != nistRecordBuilder.getRecordId()) {
        throw new ErrorDecodingNist4jException("T1::Invalid Record Type : " + tag.type);
      }

      String value =
          nextWord(token, NistDecoderHelper.TAG_SEP_GSFS, NistDecoderHelper.FIELD_MAX_LENGTH - 1);
      if (tag.field == 3) {
        token.header = value;
      }

      log.debug("NIST - tag.type: {} ,tag.field: {}, value : {}", tag.type, tag.field, value);
      Data dataText = new DataTextBuilder().withValue(value).build();
      nistRecordBuilder.withField(tag.field, dataText);

      if (tag.field == 15) {
        log.debug(
            "NIST - tag.type: {} ,tag.field: {}, setCharSetDecoder {}", tag.type, tag.field, value);
        token.setCharSetDecoder(value);
      }
    } while (token.buffer[token.pos++] != NistDecoderHelper.SEP_FS);

    return nistRecordBuilder.build();
  }
}
