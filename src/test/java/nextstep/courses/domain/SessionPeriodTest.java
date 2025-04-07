package nextstep.courses.domain;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SessionPeriodTest {

    public static SessionPeriod createSessionPeriod1() {
        LocalDateTime startedAt = LocalDateTime.of(2023, 10, 1, 0, 0, 0);
        LocalDateTime endedAt = LocalDateTime.of(2023, 10, 1, 23, 0, 0);
        return new SessionPeriod(startedAt, endedAt);
    }

}