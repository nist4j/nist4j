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

import static io.github.nist4j.enums.records.RT13FieldsEnum.ANN;
import static io.github.nist4j.enums.records.RT13FieldsEnum.ASC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.BPX;
import static io.github.nist4j.enums.records.RT13FieldsEnum.CGA;
import static io.github.nist4j.enums.records.RT13FieldsEnum.COM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.CON;
import static io.github.nist4j.enums.records.RT13FieldsEnum.DATA;
import static io.github.nist4j.enums.records.RT13FieldsEnum.DUI;
import static io.github.nist4j.enums.records.RT13FieldsEnum.EFR;
import static io.github.nist4j.enums.records.RT13FieldsEnum.FCT;
import static io.github.nist4j.enums.records.RT13FieldsEnum.FGP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.GEO;
import static io.github.nist4j.enums.records.RT13FieldsEnum.HAS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.HLL;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IDC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IMP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LCD;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LEN;
import static io.github.nist4j.enums.records.RT13FieldsEnum.LQM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.MMS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.PPC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.REM;
import static io.github.nist4j.enums.records.RT13FieldsEnum.RSP;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SAN;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SHPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SLC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SOR;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SPD;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SRC;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SUB;
import static io.github.nist4j.enums.records.RT13FieldsEnum.SVPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.THPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.TVPS;
import static io.github.nist4j.enums.records.RT13FieldsEnum.VLL;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_CALCULATE_ON_BUILD;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_DONT_CHANGE_ON_BUILD;
import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;

import io.github.nist4j.entities.record.NistRecordBuilder;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.builders.records.RT13LatentImageDataNistRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.converters.CharToByteArrayConverter;
import java.nio.charset.CharacterCodingException;
import java.nio.file.Path;
import java.util.Arrays;

public class Record13Fixtures {
  private static final int FAKE_BYTE_IMAGE = 3;

