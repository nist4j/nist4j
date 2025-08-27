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
package io.github.nist4j.use_cases.helpers.validation.function;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

public class FunctionBuilderTest {

  @Test
  public void testSuccessAndThen() {

    final FunctionalTest functionalTest = new FunctionalTest();

    final FunctionalTest.FunctionalTestInner01 functionalTestInner01 =
        new FunctionalTest.FunctionalTestInner01();

    final FunctionalTest.FunctionalTestInner01.FunctionalTestInner02 functionalTestInner02 =
        new FunctionalTest.FunctionalTestInner01.FunctionalTestInner02();

    functionalTestInner02.setValue(1);

    functionalTestInner01.setFunctionalTestInner02(functionalTestInner02);

    functionalTest.setFunctionalTestInner(functionalTestInner01);

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest::getFunctionalTestInner01)
            .andThen(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .andThen(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue);

    assertThat(function.apply(functionalTest)).isEqualTo(1);
  }

  @Test
  public void testSuccessCompose() {

    final FunctionalTest functionalTest = new FunctionalTest();

    final FunctionalTest.FunctionalTestInner01 functionalTestInner01 =
        new FunctionalTest.FunctionalTestInner01();

    final FunctionalTest.FunctionalTestInner01.FunctionalTestInner02 functionalTestInner02 =
        new FunctionalTest.FunctionalTestInner01.FunctionalTestInner02();

    functionalTestInner02.setValue(1);

    functionalTestInner01.setFunctionalTestInner02(functionalTestInner02);

    functionalTest.setFunctionalTestInner(functionalTestInner01);

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue)
            .compose(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .compose(FunctionalTest::getFunctionalTestInner01);

    assertThat(function.apply(functionalTest)).isEqualTo(1);
  }

  @Test
  public void testSuccessAndThenWhenNullValueInChain01() {

    final FunctionalTest functionalTest = new FunctionalTest();

    final FunctionalTest.FunctionalTestInner01 functionalTestInner01 =
        new FunctionalTest.FunctionalTestInner01();

    functionalTest.setFunctionalTestInner(functionalTestInner01);

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest::getFunctionalTestInner01)
            .andThen(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .andThen(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue);

    assertThat(function.apply(functionalTest)).isNull();
  }

  @Test
  public void testSuccessAndThenWhenNullValueInChain02() {

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest::getFunctionalTestInner01)
            .andThen(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .andThen(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue);

    assertThat(function.apply(null)).isNull();
  }

  @Test
  public void testSuccessAndThenWhenNullValueInChain03() {

    final FunctionalTest functionalTest = new FunctionalTest();

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest::getFunctionalTestInner01)
            .andThen(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .andThen(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue);

    assertThat(function.apply(functionalTest)).isNull();
  }

  @Test
  public void testSuccessComposeWhenNullValueInChain01() {

    final FunctionalTest functionalTest = new FunctionalTest();

    final FunctionalTest.FunctionalTestInner01 functionalTestInner01 =
        new FunctionalTest.FunctionalTestInner01();

    functionalTest.setFunctionalTestInner(functionalTestInner01);

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue)
            .compose(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .compose(FunctionalTest::getFunctionalTestInner01);

    assertThat(function.apply(functionalTest)).isNull();
  }

  @Test
  public void testSuccessComposeWhenNullValueInChain02() {

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue)
            .compose(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .compose(FunctionalTest::getFunctionalTestInner01);

    assertThat(function.apply(null)).isNull();
  }

  @Test
  public void testSuccessComposeWhenNullValueInChain03() {

    final FunctionalTest functionalTest = new FunctionalTest();

    final Function<FunctionalTest, Integer> function =
        FunctionBuilder.of(FunctionalTest.FunctionalTestInner01.FunctionalTestInner02::getValue)
            .compose(FunctionalTest.FunctionalTestInner01::getFunctionalTestInner02)
            .compose(FunctionalTest::getFunctionalTestInner01);

    assertThat(function.apply(functionalTest)).isNull();
  }

  @Getter
  static class FunctionalTest {

    private FunctionalTestInner01 functionalTestInner01;

    public void setFunctionalTestInner(final FunctionalTestInner01 functionalTestInner01) {
      this.functionalTestInner01 = functionalTestInner01;
    }

    @Setter
    @Getter
    static class FunctionalTestInner01 {

      private FunctionalTestInner02 functionalTestInner02;

      static class FunctionalTestInner02 {

        private Integer integer;

        public Integer getValue() {
          return integer;
        }

        public void setValue(final Integer value) {
          integer = value;
        }
      }
    }
  }
}
