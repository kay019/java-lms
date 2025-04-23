package nextstep.courses.domain.session.inner;

public enum SessionStatus {
    PREPARING("준비중"),     // 강의 준비 중
    IN_PROGRESS("진행중"),   // 강의 진행 중
    COMPLETED("종료");       // 강의 종료됨

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
