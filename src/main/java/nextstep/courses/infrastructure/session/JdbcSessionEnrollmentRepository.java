package nextstep.courses.infrastructure.session;

import nextstep.courses.domain.session.SessionEnrollment;
import nextstep.courses.domain.session.SessionEnrollmentRepository;
import nextstep.courses.domain.session.inner.UserEnrollmentStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionEnrollmentRepository")
public class JdbcSessionEnrollmentRepository implements SessionEnrollmentRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionEnrollmentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionEnrollment sessionEnrollment) {
        String sql = "insert into session_enrollment (session_id, ns_user_id, status) values (?, ?, ?)";
        return jdbcTemplate.update(sql, sessionEnrollment.getSessionId(),
                sessionEnrollment.getNsUserId(),
                sessionEnrollment.getStatus().name());
    }

    @Override
    public SessionEnrollment findById(Long id) {
        String sql = "select id, session_id, ns_user_id, status from session_enrollment where id = ?";
        RowMapper<SessionEnrollment> rowMapper = (rs, rowNum) -> new SessionEnrollment(
                rs.getLong(1),
                rs.getLong(2),
                rs.getLong(3),
                UserEnrollmentStatus.valueOf(rs.getString(4))

        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }
}
