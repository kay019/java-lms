package nextstep.courses.domain;

public class CoverImage {
    private static final int MB = 1024 * 1024;

    private long sizeInBytes;
    private ImageFormat imageFormat;
    private CoverImageSize coverImageSize;

    public CoverImage(long sizeInBytes, ImageFormat imageFormat, int width, int height) {
        if (sizeInBytes > MB) {
            throw new IllegalArgumentException("size는 1MB 이하여야합니다.");
        }

        if (imageFormat == null) {
            throw new IllegalArgumentException("imageType is null.");
        }

        this.sizeInBytes = sizeInBytes;
        this.imageFormat = imageFormat;
        this.coverImageSize = new CoverImageSize(width, height);
    }
}
