package nextstep.courses.domain;

import nextstep.courses.CannotUploadImageException;

import java.util.Arrays;

public enum ImageType {
    GIF,
    JPG,
    JPEG,
    PNG,
    SVG;

    public static ImageType of(String type) {
        return Arrays.stream(ImageType.values())
                .filter(imageType -> imageType.name().equals(type))
                .findFirst()
                .orElseThrow(() -> new CannotUploadImageException("이미지의 확장자는 gif, jpg, jpeg, png, svg만 허용합니다."));
    }
}
