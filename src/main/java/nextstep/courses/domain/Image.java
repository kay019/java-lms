package nextstep.courses.domain;

import nextstep.courses.entity.ImageEntity;

public class Image {
  private static final long MAX_SIZE_IN_BYTES = 1_000_000L;
  private static final int MIN_WIDTH_IN_PIXEL = 300;
  private static final int MIN_HEIGHT_IN_PIXEL = 200;

  private final Long id;
  private final String filename;
  private final long sizeInBytes;
  private final int widthInPixel;
  private final int heightInPixel;

  public Image(String filename) {
    this(filename, 1000, 300, 200);
  }

  public Image(Long id, String filename) {
    this(id, filename, 1000, 300, 200);
  }

  public Image (String filename, long sizeInBytes, int widthInPixel, int heightInPixel) {
    this(0L, filename, sizeInBytes, widthInPixel, heightInPixel);
  }

  public Image (Long id, String filename, long sizeInBytes, int widthInPixel, int heightInPixel) {
    validate(filename, sizeInBytes, widthInPixel, heightInPixel);

    this.id = id;
    this.filename = filename;
    this.sizeInBytes = sizeInBytes;
    this.widthInPixel = widthInPixel;
    this.heightInPixel = heightInPixel;
  }

  private void validate(String filename, long sizeInBytes, int widthInPixel, int heightInPixel) {
    if (filename == null || filename.isEmpty()) {
      throw new IllegalArgumentException("파일이름이 null이거나 비어있습니다.");
    }
    ImageType.validate(filename);
    if (sizeInBytes <= 0) {
      throw new IllegalArgumentException("파일 크기가 유효하지 않습니다.");
    }
    if (sizeInBytes > MAX_SIZE_IN_BYTES) {
      throw new IllegalArgumentException("파일 크기가 너무 큽니다.");
    }
    if (widthInPixel <= 0 || heightInPixel <= 0) {
      throw new IllegalArgumentException("가로, 세로 크기가 유효하지 않습니다.");
    }
    if (widthInPixel < MIN_WIDTH_IN_PIXEL || heightInPixel < MIN_HEIGHT_IN_PIXEL) {
      throw new IllegalArgumentException("가로, 세로 크기가 너무 작습니다.");
    }
    if (widthInPixel * 2 != heightInPixel * 3) {
      throw new IllegalArgumentException("가로 세로 비율이 유효하지 않습니다.");
    }
  }

  public long id() {
    return id;
  }

  public ImageEntity toImageEntity() {
    return new ImageEntity(
            this.id,
            this.filename,
            this.sizeInBytes,
            this.widthInPixel,
            this.heightInPixel
    );
  }
}
