package nextstep.courses.infrastructure;

import nextstep.courses.domain.image.CoverImage;
import nextstep.courses.domain.image.CoverImageExtension;
import nextstep.courses.domain.image.CoverImageFileSize;
import nextstep.courses.domain.image.CoverImagePixelSize;
import nextstep.courses.domain.repository.CoverImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CoverImage image, Long sessionId) {
        String sql = "insert into cover_image (session_id, size, extension, width, height) " +
                "values(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                sessionId,
                image.getFileSize().getSize(),
                image.getExtension().name(),
                image.getPixelSize().getWidth(),
                image.getPixelSize().getHeight()
        );
    }

    @Override
    public Optional<CoverImage> findBySessionId(Long sessionId) {
        String sql = "select id, size, extension, width, height from cover_image where session_id = ?";

        return jdbcTemplate.query(sql, rs -> {
            if (!rs.next()) {
                return Optional.empty();
            }

            long size = rs.getLong("size");
            String extensionString = rs.getString("extension");
            int width = rs.getInt("width");
            int height = rs.getInt("height");

            CoverImageFileSize fileSize = new CoverImageFileSize(size);
            CoverImageExtension extension = CoverImageExtension.from(extensionString);
            CoverImagePixelSize pixelSize = new CoverImagePixelSize(width, height);
            CoverImage coverImage = new CoverImage(fileSize, extension, pixelSize);

            return Optional.of(coverImage);
        }, sessionId);
    }
}
