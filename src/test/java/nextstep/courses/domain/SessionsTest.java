package nextstep.courses.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SessionsTest {
    @Test
    void 세션_중복_확인() {
        Sessions sessions = new Sessions(List.of(1L, 2L));

        assertThat(sessions.isAlreadyIncluded(1L)).isTrue();
        assertThat(sessions.isAlreadyIncluded(2L)).isTrue();
        assertThat(sessions.isAlreadyIncluded(3L)).isFalse();
    }

    @Test
    void 세션_추가() {
        Sessions sessions = new Sessions(List.of(1L, 2L));
        Sessions updatedSessions = sessions.addSession(3L);

        assertThat(updatedSessions.isAlreadyIncluded(1L)).isTrue();
        assertThat(updatedSessions.isAlreadyIncluded(2L)).isTrue();
    }
}
