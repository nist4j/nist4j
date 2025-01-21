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

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistFileBuilder;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.enums.CharsetEnum;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.exceptions.Nist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import io.github.nist4j.use_cases.helpers.serializer.binary.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadNistFile {

  private final NistOptions nistOptions;
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

  public static final NistOptions DEFAULT_OPTIONS_FOR_READ_FILE =
      NistOptionsImpl.builder()
          .isCalculateLENOnBuild(false)
          .isCalculateCNTOnBuild(false)
          .charset(CharsetEnum.DEFAULT.getCharset())
          .build();

  public ReadNistFile() {
    this(DEFAULT_OPTIONS_FOR_READ_FILE);
  }

  public ReadNistFile(NistOptions nistOptions) {
    this.nistOptions = nistOptions;
    this.rt1TransactionInformationRecordSerializer =
        new RT1TransactionInformationRecordSerializerImpl(nistOptions);
    this.rt2UserDefinedDescriptionTextRecordSerializer =
        new RT2UserDefinedDescriptionTextRecordSerializerImpl(nistOptions);
    this.rt4HighResolutionGrayscaleFingerprintRecordSerializer =
        new RT4HighResolutionGrayscaleFingerprintRecordSerializerImpl(nistOptions);
    this.rt3LowResolutionGrayscaleFingerprintRecordSerializer =
        new RT3LowResolutionGrayscaleFingerprintRecordSerializerImpl(nistOptions);
    this.rt5LowResolutionBinaryFingerprintRecordSerializer =
        new RT5LowResolutionBinaryFingerprintRecordSerializerImpl(nistOptions);
    this.rt6HighResolutionBinaryFingerprintRecordSerializer =
        new RT6HighResolutionBinaryFingerprintRecordSerializerImpl(nistOptions);
    this.rt7UserDefinedImageRecordSerializer =
        new RT7UserDefinedImageRecordSerializerImpl(nistOptions);
    this.rt8SignatureImageRecordSerializer = new RT8SignatureImageRecordSerializerImpl(nistOptions);
    this.rt9MinutiaeDataRecordSerializer = new RT9MinutiaeDataRecordSerializerImpl(nistOptions);
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

  public NistFile execute(InputStream inputStream) throws Nist4jException {
    log.debug("Reading of NistFile : starting from inputStream");
    if (inputStream == null) {
      throw new ErrorDecodingNist4jException("inputStream is null");
    }

    NistDecoderHelper.Token token;
    NistFileBuilder nistFileBuilder = new CreateNistFile(nistOptions).execute();

    // Read 1st record
    try {
      byte[] nistBytes = new byte[inputStream.available()];
      DataInputStream dataInputStream = new DataInputStream(inputStream);
      dataInputStream.readFully(nistBytes);

      token = new NistDecoderHelper.Token(nistBytes);
      nistFileBuilder.withRecord(1, rt1TransactionInformationRecordSerializer.read(token));
    } catch (ErrorDecodingNist4jException
        | UnsupportedOperationException
        | IOException
        | NumberFormatException e) {
      throw new ErrorDecodingNist4jException(e.getMessage());
    }

    while (nextRecord(token)) {
      try {
        switch (token.crt) {
          case 0:
          case 1:
            continue; // Le record 1 a déjà été fait on byPass
          case 2:
            nistFileBuilder.withRecord(
                token.crt, rt2UserDefinedDescriptionTextRecordSerializer.read(token));
            break;
          case 3:
            nistFileBuilder.withRecord(
                token.crt, rt3LowResolutionGrayscaleFingerprintRecordSerializer.read(token));
            break;
          case 4:
            nistFileBuilder.withRecord(
                token.crt, rt4HighResolutionGrayscaleFingerprintRecordSerializer.read(token));
            break;
          case 5:
            nistFileBuilder.withRecord(
                token.crt, rt5LowResolutionBinaryFingerprintRecordSerializer.read(token));
            break;
          case 6:
            nistFileBuilder.withRecord(
                token.crt, rt6HighResolutionBinaryFingerprintRecordSerializer.read(token));
            break;
          case 7:
            nistFileBuilder.withRecord(token.crt, rt7UserDefinedImageRecordSerializer.read(token));
            break;
          case 8:
            nistFileBuilder.withRecord(token.crt, rt8SignatureImageRecordSerializer.read(token));
            break;
          case 9:
            nistFileBuilder.withRecord(token.crt, rt9MinutiaeDataRecordSerializer.read(token));
            break;
          case 10:
            nistFileBuilder.withRecord(token.crt, rt10defaultTextRecordSerializer.read(token));
            break;
          case 11:
            nistFileBuilder.withRecord(token.crt, rt11defaultTextRecordSerializer.read(token));
            break;
          case 12:
            nistFileBuilder.withRecord(token.crt, rt12defaultTextRecordSerializer.read(token));
            break;
          case 13:
            nistFileBuilder.withRecord(token.crt, rt13defaultTextRecordSerializer.read(token));
            break;
          case 14:
            nistFileBuilder.withRecord(
                token.crt, rt14VariableResolutionFingerprintRecordSerializer.read(token));
            break;
          case 15:
            nistFileBuilder.withRecord(token.crt, rt15defaultTextRecordSerializer.read(token));
            break;
          case 16:
            nistFileBuilder.withRecord(token.crt, rt16defaultTextRecordSerializer.read(token));
            break;
          case 17:
            nistFileBuilder.withRecord(token.crt, rt17defaultTextRecordSerializer.read(token));
            break;
          default:
            log.error("RecordType not implemented {}", token.crt);
            nistFileBuilder.withRecord(
                token.crt, new DefaultTextRecordSerializer(nistOptions, token.crt).read(token));
        }
        log.debug(
            "Parsing of record {} pos {}, length {}", token.crt, token.pos, token.buffer.length);

      } catch (Exception e) {
        log.error("Error while decoding nistFile", e);
        throw new InvalidFormatNist4jException(e.getMessage());
      }
    }
    return nistFileBuilder.build();
  }

  private boolean nextRecord(NistDecoderHelper.Token token) {
    if (token.header.isEmpty()) {
      return false;
    }

    int rsPos = token.header.indexOf(NistDecoderHelper.SEP_RS);
    if (rsPos == -1) {
      rsPos = token.header.length() - 1;
    }

    int usPos = token.header.indexOf(NistDecoderHelper.SEP_US);
    token.crt = Integer.parseInt(token.header.substring(0, usPos));
    token.header = token.header.substring(rsPos + 1);

    return true;
  }
}
