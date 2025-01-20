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
package io.github.nist4j.test_utils;

import static io.github.nist4j.enums.records.RT1FieldsEnum.*;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.GenericImageTypeEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import io.github.nist4j.enums.records.RT7FieldsEnum;
import io.github.nist4j.enums.records.RT8FieldsEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.NonNull;
import org.jnbis.api.model.Nist;
import org.jnbis.api.model.record.*;

public class AssertJnbisNist {

  private final NistFile nistFile;

  public AssertJnbisNist(NistFile nistFile) {
    this.nistFile = nistFile;
  }

  public static AssertJnbisNist assertThatNist(@NonNull NistFile nistFile) {
    return new AssertJnbisNist(nistFile);
  }

  public AssertJnbisNist isEqualsTo(Nist expectedNist) {
    this.hasTheSameJnbisRecord1(expectedNist);
    this.hasTheSameJnbisRecords2(expectedNist);
    this.hasTheSameJnbisRecords3(expectedNist);
    this.hasTheSameJnbisRecords4(expectedNist);
    this.hasTheSameJnbisRecords5(expectedNist);
    this.hasTheSameJnbisRecords6(expectedNist);
    this.hasTheSameJnbisRecords7(expectedNist);
    this.hasTheSameJnbisRecords8(expectedNist);
    this.hasTheSameJnbisRecords9(expectedNist);
    this.hasTheSameJnbisRecords14(expectedNist);
    return this;
  }

