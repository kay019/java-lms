package nextstep.courses.domain.session;

import static java.util.Collections.emptySet;

import java.util.Set;

public enum ImageFormat {
    JPEG(Set.of("jpg", "jpeg")),
    PNG(Set.of("png")),
    GIF(Set.of("gif")),
    SVG(Set.of("svg")),
    NOT_SUPPORTED(emptySet());

    private final Set<String> allFormat;

    ImageFormat(Set<String> allFormat) {
        this.allFormat = allFormat;
    }

    public static ImageFormat findFormat(String format) {
        for (ImageFormat imageFormat : values()) {
            if (imageFormat.allFormat.contains(format.toLowerCase())) {
                return imageFormat;
            }
        }
        return NOT_SUPPORTED;
    }

}
