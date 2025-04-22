package nextstep.courses.infrastructure;

import java.util.Optional;
import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.SessionRepository;
import nextstep.courses.domain.ImageRepository;
import nextstep.courses.domain.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        ImageRepository imageRepository = new JdbcImageRepository(jdbcTemplate);
        ParticipantRepository participantRepository = new JdbcParticipantRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, imageRepository, participantRepository);
    }

    @Test
    @DisplayName("id로 조회할 수 있다.")
    void findById() {
        Optional<Session> savedSession = sessionRepository.findById(1L);
        assertThat(savedSession).isPresent();
        assertThat(savedSession.get()).isInstanceOf(FreeSession.class);

        Optional<Session> savedSession2 = sessionRepository.findById(2L);
        assertThat(savedSession2).isPresent();
        assertThat(savedSession2.get()).isInstanceOf(PaidSession.class);
        assertThat(savedSession2.get().isParticipant(1L)).isTrue();
        assertThat(savedSession2.get().isParticipant(2L)).isTrue();

        LOGGER.debug("Session: {}", savedSession);
        LOGGER.debug("Session: {}", savedSession2);
    }
}
