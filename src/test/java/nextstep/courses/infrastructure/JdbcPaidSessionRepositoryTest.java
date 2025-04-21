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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcPaidSessionRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcPaidSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        // given
        PaidSession paidSession = new PaidSessionBuilder().build();

        // when
        sessionRepository.save(paidSession);
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

}
