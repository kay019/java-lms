package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

class PaidSessionType extends SessionType {

    public PaidSessionType(int maxEnrollment, long fee) {
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
