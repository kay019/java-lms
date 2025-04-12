package nextstep.courses.domain.session;

import nextstep.payments.domain.Payment;

public abstract class SessionType {

    protected int maxEnrollment;
    protected long fee;

    abstract boolean enroll(Payment payment, Session session);
}
