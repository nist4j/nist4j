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
package io.github.nist4j.use_cases.helpers.converters;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import io.github.nist4j.exceptions.InvalidFormatNist4jException;
import io.github.nist4j.use_cases.helpers.NistDecoderHelper;
import java.util.*;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubFieldToStringConverter {

  /**
   * Deprecate use fromItems or fromListOfList
   * <br/>
   * Cause this methode is not reversible with toList
   *
   * @param listOfStrings list of elements
   * @return elements separated with US or RS
   */
  @Deprecated
  public static String fromList(@NonNull List<String> listOfStrings) {
    if (listOfStrings.size() % 2 == 1) {
      throw new InvalidFormatNist4jException("The list of subfields must be pair");
    }
    StringBuilder subFieldValues = new StringBuilder();
    for (int i = 0; i < listOfStrings.size(); i += 2) {
      boolean isTheLastValue = ((i + 2) >= listOfStrings.size());
      subFieldValues
          .append(listOfStrings.get(i))
          .append(NistDecoderHelper.SEP_US)
          .append(listOfStrings.get(i + 1))
          .append(isTheLastValue ? "" : NistDecoderHelper.SEP_RS);
    }
    return subFieldValues.toString();
  }

  /**
   * Method to split string with RS separator
   *
   * @param subfieldString to split
   * @return list of elements
   */
  public static List<String> toListUsingSplitByRS(String subfieldString) {
    if (isEmpty(subfieldString)) {
      return emptyList();
    }
    return Arrays.asList(subfieldString.split(String.valueOf(NistDecoderHelper.SEP_RS)));
  }

  /**
   * Method to merge string with RS separator
   *
   * @param listOfItems to split
   * @return string of items separated by RS
   */
  public static String fromListUsingSplitByRS(List<String> listOfItems) {
    if (listOfItems.isEmpty()) {
      return EMPTY;
    }
    return listOfItems.stream().collect(joining(String.valueOf(NistDecoderHelper.SEP_RS)));
  }

  /**
   * Convert string to pair of elements
   *
   * @param subfieldString string containing US separator
   * @return List of Pairs items
   */
  public static List<Pair<String, String>> toListOfPairs(String subfieldString) {
    if (isEmpty(subfieldString)) {
      return emptyList();
    }
    return toListUsingSplitByRS(subfieldString).stream()
        .map(
            str -> {
              String[] items = str.split(String.valueOf(NistDecoderHelper.SEP_US));
              if (items.length > 1) {
                return Pair.of(items[0], items[1]);
              } else {
                return Pair.of(items[0], EMPTY);
              }
            })
        .collect(Collectors.toList());
  }

  /**
   * Convert string to pair of elements
   *
   * @param listOfPairs string containing US separator
   * @return List of Pairs items
   */
  public static String fromListOfPairs(@NonNull List<Pair<String, String>> listOfPairs) {
    if (listOfPairs.isEmpty()) {
      return EMPTY;
    }
    return listOfPairs.stream()
        .map(pair -> pair.getKey() + NistDecoderHelper.SEP_US + pair.getValue())
        .collect(joining(String.valueOf(NistDecoderHelper.SEP_RS)));
  }

  /**
   * Methode converting split US or RS to a flat List of String
   *
   * @param subfieldString subfield value
   * @return list of fields
   */
  public static List<String> toList(String subfieldString) {
    if (isEmpty(subfieldString)) {
      return emptyList();
    }
    List<String> listOfStrings = new ArrayList<>();
    int posEnd, posStart = 0;
    for (int i = 0; i < subfieldString.length(); i++) {
      if (subfieldString.charAt(i) == NistDecoderHelper.SEP_US
          || subfieldString.charAt(i) == NistDecoderHelper.SEP_RS) {
        posEnd = i;
        listOfStrings.add(subfieldString.substring(posStart, posEnd));
        posStart = i + 1;
      }
    }
    if (posStart < subfieldString.length()) {
      listOfStrings.add(subfieldString.substring(posStart));
    }
    return listOfStrings;
  }

  /**
   * Methode to get an specific element corresponding to index when split
   *
   * @param subfieldValue string to split
   * @param index of element to get
   * @return element of index found
   * @throws IllegalArgumentException when null
   */
  public static Optional<String> toListAndGetByIndex(String subfieldValue, int index)
      throws IllegalArgumentException {
    if (subfieldValue == null) {
      return Optional.empty();
    }
    List<String> subFields = SubFieldToStringConverter.toList(subfieldValue);
    if (subFields.size() > index) {
      return Optional.ofNullable(subFields.get(index));
    }
    return Optional.empty();
  }

  /**
   * Convert subfield to List of List
   *
   * @param subfieldString containing US and RS
   * @return list of list of String
   */
  public static List<List<String>> toListOfList(String subfieldString) {
    if (subfieldString == null) {
      return emptyList();
    }
    List<List<String>> resListOfList = new ArrayList<>();

    List<String> itemsRs = toListUsingSplitByRS(subfieldString);
    for (String itemRs : itemsRs) {
      resListOfList.add(Arrays.asList(itemRs.split(String.valueOf(NistDecoderHelper.SEP_US))));
    }
    return resListOfList;
  }

  /**
   * Convert List of List to String seperates by US and RS
   *
   * @param listOfItems list of items
   * @return String exemple US or RS
   */
  public static String fromListOfList(List<List<String>> listOfItems) {
    if (listOfItems == null) {
      return EMPTY;
    }
    return listOfItems.stream()
        .map(item -> item.stream().collect(joining(String.valueOf(NistDecoderHelper.SEP_US))))
        .collect(joining(String.valueOf(NistDecoderHelper.SEP_RS)));
  }

  /**
   * List of items are separated by US
   *
   * @param items list
   * @return Items separated by US
   */
  public static String fromItems(List<String> items) {
    if (items == null) {
      return EMPTY;
    }
    return items.stream().collect(joining(String.valueOf(NistDecoderHelper.SEP_US)));
  }

  /**
   * List of items are separated by US
   *
   * @param items list of items
   * @return Items separated by US
   */
  public static String fromItems(String... items) {
    if (items[0] == null) {
      return EMPTY;
    }
    return fromItems(Arrays.asList(items));
  }

  /**
   * Convert string items separated with US to list
   *
   * @param items containing US separator
   * @return list of items
   */
  public static List<String> toItems(String items) {
    if (items == null) {
      return emptyList();
    }
    return Arrays.asList(items.split(String.valueOf(NistDecoderHelper.SEP_US)));
  }
}
