package nextstep.courses.infrastructure;

import java.sql.PreparedStatement;
import java.util.Optional;
import nextstep.courses.domain.Image;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

@Repository
public class JdbcImageRepository implements nextstep.courses.domain.ImageRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcImageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Image save(Image image) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
            connection -> {
                PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO image (url, width, height, format, size) VALUES (?, ?, ?, ?, ?)",
                    new String[] { "id" } // 자동 생성된 ID를 반환받도록 설정
                );
                ps.setString(1, image.getUrl());
                ps.setInt(2, image.getWidth());
                ps.setInt(3, image.getHeight());
                ps.setString(4, image.getFormat());
                ps.setInt(5, image.getSize());
                return ps;
            },
            keyHolder
        );

        Long id = keyHolder.getKey().longValue();
        return findById(id).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
    }

    @Override
    public Optional<Image> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
            "SELECT * FROM image WHERE id = ?",
            (rs, rowNum) -> new Image(rs.getLong("id"), rs.getString("url"), rs.getInt("width"), rs.getInt("height"), rs.getString("format"), rs.getInt("size")),
            id
        ));
    }
    

}
