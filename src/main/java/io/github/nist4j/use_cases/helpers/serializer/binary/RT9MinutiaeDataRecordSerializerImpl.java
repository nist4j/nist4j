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
import io.github.nist4j.use_cases.helpers.builders.records.RT9MinutiaeDataNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.serializer.RecordReader;
import io.github.nist4j.use_cases.helpers.serializer.RecordWriter;
import io.github.nist4j.use_cases.helpers.serializer.binary.abstracts.AbstractTextRecordSerializer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RT9MinutiaeDataRecordSerializerImpl
    extends AbstractTextRecordSerializer<RT9MinutiaeDataNistRecordBuilderImpl>
    implements RecordReader, RecordWriter {

  public RT9MinutiaeDataRecordSerializerImpl(NistOptions nistOptions) {
    super(nistOptions, new RT9MinutiaeDataNistRecordBuilderImpl(nistOptions));
  }
}
