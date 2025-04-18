package nextstep.courses.domain;

import java.util.Objects;

import nextstep.exception.ImageSizeIllegalArgumentException;

public class ImageSize {
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;

    private final int width;
    private final int height;

    public ImageSize(int width, int height) {
        validate(width, height);

        this.width = width;
        this.height = height;
    }

    private void validate(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new ImageSizeIllegalArgumentException();
        }

        if (!isValidAspectRatio(width, height)) {
            throw new ImageSizeIllegalArgumentException();
        }
    }

    private boolean isValidAspectRatio(int width, int height) {
        return width * 2 == height * 3;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageSize imageSize = (ImageSize) o;
        return width == imageSize.width && height == imageSize.height;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }
}
