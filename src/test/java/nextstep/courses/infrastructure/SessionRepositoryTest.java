package nextstep.courses.infrastructure;

import nextstep.courses.PayStrategyFactory;
import nextstep.courses.domain.*;
import nextstep.users.domain.NsUser;
import nextstep.users.domain.UserRepository;
import nextstep.users.infrastructure.JdbcUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
public class SessionRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepositoryTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        List<PayStrategy> strategies = List.of(
                new PaidPayStrategy(1000L),
                new FreePayStrategy()
        );
        sessionRepository = new JdbcSessionRepository(jdbcTemplate, new JdbcUserRepository(jdbcTemplate), new PayStrategyFactory(strategies));
    }

    @Test
    void crud() {
        Session session = new Session("TDD, 클린 코드 with Java", 1L);
        int count = sessionRepository.save(session);
        assertThat(count).isEqualTo(1);
        Session savedCourse = sessionRepository.findById(1L);
        assertThat(session.getTitle()).isEqualTo(savedCourse.getTitle());
        LOGGER.debug("Course: {}", savedCourse);
    }
}
