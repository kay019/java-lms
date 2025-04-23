package nextstep.courses.infrastructure.session;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.inner.EnrollmentStatus;
import nextstep.courses.domain.session.inner.SessionStatus;
import nextstep.courses.domain.session.inner.SessionTypeFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session (course_id, title, session_type, max_enrollment, fee, start_date, finish_date, status, enrollment_status, enrollmentCount) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getCourseId(), session.getTitle(),
                session.getSessionType(), session.getMaxEnrollment(), session.getFee(), session.getStartDate(), session.getFinishDate(),
                session.getStatus(), session.getEnrollmentStatus(), session.getEnrollmentCount());
    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, course_id, title, session_type, max_enrollment, fee, start_date, finish_date, status, enrollment_status, enrollmentCount from session where id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong(1),
                rs.getLong(2),
                rs.getString(3),
                SessionTypeFactory.from(rs.getString(4), rs.getInt(5), rs.getLong(6)),
                toLocalDate(rs.getTimestamp(7)),
                toLocalDate(rs.getTimestamp(8)),
                SessionStatus.valueOf(rs.getString(9)),
                EnrollmentStatus.valueOf(rs.getString(10)),
                rs.getInt(11));
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    private LocalDate toLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime().toLocalDate();
    }
}
