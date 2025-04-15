package nextstep.courses.domain;

import java.util.Arrays;
import java.util.Set;

public enum ImageFormat {
    GIF(Set.of("gif")),
    JPG(Set.of("jpg", "jpeg")),
    PNG(Set.of("png")),
    SVG(Set.of("svg"));

    private final Set<String> enabledFormat;

    ImageFormat(Set<String> enabledFormat) {
        this.enabledFormat = enabledFormat;
    }

    public static ImageFormat findImageFormat(String input) {
        return Arrays.stream(ImageFormat.values())
            .filter(imageFormat ->
                imageFormat.enabledFormat.contains(input.toLowerCase()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid image format: " + input));
    }
}
