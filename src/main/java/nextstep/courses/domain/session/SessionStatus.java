package nextstep.courses.domain.session;

public enum SessionStatus {
    PREPARING,
    OPEN,
    CLOSED;

    public boolean canEnroll() {
        return this == OPEN;
    }

}
