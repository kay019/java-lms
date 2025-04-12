package nextstep.payments.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Payments {
    private List<Payment> value;

    public Payments(List<Payment> payments) {

        boolean hasDuplicate = payments.stream()
            .anyMatch(payment -> payments.stream()
                .filter(other -> !payment.equals(other))
                .anyMatch(other -> payment.equalsSessionUser(other))
            );

        if (hasDuplicate) {
            throw new IllegalArgumentException("한 유저가 동일한 코스에 두번 결재할 수 없습니다.");
        }

        this.value = payments;
    }
}
