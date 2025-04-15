package nextstep.courses.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class SessionsTest {

    @Test
    @DisplayName("세션을 추가할 수 있다.")
    void addSession() {
        Sessions sessions = new Sessions();
        sessions.add(SessionTest.DEFAULT_SESSION);
        assertThat(sessions.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("기수에 해당하는 세션을 조회할 수 있다.")
    void getSessionByTerm() {
        Sessions sessions = new Sessions();
        sessions.add(SessionTest.DEFAULT_SESSION);
        assertThat(sessions.getByTerm(1)).isEqualTo(SessionTest.DEFAULT_SESSION);
    }

    @Test
    @DisplayName("기수에 해당하는 세션이 없으면 null을 반환한다.")
    void getSessionByTerm_null() {
        Sessions sessions = new Sessions();
        assertThat(sessions.getByTerm(1)).isNull();
    }
}
