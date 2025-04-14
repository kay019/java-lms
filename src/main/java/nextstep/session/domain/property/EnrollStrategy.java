package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.constraint.SessionConstraint;

public interface EnrollStrategy {
    boolean canEnroll(SessionConstraint sessionConstraint, Payments payments, Payment payment);
}
