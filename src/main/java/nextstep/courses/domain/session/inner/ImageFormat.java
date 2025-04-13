package nextstep.courses.domain.session.inner;

import static java.util.Collections.emptySet;

import java.util.Arrays;
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
        return Arrays.stream(values())
                .filter(imageFormat -> imageFormat.allFormat.contains(format.toLowerCase()))
                .findFirst()
                .orElse(NOT_SUPPORTED);
    }

}
