package nextstep.courses.domain;

public enum SessionStatus {
    PREPARING,
    OPEN,
    CLOSED;

    public boolean canEnroll() {
        return this == OPEN;
    }

}
