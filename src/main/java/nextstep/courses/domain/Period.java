package nextstep.courses.domain;

import java.time.LocalDateTime;

public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        validateDate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InvalidPeriodException("기간의 시작 날짜가 끝나는 날짜보다 뒤입니다.");
        }
    }
}
