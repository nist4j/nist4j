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

import static io.github.nist4j.enums.records.RT1FieldsEnum.CNT;
import static io.github.nist4j.enums.records.RT1FieldsEnum.DAI;
import static io.github.nist4j.enums.records.RT1FieldsEnum.ORI;
import static io.github.nist4j.enums.records.RT1FieldsEnum.VER;
import static io.github.nist4j.enums.records.RT2FieldsEnum.IDC;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromPairs;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT5FieldsEnum;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT2UserDefinedDescriptionTextNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.builders.records.RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Match sample /references/type-5.an2
 */
public class SampleType5Fixtures {

  public static NistRecordBuilder createRecord1() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(1, newFieldText(170))
        .withField(VER, newFieldText("0400"))
        .withField(
            CNT,
            newSubfieldsFromPairs(
                asList(Pair.of("1", "2"), Pair.of("2", "00"), Pair.of("5", "01")))) // 12200501
        .withField(4, newFieldText("CAR"))
        .withField(5, newFieldText("20090728"))
        .withField(DAI, newFieldText("DAI000000"))
        .withField(ORI, newFieldText("MDNISTIMG"))
        .withField(9, newFieldText("jck t5"))
        .withField(11, newFieldText("19.69"))
        .withField(12, newFieldText("09.84"))
        .withField(13, newFieldText("NORAM" + NistDecoderHelper.SEP_US))
        .withField(14, newFieldText("20090728120000Z"));
  }

  public static NistRecordBuilder createRecord2() {
    return new RT2UserDefinedDescriptionTextNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(1, newFieldText(57))
        .withField(2, newFieldText("00"))
        .withField(3, newFieldText("domain defined text place holder"));
  }

  public static NistRecordBuilder createRecord5() throws IOException {
    return new RT5LowResolutionBinaryFingerprintNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(RT5FieldsEnum.LEN, newFieldText(13))
        .withField(RT5FieldsEnum.DATA, newFieldImage(ImageFixtures.fingerPrintImagePNG()));
  }

  public static NistFile createNistFile() throws IOException {
    List<Pair<String, String>> cnt =
        asList(Pair.of("1", "2"), Pair.of("2", "00"), Pair.of("5", "01"));
    NistRecord rt1 = createRecord1().withField(CNT, newSubfieldsFromPairs(cnt)).build();
    return NistFileFixtures.newNistFileBuilderDisableCalculation()
        .withRecord(RecordTypeEnum.RT1, rt1)
        .withRecord(RecordTypeEnum.RT2, createRecord2().withField(IDC, newFieldText("00")).build())
        .withRecord(RecordTypeEnum.RT5, createRecord5().withField(IDC, newFieldText("01")).build())
        .build();
  }

  public static String expectedRecord1() {
    return "1.001:1701.002:04001.003:122005011.004:CAR1.005:200907281.007:DAI0000001.008:MDNISTIMG1.009:jck t51.011:19.691.012:09.841.013:NORAM1.014:20090728120000Z";
  }

  public static String expectedRecord2() {
    return "2.001:57\u001D2.002:00\u001D2.003:domain defined text place holder";
  }

  public static Path referenceFile() {
    return getFileFromResource("/references/type-5.an2").toPath();
  }

  public static String referenceFileContent() throws IOException {
    return new String(Files.readAllBytes(referenceFile()), StandardCharsets.US_ASCII);
  }
}
