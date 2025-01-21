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
package io.github.nist4j.entities.field.impl;

import io.github.nist4j.entities.field.DataBuilder;
import io.github.nist4j.entities.field.DataText;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This entity is immutable so only Builder can be used be build it
 */
@EqualsAndHashCode(of = "text", cacheStrategy = EqualsAndHashCode.CacheStrategy.LAZY)
@NoArgsConstructor(access = AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public final class DataTextImmutableImpl implements DataText {

  @Getter(AccessLevel.NONE)
  private final String text;

  private DataTextImmutableImpl(String text) {
    this.text = text;
  }

  public DataTextImmutableImpl(DataBuilder<DataText, String> dataTextBuilder) {
    this.text = dataTextBuilder.getValue();
  }

  @Override
  public String getData() {
    return text;
  }

  @Override
  public int getLength() {
    return text.length();
  }

  @Override
  public String toString() {
    return "DataText(value=" + this.getData() + ")";
  }

  @Override
  public DataText deepCopy() {
    return new DataTextImmutableImpl(this.getData());
  }
}
