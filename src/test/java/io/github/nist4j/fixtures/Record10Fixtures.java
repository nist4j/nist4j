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

import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.builders.records.RT10FacialSMTImageNistRecordBuilderImpl;
import java.util.Arrays;

public class Record10Fixtures {
  private static final int FAKE_BYTE_IMAGE = 3;

  public static NistRecordBuilder basicRecordWithLENChangeDigit() {
    byte[] expectedImage = getFakeImage(28);
    return new RT10FacialSMTImageNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(1)))
        .withField(IDC, newFieldText("23"))
        .withField(IMT, newFieldText("FACE"))
        .withField(SRC, newFieldText("MY AGENCY COUNTRY 001"))
        .withField(PHD, newFieldText("20250102"))
        .withField(HLL, newFieldText("202"))
        .withField(VLL, newFieldText("250"))
        .withField(SLC, newFieldText("1"))
        .withField(HPS_LEGACY, newFieldText("300"))
        .withField(VPS_LEGACY, newFieldText("300"))
        .withField(CGA, newFieldText("JPEGB"))
        .withField(CSP, newFieldText("RGB"))
        .withField(SAP, newFieldText("0"))
        .withField(SMT, newFieldText("TATTOO"))
        .withField(DATA, newFieldImage(expectedImage));
  }

  public static NistRecordBuilder recordWithMissingMandatoryFields() {
    return new RT10FacialSMTImageNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(1)));
  }

  public static byte[] getFakeImage(int totalLen) {
    byte[] image = new byte[totalLen];
    Arrays.fill(image, (byte) FAKE_BYTE_IMAGE);
    return image;
  }
}
