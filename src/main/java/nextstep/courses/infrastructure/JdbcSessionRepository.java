package nextstep.courses.infrastructure;

import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.CoverImage;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.RegistrationPolicy;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.RegistrationPolicyType;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionStatus;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

    private final JdbcOperations jdbcTemplate;
    private final CoverImageRepository coverImageRepository;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate,
                                 CoverImageRepository coverImageRepository,
                                 PaymentRepository paymentRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.coverImageRepository = coverImageRepository;
    }

    @Override
    public int save(Session session) {
        int result = 0;

        if (session.isUnsaved()) {
            result = saveInternal(session);
        }

        coverImageRepository.save(session.getCoverImage());

        return result;
    }

    private int saveInternal(Session session) {
        String sql = "INSERT INTO session (" +
            "course_id, cover_image_id, session_status, registration_policy_type, session_fee, max_student_count, started_at, ended_at" +
            ") values (?, ?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, session.getCourseId());
            ps.setLong(2, session.getCoverImage().getId());
            ps.setString(3, session.getSessionStatus().name());
            ps.setString(4, session.getRegistrationPolicyType().name());
            ps.setLong(5, session.getSessionFee());
            ps.setInt(6, session.getMaxStudentCount());
            ps.setTimestamp(7, Timestamp.valueOf(session.getStartedAt()));
            ps.setTimestamp(8, Timestamp.valueOf(session.getEndedAt()));
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();

        session.setId(id);

        return updated;
    }

    @Override
    public Session findById(long id) {
        String sql = "SELECT * FROM session WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rn) -> {
            Long sessionId = rs.getLong("id");
            Long courseId = rs.getLong("course_id");
            CoverImage coverImage = coverImageRepository.findById(rs.getLong("cover_image_id"));
            SessionStatus status = SessionStatus.valueOf(rs.getString("session_status"));
            RegistrationPolicyType type = RegistrationPolicyType.valueOf(rs.getString("registration_policy_type"));
            long sessionFee = rs.getLong("session_fee");
            int maxStudentCount = rs.getInt("max_student_count");
            LocalDateTime startedAt = rs.getTimestamp("started_at").toLocalDateTime();
            LocalDateTime endedAt = rs.getTimestamp("ended_at").toLocalDateTime();

            RegistrationPolicy policy = type.createPolicy(sessionFee, maxStudentCount);
            SessionPeriod period = new SessionPeriod(startedAt, endedAt);

            return new Session(sessionId, courseId, coverImage, status, policy, period);
        }, id);
    }

}
