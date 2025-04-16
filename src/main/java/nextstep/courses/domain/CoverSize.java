package nextstep.courses.domain;

public class CoverSize {
    public static final int HEIGHT_RATIO = 2;
    public static final int WIDTH_RATIO = 3;
    private int width;
    private int height;

    public CoverSize(int width, int height) {
        validate(width, height);
        this.width = width;
        this.height = height;
    }

    private void validate(int width, int height) {
        if (width < 300 || height < 200) {
            throw new IllegalArgumentException("커버 사이즈는 최소 300x200 입니다.");
        }
        if (!isValidRatio(width, height)) {
            throw new IllegalArgumentException("커버 사이즈는 3:2 비율이어야 합니다.");
        }
    }

    private boolean isValidRatio(int width, int height) {
        return width * HEIGHT_RATIO == height * WIDTH_RATIO;
    }
}
