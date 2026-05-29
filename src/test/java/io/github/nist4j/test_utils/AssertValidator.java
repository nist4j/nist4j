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

import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEquals;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@SuppressWarnings("UnusedReturnValue")
@AllArgsConstructor
public class AssertValidator {

  private final List<NistValidationError> errorsList;

  public static AssertValidator assertThatErrors(@NonNull List<NistValidationError> errorsList) {
    return new AssertValidator(errorsList);
  }

  public AssertValidator containsError(@NonNull INistValidationErrorEnum errorEnum) {
    assertThat(toErrorCodesList(errorsList)).contains(errorEnum.getCode());
    return this;
  }

  public AssertValidator containsInvalidFields(@NonNull IFieldTypeEnum... fields) {
    List<String> fieldNames =
        Arrays.stream(fields).map(IFieldTypeEnum::getCode).collect(Collectors.toList());
    assertThat(toErrorFieldsList(errorsList)).containsAll(fieldNames);
    return this;
  }

  public AssertValidator doesNotContainsInvalidFields(@NonNull IFieldTypeEnum... fields) {
    List<String> fieldNames =
        Arrays.stream(fields).map(IFieldTypeEnum::getCode).collect(Collectors.toList());
    assertThat(toErrorFieldsList(errorsList)).doesNotContainSequence(fieldNames);
    return this;
  }

  public AssertValidator containsExactlyInvalidFields(@NonNull IFieldTypeEnum... fields) {
    //noinspection FuseStreamOperations
    final Set<String> fieldNamesInOrder =
        Arrays.stream(fields).map(IFieldTypeEnum::getCode).collect(Collectors.toSet());
    assertThat(toErrorFieldsSet(errorsList))
        .containsExactly(fieldNamesInOrder.toArray(new String[0]));
    return this;
  }

  @SuppressWarnings("UnusedReturnValue")
  public AssertValidator containsErrorOn(
      @NonNull RecordTypeEnum rt, @NonNull IFieldTypeEnum field, String subField) {
    String expectedStr = rt + "." + field + "." + subField;
    Set<String> errors =
        errorsList.stream()
            .map(e -> e.getRecordType() + "." + e.getFieldType() + "." + e.getSubfieldName())
            .collect(Collectors.toSet());
    assertThat(errors).contains(expectedStr);
    return this;
  }

  public AssertValidator containsInvalidFieldWithValue(
      @NonNull IFieldTypeEnum field, String expectedValue) {
    assertThat(toErrorFieldsList(errorsList)).contains(field.name());

    List<String> listValFounded =
        errorsList.stream()
            .filter(
                e ->
                    field.equals(e.getFieldType())
                        && field.getRecordType().equals(e.getRecordType()))
            .map(NistValidationError::getValueFound)
            .collect(Collectors.toList());

    assertThat(listValFounded).contains(expectedValue);
    return this;
  }

  public AssertValidator containsInvalidSubfieldWithValue(
      IFieldTypeEnum field, String subfield, String expectedValue) {
    assertThat(toErrorFieldsList(errorsList)).contains(field.name());

    Optional<NistValidationError> error = getValueFromField(field.getRecordType(), field);
    assertThat(error.map(NistValidationError::getSubfieldName).orElse(null)).isEqualTo(subfield);
    assertThat(error.map(NistValidationError::getValueFound).orElse(null)).isEqualTo(expectedValue);
    return this;
  }

  private Optional<NistValidationError> getValueFromField(RecordTypeEnum rt, IFieldTypeEnum field) {
    return errorsList.stream()
        .filter(e -> field.equals(e.getFieldType()) && rt.equals(e.getRecordType()))
        .findFirst();
  }

  private static List<String> toErrorCodesList(List<NistValidationError> errorsNist) {
    return errorsNist.stream().map(NistValidationError::getCode).collect(Collectors.toList());
  }

  private static List<String> toErrorFieldsList(List<NistValidationError> errorsNist) {
    return errorsNist.stream()
        .map(NistValidationError::getFieldType)
        .map(f -> f.name())
        .collect(Collectors.toList());
  }

  private static Set<String> toErrorFieldsSet(List<NistValidationError> errorsNist) {
    return errorsNist.stream()
        .map(NistValidationError::getFieldType)
        .map(f -> f.name())
        .collect(Collectors.toSet());
  }

  public AssertValidator doesNotContainsError(INistValidationErrorEnum errorEnum) {
    assertThat(toErrorCodesList(errorsList)).doesNotContain(errorEnum.getCode());
    return this;
  }

  public static Predicate<ValidationResult> isNotValid() {
    return validationResult -> !validationResult.isValid();
  }

  public static Predicate<ValidationResult> errorsNumberIs(int expectedCount) {
    return validationResult -> errorNumberCompare(expectedCount, validationResult);
  }

  private static boolean errorNumberCompare(int expectedCount, ValidationResult validationResult) {
    return validationResult.getErrors().size() == expectedCount;
  }

  public static Predicate<ValidationResult> errorsCodeIs(String expectedCode) {
    return validationResult ->
        validationResult.getErrors().stream()
            .map(NistValidationError::getCode)
            .anyMatch(expectedCode::equals);
  }

  public static Predicate<ValidationResult> errorsContainsMessage(String expectedMessage) {
    return validationResult -> errorMessageCompare(expectedMessage, validationResult);
  }

  private static boolean errorMessageCompare(
      String expectedMessage, ValidationResult validationResult) {
    return validationResult.getErrors().stream()
        .map(NistValidationError::getMessage)
        .anyMatch(stringEquals(expectedMessage));
  }

  public void containsValidMsg(IFieldTypeEnum... fieldsType) {
    List<IFieldTypeEnum> fields = Arrays.asList(fieldsType);
    List<String> messages =
        this.errorsList.stream()
            .filter(e -> fields.contains(e.getFieldType()))
            .map(NistValidationError::getMessage)
            .collect(Collectors.toList());
    assertThat(fieldsType).isNotEmpty();
    for (String message : messages) {
      assertThat(message).doesNotContainPattern(Pattern.quote("{recordType}"));
      assertThat(message).doesNotContainPattern(Pattern.quote("{fieldId}"));
      assertThat(message).doesNotContainPattern(Pattern.quote("{fieldName}"));
      assertThat(message).doesNotContainPattern("\\{param\\d+}");
    }
  }
}
