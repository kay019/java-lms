package nextstep.session.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SessionStatusTest {
    @Test
    void isSameAsTest() {
        SessionStatus sessionStatus = SessionStatus.READY;

        assertThat(sessionStatus.isSameAs(SessionStatus.READY)).isTrue();
    }
}
