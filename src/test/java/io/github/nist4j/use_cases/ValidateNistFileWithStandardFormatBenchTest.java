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
package io.github.nist4j.use_cases;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.test_utils.ImportFileUtils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.NoArgsConstructor;
import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@NoArgsConstructor
public class ValidateNistFileWithStandardFormatBenchTest {

  public static final String TEST_FILE = "/standards/AN2015/pass/pass-ees_fp_1391.nist";

  @Benchmark
  public void validateBenchmark() throws IOException {
    ValidateNistFileWithStandardFormat validateNist = new ValidateNistFileWithStandardFormat();
    ImportFileUtils importFileUtils = new ImportFileUtils();
    NistFile nist =
        importFileUtils.createNistFileFromFile(ImportFileUtils.getFileFromResource(TEST_FILE));

    assertThat(validateNist.execute(nist)).isEmpty();
  }
}
