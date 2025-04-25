package nextstep.session.image.domain;

import java.util.Objects;

import nextstep.session.image.exception.ImageFileSizeIllegalArgumentException;

public class ImageFileSize {
    private static final int MAX_SIZE_1MB = 1024 * 1024;

    private final int size;

    public ImageFileSize(int size) {
        validate(size);
        this.size = size;
    }

    private void validate(int size) {
        if (size < 0 || size > MAX_SIZE_1MB) {
            throw new ImageFileSizeIllegalArgumentException();
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageFileSize that = (ImageFileSize) o;
        return size == that.size;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(size);
    }
}
