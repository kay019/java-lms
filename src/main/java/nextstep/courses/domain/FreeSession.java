package nextstep.courses.domain;

import java.time.LocalDate;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(Image coverImage, LocalDate startDate, LocalDate endDate) {
        super(coverImage, startDate, endDate);
    }

    @Override
    protected void validateEnrollment(Payment payment) {
        // 무료 세션은 참여할 수 있음
    }
}
