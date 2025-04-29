package nextstep.courses.infrastructure;

import nextstep.courses.domain.Enrollment;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.repository.EnrollmentRepository;
import nextstep.courses.domain.repository.SessionRepository;
import nextstep.support.builder.FreeSessionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql(scripts = "/reset-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class JdbcEnrollmentRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private EnrollmentRepository enrollmentRepository;
    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        enrollmentRepository = new JdbcEnrollmentRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void saveTest() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();
        Enrollment enrollment1 = Enrollment.requestEnroll(1L, 1L);
        Enrollment enrollment2 = Enrollment.requestEnroll(1L, 2L);

        // when
        Long sessionId = sessionRepository.save(freeSession);
        Long enrollmentId1 = enrollmentRepository.save(enrollment1);
        Long enrollmentId2 = enrollmentRepository.save(enrollment2);

        // then
        assertThat(sessionId).isEqualTo(1);
        assertThat(enrollmentId1).isEqualTo(1);
        assertThat(enrollmentId2).isEqualTo(2);
    }

    @Test
    void findByIdTest_found() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();
        Enrollment enrollment = Enrollment.requestEnroll(1L, 1L);
        sessionRepository.save(freeSession);
        enrollmentRepository.save(enrollment);

        // when
        Optional<Enrollment> result = enrollmentRepository.findById(1L, 1L);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getStatus()).isEqualTo(enrollment.getStatus());
        assertThat(result.get().getSessionId()).isEqualTo(enrollment.getSessionId());
        assertThat(result.get().getUserId()).isEqualTo(enrollment.getUserId());
    }

    @Test
    void findByIdTest_notFound() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();
        sessionRepository.save(freeSession);

        // when
        Optional<Enrollment> result = enrollmentRepository.findById(1L, 1L);

        // then
        assertThat(result).isEmpty();
    }
}
