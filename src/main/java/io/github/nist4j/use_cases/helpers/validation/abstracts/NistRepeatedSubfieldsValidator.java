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

import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import lombok.NonNull;

public class NistRepeatedSubfieldsValidator extends AbstractValidator<String> {
  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private final NistOptions nistOptions;

  private final INistValidationErrorEnum errorField;
  private final RecordTypeEnum recordType;
  private final IFieldTypeEnum fieldType;
  private final SubfieldRule[] subfieldRules;

  public NistRepeatedSubfieldsValidator(
      @NonNull NistOptions nistOptions,
      @NonNull RecordTypeEnum recordType,
      @NonNull IFieldTypeEnum fieldType,
      @NonNull INistValidationErrorEnum errorField,
      SubfieldRule[] subfieldRules) {

    this.nistOptions = nistOptions;
    this.recordType = recordType;
    this.fieldType = fieldType;
    this.errorField = errorField;
    this.subfieldRules = subfieldRules;
  }

  @Override
  public void rules() {

    ruleForEach(str -> SubFieldToStringConverter.toListUsingSplitByRS(str))
        .whenever(listOfItems -> isNotEmpty(listOfItems))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType, this.fieldType, this.errorField, this.subfieldRules));
  }
}
