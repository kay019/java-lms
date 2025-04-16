package nextstep.courses.session.domain;

public enum SessionStatus {
    PREPARING,
    OPEN,
    CLOSE;

    public boolean canEnroll() {
        return this == OPEN;
    }
}
