package nextstep.payments.infrastructure;

import nextstep.payments.record.PaymentRecord;
import nextstep.payments.domain.PaymentRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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
    public int save(PaymentRecord paymentRecord) {
        final String sql = "INSERT INTO payment (session_id, ns_user_id, amount, created_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, paymentRecord.getSessionId());
            ps.setLong(2, paymentRecord.getNsUserId());
            ps.setLong(3, paymentRecord.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(paymentRecord.getCreatedAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        paymentRecord.setId(id);

        return updated;
    }

    @Override
    public PaymentRecord findById(long id) {
        final String sql = "SELECT * FROM payment WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getPaymentRowMapper(), id);
    }

    @Override
    public List<PaymentRecord> findBySessionId(long sessionId) {
        final String sql = "SELECT * FROM payment WHERE session_id = ? order by id desc";
        return jdbcTemplate.query(sql, getPaymentRowMapper(), sessionId);
    }

    private static RowMapper<PaymentRecord> getPaymentRowMapper() {
        return (rs, rowNum) -> {
            PaymentRecord paymentRecord = new PaymentRecord();
            paymentRecord.setId(rs.getLong("id"));
            paymentRecord.setSessionId(rs.getLong("session_id"));
            paymentRecord.setNsUserId(rs.getLong("ns_user_id"));
            paymentRecord.setAmount(rs.getLong("amount"));
            paymentRecord.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return paymentRecord;
        };
    }
}
