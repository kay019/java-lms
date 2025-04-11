package nextstep.session.domain;

public class SessionImagePixel {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 255;

    private final int r;
    private final int g;
    private final int b;

    public SessionImagePixel(int r, int g, int b) {
        if (r > MAX_VALUE || g > MAX_VALUE || b > MAX_VALUE) {
            throw new IllegalArgumentException("색상 값은 255 초과인 값이 들어올 수 없습니다.");
        }

        if (r < MIN_VALUE || g < MIN_VALUE || b < MIN_VALUE) {
            throw new IllegalArgumentException("색상 값은 0 미만인 값이 들어올 수 없습니다.");
        }

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int byteSize() {
        return r * g * b;
    }
}
