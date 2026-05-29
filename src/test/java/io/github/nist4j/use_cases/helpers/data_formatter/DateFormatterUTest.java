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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.nist4j.exceptions.InvalidDataFormatNist4jException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

@SuppressWarnings("DataFlowIssue")
class DateFormatterUTest {

  @Test
  void localDate_should_format_to_YYYYMMDD() {
    // Given
    LocalDate localDate00050102 = LocalDate.of(5, 1, 2);
    LocalDate localDateLeapYear = LocalDate.of(2024, 2, 29);
    LocalDate localDateInFuture = LocalDate.of(2100, 1, 12);

    // When
    // Then
    assertThat(DateFormatter.localDate(LocalDate.now())).isNotNull();
    assertThat(DateFormatter.localDate(localDate00050102)).isEqualTo("00050102");
    assertThat(DateFormatter.localDate(localDateLeapYear)).isEqualTo("20240229");
    assertThat(DateFormatter.localDate(localDateInFuture)).isEqualTo("21000112");
  }

  @Test
  void localDate_with_invalid_data_should_throw_exception() {
    // Given
    // When
    // Then
    Exception exception =
        assertThrows(NullPointerException.class, () -> DateFormatter.localDate(null));
    assertThat(exception.getMessage()).isEqualTo("date is marked non-null but is null");
  }

  @Test
  void partialLocalDate_should_format_to_YYYYMMDD() {
    // Given
    // When
    // Then
    assertThat(DateFormatter.partialLocalDate(5, 1, 2)).isEqualTo("00050102");
    assertThat(DateFormatter.partialLocalDate(2024, 2, 29)).isEqualTo("20240229");
    assertThat(DateFormatter.partialLocalDate(2100, 1, 12)).isEqualTo("21000112");
    assertThat(DateFormatter.partialLocalDate(2001, 0, 12)).isEqualTo("20010012");
    assertThat(DateFormatter.partialLocalDate(2001, 12, 0)).isEqualTo("20011200");
  }

  @Test
  void partialLocalDate_with_invalid_data_should_throw_exception() {
    // Given
    // When
    // Then
    // invalid years
    Exception exceptionY1 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(-201, 2, 3));
    assertThat(exceptionY1.getMessage())
        .isEqualTo(
            "Invalid format 'year for partialLocalDate must be a positive 4 digits and founded : -201'");
    Exception exceptionY2 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(20109, 2, 3));
    assertThat(exceptionY2.getMessage())
        .isEqualTo(
            "Invalid format 'year for partialLocalDate must be a positive 4 digits and founded : 20109'");

    // invalid months
    Exception exceptionM1 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(2010, -2, 3));
    assertThat(exceptionM1.getMessage())
        .isEqualTo(
            "Invalid format 'month for partialLocalDate must be a positive 2 digits and founded : -2'");
    Exception exceptionM2 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(2010, 100, 100));
    assertThat(exceptionM2.getMessage())
        .isEqualTo(
            "Invalid format 'month for partialLocalDate must be a positive 2 digits and founded : 100'");
    Exception exceptionM3 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(2010, 13, 10));
    assertThat(exceptionM3.getMessage())
        .isEqualTo(
            "Invalid format 'month for partialLocalDate must be a positive 2 digits and founded : 13'");

    // invalid days
    Exception exceptionD1 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(2010, 2, -3));
    assertThat(exceptionD1.getMessage())
        .isEqualTo(
            "Invalid format 'day for partialLocalDate must be a positive 2 digits and founded : -3'");
    Exception exceptionD2 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(2010, 1, 32));
    assertThat(exceptionD2.getMessage())
        .isEqualTo(
            "Invalid format 'day for partialLocalDate must be a positive 2 digits and founded : 32'");
    Exception exceptionD3 =
        assertThrows(
            InvalidDataFormatNist4jException.class,
            () -> DateFormatter.partialLocalDate(2010, 2, 100));
    assertThat(exceptionD3.getMessage())
        .isEqualTo(
            "Invalid format 'day for partialLocalDate must be a positive 2 digits and founded : 100'");
  }

  @Test
  void localDateTime_should_format_to_YYYYMMDDhhmmss() {
    // Given
    LocalDateTime localDate00050102111213 = LocalDateTime.of(5, 1, 2, 11, 12, 13);
    LocalDateTime localDateLeap20240212031659 = LocalDateTime.of(2024, 2, 12, 15, 16, 59);
    LocalDateTime localDate21000112125959 = LocalDateTime.of(2100, 1, 12, 0, 59, 59);
    LocalDateTime localDate20200112125959 = LocalDateTime.of(2020, 1, 12, 23, 59, 59);

    // When
    // Then
    assertThat(DateFormatter.localDateTime(LocalDateTime.now())).isNotNull();
    assertThat(DateFormatter.localDateTime(localDate00050102111213)).isEqualTo("00050102111213");
    assertThat(DateFormatter.localDateTime(localDateLeap20240212031659))
        .isEqualTo("20240212031659");
    assertThat(DateFormatter.localDateTime(localDate21000112125959)).isEqualTo("21000112125959");
    assertThat(DateFormatter.localDateTime(localDate20200112125959)).isEqualTo("20200112115959");
  }

  @Test
  void localDateTime_with_invalid_data_should_throw_exception() {
    // Given
    // When
    // Then
    Exception exception =
        assertThrows(NullPointerException.class, () -> DateFormatter.localDateTime(null));
    assertThat(exception.getMessage()).isEqualTo("dateTime is marked non-null but is null");
  }

  @Test
  void utcDateTime_should_format_to_YYYYMMDDhhmmss() {
    // Given
    ZonedDateTime now = ZonedDateTime.now();
    LocalDateTime localDateTime20240301115322Z = LocalDateTime.of(2024, 3, 1, 11, 53, 22);
    ZonedDateTime frDateTime20240301115322Z =
        localDateTime20240301115322Z.atZone(ZoneId.of("Europe/Paris"));

    // When
    // Then
    assertThat(DateFormatter.utcDateTime(now)).isNotNull();
    assertThat(DateFormatter.utcDateTime(frDateTime20240301115322Z)).isEqualTo("20240301105322Z");
    assertThat(DateFormatter.utcDateTime(frDateTime20240301115322Z))
        .doesNotContain(DateFormatter.localDateTime(localDateTime20240301115322Z));
  }

  @Test
  void utcDateTime_with_invalid_data_should_throw_exception() {
    // Given
    // When
    // Then
    Exception exception =
        assertThrows(NullPointerException.class, () -> DateFormatter.utcDateTime(null));
    assertThat(exception.getMessage()).isEqualTo("zoneDateTime is marked non-null but is null");
  }
}
