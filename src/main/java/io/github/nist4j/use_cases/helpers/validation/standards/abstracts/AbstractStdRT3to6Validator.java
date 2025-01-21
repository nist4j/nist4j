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

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.PredicateBuilder.from;
import static br.com.fluentvalidator.predicate.StringPredicate.isNumeric;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.DATA;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.FGP;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.GCA;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.HLL;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.IDC;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.IMP;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.ISR;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.LEN;
import static io.github.nist4j.enums.records.GenericImageTypeEnum.VLL;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicates.isNumberBetween;
import static java.util.function.Predicate.isEqual;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.List;
import java.util.function.Predicate;

public abstract class AbstractStdRT3to6Validator extends AbstractNistRecordValidator {

  public static final String FGP_NO_VALUE = "255";

  protected AbstractStdRT3to6Validator(NistOptions nistOptions, RecordTypeEnum recordType) {
    super(nistOptions, recordType);
  }

  protected abstract NistStandardEnum getStandard();

  protected void checkThatDATAisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryDataField(DATA, error);
  }

  protected void checkThatGCAisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryNumericFieldBetween(GCA, error, 0, 6);
  }

  protected void checkThatVLLisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryNumericFieldBetween(VLL, error, 1, 99999);
  }

  protected void checkThatHLLisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryNumericFieldBetween(HLL, error, 1, 99999);
  }

  protected void checkThatISRisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryNumericFieldBetween(ISR, error, 0, 1);
  }

  protected void checkThatIMPisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryNumericFieldBetween(IMP, error, 0, 29);
  }

  protected void checkThatLENisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryAndRegexField(LEN, error, "^[1-9]\\d{0,7}$");
  }

  protected void checkThatIDCisValidForRT3to6(INistValidationErrorEnum error) {
    checkForMandatoryNumericFieldBetween(IDC, error, 0, 99);
  }

  protected void checkThatFGPisValidForRT3to6(INistValidationErrorEnum error) {
    // is Mandatory and is defined in collection
    checkCustomPredicateOnField(
        FGP, error, not(stringEmptyOrNull()).and(validateFieldFGP(getStandard())));
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
      return from(isNumeric()).and(isNumberBetween(0, 14)).or(isEqual(FGP_NO_VALUE));
    } else {
      return from(isNumeric()).and(isNumberBetween(0, 15)).or(isEqual(FGP_NO_VALUE));
    }
  }
}
