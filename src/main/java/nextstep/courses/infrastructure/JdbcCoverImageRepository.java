package nextstep.courses.infrastructure;

import nextstep.session.domain.CoverImage;
import nextstep.session.domain.CoverImageRepository;
import nextstep.session.domain.CoverImageType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CoverImage coverImage) {
        if (coverImage.isUnsaved()) {
            return saveInternal(coverImage);
        }

        return 0;
    }

    private int saveInternal(CoverImage coverImage) {
        final String sql = "INSERT INTO cover_image (session_id, size, type, width, height) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, coverImage.getSessionId());
            ps.setLong(2, coverImage.getSize());
            ps.setString(3, coverImage.getType().name());
            ps.setInt(4, coverImage.getWidth());
            ps.setInt(5, coverImage.getHeight());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        coverImage.setId(id);

        return updated;
    }

    @Override
    public CoverImage findById(long id) {
        final String sql = "SELECT * FROM cover_image WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, getCoverImageRowMapper(), id);
    }

    @Override
    public List<CoverImage> findBySessionId(long sessionId) {
        final String sql = "SELECT * FROM cover_image WHERE session_id = ?";
        return jdbcTemplate.query(sql, getCoverImageRowMapper(), sessionId);
    }

    private static RowMapper<CoverImage> getCoverImageRowMapper() {
        return (rs, rowNum) -> {
            long id = rs.getLong("id");
            long sessionId = rs.getLong("session_id");
            CoverImageType type = CoverImageType.valueOf(rs.getString("type"));
            long size = rs.getLong("size");
            int width = rs.getInt("width");
            int height = rs.getInt("height");
            return new CoverImage(id, sessionId, type, size, width, height);
        };
    }
}
