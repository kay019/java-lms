package nextstep.courses.infrastructure;


import nextstep.courses.domain.Cohort;
import nextstep.courses.domain.Course;
import nextstep.courses.domain.FreeEnrollmentPolicy;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionStatus;
import nextstep.courses.entity.SessionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private JdbcCourseRepository courseRepository = new JdbcCourseRepository(jdbcTemplate);
  private JdbcCohortRepository cohortRepository = new JdbcCohortRepository(jdbcTemplate);
  private JdbcSessionRepository sessionRepository = new JdbcSessionRepository(jdbcTemplate);
  private JdbcImageRepository imageRepository = new JdbcImageRepository(jdbcTemplate);

  @BeforeEach
  void setUp() {
    sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    cohortRepository = new JdbcCohortRepository(jdbcTemplate);
    courseRepository = new JdbcCourseRepository(jdbcTemplate);
    imageRepository = new JdbcImageRepository(jdbcTemplate);
  }

  @Test
  void crud() {
    String title = "TDD, 클린 코드 with Java";
    Cohort cohort = cohortRepository.findByCourseId(1L).toCohort();
    Course course = courseRepository.findById(1L).toCourse(cohort);
    Image coverImage = imageRepository.findById(1L).toImage();
    Session session = new Session(course, title,
            LocalDateTime.now(), LocalDateTime.now().plusDays(1), coverImage, new FreeEnrollmentPolicy());
    long id = sessionRepository.save(session.toSessionEntity());

    SessionEntity savedSession = sessionRepository.findById(id);
    assertThat(savedSession.title()).isEqualTo(title);
    assertThat(savedSession.courseId()).isEqualTo(1L);
    assertThat(savedSession.coverImageId()).isEqualTo(1L);
    assertThat(savedSession.status()).isEqualTo(SessionStatus.PREPARING);
    assertThat(savedSession.price()).isEqualTo(0);
    assertThat(savedSession.maxCapacity()).isEqualTo(0);
  }
}