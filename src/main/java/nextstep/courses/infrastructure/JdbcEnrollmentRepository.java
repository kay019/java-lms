package nextstep.courses.infrastructure;

import nextstep.courses.entity.EnrollmentEntity;
import nextstep.courses.repository.EnrollmentRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("enrollmentRepository")
public class JdbcEnrollmentRepository implements EnrollmentRepository {
  private JdbcOperations jdbcTemplate;

  public JdbcEnrollmentRepository(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public long save(EnrollmentEntity enrollmentEntity) {
    String sql = "INSERT INTO enrollment (session_id, user_id) VALUES (?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, new String[]{"id"});
      ps.setLong(1, enrollmentEntity.sessionId());
      ps.setString(2, enrollmentEntity.studentId());
      return ps;
    }, keyHolder);
    return keyHolder.getKey().longValue();
  }

  @Override
  public void saveAll(List<EnrollmentEntity> enrollments) {
    String sql = "INSERT INTO enrollment (session_id, user_id) VALUES (?, ?)";
    for (EnrollmentEntity enrollment : enrollments) {
      jdbcTemplate.update(sql, enrollment.sessionId(), enrollment.studentId());
    }
  }

  @Override
  public List<EnrollmentEntity> findBySessionId(Long sessionId) {
    String sql = "SELECT * FROM enrollment WHERE session_id = ?";
    return jdbcTemplate.query(sql, (rs, rowNum) -> new EnrollmentEntity(
            rs.getLong("id"),
            rs.getString("user_id"),
            rs.getLong("session_id")),
        sessionId);
  }
}
