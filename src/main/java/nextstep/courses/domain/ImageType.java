package nextstep.courses.domain;

public enum ImageType {
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    SVG("svg"),
    GIF("gif");

    private final String type;

    ImageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static void validate(String imageName) {
        for (ImageType imageType : values()) {
            if (imageName.toLowerCase().endsWith(imageType.getType())) {
                return;
            }
        }
        throw new IllegalArgumentException("이미지 형식이 유효하지 않습니다.");
    }
}
