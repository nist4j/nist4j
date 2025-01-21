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

import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newSubfieldsFromPairs;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.use_cases.helpers.builders.records.RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl;
import java.io.IOException;
import java.util.Collections;
import org.apache.commons.lang3.tuple.Pair;

public class Record4Fixtures {

  public static NistRecordBuilder record4Cas2_with_real_image_WSQ_Record1() throws IOException {
    return new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(
            OPTIONS_CALCULATE_ON_BUILD)
        .withField(GenericImageTypeEnum.LEN, newFieldText(String.valueOf(1)))
        .withField(GenericImageTypeEnum.IDC, newFieldText("1"))
        .withField(GenericImageTypeEnum.IMP, newFieldText("2"))
        .withField(
            GenericImageTypeEnum.FGP,
            newSubfieldsFromPairs(Collections.singletonList(Pair.of("1", "255"))))
        .withField(GenericImageTypeEnum.ISR, newFieldText("0"))
        .withField(GenericImageTypeEnum.HLL, newFieldText("545"))
        .withField(GenericImageTypeEnum.VLL, newFieldText("622"))
        .withField(GenericImageTypeEnum.GCA, newFieldText("1"))
        .withField(GenericImageTypeEnum.DATA, newFieldImage(ImageFixtures.fingerPrintImageWSQ()));
  }

  public static NistRecordBuilder record4Cas3_with_real_image_WSQ_Record2() throws IOException {
    return new RT4HighResolutionGreyscaleFingerprintNistRecordBuilderImpl(
            OPTIONS_CALCULATE_ON_BUILD)
        .withField(GenericImageTypeEnum.LEN, newFieldText(String.valueOf(1)))
        .withField(GenericImageTypeEnum.IDC, newFieldText("2"))
        .withField(GenericImageTypeEnum.IMP, newFieldText("2"))
        .withField(
            GenericImageTypeEnum.FGP,
            newSubfieldsFromPairs(Collections.singletonList(Pair.of("11", "255"))))
        .withField(GenericImageTypeEnum.ISR, newFieldText("0"))
        .withField(GenericImageTypeEnum.HLL, newFieldText("545"))
        .withField(GenericImageTypeEnum.VLL, newFieldText("622"))
        .withField(GenericImageTypeEnum.GCA, newFieldText("1"))
        .withField(GenericImageTypeEnum.DATA, newFieldImage(ImageFixtures.fingerPrintImageWSQ()));
  }
}
