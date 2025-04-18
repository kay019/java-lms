package nextstep.courses.domain;

import java.time.LocalDateTime;

public class SessionDate {
    private final LocalDateTime startedAt;
    private final LocalDateTime endedAt;

    public SessionDate(LocalDateTime startedAt, LocalDateTime endedAt) {
        validate(startedAt, endedAt);
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void validate(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (startedAt.isAfter(endedAt)) {
            throw new IllegalArgumentException("강의 시작일은 종료일 보다 늦을 수 없습니다.");
        }
    }
}
