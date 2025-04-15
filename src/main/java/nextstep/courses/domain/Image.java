package nextstep.courses.domain;

import java.util.List;

public class Image {
    private static final int MAX_SIZE = 1024 * 1024;
    private static final List<String> SUPPORTED_FORMATS = List.of("gif", "jpg", "png", "svg");
    private static final int MIN_RESOLUTION = 300;

    private final String url;
    private final int width;
    private final int height;
    private final String format;
    private final int size;

    public Image(String url, int width, int height, String format, int size) {
        validate(url, width, height, format, size);

        this.url = url;
        this.width = width;
        this.height = height;
        this.format = format;
        this.size = size;
    }

    private void validate(String url, int width, int height, String format, int size) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL은 필수 값입니다.");
        }
        
        if (width < MIN_RESOLUTION || height < MIN_RESOLUTION) {
            throw new IllegalArgumentException("너비와 높이는 최소 300픽셀, 200픽셀 이상이어야 합니다.");
        }
        
        if (format == null || format.isEmpty()) {
            throw new IllegalArgumentException("형식은 필수 값입니다.");
        }
        
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("크기는 1MB 이하여야 합니다.");
        }
        
        if (!SUPPORTED_FORMATS.contains(format)) {
            throw new IllegalArgumentException("지원 형식은 gif, jpg, png, svg 중 하나여야 합니다.");
        }
    }
}
