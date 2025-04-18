package nextstep.courses.domain;

import java.util.Arrays;

public enum ImageType {
    gif, jpg, jpeg, png, svg;

    public static boolean contains(String value) {
        return Arrays.stream(values())
                .anyMatch(type -> type.name().equals(value));
    }
}
