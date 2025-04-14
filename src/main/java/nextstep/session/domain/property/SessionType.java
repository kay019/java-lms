package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.constraint.SessionConstraint;

public enum SessionType {
    FREE((sessionConstraint, payments, payment) -> true),
    PAID((sessionConstraint, payments, payment) ->
        payment.matchesFee(sessionConstraint) && payments.isAvailability(sessionConstraint)
    );

    private final SessionTypeEnrollStrategy sessionTypeEnrollStrategy;

    SessionType(SessionTypeEnrollStrategy sessionTypeEnrollStrategy) {
        this.sessionTypeEnrollStrategy = sessionTypeEnrollStrategy;
    }

    public boolean canEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment) {
        return sessionTypeEnrollStrategy.canEnroll(sessionConstraint, payments, payment);
    }

    public boolean canNotEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment) {
        return !canEnroll(sessionConstraint, payments, payment);
    }
}
