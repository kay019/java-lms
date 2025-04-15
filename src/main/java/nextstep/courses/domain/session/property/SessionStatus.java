package nextstep.courses.domain.session.property;

public enum SessionStatus {
    PREPARING, ENROLLING, CLOSED;

    public boolean canEnroll() {
        return this == ENROLLING;
    }
}
