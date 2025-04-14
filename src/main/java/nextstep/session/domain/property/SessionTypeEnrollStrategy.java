package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.constraint.SessionConstraint;

public interface SessionTypeEnrollStrategy {
    boolean canEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment);
}
