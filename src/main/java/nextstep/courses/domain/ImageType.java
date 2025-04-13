package nextstep.courses.domain;

import java.util.List;

public enum ImageType {
    GIF, JPG, JPEG, PNG, SVG;

    public static final List<ImageType> SESSION_ALLOWED_IMAGE_TYPES = List.of(GIF, JPG, JPEG, PNG, SVG);
}
