package nextstep.session.domain.property;

public enum SessionStatus {
    PREPARING, ENROLLING, CLOSED;

    public boolean canEnroll() {
        return this == ENROLLING;
    }

    public boolean canNotEnroll() {
        return !canEnroll();
    }
}
