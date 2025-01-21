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
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.CharToByteArrayConverter;
import java.nio.charset.CharacterCodingException;

public class Record1Fixtures {

  public static NistRecordBuilder record1Cas2_with_size_limit() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT1FieldsEnum.LEN, newFieldText(String.valueOf(1)))
        .withField(RT1FieldsEnum.CNT, newFieldText(""))
        .withField(RT1FieldsEnum.VER, newFieldText("1"))
        .withField(RT1FieldsEnum.ORI, newFieldText("ORI1234567890"))
        .withField(RT1FieldsEnum.DAI, newFieldText("DAI12345678901234567890"))
        .withField(RT1FieldsEnum.DOM, newFieldText("DOM12345678901234567890"));
  }

  public static NistRecordBuilder record1Cas1_basic_Record() {
    return record1Cas1_basic_Record_withVersion("1");
  }

  public static NistRecordBuilder record1Cas1_basic_Record_withVersion(String version) {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT1FieldsEnum.LEN, newFieldText(String.valueOf(1)))
        .withField(RT1FieldsEnum.VER, newFieldText(version))
        .withField(RT1FieldsEnum.CNT, newFieldText("1\u001F2\u001E2\u001F0\u001E5\u001F1"))
        .withField(RT1FieldsEnum.TOT, newFieldText("AMN"))
        .withField(RT1FieldsEnum.DAT, newFieldText("20091117"))
        .withField(RT1FieldsEnum.DAI, newFieldText("DAI000000"))
        .withField(RT1FieldsEnum.ORI, newFieldText("MDNISTIMG"))
        .withField(RT1FieldsEnum.TCN, newFieldText("jck t4 and t14 slaps"))
        .withField(RT1FieldsEnum.NSR, newFieldText("00.00"))
        .withField(RT1FieldsEnum.NTR, newFieldText("00.00"))
        .withField(RT1FieldsEnum.DOM, newFieldText("NORAM\u001F1"))
        .withField(RT1FieldsEnum.GMT, newFieldText("20091117124523Z"));
  }

  public static NistRecordBuilder record1Cas3_full_Record() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT1FieldsEnum.LEN, newFieldText("232"))
        .withField(RT1FieldsEnum.VER, newFieldText("0400"))
        .withField(
            RT1FieldsEnum.CNT,
            newFieldText("1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03"))
        .withField(RT1FieldsEnum.TOT, newFieldText("AMN"))
        .withField(RT1FieldsEnum.DAT, newFieldText("20091117"))
        .withField(RT1FieldsEnum.DAI, newFieldText("DAI000000"))
        .withField(RT1FieldsEnum.ORI, newFieldText("MDNISTIMG"))
        .withField(RT1FieldsEnum.TCN, newFieldText("jck t4 and t14 slaps"))
        .withField(RT1FieldsEnum.NSR, newFieldText("19.69"))
        .withField(RT1FieldsEnum.NTR, newFieldText("19.69"))
        .withField(RT1FieldsEnum.DOM, newFieldText("NORAM\u001F1"))
        .withField(RT1FieldsEnum.GMT, newFieldText("20091117124523Z"))
        .withField(RT1FieldsEnum.ANM, newFieldText("DAI000000\u001FMDNISTIMG"))
        .withField(RT1FieldsEnum.GNS, newFieldText("ISO"));
  }

  public static NistRecordBuilder record1Cas3_lecture_nistviewer_Record() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT1FieldsEnum.LEN, newFieldText("232"))
        .withField(RT1FieldsEnum.VER, newFieldText("0400"))
        .withField(
            RT1FieldsEnum.CNT,
            newFieldText("1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03"))
        .withField(RT1FieldsEnum.TOT, newFieldText("AMN"))
        .withField(RT1FieldsEnum.DAT, newFieldText("20091117"))
        .withField(RT1FieldsEnum.DAI, newFieldText("DAI000000"))
        .withField(RT1FieldsEnum.ORI, newFieldText("MDNISTIMG"))
        .withField(RT1FieldsEnum.TCN, newFieldText("jck t4 and t14 slaps"))
        .withField(RT1FieldsEnum.NSR, newFieldText("19.69"))
        .withField(RT1FieldsEnum.NTR, newFieldText("19.69"))
        .withField(RT1FieldsEnum.DOM, newFieldText("NORAM\u001F1"))
        .withField(RT1FieldsEnum.GMT, newFieldText("20091117124523Z"));
  }

  public static byte[] record1Cas3_full_Binary() throws CharacterCodingException {
    String record1Cas3 =
        "1.001:232\u001D"
            + "1.002:0400\u001D"
            + "1.003:1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03\u001D"
            + "1.004:AMN\u001D"
            + "1.005:20091117\u001D"
            + "1.007:DAI000000\u001D"
            + "1.008:MDNISTIMG\u001D"
            + "1.009:jck t4 and t14 slaps\u001D"
            + "1.011:19.69\u001D"
            + "1.012:19.69\u001D"
            + "1.013:NORAM\u001F1\u001D"
            + "1.014:20091117124523Z\u001D"
            + "1.017:DAI000000\u001FMDNISTIMG\u001D"
            + "1.018:ISO"
            + "\u001C";
    return new CharToByteArrayConverter(OPTIONS_DONT_CHANGE_ON_BUILD)
        .toByteArray(record1Cas3.toCharArray());
  }

  public static byte[] record1Cas4_encoding_UTF8_Binary() throws CharacterCodingException {
    String record1Cas3 =
        "1.001:195\u001D"
            + "1.002:0400\u001D"
            + "1.003:1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03\u001D"
            + "1.004:AMN\u001D"
            + "1.005:20091117\u001D"
            + "1.007:DAI000000\u001D"
            + "1.008:MDNISTIMG\u001D"
            + "1.009:jck t4 and t14 slaps\u001D"
            + "1.011:19.69\u001D"
            + "1.012:19.69\u001D"
            + "1.013:NORAM\u001F\u001D"
            + "1.014:20091117124523Z\u001D"
            + "1.015:003\u001C";
    return new CharToByteArrayConverter(OPTIONS_DONT_CHANGE_ON_BUILD)
        .toByteArray(record1Cas3.toCharArray());
  }

  public static NistRecordBuilder record1Cas4_encoding_UTF8_Record() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT1FieldsEnum.LEN, newFieldText("195"))
        .withField(2, newFieldText("0400"))
        .withField(
            3,
            newFieldText("1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03"))
        .withField(4, newFieldText("AMN"))
        .withField(5, newFieldText("20091117"))
        .withField(7, newFieldText("DAI000000"))
        .withField(8, newFieldText("MDNISTIMG"))
        .withField(9, newFieldText("jck t4 and t14 slaps"))
        .withField(11, newFieldText("19.69"))
        .withField(12, newFieldText("19.69"))
        .withField(13, newFieldText("NORAM\u001F"))
        .withField(14, newFieldText("20091117124523Z"))
        .withField(15, newFieldText("003"));
  }

  public static byte[] record1Cas4_encoding_UTF16_Binary() throws CharacterCodingException {
    String record1Cas3 =
        "1.001:195\u001D"
            + "1.002:0400\u001D"
            + "1.003:1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03\u001D"
            + "1.004:AMN\u001D"
            + "1.005:20091117\u001D"
            + "1.007:DAI000000\u001D"
            + "1.008:MDNISTIMG\u001D"
            + "1.009:jck t4 and t14 slaps\u001D"
            + "1.011:19.69\u001D"
            + "1.012:19.69\u001D"
            + "1.013:NORAM\u001F\u001D"
            + "1.014:20091117124523Z\u001D"
            + "1.015:002\u001C";
    return new CharToByteArrayConverter(OPTIONS_DONT_CHANGE_ON_BUILD)
        .toByteArray(record1Cas3.toCharArray());
  }

  public static NistRecordBuilder record1Cas4_encoding_UTF16_Record() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT1FieldsEnum.LEN, newFieldText("195"))
        .withField(2, newFieldText("0400"))
        .withField(
            3,
            newFieldText("1\u001F4\u001E2\u001F00\u001E4\u001F01\u001E4\u001F02\u001E14\u001F03"))
        .withField(4, newFieldText("AMN"))
        .withField(5, newFieldText("20091117"))
        .withField(7, newFieldText("DAI000000"))
        .withField(8, newFieldText("MDNISTIMG"))
        .withField(9, newFieldText("jck t4 and t14 slaps"))
        .withField(11, newFieldText("19.69"))
        .withField(12, newFieldText("19.69"))
        .withField(13, newFieldText("NORAM\u001F"))
        .withField(14, newFieldText("20091117124523Z"))
        .withField(15, newFieldText("002"));
  }
}
