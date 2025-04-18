package nextstep.courses.domain;

public class SessionImageInfo {

  private ImageType imageType;
  private int imageSize;
  private int width;
  private int height;

  public SessionImageInfo(ImageType imageType, int imageSize, int width, int height) {
    validate(imageType, imageSize, width, height);
    this.imageType = imageType;
    this.imageSize = imageSize;
    this.width = width;
    this.height = height;
  }

  private void validate(ImageType type, int size, int width, int height) {
    if (size > 1_000_000) {
      throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
    }
    if (!type.isSupported()) {
      throw new IllegalArgumentException("지원하지 않는 이미지 타입입니다.");
    }
    if (width < 300 || height < 200) {
      throw new IllegalArgumentException("이미지 최소 크기는 300x200 이상이어야 합니다.");
    }
    if (width * 2 != height * 3) {
      throw new IllegalArgumentException("이미지 비율은 3:2여야 합니다.");
    }
  }

}
