package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.session.constraint.SessionConstraint;

import java.util.Objects;

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

    public String type() {
        return type.getType();
    }

    public String status() {
        return status.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionEnrollPolicy that = (SessionEnrollPolicy) o;
        return status == that.status && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, type);
    }
}
