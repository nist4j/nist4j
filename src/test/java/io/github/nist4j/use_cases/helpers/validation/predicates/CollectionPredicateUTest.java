/*
 * Copyright (C) 2019 Sopra Steria.
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

import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.empty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasAny;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasItem;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasItems;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasSize;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasSizeBetween;
import static io.github.nist4j.use_cases.helpers.validation.predicates.CollectionPredicate.hasSizeBetweenInclusive;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.junit.jupiter.api.Test;

public class CollectionPredicateUTest {

  @Test
  public void testNullCollectionEmpty() {
    assertTrue(empty().test(null));
  }

  @Test
  public void testNullCollectionHasItems() {
    assertFalse(hasItems(new Integer[] {1}).test(null));
    assertFalse(hasItems((Integer[]) null).test(null));
    assertFalse(hasItems((Integer[]) null).test(Collections.singletonList(1)));
    assertFalse(hasItems((Collection<Integer>) null).test(null));
    assertFalse(hasItems((Collection<Integer>) null).test(Collections.singletonList(1)));
  }

  @Test
  public void testNullCollectionHasItem() {
    assertFalse(hasItem(1).test(null));
    assertFalse(hasItem((Integer) null).test(null));
    assertFalse(hasItem((Integer) null).test(Collections.singletonList(1)));
  }

  @Test
  public void testNullCollectionHasAny() {
    assertFalse(hasAny(new Integer[] {1}).test(null));
    assertFalse(hasAny((Integer[]) null).test(null));
    assertFalse(hasAny((Integer[]) null).test(Collections.singletonList(1)));
    assertFalse(hasAny((Collection<Integer>) null).test(Collections.singletonList(1)));
    assertFalse(hasAny((Collection<Integer>) null).test(null));
  }

  @Test
  public void testNullCollectionHasSize() {
    assertFalse(hasSize(1).test(null));
    assertFalse(hasSize(null).test(null));
    assertFalse(hasSize(null).test(Collections.singletonList(1)));
  }

  @Test
  public void testCollectionEmty() {
    final String element = "1";
    assertTrue(empty().test(Collections.emptyList()));
    assertFalse(empty().test(Collections.singletonList(element)));
  }

  @Test
  public void testCollectionHasItems() {
    final String element = "1";
    assertTrue(hasItems(new String[] {element}).test(Collections.singletonList(element)));
    assertFalse(hasItems(new String[] {"1"}).test(Collections.emptyList()));
  }

  @Test
  public void testCollectionHasItem() {
    final String element = "1";
    assertTrue(hasItem(element).test(Collections.singletonList(element)));
    assertFalse(hasItem("1").test(Collections.emptyList()));
  }

  @Test
  public void testCollectionHasAny() {
    final String element = "1";
    assertTrue(hasAny(new String[] {element}).test(Collections.singletonList(element)));
    assertFalse(hasAny(new String[] {"1"}).test(Collections.emptyList()));
  }

  @Test
  public void testCollectionHasSize() {
    final String element = "1";
    assertTrue(hasSize(1).test(Collections.singletonList(element)));
    assertFalse(hasSize(1).test(Collections.emptyList()));
  }

  @Test
  public void testCollectionHasSizeBetween() {
    final List<Integer> asList = Arrays.asList(1, 2, 3, 4);
    assertTrue(hasSizeBetween(fn -> asList, 2, 5).test(asList));
    assertFalse(hasSizeBetween(fn -> asList, 1, 2).test(asList));
  }

  @Test
  public void testCollectionHasSizeBetween01() {
    assertTrue(hasSizeBetween(2, 5).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetween(1, 2).test(Arrays.asList(1, 2, 3, 4)));
  }

  @Test
  public void testCollectionHasSizeBetweenInclusive() {
    final List<Integer> asList = Arrays.asList(1, 2, 3, 4);
    assertTrue(hasSizeBetweenInclusive(fn -> asList, 1, 4).test(asList));
    assertTrue(hasSizeBetweenInclusive(fn -> asList, 1, 10).test(asList));
    assertTrue(hasSizeBetweenInclusive(fn -> asList, 4, 4).test(asList));
    assertFalse(hasSizeBetweenInclusive(fn -> asList, 4, 1).test(asList));
    assertFalse(hasSizeBetweenInclusive(fn -> asList, 1, 2).test(asList));
  }

  @Test
  public void testCollectionHasSizeBetweenInclusive01() {
    assertTrue(hasSizeBetweenInclusive(1, 4).test(Arrays.asList(1, 2, 3, 4)));
    assertTrue(hasSizeBetweenInclusive(1, 10).test(Arrays.asList(1, 2, 3, 4)));
    assertTrue(hasSizeBetweenInclusive(4, 4).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetweenInclusive(4, 1).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetweenInclusive(1, 2).test(Arrays.asList(1, 2, 3, 4)));
  }

  @Test
  public void testCollectionHasSizeBetweenNull() {
    assertFalse(hasSizeBetween(null, null).test(null));
    assertFalse(hasSizeBetween(1, null).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetween(null, 2).test(null));
    assertFalse(hasSizeBetween(1, 2).test(null));
  }

  @Test
  public void testCollectionHasSizeBetweenNull01() {
    final List<Integer> asList = Arrays.asList(1, 2, 3, 4);
    assertFalse(hasSizeBetween(null, null, null).test(null));
    assertFalse(hasSizeBetween(fn -> asList, null, 2).test(asList));
    assertFalse(hasSizeBetween(fn -> asList, 1, null).test(null));
    assertFalse(hasSizeBetween(fn -> asList, 1, 2).test(null));
  }

  @Test
  public void testCollectionHasSizeBetweenInclusiveNull01() {
    final List<Integer> asList = Arrays.asList(1, 2, 3, 4);
    assertFalse(hasSizeBetweenInclusive(fn -> asList, null, null).test(null));
    assertFalse(hasSizeBetweenInclusive(fn -> asList, 1, null).test(asList));
    assertFalse(hasSizeBetweenInclusive(fn -> asList, null, 4).test(null));
    assertFalse(hasSizeBetweenInclusive(fn -> asList, 1, 2).test(null));
  }

  @Test
  public void testCollectionHasSizeBetweenInclusiveNull() {
    assertFalse(hasSizeBetweenInclusive(null, null).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetweenInclusive(1, null).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetweenInclusive(null, 4).test(Arrays.asList(1, 2, 3, 4)));
    assertFalse(hasSizeBetweenInclusive(4, 1).test(null));
  }

  @Test
  public void testObjectNullCollectionEmpty() {
    assertTrue(empty(TestClass::getSource).test(new TestClass(null)));
  }

  @Test
  public void testObjectNullCollectionHasItems() {
    assertFalse(hasItems(TestClass::getSource, new Integer[] {1}).test(null));
    assertFalse(hasItems(TestClass::getSource, (Integer[]) null).test(null));
    assertFalse(
        hasItems(TestClass::getSource, (Integer[]) null)
            .test(new TestClass(Collections.singletonList(1))));
    assertFalse(hasItems(TestClass::getSource, (Collection<Integer>) null).test(null));
    assertFalse(
        hasItems(TestClass::getSource, (Collection<Integer>) null)
            .test(new TestClass(Collections.singletonList(1))));
  }

  @Test
  public void testObjectNullCollectionHasItem() {
    assertFalse(hasItem(TestClass::getSource, 1).test(null));
    assertFalse(hasItem(TestClass::getSource, null).test(null));
    assertFalse(
        hasItem(TestClass::getSource, null).test(new TestClass(Collections.singletonList(1))));
  }

  @Test
  public void testObjectNullCollectionHasAny() {
    assertFalse(hasAny(TestClass::getSource, new Integer[] {1}).test(null));
    assertFalse(hasAny(TestClass::getSource, (Integer[]) null).test(null));
    assertFalse(
        hasAny(TestClass::getSource, (Integer[]) null)
            .test(new TestClass(Collections.singletonList(1))));
    assertFalse(
        hasAny(TestClass::getSource, (Collection<Integer>) null)
            .test(new TestClass(Collections.singletonList(1))));
    assertFalse(hasAny(TestClass::getSource, (Collection<Integer>) null).test(null));
  }

  @Test
  public void testObjectNullCollectionHasSize() {
    assertFalse(hasSize(TestClass::getSource, 1).test(null));
    assertFalse(hasSize(TestClass::getSource, (Integer) null).test(null));
    assertFalse(
        hasSize(TestClass::getSource, (Integer) null)
            .test(new TestClass(Collections.singletonList(1))));
    assertFalse(hasSize(TestClass::getSource, (Integer) null).test(new TestClass(null)));
  }

  @Test
  public void testObjectNullCollectionHasSize2() {
    assertFalse(hasSize(TestClass::getSource, fn -> 1).test(null));
    assertFalse(hasSize(TestClass::getSource, TestClass::getSize).test(null));
    assertFalse(
        hasSize(TestClass::getSource, TestClass::getSize)
            .test(new TestClass(Collections.singletonList(1))));
    assertFalse(hasSize(TestClass::getSource, TestClass::getSize).test(new TestClass(null)));
  }

  @Test
  public void testObjectCollectionEmty() {
    assertTrue(empty(TestClass::getSource).test(new TestClass(Collections.emptyList())));
    assertFalse(empty(TestClass::getSource).test(new TestClass(Collections.singletonList(1))));
  }

  @Test
  public void testObjectCollectionHasItems() {
    final Integer element = 1;
    assertTrue(
        hasItems(TestClass::getSource, new Integer[] {element})
            .test(new TestClass(Collections.singletonList(element))));
    assertFalse(
        hasItems(TestClass::getSource, new Integer[] {1})
            .test(new TestClass(Collections.emptyList())));
  }

  @Test
  public void testObjectCollectionHasItem() {
    final Integer element = 1;
    assertTrue(
        hasItem(TestClass::getSource, element)
            .test(new TestClass(Collections.singletonList(element))));
    assertFalse(hasItem(TestClass::getSource, 1).test(new TestClass(Collections.emptyList())));
  }

  @Test
  public void testObjectCollectionHasAny() {
    final Integer element = 1;
    assertTrue(
        hasAny(TestClass::getSource, new Integer[] {element})
            .test(new TestClass(Collections.singletonList(element))));
    assertFalse(
        hasAny(TestClass::getSource, new Integer[] {1})
            .test(new TestClass(Collections.emptyList())));
  }

  @Test
  public void testObjectCollectionHasSize() {
    assertTrue(hasSize(TestClass::getSource, 1).test(new TestClass(Collections.singletonList(1))));
    assertFalse(hasSize(TestClass::getSource, 1).test(new TestClass(Collections.emptyList())));
  }

  @Test
  public void testObjectCollectionHasSize2() {
    assertTrue(
        hasSize(TestClass::getSource, TestClass::getSize)
            .test(new TestClass(Collections.singletonList(1), 1)));
    assertFalse(
        hasSize(TestClass::getSource, TestClass::getSize)
            .test(new TestClass(Collections.emptyList(), 1)));
  }

  @Getter
  static class TestClass {

    private final Integer size;
    private final Collection<Integer> source;

    private TestClass(final Collection<Integer> source, final Integer size) {
      this.source = source;
      this.size = size;
    }

    private TestClass(final Collection<Integer> source) {
      this(source, null);
    }
  }
}
