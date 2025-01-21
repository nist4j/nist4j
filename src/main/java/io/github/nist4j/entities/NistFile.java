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
package io.github.nist4j.entities;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.RecordTypeEnum;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NistFile {
  Map<RecordTypeEnum, List<NistRecord>> getRxMapDefaultRecords();

  NistRecord getRT1TransactionInformationRecord();

  List<NistRecord> getRT2UserDefinedDescriptionTextRecords();

  List<NistRecord> getRT3LowResolutionGrayscaleFingerprintRecords();

  List<NistRecord> getRT4HighResolutionGrayscaleFingerprintRecords();

  List<NistRecord> getRT5LowResolutionBinaryFingerprintRecords();

  List<NistRecord> getRT6HighResolutionBinaryFingerprintRecords();

  List<NistRecord> getRT7UserDefinedImageRecords();

  List<NistRecord> getRT8SignatureImageRecords();

  List<NistRecord> getRT9MinutiaeDataRecords();

  List<NistRecord> getRT10FacialAndSmtImageRecords();

  List<NistRecord> getRT11thRecords();

  List<NistRecord> getRT12thRecords();

  List<NistRecord> getRT13VariableResolutionLatentImageRecords();

  List<NistRecord> getRT14VariableResolutionFingerprintRecords();

  List<NistRecord> getRT15VariableResolutionPalmprintRecords();

  List<NistRecord> getRT16UserDefinedTestingImageRecords();

  List<NistRecord> getRT17IrisImageRecords();

  Map<RecordTypeEnum, List<NistRecord>> getMapOfAllrecords();

  List<NistRecord> getRecordListByRecordTypeEnum(RecordTypeEnum recordTypeEnum);

  Optional<NistRecord> getRecordByTypeAndIdc(RecordTypeEnum recordType, Integer idcId);
}
