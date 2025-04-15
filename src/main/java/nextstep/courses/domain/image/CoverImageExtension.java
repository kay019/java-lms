package nextstep.courses.domain.image;

import java.util.Arrays;

public enum CoverImageExtension {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String extension;

    CoverImageExtension(String extension) {
        this.extension = extension;
    }

    public static CoverImageExtension from(String extension) {
        return Arrays.stream(values())
                .filter(e -> e.extension.equalsIgnoreCase(extension))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 이미지 확장자 입니다."));
    }
}
