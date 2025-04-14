package nextstep.sessions.domain;

import java.util.Objects;
import java.util.Set;

public class ImageInfo {
    private static final Set<String> ACCEPTABLE_TYPES = Set.of("gif", "jpg", "jpeg", "png", "svg");
    private static final int MAX_BYTES = 10^6;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final float STANDARD_RATIO = 1.5f;

    private final int bytes;
    private final String type;
    private final int width;
    private final int height;

    public ImageInfo(int bytes, String type, int width, int height) {
        if (!validate(bytes, type, width, height)) {
            throw new IllegalArgumentException("Invalid image");
        }
        this.bytes = bytes;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    private boolean validate(int bytes, String type, int width, int height) {
        return ACCEPTABLE_TYPES.contains(type)
                && smallerOrEqual(bytes)
                && widerOrEqual(width)
                && longerOrEqual(height)
                && validRatio(width, height);
    }

    private boolean smallerOrEqual(int bytes) {
        return bytes <= MAX_BYTES;
    }

    private boolean widerOrEqual(int width) {
        return width >= MIN_WIDTH;
    }

    private boolean longerOrEqual(int height) {
        return height >= MIN_HEIGHT;
    }

    private boolean validRatio(int width, int height) {
        return (float) width / height == STANDARD_RATIO;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ImageInfo imageInfo = (ImageInfo) o;
        return bytes == imageInfo.bytes && width == imageInfo.width && height == imageInfo.height && Objects.equals(type, imageInfo.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bytes, type, width, height);
    }
}
