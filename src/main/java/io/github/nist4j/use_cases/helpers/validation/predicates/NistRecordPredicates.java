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
package io.github.nist4j.use_cases.helpers.validation.predicates;

import static br.com.fluentvalidator.predicate.StringPredicate.stringEquals;
import static br.com.fluentvalidator.predicate.StringPredicate.stringInCollection;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.enums.records.interfaces.IFieldTypeEnum;
import java.util.List;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NistRecordPredicates {

  public static Predicate<NistRecord> isFieldAbsent(IFieldTypeEnum field) {
    return r -> !r.getFieldText(field).isPresent();
  }

  public static Predicate<NistRecord> isFieldInCollection(
      IFieldTypeEnum field, List<String> allowedValues) {
    return r -> stringInCollection(allowedValues).test(getFieldStringOrNull(field, r));
  }

  public static Predicate<NistRecord> isFieldEquals(IFieldTypeEnum field, String allowedValue) {
    return r -> stringEquals(allowedValue).test(getFieldStringOrNull(field, r));
  }

  public static String getFieldStringOrNull(IFieldTypeEnum field, NistRecord r) {
    return r.getFieldText(field).orElse(null);
  }

  public static Data getFieldImageOrNull(IFieldTypeEnum field, NistRecord r) {
    return r.getFieldData(field).orElse(null);
  }
}
