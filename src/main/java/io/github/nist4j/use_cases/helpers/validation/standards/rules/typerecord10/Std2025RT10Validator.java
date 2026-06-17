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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord10;

import static io.github.nist4j.enums.CharacterTypeEnum.*;
import static io.github.nist4j.enums.records.RT10FieldsEnum.*;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.equalTo;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicate.emptyOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldEquals;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static java.util.Collections.emptyList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import java.util.function.Predicate;

public class Std2025RT10Validator extends Std2015RT10Validator {

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2025;
  }

  protected Std2025RT10Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2025RT10Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  public void rules() {
    super.rules();
    // 10.035 FSB - New field
    checkForFieldFSB10_035();

    // 10.036 TIF - New field to support Media type in CGA
    checkForFieldTIF10_036();

    // 10.199 BRI - New field
    checkForFieldBRI10_199();
  }

  @Override
  protected void checkForFieldFEC10_033() {
    checkCustomPredicateOnField(FEC, StdNistValidatorErrorEnum.STD_ERR_FEC, validateFieldFEC());
  }

  protected Predicate<String> validateFieldFEC() {
    return field -> {
      List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(field);
      return isEmpty(listOfItems) || listOfItems.stream().allMatch(validateFieldFECItems());
    };
  }

  protected Predicate<List<String>> validateFieldFECItems() {
    // 10.033 FEC - Corrected minimum number of feature points to 2, as it is an open path.
    final int nopMinVal = 2;
    final int nopMaxVal = 99;

    return items -> {
      List<String> listOfHPO_VPO = emptyList();
      if (items.size() >= 4) {
        listOfHPO_VPO = items.subList(2, items.size());
      }
      return items.size() >= 4
          && stringInCollection(getAllowedValuesForFEC(getStandard())).test(items.get(0)) // FCC
          && isNumberBetween(nopMinVal, nopMaxVal).test(items.get(1)) // NOP
          && equalTo(0).test(listOfHPO_VPO.size() % 2) // must be a pair of values
          && areNumbersBetween(0, 99999).test(listOfHPO_VPO) // repeat HPO & VPO
      ;
    };
  }

  protected void checkForFieldFSB10_035() {
    checkForOptionalButUniqueSubfields(
        RT10FieldsEnum.FSB,
        StdNistValidatorErrorEnum.STD_ERR_FSB,
        SubfieldRule.of("QNQ", isCharTypeWithMinLength(ANS, 1)),
        SubfieldRule.of("QAV", isHexaCodeWithLength(4)),
        SubfieldRule.of("QAP", isNumberBetween(1, 65535)),
        SubfieldRule.of("QPV", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("QCM", optional(isCharTypeWithMinLength(U, 1))),
        SubfieldRule.of("QCK", optional(isHexaCodeWithLength(64))));
  }

  protected void checkForFieldTIF10_036() {
    // Mandatory when CGA=MEDIA
    ruleFor(r -> r)
        .must(handlePredicateOnField(RT10FieldsEnum.TIF, not(emptyOrNull())))
        .when(isFieldEquals(RT10FieldsEnum.CGA, "MEDIA"))
        .handlerInvalidField(
            handlerInvalidFieldInRecordWithError(
                recordType, RT10FieldsEnum.TIF, StdNistValidatorErrorEnum.STD_ERR_TIF));

    // Optional otherwise
    checkForOptionalButUniqueSubfields(
        RT10FieldsEnum.TIF,
        StdNistValidatorErrorEnum.STD_ERR_TIF,
        SubfieldRule.of("FTY", isCharTypeWithMinMaxLength(U, 3, 127)),
        SubfieldRule.of("DEI", optional(isCharTypeWithMinMaxLength(U, 1, 1000))));
  }

  @Override
  protected void checkForFieldPID10_048() {
    // 10.048 PID - Deprecated field (since 2025)
    checkForEmptyField(PID);
  }

  @Override
  protected void checkForFieldCID10_049() {
    // 10.049 CID - Deprecated field (since 2025)
    checkForEmptyField(CID);
  }

  protected void checkForFieldBRI10_199() {
    checkForGenericFieldBRI_199(RT10FieldsEnum.BRI);
  }
}
