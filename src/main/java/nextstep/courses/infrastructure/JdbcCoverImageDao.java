package nextstep.courses.infrastructure;

import nextstep.courses.domain.CoverImage;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCoverImageDao {
    private final JdbcOperations jdbcTemplate;

    public JdbcCoverImageDao(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CoverImage> findBySessionId(Long sessionId) {
        String sql = "SELECT size, type, width, height FROM cover_image WHERE session_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new CoverImage(
                                rs.getInt("size"),
                                rs.getString("type"),
                                rs.getInt("width"),
                                rs.getInt("height")
                        ),
                sessionId
        );
    }
}
