package nextstep.courses.infrastructure;

import nextstep.courses.domain.Course;
import nextstep.courses.domain.CourseRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcCourseRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Course course) {
        String sql = "insert into course (title, creator_id, created_at) values(?, ?, ?)";
        int ret = jdbcTemplate.update(sql, course.getTitle(), course.getCreatorId(), course.getCreatedAt());
        saveSessions(course.getId(), course.getSessionsIds());
        return ret;
    }

    @Override
    public Optional<Course> findById(Long id) {
        String sql = "SELECT * FROM course WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(mapRowToCourse(rs));
        }, id);
    }

    private void saveSessions(Long courseId, List<Long> sessionIds) {
        String sql = "INSERT INTO course_session (course_id, session_id) VALUES (?, ?)";
        for (Long sessionId : sessionIds) {
            jdbcTemplate.update(sql, courseId, sessionId);
        }
    }

    private Course mapRowToCourse(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        Long creatorId = rs.getLong("creator_id");
        LocalDateTime createdAt = toLocalDateTime(rs.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(rs.getTimestamp("updated_at"));

        return new Course(id, title, creatorId, createdAt, updatedAt);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
