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
package io.github.nist4j.test_utils;

import io.github.nist4j.entities.NistFile;
import io.github.nist4j.exceptions.ErrorDecodingNist4jException;
import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.use_cases.ReadNistFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ImportFileUtils {

  private final ReadNistFile nistDecoder =
      new ReadNistFile(ReadNistFile.DEFAULT_OPTIONS_FOR_READ_FILE);

  public static File getFileFromResource(String path) throws InvalidFormatNist4jException {
    URL url = ImportFileUtils.class.getResource(path);
    if (url == null) {
      throw new InvalidFormatNist4jException("File not found " + path);
    }
    return new File(url.getFile());
  }

  public static List<File> getFilesFromResources(String path, String regex)
      throws InvalidFormatNist4jException {
    URL url = ImportFileUtils.class.getResource(path);
    if (url == null) {
      throw new InvalidFormatNist4jException("Dir not found" + path);
    }
    File dir = new File(url.getFile());
    File[] files = dir.listFiles();
    if (files == null) {
      throw new InvalidFormatNist4jException("No files found in dir" + path);
    }
    return Arrays.stream(files)
        .filter(file -> file.getName().matches(regex))
        .collect(Collectors.toList());
  }

  public NistFile createNistFileFromFile(File file)
      throws IOException, ErrorDecodingNist4jException {
    return nistDecoder.execute(Files.newInputStream(file.toPath()));
  }
}
