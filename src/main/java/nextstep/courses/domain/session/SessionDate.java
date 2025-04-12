package nextstep.courses.domain.session;

import java.time.LocalDate;

public class SessionDate {

    private LocalDate startDate;

    private LocalDate finishDate;

    public SessionDate(LocalDate startDate, LocalDate finishDate) {
        validateDateRange(startDate, finishDate);
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    private void validateDateRange(LocalDate startDate, LocalDate finishDate) {
        if (startDate == null || finishDate == null || startDate.isAfter(finishDate)) {
            throw new IllegalArgumentException("종료일은 시작일보다 뒤에 있어야 합니다.");
        }
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate finishDate() {
        return finishDate;
    }
}
