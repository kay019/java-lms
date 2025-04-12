package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionPeriod {

    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public SessionPeriod(LocalDateTime startedAt, LocalDateTime endedAt) {
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }
}
