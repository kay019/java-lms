package nextstep.session.domain.property;

import nextstep.payments.domain.Payment;
import nextstep.session.domain.payment.SessionPayments;

public class SessionProperty {

    private final SessionStatus status;

    private final SessionType type;

    public SessionProperty() {
        this(SessionStatus.PREPARING, SessionType.FREE);
    }

    public SessionProperty(SessionStatus status, SessionType type) {
        this.status = status;
        this.type = type;
    }

    public boolean canNotEnroll(SessionPayments sessionPayments, Payment payment) {
        return type.canNotEnroll(sessionPayments, payment) && status.canNotEnroll();
    }
}
