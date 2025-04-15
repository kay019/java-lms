package nextstep.payments.domain;

import java.util.List;

public interface PaymentRepository {
    int save(Payment payment);

    List<Payment> findBySession(Long sessionId);
}
