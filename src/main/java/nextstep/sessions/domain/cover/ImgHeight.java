package nextstep.sessions.domain.cover;

import java.util.Objects;

public class ImgHeight {
    private static final int MIN_HEIGHT = 200;

    private final int value;

    public ImgHeight(int value) {
        if (value < MIN_HEIGHT) {
            throw new IllegalArgumentException("img height must be greater than 200px");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImgHeight imgHeight = (ImgHeight) o;
        return value == imgHeight.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
