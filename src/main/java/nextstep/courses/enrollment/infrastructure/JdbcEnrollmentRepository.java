package nextstep.courses.enrollment.infrastructure;

import nextstep.courses.enrollment.domain.Enrollment;
import nextstep.courses.enrollment.domain.EnrollmentRepository;
import nextstep.courses.enrollment.domain.EnrollmentStatus;
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
        String sql = "INSERT INTO enrollment (session_id, user_id, payment_id, status, selected, created_at) VALUES (?, ?, ?, ?)";
        String paymentId = enrollment.getPayment() != null ? enrollment.getPayment().getId() : null;

        return jdbcTemplate.update(sql,
                enrollment.getSessionId(),
                enrollment.getNsUser().getId(),
                paymentId,
                enrollment.getStatus().name(),
                enrollment.isSelected(),
                enrollment.getCreatedAt()
        );
    }

    @Override
    public Enrollment findById(Long id) {
        String sql = "SELECT id, session_id, user_id, payment_id, status, selected, created_at FROM enrollment WHERE id = ?";

        RowMapper<Enrollment> rowMapper = (rs, rowNum) -> {
            String stringStatus = rs.getString("status");

            EnrollmentStatus status = stringStatus == null ? EnrollmentStatus.APPROVED : EnrollmentStatus.valueOf(stringStatus);

            return new Enrollment(
                    rs.getLong("id"),
                    rs.getLong("session_id"),

                    // id를 가지고 service 에서 객체를 넣어줌
                    null,
                    null,
                    status,
                    rs.getBoolean("selected"),
                    toLocalDateTime(rs.getTimestamp("created_at")));
        };
        return jdbcTemplate.queryForObject(sql, rowMapper, id);

    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
