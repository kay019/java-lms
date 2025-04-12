package nextstep.courses.domain.session.inner;

import nextstep.courses.domain.session.Session;
import nextstep.payments.domain.Payment;

public class FreeSessionType extends SessionType {

    private static final String TYPE = "FREE";

    public FreeSessionType() {
        this.sessionType = TYPE;
        this.maxEnrollment = -1;
        this.fee = 0;
    }

    @Override
    public boolean enroll(Payment payment, Session session) {
        return true;
    }
}