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
package io.github.nist4j.use_cases.helpers.builders.field;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import io.github.nist4j.use_cases.helpers.converters.SubFieldToStringConverter;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

public class RT9FieldsHelper {

  public static List<String> generateItemForLegacyMRC(
      @NonNull Integer index,
      @NonNull Integer x,
      @NonNull Integer y,
      @NonNull Integer theta,
      @NonNull Integer quality,
      @NonNull String minutiaType,
      @NonNull List<String> ridgeCountDatas) {
    List<String> returnList = new ArrayList<>();
    returnList.addAll(
        asList(
            format("%03d", index),
            format("%04d%04d%03d", x, y, theta),
            format("%02d", quality),
            minutiaType));
    returnList.addAll(ridgeCountDatas);
    return returnList;
  }

  public static List<List<String>> parseLegacyMRCField(String legacyMRC) {
    return SubFieldToStringConverter.toListOfList(legacyMRC);
  }

  public static String formatLegacyMRCField(List<List<String>> listOfListItems) {
    return SubFieldToStringConverter.fromListOfList(listOfListItems);
  }
}
