package nextstep.courses.infrastructure;

import nextstep.courses.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(Session session) {
        CoverImage coverImage = session.getCoverImage();
        String sql = "INSERT INTO session (start_date, end_date, cover_size, cover_type, cover_width, cover_height, status, type, capacity, fee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, session.getStartDate(), session.getEndDate(), coverImage.getSize(), coverImage.getType(), coverImage.getWidth(), coverImage.getHeight(), session.getStatus().name(), session.getType().name(), session.getCapacity(), session.getFee());
    }

    @Override
    public Session findById(Long id) {
        String sql = "SELECT * FROM session WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CoverImage cover = new CoverImage(
                    rs.getInt("cover_size"),
                    rs.getString("cover_type"),
                    rs.getInt("cover_width"),
                    rs.getInt("cover_height")
            );

            return new Session(
                    rs.getLong("id"),
                    rs.getDate("start_date").toLocalDate(),
                    rs.getDate("end_date").toLocalDate(),
                    cover,
                    SessionStatus.valueOf(rs.getString("status")),
                    SessionType.valueOf(rs.getString("type")),
                    rs.getInt("capacity"),
                    rs.getInt("fee")
            );
        }, id);
    }
}
