package nextstep.payments.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("paymentRepository")
public class JdbcPaymentRepository implements PaymentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcPaymentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Payment payment) {
        String sql = "insert into payment (session_id, ns_user_id, amount, created_at) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, payment.getSessionId(), payment.getNsUserId(), payment.getAmount(), payment.getCreatedAt());
    }
}
