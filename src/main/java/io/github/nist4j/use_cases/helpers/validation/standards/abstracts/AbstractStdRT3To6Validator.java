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
package io.github.nist4j.use_cases.helpers.validation.standards.abstracts;

import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.PredicateBuilder.from;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.isNumberBetween;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.stringEmptyOrNull;
import static java.util.function.Predicate.isEqual;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.List;
import java.util.function.Predicate;
import lombok.NonNull;

public abstract class AbstractStdRT3To6Validator extends AbstractNistRecordValidator {

  public static final String FGP_NO_VALUE = "255";

  protected AbstractStdRT3To6Validator(NistOptions nistOptions, RecordTypeEnum recordType) {
    super(nistOptions, recordType);
  }

  protected abstract NistStandardEnum getStandard();

  protected void checkThatDATAisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryImageField(field);
  }

  protected void checkThatGCAisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryNumericFieldBetween(field, 0, 6);
  }

  protected void checkThatVLLisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryNumericFieldBetween(field, 10, 65535);
  }

  protected void checkThatHLLisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryNumericFieldBetween(field, 10, 65535);
  }

  protected void checkThatISRisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryNumericFieldBetween(field, 0, 1);
  }

  protected void checkThatIMPisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryNumericFieldBetween(field, 0, 29);
  }

  protected void checkThatLENisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryLENField(field);
  }

  protected void checkThatIDCisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    checkForMandatoryNumericFieldBetween(field, 0, 99);
  }

  protected void checkThatFGPisValidForRT3to6(@NonNull IFieldTypeEnum field) {
    // is Mandatory and is defined in collection
    checkCustomPredicateOnField(
        field, STD_ERR_FGP, not(stringEmptyOrNull()).and(validateFieldFGP(getStandard())));
  }

  protected static Predicate<String> validateFieldFGP(NistStandardEnum nistStd) {
    return field -> {
      final List<String> items = SubFieldToStringConverter.toItems(field);

      return isNotEmpty(items)
          && items.size() == 6 // 6 elements must be found in FGP
          && items.stream().allMatch(fgp -> validateFGPValueBasedOnStandard(nistStd).test(fgp));
    };
  }

  protected static Predicate<String> validateFGPValueBasedOnStandard(NistStandardEnum nistStd) {
    if (nistStd == NistStandardEnum.ANSI_NIST_ITL_2007) {
      return from(isNumberBetween(0, 14)).or(isEqual(FGP_NO_VALUE));
    } else {
      return from(isNumberBetween(0, 15)).or(isEqual(FGP_NO_VALUE));
    }
  }
}
