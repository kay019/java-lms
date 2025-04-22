package nextstep.courses.session.infrastructure;

import nextstep.courses.enrollment.domain.Enrollments;
import nextstep.courses.session.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "INSERT INTO session (course_id, start_date, end_date, image_size, image_type, image_width, image_height, status, enroll_status, type, max_participants, fee) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SessionDate date = session.getSessionDate();
        SessionCoverImage image = session.getSessionCoverImage();

        return jdbcTemplate.update(sql,
                session.getCourseId(),
                date.getStartDate(),
                date.getEndDate(),
                image.getSize(),
                image.getType().name(),
                image.getWidth(),
                image.getHeight(),
                session.getSessionStatus().name(),
                session.getEnrollStatus().name(),
                session.getSessionType().name(),
                session.getMaxParticipants(),
                session.getFee()
        );
    }

    @Override
    public Session findById(Long id) {
        String sql = "select id, course_id, start_date, end_date, image_size, image_type, image_width, image_height, status, enroll_status, type, max_participants, fee from session where id = ?";
        return jdbcTemplate.queryForObject(sql, mapRowToSession(), id);

    }

    private RowMapper<Session> mapRowToSession() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long courseId = rs.getLong("course_id");

            SessionDate date = new SessionDate(
                    toLocalDateTime(rs.getTimestamp("start_date")),
                    toLocalDateTime(rs.getTimestamp("end_date"))
            );

            SessionCoverImage image = new SessionCoverImage(
                    rs.getDouble("image_size"),
                    SessionImageType.valueOf(rs.getString("image_type")),
                    rs.getInt("image_width"),
                    rs.getInt("image_height")
            );

            SessionStatus status = SessionStatus.valueOf(rs.getString("status"));
            EnrollStatus enrollStatus = EnrollStatus.valueOf(rs.getString("enroll_status"));
            SessionType type = SessionType.valueOf(rs.getString("type"));
            int maxParticipants = rs.getInt("max_participants");
            Long fee = rs.getLong("fee");
            Enrollments enrollments = new Enrollments(new ArrayList<>());

            return new Session(id, courseId, date, image, status, enrollStatus, type, maxParticipants, fee, enrollments);
        };
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
