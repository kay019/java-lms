package nextstep.courses.infrastructure;

import java.time.LocalDate;
import java.util.Optional;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Image;
import nextstep.courses.domain.Period;
import org.springframework.transaction.annotation.Transactional;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("id로 조회할 수 있다.")
    void findById() {
        Optional<Session> savedSession = sessionRepository.findById(1L);
        assertThat(savedSession).isPresent();
        LOGGER.debug("Session: {}", savedSession);
    }

    @Transactional
    @Test
    @DisplayName("session을 저장할 수 있다.")
    void save() {
        Image image = new Image("https://example.com/image.jpg", 300, 200, "jpg", 1000);
        Period period = new Period(LocalDate.now(), LocalDate.now().plusDays(1));
        Session session = new FreeSession(image, period);
        int save = sessionRepository.save(session);
        assertThat(save).isEqualTo(1);
    }
}
