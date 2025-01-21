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
package io.github.nist4j;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

@Slf4j
public class LayeredArchitectureTest {
  private static final String ROOT_PATH = "io.github.nist4j";
  private static final String USE_CASES_PATH = ROOT_PATH + ".use_cases";
  private static final String ENTITIES_PATH = ROOT_PATH + ".entities";
  private static final String ENUMS_PATH = ROOT_PATH + ".enums";
  private static final String EXCEPTIONS_PATH = ROOT_PATH + ".exceptions";

  /**
   * use_cases -> entities
   * (use_cases,entities) -> enums
   * (use_cases,entities,enums) -> exceptions
   */
  @Test
  void expected_architects_layers_are_well_respected() {
    assertThatLayer(ENTITIES_PATH)
        .shouldNotAccessToPackage(USE_CASES_PATH)
        .haveToAccessToPackage(ENUMS_PATH)
        .haveToAccessToPackage(EXCEPTIONS_PATH);

    assertThatLayer(ENUMS_PATH)
        .shouldNotAccessToPackage(USE_CASES_PATH)
        // enums can references Interfaces localize in entities packages
        .shouldNotAccessToPackageExcept(
            ENTITIES_PATH,
            "io.github.nist4j.entities.field.DataImage",
            "io.github.nist4j.entities.field.DataText",
            "io.github.nist4j.entities.field.Data")
        .haveToAccessToPackage(EXCEPTIONS_PATH);

    assertThatLayer(EXCEPTIONS_PATH)
        .shouldNotAccessToPackage(USE_CASES_PATH)
        .shouldNotAccessToPackage(ENTITIES_PATH)
        .shouldNotAccessToPackage(ENUMS_PATH);
  }

  @Test
  void layers_architectures_should_failed_if_layers_are_not_respected() {
    assertThrows(
        AssertionFailedError.class,
        () -> assertThatLayer(USE_CASES_PATH).shouldNotAccessToPackage(ENTITIES_PATH));
    assertThrows(
        AssertionFailedError.class,
        () -> assertThatLayer(ENTITIES_PATH).shouldNotAccessToPackage(ENUMS_PATH));
  }

  private static LayeredArchTest assertThatLayer(String name) {
    return new LayeredArchTest(name);
  }

  private static class LayeredArchTest {
    private final Set<String> dependencies;
    private final String fromPackageName;

    public LayeredArchTest(String fromPackageName) {
      this.fromPackageName = fromPackageName;
      this.dependencies = scanDependencies(fromPackageName);
    }

    private Set<String> scanDependencies(String fromPackageName) {
      try (ScanResult scanResult =
          new ClassGraph().acceptPackages(ROOT_PATH).enableInterClassDependencies().scan()) {

        return scanResult.getClassDependencyMap().entrySet().stream()
            .filter((e) -> classNameStartsWith(fromPackageName, e))
            .filter(this::ignoreTestClasses)
            .flatMap((e) -> e.getValue().getNames().stream())
            .collect(Collectors.toSet());
      }
    }

    private boolean classNameStartsWith(
        String fromPackageName, Map.Entry<ClassInfo, ClassInfoList> e) {
      return e.getKey().getName().startsWith(fromPackageName);
    }

    private boolean ignoreTestClasses(Map.Entry<ClassInfo, ClassInfoList> e) {
      return !e.getKey().getName().endsWith("Test");
    }

    public LayeredArchTest shouldNotAccessToPackage(String toPackageName) {

      dependencies.stream()
          .peek((toClassName) -> log.debug("checking {} -> {}", fromPackageName, toClassName))
          .forEach(
              (toClassName) ->
                  assertThat(toClassName.startsWith(toPackageName))
                      .withFailMessage(
                          String.format("%s -> %s is forbidden", fromPackageName, toClassName))
                      .isFalse());

      return this;
    }

    public LayeredArchTest shouldNotAccessToPackageExcept(
        String toPackageName, String... ignoreClasses) {

      Set<String> ignoreArrays = new HashSet<>(Arrays.asList(ignoreClasses));

      dependencies.stream()
          .peek((toClassName) -> log.debug("checking {} -> {}", fromPackageName, toClassName))
          .filter(((toClassName) -> ignoreIfClassNameIncludes(ignoreArrays, toClassName)))
          .forEach(
              (toClassName) ->
                  assertThat(toClassName.startsWith(toPackageName))
                      .withFailMessage(
                          String.format("%s -> %s is forbidden", fromPackageName, toClassName))
                      .isFalse());

      return this;
    }

    private boolean ignoreIfClassNameIncludes(Set<String> ignoreArrays, String toClassName) {
      return ignoreArrays.stream().noneMatch(toClassName::contains);
    }

    public LayeredArchTest haveToAccessToPackage(String toPackageName) {
      long numberOfAccess =
          dependencies.stream().filter((d) -> d.startsWith(toPackageName)).count();

      assertThat(numberOfAccess)
          .withFailMessage(
              String.format("%s -> %s have to be present", fromPackageName, toPackageName))
          .isGreaterThan(0);

      return this;
    }
  }
}
