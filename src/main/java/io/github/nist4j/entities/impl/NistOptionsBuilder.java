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
package io.github.nist4j.entities.impl;

import io.github.nist4j.entities.NistOptions;
import java.nio.charset.Charset;

@SuppressWarnings("unused")
public interface NistOptionsBuilder {
  NistOptions build();

  NistOptionsBuilder from(NistOptions nistOptions);

  boolean isCalculateLENOnBuild();

  NistOptionsBuilder isCalculateLENOnBuild(boolean value);

  boolean isCalculateCNTOnBuild();

  NistOptionsBuilder isCalculateCNTOnBuild(boolean value);

  boolean isDCSfieldUsedToDetectCharset();

  NistOptionsBuilder isDCSfieldUsedToDetectCharset(boolean value);

  Charset getCharset();

  NistOptionsBuilder charset(Charset value);
}
