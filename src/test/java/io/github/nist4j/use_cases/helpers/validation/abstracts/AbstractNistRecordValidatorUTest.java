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
package io.github.nist4j.use_cases.helpers.validation.abstracts;

import static io.github.nist4j.enums.CharacterTypeEnum.A;
import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.fixtures.OptionsFixtures.OPTIONS_FOR_VALIDATION;
import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.records.DefaultNistTextRecordBuilderImpl;
import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

class AbstractNistRecordValidatorUTest {

  @Test
  void checkForCustomPredicate_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("valid"))
            .build();

    NistRecord badRecord = new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    Predicate<String> predicate = str -> Objects.equals(str, "valid");

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkCustomPredicateOnField(FakeFieldTypeEnum.F4T, FakeError.ERR, predicate);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecord).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("value"))
            .build();

    NistRecord badRecord = new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecord).isValid()).isFalse();
  }

  @Test
  void checkForEmptyField_should_check_field() {
    // Given
    NistRecord okRecord = new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("value"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForEmptyField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    ValidationResult resultBadRecord = validator.validate(badRecord);
    assertThat(resultBadRecord.isValid()).isFalse();
    assertThat(resultBadRecord.getErrors().get(0).getAttemptedFound()).isEqualTo("value");
  }

  @Test
  void checkForMandatoryAndRegexField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("7"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad format"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryAndRegexField(FakeFieldTypeEnum.F4T, "^[1-9]$");
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).getErrors().get(0).getMessage())
        .doesNotContain("%s");
  }

  @Test
  void checkForMandatoryLENField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("1234567"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad format"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryLENField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).getErrors().get(0).getMessage())
        .doesNotContain("%s");
  }

  @Test
  void checkForMandatoryAndExactStringField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("expected value"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseNullValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText((String) null))
            .build();

    NistRecord badRecordBecauseEmptyValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText(""))
            .build();

    NistRecord badRecordBecauseBadValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("not the expected value"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryAndExactStringField(FakeFieldTypeEnum.F4T, "expected value");
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseNullValue).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseEmptyValue).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue).isValid()).isFalse();
  }

  @Test
  void checkForOptionalButRegexField_should_check_field() {
    // Given
    NistRecord okRecordMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord okRecordFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("7"))
            .build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForOptionalButRegexField(FakeFieldTypeEnum.F4T, "^[1-9]$");
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecordMissing).isValid()).isTrue();
    assertThat(validator.validate(okRecordFormat).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryDateField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20231213"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("2023/12/13"))
            .build();

    NistRecord badRecordBecauseBadValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20230231"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryDateField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue).isValid()).isFalse();
  }

  @Test
  void checkForOptionalDateField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20231213"))
            .build();

    NistRecord okRecordEvenIfMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("2023/12/13"))
            .build();

    NistRecord badRecordBecauseBadValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20230231"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForOptionalButDateField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(okRecordEvenIfMissing).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryDateTimeField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20231213233259Z"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("2023/12/13 23:32:59"))
            .build();

    NistRecord badRecordBecauseBadValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20231213233261Z"))
            .build();

    NistRecord badRecordBecauseBadValue2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20230231233261Z"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryDateTimeField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue2).isValid()).isFalse();
  }

  @Test
  void checkForOptionalButDateTimeField_should_check_field() {
    // Given
    NistRecord okRecordMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord okRecordFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20231213233259Z"))
            .build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("2023/12/13 23:32:59"))
            .build();

    NistRecord badRecordBecauseBadValue =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("20231213233261Z"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForOptionalButDateTimeField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecordMissing).isValid()).isTrue();
    assertThat(validator.validate(okRecordFormat).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryInCollectionField_should_check_field() {
    List<String> collection = Arrays.asList("A", "B", "C");
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("A"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("AB"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("2023/12/13"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryInCollectionField(FakeFieldTypeEnum.F4T, collection);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
  }

  @Test
  void checkForOptionalButInCollectionField_should_check_field() {
    List<String> collection = Arrays.asList("A", "B", "C");
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("A"))
            .build();

    NistRecord okRecordEvenIfMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("AB"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("2023/12/13"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForOptionalButInCollectionField(FakeFieldTypeEnum.F4T, collection);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(okRecordEvenIfMissing).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryNumericFieldBetween_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("1000"))
            .build();

    NistRecord badRecordBecauseMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("9Z"))
            .build();

    NistRecord badRecordBecauseBadValue1 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("0"))
            .build();

    NistRecord badRecordBecauseBadValue2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("1001"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryNumericFieldBetween(FakeFieldTypeEnum.F4T, 1, 1000);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue1).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue2).isValid()).isFalse();
  }

  @Test
  void checkForOptionalButNumericFieldBetween_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("1000"))
            .build();

    NistRecord okRecordWithMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordBecauseBadFormat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("bad value"))
            .build();

    NistRecord badRecordBecauseBadFormat2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("9Z"))
            .build();

    NistRecord badRecordBecauseBadValue1 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("0"))
            .build();

    NistRecord badRecordBecauseBadValue2 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("1001"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForOptionalButNumericFieldBetween(FakeFieldTypeEnum.F4T, 1, 1000);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(okRecordWithMissing).isValid()).isTrue();
    assertThat(validator.validate(badRecordBecauseBadFormat).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadFormat2).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue1).isValid()).isFalse();
    assertThat(validator.validate(badRecordBecauseBadValue2).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryDataField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldImage(new byte[] {1, 2, 3}))
            .build();

    NistRecord badRecordWithMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordWithText =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("not ok"))
            .build();

    NistRecord badRecordWithEmpty =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldImage(new byte[0]))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryImageField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(badRecordWithMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordWithText).isValid()).isFalse();
    assertThat(validator.validate(badRecordWithEmpty).isValid()).isFalse();
  }

  @Test
  void checkForOptionalButDataField_should_check_field() {
    // Given
    NistRecord okRecord =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldImage(new byte[] {1, 2, 3}))
            .build();

    NistRecord okRecordWithMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordWithText =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("not ok"))
            .build();

    NistRecord badRecordWithEmpty =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldImage(new byte[0]))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForOptionalButImageField(FakeFieldTypeEnum.F4T);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okRecord).isValid()).isTrue();
    assertThat(validator.validate(okRecordWithMissing).isValid()).isTrue();
    assertThat(validator.validate(badRecordWithText).isValid()).isFalse();
    assertThat(validator.validate(badRecordWithEmpty).isValid()).isFalse();
  }

  @Test
  void checkForMandatoryNumericFieldBetween_should_allow_01() {
    // Given
    NistRecord okVal0 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("0"))
            .build();

    NistRecord okVal01 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("01"))
            .build();

    NistRecord okVal99 =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText(99))
            .build();

    NistRecord badRecordWithMissing =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1).build();

    NistRecord badRecordWithEmpty =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText(""))
            .build();

    NistRecord badRecordWithTooGreat =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("100"))
            .build();
    NistRecord badRecordWithTooLow =
        new DefaultNistTextRecordBuilderImpl(OPTIONS_FOR_VALIDATION, 1)
            .withField(FakeFieldTypeEnum.F4T, newFieldText("-1"))
            .build();

    Validator<NistRecord> validator =
        new AbstractNistRecordValidator(OPTIONS_FOR_VALIDATION, RT1) {
          @Override
          public void rules() {
            checkForMandatoryNumericFieldBetween(FakeFieldTypeEnum.F4T, 0, 99);
          }
        };

    // When
    // Then
    assertThat(validator.validate(okVal0).isValid()).isTrue();
    assertThat(validator.validate(okVal01).isValid()).isTrue();
    assertThat(validator.validate(okVal99).isValid()).isTrue();
    assertThat(validator.validate(badRecordWithMissing).isValid()).isFalse();
    assertThat(validator.validate(badRecordWithEmpty).isValid()).isFalse();
    assertThat(validator.validate(badRecordWithTooGreat).isValid()).isFalse();
    assertThat(validator.validate(badRecordWithTooLow).isValid()).isFalse();
  }

  @Getter
  protected enum FakeError implements INistValidationErrorEnum {
    ERR("Fake error", FakeFieldTypeEnum.F4T);
    private final String message;
    private final String code;
    private final String fieldName;
    private final IFieldTypeEnum fieldTypeEnum;

    @SuppressWarnings("SameParameterValue")
    FakeError(String message, IFieldTypeEnum defaultFieldsEnum) {
      this.code = this.name();
      this.message = message;
      this.fieldName = defaultFieldsEnum.getCode();
      this.fieldTypeEnum = defaultFieldsEnum;
    }
  }

  @Getter
  @AllArgsConstructor
  protected enum FakeFieldTypeEnum implements IFieldTypeEnum {
    F4T(101, "F4T", "Field for Test", DataTextImmutableImpl.class, A);

    private final RecordTypeEnum recordType = null;
    private final int id;
    private final String code;
    private final String description;
    private final Class<? extends Data<?>> typeClass;
    private final CharacterTypeEnum characterType;
  }
}
