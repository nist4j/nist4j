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
package io.github.nist4j.enums;

import static java.util.Collections.unmodifiableSet;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("unused")
@Getter
@AllArgsConstructor
public enum CharacterTypeEnum {
  A("Alphabetic", null, AllowedCharacterTypes.ALLOWED_A),
  AN("Alphanumeric and numeric", null, AllowedCharacterTypes.ALLOWED_AN),
  ANS("Alphanumeric and special characters", null, AllowedCharacterTypes.ALLOWED_ANS),
  AS("Alphabetic and special characters", null, AllowedCharacterTypes.ALLOWED_AS),
  B("Binary", null, null),
  BASE64("base64", "^[A-Za-z]+$", null),
  H("Hexadecimal", null, AllowedCharacterTypes.ALLOWED_H),
  N("Numeric", null, AllowedCharacterTypes.ALLOWED_N),
  S("Numeric and special characters", null, AllowedCharacterTypes.ALLOWED_S),
  NS("Numeric and special characters", null, AllowedCharacterTypes.ALLOWED_NS),
  U("Unicode", "^[\\s\\S&&[^\u001E\u001F\u001D\u001C\u0002\u0003]]+$", null);

  private final String description;
  private final String regexpValidation;
  private final Set<Character> allowedCharacters;

  protected static class AllowedCharacterTypes {
    protected static final Set<Character> ALLOWED_N;
    protected static final Set<Character> ALLOWED_A;
    protected static final Set<Character> ALLOWED_S;
    protected static final Set<Character> ALLOWED_H;
    protected static final Set<Character> ALLOWED_AN;
    protected static final Set<Character> ALLOWED_AS;
    protected static final Set<Character> ALLOWED_NS;
    protected static final Set<Character> ALLOWED_ANS;

    static {
      Set<Character> allowedN = new HashSet<>();
      Set<Character> allowedA = new HashSet<>();
      Set<Character> allowedS = new HashSet<>();
      Set<Character> allowedH = new HashSet<>();
      Set<Character> allowedAN = new HashSet<>();
      Set<Character> allowedAS = new HashSet<>();
      Set<Character> allowedNS = new HashSet<>();
      Set<Character> allowedANS = new HashSet<>();
      for (char c = 'a'; c <= 'z'; c++) {
        allowedA.add(c);
      }
      for (char c = 'A'; c <= 'Z'; c++) {
        allowedA.add(c);
      }
      for (char c = '0'; c <= '9'; c++) {
        allowedN.add(c);
      }
      /* note that :space: is an A for std2011 but an S for other*/
      String specials = " !\"#$%&'()*+,-./:;<=>?@[\\^_`{|}~";
      for (char c : specials.toCharArray()) {
        allowedS.add(c);
      }
      for (char c = 'A'; c <= 'F'; c++) {
        allowedH.add(c);
      }
      allowedH.addAll(allowedN);

      allowedAN.addAll(allowedA);
      allowedAN.addAll(allowedN);

      allowedAS.addAll(allowedA);
      allowedAS.addAll(allowedS);

      allowedNS.addAll(allowedN);
      allowedNS.addAll(allowedS);

      allowedANS.addAll(allowedA);
      allowedANS.addAll(allowedN);
      allowedANS.addAll(allowedS);

      ALLOWED_N = unmodifiableSet(allowedN);
      ALLOWED_A = unmodifiableSet(allowedA);
      ALLOWED_S = unmodifiableSet(allowedS);
      ALLOWED_H = unmodifiableSet(allowedH);
      ALLOWED_AN = unmodifiableSet(allowedAN);
      ALLOWED_AS = unmodifiableSet(allowedAS);
      ALLOWED_NS = unmodifiableSet(allowedNS);
      ALLOWED_ANS = unmodifiableSet(allowedANS);
    }
  }
}
