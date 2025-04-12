package nextstep.sessionstudent;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository("sessionStudentRepository")
public class JdbcSessionStudentRepository implements SessionStudentRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionStudentRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public SessionStudent findById(long id) {
        String sql = "SELECT * FROM session_student WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getSessionStudentRowMapper(), id);
    }

    @Override
    public SessionStudents findBySessionId(long sessionId) {
        String sql = "SELECT * FROM session_student WHERE session_id = ?";
        List<SessionStudent> students
            = jdbcTemplate.query(sql, getSessionStudentRowMapper(), sessionId);
        return new SessionStudents(students);
    }

    @Override
    public int save(SessionStudent sessionStudent) {
        if (sessionStudent.isUnsaved()) {
            return saveInternal(sessionStudent);
        } else {
            return updateInternal(sessionStudent);
        }
    }

    public int saveInternal(SessionStudent sessionStudent) {
        String sql = "INSERT INTO session_student " +
            "(session_id, ns_user_id, status, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, sessionStudent.getSessionId());
            ps.setLong(2, sessionStudent.getNsUserId());
            ps.setString(3, sessionStudent.getStatus().name());
            ps.setTimestamp(4, Timestamp.valueOf(sessionStudent.getCreatedAt()));
            ps.setTimestamp(5, Timestamp.valueOf(sessionStudent.getUpdatedAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        sessionStudent.setId(id);

        return updated;
    }

    public int updateInternal(SessionStudent sessionStudent) {
        String sql = "UPDATE session_student SET status = ?, updated_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                sessionStudent.getStatus().name(),
                Timestamp.valueOf(sessionStudent.getUpdatedAt()),
                sessionStudent.getId());
    }

    public RowMapper<SessionStudent> getSessionStudentRowMapper() {
        return (rs, rowNum) -> {
            return new SessionStudent(
                    rs.getLong("id"),
                    rs.getLong("session_id"),
                    rs.getLong("ns_user_id"),
                    SessionStudentStatus.valueOf(rs.getString("status")),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime());
        };
    }
}
