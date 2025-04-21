package nextstep.courses.infrastructure;

import nextstep.courses.entity.CohortEntity;
import nextstep.courses.repository.CohortRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCohortRepository implements CohortRepository {
  private final JdbcOperations jdbcTemplate;

  public JdbcCohortRepository(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public long saveCohort(CohortEntity cohortEntity) {
    String sql = "INSERT INTO cohort (course_id, name) values(?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, new String[]{"id"});
      ps.setLong(1, cohortEntity.courseId());
      ps.setString(2, cohortEntity.name());
      return ps;
    }, keyHolder);
    return keyHolder.getKey().longValue();
  }

  @Override
  public CohortEntity findByCourseId(Long courseId) {
    String sql = "SELECT id, course_id, name FROM cohort WHERE course_id = ?";
    return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new CohortEntity(
                    rs.getLong("id"),
                    rs.getLong("course_id"),
                    rs.getString("name")
            ), courseId);
  }
}
