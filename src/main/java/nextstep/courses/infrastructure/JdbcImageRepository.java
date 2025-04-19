package nextstep.courses.infrastructure;

import nextstep.courses.entity.ImageEntity;
import nextstep.courses.repository.ImageRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("imageRepository")
public class JdbcImageRepository implements ImageRepository {
  private JdbcOperations jdbcTemplate;

  public JdbcImageRepository(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public long save(ImageEntity image) {
    String sql = "INSERT INTO image (filename, size_in_bytes, width_in_pixel, height_in_pixel) VALUES (?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
              var ps = connection.prepareStatement(sql, new String[]{"id"});
              ps.setString(1, image.filename());
              ps.setLong(2, image.sizeInBytes());
              ps.setInt(3, image.widthInPixel());
              ps.setInt(4, image.heightInPixel());
              return ps;
            }, keyHolder
    );
    return keyHolder.getKey().longValue();
  }

  public ImageEntity findById(Long id) {
    String sql = "SELECT * FROM image WHERE id = ?";
    return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new ImageEntity(
                    rs.getLong("id"),
                    rs.getString("filename"),
                    rs.getLong("size_in_bytes"),
                    rs.getInt("width_in_pixel"),
                    rs.getInt("height_in_pixel")
            ), id);
  }
}
