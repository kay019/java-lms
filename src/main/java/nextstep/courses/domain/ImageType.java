package nextstep.courses.domain;

public enum ImageType {
  GIF, JPG, JPEG, PNG, SVG;

  public boolean isSupported() {
    return this == GIF || this == JPG || this == JPEG || this == PNG || this == SVG;
  }
}
