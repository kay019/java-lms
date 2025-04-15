package nextstep.courses.domain.session;

import java.time.LocalDateTime;

public class SessionPeriod {

    private final LocalDateTime startDate;

    private LocalDateTime endDate;

    public SessionPeriod() {
        this(LocalDateTime.now(), LocalDateTime.now());
    }

    public SessionPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
