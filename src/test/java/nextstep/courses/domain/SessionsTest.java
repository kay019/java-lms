package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SessionsTest {
    @Test
    void 세션_중복_확인() {
        Session session1 = new Session(1L, null, null, null, null, null, 0, 0L);
        Session session2 = new Session(2L, null, null, null, null, null, 0, 0L);
        Sessions sessions = new Sessions(List.of(session1, session2));

        assertThat(sessions.isAlreadyIncluded(1L)).isTrue();
        assertThat(sessions.isAlreadyIncluded(2L)).isTrue();
        assertThat(sessions.isAlreadyIncluded(3L)).isFalse();
    }

    @Test
    void 세션_추가() {
        Session session1 = new Session(1L, null, null, null, null, null, 0, 0L);
        Session session2 = new Session(2L, null, null, null, null, null, 0, 0L);
        Sessions sessions = new Sessions(List.of(session1));

        Sessions updatedSessions = sessions.addSession(session2);

        assertThat(updatedSessions.isAlreadyIncluded(1L)).isTrue();
        assertThat(updatedSessions.isAlreadyIncluded(2L)).isTrue();
    }
}