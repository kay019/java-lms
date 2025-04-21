package nextstep.courses.domain.image;

public class CoverImagePixelSize {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int REQUIRED_RATIO_WIDTH = 3;
    private static final int REQUIRED_RATIO_HEIGHT = 2;

    private final int width;
    private final int height;

    public CoverImagePixelSize(int width, int height) {
        validate(width, height);
        this.width = width;
        this.height = height;
    }

    private void validate(int width, int height) {
        validatePixelSize(width, height);
        validateRatio(width, height);
    }

    private void validatePixelSize(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("이미지 최소 크기는 width는 300픽셀, height는 200픽셀 이상 입니다.");
        }
    }

    private void validateRatio(int width, int height) {
        if (width * REQUIRED_RATIO_HEIGHT != height * REQUIRED_RATIO_WIDTH) {
            throw new IllegalArgumentException("width와 height의 비율은 3:2여야 합니다.");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
