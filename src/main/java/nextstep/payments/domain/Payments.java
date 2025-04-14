package nextstep.payments.domain;

import nextstep.session.domain.constraint.SessionConstraint;

import java.util.ArrayList;
import java.util.List;

public class Payments {
    private final List<Payment> value;

    public Payments() {
        this(new ArrayList<>());
    }

    public Payments(List<Payment> payments) {
        this.value = payments;
    }

    public boolean isAvailability(SessionConstraint sessionConstraint) {
        return sessionConstraint.isGreaterThenCapacity(value.size());
    }

    public void add(Payment payment) {
        boolean hasDuplicate = value.stream().anyMatch(payment::equalsSessionUser);
        if (hasDuplicate) {
            throw new IllegalArgumentException("한 유저가 동일한 코스에 두번 결재할 수 없습니다.");
        }
        value.add(payment);
    }
}
