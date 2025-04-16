package nextstep.courses.domain;

import java.time.LocalDate;

public class Term {
    private LocalDate startDate;
    private LocalDate endDate;

    public Term(LocalDate startDate, LocalDate endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("기간의 시작일과 종료일은 필수입니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("기간의 시작일은 종료일보다 늦을 수 없습니다.");
        }
    }
}
