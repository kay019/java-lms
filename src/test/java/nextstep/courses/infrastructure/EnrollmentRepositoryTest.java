package nextstep.courses.infrastructure;

import nextstep.courses.domain.Enrollment;
import nextstep.courses.domain.Enrollments;
import nextstep.courses.entity.EnrollmentEntity;
import nextstep.users.domain.NsUser;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class EnrollmentRepositoryTest {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  private JdbcEnrollmentRepository enrollmentRepository = new JdbcEnrollmentRepository(jdbcTemplate);
  private JdbcUserRepository userRepository = new JdbcUserRepository(jdbcTemplate);

  @BeforeEach
  void setUp() {
    enrollmentRepository = new JdbcEnrollmentRepository(jdbcTemplate);
    userRepository = new JdbcUserRepository(jdbcTemplate);
  }

  @Test
  void crud() {
    long sessionId = 1L;
    NsUser user1 = userRepository.findByUserId("javajigi").orElseThrow();
    NsUser user2 = userRepository.findByUserId("sanjigi").orElseThrow();

    Enrollments enrollments = new Enrollments(
            List.of(
                    new Enrollment(user1, sessionId),
                    new Enrollment(user2, sessionId)
            )
    );
    enrollmentRepository.saveAll(enrollments.toEnrollmentEntities(sessionId));

    List<EnrollmentEntity> enrollmentEntities = enrollmentRepository.findBySessionId(sessionId);
    for (EnrollmentEntity enrollmentEntity : enrollmentEntities) {
      assertThat(enrollmentEntity.sessionId()).isEqualTo(sessionId);
    }
  }
}
