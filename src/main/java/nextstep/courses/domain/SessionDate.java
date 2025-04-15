package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionDate {
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    public SessionDate(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (endedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("endedAt은 startedAt 이후여야합니다.");
        }
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }
}
