package nextstep.courses.infrastructure;

import nextstep.courses.RegistryDto;
import nextstep.courses.SessionDto;
import nextstep.courses.domain.*;
import nextstep.users.domain.NsStudent;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        // session 저장
        String sql_session = "insert into session (start_at, end_at, image_size, image_type, image_width, image_height, price) values(?, ?, ?, ?, ?, ?, ?)";
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql_session, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, Timestamp.valueOf(session.getStartDate()));
            ps.setTimestamp(2, Timestamp.valueOf(session.getEndDate()));
            ps.setLong(3, session.getImageSize());
            ps.setString(4, session.getImageType());
            ps.setLong(5, session.getImageWidth());
            ps.setLong(6, session.getImageHeight());
            ps.setLong(7, session.getPrice());
            return ps;
        }, generatedKeyHolder);

        Long sessionId = generatedKeyHolder.getKey().longValue();
        Registry registry = session.getRegistry();

        // registry 저장
        String sql_registry = "insert into registry (session_id, session_state, pay_strategy, capacity) values(?, ?, ?, ?)";

        jdbcTemplate.update(sql_registry, sessionId, registry.getSessionState(), registry.getPayStrategy(), registry.getCapacity());

        List<NsStudent> students = registry.getStudents();

        // students 저장
        String sql_students = "insert into ns_students (session_id, user_id) values(?, ?)";
        for(NsStudent student: students) {
            jdbcTemplate.update(sql_students, sessionId, student.getUserId());
        }

        return 1;
    }

    @Override
    public SessionDto findSessionDtoById(Long id) {
        String sql = "SELECT id, start_at, end_at, image_size, image_type, image_width, image_height, price FROM session WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new SessionDto(
                rs.getLong(1),
                toLocalDateTime(rs.getTimestamp(2)),
                toLocalDateTime(rs.getTimestamp(3)),
                rs.getLong(4),
                rs.getString(5),
                rs.getLong(6),
                rs.getLong(7),
                rs.getLong(8)
        ), id);
    }

    @Override
    public RegistryDto findRegistryDtoBySessionId(Long sessionId) {
        String sql = "SELECT pay_strategy, session_state, capacity FROM registry WHERE session_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new RegistryDto(
                rs.getString(1),
                rs.getString(2),
                rs.getLong(3)
        ), sessionId);
    }

    @Override
    public List<Long> findStudentIdBySessionId(Long sessionId) {
        String sql = "SELECT user_id FROM ns_students WHERE session_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getLong(1), sessionId);
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
