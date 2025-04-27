package nextstep.courses.session.infrastructure;

import nextstep.courses.session.domain.coverImages.CoverImageRepository;
import nextstep.courses.session.domain.coverImages.SessionCoverImage;
import nextstep.courses.session.domain.coverImages.SessionCoverImages;
import nextstep.courses.session.domain.coverImages.SessionImageType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcCoverImageRepository implements CoverImageRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCoverImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(SessionCoverImage image) {
        return jdbcTemplate.update(
                "INSERT INTO session_cover_image (session_id, image_size, image_type, image_width, image_height) VALUES (?, ?, ?, ?, ?)",
                image.getSessionId(),
                image.getSize(),
                image.getType().name(),
                image.getWidth(),
                image.getHeight()
        );
    }

    @Override
    public SessionCoverImages findBySessionId(Long sessionId) {
        List<SessionCoverImage> images = jdbcTemplate.query(
                "SELECT * FROM session_cover_image WHERE session_id = ? ORDER BY id ASC",
                mapRowToImage(),
                sessionId
        );
        return new SessionCoverImages(images);
    }

    private RowMapper<SessionCoverImage> mapRowToImage() {
        return (rs, rowNum) -> new SessionCoverImage(
                rs.getLong("id"),
                rs.getLong("session_id"),
                rs.getDouble("image_size"),
                SessionImageType.valueOf(rs.getString("image_type")),
                rs.getInt("image_width"),
                rs.getInt("image_height")
        );
    }
}

