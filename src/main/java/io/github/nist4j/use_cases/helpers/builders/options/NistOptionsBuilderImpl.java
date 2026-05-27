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
package io.github.nist4j.use_cases.helpers.builders.options;

import io.github.nist4j.entities.NistOptions;
import io.github.nist4j.entities.impl.NistOptionsBuilder;
import io.github.nist4j.entities.impl.NistOptionsImpl;
import io.github.nist4j.enums.CharsetEnum;
import java.nio.charset.Charset;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NistOptionsBuilderImpl implements NistOptionsBuilder {
  private boolean isCalculateLENOnBuild = true;
  private boolean isCalculateCNTOnBuild = true;
  private boolean isDCSfieldUsedToDetectCharset = true;
  private Charset charset = CharsetEnum.getDefault().getCharset();

  public static NistOptionsBuilderImpl newBuilder() {
    return new NistOptionsBuilderImpl();
  }

  public static NistOptionsBuilder newBuilder(NistOptions nistOptions) {
    return new NistOptionsBuilderImpl().from(nistOptions);
  }

  @Override
  public NistOptions build() {
    return new NistOptionsImpl(this);
  }

  @Override
  public NistOptionsBuilder from(NistOptions nistOptions) {
    return new NistOptionsBuilderImpl(
        nistOptions.isCalculateLENOnBuild(),
        nistOptions.isCalculateCNTOnBuild(),
        nistOptions.isDCSfieldUsedToDetectCharset(),
        nistOptions.getCharset());
  }

  @Override
  public NistOptionsBuilder isCalculateLENOnBuild(boolean value) {
    this.isCalculateLENOnBuild = value;
    return this;
  }

  @Override
  public NistOptionsBuilder isCalculateCNTOnBuild(boolean value) {
    this.isCalculateCNTOnBuild = value;
    return this;
  }

  @Override
  public NistOptionsBuilder isDCSfieldUsedToDetectCharset(boolean value) {
    this.isDCSfieldUsedToDetectCharset = value;
    return this;
  }

  @Override
  public NistOptionsBuilder charset(Charset value) {
    this.charset = value;
    return this;
  }

  @Getter
  @AllArgsConstructor
  public enum DefaultOpts {
    // More understanding expression
    DONT_MODIFY_RT_BUT_RESPECT_ENCODING(false, false, true, CharsetEnum.getDefault().getCharset()),
    MODIFY_RT_AND_RESPECT_ENCODING(true, true, true, CharsetEnum.getDefault().getCharset()),
    DONT_MODIFY_RT_FORCE_ASCII(false, false, false, CharsetEnum.ASCII.getCharset()),
    DONT_MODIFY_RT_FORCE_UTF8(false, false, false, CharsetEnum.UTF_8.getCharset()),
    DONT_MODIFY_RT_FORCE_UTF16(false, false, false, CharsetEnum.UTF_16.getCharset()),
    DONT_MODIFY_RT_FORCE_UTF32(false, false, false, CharsetEnum.UTF_32.getCharset()),
    // For reading don't change values of LEN, CNT but use DCS field to detect the charset encoding
    TO_READ(DONT_MODIFY_RT_BUT_RESPECT_ENCODING),
    TO_VALIDATE(DONT_MODIFY_RT_BUT_RESPECT_ENCODING),
    // For create calculate the value of LEN, CNT but DCS has no effect
    TO_CREATE(MODIFY_RT_AND_RESPECT_ENCODING),
    // For writing use DCS to detect the charset but calculate the value of LEN, CNT has no effect
    TO_WRITE(MODIFY_RT_AND_RESPECT_ENCODING);

    private final NistOptions options;

    DefaultOpts(
        boolean isCalculateLENOnBuild,
        boolean isCalculateCNTOnBuild,
        boolean isDCSfieldUsedToDetectCharset,
        Charset charset) {
      this(
          NistOptionsBuilderImpl.newBuilder()
              .isCalculateLENOnBuild(isCalculateLENOnBuild)
              .isCalculateCNTOnBuild(isCalculateCNTOnBuild)
              .isDCSfieldUsedToDetectCharset(isDCSfieldUsedToDetectCharset)
              .charset(charset)
              .build());
    }

    DefaultOpts(DefaultOpts defaultOpts) {
      this(
          NistOptionsBuilderImpl.newBuilder()
              .isCalculateLENOnBuild(defaultOpts.options.isCalculateLENOnBuild())
              .isCalculateCNTOnBuild(defaultOpts.options.isCalculateCNTOnBuild())
              .isDCSfieldUsedToDetectCharset(defaultOpts.options.isDCSfieldUsedToDetectCharset())
              .charset(defaultOpts.options.getCharset())
              .build());
    }
  }
}
