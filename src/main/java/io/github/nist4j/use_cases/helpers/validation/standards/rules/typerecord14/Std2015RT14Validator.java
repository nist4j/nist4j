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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord14;

import static io.github.nist4j.enums.RecordTypeEnum.RT14;
import static io.github.nist4j.enums.records.RT13FieldsEnum.IMP;
import static io.github.nist4j.enums.records.RT14FieldsEnum.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistRecordPredicate.isFieldPresent;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.RecordTypeEnum;
import io.github.nist4j.enums.records.RT14FieldsEnum;
import java.util.function.Predicate;

public class Std2015RT14Validator extends Std2013RT14Validator {

  protected Std2015RT14Validator() {
    this(DEFAULT_OPTIONS_FOR_VALIDATION);
  }

  public Std2015RT14Validator(NistOptions nistOptions) {
    super(nistOptions);
  }

  @Override
  protected NistStandardEnum getStandard() {
    return NistStandardEnum.ANSI_NIST_ITL_2015;
  }

  @Override
  public void rules() {
    // Common rules on fields
    checkForFieldLEN14_001();
    checkForFieldIDC14_002();
    // IMP 14_003 in sub Validator
    checkForFieldSRC14_004();
    checkForFieldFCD14_005();
    // HLL 14_006 in sub Validator
    // VLL 14_007 in sub Validator
    checkForFieldSLC14_008();
    // THPS 14_009 in sub Validator
    // TVPS 14_010 in sub Validator
    // CGA 14_011 in sub Validator
    // BPX 14_012 in sub Validator
    checkForFieldFGP14_013();
    checkForFieldPPD14_014();
    checkForFieldPPC14_015();
    // SHPS 14_016 in sub validator
    // SVPS 14_017 in sub validator
    checkForFieldAMP14_018();
    // 14.019 reserved for future used
    checkForFieldCOM14_020();
    checkForFieldSEG14_021();
    checkForFieldNQM14_022();
    checkForFieldSQM14_023();
    checkForFieldFQM14_024();
    checkForFieldASEG14_025();
    checkForFieldSCF14_026();
    checkForFieldSIF14_027();
    // 14.028 - 14.029 reserved for future used
    checkForFieldDDM14_030();
    checkForFieldFAP14_031();
    // 14.032 - 14.045 reserved for future used
    checkForFieldSUB14_046();
    checkForFieldCON14_047();
    // 14.048 - 14.199 reserved for future used
    // 14.200 - 14.900 USER-DEFINED FIELDS
    checkForFieldFCT14_901(); // since 2013
    checkForFieldANN14_902();
    checkForFieldDUI14_903();
    checkForFieldMMS14_904();
    // 14.905 - 14.992 reserved for future used
    checkForFieldSAN14_993();
    // 14.994 reserved for future used
    checkForFieldASC14_995();
    checkForFieldHAS14_996();
    checkForFieldSOR14_997();
    checkForFieldGEO14_998();
    // DATA 14_999 in sub Validator

    // Conditional rules
    ruleFor(r -> r)
        .whenever(r -> hasImageOrExternalFile(r))
        .withValidator(new Std2015RT14WithImageValidator(nistOptions, RT14))
        .whenever(r -> !hasImageOrExternalFile(r))
        .withValidator(new Std2015RT14WithoutImageValidator(nistOptions, RT14));
  }

  protected void checkForFieldFCT14_901() {
    // since 2013
    Predicate<NistRecord> conditionOptionalField = isFieldPresent(FCT);
    checkForGenericFieldFCT_901(FCT, IMP, getStandard(), conditionOptionalField);
  }

  protected boolean hasImageOrExternalFile(NistRecord record14) {
    return record14.getFieldImage(RT14FieldsEnum.DATA).isPresent()
        || record14.getFieldText(RT14FieldsEnum.EFR).isPresent();
  }

  public class Std2015RT14WithImageValidator extends Std2011RT14WithImageValidator {

    @SuppressWarnings("SameParameterValue")
    protected Std2015RT14WithImageValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      super.rules();
      subCheckForFieldSHPS14_016();
      subCheckForFieldSVPS14_017();
    }

    protected void subCheckForFieldSVPS14_017() {
      checkForOptionalButNumericFieldBetween(RT14FieldsEnum.SVPS, 1, 99999);
    }

    protected void subCheckForFieldSHPS14_016() {
      checkForOptionalButNumericFieldBetween(RT14FieldsEnum.SHPS, 1, 99999);
    }
  }

  public class Std2015RT14WithoutImageValidator extends Std2011RT14WithoutImageValidator {
    @SuppressWarnings("SameParameterValue")
    protected Std2015RT14WithoutImageValidator(NistOptions nistOptions, RecordTypeEnum recordType) {
      super(nistOptions, recordType);
    }

    @Override
    public void rules() {
      super.rules();
      checkForEmptyField(IMP);
      checkForEmptyField(SHPS);
      checkForEmptyField(SVPS);
    }
  }
}
