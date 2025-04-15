package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.session.constraint.SessionConstraint;

public class SessionEnrollPolicy {

    private final SessionStatus status;

    private final SessionType type;

    public SessionEnrollPolicy() {
        this(SessionStatus.PREPARING, SessionType.FREE);
    }

    public SessionEnrollPolicy(SessionStatus status, SessionType type) {
        this.status = status;
        this.type = type;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollCount, long amount) {
        return type.canEnroll(sessionConstraint, enrollCount, amount) && status.canEnroll();
    }
}
