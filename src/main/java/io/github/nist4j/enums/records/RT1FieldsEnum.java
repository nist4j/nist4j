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
package io.github.nist4j.enums.records;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RT1FieldsEnum implements IFieldTypeEnum {
  LEN(RTDefaultFieldsEnum.LEN),
  VER(2, "VER", "Version number", DataText.class),
  CNT(3, "CNT", "File Content", DataText.class),
  TOT(4, "TOT", "typeOfTransaction", DataText.class),
  DAT(5, "DAT", "date", DataText.class),
  PRY(6, "PRY", "priority", DataText.class),
  DAI(7, "DAI", "Destination agency identifier", DataText.class),
  ORI(8, "ORI", "Originating agency identifier", DataText.class),
  TCN(9, "TCN", "controlNumber", DataText.class),
  TCR(10, "TCR", "Transaction Control Reference", DataText.class),
  NSR(11, "NSR", "nativeScanningResolution", DataText.class),
  NTR(12, "NTR", "nominalTransmittingResolution", DataText.class),
  DOM(13, "DOM", "domainName", DataText.class),
  GMT(14, "GMT", "greenwichMeanTime", DataText.class),
  DCS(15, "DCS", "GMT", DataText.class),
  APS(16, "APS", "Application profile specifications", DataText.class),
  ANM(17, "ANM", "Agency names", DataText.class),
  GNS(18, "GNS", "Geographic name set", DataText.class);

  private final String recordType = "RT1";
  private final int id;
  private final String code;
  private final String description;
  private final Class<? extends Data> typeClass;

  <T extends IFieldTypeEnum> RT1FieldsEnum(T parentEnum) {
    this(
        parentEnum.getId(),
        parentEnum.getCode(),
        parentEnum.getDescription(),
        parentEnum.getTypeClass());
  }
}
