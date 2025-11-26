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

import static io.github.nist4j.use_cases.helpers.builders.field.DataImageBuilder.newFieldImage;
import static io.github.nist4j.use_cases.helpers.builders.field.DataTextBuilder.newFieldText;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.equalObject;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.instanceOf;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;
import static org.assertj.core.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ObjectPredicateUTest {

  @Test
  public void testNullObjectEqualTo() {
    assertFalse(equalObject("1").test(null));
  }

  @Test
  public void testNullObjectInstanceOf() {
    assertFalse(instanceOf(String.class).test(null));
    assertFalse(instanceOf(null).test(null));
    assertFalse(instanceOf(null).test("he"));
  }

  @Test
  public void testEqualTo() {
    assertTrue(equalObject("1").test("1"));
    assertFalse(equalObject("1").test("he"));
  }

  @Test
  public void testInstanceOf() {
    assertTrue(instanceOf(String.class).test("1"));
    assertTrue(instanceOf(Object.class).test("1"));
    assertTrue(instanceOf(Object.class).test(1));
    assertFalse(instanceOf(String.class).test(1));
    assertFalse(instanceOf(String.class).test(1));
  }

  @Test
  public void testNullValue() {
    assertTrue(nullValue().test(null));
    assertFalse(nullValue().test("false"));
  }

  @Test
  public void testObjectNullValue() {
    assertTrue(PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(null)).test(null));
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(null))
            .test(new ObjectFrom<>(null, null)));
    assertTrue(PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(fn -> null)).test(null));
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(fn -> null))
            .test(new ObjectFrom<>(null, null)));
    assertTrue(PredicateBuilder.from(nullValue(null)).test(null));
    assertTrue(PredicateBuilder.from(nullValue(null)).test(new ObjectFrom<>(null, null)));
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(ObjectFrom::getSource))
            .test(new ObjectFrom<>(null, null)));
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(ObjectFrom::getSource)).test(null));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(nullValue(ObjectFrom::getSource))
            .test(new ObjectFrom<>(1, null)));
  }

  @Test
  public void testObjectEqualTo() {
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(
                equalObject(ObjectFrom::getSource, ObjectFrom::getTarget))
            .test(new ObjectFrom<>(1, 1)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(
                equalObject(ObjectFrom::getSource, ObjectFrom::getTarget))
            .test(new ObjectFrom<>(2, 1)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(
                equalObject(ObjectFrom::getSource, ObjectFrom::getTarget))
            .test(new ObjectFrom<>(1, 2)));
  }

  @Test
  public void testObjectEqualTo2() {
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(equalObject(ObjectFrom::getSource, 1))
            .test(new ObjectFrom<>(1, null)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(equalObject(ObjectFrom::getSource, 1))
            .test(new ObjectFrom<>(2, null)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(equalObject(ObjectFrom::getSource, 2))
            .test(new ObjectFrom<>(1, null)));
  }

  @Test
  public void testObjectNullEqualTo() {
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(
                equalObject(ObjectFrom::getSource, ObjectFrom::getTarget))
            .test(new ObjectFrom<>(1, null)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(
                equalObject(ObjectFrom::getSource, ObjectFrom::getTarget))
            .test(new ObjectFrom<>(null, 1)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(
                equalObject(ObjectFrom::getSource, ObjectFrom::getTarget))
            .test(new ObjectFrom<>(null, null)));
  }

  @Test
  public void testObjectNullEqualTo2() {
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(equalObject(ObjectFrom::getSource, null))
            .test(new ObjectFrom<>(1, null)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(equalObject(ObjectFrom::getSource, null))
            .test(new ObjectFrom<>(null, 1)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(equalObject(ObjectFrom::getSource, null))
            .test(new ObjectFrom<>(null, null)));
  }

  @Test
  public void testObjectInstanceOf() {
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(instanceOf(ObjectFrom::getSource, Integer.class))
            .test(new ObjectFrom<>(1, 1)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(instanceOf(ObjectFrom::getSource, String.class))
            .test(new ObjectFrom<>(1, 1)));
    assertTrue(
        PredicateBuilder.<ObjectFrom<Integer>>from(instanceOf(ObjectFrom::getSource, Object.class))
            .test(new ObjectFrom<>(1, 1)));
  }

  @Test
  public void testObjectNullInstanceOf() {
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(instanceOf(ObjectFrom::getSource, null))
            .test(new ObjectFrom<>(1, 1)));
    assertFalse(
        PredicateBuilder.<ObjectFrom<Integer>>from(instanceOf(ObjectFrom::getSource, null))
            .test(new ObjectFrom<>(null, 1)));
  }

  @Test
  public void isEmpty_should_verify_if_object_is_empty() {
    assertTrue(ObjectPredicate.emptyValue().test(null));
    assertTrue(ObjectPredicate.emptyValue().test(""));
    assertTrue(ObjectPredicate.emptyValue().test(new ArrayList<String>()));
    assertTrue(ObjectPredicate.emptyValue().test(new HashMap<String, Integer>()));
    assertTrue(ObjectPredicate.emptyValue().test(newFieldText("")));
    assertTrue(ObjectPredicate.emptyValue().test(newFieldImage(new byte[0])));
    assertTrue(ObjectPredicate.emptyValue().test(new byte[0]));

    assertFalse(ObjectPredicate.emptyValue().test("not empty"));
    assertFalse(ObjectPredicate.emptyValue().test(asList(new String[] {"1", "2"})));
    Map<String, String> map = new HashMap<>();
    map.put("a", "b");
    assertFalse(ObjectPredicate.emptyValue().test(map));
    assertFalse(ObjectPredicate.emptyValue().test(newFieldText("not empty")));
    assertFalse(ObjectPredicate.emptyValue().test(newFieldImage(new byte[] {1})));
    assertFalse(ObjectPredicate.emptyValue().test(new byte[] {1}));
    assertFalse(ObjectPredicate.emptyValue().test(new Object()));
  }
}
