package nextstep.courses.domain.enroll;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;

public class FreeSessionEnroll extends SessionEnroll {
    public FreeSessionEnroll() {
        this.feePolicy = SessionFeePolicy.FREE;
        this.fee = 0;
        this.maxStudent = 0;
    }

    @Override
    public void validateEnroll(Payment payment, Session session) {
        // 무료 강의는 제한 없이 가입 가능
    }
}
