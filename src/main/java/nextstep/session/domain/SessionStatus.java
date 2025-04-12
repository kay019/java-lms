package nextstep.session.domain;

public enum SessionStatus {
    PREPARING, ENROLLING, CLOSED;

    public boolean canEnroll() {
        return this == ENROLLING;
    }
}
