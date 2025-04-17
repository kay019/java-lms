package nextstep.courses.infrastructure;

import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.Session;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "insert into session (" +
            "created_at," +
            "deleted," +
            "course_id," +
            "fee," +
            "capacity," +
            "image_url," +
            "image_type," +
            "start_date," +
            "end_date," +
            "type," +
            "status" +
            ") values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(
            sql,
            session.createdAt(),
            session.deleted(),
            session.courseId(),
            session.fee(),
            session.capacity(),
            session.imageUrl(),
            session.imageType(),
            session.startDate(),
            session.endDate(),
            session.type(),
            session.status()
        );
    }

    @Override
    public Session findById(Long sessionId) {
        return null;
    }

    @Override
    public List<Session> findByCourse(Long courseId) {
        return null;
    }
}
