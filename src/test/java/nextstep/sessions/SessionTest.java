package nextstep.sessions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import nextstep.sessions.domain.Session;
import org.junit.jupiter.api.Test;

class SessionTest {
    @Test
    void createTest() {
        LocalDateTime startAt = LocalDateTime.of(2025, 3, 15, 0, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 4, 22, 0, 0, 0);

        Session session = new Session(startAt, endAt);

        assertThat(session).isEqualTo(new Session(startAt, endAt));
    }
}
