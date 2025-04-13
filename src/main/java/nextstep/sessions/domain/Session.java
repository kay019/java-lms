package nextstep.sessions.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Session {
    private final Period sessionPeriod;

    public Session(LocalDate startDate, LocalDate endDate) {
        this.sessionPeriod = new Period(startDate, endDate);
    }

    public Session(Period sessionPeriod) {
        this.sessionPeriod = sessionPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(sessionPeriod, session.sessionPeriod);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessionPeriod);
    }
}
