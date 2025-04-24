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

@Repository("paidSessionRepository")
public class JdbcPaidSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcPaidSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Session session) {
        PaidSession paidSession = (PaidSession) session;
        String sql = "insert into session (session_status, enroll_status, started_at, ended_at, created_at, fee_policy, fee, max_student) " +
                "values(?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, paidSession.getSessionStatus().name());
            ps.setString(2, paidSession.getEnrollStatus().name());
            ps.setObject(3, paidSession.getDate().getStartedAt());
            ps.setObject(4, paidSession.getDate().getEndedAt());
            ps.setObject(5, paidSession.getCreatedAt());
            ps.setString(6, SessionFeePolicy.PAID.name());
            ps.setInt(7, paidSession.getFee());
            ps.setInt(8, paidSession.getMaxStudent());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, session_status, enroll_status, started_at, ended_at, created_at, updated_at, fee, max_student from session where id = ?";

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
            int fee = rs.getInt("fee");
            int maxStudent = rs.getInt("max_student");

            SessionDate sessionDate = new SessionDate(startedAt, endedAt);
            PaidSession session = new PaidSession(sessionId, sessionStatus, enrollStatus, sessionDate, createdAt, updatedAt, fee, maxStudent);

            return Optional.of(session);
        }, id);
    }
}
