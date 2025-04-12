package nextstep.courses.infrastructure;

import nextstep.courses.record.SessionRecord;
import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionRecord sessionRecord) {
        String sql = "INSERT INTO session (" +
                "course_id, cover_image_id, session_status, registration_policy_type, session_fee, max_student_count, started_at, ended_at" +
                ") values (?, ?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, sessionRecord.getCourseId());
            ps.setLong(2, sessionRecord.getCoverImageId());
            ps.setString(3, sessionRecord.getSessionStatus().name());
            ps.setString(4, sessionRecord.getRegistrationPolicyType().name());
            ps.setLong(5, sessionRecord.getSessionFee());
            ps.setInt(6, sessionRecord.getMaxStudentCount());
            ps.setTimestamp(7, Timestamp.valueOf(sessionRecord.getStartedAt()));
            ps.setTimestamp(8, Timestamp.valueOf(sessionRecord.getEndedAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        sessionRecord.setId(id);

        return updated;
    }

    @Override
    public SessionRecord findById(long id) {
        final String sql = "SELECT * FROM session WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            SessionRecord sessionRecord = new SessionRecord();
            sessionRecord.setId(rs.getLong("id"));
            sessionRecord.setCourseId(rs.getLong("course_id"));
            sessionRecord.setCoverImageId(rs.getLong("cover_image_id"));
            String sessionStatus = rs.getString("session_status");
            sessionRecord.setSessionStatus(SessionStatus.valueOf(sessionStatus));
            String registrationPolicyType = rs.getString("registration_policy_type");
            sessionRecord.setRegistrationPolicyType(RegistrationPolicyType.valueOf(registrationPolicyType));
            sessionRecord.setSessionFee(rs.getLong("session_fee"));
            sessionRecord.setMaxStudentCount(rs.getInt("max_student_count"));
            Timestamp startedAt = rs.getTimestamp("started_at");
            sessionRecord.setStartedAt(startedAt.toLocalDateTime());
            Timestamp endedAt = rs.getTimestamp("ended_at");
            sessionRecord.setEndedAt(endedAt.toLocalDateTime());
            return sessionRecord;
        }, id);
    }

}
