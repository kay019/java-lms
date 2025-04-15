package nextstep.courses.domain.session.policy;

import nextstep.courses.domain.session.constraint.SessionConstraint;

public enum SessionType {
    FREE((sessionConstraint, payments, payment) -> true),
    PAID((sessionConstraint, enrollmentCount, amount) ->
        sessionConstraint.isGreaterThenCapacity(enrollmentCount) && sessionConstraint.isSameFee(amount)
    );

    private final EnrollStrategy enrollStrategy;

    SessionType(EnrollStrategy enrollStrategy) {
        this.enrollStrategy = enrollStrategy;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, int enrollmentCount, long amount) {
        return enrollStrategy.canEnroll(sessionConstraint, enrollmentCount, amount);
    }
}
