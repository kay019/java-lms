package nextstep.session.domain.cover;

import java.util.Objects;

public class ImgSize {
    private static final int MAX_SIZE_BYTES = 1_048_576;

    private final int value;

    public ImgSize(int value) {
        if (value <= 0 || value > MAX_SIZE_BYTES) {
            throw new IllegalArgumentException("img size must be less than 1MB");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImgSize imgSize = (ImgSize) o;
        return value == imgSize.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
