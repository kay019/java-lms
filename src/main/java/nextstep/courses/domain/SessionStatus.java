package nextstep.courses.domain;

public enum SessionStatus {
    READY("준비중"), OPEN("모집중"), CLOSED("종료");

    private final String description;

    SessionStatus(String description) {
        this.description = description;
    }

    public boolean isNotOpen() {
        return this != OPEN;
    }
}
