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
package io.github.nist4j.use_cases.helpers;

import io.github.nist4j.enums.CharsetEnum;
import java.nio.charset.CharsetDecoder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NistDecoderHelper {

  public static final char SEP_US = 31;
  public static final char SEP_RS = 30;
  public static final char SEP_GS = 29;
  public static final char SEP_FS = 28;

  public static final int FIELD_MAX_LENGTH = 300000;

  public static final char[] TAG_SEP_DOT = {'.', '.'};
  public static final char[] TAG_SEP_COLN = {':', ':'};
  public static final char[] TAG_SEP_GSFS = {SEP_GS, SEP_FS};

  public static class Tag {
    public final int type;
    public final int field;

    public Tag(int type, int field) {
      this.type = type;
      this.field = field;
    }
  }

  public static class Token {

    public final byte[] buffer;
    public int pos;

    public String header;
    public int crt;

    public CharsetDecoder charsetDecoder;

    public Token(byte[] buffer) {
      this.buffer = buffer;
      this.charsetDecoder = CharsetEnum.CP1256.getCharset().newDecoder();
    }

    // directory of charset.
    // METHODE UTILISEE DANS LE CAS OU LE CHAMP 1.015 EST LU
    public void setCharSetDecoder(String dcs) {
      if (dcs != null) {
        if (dcs.startsWith("000")) {
          this.charsetDecoder = CharsetEnum.CP1256.getCharset().newDecoder();
        } else if (dcs.startsWith("002")) {
          this.charsetDecoder = CharsetEnum.UTF_16.getCharset().newDecoder();
        } else if (dcs.startsWith("003")) {
          this.charsetDecoder = CharsetEnum.UTF_8.getCharset().newDecoder();
        }
      }
    }
  }
}
