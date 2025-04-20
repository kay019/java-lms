package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    GIF("gif"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg");

    private final String value;

    ImageType(String value) {
        this.value = value;
    }

    public static boolean isApplyType(String value) {
        ImageType[] imageTypes = values();
        return Arrays.stream(values())
                .anyMatch(type -> type.value.equals(value));
    }

}
