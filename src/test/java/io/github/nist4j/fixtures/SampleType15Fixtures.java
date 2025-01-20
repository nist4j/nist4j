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

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.RecordTypeEnum.RT15;
import static io.github.nist4j.enums.records.RT1FieldsEnum.*;
import static io.github.nist4j.fixtures.NistFileFixtures.newNistFileBuilderDisableCalculation;
import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromPairs;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Match sample /references/type-15-palms.an2
 */
public class SampleType15Fixtures {

  public static NistRecordBuilder createRecord1() {
    List<Pair<String, String>> cnt =
        asList(Pair.of("1", "3"), Pair.of("2", "00"), Pair.of("15", "01"), Pair.of("15", "02"));
    return newRecordBuilderDisableCalculation(1)
        .withField(VER, newFieldText("0400"))
        .withField(CNT, newSubfieldsFromPairs(cnt))
        .withField(4, newFieldText("CAR"))
        .withField(5, newFieldText("20090728"))
        .withField(DAI, newFieldText("DAI000000"))
        .withField(ORI, newFieldText("MDNISTIMG"))
        .withField(9, newFieldText("jck type 15 palms"))
        .withField(11, newFieldText("00.00"))
        .withField(12, newFieldText("00.00"))
        .withField(13, newFieldText("NORAM" + NistDecoderHelper.SEP_US))
        .withField(14, newFieldText("20090728120000Z"));
  }

  public static NistRecordBuilder createRecord15_1() throws IOException {
    return newRecordBuilderDisableCalculation(15)
        .withField(1, newFieldText(189626))
        .withField(2, newFieldText("01"))
        .withField(3, newFieldText("11"))
        .withField(4, newFieldText("MDNISTIMG"))
        .withField(5, newFieldText("20091117"))
        .withField(6, newFieldText("900"))
        .withField(7, newFieldText("2500"))
        .withField(8, newFieldText("1"))
        .withField(9, newFieldText("500"))
        .withField(10, newFieldText("500"))
        .withField(11, newFieldText("WSQ20"))
        .withField(12, newFieldText("8"))
        .withField(13, newFieldText("24"))
        .withField(
            999,
            newFieldImage(
                Files.readAllBytes(getFileFromResource("/fake/sample_15_type15_1.wsq").toPath())));
  }

  public static NistRecordBuilder createRecord15_2() {
    // Missing 999
    return newRecordBuilderDisableCalculation(15)
        .withField(1, newFieldText(2354642))
        .withField(2, newFieldText("02"))
        .withField(3, newFieldText("11"))
        .withField(4, newFieldText("MDNISTIMG"))
        .withField(5, newFieldText("20091117"))
        .withField(6, newFieldText("2750"))
        .withField(7, newFieldText("2750"))
        .withField(8, newFieldText("1"))
        .withField(9, newFieldText("1000"))
        .withField(10, newFieldText("1000"))
        .withField(11, newFieldText("JP2L"))
        .withField(12, newFieldText("8"))
        .withField(13, newFieldText("27"));
  }

  public static NistFile createNistFile() throws IOException {
    List<Pair<String, String>> cnt =
        asList(Pair.of("1", "5"), Pair.of("15", "01"), Pair.of("15", "02"));
    NistRecord rt1 = createRecord1().withField(CNT, newSubfieldsFromPairs(cnt)).build();
    return newNistFileBuilderDisableCalculation()
        .withRecord(RT1, rt1)
        .withRecord(RT15, createRecord15_1().build())
        .withRecord(RT15, createRecord15_2().build())
        .build();
  }

  public static String expectedStartRecord15_1() {
    // Missing 999
    return "15.001:18962615.002:0115.003:1115.004:MDNISTIMG15.005:2009111715.006:90015.007:250015.008:115.009:50015.010:50015.011:WSQ2015.012:815.013:2415.999:";
  }

  public static String expectedStartRecord15_2() {
    // Missing 999
    return "15.002:0215.003:1115.004:MDNISTIMG15.005:2009111715.006:275015.007:275015.008:115.009:100015.010:100015.011:JP2L15.012:815.013:27";
  }

  public static Path referenceFile() {
    return getFileFromResource("/references/type-15-palms.an2").toPath();
  }

  public static String referenceFileContent() throws IOException {
    return new String(Files.readAllBytes(referenceFile()), StandardCharsets.US_ASCII);
  }
}
