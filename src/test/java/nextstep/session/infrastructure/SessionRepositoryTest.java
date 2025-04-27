package nextstep.session.infrastructure;

import static nextstep.users.domain.NsUserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionRepository;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.Student;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.FreeSessionType;
import nextstep.session.domain.type.SessionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class SessionRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SessionRepository sessionRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = new JdbcSessionRepository(jdbcTemplate);
    }

    @Test
    void crud() {
        int savedId = sessionRepository.save(createSession());

        assertThat(savedId).isEqualTo(1);

        Session savedSession = sessionRepository.findById(1L).orElse(null);
        assertThat(savedSession.getId()).isEqualTo(createSession().getId());
    }

    private Session createSession() {
        LocalDateTime startAt = LocalDateTime.of(2025, 3, 15, 0, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 4, 22, 0, 0, 0);
        SessionCover sessionCover = new SessionCover(1_048_576, "png", 300, 200);
        SessionType sessionType = new FreeSessionType();
        SessionStatus sessionStatus = SessionStatus.READY;
        Long capacity = 10L;
        Session session = new Session(1L, startAt, endAt, sessionCover, sessionType, sessionStatus, capacity, null);
        List<Student> students = List.of(new Student(JAVAJIGI, session));

        return new Session(1L, startAt, endAt, sessionCover, sessionType, sessionStatus, capacity, students);
    }

}
