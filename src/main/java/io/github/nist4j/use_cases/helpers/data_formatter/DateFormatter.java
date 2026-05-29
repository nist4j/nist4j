/*
 * Copyright (C) 2026 Sopra Steria.
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
package io.github.nist4j.use_cases.helpers.data_formatter;

import static java.time.format.DateTimeFormatter.ofPattern;

import io.github.nist4j.exceptions.InvalidDataFormatNist4jException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.NonNull;

public class DateFormatter {
  private static final DateTimeFormatter LD_FORMATTER = ofPattern("yyyyMMdd");
  private static final DateTimeFormatter LDT_FORMATTER = ofPattern("yyyyMMddhhmmss");
  private static final DateTimeFormatter UTC_DT_FORMATTER = ofPattern("yyyyMMddHHmmssX");

  /**
   * YYYYMMDD localdate
   *
   * @param date to format
   * @return YYYYMMDD date in string
   */
  public static String localDate(@NonNull LocalDate date) {
    return date.format(LD_FORMATTER);
  }

  /**
   * YYYYMMDD partial date e.g. 20241200
   *
   * @param year to format
   * @param month to format
   * @param day to format
   * @return YYYYMMDD partial date in string
   */
  public static String partialLocalDate(int year, int month, int day) {
    String yearString = String.format("%04d", year);
    String monthString = String.format("%02d", month);
    String dayString = String.format("%02d", day);

    if (yearString.length() != 4 || year < 0) {
      throw new InvalidDataFormatNist4jException(
          "year for partialLocalDate must be a positive 4 digits and founded : " + yearString);
    }
    if (monthString.length() != 2 || month < 0 || month > 12) {
      throw new InvalidDataFormatNist4jException(
          "month for partialLocalDate must be a positive 2 digits and founded : " + monthString);
    }
    if (dayString.length() != 2 || day < 0 || day > 31) {
      throw new InvalidDataFormatNist4jException(
          "day for partialLocalDate must be a positive 2 digits and founded : " + dayString);
    }
    return yearString + monthString + dayString;
  }

  /**
   * YYYYMMDDhhmmss localDatetime
   *
   * @param dateTime to format
   * @return YYYYMMDDhhmmss dateTime in string
   */
  public static String localDateTime(@NonNull LocalDateTime dateTime) {
    return dateTime.format(LDT_FORMATTER);
  }

  /**
   * YYYYMMDDhhmmssZ utcDateTime
   *
   * @param zoneDateTime to format
   * @return YYYYMMDDhhmmssZ dateTime in string
   */
  public static String utcDateTime(@NonNull ZonedDateTime zoneDateTime) {
    return zoneDateTime.withZoneSameInstant(ZoneOffset.UTC).format(UTC_DT_FORMATTER);
  }
}
