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
package io.github.nist4j.use_cases.helpers.validation.context;

import io.github.nist4j.entities.validation.NistValidationError;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class ValidationContext {

  private static final ThreadLocal<Context> threadLocal = new ThreadLocal<>();

  private ValidationContext() {
    super();
  }

  public static Context get() {
    if (Objects.isNull(threadLocal.get())) {
      threadLocal.set(new Context());
    }
    return threadLocal.get();
  }

  public static void remove() {
    threadLocal.remove();
  }

  /** Context of validation */
  public static final class Context {

    private final Map<String, Object> properties = new ConcurrentHashMap<>();

    private final Queue<NistValidationError> nistValidationErrors = new ConcurrentLinkedQueue<>();

    public void addErrors(final Collection<NistValidationError> errs) {
      nistValidationErrors.addAll(errs);
    }

    public void setProperty(final String property, final Object value) {
      if (Objects.nonNull(property)) {
        properties.put(property, value);
      }
    }

    public ValidationResult getValidationResult() {
      ValidationContext.remove();
      return nistValidationErrors.isEmpty()
          ? ValidationResult.ok()
          : ValidationResult.fail(new ArrayList<>(nistValidationErrors));
    }

    public <P> P getProperty(final String property, final Class<P> clazz) {
      return clazz.cast(properties.getOrDefault(property, null));
    }
  }
}
