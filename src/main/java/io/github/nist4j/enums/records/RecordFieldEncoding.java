/*
 * Copyright (C) 2026 Sopra Steria.
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

import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import java.util.*;
import java.util.stream.Collectors;

public class RecordFieldEncoding {
  private static final Set<CharacterTypeEnum> nonUnicodeTypes =
      Arrays.stream(CharacterTypeEnum.values())
          .filter(t -> !t.equals(CharacterTypeEnum.U))
          .collect(Collectors.toSet());
  private static final Set<String> nonUnicodeFields;

  static {
    Set<String> listOfNonUnicodeFields = new HashSet<>();
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT1FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT2FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT3FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT4FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT5FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT6FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT7FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT8FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT9FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT10FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT13FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT14FieldsEnum.values()));
    listOfNonUnicodeFields.addAll(populateNonUnicodeFields(RT15FieldsEnum.values()));
    nonUnicodeFields = Collections.unmodifiableSet(listOfNonUnicodeFields);
  }

  private static List<String> populateNonUnicodeFields(IFieldTypeEnum[] fieldsEnums) {
    return Arrays.stream(fieldsEnums)
        .filter(
            f ->
                nonUnicodeTypes.contains(
                    f.getCharacterType())) // All userDefined records are Unicode by default
        .map(f -> generateKey(f))
        .collect(Collectors.toList());
  }

  private static String generateKey(IFieldTypeEnum fieldType) {
    return generateKey(fieldType.getRecordType(), fieldType.getId());
  }

  private static String generateKey(String recordType, int fieldId) {
    int recordId =
        recordType.startsWith("RT")
            ? Integer.parseInt(recordType.substring(2))
            : Integer.parseInt(recordType);
    return generateKey(recordId, fieldId);
  }

  private static String generateKey(int recordId, int fieldId) {
    return recordId + "." + fieldId;
  }

  public static boolean isUnicode(int recordId, int fieldId) {
    return !nonUnicodeFields.contains(generateKey(recordId, fieldId));
  }

  public static boolean isUnicode(IFieldTypeEnum fieldType) {
    return !nonUnicodeFields.contains(generateKey(fieldType));
  }
}
