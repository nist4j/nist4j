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

public class ImmutablePairImpl<L, R> extends Pair<L, R> {
  private static final ImmutablePairImpl<?, ?> NULL = new ImmutablePairImpl<>(null, null);

  private static final long serialVersionUID = 2143803375708357112L;

  public static <L, R> ImmutablePairImpl<L, R> nullPair() {
    return (ImmutablePairImpl<L, R>) NULL;
  }

  public static <L, R> ImmutablePairImpl<L, R> of(final L left, final R right) {
    return left != null || right != null ? new ImmutablePairImpl<>(left, right) : nullPair();
  }

  public final L left;

  public final R right;

  public ImmutablePairImpl(final L left, final R right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public L getLeft() {
    return left;
  }

  @Override
  public R getRight() {
    return right;
  }

  @Override
  public R setValue(final R value) {
    throw new UnsupportedOperationException();
  }
}
