package nextstep.courses.domain;

public class CoverImage {

    private final long size;
    private final ImageType imageType;
    private final long width;
    private final long height;

    public CoverImage(long size, ImageType imageType, long width, long height) {
        validateSize(size);

        if (imageType == ImageType.UNSUPPORTED) {
            throw new IllegalArgumentException("지원하지 않는 이미지 타입입니다.");
        }

        if (width < 300) {
            throw new IllegalArgumentException("이미지 너비는 300픽셀 이상이어야 합니다: " + width);
        }

        if (height < 200) {
            throw new IllegalArgumentException("이미지 높이는 200픽셀 이상이어야 합니다: " + height);
        }

        if (isInValidRatio(width, height)) {
            throw new IllegalArgumentException("이미지 비율은 3:2이어야 합니다.");
        }

        this.size = size;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    private static void validateSize(long imageSize) {
        if (imageSize > 1_048_576) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다: " + imageSize);
        }
    }

    private boolean isInValidRatio(long width, long height) {
        double ratio = (double) width / height;
        return Math.abs(ratio - 1.5) > 0.01;
    }
}
