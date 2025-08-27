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
package io.github.nist4j.use_cases.helpers.validation.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import org.junit.jupiter.api.Test;

@SuppressWarnings("StringOperationCanBeSimplified")
public class HandlerInvalidFieldTest {

  @Test
  public void testSuccessDefaultHandle() {

    final HandlerInvalidField<String> handlerInvalidField = new HandlerInvalidField<String>() {};

    assertThat(handlerInvalidField.handle(new String())).isNotNull();
    assertThat(handlerInvalidField.handle(new String())).isInstanceOf(Collection.class);
    assertThat(handlerInvalidField.handle(new String())).isEmpty();
  }

  @Test
  public void testSuccessDefaultHandle2() {

    final HandlerInvalidField<String> handlerInvalidField = new HandlerInvalidField<String>() {};

    assertThat(handlerInvalidField.handle(new Object(), new String())).isNotNull();
    assertThat(handlerInvalidField.handle(new Object(), new String()))
        .isInstanceOf(Collection.class);
    assertThat(handlerInvalidField.handle(new Object(), new String())).isEmpty();
  }
}
