package nextstep.payments.domain;

import java.util.List;

public interface PaymentRepository {
    int save(Payment paymentRecord);
    Payment findById(long id);
    List<Payment> findBySessionId(long sessionId);
}
