package nextstep.courses.domain;

public class CoverImage {

    private static final String ERROR_INVALID_IMAGE_WIDTH = "이미지 너비는 300픽셀 이상이어야 합니다: ";
    private static final String ERROR_INVALID_IMAGE_HEIGHT = "이미지 높이는 200픽셀 이상이어야 합니다:";
    private static final String ERROR_INVALID_RATIO = "이미지 비율은 3:2이어야 합니다.";
    private static final String ERROR_NOT_SUPPORTED_TYPE = "지원하지 않는 이미지 타입입니다.";
    private static final String ERROR_INVALID_SIZE = "이미지 크기는 1MB 이하여야 합니다: ";
    private static final int IMAGE_SIZE_UPPER_BOUND = 1_048_576;
    private static final int IMAGE_WIDTH_LOWER_BOUND = 300;
    private static final int IMAGE_HEIGHT_LOWER_BOUND = 200;
    private static final double VALID_RATIO = 1.5;
    private static final double RATIO_TOLERANCE = 0.01;

    private final Integer size;
    private final ImageType imageType;
    private final Integer width;
    private final Integer height;

    public CoverImage(Integer size, ImageType imageType, Integer width, Integer height) {
        validateSize(size);
        validateImageType(imageType);
        validateImageResolution(width, height);

        this.size = size;
        this.imageType = imageType;
        this.width = width;
        this.height = height;
    }

    private void validateImageResolution(long width, long height) {
        if (width < IMAGE_WIDTH_LOWER_BOUND) {
            throw new IllegalArgumentException(ERROR_INVALID_IMAGE_WIDTH + width);
        }

        if (height < IMAGE_HEIGHT_LOWER_BOUND) {
            throw new IllegalArgumentException(ERROR_INVALID_IMAGE_HEIGHT + " " + height);
        }

        if (isInValidRatio(width, height)) {
            throw new IllegalArgumentException(ERROR_INVALID_RATIO);
        }
    }

    private static void validateImageType(ImageType imageType) {
        if (imageType == ImageType.UNSUPPORTED) {
            throw new IllegalArgumentException(ERROR_NOT_SUPPORTED_TYPE);
        }
    }

    private static void validateSize(long imageSize) {
        if (imageSize > IMAGE_SIZE_UPPER_BOUND) {
            throw new IllegalArgumentException(ERROR_INVALID_SIZE + imageSize);
        }
    }

    private boolean isInValidRatio(long width, long height) {
        double ratio = (double) width / height;
        return Math.abs(ratio - VALID_RATIO) > RATIO_TOLERANCE;
    }
}
