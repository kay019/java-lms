package nextstep.courses.infrastructure;

import nextstep.courses.domain.Enrollment;
import nextstep.courses.domain.RequestStatus;
import nextstep.courses.domain.repository.EnrollmentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class JdbcEnrollmentRepository implements EnrollmentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcEnrollmentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Enrollment enrollment) {
        String sql = "insert into enrollment (ns_user_id, session_id, status) " +
                "values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, enrollment.getUserId());
            ps.setLong(2, enrollment.getSessionId());
            ps.setString(3, enrollment.getStatus().name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Enrollment> findById(Long sessionId, Long userId) {
        String sql = "select id, ns_user_id, session_id, status from enrollment where session_id = ? and ns_user_id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            Long id = rs.getLong("id");
            RequestStatus status = RequestStatus.valueOf(rs.getString("status"));
            Enrollment enrollment = new Enrollment(id, sessionId, userId, status);
            return Optional.of(enrollment);
        }, sessionId, userId);
    }
}
