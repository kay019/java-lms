package nextstep.courses.domain.session.policy;

public enum SessionStatus {
    PREPARING, ENROLLING, CLOSED;

    public boolean canEnroll() {
        return this == ENROLLING;
    }
}
