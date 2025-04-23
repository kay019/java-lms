package nextstep.courses.infrastructure;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionDate;
import nextstep.courses.domain.SessionStatus;
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
        String sql = "insert into session (status, started_at, ended_at, created_at, fee_policy, fee, max_student) " +
                "values(?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, paidSession.getStatus().name());
            ps.setObject(2, paidSession.getDate().getStartedAt());
            ps.setObject(3, paidSession.getDate().getEndedAt());
            ps.setObject(4, paidSession.getCreatedAt());
            ps.setString(5, SessionFeePolicy.PAID.name());
            ps.setInt(6, paidSession.getFee()); // Assuming fee is a BigDecimal
            ps.setInt(7, paidSession.getMaxStudent());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "select id, status, started_at, ended_at, created_at, updated_at, fee, max_student from session where id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            Long sessionId = rs.getLong("id");
            SessionStatus status = SessionStatus.valueOf(rs.getString("status"));
            LocalDateTime startedAt = rs.getTimestamp("started_at").toLocalDateTime();
            LocalDateTime endedAt = rs.getTimestamp("ended_at").toLocalDateTime();
            LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
            Timestamp updatedAtTimestamp = rs.getTimestamp("updated_at");
            LocalDateTime updatedAt = updatedAtTimestamp != null ? updatedAtTimestamp.toLocalDateTime() : null;
            int fee = rs.getInt("fee");
            int maxStudent = rs.getInt("max_student");

            SessionDate sessionDate = new SessionDate(startedAt, endedAt);
            PaidSession session = new PaidSession(sessionId, status, sessionDate, createdAt, updatedAt, fee, maxStudent);

            return Optional.of(session);
        }, id);
    }
}
