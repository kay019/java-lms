package nextstep.courses.infrastructure.session.inner;

import java.sql.Timestamp;
import java.time.LocalDate;
import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import nextstep.courses.domain.session.inner.CoverImage;
import nextstep.courses.domain.session.inner.CoverImageRepository;
import nextstep.courses.domain.session.inner.ImageFormat;
import nextstep.courses.domain.session.inner.ImageSize;
import nextstep.courses.domain.session.inner.SessionStatus;
import nextstep.courses.domain.session.inner.SessionTypeFactory;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {
    private JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public int save(CoverImage coverImage) {
        String sql = "insert into cover_image (session_id, url, width, height, size, format) values(?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                coverImage.getSessionId(),
                coverImage.getUrl(),
                coverImage.getWidth(),
                coverImage.getHeight(),
                coverImage.getSize(),
                coverImage.getFormat().toString());
    }

    @Override
    public CoverImage findById(Long id) {
        String sql = "select id, session_id, url, width, height, size, format from cover_image where id = ?";
        RowMapper<CoverImage> rowMapper = (rs, rowNum) -> new CoverImage(
                rs.getLong(1),      // id
                rs.getLong(2),      // session_id
                rs.getString(3),    // url
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getString(7)
        );
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
