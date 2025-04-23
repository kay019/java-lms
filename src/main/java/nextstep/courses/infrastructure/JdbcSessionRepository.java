package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        String sql = "INSERT INTO session " +
                "(start_date, end_date, cover_size, cover_type, cover_width, cover_height, phase, recruit_status, type, capacity, fee) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int res = jdbcTemplate.update(sql,
                session.getPeriod().getStartDate(),
                session.getPeriod().getEndDate(),
                session.getCoverImage().getSize(),
                session.getCoverImage().getType(),
                session.getCoverImage().getWidth(),
                session.getCoverImage().getHeight(),
                session.getStatus().getPhase().name(),
                session.getStatus().getRecruitStatus().name(),
                session.getType().name(),
                (session instanceof PaidSession) ? ((PaidSession) session).getCapacity() : null,
                (session instanceof PaidSession) ? ((PaidSession) session).getFee() : null
        );
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
        CoverImage coverImage = new CoverImage(
                rs.getInt("cover_size"),
                rs.getString("cover_type"),
                rs.getInt("cover_width"),
                rs.getInt("cover_height")
        );
        SessionStatus status = new SessionStatus(
                Phase.valueOf(rs.getString("phase")),
                RecruitStatus.valueOf(rs.getString("recruit_status"))
        );
        SessionType type = SessionType.valueOf(rs.getString("type"));

        List<Long> studentIds = jdbcTemplate.query(
                "SELECT student_id FROM session_student WHERE session_id = ?",
                (rs2, rowNum) -> rs2.getLong("student_id"),
                id
        );

        if (type == SessionType.FREE) {
            return new FreeSession(id, period, coverImage, status);
        }

        Integer capacity = rs.getInt("capacity");
        Long fee = rs.getLong("fee");
        return new PaidSession(id, period, coverImage, status, capacity, fee);
    }
}
