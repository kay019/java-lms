package nextstep.payments.domain;

import java.util.ArrayList;
import java.util.List;

public class Payments {
    private final List<Payment> payments;

    public Payments() {
        this(new ArrayList<>());
    }

    public Payments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public int getSize() {
        return payments.size();
    }

    public void add(Payment payment) {
        payments.add(payment);
    }
}
