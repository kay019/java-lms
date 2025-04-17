package nextstep.courses.domain;

import java.time.LocalDate;

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
}
