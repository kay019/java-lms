package nextstep.courses.domain.session.inner;

public class ImageSize {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final double RATIO = 1.5;
    private static final long MAX_SIZE = 1 * 1024 * 1024; // 1MB

    private int width;
    private int height;
    private long size;

    public ImageSize(int width, int height, long size) {
        validSize(width, height, size);
        this.width = width;
        this.height = height;
        this.size = size;
    }

    private void validSize(int width, int height, long size) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("이미지의 크기가 너무 작습니다.");
        }

        if ((double) width / height != RATIO) {
            throw new IllegalArgumentException("이미지의 비율이 맞지 않습니다.");
        }

        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("이미지의 용량이 너무 큽니다.");
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getSize() {
        return size;
    }
}
