package nextstep.sessions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.cover.SessionCover;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    void createTest() {
        LocalDateTime startAt = LocalDateTime.of(2025, 3, 15, 0, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 4, 22, 0, 0, 0);
        SessionCover sessionCover = new SessionCover(1_048_576, "png", 300, 200);
        SessionStatus sessionStatus = SessionStatus.READY;

        Session session = new Session(startAt, endAt, sessionCover, sessionStatus);

        assertThat(session).isEqualTo(new Session(startAt, endAt, sessionCover, SessionStatus.READY));
    }
}
