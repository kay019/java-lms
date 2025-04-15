package nextstep.session.domain.property;

import nextstep.session.domain.constraint.SessionConstraint;

public class SessionProperty {

    private final SessionStatus status;

    private final SessionType type;

    public SessionProperty() {
        this(SessionStatus.PREPARING, SessionType.FREE);
    }

    public SessionProperty(SessionStatus status, SessionType type) {
        this.status = status;
        this.type = type;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollCount, long amount) {
        return type.canEnroll(sessionConstraint, enrollCount, amount) && status.canEnroll();
    }

    public boolean canNotEnroll(SessionConstraint sessionConstraint, int enrollCount, long amount) {
        return !canEnroll(sessionConstraint, enrollCount, amount);
    }
}
