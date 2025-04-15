package nextstep.courses.domain.enroll;

import nextstep.courses.domain.Session;
import nextstep.payments.domain.Payment;

public abstract class SessionEnroll {
    protected SessionFeePolicy feePolicy;
    protected int fee;
    protected int maxStudent;

    public abstract void validateEnroll(Payment payment, Session session);

}
