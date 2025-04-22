package nextstep.courses.infrastructure.session;

import static nextstep.courses.domain.session.SessionEnrollmentTest.SESSION_ENROLLMENT1;
import static nextstep.courses.domain.session.SessionTest.SESSION1;
import static org.assertj.core.api.Assertions.assertThat;

import nextstep.courses.domain.session.SessionEnrollment;
import nextstep.courses.domain.session.SessionEnrollmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class SessionEnrollmentRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionEnrollmentRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionEnrollmentRepository sessionEnrollmentRepository;

    @BeforeEach
    void setUp() {
        sessionEnrollmentRepository = new JdbcSessionEnrollmentRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        SessionEnrollment sessionEnrollment = SESSION_ENROLLMENT1;
        int count = sessionEnrollmentRepository.save(SESSION_ENROLLMENT1);
        assertThat(count).isEqualTo(1);
        SessionEnrollment savedSessionEnrollment = sessionEnrollmentRepository.findById(1L);
        assertThat(sessionEnrollment.getSessionId()).isEqualTo(savedSessionEnrollment.getSessionId());
        assertThat(sessionEnrollment.getNsUserId()).isEqualTo(savedSessionEnrollment.getNsUserId());
        assertThat(sessionEnrollment.getStatus()).isEqualTo(savedSessionEnrollment.getStatus());
        LOGGER.debug("SessionEnrollment: {}", savedSessionEnrollment);
    }
}
