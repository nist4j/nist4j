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
package io.github.nist4j.use_cases;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static java.lang.String.format;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.ErrorEncodingNist4jException;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.exceptions.Nist4jException;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.serializer.binary.*;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

@Slf4j
public class WriteNistFile {

  public static final NistOptions DEFAULT_OPTIONS_FOR_WRITE =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  private final RT1TransactionInformationRecordSerializerImpl
      rt1TransactionInformationRecordSerializer; // R1
  private final RT2UserDefinedDescriptionTextRecordSerializerImpl
      rt2UserDefinedDescriptionTextRecordSerializer; // R2
  private final RT3LowResolutionGrayscaleFingerprintRecordSerializerImpl
      rt3LowResolutionGrayscaleFingerprintRecordSerializer; // R3
  private final RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl
      rt4HighResolutionGrayscaleFingerprintRecordSerializer; // R4
  private final RT5LowResolutionBinaryFingerprintRecordSerializerImpl
      rt5LowResolutionBinaryFingerprintRecordSerializer; // R5
  private final RT6HighResolutionBinaryFingerprintRecordSerializerImpl
      rt6HighResolutionBinaryFingerprintRecordSerializer; // R6
  private final RT7UserDefinedImageRecordSerializerImpl rt7UserDefinedImageRecordSerializer; // R7
  private final RT8SignatureImageRecordSerializerImpl rt8SignatureImageRecordSerializer; // R8
  private final RT9MinutiaeDataRecordSerializerImpl rt9MinutiaeDataRecordSerializer; // R9
  private final DefaultTextRecordSerializer rt10defaultTextRecordSerializer; // R10
  private final DefaultTextRecordSerializer rt11defaultTextRecordSerializer; // R11
  private final DefaultTextRecordSerializer rt12defaultTextRecordSerializer; // R12
  private final RT13LatentImageDataRecordSerializerImpl rt13defaultTextRecordSerializer; // R13
  private final RT14VariableResolutionFingerprintRecordSerializerImpl
      rt14VariableResolutionFingerprintRecordSerializer; // R14
  private final DefaultTextRecordSerializer rt15defaultTextRecordSerializer; // R15
  private final DefaultTextRecordSerializer rt16defaultTextRecordSerializer; // R16
  private final DefaultTextRecordSerializer rt17defaultTextRecordSerializer; // R17
  private final NistOptions nistOptions;

  public WriteNistFile() {
    this(DEFAULT_OPTIONS_FOR_WRITE);
  }

  public WriteNistFile(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
    this.rt1TransactionInformationRecordSerializer =
        new RT1TransactionInformationRecordSerializerImpl(nistOptions);
    this.rt2UserDefinedDescriptionTextRecordSerializer =
        new RT2UserDefinedDescriptionTextRecordSerializerImpl(nistOptions);
    this.rt3LowResolutionGrayscaleFingerprintRecordSerializer =
        new RT3LowResolutionGrayscaleFingerprintRecordSerializerImpl(nistOptions);
    this.rt4HighResolutionGrayscaleFingerprintRecordSerializer =
        new RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl(nistOptions);
    this.rt5LowResolutionBinaryFingerprintRecordSerializer =
        new RT5LowResolutionBinaryFingerprintRecordSerializerImpl(nistOptions);
    this.rt6HighResolutionBinaryFingerprintRecordSerializer =
        new RT6HighResolutionBinaryFingerprintRecordSerializerImpl(nistOptions);
    this.rt8SignatureImageRecordSerializer = new RT8SignatureImageRecordSerializerImpl(nistOptions);
    this.rt9MinutiaeDataRecordSerializer = new RT9MinutiaeDataRecordSerializerImpl(nistOptions);
    this.rt7UserDefinedImageRecordSerializer =
        new RT7UserDefinedImageRecordSerializerImpl(nistOptions);
    this.rt10defaultTextRecordSerializer = new DefaultTextRecordSerializer(nistOptions, 10);
    this.rt11defaultTextRecordSerializer = new DefaultTextRecordSerializer(nistOptions, 11);
    this.rt12defaultTextRecordSerializer = new DefaultTextRecordSerializer(nistOptions, 12);
    this.rt13defaultTextRecordSerializer = new RT13LatentImageDataRecordSerializerImpl(nistOptions);
    this.rt14VariableResolutionFingerprintRecordSerializer =
        new RT14VariableResolutionFingerprintRecordSerializerImpl(nistOptions);
    this.rt15defaultTextRecordSerializer = new DefaultTextRecordSerializer(nistOptions, 15);
    this.rt16defaultTextRecordSerializer = new DefaultTextRecordSerializer(nistOptions, 16);
    this.rt17defaultTextRecordSerializer = new DefaultTextRecordSerializer(nistOptions, 17);
  }

  public OutputStream execute(NistFile nistFile, OutputStream outputStream) throws Nist4jException {
    log.debug("Writing a nistFile to outputStream");
    if (nistFile == null) {
      throw new ErrorDecodingNist4jException("nistFile is null");
    }
    if (outputStream == null) {
      throw new ErrorDecodingNist4jException("outputStream is null");
    }

    try (BufferedOutputStream bufferedOS = new BufferedOutputStream(outputStream)) {
      // R1
      if (isEmpty(nistFile.getRT1TransactionInformationRecord())) {
        throw new InvalidFormatNist4jException("Record : " + RT1.getLabel() + " must be present");
      }
      rt1TransactionInformationRecordSerializer.write(
          bufferedOS, nistFile.getRT1TransactionInformationRecord());

      // The write order is based on CNT field 1.003
      List<Pair<String, String>> cntPairs =
          nistFile
              .getRT1TransactionInformationRecord()
              .getFieldText(RT1FieldsEnum.CNT)
              .map(SubFieldToStringConverter::toListOfPairs)
              .orElseThrow(
                  () -> new InvalidFormatNist4jException("Missing CNT field on RT1 code '1.003'"));

      for (Pair<String, String> cntPair : cntPairs) {
        final int recordId = Integer.parseInt(cntPair.getKey());
        final Integer idcId = Integer.parseInt(cntPair.getValue());
        final RecordTypeEnum recordType = RecordTypeEnum.findByRecordId(recordId);
        if (recordType != RT1) {
          final NistRecord nistRecord =
              nistFile
                  .getRecordByTypeAndIdc(recordType, idcId)
                  .orElseThrow(
                      () ->
                          new ErrorEncodingNist4jException(
                              format("Missing record %s with idc %s", recordId, idcId)));
          switch (recordType) {
            case RT2:
              rt2UserDefinedDescriptionTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT3:
              rt3LowResolutionGrayscaleFingerprintRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT4:
              rt4HighResolutionGrayscaleFingerprintRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT5:
              rt5LowResolutionBinaryFingerprintRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT6:
              rt6HighResolutionBinaryFingerprintRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT7:
              rt7UserDefinedImageRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT8:
              rt8SignatureImageRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT9:
              rt9MinutiaeDataRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT10:
              rt10defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT11:
              rt11defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT12:
              rt12defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT13:
              rt13defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT14:
              rt14VariableResolutionFingerprintRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT15:
              rt15defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT16:
              rt16defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            case RT17:
              rt17defaultTextRecordSerializer.write(bufferedOS, nistRecord);
              break;
            default:
              new DefaultTextRecordSerializer(nistOptions, recordId).write(bufferedOS, nistRecord);
          }
        }
      }
      bufferedOS.flush();
    } catch (Exception e) {
      log.error("Exception while execute", e);
      throw new ErrorEncodingNist4jException("Exception while execute" + e.getMessage());
    }
    return outputStream;
  }
}
