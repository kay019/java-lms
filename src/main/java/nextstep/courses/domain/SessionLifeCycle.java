package nextstep.courses.domain;

public enum SessionLifeCycle {
    READY("준비중"), IN_PROGRESS("진행중"), CLOSED("종료");

    public final String description;

    SessionLifeCycle(String description) {
        this.description = description;
    }
}