package nextstep.session.domain;

import java.time.LocalDate;
import java.util.Objects;

import nextstep.exception.SessionDateIllegalArgumentException;

public class SessionDate {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public SessionDate(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);

        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new SessionDateIllegalArgumentException();
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SessionDate that = (SessionDate) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate);
    }
}
