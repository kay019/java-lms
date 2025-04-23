package nextstep.courses.course.infrastructure;

import nextstep.courses.course.domain.Course;
import nextstep.courses.course.domain.CourseRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCourseRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Course course) {
        String sql = "insert into course (title, creator_id, created_at, requires_selection) values(?, ?, ?, ?)";
        return jdbcTemplate.update(sql, course.getTitle(), course.getCreatorId(), course.getCreatedAt(), course.getRequiresSelection());
    }

    @Override
    public Course findById(Long id) {
        String sql = "select id, title, creator_id, created_at, updated_at, requires_selection from course where id = ?";
        RowMapper<Course> rowMapper = (rs, rowNum) ->
                new Course(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getLong("creator_id"),
                        toLocalDateTime(rs.getTimestamp("created_at")),
                        toLocalDateTime(rs.getTimestamp("updated_at")),
                        null,
                        rs.getBoolean("requires_selection")
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
