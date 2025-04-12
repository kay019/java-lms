package nextstep.payments.domain;

import java.util.ArrayList;
import java.util.List;

public class Payments {
    private final List<Payment> value;

    public Payments() {
        this.value = new ArrayList<>();
    }

    public Payments(List<Payment> payments) {
        this.value = new ArrayList<>();
        for (Payment payment : payments) {
            this.add(payment);
        }
    }

    public int size() {
        return value.size();
    }

    public void add(Payment payment) {
        boolean hasDuplicate = value.stream().anyMatch(payment::equalsSessionUser);
        if (hasDuplicate) {
            throw new IllegalArgumentException("한 유저가 동일한 코스에 두번 결재할 수 없습니다.");
        }
        value.add(payment);
    }
}
