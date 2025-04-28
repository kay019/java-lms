package nextstep.session.domain.cover;

public enum ImgType {
    GIF("image/gif"),
    JPG("image/jpg"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    SVG("image/svg+xml");

    private final String contentType;

    ImgType(String contentType) {
        this.contentType = contentType;
    }

    public static ImgType from(String type) {
        checkNull(type);

        try {
            return ImgType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("지원하지 않는 이미지 타입입니다: " + type);
        }
    }

    private static void checkNull(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("이미지 타입이 비어있습니다");
        }
    }

    public String getContentType() {
        return contentType;
    }

}
