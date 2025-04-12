package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.payment.AmountValidator;
import nextstep.session.domain.payment.CapacityValidator;
import nextstep.session.domain.payment.SessionPayments;

public enum SessionType {
    FREE(((sessionCapacity) -> true), (((sessionPayment, payment) -> true))),
    PAID((SessionPayments::isFull), (SessionPayments::matchesFee));

    private final CapacityValidator capacityValidator;
    private final AmountValidator amountValidator;

    SessionType(CapacityValidator capacityValidator, AmountValidator amountValidator) {
        this.capacityValidator = capacityValidator;
        this.amountValidator = amountValidator;
    }

    public boolean canEnroll(SessionPayments sessionPayments, Payment payment) {
        return capacityValidator.canEnroll(sessionPayments) && this.amountValidator.canEnroll(sessionPayments, payment);
    }

    public boolean canNotEnroll(SessionPayments sessionPayments, Payment payment) {
        return !canEnroll(sessionPayments, payment);
    }
}
