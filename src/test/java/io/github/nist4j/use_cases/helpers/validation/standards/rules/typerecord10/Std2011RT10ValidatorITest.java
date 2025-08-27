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
package io.github.nist4j.use_cases.helpers.validation.standards.rules.typerecord10;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.record.NistRecord;
import io.github.nist4j.entities.tuple.Pair;
import io.github.nist4j.entities.validation.NistValidationError;
import io.github.nist4j.enums.records.RT10FieldsEnum;
import io.github.nist4j.test_utils.ImportFileUtils;
import io.github.nist4j.use_cases.ReadNistFile;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@Slf4j
class Std2011RT10ValidatorITest {

  private static final ReadNistFile readNistFile = new ReadNistFile();
  private final Std2011RT10Validator validator = new Std2011RT10Validator();

  @SuppressWarnings("UnnecessaryLocalVariable")
  private static Stream<Arguments> getReferencesFilesWithRT10() {
    List<String> files =
        asList(
            "/references/type-10-14-17-piv-index-iris.an2",
            "/references/type-10-branded-tattoo-mark.an2",
            "/references/type-10-sap10.an2",
            "/references/type-10-scar-face-sap50.an2",
            "/references/type-10-tattoo-face-sap20.an2",
            "/references/type-10-tattoo-zoom.an2");
    Stream<Arguments> listOfRT10Records =
        files.stream()
            .map(name -> Pair.of(name, ImportFileUtils.getFileFromResource(name)))
            .map(
                f -> {
                  try {
                    return Pair.of(
                        f.getLeft(),
                        readNistFile.execute(Files.newInputStream(f.getRight().toPath())));
                  } catch (IOException e) {
                    return null;
                  }
                })
            .filter(Objects::nonNull)
            .flatMap(
                nist ->
                    nist.getRight().getRT10FacialAndSmtImageRecords().stream()
                        .map(r -> Pair.of(nist.getLeft(), r)))
            .map(
                record ->
                    Arguments.of(
                        record.getLeft(),
                        record.getRight().getFieldText(RT10FieldsEnum.IDC).orElse("NOIDC"),
                        record.getRight()));

    return listOfRT10Records;
  }

  @SuppressWarnings("SwitchStatementWithTooFewBranches")
  @ParameterizedTest()
  @MethodSource("getReferencesFilesWithRT10")
  void validate_with_rt10_files_should_return_full_list_of_mandatory_errors(
      String filename, String idc, NistRecord nistRecord) {
    // Given
    // When
    List<NistValidationError> errorsNist = validator.validate(nistRecord).getErrors();

    List<NistValidationError> filteredErrorNistFor2011 = new ArrayList<>();
    for (NistValidationError error : errorsNist) {
      switch (error.getCode()) {
        case "STD_ERR_SMT_RT10_FORMAT":
          // In 2011 SMT requires character A but AS in 2013 and after...
          break;
        default:
          log.info(
              "Error '{}' on field '{}' with value '{}' ({})",
              error.getCode(),
              error.getFieldName(),
              error.getValueFound(),
              error.getMessage());
          filteredErrorNistFor2011.add(error);
      }
    }

    assertThat(filteredErrorNistFor2011).isEmpty();
  }
}
