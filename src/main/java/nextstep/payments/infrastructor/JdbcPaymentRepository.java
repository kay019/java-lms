package nextstep.payments.infrastructor;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("paymentRepository")
public class JdbcPaymentRepository implements PaymentRepository {

    @Override
    public int save(Payment payment) {
        return 0;
    }

    @Override
    public List<Payment> findBySession(Long sessionId) {
        return null;
    }
}
