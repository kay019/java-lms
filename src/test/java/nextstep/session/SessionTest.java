package nextstep.session;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import nextstep.session.domain.EnrollmentStatus;
import nextstep.session.domain.Session;
import nextstep.session.domain.SessionStatus;
import nextstep.session.domain.cover.SessionCover;
import nextstep.session.domain.type.FreeSessionType;
import nextstep.session.domain.type.SessionType;
import org.junit.jupiter.api.Test;

public class SessionTest {
    public static Session createSession() {
        LocalDateTime startAt = LocalDateTime.of(2025, 3, 15, 0, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 4, 22, 0, 0, 0);
        SessionCover sessionCover = new SessionCover(1_048_576, "png", 300, 200);
        SessionType sessionType = new FreeSessionType();
        SessionStatus sessionStatus = SessionStatus.READY;
        EnrollmentStatus enrollmentStatus = EnrollmentStatus.ENROLLED;
        Long capacity = 10L;

        return new Session(1L, startAt, endAt, sessionCover, sessionType, sessionStatus, enrollmentStatus, capacity,
                null);
    }

    @Test
    void createTest() {
        LocalDateTime startAt = LocalDateTime.of(2025, 3, 15, 0, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 4, 22, 0, 0, 0);
        SessionCover sessionCover = new SessionCover(1_048_576, "png", 300, 200);
        SessionType sessionType = new FreeSessionType();
        SessionStatus sessionStatus = SessionStatus.READY;
        EnrollmentStatus enrollmentStatus = EnrollmentStatus.ENROLLED;
        Long capacity = 10L;

        Session session = createSession();

        assertThat(session).isEqualTo(
                new Session(1L, startAt, endAt, sessionCover, sessionType, sessionStatus, enrollmentStatus, capacity,
                        null));
    }

}
