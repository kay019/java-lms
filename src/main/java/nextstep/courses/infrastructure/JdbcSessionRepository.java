package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import nextstep.courses.domain.repository.SessionRepository;
import nextstep.courses.entity.SessionEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        SessionEntity entity = SessionEntity.toEntity(session);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "insert into session (session_status, enroll_status, started_at, ended_at, created_at, fee_policy, fee, max_student) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, entity.getSessionStatus());
            ps.setString(2, entity.getEnrollStatus());
            ps.setObject(3, entity.getStartedAt());
            ps.setObject(4, entity.getEndedAt());
            ps.setObject(5, entity.getCreatedAt());
            ps.setString(6, entity.getFeePolicy());
            ps.setInt(7, entity.getFee());
            ps.setInt(8, entity.getMaxStudent());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, session_status, enroll_status, started_at, ended_at, created_at, updated_at, fee, max_student, fee_policy from session where id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Long sessionId = rs.getLong("id");
            SessionStatus sessionStatus = SessionStatus.valueOf(rs.getString("session_status"));
            EnrollStatus enrollStatus = EnrollStatus.valueOf(rs.getString("enroll_status"));
            LocalDateTime startedAt = rs.getTimestamp("started_at").toLocalDateTime();
            LocalDateTime endedAt = rs.getTimestamp("ended_at").toLocalDateTime();
            SessionDate sessionDate = new SessionDate(startedAt, endedAt);
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
            LocalDateTime updatedAt = updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null;
            SessionFeePolicy feePolicy = SessionFeePolicy.valueOf(rs.getString("fee_policy"));

            if (feePolicy.equals(SessionFeePolicy.FREE)) {
                FreeSession freeSession = new FreeSession(sessionId, sessionStatus, enrollStatus, sessionDate, createdAt, updatedAt);
                return Optional.of(freeSession);
            }

            int fee = rs.getInt("fee");
            int maxStudent = rs.getInt("max_student");
            PaidSession paidSession = new PaidSession(sessionId, sessionStatus, enrollStatus, sessionDate, createdAt, updatedAt, fee, maxStudent);
            return Optional.of(paidSession);
        }, id);
    }
}
