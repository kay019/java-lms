package nextstep.session.domain.payment;

import nextstep.payments.domain.Payment;

public interface AmountValidator {
    boolean canEnroll(SessionPayments sessionPayments, Payment payment);
}
