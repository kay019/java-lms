package nextstep.courses.infrastructure;

import nextstep.courses.PayStrategyFactory;
import nextstep.courses.domain.*;
import nextstep.users.domain.UserRepository;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.Month;


import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new JdbcUserRepository(jdbcTemplate);
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, new PayStrategyFactory());
    }

    @Test
    void crud() {
        Session session = new Session(1L,
                LocalDateTime.of(2025, Month.APRIL, 10, 15, 27),
                LocalDateTime.of(2025, Month.APRIL, 10, 15, 29),
                SessionState.RECRUTING,
                new PaidPayStrategy(),
                new Image(
                        1000L,
                        ImageType.JPG,
                        300L,
                        200L
                ),
                10L,
                1000L);
        session.register(userRepository.findByUserId("javajigi").get(), new PositiveNumber(1000L));
        LOGGER.debug("Student: {}", userRepository.findByUserId("javajigi").get());
        LOGGER.debug("Student Registry: {}", session.getRegistry().getStudents().get(0).getUserId());
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        Session savedSession = sessionRepository.findById(1L);
        assertThat(session.getPrice()).isEqualTo(savedSession.getPrice());
        LOGGER.debug("Session: {}", savedSession);
    }
}