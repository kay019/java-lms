package nextstep.courses.domain.session.inner;

import nextstep.courses.domain.session.Session;
import nextstep.payments.domain.Payment;

public abstract class SessionType {

    protected String sessionType;
    protected int maxEnrollment;
    protected long fee;

    public abstract boolean enroll(Payment payment, Session session);

    public String getSessionType(){
        return sessionType;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public long getFee() {
        return fee;
    }
}
