package nextstep.payments.infrastructure;

import nextstep.payments.domain.Payment;
import nextstep.payments.domain.PaymentRepository;
import nextstep.session.domain.Money;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("paymentRepository")
public class JdbcPaymentRepository implements PaymentRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcPaymentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Payment payment) {
        if (payment.isUnsaved()) {
            return saveInternal(payment);
        }

        return 0;
    }

    private int saveInternal(Payment payment) {
        final String sql = "INSERT INTO payment (session_id, ns_user_id, amount, created_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, payment.getSessionId());
            ps.setLong(2, payment.getNsUserId());
            ps.setLong(3, payment.getAmount().getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(payment.getCreatedAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        payment.setId(id);

        return updated;
    }

    @Override
    public Payment findById(long id) {
        final String sql = "SELECT * FROM payment WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getPaymentRowMapper(), id);
    }

    @Override
    public List<Payment> findBySessionId(long sessionId) {
        final String sql = "SELECT * FROM payment WHERE session_id = ? order by id desc";
        return jdbcTemplate.query(sql, getPaymentRowMapper(), sessionId);
    }

    private static RowMapper<Payment> getPaymentRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long sessionId = rs.getLong("session_id");
            long nsUserId = rs.getLong("ns_user_id");
            long amount = rs.getLong("amount");
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            return new Payment(id, sessionId, nsUserId, new Money(amount), createdAt);
        };
    }
}
