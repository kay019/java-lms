package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SessionsTest {
    @Test
    void 세션_중복_확인() {
        Session session1 = new FreeSession(1L, null, null, null, null);
        Session session2 = new FreeSession(2L, null, null, null, null);
        Session session3 = new FreeSession(3L, null, null, null, null);

        Sessions sessions = new Sessions(List.of(session1, session2));

        assertThat(sessions.contains(session1)).isTrue();
        assertThat(sessions.contains(session2)).isTrue();
        assertThat(sessions.contains(session3)).isFalse();
    }

    @Test
    void 세션_추가() {
        Session session1 = new FreeSession(1L, null, null, null, null);
        Session session2 = new FreeSession(2L, null, null, null, null);
        Session session3 = new FreeSession(3L, null, null, null, null);

        Sessions sessions = new Sessions(List.of(session1, session2));
        Sessions updatedSessions = sessions.addSession(session3);

        assertThat(updatedSessions.contains(session1)).isTrue();
        assertThat(updatedSessions.contains(session2)).isTrue();
        assertThat(updatedSessions.contains(session3)).isTrue();
    }
}
