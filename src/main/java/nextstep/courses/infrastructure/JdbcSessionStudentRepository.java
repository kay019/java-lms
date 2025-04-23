package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStudentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Long> findSessionIdsByStudent(Long studentId) {
        return jdbcTemplate.query("SELECT session_id FROM session_student WHERE student_id = ?",
                (rs, rowNum) -> rs.getLong("session_id"), studentId);
    }

    @Override
    public List<Long> findStudentIdsBySession(Long sessionId) {
        return jdbcTemplate.query("SELECT student_id FROM session_student WHERE session_id = ?",
                (rs, rowNum) -> rs.getLong("student_id"), sessionId);
    }
}
