package nextstep.courses.domain;

import nextstep.payments.domain.Payment;

public class FreeSession extends Session {

    public FreeSession(Image coverImage, Period period) {
        super(coverImage, period);
    }

    @Override
    protected void validateEnrollment(Payment payment) {
        // 무료 세션은 참여할 수 있음
    }
}
