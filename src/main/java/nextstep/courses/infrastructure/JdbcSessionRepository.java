package nextstep.courses.infrastructure;

import nextstep.courses.domain.SessionStatus;
import nextstep.courses.entity.SessionEntity;
import nextstep.courses.repository.SessionRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class JdbcSessionRepository implements SessionRepository {
  private final JdbcOperations jdbcTemplate;

  public JdbcSessionRepository(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public long save(SessionEntity sessionEntity) {
    String sql = "INSERT INTO session (course_id, cover_image_id, title, status, price, max_capacity, enrolled_cnt, start_date, end_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
              PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
              ps.setLong(1, sessionEntity.courseId());
              ps.setLong(2, sessionEntity.coverImageId());
              ps.setString(3, sessionEntity.title());
              ps.setString(4, sessionEntity.status().toString());
              ps.setLong(5, sessionEntity.price());
              ps.setInt(6, sessionEntity.maxCapacity());
              ps.setInt(7, sessionEntity.enrolledCnt());
              ps.setTimestamp(8, java.sql.Timestamp.valueOf(sessionEntity.startDate()));
              ps.setTimestamp(9, java.sql.Timestamp.valueOf(sessionEntity.endDate()));
              return ps;
            }, keyHolder
    );
    return keyHolder.getKey().longValue();
  }

  @Override
  public SessionEntity findById(Long id) {
    String sql = "SELECT * FROM session WHERE id = ?";

    return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new SessionEntity(
                    rs.getLong("id"),
                    rs.getLong("course_id"),
                    rs.getLong("cover_image_id"),
                    rs.getString("title"),
                    SessionStatus.valueOf(rs.getString("status")),
                    rs.getLong("price"),
                    rs.getInt("max_capacity"),
                    rs.getInt("enrolled_cnt"),
                    rs.getTimestamp("start_date").toLocalDateTime(),
                    rs.getTimestamp("end_date").toLocalDateTime()
            ), id
    );
  }
}
