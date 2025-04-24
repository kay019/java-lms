package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;
    private final JdbcCoverImageDao jdbcCoverImageDao;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate, JdbcCoverImageDao jdbcCoverImageDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcCoverImageDao = jdbcCoverImageDao;
    }

    @Override
    public int save(Session session) {
        String sql = "INSERT INTO session " +
                "(start_date, end_date, phase, recruit_status, pricing_type, participant_type, capacity, fee) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int res = jdbcTemplate.update(sql,
                session.getPeriod().getStartDate(),
                session.getPeriod().getEndDate(),
                session.getStatus().getPhase().name(),
                session.getStatus().getRecruitStatus().name(),
                session.getType().getPricingType().name(),
                session.getType().getParticipantType().name(),
                (session instanceof PaidSession) ? ((PaidSession) session).getCapacity() : null,
                (session instanceof PaidSession) ? ((PaidSession) session).getFee() : null
        );
        saveCoverImages(session.getId(), session.getCoverImages());
        saveStudents(session.getId(), session.getStudents());
        return res;
    }

    @Override
    public Optional<Session> findById(Long id) {
        String sql = "SELECT * FROM session WHERE id = ?";
        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }
            return Optional.of(mapRowToSession(rs));
        }, id);
    }

    private void saveCoverImages(Long sessionId, CoverImages coverImages) {
        String sql = "INSERT INTO cover_image (session_id, size, type, width, height) VALUES (?, ?, ?, ?, ?)";
        for (CoverImage coverImage : coverImages.getValues()) {
            jdbcTemplate.update(sql,
                    sessionId,
                    coverImage.getSize(),
                    coverImage.getType(),
                    coverImage.getWidth(),
                    coverImage.getHeight()
            );
        }
    }

    private void saveStudents(Long id, Students students) {
        String sql = "INSERT INTO session_student (session_id, student_id) VALUES (?, ?)";
        for (Student student : students.getValues()) {
            jdbcTemplate.update(sql, id, student.getId());
        }
    }

    private Session mapRowToSession(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Period period = new Period(
                rs.getDate("start_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate()
        );

        CoverImages coverImages = new CoverImages(jdbcCoverImageDao.findBySessionId(id));

        SessionStatus status = new SessionStatus(
                Phase.valueOf(rs.getString("phase")),
                RecruitStatus.valueOf(rs.getString("recruit_status"))
        );
        PricingType pricingType = PricingType.valueOf(rs.getString("pricing_type"));
        ParticipantType participantType = ParticipantType.valueOf(rs.getString("participant_type"));

        if (pricingType == PricingType.FREE) {
            return new FreeSession(id, period, coverImages, status, participantType);
        }

        Integer capacity = rs.getInt("capacity");
        Long fee = rs.getLong("fee");
        return new PaidSession(id, period, coverImages, status, participantType, capacity, fee);
    }
}