  public static NistRecordBuilder basicRecordWithLENChangeDigit() {
    byte[] expectedImage = getFakeImage(28);
    return new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(1)))
        .withField(IDC, newFieldText("IDC"))
        .withField(IMP, newFieldText("IMP"))
        .withField(FGP, newFieldText("FPG"))
        .withField(HLL, newFieldText("HVV"))
        .withField(VLL, newFieldText("VLL"))
        .withField(DATA, newFieldImage(expectedImage));
  }

  public static NistRecordBuilder record13Cas1_basic_Record() {
    return new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_DONT_CHANGE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(432)))
        .withField(IDC, newFieldText("0"))
        .withField(IMP, newFieldText("0"))
        .withField(SRC, newFieldText("SRC"))
        .withField(LCD, newFieldText("20120730"))
        .withField(HLL, newFieldText("804"))
        .withField(VLL, newFieldText("1000"))
        .withField(SLC, newFieldText("2"))
        .withField(THPS, newFieldText("197"))
        .withField(TVPS, newFieldText("197"))
        .withField(CGA, newFieldText("WSQ20"))
        .withField(BPX, newFieldText("8"))
        .withField(FGP, newFieldText("1"))
        .withField(SPD, newFieldText("1"))
        .withField(SHPS, newFieldText("197"))
        .withField(SVPS, newFieldText("197"))
        .withField(RSP, newFieldText("MM" + SEP_US + "1" + SEP_US + "2" + SEP_US + "3"))
        .withField(
            REM,
            newFieldText(
                "RULER" + SEP_US + "0.23" + SEP_US + "MM" + SEP_US + "1" + SEP_US + "2" + SEP_US
                    + "3" + SEP_US + "4"))
        .withField(COM, newFieldText("Comment"))
        .withField(LQM, newFieldText("1" + SEP_US + "1" + SEP_US + "0000" + SEP_US + "1"))
        .withField(SUB, newFieldText("1" + SEP_US + "0" + SEP_US + "0000" + SEP_US + "1"))
        .withField(CON, newFieldText("100"))
        .withField(FCT, newFieldText("18"))
        .withField(
            ANN, newFieldText("20120930145559Z" + SEP_US + "1" + SEP_US + "2" + SEP_US + "3"))
        .withField(DUI, newFieldText("M1234567891012"))
        .withField(MMS, newFieldText("1" + SEP_US + "2" + SEP_US + "3"))
        .withField(SAN, newFieldText("124"))
        .withField(EFR, newFieldText("199"))
        .withField(ASC, newFieldText("254" + SEP_US + "98"))
        .withField(HAS, newFieldText("64caracteres"))
        .withField(SOR, newFieldText("254" + SEP_US + "254"))
        .withField(
            GEO,
            newFieldText(
                "20120930145559Z"
                    + SEP_US
                    + "-89"
                    + SEP_US
                    + "60"
                    + SEP_US
                    + "60"
                    + SEP_US
                    + "-179"
                    + SEP_US
                    + "59"
                    + SEP_US
                    + "59"
                    + SEP_US
                    + "87654321"
                    + SEP_US
                    + "254"
                    + SEP_US
                    + "12A"
                    + SEP_US
                    + "1"
                    + SEP_US
                    + "1"
                    + SEP_US
                    + "AIRY"
                    + SEP_US
                    + "9"
                    + SEP_US
                    + "125"))
        .withField(DATA, newFieldImage(getFakeImage(64)));
  }

  public static NistRecordBuilder record13Cas2_EJI_Record() {
    return record13Cas1_basic_Record()
        .withField(FGP, newFieldText("19"))
        .withField(
            PPC,
            newFieldText(
                "FV1" + SEP_US + "NA" + SEP_US + "100" + SEP_US + "101" + SEP_US + "100" + SEP_US
                    + "101"));
  }

  public static NistRecordBuilder record13Cas3_len_calculate() {
    return new RT13LatentImageDataNistRecordBuilderImpl(OPTIONS_CALCULATE_ON_BUILD)
        .withField(LEN, newFieldText(String.valueOf(432)))
        .withField(IDC, newFieldText("0"))
        .withField(IMP, newFieldText("0"))
        .withField(SRC, newFieldText("SRC"))
        .withField(LCD, newFieldText("20120730"))
        .withField(HLL, newFieldText("804"))
        .withField(VLL, newFieldText("1000"))
        .withField(SLC, newFieldText("2"))
        .withField(THPS, newFieldText("197"))
        .withField(TVPS, newFieldText("197"))
        .withField(CGA, newFieldText("WSQ20"))
        .withField(BPX, newFieldText("8"))
        .withField(FGP, newFieldText("1"))
        .withField(SPD, newFieldText("1"))
        .withField(PPC, newFieldText("1"))
        .withField(SHPS, newFieldText("197"))
        .withField(SVPS, newFieldText("197"))
        .withField(RSP, newFieldText("MM" + SEP_US + "1" + SEP_US + "2" + SEP_US + "3"))
        .withField(
            REM,
            newFieldText(
                "RULER" + SEP_US + "0.23" + SEP_US + "MM" + SEP_US + "1" + SEP_US + "2" + SEP_US
                    + "3" + SEP_US + "4"))
        .withField(COM, newFieldText("Comment"))
        .withField(LQM, newFieldText("1" + SEP_US + "2"))
        .withField(SUB, newFieldText("1" + SEP_US + "0" + SEP_US + "0000" + SEP_US + "1"))
        .withField(CON, newFieldText("100"))
        .withField(FCT, newFieldText("18"))
        .withField(
            ANN, newFieldText("20120930145559Z" + SEP_US + "1" + SEP_US + "2" + SEP_US + "3"))
        .withField(DUI, newFieldText("M1234567891012"))
        .withField(MMS, newFieldText("1" + SEP_US + "2" + SEP_US + "3"))
        .withField(SAN, newFieldText("124"))
        .withField(EFR, newFieldText("199"))
        .withField(ASC, newFieldText("254" + SEP_US + "98"))
        .withField(HAS, newFieldText("64caracteres"))
        .withField(SOR, newFieldText("254" + SEP_US + "254"))
        .withField(DATA, newFieldImage(getFakeImage(64)));
  }

  public static byte[] record13Cas2_EJI_Binary() throws CharacterCodingException {
    String prefix =
        "13.001:43213.002:013.003:013.004:SRC13.005:2012073013.006:80413.007:100013.008:213.009:197"
            + "13.010:19713.011:WSQ2013.012:813.013:1913.014:113.015:FV1\u001FNA\u001F100\u001F101\u001F100\u001F10113.016:19713.017:19713.018:MM\u001F1\u001F2\u001F313.019:RULER\u001F0.23\u001FMM\u001F1\u001F2\u001F3\u001F4"
            + "13.020:Comment13.024:1\u001F1\u001F0000\u001F1"
            + "13.046:1\u001F0\u001F0000\u001F113.047:100"
            + "13.901:1813.902:20120930145559Z\u001F1\u001F2\u001F313.903:M123456789101213.904:1\u001F2\u001F3"
            + "13.993:12413.994:19913.995:254\u001F9813.996:64caracteres13.997:254\u001F25413.998:20120930145559Z\u001F-89\u001F60\u001F60\u001F-179\u001F59\u001F59\u001F87654321\u001F254\u001F12A\u001F1\u001F1\u001FAIRY\u001F9\u001F125"
            + "13.999:";
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

  public static Path referenceFailFile() {
    return getFileFromResource(
            "/standards/AN2011/Traditional_Encoding/fail_validator/fail-type-13-L1.an2")
        .toPath();
  }
}
