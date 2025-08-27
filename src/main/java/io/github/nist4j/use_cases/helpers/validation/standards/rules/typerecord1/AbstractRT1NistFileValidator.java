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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord1;

import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.use_cases.helpers.builders.NistValidationErrorBuilderImpl.newNistValidationErrorBuilder;
import static io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter.toListOfPairs;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasSizeBetweenInclusive;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.not;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.optional;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.areCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistCharacterPredicate.isCharTypeWithMinLength;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFilePredicates.hasRecordsByType;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.getFieldStringOrNull;
import static io.github.nist4j.use_cases.helpers.validation.predicates.StringPredicate.*;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.CharacterTypeEnum;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.calculators.FieldCNTCalculator;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistFileValidator;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import io.github.nist4j.use_cases.helpers.validation.handlers.HandlerInvalidField;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractRT1NistFileValidator extends AbstractNistFileValidator {
  protected AbstractRT1NistFileValidator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @SuppressWarnings("unused")
  protected abstract NistStandardEnum getStandard();

  private static final NistOptionsImpl NIST_OPTIONS_CALCULATE_LEN_CNT =
      NistOptionsImpl.builder().isCalculateCNTOnBuild(true).isCalculateLENOnBuild(true).build();
  private final FieldCNTCalculator fieldCNTCalculator =
      new FieldCNTCalculator(NIST_OPTIONS_CALCULATE_LEN_CNT);
  protected static final List<String> GNS_ALLOWED_VALUES =
      Collections.unmodifiableList(Arrays.asList("GENC", "ISO"));

  protected void checkForCNTField() {
    ruleFor(nist -> nist)
        .must(
            nist ->
                validateCNTField(fieldCNTCalculator.fromNistFile(nist))
                    .test(nist.getRT1TransactionInformationRecord()))
        .handlerInvalidField(
            handlerInvalidRT1FieldInNistFileWithError(
                StdNistValidatorErrorEnum.STD_ERR_CNT_CONTENT_RT1));
  }

  protected void checkForSpecialResolutionFields(
      List<RecordTypeEnum> recordsWithSpecialResolution) {
    ruleFor(nist -> nist)
        .must(fieldMatchRegexInRecord1(RT1FieldsEnum.NSR, "^\\d{2}\\.\\d{2}$"))
        .when(hasRecordsWithSpecialResolution(recordsWithSpecialResolution))
        .handlerInvalidField(
            handlerInvalidRT1FieldInNistFileWithError(
                StdNistValidatorErrorEnum.STD_ERR_NSR_WITH_RT4_RT1))
        .must(fieldMatchRegexInRecord1(RT1FieldsEnum.NSR, "^00.00$"))
        .when(not(hasRecordsWithSpecialResolution(recordsWithSpecialResolution)))
        .handlerInvalidField(
            handlerInvalidRT1FieldInNistFileWithError(
                StdNistValidatorErrorEnum.STD_ERR_NSR_NO_RT4_RT1));

    ruleFor(nist -> nist)
        .must(fieldMatchRegexInRecord1(RT1FieldsEnum.NTR, "^\\d{2}\\.\\d{2}$"))
        .when(hasRecordsWithSpecialResolution(recordsWithSpecialResolution))
        .handlerInvalidField(
            handlerInvalidRT1FieldInNistFileWithError(
                StdNistValidatorErrorEnum.STD_ERR_NTR_WITH_RT4_RT1))
        .must(fieldMatchRegexInRecord1(RT1FieldsEnum.NTR, "^00.00$"))
        .when(not(hasRecordsWithSpecialResolution(recordsWithSpecialResolution)))
        .handlerInvalidField(
            handlerInvalidRT1FieldInNistFileWithError(
                StdNistValidatorErrorEnum.STD_ERR_NTR_NO_RT4_RT1));
  }

  public static HandlerInvalidField<NistFile> handlerInvalidRT1FieldInNistFileWithError(
      INistValidationErrorEnum error) {
    return new HandlerInvalidField<NistFile>() {
      @Override
      public Collection<NistValidationError> handle(final NistFile attemptedNistFile) {
        // Fetch field value if error is on one field in particular
        String attemptedValue =
            Optional.of(error.getFieldTypeEnum())
                .flatMap(
                    fieldType ->
                        attemptedNistFile
                            .getRT1TransactionInformationRecord()
                            .getFieldText(fieldType))
                .orElse(attemptedNistFile.toString());
        return Collections.singletonList(
            newNistValidationErrorBuilder(error, attemptedValue).build());
      }
    };
  }

  public abstract static class AbstractRT1RecordValidator extends AbstractNistRecordValidator {
    private final NistStandardEnum nistStandardEnum;

    protected AbstractRT1RecordValidator(
        NistOptions nistOptions, NistStandardEnum nistStandardEnum) {
      super(nistOptions, RT1);
      this.nistStandardEnum = nistStandardEnum;
    }

    protected void checkForVERField() {
      checkCustomPredicateOnField(
          RT1FieldsEnum.VER,
          StdNistValidatorErrorEnum.STD_ERR_VER_RT1,
          stringSize(4).and(stringEquals(nistStandardEnum.getCode())));
    }

    protected void checkForDOMField() {
      checkCustomPredicateOnField(
          RT1FieldsEnum.DOM,
          StdNistValidatorErrorEnum.STD_ERR_DOM_RT1,
          optional(validateDOMField()));
    }

    protected void checkForDCSField() {
      checkCustomPredicateOnField(
          RT1FieldsEnum.DCS, StdNistValidatorErrorEnum.STD_ERR_DCS_RT1, validateDCSField());
    }

    protected void checkForANMField() {
      checkCustomPredicateOnField(
          RT1FieldsEnum.ANM, StdNistValidatorErrorEnum.STD_ERR_ANM_DAN_RT1, validateANMSubfield(0));
      checkCustomPredicateOnField(
          RT1FieldsEnum.ANM,
          StdNistValidatorErrorEnum.STD_ERR_ANM_OAN_RT1,
          optional(validateANMSubfield(1)));
    }
  }

  private static Predicate<NistRecord> validateCNTField(List<Pair<String, String>> expectedCNT) {
    return r -> {
      List<Pair<String, String>> tocCNTStr =
          toListOfPairs(getFieldStringOrNull(RT1FieldsEnum.CNT, r));
      boolean isNumbers =
          tocCNTStr.stream()
              .allMatch(p -> isNumeric().test(p.getKey()) && isNumeric().test(p.getValue()));
      if (!isNumbers) {
        return false;
      }
      List<Pair<Integer, Integer>> tocCNTInt =
          tocCNTStr.stream()
              .map(p -> Pair.of(toInt(p.getKey()).orElse(-1), toInt(p.getValue()).orElse(-1)))
              .collect(Collectors.toList());
      return expectedCNT.size() == tocCNTInt.size()
          && expectedCNT.stream()
              .map(p -> Pair.of(toInt(p.getKey()).orElse(-2), toInt(p.getValue()).orElse(-2)))
              .collect(Collectors.toSet())
              .containsAll(tocCNTInt);
    };
  }

  private static Predicate<NistFile> hasRecordsWithSpecialResolution(List<RecordTypeEnum> list) {
    return nist -> list.stream().anyMatch(rt -> hasRecordsByType(rt).test(nist));
  }

  private static Predicate<NistFile> fieldMatchRegexInRecord1(
      IFieldTypeEnum iFieldTypeEnum, String regex) {
    return nist ->
        stringMatches(regex)
            .test(getFieldStringOrNull(iFieldTypeEnum, nist.getRT1TransactionInformationRecord()));
  }

  protected static Predicate<String> validateDOMField() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return items.size() <= 2 && areCharTypeWithMinLength(CharacterTypeEnum.ANS, 1).test(items);
    };
  }

  private static Predicate<String> validateDCSField() {
    return field -> {
      List<String> subFields = SubFieldToStringConverter.toListUsingSplitByRS(field);
      return subFields.stream()
          .allMatch(
              subfield -> isOneCharactersEncodingValid(SubFieldToStringConverter.toList(subfield)));
    };
  }

  protected static Predicate<String> validateANMSubfield(int index) {
    return field -> {
      String firstSubString =
          SubFieldToStringConverter.toListAndGetByIndex(field, index).orElse(EMPTY);
      return stringEmptyOrNull()
          .or(isCharTypeWithMinLength(CharacterTypeEnum.ANS, 1))
          .test(firstSubString);
    };
  }

  private static boolean isOneCharactersEncodingValid(List<String> items) {
    return hasSizeBetweenInclusive(2, 3).test(new ArrayList<>(items));
  }
}
