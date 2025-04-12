package nextstep.session.domain.payment;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.Payments;

public class SessionPayments {

    private Payments payments;

    private SessionFee fee;

    private SessionCapacity capacity;

    public SessionPayments() {

    }

    public SessionPayments(SessionFee fee, SessionCapacity capacity) {
        this(fee, capacity, new Payments());
    }

    public SessionPayments( long fee, int capacity) {
        this(new SessionFee(fee), new SessionCapacity(capacity), new Payments());
    }

    public SessionPayments( long fee, int capacity, Payments payments) {
        this(new SessionFee(fee), new SessionCapacity(capacity), payments);
    }

    public SessionPayments(SessionFee fee, SessionCapacity capacity, Payments payments) {
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

    public void add(Payment payment) {
        payments.add(payment);
    }
}
