package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.CoverImage;
import nextstep.courses.domain.image.CoverImageExtension;
import nextstep.courses.domain.image.CoverImageFileSize;
import nextstep.courses.domain.image.CoverImagePixelSize;
import nextstep.courses.domain.repository.CoverImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class JdbcCoverImageRepository implements CoverImageRepository {
    private final JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(CoverImage image, Long sessionId) {
        String sql = "insert into cover_image (session_id, size, extension, width, height) " +
                "values(?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, sessionId);
            ps.setLong(2, image.getFileSize().getSize());
            ps.setString(3, image.getExtension().name());
            ps.setInt(4, image.getPixelSize().getWidth());
            ps.setInt(5, image.getPixelSize().getHeight());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<CoverImage> findByAllSessionId(Long sessionId) {
        String sql = "select id, size, extension, width, height from cover_image where session_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long size = rs.getLong("size");
            String extensionString = rs.getString("extension");
            int width = rs.getInt("width");
            int height = rs.getInt("height");

            CoverImageFileSize fileSize = new CoverImageFileSize(size);
            CoverImageExtension extension = CoverImageExtension.from(extensionString);
            CoverImagePixelSize pixelSize = new CoverImagePixelSize(width, height);
            return new CoverImage(fileSize, extension, pixelSize);
        }, sessionId);
    }
}
