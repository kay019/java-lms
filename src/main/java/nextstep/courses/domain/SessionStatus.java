package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING("준비중"),
    ONGOING("진행중"),
    ENDED("종료");

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

}