  public void hasTheSameJnbisRecord1(Nist nist) { // R1
    NistRecord result = nistFile.getRT1TransactionInformationRecord();
    TransactionInformation expected = nist.getTransactionInfo();

    assertThat(expected.getVersion())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), VER))
        .isEqualTo(result.getFieldText(VER).orElse(null));
    assertThat(
            Integer.parseInt(expected.getLogicalRecordLength())
                - result.getFieldAsInt(LEN).orElse(-99))
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), LEN))
        .isLessThanOrEqualTo(1);
    assertThat(expected.getFileContent())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), CNT))
        .isEqualTo(result.getFieldText(CNT).orElse(null));
    assertThat(expected.getTypeOfTransaction())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), TOT))
        .isEqualTo(result.getFieldText(TOT).orElse(null));
    assertThat(expected.getDate())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), DAT))
        .isEqualTo(result.getFieldText(DAT).orElse(null));
    assertThat(expected.getPriority())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), PRY))
        .isEqualTo(result.getFieldText(PRY).orElse(null));
    assertThat(expected.getDestinationAgencyId())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), DAI))
        .isEqualTo(result.getFieldText(DAI).orElse(null));
    assertThat(expected.getOriginatingAgencyId())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), ORI))
        .isEqualTo(result.getFieldText(ORI).orElse(null));
    assertThat(expected.getControlNumber())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), TCN))
        .isEqualTo(result.getFieldText(TCN).orElse(null));
    assertThat(expected.getNativeScanningResolution())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), NSR))
        .isEqualTo(result.getFieldText(NSR).orElse(null));
    assertThat(expected.getNominalTransmittingResolution())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), NTR))
        .isEqualTo(result.getFieldText(NTR).orElse(null));
    assertThat(expected.getDomainName())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), DOM))
        .isEqualTo(result.getFieldText(DOM).orElse(null));
    assertThat(expected.getGreenwichMeanTime())
        .withFailMessage(String.format("check result %s field %s", result.getRecordId(), GMT))
        .isEqualTo(result.getFieldText(GMT).orElse(null));
  }

  public void hasTheSameJnbisRecords2(Nist nist) { // R2
    List<NistRecord> results = nistFile.getRT2UserDefinedDescriptionTextRecords();
    List<UserDefinedDescriptiveText> expectedList = nist.getUserDefinedTexts();

    if (isNotEmpty(expectedList)) {
      assertThat(results).isNotEmpty();
      assertThat(results.size()).isEqualTo(expectedList.size());
      for (int i = 0; i < expectedList.size(); i++) {
        NistRecord result = results.get(i);
        UserDefinedDescriptiveText expected = expectedList.get(i);

        for (Map.Entry<Integer, String> entry : expected.getUserDefinedFields().entrySet()) {
          if (entry.getKey() != 1) { // Do not check the LEN because it can change
            assertThat(entry.getValue())
                .isEqualTo(result.getFieldText(entry.getKey()).orElse(null))
                .as("check result %s field %s", result.getRecordId(), entry.getKey());
          }
        }
      }
    }
  }

  public void hasTheSameJnbisRecords3(Nist expectedNist) { // R3
    List<LowResolutionGrayscaleFingerprint> expectedListL =
        expectedNist.getLowResGrayscaleFingerprints();
    List<NistRecord> resultList = nistFile.getRT3LowResolutionGrayscaleFingerprintRecords();
    if (isNotEmpty(expectedListL)) {
      assertThat(resultList).isNotEmpty();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        LowResolutionGrayscaleFingerprint expected = expectedListL.get(i);

        assertThat(result.getFieldText(GenericImageTypeEnum.LEN))
            .hasValue(expected.getLogicalRecordLength());
        /*Considering the NistViewer tool, Jnbis doesn't read well the record3
        assertThat(result.getFieldText(GenericImageTypeEnum.IDC)).isEqualTo(ofNullable(expected.getImageDesignationCharacter()));//1
        assertThat(result.getFieldText(GenericImageTypeEnum.HLL)).isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.VLL)).isEqualTo(ofNullable(expected.getVerticalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.GCA)).isEqualTo(ofNullable(expected.getCompressionAlgorithm()));
        assertThat(result.getFieldText(GenericImageTypeEnum.IMP)).isEqualTo(ofNullable(expected.getImpressionType()));
        assertThat(result.getFieldText(GenericImageTypeEnum.FGP)).isEqualTo(ofNullable(expected.getFingerPosition()));
        assertThat(result.getFieldText(GenericImageTypeEnum.ISR)).isEqualTo(ofNullable(expected.getImageScanningResolution()));
        */
        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(GenericImageTypeEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          assertThat(result.getFieldLength(GenericImageTypeEnum.DATA))
              .hasValue(expected.getImageData().length);
          assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
          assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }
      }
    }
  }

  public void hasTheSameJnbisRecords4(Nist expectedNist) { // R4
    List<HighResolutionGrayscaleFingerprint> expectedList =
        expectedNist.getHiResGrayscaleFingerprints();
    List<NistRecord> resultList = nistFile.getRT4HighResolutionGrayscaleFingerprintRecords();
    if (isNotEmpty(expectedList)) {
      assertThat(resultList).isNotEmpty();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        HighResolutionGrayscaleFingerprint expected = expectedList.get(i);

        assertThat(result.getFieldText(GenericImageTypeEnum.LEN))
            .hasValue(expected.getLogicalRecordLength());
        /*Jnbis does not read correctly IDC*/
        // assertThat(result.getFieldText(GenericImageTypeEnum.IDC)).isEqualTo(ofNullable(expected.getImageDesignationCharacter()));//1
        assertThat(result.getFieldText(GenericImageTypeEnum.HLL))
            .isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.VLL))
            .isEqualTo(ofNullable(expected.getVerticalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.GCA))
            .isEqualTo(ofNullable(expected.getCompressionAlgorithm()));
        assertThat(result.getFieldText(GenericImageTypeEnum.IMP))
            .isEqualTo(ofNullable(expected.getImpressionType()));

        if (expected.getFingerPosition() != null) {
          Optional<String> fieldText = result.getFieldText(GenericImageTypeEnum.FGP);
          assertThat(fieldText).isNotEmpty();
          assertThat(SubFieldToStringConverter.toItems(fieldText.get()))
              .contains(expected.getFingerPosition());
        }
        assertThat(result.getFieldText(GenericImageTypeEnum.ISR))
            .isEqualTo(ofNullable(expected.getImageScanningResolution()));

        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(GenericImageTypeEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          assertThat(result.getFieldLength(GenericImageTypeEnum.DATA))
              .hasValue(expected.getImageData().length);
          assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
          assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }
      }
    }
  }

  public void hasTheSameJnbisRecords5(Nist expectedNist) { // R5
    List<LowResolutionBinaryFingerprint> expectedList = expectedNist.getLowResBinaryFingerprints();
    List<NistRecord> resultList = nistFile.getRT5LowResolutionBinaryFingerprintRecords();
    if (isNotEmpty(resultList)) {
      assertThat(resultList).isNotNull();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        LowResolutionBinaryFingerprint expected = expectedList.get(i);
        /*Based on NistViewer tool, Jnbis doesn't read well RT5
        assertThat(result.getFieldText(GenericImageTypeEnum.IDC)).isEqualTo(ofNullable(expected.getImageDesignationCharacter()));//1
        assertThat(result.getFieldText(GenericImageTypeEnum.LEN)).isEqualTo(ofNullable(expected.getLogicalRecordLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.HLL)).isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.VLL)).isEqualTo(ofNullable(expected.getVerticalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.GCA)).isEqualTo(ofNullable(expected.getCompressionAlgorithm()));
        assertThat(result.getFieldText(GenericImageTypeEnum.IMP)).isEqualTo(ofNullable(expected.getImpressionType()));
        assertThat(result.getFieldText(GenericImageTypeEnum.FGP)).isEqualTo(ofNullable(expected.getFingerPosition()));
        assertThat(result.getFieldText(GenericImageTypeEnum.ISR)).isEqualTo(ofNullable(expected.getImageScanningResolution()));
        */

        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(GenericImageTypeEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          assertThat(result.getFieldLength(GenericImageTypeEnum.DATA))
              .hasValue(expected.getImageData().length);
          assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
          assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }
      }
    }
  }

  public void hasTheSameJnbisRecords6(Nist expectedNist) { // R6
    List<HighResolutionBinaryFingerprint> expectedList = expectedNist.getHiResBinaryFingerprints();
    List<NistRecord> resultList = nistFile.getRT6HighResolutionBinaryFingerprintRecords();
    if (isNotEmpty(resultList)) {
      assertThat(resultList).isNotNull();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        HighResolutionBinaryFingerprint expected = expectedList.get(i);
        /*Jnbis is not correctly read
        assertThat(result.getFieldText(GenericImageTypeEnum.IDC)).isEqualTo(ofNullable(expected.getImageDesignationCharacter()));
        assertThat(result.getFieldText(GenericImageTypeEnum.LEN)).isEqualTo(ofNullable(expected.getLogicalRecordLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.HLL)).isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.VLL)).isEqualTo(ofNullable(expected.getVerticalLineLength()));
        assertThat(result.getFieldText(GenericImageTypeEnum.GCA)).isEqualTo(ofNullable(expected.getCompressionAlgorithm()));
        assertThat(result.getFieldText(GenericImageTypeEnum.IMP)).isEqualTo(ofNullable(expected.getImpressionType()));
        assertThat(result.getFieldText(GenericImageTypeEnum.FGP)).isEqualTo(ofNullable(expected.getFingerPosition()));
        assertThat(result.getFieldText(GenericImageTypeEnum.ISR)).isEqualTo(ofNullable(expected.getImageScanningResolution()));
        */
        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(GenericImageTypeEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          assertThat(result.getFieldLength(GenericImageTypeEnum.DATA))
              .hasValue(expected.getImageData().length);
          assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
          assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }
      }
    }
  }

  public void hasTheSameJnbisRecords7(Nist expectedNist) { // R7
    List<UserDefinedImage> expectedList = expectedNist.getUserDefinedImages();
    List<NistRecord> resultList = nistFile.getRT7UserDefinedImageRecords();
    if (isNotEmpty(resultList)) {
      assertThat(resultList).isNotNull();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        UserDefinedImage expected = expectedList.get(i);
        /*Based on NistViewer tool, Jnbis doesn't read well this record type
        assertThat(result.getFieldText(UserDefinedImageRecord.UDIRFields.IDC)).isEqualTo(ofNullable(expected.getImageDesignationCharacter()));//1
        assertThat(result.getFieldText(UserDefinedImageRecord.UDIRFields.LEN)).isEqualTo(ofNullable(expected.getLogicalRecordLength()));
        assertThat(result.getFieldText(UserDefinedImageRecord.UDIRFields.HLL)).isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(UserDefinedImageRecord.UDIRFields.VLL)).isEqualTo(ofNullable(expected.getVerticalLineLength()));
        assertThat(result.getFieldText(UserDefinedImageRecord.UDIRFields.GCA)).isEqualTo(ofNullable(expected.getCompressionAlgorithm()));
        */

        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(RT7FieldsEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          /*Jnbis not correct
          assertThat(result.getFieldLength(UserDefinedImageRecord.UDIRFields.DATA)).hasValue(expected.getImageData().length);
          assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
          assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
          */
        }
      }
    }
  }

  public void hasTheSameJnbisRecords8(Nist expectedNist) { // R7
    List<SignatureImage> expectedList = expectedNist.getSignatures();
    List<NistRecord> resultList = nistFile.getRT8SignatureImageRecords();
    if (isNotEmpty(resultList)) {
      assertThat(resultList).isNotNull();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        SignatureImage expected = expectedList.get(i);
        assertThat(result.getFieldText(RT8FieldsEnum.IDC))
            .isEqualTo(ofNullable(expected.getImageDesignationCharacter()));
        assertThat(result.getFieldText(RT8FieldsEnum.LEN))
            .isEqualTo(ofNullable(expected.getLogicalRecordLength()));
        assertThat(result.getFieldText(RT8FieldsEnum.SIG))
            .isEqualTo(ofNullable(expected.getSignatureType()));
        assertThat(result.getFieldText(RT8FieldsEnum.SRT))
            .isEqualTo(ofNullable(expected.getSignatureRepresentationType()));
        assertThat(result.getFieldText(RT8FieldsEnum.ISR))
            .isEqualTo(ofNullable(expected.getImageScanningResolution()));
        assertThat(result.getFieldText(RT8FieldsEnum.HLL))
            .isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(RT8FieldsEnum.VLL))
            .isEqualTo(ofNullable(expected.getVerticalLineLength()));

        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(RT8FieldsEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          assertThat(result.getFieldLength(RT8FieldsEnum.DATA))
              .hasValue(expected.getImageData().length);
          assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
          assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }
      }
    }
  }

  public void hasTheSameJnbisRecords9(Nist expectedNist) { // R7
    List<MinutiaeData> expectedList = expectedNist.getMinutiaeData();
    List<NistRecord> resultList = nistFile.getRT9MinutiaeDataRecords();
    if (isNotEmpty(resultList)) {
      assertThat(resultList).isNotNull();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        MinutiaeData expected = expectedList.get(i);
        /*Jnbis cannot read integration file
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.IDC)).isEqualTo(ofNullable(expected.getImageDesignationCharacter()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.LEN)).isEqualTo(ofNullable(expected.getLogicalRecordLength()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.IMP)).isEqualTo(ofNullable(expected.getCorePosition()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.FMT)).isEqualTo(ofNullable(expected.getMinutiaeFormat()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.OFR)).isEqualTo(ofNullable(expected.getOriginatingFingerprintReadingSystem()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.FGP)).isEqualTo(ofNullable(expected.getFingerPosition()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.FPC)).isEqualTo(ofNullable(expected.getFingerprintPatternClassification()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.CRP)).isEqualTo(ofNullable(expected.getCorePosition()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.DLT)).isEqualTo(ofNullable(expected.getDeltaPosition()));
        assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.MIN)).isEqualTo(ofNullable(expected.getNumberOfMinutiae()));
        //assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.RDG)).isEqualTo(ofNullable(expected.)));
        //assertThat(result.getFieldText(MinutiaeDataRecord.MDRFields.MRC)).isEqualTo(ofNullable(expected.)));

        if (expected.getImageData() != null) {
            Optional<byte[]> resultImageData = result.getFieldImage(MinutiaeDataRecord.MDRFields.DATA);
            assertThat(resultImageData).isNotEmpty();
            assertThat(result.getFieldLength(MinutiaeDataRecord.MDRFields.DATA)).hasValue(expected.getImageData().length);
            assertThat(resultImageData.get().length).isEqualTo(expected.getImageData().length);
            assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }*/
      }
    }
  }

  public void hasTheSameJnbisRecords14(Nist expectedNist) { // R14
    List<VariableResolutionFingerprint> expectedList = expectedNist.getVariableResFingerprints();
    List<NistRecord> resultList = nistFile.getRT14VariableResolutionFingerprintRecords();
    if (isNotEmpty(resultList)) {
      assertThat(resultList).isNotNull();
      for (int i = 0; i < resultList.size(); i++) {
        NistRecord result = resultList.get(i);
        assertThat(result).isNotNull();
        VariableResolutionFingerprint expected = expectedList.get(i);
        assertThat(result.getFieldText(RT14FieldsEnum.IDC))
            .isEqualTo(ofNullable(expected.getImageDesignationCharacter())); // 1
        assertThat(result.getFieldText(RT14FieldsEnum.LEN))
            .isEqualTo(ofNullable(expected.getLogicalRecordLength()));
        assertThat(result.getFieldText(RT14FieldsEnum.HLL))
            .isEqualTo(ofNullable(expected.getHorizontalLineLength()));
        assertThat(result.getFieldText(RT14FieldsEnum.VLL))
            .isEqualTo(ofNullable(expected.getVerticalLineLength()));
        assertThat(result.getFieldText(RT14FieldsEnum.CGA))
            .isEqualTo(ofNullable(expected.getCompressionAlgorithm()));
        assertThat(result.getFieldText(RT14FieldsEnum.IMP))
            .isEqualTo(ofNullable(expected.getImpressionType()));
        assertThat(result.getFieldText(RT14FieldsEnum.FGP))
            .isEqualTo(ofNullable(expected.getFingerPosition()));

        if (expected.getImageData() != null) {
          Optional<byte[]> resultImageData = result.getFieldImage(RT14FieldsEnum.DATA);
          assertThat(resultImageData).isNotEmpty();
          // Jnbis has some len errors
          assertThat(resultImageData.get().length - expected.getImageData().length).isLessThan(2);
          // assertThat(resultImageData.get()).isEqualTo(expected.getImageData());
        }
      }
    }
  }
}
