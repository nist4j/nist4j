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
package io.github.nist4j.use_cases.helpers.validation.exceptions;

import io.github.nist4j.exceptions.Nist4jException;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationContext;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import lombok.Getter;

@Getter
public abstract class Nist4jValidationException extends Nist4jException {

  private static final long serialVersionUID = 2274879814700248645L;

  private final transient ValidationResult validationResult;

  protected Nist4jValidationException(final ValidationResult validationResult) {
    super(validationResult.toString());
    this.validationResult = validationResult;
  }

  public static <T extends Nist4jValidationException> RuntimeException create(
      final Class<T> exceptionClass) {
    return create(exceptionClass, ValidationContext.get().getValidationResult());
  }

  public static <T extends Nist4jValidationException> RuntimeException create(
      final Class<T> exceptionClass, final ValidationResult validationResult) {
    try {
      final Constructor<? extends Nist4jValidationException> ctor =
          exceptionClass.getConstructor(ValidationResult.class);
      return ctor.newInstance(validationResult);
    } catch (final NoSuchMethodException
        | SecurityException
        | InstantiationException
        | IllegalAccessException
        | IllegalArgumentException
        | InvocationTargetException e) {
      return new RuntimeException(
          "Constructor in class not found (ValidationResult validationResult)", e);
    }
  }
}
