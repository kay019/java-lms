package nextstep.courses.infrastructure;

import nextstep.courses.domain.ImageType;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionImageInfo;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.SessionStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository("sessionRepository")
public class JdbcSessionRepository implements SessionRepository {

  private JdbcOperations jdbcTemplate;

  public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public int save(Session session) {
    String sql =
        "INSERT INTO session (start_date, end_date, image_type, image_size, width, height, is_free, fee, max_capacity, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    return jdbcTemplate.update(sql,
        session.getStartDate(),
        session.getEndDate(),
        session.getSessionImageInfo().getImageType().name(),
        session.getSessionImageInfo().getImageSize(),
        session.getSessionImageInfo().getWidth(),
        session.getSessionImageInfo().getHeight(),
        session.isFree(),
        session.getFee(),
        session.getMaxCapacity(),
        session.getStatus().name()
    );
  }

  @Override
  public Session findById(Long id) {
    String sql = "SELECT start_date, end_date, image_type, image_size, width, height, is_free, fee, max_capacity, status FROM session WHERE id = ?";
    RowMapper<Session> rowMapper = (rs, rowNum) -> new Session(
        rs.getTimestamp("start_date").toLocalDateTime(),
        rs.getTimestamp("end_date").toLocalDateTime(),
        new SessionImageInfo(
            ImageType.valueOf(rs.getString("image_type")),
            rs.getInt("image_size"),
            rs.getInt("width"),
            rs.getInt("height")
        ),
        rs.getBoolean("is_free"),
        rs.getInt("fee"),
        rs.getInt("max_capacity"),
        SessionStatus.valueOf(rs.getString("status"))
    );
    return jdbcTemplate.queryForObject(sql, rowMapper, id);
  }
}
