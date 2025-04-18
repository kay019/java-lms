package nextstep.sessions.domain.cover;

import java.util.Objects;

public class ImgWidth {
    private static final int MIN_WIDTH = 300;

    private final int value;

    public ImgWidth(int value) {
        if (value < MIN_WIDTH) {
            throw new IllegalArgumentException("img width must be greater than 300px");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImgWidth imgWidth = (ImgWidth) o;
        return value == imgWidth.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
