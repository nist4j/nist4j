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
package io.github.nist4j.use_cases.helpers.mappers;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.entities.validation.impl.NistValidationErrorImpl;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class NistValidationErrorMapperUTest {

  @Test
  void fromValidationResult_should_map_correctly() {
    // Given
    NistValidationErrorMapper mapper = new NistValidationErrorMapper();
    Error error1 =
        Error.create(
            new ErrorMapper.ErrorCustomField("TOT", "1", "RT1", null).toString(),
            "message ERR1",
            "FAKE1_ERR1",
            null);
    Error error2 =
        Error.create(
            new ErrorMapper.ErrorCustomField("MN1", "22", "RT2", "1").toString(),
            "message ERR2",
            "FAKE2_ERR1",
            null);

    NistValidationError expectedErr1 =
        NistValidationErrorImpl.builder()
            .code("FAKE1_ERR1")
            .recordName("RT1")
            .fieldName("1 (TOT)")
            .valueFound(null)
            .message("message ERR1")
            .build();
    NistValidationError expectedErr2 =
        NistValidationErrorImpl.builder()
            .code("FAKE2_ERR1")
            .recordName("RT2 (IDC:1)")
            .fieldName("22 (MN1)")
            .valueFound(null)
            .message("message ERR2")
            .build();

    // When
    List<NistValidationError> results =
        mapper.fromValidationResult(ValidationResult.fail(asList(error1, error2)));

    // Then
    assertThat(results)
        .isNotEmpty()
        .usingRecursiveComparison()
        .isEqualTo((Arrays.asList(expectedErr1, expectedErr2)));
  }
}
