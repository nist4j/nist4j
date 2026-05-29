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
package io.github.nist4j.use_cases.helpers.validation.format;

import static java.util.Objects.isNull;

import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import java.util.List;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationMessage {

  private static final List<Object> NULL_PARAMS = null;
  private static final String NO_SUBFIELD = null;

  public static String format(
      @NonNull INistValidationErrorEnum error,
      RecordTypeEnum recordType,
      IFieldTypeEnum fieldType,
      String subfieldName,
      List<Object> params) {

    String msg = error.getMessage();
    if (isNull(fieldType)) {
      msg = msg.replace("{recordType}", "");
    } else {
      msg = msg.replace("{recordType}", String.valueOf(recordType.getNumber()));
    }
    if (isNull(fieldType)) {
      msg = msg.replace(".{fieldId}", "");
      msg = msg.replace("{fieldId}", "");
      msg = msg.replace("{fieldName}", "");
    } else {
      msg = msg.replace("{fieldId}", String.format("%03d", fieldType.getId()));
      msg = msg.replace("{fieldName}", fieldType.getCode());
    }
    if (isNull(subfieldName)) {
      msg = msg.replace("{subfieldName}", "");
    } else {
      msg = msg.replace("{subfieldName}", "(in subfieldName:" + subfieldName + ")");
    }
    if (params != null) {
      for (int i = 0; i < params.size(); i++) {
        msg = msg.replace("{param" + i + "}", String.valueOf(params.get(i)));
      }
    }
    if (msg.contains("%s")) {
      log.warn("validation error message is invalid, it shouldn't contain '%s' founded : {}", msg);
    }
    if (msg.contains("{param0}")
        || msg.contains("{param1}")
        || msg.contains("{param2}")
        || msg.contains("{param3}")) {
      log.warn(
          "validation error message is invalid, it shouldn't contain '{param}' founded : {}", msg);
    }
    return msg;
  }

  public static String format(
      @NonNull INistValidationErrorEnum error,
      RecordTypeEnum recordType,
      IFieldTypeEnum field,
      List<Object> params) {

    return format(error, recordType, field, NO_SUBFIELD, params);
  }

  public static String format(
      @NonNull INistValidationErrorEnum error, RecordTypeEnum recordType, IFieldTypeEnum field) {

    return format(error, recordType, field, NO_SUBFIELD, NULL_PARAMS);
  }

  public static String format(@NonNull INistValidationErrorEnum error, RecordTypeEnum recordType) {
    return format(error, recordType, null, NO_SUBFIELD, NULL_PARAMS);
  }
}
