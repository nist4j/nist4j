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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord13;

import static io.github.nist4j.enums.CharacterTypeEnum.U;
import static io.github.nist4j.enums.records.RT13FieldsEnum.RSP;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.*;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isNotEmpty;
import static io.github.nist4j.use_cases.helpers.conditions.StringCondition.EMPTY;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinMaxLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;
import static java.util.Arrays.asList;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.validation.SubfieldRule;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.records.RT13FieldsEnum;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.NistUniqueSubfieldsValidator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Std2013RT13Validator extends Std2011RT13Validator {

  private static final Set<String> RSP_RSU_ALLOWED_VALUES =
      new HashSet<>(asList("IN", "MM", "BOTH"));
  private static final Set<String> REM_MDR_ALLOWED_VALUES =
      new HashSet<>(asList("FLATBED", "FIXED", "RULER", "FORM", "EST-HUMAN", "EST-AUTO"));
  private static final Set<String> REM_KSU_ALLOWED_VALUES = new HashSet<>(asList("IN", "MM"));

  public Std2013RT13Validator(final NistOptions nistOptions) {
    super(nistOptions);
  }

  public Std2013RT13Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2013;
  }

  @Override
  public void rules() {
    // Common rules on fields
    super.rules();
    // new rules
    checkForFieldRSP13_018(); // since 2013 - new field
    checkForFieldREM13_019(); // since 2013 - new field
    checkForFieldSUB13_046(); // since 2013 - new field
    checkForFieldCON13_047(); // since 2013 - new field
  }

  protected void checkForFieldRSP13_018() {
    // Since 2013
    final int indexOfRSF = 3;
    SubfieldRule[] subfieldValidatorsWhenRSFIsEmptyThenOtherFieldMustNot =
        asList(
                SubfieldRule.of("RSU", stringInCollection(RSP_RSU_ALLOWED_VALUES)),
                SubfieldRule.of("RSM", isCharTypeWithMinMaxLength(U, 1, 50)),
                SubfieldRule.of("RSO", isCharTypeWithMinMaxLength(U, 1, 50)),
                SubfieldRule.of("RSF", optional(stringEmptyOrNull())))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(RSP).orElse(EMPTY))
        .whenever(s -> fieldIsPresentAndSubfieldEmpty(s, indexOfRSF))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                RSP,
                STD_ERR_RSP_1,
                subfieldValidatorsWhenRSFIsEmptyThenOtherFieldMustNot));

    SubfieldRule[] subfieldValidatorsWhenRSFIsNotEmptyOtherMustBeEmpty =
        asList(
                SubfieldRule.of("RSU", optional(stringEmptyOrNull())),
                SubfieldRule.of("RSM", optional(stringEmptyOrNull())),
                SubfieldRule.of("RSO", optional(stringEmptyOrNull())),
                SubfieldRule.of("RSF", isCharTypeWithMinMaxLength(U, 1, 99)))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(RSP).orElse(EMPTY))
        .whenever(s -> fieldIsPresentAndSubfieldNotEmpty(s, indexOfRSF))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                RSP,
                STD_ERR_RSP_2,
                subfieldValidatorsWhenRSFIsNotEmptyOtherMustBeEmpty));
  }

  protected boolean fieldIsPresentAndSubfieldEmpty(
      String fieldValue, @SuppressWarnings("SameParameterValue") int index) {
    if (isEmpty(fieldValue)) {
      return false;
    }
    List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(fieldValue);
    if (listOfItems.isEmpty()) {
      return false;
    }
    if (listOfItems.get(0).size() <= index) {
      return true;
    }
    return isEmpty(listOfItems.get(0).get(index));
  }

  protected boolean fieldIsPresentAndSubfieldNotEmpty(
      String fieldValue, @SuppressWarnings("SameParameterValue") int index) {
    if (isEmpty(fieldValue)) {
      return false;
    }
    List<List<String>> listOfItems = SubFieldToStringConverter.toListOfList(fieldValue);
    if (listOfItems.isEmpty()) {
      return false;
    }
    if (listOfItems.get(0).size() <= index) {
      return false;
    }
    return isNotEmpty(listOfItems.get(0).get(index));
  }

  protected void checkForFieldREM13_019() {
    // Since 2013
    SubfieldRule[] subfieldValidatorsWhenRSUisRULERthenOtherFieldsAreMandatory =
        asList(
                SubfieldRule.of("RSU", stringInCollection(REM_MDR_ALLOWED_VALUES)),
                SubfieldRule.of("KSL", isRealNumberBetween(0.01, 999.00)),
                SubfieldRule.of("KSU", stringInCollection(REM_KSU_ALLOWED_VALUES)),
                SubfieldRule.of("SXA", isNumberBetween(0, 99999)),
                SubfieldRule.of("SYA", isNumberBetween(0, 99999)),
                SubfieldRule.of("SXB", isNumberBetween(0, 99999)),
                SubfieldRule.of("SYB", isNumberBetween(0, 99999)),
                SubfieldRule.of("COM", optional(isCharTypeWithMinMaxLength(U, 1, 126))))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(RT13FieldsEnum.REM).orElse(EMPTY))
        .whenever(s -> whenMDRisPresentAndEqualsTo(s, "RULER"))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                RT13FieldsEnum.REM,
                STD_ERR_REM_1,
                subfieldValidatorsWhenRSUisRULERthenOtherFieldsAreMandatory));

    SubfieldRule[] subfieldValidatorsWhenRSUisFORMthenOtherFieldsAreOptional =
        asList(
                SubfieldRule.of("RSU", stringInCollection(REM_MDR_ALLOWED_VALUES)),
                SubfieldRule.of("KSL", optional(isRealNumberBetween(0.01, 999.00))),
                SubfieldRule.of("KSU", optional(stringInCollection(REM_KSU_ALLOWED_VALUES))),
                SubfieldRule.of("SXA", optional(isNumberBetween(0, 99999))),
                SubfieldRule.of("SYA", optional(isNumberBetween(0, 99999))),
                SubfieldRule.of("SXB", optional(isNumberBetween(0, 99999))),
                SubfieldRule.of("SYB", optional(isNumberBetween(0, 99999))),
                SubfieldRule.of("COM", optional(isCharTypeWithMinMaxLength(U, 1, 126))))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(RT13FieldsEnum.REM).orElse(EMPTY))
        .whenever(s -> whenMDRisPresentAndEqualsTo(s, "FORM"))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                RT13FieldsEnum.REM,
                STD_ERR_REM_2,
                subfieldValidatorsWhenRSUisFORMthenOtherFieldsAreOptional));

    SubfieldRule[] subfieldValidatorsWhenRSUisNotFORMandRULERthenOtherFieldsMustBeEmpty =
        asList(
                SubfieldRule.of("RSU", stringInCollection(REM_MDR_ALLOWED_VALUES)),
                SubfieldRule.of("KSL", stringEmptyOrNull()),
                SubfieldRule.of("KSU", stringEmptyOrNull()),
                SubfieldRule.of("SXA", stringEmptyOrNull()),
                SubfieldRule.of("SYA", stringEmptyOrNull()),
                SubfieldRule.of("SXB", stringEmptyOrNull()),
                SubfieldRule.of("SYB", stringEmptyOrNull()),
                SubfieldRule.of("COM", optional(isCharTypeWithMinMaxLength(U, 1, 126))))
            .toArray(new SubfieldRule[0]);

    ruleFor(r -> r.getFieldText(RT13FieldsEnum.REM).orElse(EMPTY))
        .whenever(s -> whenMDRisPresentAndNotEqualsTo(s, "RULER", "FORM"))
        .withValidator(
            new NistUniqueSubfieldsValidator(
                this.recordType,
                RT13FieldsEnum.REM,
                STD_ERR_REM_3,
                subfieldValidatorsWhenRSUisNotFORMandRULERthenOtherFieldsMustBeEmpty));
  }

  protected boolean whenMDRisPresentAndNotEqualsTo(
      String fieldREMValue, @SuppressWarnings("SameParameterValue") String... expectedMDRValues) {
    if (isEmpty(fieldREMValue)) {
      return false;
    }
    for (String expectedMDRValue : expectedMDRValues) {
      if (fieldREMValue.startsWith(expectedMDRValue)) {
        return false;
      }
    }
    return true;
  }

  protected boolean whenMDRisPresentAndEqualsTo(String fieldREMValue, String expectedMDRValue) {
    return isNotEmpty(fieldREMValue) && fieldREMValue.startsWith(expectedMDRValue);
  }

  protected void checkForFieldSUB13_046() {
    // Since 2013
    checkForGenericFieldSUB_046(RT13FieldsEnum.SUB, getStandard());
  }

  protected void checkForFieldCON13_047() {
    // Since 2013
    checkForOptionalButCharTypeAndMinMaxLengthField(RT13FieldsEnum.CON, U, 1, 1000);
  }
}
