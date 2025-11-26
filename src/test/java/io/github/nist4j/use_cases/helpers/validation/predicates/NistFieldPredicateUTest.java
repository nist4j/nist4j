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

import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.validation.predicates.NistFieldPredicate.*;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.DataText;
import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import io.github.nist4j.entities.field.impl.DataTextImmutableImpl;
import io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder;
import io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder;
import org.junit.jupiter.api.Test;

class NistFieldPredicateUTest {

  @Test
  void isFieldText_should_verify_the_type_of_field() {
    // Given
    Data<String> okField = newFieldText("toto");
    DataTextImmutableImpl okFieldImmutable =
        new DataTextImmutableImpl(new DataTextBuilder().from((DataText) okField));
    Data<byte[]> badFieldCauseImage = newFieldImage(new byte[] {1, 2, 3});

    // When
    // Then
    assertThat(isFieldText().test(okField)).isTrue();
    assertThat(isFieldText().test(okFieldImmutable)).isTrue();
    assertThat(isFieldText().test(badFieldCauseImage)).isFalse();
    assertThat(isFieldText().test(null)).isFalse();
  }

  @Test
  void isFieldImage_should_verify_the_type_of_field() {
    // Given
    Data<byte[]> okField = newFieldImage(new byte[] {1, 2, 3});
    DataImageImmutableImpl okFieldImmutable =
        new DataImageImmutableImpl(new DataImageBuilder().from((DataImage) okField));
    Data<String> badFieldText = newFieldText("toto");

    // When
    // Then
    assertThat(isFieldImage().test(okField)).isTrue();
    assertThat(isFieldImage().test(okFieldImmutable)).isTrue();
    assertThat(isFieldImage().test(badFieldText)).isFalse();
    assertThat(isFieldImage().test(null)).isFalse();
  }

  @Test
  void emptyOrNull_should_verify_the_data_is_empty_or_null() {
    // Given
    Data<String> okFieldTextIsEmpty = newFieldText("");
    Data<byte[]> okFieldImageIsEmpty = newFieldImage(new byte[0]);
    Data<byte[]> badFieldImageIsNotEmpty = newFieldImage(new byte[] {1, 2, 3});
    Data<String> badFieldTextIsNotEmpty = newFieldText("not empty");

    // When
    // Then
    assertThat(emptyOrNull().test(null)).isTrue();
    assertThat(emptyOrNull().test(okFieldTextIsEmpty)).isTrue();
    assertThat(emptyOrNull().test(okFieldImageIsEmpty)).isTrue();
    assertThat(emptyOrNull().test(badFieldImageIsNotEmpty)).isFalse();
    assertThat(emptyOrNull().test(badFieldTextIsNotEmpty)).isFalse();
  }

  @Test
  void nullValue_should_verify_the_value_is_null() {
    // Given
    Data<String> okFieldTextIsEmpty = newFieldText("");
    Data<byte[]> okFieldImageIsEmpty = newFieldImage(new byte[0]);
    Data<byte[]> badFieldImageIsNotEmpty = newFieldImage(new byte[] {1, 2, 3});
    Data<String> badFieldTextIsNotEmpty = newFieldText("not empty");

    // When
    // Then
    assertThat(nullValue().test(null)).isTrue();
    assertThat(nullValue().test(okFieldTextIsEmpty)).isFalse();
    assertThat(nullValue().test(okFieldImageIsEmpty)).isFalse();
    assertThat(nullValue().test(badFieldImageIsNotEmpty)).isFalse();
    assertThat(nullValue().test(badFieldTextIsNotEmpty)).isFalse();
  }
}
