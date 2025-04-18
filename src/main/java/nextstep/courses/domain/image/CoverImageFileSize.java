package nextstep.courses.domain.image;

public class CoverImageFileSize {
    private static final long MAX_SIZE_BYTES = 1024 * 1024; // 1MB

    private final long size;

    public CoverImageFileSize(long size) {
        validate(size);
        this.size = size;
    }

    private void validate(long size) {
        if (size > MAX_SIZE_BYTES) {
            throw new IllegalArgumentException("이미지 크기는 1MB 이하여야 합니다.");
        }
    }
}
