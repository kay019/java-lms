package nextstep.courses.infrastructure;

import nextstep.payments.domain.PaymentRepository;
import nextstep.payments.domain.Payments;
import nextstep.session.domain.CoverImage;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.CoverImages;
import nextstep.session.domain.RecruitmentStatus;
import nextstep.session.domain.RegistrationPolicy;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionPeriod;
import nextstep.session.domain.RegistrationPolicyType;
import nextstep.session.domain.SessionProgressStatus;
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

        for (CoverImage coverImage : session.getCoverImages().getCoverImages()) {
            coverImageRepository.save(coverImage);
        }

        return result;
    }

    private int saveInternal(Session session) {
        String sql = "INSERT INTO session (" +
                "course_id, progress_status, recruitment_status, registration_policy_type, session_fee, max_student_count, selection_required, started_at, ended_at" +
                ") values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, session.getCourseId());
            ps.setString(2, session.getProgressStatus().name());
            ps.setString(3, session.getRecruitmentStatus().name());
            ps.setString(4, session.getRegistrationPolicyType().name());
            ps.setLong(5, session.getSessionFee());
            ps.setInt(6, session.getMaxStudentCount());
            ps.setBoolean(7, session.isSelectionRequired());
            ps.setTimestamp(8, Timestamp.valueOf(session.getStartedAt()));
            ps.setTimestamp(9, Timestamp.valueOf(session.getEndedAt()));
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
            long sessionId = rs.getLong("id");
            long courseId = rs.getLong("course_id");
            CoverImages coverImages = new CoverImages(coverImageRepository.findBySessionId(sessionId));
            SessionProgressStatus progressStatus = SessionProgressStatus.valueOf(rs.getString("progress_status"));
            RecruitmentStatus recruitmentStatus = RecruitmentStatus.valueOf(rs.getString("recruitment_status"));
            RegistrationPolicyType type = RegistrationPolicyType.valueOf(rs.getString("registration_policy_type"));
            long sessionFee = rs.getLong("session_fee");
            int maxStudentCount = rs.getInt("max_student_count");
            LocalDateTime startedAt = rs.getTimestamp("started_at").toLocalDateTime();
            LocalDateTime endedAt = rs.getTimestamp("ended_at").toLocalDateTime();

            boolean selectionRequired = rs.getBoolean("selection_required");

            RegistrationPolicy policy = type.createPolicy(sessionFee, maxStudentCount);
            SessionPeriod period = new SessionPeriod(startedAt, endedAt);

            return new Session(sessionId, courseId, coverImages, progressStatus, recruitmentStatus, policy, period, selectionRequired);
        }, id);
    }

}
