package nextstep.courses.domain;

import java.util.List;

public class Image {
    private static final int MAX_SIZE = 1024 * 1024;
    private static final List<String> SUPPORTED_FORMATS = List.of("gif", "jpg", "png", "svg");
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final double RATIO = 3.0 / 2.0;
    private static final double RATIO_TOLERANCE = 0.01;

    private final Long id;

    private final String url;
    private final int width;
    private final int height;
    private final String format;
    private final int size;

    public Image(Long id, String url, int width, int height, String format, int size) {
        validate(url, width, height, format, size);

        this.id = id;
        this.url = url;
        this.width = width;
        this.height = height;
        this.format = format;
        this.size = size;
    }

    public Image(String url, int width, int height, String format, int size) {
        this(null, url, width, height, format, size);
    }

    private void validate(String url, int width, int height, String format, int size) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL은 필수 값입니다.");
        }
        
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("너비는 최소 " + MIN_WIDTH + "픽셀, 높이는 최소 " + MIN_HEIGHT + "픽셀 이상이어야 합니다.");
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

        double actualRatio = (double) width / height;
        if (Math.abs(actualRatio - RATIO) > RATIO_TOLERANCE) {
            throw new IllegalArgumentException(
                String.format("이미지 비율은 3:2(너비:높이)여야 합니다. 현재 비율: %.2f:1", actualRatio)
            );
        }
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFormat() {
        return format;
    }

    public int getSize() {
        return size;
    }
}
