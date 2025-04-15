package nextstep.courses.domain;

import java.time.LocalDate;

public class FreeSession extends Session {

    public FreeSession(Image coverImage, LocalDate startDate, LocalDate endDate) {
        super(coverImage, startDate, endDate);
    }

    @Override
    protected void validateEnrollment(long amount) {
        // 무료 세션은 참여할 수 있음
    }
}
