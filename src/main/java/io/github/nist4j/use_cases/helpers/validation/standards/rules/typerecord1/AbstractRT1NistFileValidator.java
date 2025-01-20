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

import static br.com.fluentvalidator.predicate.CollectionPredicate.hasSizeBetweenInclusive;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.StringPredicate.*;
import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.use_cases.helpers.NistDecoderHelper.SEP_US;
import static io.github.nist4j.use_cases.helpers.mappers.ErrorMapper.toErrorOnField;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFilePredicates.hasRecordsByType;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicates.getFieldStringOrNull;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.handler.HandlerInvalidField;
import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT1FieldsEnum;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import io.github.nist4j.enums.validation.StdNistValidatorErrorEnum;
import io.github.nist4j.enums.validation.interfaces.INistValidationErrorEnum;
import io.github.nist4j.use_cases.helpers.builders.validation.NistValidationRegexBuilder;
import io.github.nist4j.use_cases.helpers.calculators.FieldCNTCalculator;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistFileValidator;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistRecordValidator;
import java.util.*;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractRT1NistFileValidator extends AbstractNistFileValidator {
  protected AbstractRT1NistFileValidator(NistOptions nistOptions) {
    super(nistOptions);
  }

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
      public Collection<Error> handle(final NistFile attemptedNistFile) {
        // Fetch field value if error is on one field in particular
        String attemptedValue =
            Optional.of(error.getFieldTypeEnum())
                .flatMap(
                    fieldType ->
                        attemptedNistFile
                            .getRT1TransactionInformationRecord()
                            .getFieldText(fieldType))
                .orElse(attemptedNistFile.toString());
        return Collections.singletonList(toErrorOnField(error, attemptedValue, Optional.empty()));
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
          stringEmptyOrNull().or(validateDOMField()));
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
          stringEmptyOrNull().or(validateANMSubfield(1)));
    }
  }

  private static Predicate<NistRecord> validateCNTField(List<String> listToc) {
    return r -> {
      String calculatedFileContent = SubFieldToStringConverter.fromList(listToc);
      String[] array =
          SubFieldToStringConverter.toListUsingSplitByRS(getFieldStringOrNull(RT1FieldsEnum.CNT, r))
              .stream()
              .map(AbstractRT1NistFileValidator::removeLeading0)
              .toArray(String[]::new);
      String[] compareToArray =
          SubFieldToStringConverter.toListUsingSplitByRS(calculatedFileContent).stream()
              .map(AbstractRT1NistFileValidator::removeLeading0)
              .toArray(String[]::new);
      return array.length == compareToArray.length
          && Arrays.stream(array)
              .allMatch(
                  subfield -> Arrays.asList(compareToArray).contains(removeLeading0(subfield)));
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

  private static Predicate<String> validateDOMField() {
    return field -> {
      List<String> items = SubFieldToStringConverter.toList(field);
      return isNotEmpty(items)
          && stringMatches(NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH).test(items.get(0))
          && (items.size() < 2
              || stringMatches(NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH)
                  .test(items.get(1)));
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

  private static Predicate<String> validateANMSubfield(int index) {
    return field -> {
      String firstSubString =
          SubFieldToStringConverter.toListAndGetByIndex(field, index).orElse(EMPTY);
      return stringEmptyOrNull()
          .or(stringMatches(NistValidationRegexBuilder.REGEXP_ANS_ANY_LENGTH))
          .test(firstSubString);
    };
  }

  private static boolean isOneCharactersEncodingValid(List<String> items) {
    return hasSizeBetweenInclusive(2, 3).test(new ArrayList<>(items));
  }

  private static String removeLeading0(String subfield) {
    List<String> items = SubFieldToStringConverter.toList(subfield);
    if (items.size() == 2) {
      // Leading zero are permitted (but not recommended) on each IDC field
      return items.get(0)
          + SEP_US
          + (Objects.equals(items.get(1), "0")
              ? items.get(1)
              : StringUtils.stripStart(items.get(1), "0"));
    }
    return subfield;
  }
}
