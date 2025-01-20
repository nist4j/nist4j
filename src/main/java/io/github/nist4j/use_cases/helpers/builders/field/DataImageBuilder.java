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

import io.github.nist4j.entities.field.Data;
import io.github.nist4j.entities.field.DataBuilder;
import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.impl.DataImageImmutableImpl;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataImageBuilder extends AbstractDataBuilder<DataImage, byte[]>
    implements DataBuilder<DataImage, byte[]> {

  private byte[] imageData;

  @Override
  public DataImageImmutableImpl build() {
    return new DataImageImmutableImpl(this);
  }

  @Override
  public DataBuilder from(@NonNull DataImage dataImage) {
    return new DataImageBuilder().withValue(dataImage.getData());
  }

  @Override
  public DataBuilder withValue(byte[] value) {
    this.imageData = value;
    return this;
  }

  @Override
  public byte[] getValue() {
    return imageData;
  }

  public static Data newFieldImage(byte[] imageData) {
    return new DataImageBuilder().withValue(imageData).build();
  }
}
