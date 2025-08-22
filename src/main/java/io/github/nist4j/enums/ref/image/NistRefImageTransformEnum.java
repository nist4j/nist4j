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

import static io.github.nist4j.enums.NistStandardEnum.ANSI_NIST_ITL_2011;

import io.github.nist4j.enums.NistStandardEnum;
import io.github.nist4j.enums.ref.INistReferentielEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum NistRefImageTransformEnum implements INistReferentielEnum {
  AGE("AGE", "Age progressed", ANSI_NIST_ITL_2011, null),
  AXIS("AXIS", "Off-axis image rectification / Angle correction", ANSI_NIST_ITL_2011, null),
  COLORSHIFT("COLORSHIFT", "Color shifted", ANSI_NIST_ITL_2011, null),
  CONTRAST("CONTRAST", "Contrast stretched", ANSI_NIST_ITL_2011, null),
  CROP("CROP", "Cropped", ANSI_NIST_ITL_2011, null),
  DIST("DIST", "Distortion corrected (e.g. fisheye correction)", ANSI_NIST_ITL_2011, null),
  DOWNSAMPLE("DOWNSAMPLE", "Down-sampled", ANSI_NIST_ITL_2011, null),
  GRAY("GRAY", "Grayscale from color", ANSI_NIST_ITL_2011, null),
  ILLUM("ILLUM", "Illumination transform", ANSI_NIST_ITL_2011, null),
  IMGFUSE("IMGFUSE", "Image-level fusion of two or more images", ANSI_NIST_ITL_2011, null),
  INTERPOLATE("INTERPOLATE", "Up-sampled", ANSI_NIST_ITL_2011, null),
  MULTCOMP("MULTCOMP", "Multiply compressed", ANSI_NIST_ITL_2011, null),
  MULTIVIEW("MULTIVIEW", "Multi-view image", ANSI_NIST_ITL_2011, null),
  POSE("POSE", "Face-specific pose correction", ANSI_NIST_ITL_2011, null),
  ROTATE("ROTATE", "Rotated (in-plane)", ANSI_NIST_ITL_2011, null),
  SNIR("SNIR", "Simulated Near IR", ANSI_NIST_ITL_2011, null),
  SUPERRES(
      "SUPERRES",
      "Super-resolution image, derived from multiple lower resolution images",
      ANSI_NIST_ITL_2011,
      null),
  WHITE("WHITE", "White balance adjusted", ANSI_NIST_ITL_2011, null);

  private final String code;
  private final String description;
  private final NistStandardEnum createdFromStandard;
  private final NistStandardEnum deprecatedFromStandard;
}
