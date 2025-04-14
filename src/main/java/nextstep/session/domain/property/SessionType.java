package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.payment.SessionConstraint;

public enum SessionType {
    FREE((sessionConstraint, payments, payment) -> true),
    PAID((sessionConstraint, payments, payment) ->
        payment.matchesFee(sessionConstraint) && payments.isAvailability(sessionConstraint)
    );

    private final EnrollStrategy enrollStrategy;

    SessionType(EnrollStrategy enrollStrategy) {
        this.enrollStrategy = enrollStrategy;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment) {
        return enrollStrategy.canEnroll(sessionConstraint, payments, payment);
    }

    public boolean canNotEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment) {
        return !canEnroll(sessionConstraint, payments, payment);
    }
}
