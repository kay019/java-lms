package nextstep.courses.domain.session.property;

import nextstep.courses.domain.session.constraint.SessionConstraint;

public enum SessionType {
    FREE((sessionConstraint, payments, payment) -> true),
    PAID((sessionConstraint, enrollmentCount, amount) ->
        sessionConstraint.isGreaterThenCapacity(enrollmentCount) && sessionConstraint.isSameFee(amount)
    );

    private final SessionTypeEnrollStrategy sessionTypeEnrollStrategy;

    SessionType(SessionTypeEnrollStrategy sessionTypeEnrollStrategy) {
        this.sessionTypeEnrollStrategy = sessionTypeEnrollStrategy;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount) {
        return sessionTypeEnrollStrategy.canEnroll(sessionConstraint, enrollmentCount, amount);
    }

    public boolean canNotEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount) {
        return !canEnroll(sessionConstraint, enrollmentCount, amount);
    }
}
