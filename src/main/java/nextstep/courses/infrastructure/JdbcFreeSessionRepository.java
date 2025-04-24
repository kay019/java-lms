package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import nextstep.courses.domain.repository.SessionRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository("freeSessionRepository")
public class JdbcFreeSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcFreeSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        FreeSession freeSession = (FreeSession) session;

        String sql = "insert into session (session_status, enroll_status, started_at, ended_at, created_at, fee_policy) " +
                "values(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, freeSession.getSessionStatus().name());
            ps.setString(2, freeSession.getEnrollStatus().name());
            ps.setObject(3, freeSession.getDate().getStartedAt());
            ps.setObject(4, freeSession.getDate().getEndedAt());
            ps.setObject(5, freeSession.getCreatedAt());
            ps.setString(6, SessionFeePolicy.FREE.name());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, session_status, enroll_status, started_at, ended_at, created_at, updated_at from session where id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Long sessionId = rs.getLong("id");
            SessionStatus sessionStatus = SessionStatus.valueOf(rs.getString("session_status"));
            EnrollStatus enrollStatus = EnrollStatus.valueOf(rs.getString("enroll_status"));
            LocalDateTime startedAt = rs.getTimestamp("started_at").toLocalDateTime();
            LocalDateTime endedAt = rs.getTimestamp("ended_at").toLocalDateTime();
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
            LocalDateTime updatedAt = updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null;

            SessionDate sessionDate = new SessionDate(startedAt, endedAt);
            FreeSession session = new FreeSession(sessionId, sessionStatus, enrollStatus, sessionDate, createdAt, updatedAt);

            return Optional.of(session);
        }, id);
    }
}
