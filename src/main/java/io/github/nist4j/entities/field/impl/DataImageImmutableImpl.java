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
import io.github.nist4j.entities.field.DataImage;
import java.nio.ByteBuffer;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This entity is immutable so only Builder can be used be build it
 */
@NoArgsConstructor(access = AccessLevel.NONE)
@AllArgsConstructor(access = AccessLevel.NONE)
@Getter(AccessLevel.NONE)
@Setter(AccessLevel.NONE)
public final class DataImageImmutableImpl implements DataImage {

  private final ByteBuffer imageData;
  private final int length;
  private int hashCode = 0; // cache

  private DataImageImmutableImpl(byte[] imageData) {
    this.imageData = ByteBuffer.wrap(imageData).asReadOnlyBuffer();
    this.length = imageData.length;
  }

  public DataImageImmutableImpl(DataBuilder<DataImage, byte[]> dataImageBuilder) {
    byte[] imageData = dataImageBuilder.getValue();
    this.imageData = ByteBuffer.wrap(imageData).asReadOnlyBuffer();
    this.length = imageData.length;
  }

  @Override
  public byte[] getData() {
    ByteBuffer clone = ByteBuffer.allocate(imageData.capacity());
    imageData.rewind();
    clone.put(imageData);
    return clone.array();
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public String toString() {
    String valueStr = "***";
    if (length <= 0) {
      valueStr = "null";
    }
    return "DataImage(value=" + valueStr + ", length=" + length + ")";
  }

  @Override
  public DataImage deepCopy() {
    return new DataImageImmutableImpl(this.getData());
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    } else if (!(o instanceof DataImageImmutableImpl)) {
      return false;
    } else {
      DataImageImmutableImpl other = (DataImageImmutableImpl) o;
      if (this.imageData == null) {
        return other.imageData == null;
      } else {
        return Arrays.equals(this.getData(), other.getData());
      }
    }
  }

  @Override
  public int hashCode() {
    if (hashCode == 0) {
      if (this.imageData != null) {
        hashCode = Arrays.hashCode(this.getData());
      } else {
        hashCode = Integer.MIN_VALUE;
      }
    }
    return hashCode;
  }
}
