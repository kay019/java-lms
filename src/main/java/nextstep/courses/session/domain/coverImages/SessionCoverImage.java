package nextstep.courses.session.domain.coverImages;

public class SessionCoverImage {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int MAX_SIZE = 1;
    private static final int WIDTH_RATE = 2;
    private static final int HEIGHT_RATE = 3;

    private final double size;
    private final SessionImageType type;
    private final int width;
    private final int height;

    public SessionCoverImage(double size, SessionImageType type, int width, int height) {
        validate(size, width, height);
        this.size = size;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    private void validate(double size, double width, double height) {
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("이미지 사이즈는" + MAX_SIZE + "를 초과할 수 없습니다.");
        }

        if (width < MIN_WIDTH && height < MIN_HEIGHT) {
            throw new IllegalArgumentException(String.format("이미지의 폭은 %d 보다 커야하며 높이는 %d 보다 커야합니다.", MIN_WIDTH, MIN_HEIGHT));
        }

        if (width * WIDTH_RATE != height * HEIGHT_RATE) {
            throw new IllegalArgumentException(String.format("이미지의 폭과 높이는 %d:%d 비율이어야 합니다.", HEIGHT_RATE, WIDTH_RATE));
        }
    }

    public double getSize() {
        return size;
    }

    public SessionImageType getType() {
        return type;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
