package nextstep.courses.domain;

public enum SessionStatus {
    READY("준비중"),
    RECRUITING("모집중"),
    CLOSED("종료");

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

    public void validateEnroll() {
        if (this != RECRUITING) {
            throw new IllegalArgumentException("강의 수강신청은 강의 상태가 모집중일 때만 가능합니다.");
        }
    }
}
