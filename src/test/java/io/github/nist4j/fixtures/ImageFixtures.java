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
package io.github.nist4j.fixtures;

import static io.github.nist4j.test_utils.ImportFileUtils.getFileFromResource;

import java.io.IOException;
import java.nio.file.Files;

public class ImageFixtures {

  public static byte[] nist4jLogo() throws IOException {
    String path = "/fake/nist4j-logo.png";
    return Files.readAllBytes(getFileFromResource(path).toPath());
  }

  // docker run -i --rm -v ./:/opt/work biometrictechnologies/biometric-converter-cli convert -i
  // sample_image.wsq -ti WSQ -o sample_image.png -to PNG
  public static byte[] fingerPrintImageWSQ() throws IOException {
    String path = "/fake/sample_image.wsq";
    return Files.readAllBytes(getFileFromResource(path).toPath());
  }

  public static byte[] fingerPrintImagePNG() throws IOException {
    String path = "/fake/sample_image.png";
    return Files.readAllBytes(getFileFromResource(path).toPath());
  }
}
