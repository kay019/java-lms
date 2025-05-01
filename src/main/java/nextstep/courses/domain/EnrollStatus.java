package nextstep.courses.domain;

public enum EnrollStatus {
    NOT_RECRUITING("비모집중"),
    RECRUITING("모집중");

    private final String description;

    EnrollStatus(String description) {
        this.description = description;
    }

    public void validateEnroll() {
        if (this != RECRUITING) {
            throw new IllegalArgumentException("강의 수강신청은 강의 상태가 모집중일 때만 가능합니다.");
        }
    }
}
