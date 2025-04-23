package nextstep.courses.infrastructure;

import nextstep.courses.domain.PaidSession;
import nextstep.courses.domain.Session;
import nextstep.courses.domain.repository.SessionRepository;
import nextstep.support.builder.PaidSessionBuilder;
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
class JdbcPaidSessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcPaidSessionRepository(jdbcTemplate);
    }

    @Test
    void saveTest() {
        // given
        PaidSession paidSession = new PaidSessionBuilder().build();

        // when
        Long result = sessionRepository.save(paidSession);

        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void findByIdTest_found() {
        // given
        PaidSession paidSession = new PaidSessionBuilder().build();
        sessionRepository.save(paidSession);

        // when
        Optional<Session> result = sessionRepository.findById(1L);

        // then
        assertThat(result).isPresent();
        Session session = result.get();
        assertThat(session.getStatus()).isEqualTo(paidSession.getStatus());
        assertThat(session.getDate().getStartedAt()).isEqualTo(paidSession.getDate().getStartedAt());
        assertThat(session.getDate().getEndedAt()).isEqualTo(paidSession.getDate().getEndedAt());
        assertThat(session.getCreatedAt()).isEqualTo(paidSession.getCreatedAt());
        assertThat(((PaidSession) session).getFee()).isEqualTo(paidSession.getFee());
        assertThat(((PaidSession) session).getMaxStudent()).isEqualTo(paidSession.getMaxStudent());
    }

    @Test
    void findByIdTest_notFound() {
        // when
        Optional<Session> result = sessionRepository.findById(999L);

        // then
        assertThat(result).isEmpty();
    }

}
