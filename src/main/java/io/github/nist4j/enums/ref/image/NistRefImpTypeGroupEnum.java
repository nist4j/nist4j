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
package io.github.nist4j.enums.ref.image;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Implementation of table view 'Table 7 Friction ridge impression types' <br>
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefImpTypeGroupEnum {
  FINGER,
  FINGER_PLAIN,
  FINGER_ROLLED,
  PALM,
  PLANTAR,
  UNKNOWN_FRICTION_RIDGE,
  LIVESCAN,
  NON_LIVESCAN,
  LATENT,
  NO_GROUP,
}
