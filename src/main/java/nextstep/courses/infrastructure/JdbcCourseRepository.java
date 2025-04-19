package nextstep.courses.infrastructure;

import nextstep.courses.entity.CohortEntity;
import nextstep.courses.entity.CourseEntity;
import nextstep.courses.repository.CourseRepository;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository("courseRepository")
public class JdbcCourseRepository implements CourseRepository {
  private final JdbcOperations jdbcTemplate;

  public JdbcCourseRepository(JdbcOperations jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public long save(CourseEntity course) {
    String sql = "INSERT INTO COURSE (title, creator_id, cohort_id, created_at, updated_at) values(?, ?, ?, ?, ?)";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      var ps = connection.prepareStatement(sql, new String[]{"id"});
      ps.setString(1, course.title());
      ps.setLong(2, course.creatorId());
      ps.setLong(3, course.cohortId());
      ps.setTimestamp(4, java.sql.Timestamp.valueOf(course.createdAt()));
      ps.setTimestamp(5, java.sql.Timestamp.valueOf(course.updatedAt()));
      return ps;
    }, keyHolder);
    return keyHolder.getKey().longValue();
  }

  @Override
  public CourseEntity findById(Long id) {
    String sql = "SELECT id, title, creator_id, cohort_id, created_at, updated_at from course where id = ?";
    return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new CourseEntity(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getLong("creator_id"),
                    rs.getLong("cohort_id"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("updated_at").toLocalDateTime()
            ), id);
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
  public CohortEntity findCohortByCourseId(Long courseId) {
    String sql = "SELECT id, course_id, name FROM cohort WHERE course_id = ?";
    return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new CohortEntity(
                    rs.getLong("id"),
                    rs.getLong("course_id"),
                    rs.getString("name")
            ), courseId);
  }
}
