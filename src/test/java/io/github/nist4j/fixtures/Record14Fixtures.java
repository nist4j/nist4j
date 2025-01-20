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

import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.records.RT14VariableResolutionFingerprintNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.CharToByteArrayConverter;
import java.io.IOException;
import java.nio.charset.CharacterCodingException;
import java.util.Arrays;

public class Record14Fixtures {
  private static final int FAKE_BYTE_IMAGE = 3;

  public static NistRecordBuilder basicRecordWithLENChangeDigit() {
    byte[] expectedImage = getFakeImage(28);
    return new RT14VariableResolutionFingerprintNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(1)))
        .withField(IDC, newFieldText("IDC"))
        .withField(IMP, newFieldText("IMP"))
        .withField(FGP, newFieldText("FPG"))
        .withField(HLL, newFieldText("HVV"))
        .withField(VLL, newFieldText("VLL"))
        .withField(DATA, newFieldImage(expectedImage));
  }

  public static NistRecordBuilder record14Cas1_basic_Record() {
    return new RT14VariableResolutionFingerprintNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(432)))
        .withField(IDC, newFieldText("0"))
        .withField(IMP, newFieldText("0"))
        .withField(SRC, newFieldText("SRC"))
        .withField(FCD, newFieldText("20120730"))
        .withField(HLL, newFieldText("804"))
        .withField(VLL, newFieldText("1000"))
        .withField(SLC, newFieldText("2"))
        .withField(THPS, newFieldText("197"))
        .withField(TVPS, newFieldText("197"))
        .withField(CGA, newFieldText("WSQ20"))
        .withField(BPX, newFieldText("8"))
        .withField(FGP, newFieldText("1"))
        .withField(SHPS, newFieldText("197"))
        .withField(SVPS, newFieldText("197"))
        .withField(AMP, newFieldText("1" + SEP_US + "XX"))
        .withField(COM, newFieldText("Comment"))
        .withField(NQM, newFieldText("1" + SEP_US + "2"))
        .withField(SQM, newFieldText("1" + SEP_US + "0" + SEP_US + "0000" + SEP_US + "1"))
        .withField(FQM, newFieldText("1" + SEP_US + "0" + SEP_US + "0000" + SEP_US + "1"))
        .withField(
            ASEG,
            newFieldText(
                "1" + SEP_US + "3" + SEP_US + "0" + SEP_US + "0" + SEP_US + "35" + SEP_US + "55"
                    + SEP_US + "12" + SEP_US + "85"))
        .withField(SCF, newFieldText("1"))
        .withField(SIF, newFieldText("Y"))
        .withField(DMM, newFieldText("UNKNOWN"))
        .withField(FAP, newFieldText("10"))
        .withField(SUB, newFieldText("A"))
        .withField(DATA, newFieldImage(getFakeImage(64)));
  }

  public static NistRecordBuilder record14Cas2_EJI_Record() {
    return record14Cas1_basic_Record()
        .withField(FGP, newFieldText("19"))
        .withField(PPD, newFieldText("1" + SEP_US + "EJI"))
        .withField(
            PPC,
            newFieldText(
                "FV1" + SEP_US + "NA" + SEP_US + "100" + SEP_US + "101" + SEP_US + "100" + SEP_US
                    + "101"));
  }

  public static NistRecordBuilder record14Cas3_fingers_combination_Record() {
    return record14Cas1_basic_Record()
        .withField(FGP, newFieldText("14"))
        .withField(
            SEG, newFieldText("1" + SEP_US + "12" + SEP_US + "14" + SEP_US + "12" + SEP_US + "14"))
        .withField(SQM, newFieldText("1" + SEP_US + "0" + SEP_US + "0000" + SEP_US + "1"))
        .withField(
            ASEG,
            newFieldText(
                "1" + SEP_US + "3" + SEP_US + "0" + SEP_US + "0" + SEP_US + "100" + SEP_US + "104"
                    + SEP_US + "355" + SEP_US + "600"))
        .withField(SIF, newFieldText("Y"));
  }

  public static NistRecordBuilder record14Cas4_amputed_finger_Record() {
    return new RT14VariableResolutionFingerprintNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(1)))
        .withField(IDC, newFieldText("0"))
        .withField(SRC, newFieldText("SRC"))
        .withField(FCD, newFieldText("20120730"))
        .withField(FGP, newFieldText("1"))
        .withField(AMP, newFieldText("1" + SEP_US + "UP"));
  }

  public static NistRecordBuilder record14Cas2_with_real_image_WSQ_Record() throws IOException {
    return new RT14VariableResolutionFingerprintNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(138)))
        .withField(IDC, newFieldText("1"))
        .withField(IMP, newFieldText("3"))
        .withField(FGP, newFieldText("1"))
        .withField(SRC, newFieldText("TEST"))
        .withField(HLL, newFieldText("804"))
        .withField(VLL, newFieldText("752"))
        .withField(THPS, newFieldText("197"))
        .withField(TVPS, newFieldText("197"))
        .withField(CGA, newFieldText("WSQ20"))
        .withField(BPX, newFieldText("8"))
        .withField(NQM, newFieldText("8"))
        .withField(DATA, newFieldImage(ImageFixtures.fingerPrintImageWSQ()));
  }

  public static NistRecordBuilder record14Cas3_with_real_image_PNG_Record() throws IOException {
    return new RT14VariableResolutionFingerprintNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(138)))
        .withField(IDC, newFieldText("2"))
        .withField(IMP, newFieldText("3"))
        .withField(FGP, newFieldText("2"))
        .withField(SRC, newFieldText("TEST"))
        .withField(HLL, newFieldText("804"))
        .withField(VLL, newFieldText("752"))
        .withField(THPS, newFieldText("197"))
        .withField(TVPS, newFieldText("197"))
        .withField(CGA, newFieldText("PNG"))
        .withField(BPX, newFieldText("8"))
        .withField(NQM, newFieldText("8"))
        .withField(DATA, newFieldImage(ImageFixtures.nist4jLogo()));
  }

  public static byte[] record14Cas2_EJI_Binary() throws CharacterCodingException {
    String prefix =
        "14.001:43214.002:014.003:014.004:SRC14.005:2012073014.006:80414.007:100014.008:214.009:197"
            + "14.010:19714.011:WSQ2014.012:814.013:1914.014:1\u001FEJI14.015:FV1\u001FNA\u001F100\u001F101\u001F100\u001F101"
            + "14.016:19714.017:19714.018:1\u001FXX14.020:Comment14.022:1\u001F214.023:1\u001F0\u001F0000\u001F114.024:1\u001F0\u001F0000\u001F1"
            + "14.025:1\u001F3\u001F0\u001F0\u001F35\u001F55\u001F12\u001F8514.026:114.027:Y14.030:UNKNOWN14.031:10"
            + "14.046:A"
            + "14.999:";
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
