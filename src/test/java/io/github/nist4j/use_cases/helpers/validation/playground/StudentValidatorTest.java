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
package io.github.nist4j.use_cases.helpers.validation.playground;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.use_cases.helpers.validation.Validator;
import io.github.nist4j.use_cases.helpers.validation.context.ValidationResult;
import io.github.nist4j.use_cases.helpers.validation.playground.model.Student;
import io.github.nist4j.use_cases.helpers.validation.playground.validator.StudentValidatorAnotherWay01Nist4j;
import io.github.nist4j.use_cases.helpers.validation.playground.validator.StudentValidatorAnotherWay02Nist4j;
import io.github.nist4j.use_cases.helpers.validation.playground.validator.StudentValidatorAnotherWay03Nist4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentValidatorTest {

  private Student.Builder studentBuilder;
  private Student student;

  @BeforeEach
  public void setUp() {
    studentBuilder = Student.Builder.newInstance();
  }

  @Test
  public void shouldValidateStudentValidatorAnotherWay01() {

    final Validator<Student> studentValidator = new StudentValidatorAnotherWay01Nist4j();

    student = studentBuilder.build();
    final ValidationResult result = studentValidator.validate(student);
    assertThat(result.isValid()).isFalse();
  }

  @Test
  public void shouldValidateStudentValidatorAnotherWay02() {

    final Validator<Student> studentValidator = new StudentValidatorAnotherWay02Nist4j();

    student = studentBuilder.build();
    final ValidationResult result = studentValidator.validate(student);
    assertThat(result.isValid()).isFalse();
  }

  @Test
  public void shouldValidateStudentValidatorAnotherWay03() {

    final Validator<Student> studentValidator = new StudentValidatorAnotherWay03Nist4j();

    student = studentBuilder.build();
    final ValidationResult result = studentValidator.validate(student);
    assertThat(result.isValid()).isFalse();
  }
}
