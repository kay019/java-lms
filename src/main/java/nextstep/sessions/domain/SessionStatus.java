package nextstep.sessions.domain;

public enum SessionStatus {
    READY,
    OPEN,
    CLOSED;

    public boolean isOpened() {
        return this == OPEN;
    }
}
