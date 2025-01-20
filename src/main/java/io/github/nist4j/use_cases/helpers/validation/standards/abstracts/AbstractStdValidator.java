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

import static br.com.fluentvalidator.predicate.CollectionPredicate.empty;
import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static io.github.nist4j.enums.RecordTypeEnum.RT1;
import static io.github.nist4j.enums.validation.StdNistValidatorErrorEnum.STD_ERR_FORBIDDEN_RT;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFilePredicates.hasRecordsByType;

import br.com.fluentvalidator.Validator;
import io.github.nist4j.entities.NistFile;
import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.use_cases.helpers.validation.abstracts.AbstractNistFileValidator;

/**
 * Abstract Specification for Standard
 * Record type validator implemented : RT1, RT2 et RT14
 * others are coming soon
 */
public abstract class AbstractStdValidator extends AbstractNistFileValidator {
  protected AbstractStdValidator(NistOptions nistOptions) {
    super(nistOptions);
  }

  protected abstract NistStandardEnum getStandard();

  @Override
  public void rules() {
    // Verify forbidden records
    RecordTypeEnum.forbiddenRecordTypesByStandard(getStandard())
        .forEach(
            recordTypeEnum ->
                ruleForEach(n -> n.getRecordListByRecordTypeEnum(recordTypeEnum))
                    .must(empty())
                    .handlerInvalidField(handlerInvalidRecordsWithError(STD_ERR_FORBIDDEN_RT)));
    // RT1 check
    ruleFor(n -> n)
        // RT1 - Validator record
        .whenever(hasRecordsByType(RT1))
        .withValidator(getValidatorForRT1Records());
    // RT2 check
    ruleForEach(NistFile::getRT2UserDefinedDescriptionTextRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT2Records());
    // RT3 check
    ruleForEach(NistFile::getRT3LowResolutionGrayscaleFingerprintRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT3Records());
    // RT4 check
    ruleForEach(NistFile::getRT4HighResolutionGrayscaleFingerprintRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT4Records());
    // RT5 check
    ruleForEach(NistFile::getRT5LowResolutionBinaryFingerprintRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT5Records());
    // RT6 check
    ruleForEach(NistFile::getRT6HighResolutionBinaryFingerprintRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT6Records());
    // RT13 check
    ruleForEach(NistFile::getRT13VariableResolutionLatentImageRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT13Records());
    // RT14 check
    ruleForEach(NistFile::getRT14VariableResolutionFingerprintRecords)
        .whenever(not(nullValue()))
        .withValidator(getValidatorForRT14Records());
  }

  protected abstract Validator<NistFile> getValidatorForRT1Records();

  protected abstract Validator<NistRecord> getValidatorForRT2Records();

  protected abstract Validator<NistRecord> getValidatorForRT3Records();

  protected abstract Validator<NistRecord> getValidatorForRT4Records();

  protected abstract Validator<NistRecord> getValidatorForRT5Records();

  protected abstract Validator<NistRecord> getValidatorForRT6Records();

  protected abstract Validator<NistRecord> getValidatorForRT14Records();

  protected abstract Validator<NistRecord> getValidatorForRT13Records();
}
