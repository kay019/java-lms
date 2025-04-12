package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

class FreeSessionType extends SessionType {

    public FreeSessionType() {
        this.maxEnrollment = -1;
        this.fee = 0;
    }

    @Override
    public boolean enroll(Payment payment, Session session) {
        return true;
    }
}