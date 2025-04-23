package nextstep.courses.infrastructure;

import nextstep.courses.domain.FreeSession;
import nextstep.courses.domain.Session;
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
class JdbcFreeSessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcFreeSessionRepository(jdbcTemplate);
    }

    @Test
    void saveTest() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();

        // when
        Long result = sessionRepository.save(freeSession);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findByIdTest_found() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();
        sessionRepository.save(freeSession);

        // when
        Optional<Session> result = sessionRepository.findById(1L);

        // then
        assertThat(result).isPresent();
        Session session = result.get();
        assertThat(session.getStatus()).isEqualTo(freeSession.getStatus());
        assertThat(session.getDate().getStartedAt()).isEqualTo(freeSession.getDate().getStartedAt());
        assertThat(session.getDate().getEndedAt()).isEqualTo(freeSession.getDate().getEndedAt());
        assertThat(session.getCreatedAt()).isEqualTo(freeSession.getCreatedAt());
    }

    @Test
    void findByIdTest_notFound() {
        // when
        Optional<Session> result = sessionRepository.findById(1L);

        // then
        assertThat(result).isEmpty();
    }

}
