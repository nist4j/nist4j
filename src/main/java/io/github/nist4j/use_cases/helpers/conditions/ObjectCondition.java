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
package io.github.nist4j.use_cases.helpers.conditions;

import static java.util.Objects.isNull;

import io.github.nist4j.entities.field.DataImage;
import io.github.nist4j.entities.field.DataText;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class ObjectCondition {

  public static boolean isNotNull(Object object) {
    return !isNull(object);
  }

  public static boolean isNotEmpty(Object object) {
    return !isEmpty(object);
  }

  public static boolean isEmpty(Object object) {
    if (object == null) {
      return true;
    } else if (object instanceof CharSequence) {
      return ((CharSequence) object).length() == 0;
    } else if (isArray(object)) {
      return Array.getLength(object) == 0;
    } else if (object instanceof Collection) {
      return ((Collection<?>) object).isEmpty();
    } else if (object instanceof Map) {
      return ((Map<?, ?>) object).isEmpty();
    } else if (object instanceof Optional) {
      return !((Optional<?>) object).isPresent();
    } else if (object instanceof DataText) {
      return ((DataText) object).getLength() == 0;
    } else if (object instanceof DataImage) {
      return ((DataImage) object).getLength() == 0;
    } else {
      return false;
    }
  }

  public static boolean isArray(Object object) {
    return object != null && object.getClass().isArray();
  }
}
