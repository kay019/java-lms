package nextstep.courses.domain;

import java.util.EnumSet;
import java.util.Set;

public enum ImageType {
  GIF, JPG, JPEG, PNG, SVG;

  private static final Set<ImageType> SUPPORTED_TYPES = EnumSet.of(GIF, JPG, JPEG, PNG, SVG);

  public boolean isSupported() {
    return SUPPORTED_TYPES.contains(this);
  }
}
