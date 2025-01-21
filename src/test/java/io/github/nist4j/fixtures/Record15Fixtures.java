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

import static io.github.nist4j.enums.records.RT15FieldsEnum.*;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.records.RT15PalmNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.CharToByteArrayConverter;
import java.nio.charset.CharacterCodingException;
import java.util.Arrays;

public class Record15Fixtures {
  private static final int FAKE_BYTE_IMAGE = 3;

  public static NistRecordBuilder record15Cas1_basic_Record() {
    return new RT15PalmNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(292)))
        .withField(IDC, newFieldText("0"))
        .withField(IMP, newFieldText("0"))
        .withField(SRC, newFieldText("SRC"))
        .withField(PCD, newFieldText("20120730"))
        .withField(HLL, newFieldText("804"))
        .withField(VLL, newFieldText("1000"))
        .withField(SLC, newFieldText("2"))
        .withField(THPS, newFieldText("197"))
        .withField(TVPS, newFieldText("197"))
        .withField(CGA, newFieldText("WSQ20"))
        .withField(BPX, newFieldText("8"))
        .withField(FGP, newFieldText("21"))
        .withField(SHPS, newFieldText("197"))
        .withField(SVPS, newFieldText("197"))
        .withField(AMP, newFieldText("21" + SEP_US + "XX"))
        .withField(COM, newFieldText("Comment"))
        .withField(PQM, newFieldText("21" + SEP_US + "0" + SEP_US + "0000" + SEP_US + "1"))
        .withField(SUB, newFieldText("A"))
        .withField(DATA, newFieldImage(getFakeImage(64)));
  }

  public static byte[] record15Cas1_basic_Record_Binary() throws CharacterCodingException {
    String prefix =
        "15.001:29215.002:015.003:015.004:SRC15.005:2012073015.006:80415.007:100015.008:2"
            + "15.009:19715.010:197\u001D15.011:WSQ2015.012:815.013:21"
            + "15.016:19715.017:19715.018:21\u001FXX15.020:Comment15.024:21\u001F0\u001F0000\u001F1"
            + "15.046:A"
            + "15.999:";
    byte[] imageBinary = getFakeImage(64);
    byte[] prefixBytesArray =
        new CharToByteArrayConverter(OPTIONS_DONT_CHANGE_ON_BUILD)
            .toByteArray(prefix.toCharArray());
    byte[] buffer = new byte[prefixBytesArray.length + imageBinary.length + 1];
    System.arraycopy(prefixBytesArray, 0, buffer, 0, prefixBytesArray.length);
    System.arraycopy(imageBinary, 0, buffer, prefixBytesArray.length, imageBinary.length);
    buffer[buffer.length - 1] = (byte) NistDecoderHelper.SEP_FS;
    return buffer;
  }

  public static byte[] getFakeImage(int totalLen) {
    byte[] image = new byte[totalLen];
    Arrays.fill(image, (byte) FAKE_BYTE_IMAGE);
    return image;
  }
}
