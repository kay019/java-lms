package nextstep.courses.domain.enroll;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;

public class PaidSessionEnroll extends SessionEnroll {
    public PaidSessionEnroll(int fee, int maxStudent) {
        this.feePolicy = SessionFeePolicy.PAID;
        this.fee = fee;
        this.maxStudent = maxStudent;
    }

    @Override
    public void validateEnroll(Payment payment, Session session) {
        session.validateSessionFullStudent(maxStudent);

        if (fee != payment.getAmount()) {
            throw new IllegalArgumentException("수강생이 결제한 금액과 수강료가 일치할 때 수강 신청이 가능합니다.");
        }
    }
}
