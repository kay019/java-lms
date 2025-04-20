package nextstep.courses.domain;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

public class SessionPeriod {
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public SessionPeriod(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt.isEqual(endAt) || startAt.isAfter(endAt)) {
            throw new InvalidParameterException("Start date must be before end date");
        }

        this.startAt = startAt;
        this.endAt = endAt;
    }
}
