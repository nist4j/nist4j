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

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_TOO_MANY_SUBFIELDS_FOUNDED;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidNistSubfield;
import java.util.List;
import java.util.function.Predicate;
import lombok.NonNull;

public class NistUniqueSubfieldsValidator extends AbstractValidator<String> {
  private final INistValidationErrorEnum errorField;
  private final RecordTypeEnum recordType;
  private final IFieldTypeEnum fieldType;
  private final SubfieldRule[] subfieldValidators;

  public NistUniqueSubfieldsValidator(
      @NonNull RecordTypeEnum recordType,
      @NonNull IFieldTypeEnum fieldType,
      @NonNull INistValidationErrorEnum errorField,
      SubfieldRule[] subfieldValidators) {

    this.recordType = recordType;
    this.fieldType = fieldType;
    this.errorField = errorField;
    this.subfieldValidators = subfieldValidators;
  }

  @Override
  public void rules() {

    ruleFor(str -> str)
        .must(nbOfValidatorsMatchsWithNbOfItems(this.subfieldValidators.length))
        .handlerInvalidField(
            new HandlerInvalidNistSubfield(
                STD_ERR_TOO_MANY_SUBFIELDS_FOUNDED,
                this.recordType,
                this.fieldType,
                null,
                singletonList(
                    "nb subfields expected : '" + this.subfieldValidators.length + "' ")));

    for (int i = 0; i < subfieldValidators.length; i++) {
      final int idx = i;
      final INistValidationErrorEnum validationError =
          ofNullable(this.subfieldValidators[idx].getError()).orElse(this.errorField);
      final String subfieldName = this.subfieldValidators[idx].getSubfieldName();

      ruleFor(str -> getIndexOrNull(SubFieldToStringConverter.toList(str), idx))
          .must(this.subfieldValidators[idx].getValidator())
          .handlerInvalidField(
              new HandlerInvalidNistSubfield(
                  validationError, this.recordType, this.fieldType, subfieldName, emptyList()));
    }
  }

  private Predicate<String> nbOfValidatorsMatchsWithNbOfItems(int length) {
    return str -> length >= SubFieldToStringConverter.toList(str).size();
  }

  private String getIndexOrNull(List<String> items, int index) {
    if (isEmpty(items) || index >= items.size()) {
      return null;
    } else {
      return items.get(index);
    }
  }
}
