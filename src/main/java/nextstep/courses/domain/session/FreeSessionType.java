package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

class FreeSessionType implements SessionType {

    @Override
    public boolean enroll(Payment payment, Session session) {
        return true;
    }
}