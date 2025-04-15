package nextstep.support.fixture;

import nextstep.courses.domain.SessionDate;

import java.time.LocalDateTime;

public class SessionDateFixture {
    private static final LocalDateTime STARTED_AT = LocalDateTime.of(2025, 1, 1, 0, 0);
    private static final LocalDateTime ENDED_AT = LocalDateTime.of(2025, 6, 1, 0, 0);

    public static SessionDate create() {
        return new SessionDate(STARTED_AT, ENDED_AT);
    }
}
