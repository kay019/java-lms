package nextstep.courses.infrastructure;

import nextstep.courses.record.CoverImageRecord;
import nextstep.courses.domain.CoverImageRepository;
import nextstep.courses.domain.CoverImageType;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository("coverImageRepository")
public class JdbcCoverImageRepository implements CoverImageRepository {

    private final JdbcOperations jdbcTemplate;

    public JdbcCoverImageRepository(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int save(CoverImageRecord coverImage) {
        final String sql = "INSERT INTO cover_image (size, type, width, height) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int updated = jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, coverImage.getSize());
            ps.setString(2, coverImage.getType().name());
            ps.setInt(3, coverImage.getWidth());
            ps.setInt(4, coverImage.getHeight());
            return ps;
        }, keyHolder);

        long id = keyHolder.getKey().longValue();
        coverImage.setId(id);

        return updated;
    }

    @Override
    public CoverImageRecord findById(long id) {
        final String sql = "SELECT * FROM cover_image WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            CoverImageRecord coverImageRecord = new CoverImageRecord();
            coverImageRecord.setId(rs.getLong("id"));
            coverImageRecord.setSize(rs.getLong("size"));
            String type = rs.getString("type");
            coverImageRecord.setType(CoverImageType.valueOf(type));
            coverImageRecord.setWidth(rs.getInt("width"));
            coverImageRecord.setHeight(rs.getInt("height"));
            return coverImageRecord;
        }, id);
    }
}
