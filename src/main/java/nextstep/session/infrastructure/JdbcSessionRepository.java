package nextstep.session.infrastructure;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.Student;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.FreeSessionType;
import nextstep.session.domain.type.PaidSessionType;
import nextstep.session.domain.type.SessionType;
import nextstep.users.domain.NsUser;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "INSERT INTO session(start_at, end_at, session_status, capacity, price) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getStartedAt(), session.getEndedAt(), session.getSessionStatus(),
                session.getCapacity(), session.getPrice());
    }

    @Override
    public Optional<Session> findById(Long sessionId) {
        String sql = "SELECT id, start_at, end_at, session_status, capacity, price FROM session WHERE id = ?";
        RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
                rs.getLong(1),
                toLocalDateTime(rs.getTimestamp(2)),
                toLocalDateTime(rs.getTimestamp(3)),
                getSessionCover(rs.getLong(1)),
                getSessionType(rs.getLong(6)),
                SessionStatus.from(rs.getString(4)),
                rs.getLong(5),
                getStudents(rs.getLong(1))
        );

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, sessionId));
    }

    private SessionCover getSessionCover(Long sessionId) {
        String sql = "SELECT id, session_id, size, img_type, width, height FROM session_cover_image WHERE session_id = ?";

        RowMapper<SessionCover> rowMapper = (rs, rowNum) -> new SessionCover(
                rs.getLong(1),
                rs.getLong(2),
                rs.getInt(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getInt(6));

        return jdbcTemplate.queryForObject(sql, rowMapper, sessionId);
    }

    private SessionType getSessionType(Long price) {
        if (price > 0) {
            return new PaidSessionType(price);
        }
        return new FreeSessionType();
    }

    private List<Student> getStudents(Long sessionId) {
        String sql = "SELECT id, ns_user_id, session_id, create_dt FROM student WHERE session_id = ?";

        RowMapper<Student> rowMapper = (rs, rowNum) -> new Student(
                rs.getLong(1),
                new NsUser(rs.getLong(2)),
                new Session(rs.getLong(3)),
                toLocalDateTime(rs.getTimestamp(4)));

        return new ArrayList<>(jdbcTemplate.query(sql, rowMapper, sessionId));
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
