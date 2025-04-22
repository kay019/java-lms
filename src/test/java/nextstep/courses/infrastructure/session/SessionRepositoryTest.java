package nextstep.courses.infrastructure.session;

import nextstep.courses.domain.session.Session;
import nextstep.courses.domain.session.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static nextstep.courses.domain.session.SessionTest.SESSION1;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        Session session = SESSION1;
        int count = sessionRepository.save(SESSION1);
        assertThat(count).isEqualTo(1);
        Session savedSession = sessionRepository.findById(1L);
        assertThat(session.getTitle()).isEqualTo(savedSession.getTitle());
        assertThat(session.getSessionType()).isEqualTo(savedSession.getSessionType());
        assertThat(session.getCourseId()).isEqualTo(savedSession.getCourseId());
        assertThat(session.getFee()).isEqualTo(savedSession.getFee());
        assertThat(session.getEnrollmentCount()).isEqualTo(savedSession.getEnrollmentCount());
        assertThat(session.getMaxEnrollment()).isEqualTo(savedSession.getMaxEnrollment());
        assertThat(session.getStatus()).isEqualTo(savedSession.getStatus());
        assertThat(session.getStartDate()).isEqualTo(savedSession.getStartDate());
        assertThat(session.getFinishDate()).isEqualTo(savedSession.getFinishDate());
        LOGGER.debug("Session: {}", savedSession);
    }
}
