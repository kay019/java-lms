package nextstep.courses.enrollment.infrastructure;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.EnrollmentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository("enrollmentRepository")
public class JdbcEnrollmentRepository implements EnrollmentRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcEnrollmentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (session_id, user_id, payment_id, created_at) VALUES (?, ?, ?, ?)";
        String paymentId = enrollment.getPayment() != null ? enrollment.getPayment().getId() : null;

        return jdbcTemplate.update(sql,
                enrollment.getSessionId(),
                enrollment.getNsUser().getId(),
                paymentId,
                enrollment.getCreatedAt()
        );
    }

    @Override
    public Enrollment findById(Long id) {
        String sql = "SELECT id, session_id, user_id, payment_id, created_at FROM enrollment WHERE id = ?";

        RowMapper<Enrollment> rowMapper = (rs, rowNum) -> new Enrollment(
                rs.getLong("id"),
                rs.getLong("session_id"),

                // 도메인주입
                null,
                null,

                toLocalDateTime(rs.getTimestamp("created_at"))
        );

        return jdbcTemplate.queryForObject(sql, rowMapper, id);

    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
