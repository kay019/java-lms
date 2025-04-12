package nextstep.session.domain.payment;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;

public class SessionPayments {
    private final SessionType sessionType;
    private final Payments payments;
    private final SessionFee fee;
    private final SessionCapacity capacity;


    public SessionPayments(SessionType sessionType, SessionFee fee, SessionCapacity capacity) {
        this(sessionType, fee, capacity, new Payments());
    }

    public SessionPayments(SessionType sessionType, long fee, int capacity) {
        this(sessionType, new SessionFee(fee), new SessionCapacity(capacity), new Payments());
    }

    public SessionPayments(SessionType sessionType, long fee, int capacity, Payments payments) {
        this(sessionType, new SessionFee(fee), new SessionCapacity(capacity), payments);
    }

    public SessionPayments(SessionType sessionType, SessionFee fee, SessionCapacity capacity, Payments payments) {
        this.sessionType = sessionType;
        this.fee = fee;
        this.capacity = capacity;
        this.payments = payments;
    }

    public boolean isFull() {
        return capacity.isSame(payments.size());
    }

    public boolean matchesFee(Payment payment) {
        return payment.isSameAmount(fee.getValue());
    }

    public boolean add(Payment payment) {
        if (sessionType.canNotEnroll(this, payment)) {
            return false;
        }
        return payments.add(payment);
    }
}
