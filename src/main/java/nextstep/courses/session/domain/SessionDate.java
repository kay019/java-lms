package nextstep.courses.session.domain;

import java.time.LocalDateTime;

public class SessionDate {
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public SessionDate(LocalDateTime startDate, LocalDateTime endDate) {
        validate(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validate(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate) || startDate.isEqual(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이후일 수 없습니다.");
        }
    }
}
