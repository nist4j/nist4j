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
package io.github.nist4j.fixtures;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "SameReturnValue"})
public class CharacterFixtures {
  public static final byte[] japCharUTF8InBytes =
      new byte[] {(byte) 0xE7, (byte) 0x99, (byte) 0xBD};
  public static final byte[] japCharUTF16InBytes = new byte[] {(byte) 0x76, (byte) 0x7D};
  public static final byte[] japCharUTF32InBytes =
      new byte[] {(byte) 0x00, (byte) 0x00, (byte) 0x76, (byte) 0x7D};

  @SuppressWarnings("UnnecessaryUnicodeEscape")
  public static final String japCharUnicode = "\u767D";

  public static final char[] musicCharUnicodeInChars = Character.toChars(0x1D11E);
  public static final String musicCharUnicodeInString = new String(musicCharUnicodeInChars);
  public static final byte[] musicCharUnicodeInBytes =
      musicCharUnicodeInString.getBytes(StandardCharsets.UTF_8);
  public static final byte[] musicCharUTF8InBytes =
      new byte[] {(byte) 0xF0, (byte) 0x9D, (byte) 0x84, (byte) 0x9E};
  public static final byte[] musicCharUTF16InBytes =
      new byte[] {(byte) 0xD8, (byte) 0x34, (byte) 0xDD, (byte) 0x1E};
  public static final byte[] musicCharUTF32InBytes =
      new byte[] {(byte) 0x00, (byte) 0x01, (byte) 0xD1, (byte) 0x1E};

  public static final byte[] chines2CharUTF8InBytes =
      new byte[] {(byte) 0xe8, (byte) 0xa3, (byte) 0x94};
  public static final byte[] chinesCharUTF8InBytes =
      new byte[] {(byte) 0xe8, (byte) 0x8f, (byte) 0xaf};

  public static byte[] japaneseAndMusicUTF8InBytes() {
    return concatenateBytes(japCharUTF8InBytes, musicCharUTF8InBytes);
  }

  public static byte[] japaneseAndMusicUTF16InBytes() {
    return concatenateBytes(japCharUTF16InBytes, musicCharUTF16InBytes);
  }

  public static byte[] japaneseAndMusicUTF32InBytes() {
    return concatenateBytes(japCharUTF32InBytes, musicCharUTF32InBytes);
  }

  public static byte[] concatenateBytes(byte[]... inputs) {
    int totalLength = 0;
    for (byte[] input : inputs) {
      totalLength += input.length;
    }

    byte[] result = new byte[totalLength];

    int offset = 0;
    for (byte[] input : inputs) {
      System.arraycopy(input, 0, result, offset, input.length);
      offset += input.length;
    }

    return result;
  }

  public static String japaneseCharInUTF8() {
    return new String(japCharUTF8InBytes, StandardCharsets.UTF_8);
  }

  public static String japaneseCharInUTF16() {
    return new String(japCharUTF16InBytes, StandardCharsets.UTF_16);
  }

  public static String japaneseCharInUTF32() {
    return new String(japCharUTF32InBytes, Charset.forName("UTF-32BE"));
  }

  public static String japaneseCharInUnicode() {
    return japCharUnicode;
  }

  public static String musicCharInUTF8() {
    return new String(musicCharUTF8InBytes, StandardCharsets.UTF_8);
  }

  public static String musicCharInUTF16() {
    return new String(musicCharUTF16InBytes, StandardCharsets.UTF_16BE);
  }

  public static String musicCharInUTF32() {
    return new String(musicCharUTF32InBytes, Charset.forName("UTF-32BE"));
  }

  public static String musicCharInUnicode() {
    return musicCharUnicodeInString;
  }

  public static String repeat(String text, int nb) {
    StringBuilder builder = new StringBuilder(nb);
    for (int i = 0; i < nb; i++) {
      builder.append(text);
    }
    return builder.toString();
  }
}
