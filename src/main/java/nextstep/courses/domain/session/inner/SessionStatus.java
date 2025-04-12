package nextstep.courses.domain.session.inner;

public enum SessionStatus {
    PREPARING,
    OPEN,
    CLOSED;

    public boolean canEnroll() {
        return this == OPEN;
    }

}
