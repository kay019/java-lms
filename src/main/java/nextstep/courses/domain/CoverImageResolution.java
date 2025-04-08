package nextstep.courses.domain;

public class CoverImageResolution {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int ASPECT_RATIO_WIDTH = 3;
    private static final int ASPECT_RATIO_HEIGHT = 2;

    private final int width;
    private final int height;

    public CoverImageResolution(int width, int height) {
        validate(width, height);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private static void validate(int width, int height) {
        validateWidthAndHeight(width, height);
        validateAspectRatioValid(width, height);
    }

    private static void validateWidthAndHeight(int width, int height) {
        boolean isValid = width >= MIN_WIDTH && height >= MIN_HEIGHT;

        if (!isValid) {
            throw new IllegalArgumentException("이미지의 width는 300픽셀, height는 200픽셀 이상이어야 합니다.");
        }
    }

    private static void validateAspectRatioValid(int width, int height) {
        boolean isValid = width * ASPECT_RATIO_HEIGHT == height * ASPECT_RATIO_WIDTH;

        if (!isValid) {
            throw new IllegalArgumentException("width와 height의 비율은 3:2여야 합니다.");
        }
    }
}
