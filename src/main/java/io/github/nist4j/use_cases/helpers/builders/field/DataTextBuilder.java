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
package io.github.nist4j.use_cases.helpers.builders.field;

import static java.util.Arrays.asList;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataBuilder;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;

@NoArgsConstructor
@Setter
public class DataTextBuilder extends AbstractDataBuilder<DataText, String>
    implements DataBuilder<DataText, String> {

  private String text;

  @Override
  public DataText build() {
    return new DataTextImmutableImpl(this);
  }

  @Override
  public DataBuilder from(DataText dataText) {
    return new DataTextBuilder().withValue(dataText.getData());
  }

  @Override
  public DataBuilder withValue(String value) {
    this.text = value;
    return this;
  }

  @Deprecated
  public DataBuilder withValues(List<String> values) {
    this.text = SubFieldToStringConverter.fromList(values);
    return this;
  }

  public DataBuilder withListOfList(List<List<String>> values) {
    this.text = SubFieldToStringConverter.fromListOfList(values);
    return this;
  }

  public DataBuilder withPairs(List<Pair<String, String>> pairsValues) {
    this.text = SubFieldToStringConverter.fromListOfPairs(pairsValues);
    return this;
  }

  public DataBuilder withItems(List<String> items) {
    this.text = SubFieldToStringConverter.fromItems(items);
    return this;
  }

  public DataBuilder withListUsingSplitByRS(List<String> items) {
    this.text = SubFieldToStringConverter.fromListUsingSplitByRS(items);
    return this;
  }

  @Override
  public String getValue() {
    return text;
  }

  public static Data newFieldText(Integer intVal) {
    return newFieldText(String.valueOf(intVal));
  }

  public static Data newFieldText(String text) {
    return new DataTextBuilder().withValue(text).build();
  }

  public static Data newSubfieldsFromPairs(List<Pair<String, String>> listOfPairs) {
    return new DataTextBuilder().withPairs(listOfPairs).build();
  }

  public static Data newSubfieldsFromListOfList(List<List<String>> listOfList) {
    return new DataTextBuilder().withListOfList(listOfList).build();
  }

  public static Data newSubfieldsFromItems(List<String> items) {
    return new DataTextBuilder().withItems(items).build();
  }

  public static Data newSubfieldsFromItems(String... items) {
    return new DataTextBuilder().withItems(asList(items)).build();
  }

  public static Data newSubfieldsFromListUsingSplitByRS(List<String> elements) {
    return new DataTextBuilder().withListUsingSplitByRS(elements).build();
  }

  public static Data newSubfieldsFromListUsingSplitByRS(String... elements) {
    return new DataTextBuilder().withListUsingSplitByRS(asList(elements)).build();
  }
}
