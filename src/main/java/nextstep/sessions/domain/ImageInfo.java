package nextstep.sessions.domain;

import java.util.Objects;
import java.util.Set;

public class ImageInfo {
    private final Set<String> ACCEPTABLE_TYPES = Set.of("gif", "jpg", "jpeg", "png", "svg");

    private final int bytes;
    private final String type;
    private final int width;
    private final int height;

    public ImageInfo(int bytes, String type, int width, int height) {
        if (!ACCEPTABLE_TYPES.contains(type)) {
            throw new IllegalArgumentException();
        }

        this.bytes = bytes;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public boolean smallerOrEqual(ImageInfo imageInfo) {
        return this.bytes <= imageInfo.bytes;
    }

    public boolean widerOrEqual(ImageInfo imageInfo) {
        return this.width >= imageInfo.width;
    }

    public boolean LongerOrEqual(ImageInfo imageInfo) {
        return this.height >= imageInfo.height;
    }

    public boolean hasRatio(float ratio) {
        return (float) this.width / this.height == ratio;
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
