package nextstep.courses.domain.session.inner;

import nextstep.courses.domain.session.Session;
import nextstep.payments.domain.Payment;

public class PaidSessionType extends SessionType {

    public static final String TYPE = "PAID";

    public PaidSessionType(int maxEnrollment, long fee) {
        this.sessionType = TYPE;
        this.maxEnrollment = maxEnrollment;
        this.fee = fee;
    }

    @Override
    public boolean enroll(Payment payment, Session session) {
        if (session.enrollmentCountOver(maxEnrollment)) {
            return false;
        }

        return payment.enoughAmount(fee);
    }
}
