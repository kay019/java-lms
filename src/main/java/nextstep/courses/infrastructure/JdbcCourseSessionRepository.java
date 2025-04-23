package nextstep.courses.infrastructure;

import nextstep.courses.domain.CourseSessionRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("courseSessionRepository")
public class JdbcCourseSessionRepository implements CourseSessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcCourseSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Long> findCourseIdsBySession(Long sessionId) {
        return jdbcTemplate.query("SELECT course_id FROM course_session WHERE session_id = ?",
                (rs, rowNum) -> rs.getLong("course_id"), sessionId);
    }

    @Override
    public List<Long> findSessionIdsByCourse(Long courseId) {
        return jdbcTemplate.query("SELECT session_id FROM course_session WHERE course_id = ?",
                (rs, rowNum) -> rs.getLong("session_id"), courseId);
    }
}
