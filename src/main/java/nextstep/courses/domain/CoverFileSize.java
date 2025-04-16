package nextstep.courses.domain;

public class CoverFileSize {
    private static final long MAX_SIZE = 1024 * 1024; // 1MB
    private long size;

    public CoverFileSize(long size) {
        validate(size);
        this.size = size;
    }

    private void validate(long size) {
        if (size > MAX_SIZE) {
            throw new IllegalArgumentException("커버 파일 사이즈는 최대 1MB 입니다.");
        }
    }
}
