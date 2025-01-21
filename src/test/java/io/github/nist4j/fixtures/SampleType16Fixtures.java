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
import static io.github.nist4j.enums.RecordTypeEnum.RT16;
import static io.github.nist4j.enums.records.RT1FieldsEnum.*;
import static io.github.nist4j.fixtures.NistFileFixtures.newNistFileBuilderDisableCalculation;
import static io.github.nist4j.fixtures.RecordFixtures.*;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
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
 * Match sample /references/rec01_rec02_rec16.nst.an2
 */
public class SampleType16Fixtures {

  public static NistRecordBuilder createRecord1() {
    List<Pair<String, String>> cnt =
        asList(Pair.of("1", "3"), Pair.of("2", "00"), Pair.of("16", "01"));
    return newRecordBuilderDisableCalculation(1)
        .withField(VER, newFieldText("0400"))
        .withField(CNT, newSubfieldsFromPairs(cnt))
        .withField(4, newFieldText("CAR"))
        .withField(5, newFieldText("20090728"))
        .withField(DAI, newFieldText("Wallace"))
        .withField(ORI, newFieldText("Gormit"))
        .withField(9, newFieldText("user-defined"))
        .withField(11, newFieldText("19.68"))
        .withField(12, newFieldText("19.68"))
        .withField(13, newFieldText("NORAM" + NistDecoderHelper.SEP_US))
        .withField(14, newFieldText("20090728120000Z"));
  }

  public static NistRecordBuilder createRecord16() {
    return newRecordBuilderDisableCalculation(16)
        .withField(1, newFieldText(4733))
        .withField(2, newFieldText("01"))
        .withField(3, newFieldText("WallaceGromitMcGraw"))
        .withField(4, newFieldText("ShaunPrestonPiella Backleicht"))
        .withField(5, newFieldText("single value"))
        .withField(6, newFieldText("243"))
        .withField(7, newFieldText("24"))
        .withField(8, newFieldText("1"))
        .withField(9, newFieldText("1"))
        .withField(10, newFieldText("1"))
        .withField(11, newFieldText("JPEGB"))
        .withField(12, newFieldText("24"))
        .withField(13, newFieldText("A1B1C1A2B2C2A3B3C3"));
  }

  public static NistFile createNistFile() {
    NistRecord rt1 =
        createRecord1()
            .withField(CNT, newSubfieldsFromPairs(asList(Pair.of("1", "5"), Pair.of("16", "01"))))
            .build();
    return newNistFileBuilderDisableCalculation()
        .withRecord(RT1, rt1)
        .withRecord(RT16, createRecord16().build())
        .build();
  }

  public static String expectedStartRecord16() {
    // Missing 999
    return "16.002:0116.003:WallaceGromitMcGraw16.004:ShaunPrestonPiella Backleicht16.005:single value16.006:24316.007:2416.008:116.009:116.010:116.011:JPEGB16.012:2416.013:A1B1C1A2B2C2A3B3C3";
  }

  public static Path referenceFile() {
    return getFileFromResource("/references/rec01_rec02_rec16.nst.an2").toPath();
  }

  public static String referenceFileContent() throws IOException {
    return new String(Files.readAllBytes(referenceFile()), StandardCharsets.US_ASCII);
  }
}
