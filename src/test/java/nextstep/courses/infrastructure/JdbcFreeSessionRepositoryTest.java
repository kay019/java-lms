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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcFreeSessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcFreeSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        // given
        FreeSession freeSession = new FreeSessionBuilder().build();

        // when
        sessionRepository.save(freeSession);
        Optional<Session> result = sessionRepository.findById(1L);

        // then
        assertThat(result).isPresent();
        Session session = result.get();
        assertThat(session.getStatus()).isEqualTo(freeSession.getStatus());
        assertThat(session.getDate().getStartedAt()).isEqualTo(freeSession.getDate().getStartedAt());
        assertThat(session.getDate().getEndedAt()).isEqualTo(freeSession.getDate().getEndedAt());
        assertThat(session.getCreatedAt()).isEqualTo(freeSession.getCreatedAt());
    }

}
