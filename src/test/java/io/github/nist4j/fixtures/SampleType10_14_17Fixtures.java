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

import static io.github.nist4j.enums.RecordTypeEnum.*;
import static io.github.nist4j.enums.records.RT1FieldsEnum.*;
import static io.github.nist4j.fixtures.NistFileFixtures.newNistFileBuilderDisableCalculation;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromPairs;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.records.RT1TransactionInformationNistRecordBuilderImpl;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Match sample /references/type-10-14-17-piv-index-iris.an2
 */
public class SampleType10_14_17Fixtures {

  public static NistRecordBuilder createRecord1() {
    return new RT1TransactionInformationNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(VER, newFieldText("0400"))
        .withField(
            CNT,
            newSubfieldsFromPairs(
                asList(
                    Pair.of("1", "5"),
                    Pair.of("10", "01"),
                    Pair.of("17", "02"),
                    Pair.of("14", "03"),
                    Pair.of("14", "04"))))
        .withField(4, newFieldText("CAR"))
        .withField(5, newFieldText("20090728"))
        .withField(DAI, newFieldText("DAI000000"))
        .withField(ORI, newFieldText("MDNISTIMG"))
        .withField(9, newFieldText("jck piv index iris"))
        .withField(11, newFieldText("00.00"))
        .withField(12, newFieldText("00.00"))
        .withField(13, newFieldText("NORAM" + NistDecoderHelper.SEP_US))
        .withField(14, newFieldText("20090728120000Z"));
  }

  public static NistRecordBuilder createRecord10() throws IOException {
    return newRecordBuilderDisableCalculation(10)
        .withField(1, newFieldText(68613))
        .withField(2, newFieldText("01"))
        .withField(3, newFieldText("FACE"))
        .withField(4, newFieldText("MDNISTIMG"))
        .withField(5, newFieldText("20000101"))
        .withField(6, newFieldText("480"))
        .withField(7, newFieldText("640"))
        .withField(8, newFieldText("0"))
        .withField(9, newFieldText("1"))
        .withField(10, newFieldText("1"))
        .withField(11, newFieldText("JPEGB"))
        .withField(12, newFieldText("SRGB"))
        .withField(13, newFieldText("15"))
        .withField(
            999,
            newFieldImage(
                Files.readAllBytes(
                    getFileFromResource("/fake/sample_10_14_17_type10.jpg").toPath())));
  }

  public static NistRecordBuilder createRecord17() throws IOException {
    return newRecordBuilderDisableCalculation(17)
        .withField(1, newFieldText(107132))
        .withField(2, newFieldText("02"))
        .withField(3, newFieldText("2"))
        .withField(4, newFieldText("MDNISTIMG"))
        .withField(5, newFieldText("20000101"))
        .withField(6, newFieldText("449"))
        .withField(7, newFieldText("312"))
        .withField(8, newFieldText("1"))
        .withField(9, newFieldText("1000"))
        .withField(10, newFieldText("1000"))
        .withField(11, newFieldText("PNG"))
        .withField(12, newFieldText("8"))
        .withField(13, newFieldText("GRAY"))
        .withField(
            999,
            newFieldImage(
                Files.readAllBytes(
                    getFileFromResource("/fake/sample_10_14_17_type17.png").toPath())));
  }

  public static NistFile createNistFile() throws IOException {
    return newNistFileBuilderDisableCalculation()
        .withRecord(
            RT1,
            createRecord1()
                .withField(
                    CNT,
                    newSubfieldsFromPairs(
                        asList(Pair.of("1", "5"), Pair.of("10", "01"), Pair.of("17", "02"))))
                .build())
        .withRecord(RT10, createRecord10().build())
        .withRecord(RT17, createRecord17().build())
        .build();
  }

  public static String expectedStartRecord10() {
    // Missing 999
    return "10.001:6861310.002:0110.003:FACE10.004:MDNISTIMG10.005:2000010110.006:48010.007:64010.008:010.009:110.010:110.011:JPEGB10.012:SRGB10.013:1510.999:";
  }

  public static String expectedStartRecord17() {
    // Missing 999
    return "17.001:107132\u001D17.002:0217.003:217.004:MDNISTIMG17.005:2000010117.006:44917.007:31217.008:117.009:100017.010:100017.011:PNG17.012:817.013:GRAY\u001D17.999:";
  }

  public static Path referenceFile() {
    return getFileFromResource("/references/type-10-14-17-piv-index-iris.an2").toPath();
  }

  public static String referenceFileContent() throws IOException {
    return new String(Files.readAllBytes(referenceFile()), StandardCharsets.US_ASCII);
  }
}
