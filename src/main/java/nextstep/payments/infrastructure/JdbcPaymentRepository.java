package nextstep.payments.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("paymentRepository")
public class JdbcPaymentRepository implements PaymentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcPaymentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Payment> findAllBySessionIdAndUserId(Long sessionId, Long userId) {
        return List.of();
    }

} 
