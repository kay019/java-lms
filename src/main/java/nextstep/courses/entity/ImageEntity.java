package nextstep.courses.entity;

import nextstep.courses.domain.Image;

public class ImageEntity {

  private final Long id;
  private final String filename;
  private final long sizeInBytes;
  private final int widthInPixel;
  private final int heightInPixel;

  public ImageEntity(Long id, String filename, long sizeInBytes, int widthInPixel, int heightInPixel) {
    this.id = id;
    this.filename = filename;
    this.sizeInBytes = sizeInBytes;
    this.widthInPixel = widthInPixel;
    this.heightInPixel = heightInPixel;
  }

  public Long id() {
    return id;
  }
  public String filename() {
    return filename;
  }
  public long sizeInBytes() {
    return sizeInBytes;
  }
  public int widthInPixel() {
    return widthInPixel;
  }
  public int heightInPixel() {
    return heightInPixel;
  }

  public Image toImage() {
    return new Image(id, filename, sizeInBytes, widthInPixel, heightInPixel);
  }
}
