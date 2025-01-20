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
package io.github.nist4j.fixtures;

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_UTF8_CALCULATE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.RT2FieldsEnum;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.CharToByteArrayConverter;
import java.nio.charset.CharacterCodingException;

public class Record2Fixtures {
  public static NistRecordBuilder record2Cas2_with_size_limit() {
    return new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT2FieldsEnum.LEN, newFieldText(String.valueOf(1)))
        .withField(2, newFieldText(""))
        .withField(3, newFieldText("1"))
        .withField(4, newFieldText("ORI1234567890"))
        .withField(5, newFieldText("DAI12345678901234567890"))
        .withField(6, newFieldText("DOM12345678901234567890"));
  }

  public static NistRecordBuilder record2Cas1_basic_Record() {
    return new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT2FieldsEnum.LEN, newFieldText(String.valueOf(66)))
        .withField(RT2FieldsEnum.IDC, newFieldText("57"))
        .withField(3, newFieldText("00"))
        .withField(4, newFieldText("domain defined text place holder"));
  }

  public static byte[] record2Cas1_basic_Binary() throws CharacterCodingException {
    String text =
        "2.001:662.002:572.003:002.004:domain defined text place holder"
            + NistDecoderHelper.SEP_FS;
    return new CharToByteArrayConverter(OPTIONS_DONT_CHANGE_ON_BUILD)
        .toByteArray(text.toCharArray());
  }

  public static NistRecordBuilder record2Cas3_with_accents_utf8() {

    return new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_UTF8_CALCULATE_ON_BUILD)
        .withField(RT2FieldsEnum.LEN, newFieldText(String.valueOf(61)))
        .withField(RT2FieldsEnum.IDC, newFieldText("1"))
        .withField(12, newFieldText("sans_accent"))
        .withField(13, newFieldText("€$"))
        .withField(14, newFieldText("éà"))
        .withField(15, newFieldText("慢"));
  }
}
