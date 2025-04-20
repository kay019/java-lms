package nextstep.courses.domain;

public enum SessionStatus {
    READY, OPEN, CLOSED;

    public boolean isNotOpen() {
        return this != OPEN;
    }
}
