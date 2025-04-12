package nextstep.session.domain;

import java.time.LocalDateTime;

public class SessionPeriod {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public SessionPeriod() {
    }

    public SessionPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
