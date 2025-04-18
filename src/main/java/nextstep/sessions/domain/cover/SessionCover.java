package nextstep.sessions.domain.cover;

import java.util.Objects;

public class SessionCover {
    private static final double RATIO = 1.5;

    private final ImgSize size;

    private final ImgType imgType;

    private final ImgWidth width;

    private final ImgHeight height;

    public SessionCover(int size, String imgType, int width, int height) {
        checkRatio(width, height);
        this.size = new ImgSize(size);
        this.imgType = ImgType.from(imgType);
        this.width = new ImgWidth(width);
        this.height = new ImgHeight(height);
    }

    private void checkRatio(double width, int height) {
        double ratio = width / height;
        if (Math.abs(ratio - RATIO) > 0.01) {
            throw new IllegalArgumentException("session cover ratio must be 3:2");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SessionCover that = (SessionCover) o;
        return Objects.equals(size, that.size) && imgType == that.imgType && Objects.equals(width,
                that.width) && Objects.equals(height, that.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, imgType, width, height);
    }
}
