package nextstep.courses.domain.session.image;

import java.util.stream.Stream;

public enum SessionImageType {
    GIF, JPG, JPEG, PNG, SVG;

    public static SessionImageType fromString(String imageType) {
        return Stream.of(SessionImageType.values())
            .filter(type -> type.name().equalsIgnoreCase(imageType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown image type: " + imageType));
    }
}
