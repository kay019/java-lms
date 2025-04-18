package nextstep.payments.domain;

import nextstep.courses.domain.session.Session;
import nextstep.payments.entity.PaymentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Payments {
    private final List<Payment> value;

    public Payments() {
        this(new ArrayList<>());
    }

    public Payments(List<Payment> payments) {
        this.value = payments;
    }

    public void add(Payment payment) {
        boolean hasDuplicate = value.stream().anyMatch(payment::equalsSessionUser);
        if (hasDuplicate) {
            throw new IllegalArgumentException("한 유저가 동일한 코스에 두번 결재할 수 없습니다.");
        }
        value.add(payment);
    }

    public int size() {
        return value.size();
    }

    public boolean canEnroll(Session session, Payment other) {
        Payments filtered = new Payments(
            value.stream()
                .filter(other::isSameSession)
                .collect(Collectors.toList())
        );
        return other.canEnroll(session, filtered.size());
    }
}
