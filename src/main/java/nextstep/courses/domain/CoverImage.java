package nextstep.courses.domain;

import static nextstep.courses.domain.ImageType.SESSION_ALLOWED_IMAGE_TYPES;

public class CoverImage {
    private static final int SIZE_LIMIT = 1024 * 1024;
    private static final int MIN_WIDTH = 300;
    private static final int MIN_HEIGHT = 200;
    private static final int WIDTH_RATIO = 3;
    private static final int HEIGHT_RATIO = 2;

    private int size;
    private String type;
    private int width;
    private int height;

    public CoverImage(int size, String type, int width, int height) {
        validateInput(size, type, width, height);
        this.size = size;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    private void validateInput(int size, String type, int width, int height) {
        validateSize(size);
        validateCoverImageType(type);
        validateResolution(width, height);
    }

    private void validateSize(int size) {
        if (size > SIZE_LIMIT) {
            throw new IllegalArgumentException("이미지의 용량은 1MB를 넘을 수 없습니다.");
        }
    }

    private void validateCoverImageType(String type) {
        boolean isSupportedType = SESSION_ALLOWED_IMAGE_TYPES.stream()
                .anyMatch(t -> t.name().equalsIgnoreCase(type));
        if (!isSupportedType) {
            throw new IllegalArgumentException("지원하지 않는 이미지 타입입니다.");
        }
    }

    private void validateResolution(int width, int height) {
        if (width < MIN_WIDTH || height < MIN_HEIGHT) {
            throw new IllegalArgumentException("이미지 크기가 적절하지 않습니다.");
        }
        if (width * HEIGHT_RATIO != height * WIDTH_RATIO) {
            throw new IllegalArgumentException("이미지 크기가 적절하지 않습니다.");
        }
    }
}
