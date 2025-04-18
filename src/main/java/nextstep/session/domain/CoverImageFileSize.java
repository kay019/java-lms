package nextstep.session.domain;

public class CoverImageFileSize {
    private static final long MAX_SIZE = 1024 * 1024; // 1MB
    private final long size;

    public CoverImageFileSize(long size) {
        validate(size);
        this.size = size;
    }

    private static void validate(long size) {
        if (size <= 0 || size > MAX_SIZE) {
            throw new IllegalArgumentException("이미지 크기는 0보다 크고 1MB 이하여야 합니다.");
        }
    }

    public long getSize() {
        return size;
    }
}
