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
package io.github.nist4j.entities.tuple;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public abstract class Pair<L, R> implements Map.Entry<L, R>, Serializable {

  private static final long serialVersionUID = 4430250776028344843L;

  public static <L, R> Pair<L, R> of(final L left, final R right) {
    return ImmutablePairImpl.of(left, right);
  }

  protected Pair() {
    // empty
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Map.Entry<?, ?>) {
      final Map.Entry<?, ?> other = (Map.Entry<?, ?>) obj;
      return Objects.equals(getKey(), other.getKey())
          && Objects.equals(getValue(), other.getValue());
    }
    return false;
  }

  @Override
  public final L getKey() {
    return getLeft();
  }

  public abstract L getLeft();

  public abstract R getRight();

  @Override
  public R getValue() {
    return getRight();
  }

  @Override
  public int hashCode() {
    // see Map.Entry API specification
    return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
  }

  @Override
  public String toString() {
    return "(" + getLeft() + ',' + getRight() + ')';
  }
}
