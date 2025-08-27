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
package io.github.nist4j.use_cases.helpers.validation.predicates;

import static io.github.nist4j.use_cases.helpers.conditions.ObjectCondition.isEmpty;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.greaterThan;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ComparablePredicate.lessThan;
import static io.github.nist4j.use_cases.helpers.validation.predicates.LogicalPredicate.*;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.equalObject;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.notNullValue;
import static io.github.nist4j.use_cases.helpers.validation.predicates.ObjectPredicate.nullValue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public final class StringPredicate {

  public static Predicate<String> isAlpha() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(isNumeric -> isNumeric.chars().allMatch(Character::isLetter));
  }

  public static <T> Predicate<T> isAlpha(final Function<T, String> source) {
    return PredicateBuilder.<T>from(notNullValue()).and(obj -> isAlpha().test(source.apply(obj)));
  }

  public static Predicate<String> isAlphaNumeric() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(isNumeric -> isNumeric.chars().allMatch(Character::isLetterOrDigit));
  }

  public static <T> Predicate<T> isAlphaNumeric(final Function<T, String> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> isAlphaNumeric().test(source.apply(obj)));
  }

  public static <T> Predicate<T> isDate(final Function<T, String> source, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> isDate(pattern).test(source.apply(obj)));
  }

  public static Predicate<String> isDate(final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(date -> not(stringEmptyOrNull()).test(pattern))
        .and(
            date -> {
              try {
                final DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                LocalDate.parse(date, dateFormat);
                return true;
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> isDateTime(
      final Function<T, String> source, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> isDateTime(pattern).test(source.apply(obj)));
  }

  public static Predicate<String> isDateTime(final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(isDateTime -> not(stringEmptyOrNull()).test(pattern))
        .and(
            isDateTime -> {
              try {
                final DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                LocalDateTime.parse(isDateTime, dateFormat);
                return true;
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static Predicate<String> isNumber() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(
            isNumber -> {
              try {
                new BigDecimal(isNumber);
              } catch (final NumberFormatException e) {
                return false;
              }
              return true;
            });
  }

  public static <T> Predicate<T> isNumber(final Function<T, String> source) {
    return PredicateBuilder.<T>from(notNullValue()).and(obj -> isNumber().test(source.apply(obj)));
  }

  public static Predicate<String> isNumeric() {
    return PredicateBuilder.from(not(stringEmptyOrNull()))
        .and(isNumeric -> isNumeric.chars().allMatch(Character::isDigit));
  }

  public static <T> Predicate<T> isNumeric(final Function<T, String> source) {
    return PredicateBuilder.<T>from(notNullValue()).and(obj -> isNumeric().test(source.apply(obj)));
  }

  public static <T> Predicate<T> isTime(final Function<T, String> source, final String pattern) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> isTime(pattern).test(source.apply(obj)));
  }

  public static Predicate<String> isTime(final String pattern) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(time -> not(stringEmptyOrNull()).test(pattern))
        .and(
            time -> {
              try {
                final DateTimeFormatter dateFormat =
                    DateTimeFormatter.ofPattern(pattern).withResolverStyle(ResolverStyle.STRICT);
                LocalTime.parse(time, dateFormat);
                return true;
              } catch (final IllegalArgumentException | DateTimeParseException ex) {
                return false;
              }
            });
  }

  public static <T> Predicate<T> stringContains(
      final Function<T, String> source, final String str) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringContains(str).test(source.apply(obj)));
  }

  public static Predicate<String> stringContains(final String str) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringContains -> notNullValue().test(str))
        .and(stringContains -> stringContains.contains(str));
  }

  public static Predicate<String> stringEmptyOrNull() {
    return PredicateBuilder.<String>from(is(nullValue())).or(String::isEmpty);
  }

  public static <T> Predicate<T> stringEmptyOrNull(final Function<T, String> source) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringEmptyOrNull().test(source.apply(obj)));
  }

  public static <T> Predicate<T> stringEquals(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringEquals(source, target.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> stringEquals(
      final Function<T, String> source, final String value) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(source.apply(obj)))
        .and(obj -> stringEquals(value).test(source.apply(obj)));
  }

  public static <T> Predicate<T> stringEquals(final String value) {
    return PredicateBuilder.<T>from(notNullValue()).and(obj -> obj.equals(value));
  }

  public static <T> Predicate<T> stringEqualsIgnoreCase(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringEqualsIgnoreCase(source, target.apply(obj)).test(obj));
  }

  public static <T> Predicate<T> stringEqualsIgnoreCase(
      final Function<T, String> source, final String value) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(source.apply(obj)))
        .and(obj -> stringEqualsIgnoreCase(value).test(source.apply(obj)));
  }

  public static Predicate<String> stringEqualsIgnoreCase(final String value) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(obj -> notNullValue().test(value))
        .and(obj -> obj.equalsIgnoreCase(value));
  }

  public static <T> Predicate<T> stringMatches(
      final Function<T, String> source, final String regex) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringMatches(regex).test(source.apply(obj)));
  }

  public static Predicate<String> stringMatches(final String regex) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringMatches -> notNullValue().test(regex))
        .and(stringMatches -> stringMatches.matches(regex));
  }

  public static <T> Predicate<T> stringSize(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target.apply(obj)))
        .and(obj -> stringSize(target.apply(obj).length()).test(source.apply(obj)));
  }

  public static <T> Predicate<T> stringSize(final Function<T, String> source, final Integer size) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringSize(size).test(source.apply(obj)));
  }

  public static Predicate<String> stringSize(final Integer size) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringSize -> notNullValue().test(size))
        .and(stringSize -> equalObject(size).test(stringSize.length()));
  }

  public static <T> Predicate<T> stringSizeBetween(
      final Function<T, String> source, final Integer minSize, final Integer maxSize) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringSizeBetween(minSize, maxSize).test(source.apply(obj)));
  }

  public static Predicate<String> stringSizeBetween(final Integer minSize, final Integer maxSize) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringSizeGreaterThanOrEqual(minSize).and(stringSizeLessThanOrEqual(maxSize)));
  }

  public static <T> Predicate<T> stringSizeGreaterThan(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target.apply(obj)))
        .and(obj -> stringSizeGreaterThan(target.apply(obj).length()).test(source.apply(obj)));
  }

  public static <T> Predicate<T> stringSizeGreaterThan(
      final Function<T, String> source, final Integer size) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringSizeGreaterThan(size).test(source.apply(obj)));
  }

  public static Predicate<String> stringSizeGreaterThan(final Integer size) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringSizeGreaterThan -> notNullValue().test(size))
        .and(stringSizeGreaterThan -> greaterThan(size).test(stringSizeGreaterThan.length()));
  }

  public static <T> Predicate<T> stringSizeGreaterThanOrEqual(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(stringSizeGreaterThan(source, target).or(stringSize(source, target)));
  }

  public static <T> Predicate<T> stringSizeGreaterThanOrEqual(
      final Function<T, String> source, final Integer size) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(stringSizeGreaterThan(source, size).or(stringSize(source, size)));
  }

  public static Predicate<String> stringSizeGreaterThanOrEqual(final Integer size) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringSizeGreaterThan(size).or(stringSize(size)));
  }

  public static <T> Predicate<T> stringSizeLessThan(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(target.apply(obj)))
        .and(obj -> stringSizeLessThan(target.apply(obj).length()).test(source.apply(obj)));
  }

  public static <T> Predicate<T> stringSizeLessThan(
      final Function<T, String> source, final Integer size) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> stringSizeLessThan(size).test(source.apply(obj)));
  }

  public static Predicate<String> stringSizeLessThan(final Integer size) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringSizeLessThan -> notNullValue().test(size))
        .and(stringSizeLessThan -> lessThan(size).test(stringSizeLessThan.length()));
  }

  public static <T> Predicate<T> stringSizeLessThanOrEqual(
      final Function<T, String> source, final Function<T, String> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(stringSizeLessThan(source, target).or(stringSize(source, target)));
  }

  public static <T> Predicate<T> stringSizeLessThanOrEqual(
      final Function<T, String> source, final Integer size) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(stringSizeLessThan(source, size).or(stringSize(source, size)));
  }

  public static Predicate<String> stringSizeLessThanOrEqual(final Integer size) {
    return PredicateBuilder.<String>from(notNullValue())
        .and(stringSizeLessThan(size).or(stringSize(size)));
  }

  public static <T extends String> Predicate<T> stringInCollection(
      final Collection<String> collection) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(obj -> notNullValue().test(collection))
        .and(collection::contains);
  }

  public static <T> Predicate<T> stringInCollection(
      final Function<T, String> source, final Collection<String> collection) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(obj -> notNullValue().test(collection))
        .and(obj -> stringInCollection(collection).test(source.apply(obj)));
  }

  public static <T> Predicate<T> stringInCollection(
      final String source, final Function<T, Collection<String>> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(target))
        .and(obj -> stringInCollection(target.apply(obj)).test(source));
  }

  public static <T> Predicate<T> stringInCollection(
      final Function<T, String> source, final Function<T, Collection<String>> target) {
    return PredicateBuilder.<T>from(notNullValue())
        .and(notNullValue(source))
        .and(notNullValue(target))
        .and(obj -> stringInCollection(target.apply(obj)).test(source.apply(obj)));
  }

  public static Predicate<String> isNumberBetween(int min, int max) {
    return mandatory(s -> toInt(s).map(val -> val >= min && val <= max).orElse(false));
  }

  public static Optional<Integer> toInt(String str) {
    try {
      final Integer intVal = Integer.parseInt(str);
      return Optional.of(intVal);
    } catch (final IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  public static Predicate<List<String>> areNumbersBetween(int min, int max) {
    return items -> {
      if (isEmpty(items)) {
        return false;
      }
      for (String item : items) {
        boolean isValid = toInt(item).map(intVal -> intVal >= min && intVal <= max).orElse(false);
        if (!isValid) {
          return false;
        }
      }
      return true;
    };
  }

  public static Predicate<List<String>> areInCollection(List<String> allowsValues) {
    return items -> {
      if (isEmpty(items)) {
        return false;
      }
      for (String item : items) {
        boolean isValid = allowsValues.contains(item);
        if (!isValid) {
          return false;
        }
      }
      return true;
    };
  }

  public static Predicate<String> stringStartingWith(String txt) {
    return mandatory(
        str -> {
          try {
            return str.startsWith(txt);
          } catch (final IllegalArgumentException ex) {
            return false;
          }
        });
  }

  public static Predicate<String> stringNotStartingWith(String txt) {
    return optional(
        str -> {
          try {
            return !str.startsWith(txt);
          } catch (final IllegalArgumentException ex) {
            return false;
          }
        });
  }

  public static Predicate<String> isRealNumberBetween(double min, double max) {
    return mandatory(s -> toReal(s).map(val -> val >= min && val <= max).orElse(false));
  }

  private static Optional<Double> toReal(String str) {
    try {
      final Double realVal = Double.parseDouble(str);
      return Optional.of(realVal);
    } catch (final IllegalArgumentException e) {
      return Optional.empty();
    }
  }

  private StringPredicate() {
    super();
  }
}
