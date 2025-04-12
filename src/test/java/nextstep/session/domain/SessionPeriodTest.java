package nextstep.session.domain;

import java.time.LocalDateTime;

class SessionPeriodTest {

    public static SessionPeriod createSessionPeriod1() {
        LocalDateTime startedAt = LocalDateTime.of(2023, 10, 1, 0, 0, 0);
        LocalDateTime endedAt = LocalDateTime.of(2023, 10, 1, 23, 0, 0);
        return new SessionPeriod(startedAt, endedAt);
    }

}
