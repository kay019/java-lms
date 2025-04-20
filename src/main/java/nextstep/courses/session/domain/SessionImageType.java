package nextstep.courses.session.domain;

import java.util.Arrays;

public enum SessionImageType {
    GIF, JPG, JPEG,PNG, SVG;

    public static SessionImageType from(String imageType) {
        return Arrays.stream(values())
                .filter(type -> type.name().equalsIgnoreCase(imageType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않은 이미지 타입입니다. : " + imageType));
    }
}
