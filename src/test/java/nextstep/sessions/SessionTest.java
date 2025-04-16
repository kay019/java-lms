package nextstep.sessions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import nextstep.sessions.domain.Session;
import nextstep.sessions.domain.SessionStatus;
import nextstep.sessions.domain.cover.SessionCover;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @DisplayName("강의 수강신청은 강의 상태가 모집중일 때만 가능하다.")
    @ParameterizedTest
    @CsvSource({"READY", "CLOSED"})
    void validateSessionInProgressInvalidTest(SessionStatus sessionStatus) {
        Session session = new Session(sessionStatus);

        assertThatThrownBy(session::validateSessionInProgress)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("session is not in progress");
    }

    @Test
    @DisplayName("강의 상태가 모집중일 때는 검증을 통과한다")
    void validateSessionInProgressTest() {
        Session session = new Session(SessionStatus.ONGOING);

        assertThatCode(session::validateSessionInProgress)
                .doesNotThrowAnyException();
    }

}
